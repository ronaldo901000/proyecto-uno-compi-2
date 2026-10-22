package com.ronaldo.cd3.compiler.api.services.generador.riscv;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.Cuarteta;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.ir.MotorIR;
import com.ronaldo.cd3.compiler.api.modelos.ir.PoolLiterales;
import com.ronaldo.cd3.compiler.api.modelos.vtable.EntradaVTable;
import com.ronaldo.cd3.compiler.api.modelos.vtable.TablaVTables;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Generador de codigo RISC-V (RV32). Recorre el IR con un
 * {@link EmisorRiscV} y ensambla la salida final: seccion .data (literales del
 * pool), .text (con un punto de entrada main) y las rutinas de soporte de
 * {@link RuntimeRiscV}.
 *
 * <p>Las vTables se generan con direcciones numericas (no nombres de etiqueta)
 * para ser compatibles con ensambladores como Venus que no resuelven
 * etiquetas en directivas {@code .word}.
 */
public class GeneradorRiscV {

    private static final int BASE_MEMORIA = 0x00010000;

    private final int direccionInicioHeap = 0x10040000;

    public String generar(ListaCuartetas cuartetas) {
        if (cuartetas == null || cuartetas.getCuartetas().isEmpty()) {
            return "";
        }

        MotorIR motor = new MotorIR(cuartetas);
        EmisorRiscV emisor = new EmisorRiscV(motor.getAmbiente());
        motor.recorrer(emisor);
        emisor.finalizar();

        // Seccion .data con strings y literales del pool (sin vTables todavia).
        String seccionDatos = seccionDatos(motor.getAmbiente().getPoolLiterales());
        int tamanioDatos = tamanioEnBytes(seccionDatos);

        // Cuerpo .text (sin vTables, que van en .data).
        String cuerpo = generarCuerpo(emisor, cuartetas);

        // Escanear etiquetas del cuerpo para calcular sus direcciones.
        // .text empieza despues de .data (strings + vTables). El tamaño de
        // vTables se conoce de antemano (entrada x 4 bytes + alineacion).
        int tamanioVTables = tamanioVTables(cuartetas);
        int direccionInicioTexto = BASE_MEMORIA + tamanioDatos + tamanioVTables;
        Map<String, Integer> direccionesEtiquetas = escanearEtiquetas(cuerpo, direccionInicioTexto);

        // Seccion .data con vTables usando direcciones numericas.
        String seccionVTables = seccionVTables(cuartetas, direccionesEtiquetas);

        // Ensamblar salida final.
        StringBuilder codigo = new StringBuilder();
        codigo.append(seccionDatos);
        codigo.append(seccionVTables);
        codigo.append(".text\n");
        codigo.append(".globl main\n");
        codigo.append("main:\n");
        String entrada = buscarEntrada(cuartetas);
        if (entrada != null) {
            String real = "__entrada_" + entrada;
            cuerpo = cuerpo.replaceAll(
                    "(?m)^" + Pattern.quote(entrada) + ":\\s*$",
                    real + ":");
            codigo.append("    call ").append(real).append("\n");
        }
        codigo.append("    call z_exit\n\n");
        codigo.append(cuerpo);
        codigo.append("    j z_exit\n\n");
        RuntimeRiscV runtime = new RuntimeRiscV();
        codigo.append(runtime.codigo());
        return codigo.toString();
    }

    /**
     * Genera el cuerpo .text (sin seccion .data ni vTables).
     */
    private String generarCuerpo(EmisorRiscV emisor, ListaCuartetas cuartetas) {
        StringBuilder sb = new StringBuilder();
        String cuerpo = emisor.getCuerpo();
        sb.append(cuerpo);
        return sb.toString();
    }

    /**
     * Estima el tamaño en bytes de una sección de texto ensamblador.
     * Cada línea que no es etiqueta ni directaiva cuenta como 4 bytes.
     * Las directivas .align y .word/.space/.byte/.asciiz se estiman.
     */
    private int tamanioEnBytes(String seccionTexto) {
        int tamanio = 0;
        for (String linea : seccionTexto.split("\n")) {
            String s = linea.trim();
            if (s.isEmpty() || s.startsWith("#")) {
                continue;
            }
            if (s.endsWith(":") && !s.startsWith(".")) {
                continue; // etiqueta, no ocupa bytes
            }
            if (s.startsWith(".align")) {
                try {
                    int exp = Integer.parseInt(s.substring(s.indexOf(' ') + 1).trim());
                    int alineacion = 1 << exp;
                    while ((tamanio % alineacion) != 0) {
                        tamanio++;
                    }
                } catch (Exception ex) {
                    // ignorar
                }
                continue;
            }
            if (s.startsWith(".asciiz")) {
                int ini = s.indexOf('"');
                int fin = s.lastIndexOf('"');
                if (ini >= 0 && fin > ini) {
                    tamanio += (fin - ini - 1) + 1; // contenido + nulo
                }
                continue;
            }
            if (s.startsWith(".float")) {
                tamanio += 4;
                continue;
            }
            if (s.startsWith(".word")) {
                tamanio += 4;
                continue;
            }
            if (s.startsWith(".space") || s.startsWith(".zero")) {
                try {
                    tamanio += Integer.parseInt(s.substring(s.indexOf(' ') + 1).trim());
                } catch (Exception ex) {
                    // ignorar
                }
                continue;
            }
            // Cualquier otra cosa (instrucción) = 4 bytes.
            tamanio += 4;
        }
        return tamanio;
    }

