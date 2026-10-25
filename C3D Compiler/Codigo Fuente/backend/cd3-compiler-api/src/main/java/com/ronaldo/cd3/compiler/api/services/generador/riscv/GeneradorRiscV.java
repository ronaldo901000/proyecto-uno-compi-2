package com.ronaldo.cd3.compiler.api.services.generador.riscv;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.Cuarteta;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.ir.EntradaLiteral;
import com.ronaldo.cd3.compiler.api.modelos.ir.MotorIR;
import com.ronaldo.cd3.compiler.api.modelos.ir.PoolLiterales;
import com.ronaldo.cd3.compiler.api.modelos.vtable.EntradaVTable;
import com.ronaldo.cd3.compiler.api.modelos.vtable.TablaVTables;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 
 * @author ronaldo
 */
public class GeneradorRiscV {

    public String generar(ListaCuartetas cuartetas) {
        if (cuartetas == null || cuartetas.getCuartetas().isEmpty()) {
            return "";
        }

        // Recorrer el IR y emitir el cuerpo .text
        MotorIR motor = new MotorIR(cuartetas);
        EmisorRiscV emisor = new EmisorRiscV(motor.getAmbiente());
        motor.recorrer(emisor);
        emisor.finalizar();

        // Seccion .data con strings y literales del pool
        String seccionDatos = seccionDatos(motor.getAmbiente().getPoolLiterales());

        // Cuerpo .text
        String cuerpo = emisor.getCuerpo();

        // vTables solo para las clases cuyos metodos se generaron en el cuerpo
        Set<String> clasesConMetodos = extraerClasesConMetodos(cuerpo);
        String seccionVTables = seccionVTables(cuartetas, clasesConMetodos);

        // Ensamblar salida final
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

        RuntimeRiscV runtime = new RuntimeRiscV();
        codigo.append(runtime.codigo());
        return codigo.toString();
    }

    private String seccionDatos(PoolLiterales pool) {
        StringBuilder datos = new StringBuilder();
        datos.append(".data\n");
        if (pool != null) {
            for (EntradaLiteral entrada : pool.getEntradas()) {
                datos.append(".align 2\n");
                datos.append(entrada.getEtiqueta()).append(": ");
                if (esCadena(entrada)) {
                    datos.append(".asciz ").append(entrada.getValor());
                } else {
                    double val = Double.parseDouble(entrada.getValor());
                    int escalado = (int) Math.round(val * 1000.0);
                    datos.append(".word ").append(escalado);
                }
                datos.append("\n");
            }
        }
        return datos.toString();
    }

    /**
     * Genera la seccion de vTables usando etiquetas de metodos. Solo emite
     * vTables para clases cuyos metodos se generan en el cuerpo.
     */
    private String seccionVTables(ListaCuartetas cuartetas, Set<String> clasesConMetodos) {
        TablaVTables vTables = (cuartetas != null) ? cuartetas.getVTables() : null;
        if (vTables == null || vTables.getVTables().isEmpty()) {
            return "";
        }
        StringBuilder datos = new StringBuilder();
        for (Map.Entry<String, List<EntradaVTable>> entrada
                : vTables.getVTables().entrySet()) {
            if (!clasesConMetodos.contains(entrada.getKey())) {
                continue;
            }
            List<EntradaVTable> tabla = entrada.getValue();
            if (tabla == null || tabla.isEmpty()) {
                continue;
            }
            datos.append(".align 2\n");
            datos.append("__vtable_").append(entrada.getKey()).append(":\n");
            for (EntradaVTable entradaVTable : tabla) {
                datos.append("    .word ")
                        .append(entradaVTable.getEtiquetaImplementacion())
                        .append("\n");
            }
        }
        return datos.toString();
    }

    /**
     * Extrae los nombres de clase cuyas etiquetas de metodo aparecen en el
     * cuerpo. Formato: metodo_Clase_metodo_N o constructor_Clase_N
     */
    private Set<String> extraerClasesConMetodos(String cuerpo) {
        Set<String> clases = new HashSet<>();
        if (cuerpo == null) {
            return clases;
        }
        for (String linea : cuerpo.split("\n")) {
            String s = linea.trim();
            if (s.endsWith(":") && (s.startsWith("metodo_") || s.startsWith("constructor_"))) {
                String nombre = s.substring(0, s.indexOf(':'));
                String[] partes = nombre.split("_", 3);
                if (partes.length >= 2) {
                    clases.add(partes[1]);
                }
            }
        }
        return clases;
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

    private boolean esCadena(EntradaLiteral entrada) {
        return entrada.getValor() != null && entrada.getValor().trim().startsWith("\"");
    }
}