    /**
     * Calcula el tamaño en bytes que ocuparán las vTables en .data.
     */
    private int tamanioVTables(ListaCuartetas cuartetas) {
        TablaVTables vTables = (cuartetas != null) ? cuartetas.getVTables() : null;
        if (vTables == null || vTables.getVTables().isEmpty()) {
            return 0;
        }
        int total = 0;
        for (List<EntradaVTable> tabla : vTables.getVTables().values()) {
            if (tabla == null || tabla.isEmpty()) {
                continue;
            }
            // .align 2 (alineación a 4) - el padding es a lo sumo 3 bytes.
            total += 3;
            // Etiqueta (__vtable_xxx:) - no ocupa bytes.
            // Cada entrada = 1 palabra = 4 bytes.
            total += tabla.size() * 4;
        }
        return total;
    }

    /**
     * Escanea el cuerpo .text y mapea cada etiqueta a su dirección de memoria.
     * La dirección empieza en {@code direccionInicio} y cada instrucción ocupa 4 bytes.
     */
    private Map<String, Integer> escanearEtiquetas(String cuerpo, int direccionInicio) {
        Map<String, Integer> direcciones = new LinkedHashMap<>();
        int direccion = direccionInicio;
        for (String linea : cuerpo.split("\n")) {
            String s = linea.trim();
            if (s.isEmpty() || s.startsWith("#")) {
                continue;
            }
            if (s.endsWith(":") && !s.startsWith(".")) {
                String nombre = s.substring(0, s.length() - 1).trim();
                direcciones.putIfAbsent(nombre, direccion);
                continue;
            }
            // Es una instrucción: 4 bytes.
            direccion += 4;
        }
        return direcciones;
    }

    private String seccionDatos(PoolLiterales pool) {
        StringBuilder datos = new StringBuilder();
        datos.append(".data\n");
        if (pool != null) {
            for (PoolLiterales.EntradaLiteral entrada : pool.getEntradas()) {
                datos.append(".align 2\n");
                datos.append(entrada.getEtiqueta()).append(": ");
                if (esCadena(entrada)) {
                    datos.append(".asciiz ").append(entrada.getValor());
                } else {
                    datos.append(".word ").append((int) Double.parseDouble(entrada.getValor()));
                }
                datos.append("\n");
            }
        }
        return datos.toString();
    }

    /**
     * Genera la sección de vTables usando direcciones numéricas resueltas.
     */
    private String seccionVTables(ListaCuartetas cuartetas, Map<String, Integer> direcciones) {
        TablaVTables vTables = (cuartetas != null) ? cuartetas.getVTables() : null;
        if (vTables == null || vTables.getVTables().isEmpty()) {
            return "";
        }
        StringBuilder datos = new StringBuilder();
        for (Map.Entry<String, List<EntradaVTable>> entrada
                : vTables.getVTables().entrySet()) {
            List<EntradaVTable> tabla = entrada.getValue();
            if (tabla == null || tabla.isEmpty()) {
                continue;
            }
            datos.append(".align 2\n");
            datos.append("__vtable_").append(entrada.getKey()).append(":\n");
            for (EntradaVTable entradaVTable : tabla) {
                String etiqueta = entradaVTable.getEtiquetaImplementacion();
                Integer dir = direcciones.get(etiqueta);
                if (dir == null) {
                    // Etiqueta no encontrada (no debería ocurrir); usar 0.
                    dir = 0;
                }
                datos.append("    .word ").append(dir).append("\n");
            }
        }
        return datos.toString();
    }

    private String buscarEntrada(ListaCuartetas cuartetas) {
        String candidata = null;
        for (Cuarteta cuarteta : cuartetas.getCuartetas()) {
            if (cuarteta.getOperador() != OperadorCuarteta.ETIQUETA) {
                continue;
            }
            String etiqueta = cuarteta.getArg1();
            if (etiqueta == null
                    || cuartetas.categoriaDe(etiqueta) == TipoOperando.ETIQUETA_INTERNA) {
                continue;
            }
            if (etiqueta.equals("main")) {
                return etiqueta;
            }
            if (candidata == null && tieneMain(etiqueta)) {
                candidata = etiqueta;
            }
        }
        return candidata;
    }

    private boolean tieneMain(String etiqueta) {
        return etiqueta.contains("_main_") || etiqueta.endsWith("main");
    }

    private boolean esCadena(PoolLiterales.EntradaLiteral entrada) {
        return entrada.getValor() != null && entrada.getValor().trim().startsWith("\"");
    }

    private int alineacionExponente(int alineacion) {
        int exponente = 0;
        int valor = Math.max(1, alineacion);
        while (valor > 1) {
            valor >>= 1;
            exponente++;
        }
        return exponente;
    }

    /**
     * Genera el binario ELF32 RV32 listo para ejecutar con
     * {@code qemu-riscv32}, ensamblando el texto de {@link #generar} con
     * {@link EnsambladorRiscV32}.
     */
    public byte[] generarElf(ListaCuartetas cuartetas) {
        String asm = generar(cuartetas);
        EnsambladorRiscV32 ensamblador = new EnsambladorRiscV32(asm);
        return ensamblador.ensamblar();
    }

    public int getEntryPoint(ListaCuartetas cuartetas) {
        String asm = generar(cuartetas);
        EnsambladorRiscV32 ensamblador = new EnsambladorRiscV32(asm);
        return ensamblador.getEntryPoint();
    }
}
