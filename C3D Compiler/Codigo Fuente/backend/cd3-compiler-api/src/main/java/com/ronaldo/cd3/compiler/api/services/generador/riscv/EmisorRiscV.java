package com.ronaldo.cd3.compiler.api.services.generador.riscv;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.ir.AmbienteIR;
import com.ronaldo.cd3.compiler.api.modelos.ir.EmisorIR;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.Operando;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoAtributo;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoIndice;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoLiteral;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoThis;
import com.ronaldo.cd3.compiler.api.modelos.ir.runtime.RuntimeZ;
import com.ronaldo.cd3.compiler.api.modelos.tabla.simbolos.SimboloParametro;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoStructura;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Emisor de ensamblador RISC-V (RV32) para el IR de Z. Traduce el modelo de
 * cuartetas a un texto .s usando una maquina de pila: cada operando nombrado
 * vive en una ranura del frame y cada operacion materializa su resultado en
 * una ranura temporal. Registros de 32 bits, punteros de 4 bytes, alineacion
 * a 4 y aritmetica entera de punto fijo (sin instrucciones flotantes) para
 * ser compatible con Venus.
 */
public class EmisorRiscV implements EmisorIR {

    private final int TAMAÑO_SLOT = 4;
    private final int TAMAÑO_ENCABEZADO_FRAME = 8;
    private final int TAMAÑO_BUFFER_CADENA = 256;

    private final AmbienteIR ambiente;
    private final ListaCuartetas cuartetas;

    private final StringBuilder cuerpo = new StringBuilder();
    private final StringBuilder unidad = new StringBuilder();

    private String unidadEnCurso;
    private boolean metodoEnCurso;
    private final Map<String, Integer> ranuras = new LinkedHashMap<>();
    private final Map<String, Integer> tamañosRanura = new LinkedHashMap<>();
    private final List<String> parametros = new ArrayList<>();
    private int siguienteOffset;

    private final List<Operando> parametrosPendientes = new ArrayList<>();

    public EmisorRiscV(AmbienteIR ambiente) {
        this.ambiente = ambiente;
        this.cuartetas = ambiente.getCuartetas();
    }

    public String getCuerpo() {
        return cuerpo.toString();
    }

    public void finalizar() {
        cerrarUnidad();
    }

    // ------------------------------------------------------------------
    // Estructura de unidades
    // ------------------------------------------------------------------

    private void abrirUnidad(String nombre) {
        unidadEnCurso = nombre;
        unidad.setLength(0);
        ranuras.clear();
        tamañosRanura.clear();
        siguienteOffset = 0;
        parametros.clear();
        parametrosPendientes.clear();
        metodoEnCurso = nombre != null
                && (nombre.startsWith("metodo_") || nombre.startsWith("constructor_"));
        if (metodoEnCurso) {
            asignarRanura("=this");
        }
        List<String> nombres = new ArrayList<>();
        if (cuartetas != null && nombre != null) {
            List<SimboloParametro> lista
                    = cuartetas.parametrosDeFuncion(nombre);
            if (lista != null) {
                for (SimboloParametro p : lista) {
                    nombres.add(p.getId());
                    asignarRanura(claveParametro(p.getId()));
                }
            }
        }
        parametros.addAll(nombres);
    }

    private void cerrarUnidad() {
        if (unidadEnCurso == null) {
            return;
        }
        int frame = alinear4(TAMAÑO_ENCABEZADO_FRAME + siguienteOffset);
        cuerpo.append(unidadEnCurso).append(":\n");
        cuerpo.append("    addi sp, sp, -").append(frame).append("\n");
        cuerpo.append("    sw ra, ").append(frame - 4).append("(sp)\n");
        cuerpo.append("    sw fp, ").append(frame - 8).append("(sp)\n");
        cuerpo.append("    addi fp, sp, ").append(frame).append("\n");
        if (metodoEnCurso) {
            cuerpo.append("    sw a0, ").append(offsetDeClave("=this")).append("(fp)\n");
        }
        for (int i = 0; i < parametros.size(); i++) {
            int registro = i + (metodoEnCurso ? 1 : 0);
            cuerpo.append("    sw a").append(registro).append(", ")
                    .append(offsetDeClave(claveParametro(parametros.get(i)))).append("(fp)\n");
        }
        cuerpo.append(unidad);
        cuerpo.append("__epilogo_").append(unidadEnCurso).append(":\n");
        if (unidadEnCurso.startsWith("constructor_")) {
            cuerpo.append("    lw a0, ").append(offsetDeClave("=this")).append("(fp)\n");
        }
        cuerpo.append("    lw t0, -8(fp)\n");
        cuerpo.append("    lw ra, -4(fp)\n");
        cuerpo.append("    addi sp, sp, ").append(frame).append("\n");
        cuerpo.append("    mv fp, t0\n");
        if (unidadEnCurso.startsWith("__entrada_")) {
            cuerpo.append("    j z_exit\n\n");
        } else {
            cuerpo.append("    ret\n\n");
        }
        unidadEnCurso = null;
    }

    private void asegurarUnidad() {
        if (unidadEnCurso == null) {
            abrirUnidad("__global");
        }
    }

    // ------------------------------------------------------------------
    // Manejo de ranuras
    // ------------------------------------------------------------------

    private int alinear4(int valor) {
        return (valor + 3) & ~3;
    }

    private String claveParametro(String nombre) {
        return "OperandoParametro:" + nombre;
    }

    private String clave(Operando operando) {
        if (operando instanceof OperandoThis) {
            return "=this";
        }
        return operando.getClass().getSimpleName() + ":" + operando.getTexto();
    }

    private int asignarRanura(String clave) {
        Integer existente = ranuras.get(clave);
        if (existente != null) {
            return existente;
        }
        siguienteOffset += TAMAÑO_SLOT;
        ranuras.put(clave, siguienteOffset);
        tamañosRanura.put(clave, TAMAÑO_SLOT);
        return siguienteOffset;
    }

    private int asignarRanura(String clave, int tamaño) {
        Integer existente = ranuras.get(clave);
        if (existente != null) {
            return existente;
        }
        int reservado = (tamaño > TAMAÑO_SLOT) ? alinear4(tamaño) : TAMAÑO_SLOT;
        siguienteOffset += reservado;
        ranuras.put(clave, siguienteOffset);
        tamañosRanura.put(clave, reservado);
        return siguienteOffset;
    }

    private String offsetDeClave(String clave) {
        int fin = asignarRanura(clave);
        int reservado = tamañosRanura.getOrDefault(clave, TAMAÑO_SLOT);
        return String.valueOf(-TAMAÑO_ENCABEZADO_FRAME - fin);
    }

    private String offset(Operando operando) {
        String clave = clave(operando);
        int fin = asignarRanura(clave, tamañoDeOperando(operando));
        return String.valueOf(-TAMAÑO_ENCABEZADO_FRAME - fin);
    }

    private int tamañoDeOperando(Operando operando) {
        Tipo tipo = (operando != null) ? operando.getTipo() : null;
        if (tipo instanceof TipoStructura && !((TipoStructura) tipo).esPorReferencia()) {
            return Math.max(TAMAÑO_SLOT, ((TipoStructura) tipo).getTamañoHeap());
        }
        return TAMAÑO_SLOT;
    }

    // ------------------------------------------------------------------
    // Operaciones del contrato EmisorIR
    // ------------------------------------------------------------------

    @Override
    public AmbienteIR getAmbiente() {
        return ambiente;
    }

    @Override
    public void emitirAsignacion(Operando destino, Operando origen) {
        asegurarUnidad();
        if (esStructPorValor(destino) && esStructPorValor(origen)) {
            int tamaño = ((TipoStructura) destino.getTipo()).getTamañoHeap();
            int palabras = min(tamaño / 4 + ((tamaño % 4 != 0) ? 1 : 0), 4);
            for (int i = 0; i < palabras; i++) {
                linea("lw t1, " + offsetPalabra(origen, i) + "(fp)");
                linea("sw t1, " + offsetPalabra(destino, i) + "(fp)");
            }
            return;
        }
        cargarValor(origen, "t1");
        guardarValor(destino, "t1");
    }

    @Override
    public void emitirAritmetica(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado) {
        asegurarUnidad();
        cargarValor(izquierda, "t1");
        cargarValor(derecha, "t2");
        linea(instruccionAritmetica(operador) + " t3, t1, t2");
        guardarValor(resultado, "t3");
    }

    @Override
    public void emitirComparacion(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado) {
        asegurarUnidad();
        cargarValor(izquierda, "t1");
        cargarValor(derecha, "t2");
        switch (operador) {
            case MENOR_Q:
                linea("slt t3, t1, t2");
                break;
            case MENOR_EQ_Q:
                linea("slt t3, t2, t1");
                linea("xori t3, t3, 1");
                break;
            case MAYOR_Q:
                linea("slt t3, t2, t1");
                break;
            case MAYOR_EQ_Q:
                linea("slt t3, t1, t2");
                linea("xori t3, t3, 1");
                break;
            default:
                linea("slt t3, t1, t2");
                break;
        }
        guardarValor(resultado, "t3");
    }

    @Override
    public void emitirIgualdad(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado) {
        asegurarUnidad();
        cargarValor(izquierda, "t1");
        cargarValor(derecha, "t2");
        linea("sub t3, t1, t2");
        if (operador == OperadorCuarteta.DISTINTO) {
            linea("snez t3, t3");
        } else {
            linea("seqz t3, t3");
        }
        guardarValor(resultado, "t3");
    }

    @Override
    public void emitirLogicaBinaria(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado) {
        asegurarUnidad();
        cargarValor(izquierda, "t1");
        cargarValor(derecha, "t2");
        linea((operador == OperadorCuarteta.AND ? "and" : "or") + " t3, t1, t2");
        guardarValor(resultado, "t3");
    }

    @Override
    public void emitirNegacion(OperadorCuarteta operador, Operando operando,
            Operando resultado) {
        asegurarUnidad();
        cargarValor(operando, "t1");
        if (operador == OperadorCuarteta.NOT) {
            linea("seqz t3, t1");
        } else {
            linea("neg t3, t1");
        }
        guardarValor(resultado, "t3");
    }

    @Override
    public void emitirIncrementoDecremento(OperadorCuarteta operador, Operando objetivo,
            Operando resultado) {
        asegurarUnidad();
        cargarValor(objetivo, "t1");
        linea((operador == OperadorCuarteta.INCREMENTO ? "addi t3, t1, 1" : "addi t3, t1, -1"));
        guardarValor(objetivo, "t3");
        if (resultado != null) {
            guardarValor(resultado, "t3");
        }
    }

    @Override
    public void emitirEtiqueta(Operando etiqueta) {
        String texto = etiqueta.getTexto();
        String unidadActual = ambiente.getUnidadActual();
        if (unidadActual != null && unidadActual.equals(texto)
                && !unidadActual.equals(unidadEnCurso)) {
            cerrarUnidad();
            abrirUnidad(unidadActual);
        } else {
            asegurarUnidad();
            unidad.append(texto).append(":\n");
        }
    }

    @Override
    public void emitirSalto(Operando etiqueta) {
        asegurarUnidad();
        linea("j " + etiqueta.getTexto());
    }

    @Override
    public void emitirCondicional(OperadorCuarteta operador, Operando condicion,
            Operando etiqueta) {
        asegurarUnidad();
        cargarValor(condicion, "t1");
        linea("bnez t1, " + etiqueta.getTexto());
    }

    @Override
    public void emitirImprimir(Operando valor, boolean conSaltoLinea) {
        asegurarUnidad();
        if (valor != null) {
            String rutina = RuntimeZ.rutinaImpresion(valor.getTipo());
            if (rutina != null) {
                cargarValor(valor, "a0");
                linea("call " + rutina);
            }
        }
        if (conSaltoLinea) {
            linea("call " + RuntimeZ.imprimirSaltoDeLinea());
        }
    }

    @Override
    public void emitirLeer(Operando destino) {
        asegurarUnidad();
        if (destino == null || destino.getTipo() == null) {
            return;
        }
        if (destino.getTipo().getTipoDato() == TipoDato.CADENA) {
            // z_alloc reserva el buffer y lo deja en a0; z_leer_cadena de Venus
            // espera el buffer en a1 y el maximo en a2.
            linea("li a0, " + TAMAÑO_BUFFER_CADENA);
            linea("call z_alloc");
            linea("mv a1, a0");
            linea("li a2, " + TAMAÑO_BUFFER_CADENA);
            linea("call z_leer_cadena");
            linea("mv a0, a1");
            guardarValor(destino, "a0");
            return;
        }
        String rutina = RuntimeZ.rutinaLectura(destino.getTipo());
        if (rutina == null) {
            return;
        }
        linea("call " + rutina);
        guardarValor(destino, "a0");
    }

    @Override
    public void emitirPunteroInicio(Operando destino, Operando cantidad) {
        asegurarUnidad();
        int bytes = bytesDeObjeto(destino, cantidad);
        // sbrk de Venus: a0 = 9, a1 = bytes. Retorna puntero en a0.
        linea("li a1, " + bytes);
        linea("call z_alloc");
        guardarValor(destino, "a0");
    }

    @Override
    public void emitirAccesoIndice(Operando base, Operando indice, Operando destino) {
        asegurarUnidad();
        cargarValor(indice, "t2");
        cargarValor(base, "t1");
        int tamaño = tamañoElemento(destino);
        linea("li t3, " + tamaño);
        linea("mul t2, t2, t3");
        linea("add t1, t1, t2");
        if (destino instanceof OperandoIndice) {
            return;
        }
        cargarDesdeDireccion("t3", "t1", tamañoAlmacen(destino));
        guardarValor(destino, "t3");
    }

    @Override
    public void emitirParametro(Operando operando) {
        parametrosPendientes.add(operando);
    }

    @Override
    public void emitirLlamada(Operando funcion, Operando resultado) {
        asegurarUnidad();
        int total = parametrosPendientes.size();
        int registros = Math.min(total, 8);
        int extra = total - registros;
        if (extra > 0) {
            linea("addi sp, sp, -" + (extra * TAMAÑO_SLOT));
        }
        for (int i = 0; i < total; i++) {
            Operando argumento = parametrosPendientes.get(i);
            if (i < registros) {
                cargarValor(argumento, "a" + i);
            } else {
                cargarValor(argumento, "t1");
                linea("sw t1, " + ((i - registros) * TAMAÑO_SLOT) + "(sp)");
            }
        }
        if (funcion != null && funcion.getTexto() != null) {
            linea("call " + funcion.getTexto());
        }
        if (extra > 0) {
            linea("addi sp, sp, " + (extra * TAMAÑO_SLOT));
        }
        guardarResultado(resultado);
        parametrosPendientes.clear();
    }

    private void guardarResultado(Operando resultado) {
        if (resultado == null) {
            return;
        }
        if (esStructPorValor(resultado)) {
            int tamaño = ((TipoStructura) resultado.getTipo()).getTamañoHeap();
            int palabras = min((tamaño + 3) / 4, 4);
            for (int i = 0; i < palabras; i++) {
                linea("lw t1, " + offsetPalabra(resultado, i) + "(fp)");
                linea("mv a" + i + ", t1");
            }
            return;
        }
        guardarValor(resultado, "a0");
    }

    @Override
    public void emitirLlamadaVirtual(Operando receptor, int slot, Operando resultado) {
        asegurarUnidad();
        int total = parametrosPendientes.size();
        int registros = Math.min(total, 8);
        int extra = total - registros;
        if (extra > 0) {
            linea("addi sp, sp, -" + (extra * TAMAÑO_SLOT));
        }
        for (int i = 0; i < total; i++) {
            Operando argumento = parametrosPendientes.get(i);
            if (i < registros) {
                cargarValor(argumento, "a" + i);
            } else {
                cargarValor(argumento, "t1");
                linea("sw t1, " + ((i - registros) * TAMAÑO_SLOT) + "(sp)");
            }
        }
        if (total > 0) {
            linea("mv t0, a0");
        } else if (receptor != null) {
            cargarValor(receptor, "t0");
        }
        linea("lw t0, 0(t0)");
        linea("lw t0, " + (slot * TAMAÑO_SLOT) + "(t0)");
        linea("jalr t0");
        if (extra > 0) {
            linea("addi sp, sp, " + (extra * TAMAÑO_SLOT));
        }
        guardarResultado(resultado);
        parametrosPendientes.clear();
    }

    @Override
    public void emitirFijarVtable(Operando objeto, String nombreClase) {
        asegurarUnidad();
        if (objeto == null || nombreClase == null) {
            return;
        }
        String offset = offset(objeto);
        linea("lw t6, " + offset + "(fp)");
        linea("la t1, __vtable_" + nombreClase);
        linea("sw t1, 0(t6)");
    }

    @Override
    public void emitirRetorno(Operando valor) {
        asegurarUnidad();
        if (valor != null) {
            cargarValor(valor, "a0");
        }
        if (unidadEnCurso != null) {
            if (unidadEnCurso.startsWith("__entrada_")) {
                linea("j z_exit");
            } else {
                linea("j __epilogo_" + unidadEnCurso);
            }
        }
    }

    private boolean esStructPorValor(Operando operando) {
        Tipo tipo = (operando != null) ? operando.getTipo() : null;
        return tipo instanceof TipoStructura && !((TipoStructura) tipo).esPorReferencia();
    }

    private int min(int a, int b) {
        return (a < b) ? a : b;
    }

    private String offsetPalabra(Operando operando, int palabra) {
        int offset = Integer.parseInt(offset(operando));
        return String.valueOf(offset + palabra * TAMAÑO_SLOT);
    }

    // ------------------------------------------------------------------
    // Carga y almacenamiento de operandos
    // ------------------------------------------------------------------

    private void cargarValor(Operando operando, String registro) {
        if (operando == null) {
            return;
        }
        if (operando instanceof OperandoLiteral) {
            cargarLiteral((OperandoLiteral) operando, registro);
        } else if (operando instanceof OperandoAtributo
                || operando instanceof OperandoIndice) {
            cargarDireccion(operando, "t6");
            cargarDesdeDireccion(registro, "t6", tamañoAlmacen(operando));
        } else {
            linea("lw " + registro + ", " + offset(operando) + "(fp)");
        }
    }

    private void guardarValor(Operando destino, String registro) {
        if (destino == null) {
            return;
        }
        if (destino instanceof OperandoLiteral) {
            return;
        }
        if (destino instanceof OperandoAtributo
                || destino instanceof OperandoIndice) {
            cargarDireccion(destino, "t6");
            guardarEnDireccion(registro, "t6", tamañoAlmacen(destino));
            return;
        }
        linea("sw " + registro + ", " + offset(destino) + "(fp)");
    }

    private void cargarDireccion(Operando operando, String registro) {
        if (operando instanceof OperandoAtributo) {
            OperandoAtributo atributo = (OperandoAtributo) operando;
            baseDeCampo(atributo.getBase(), registro);
            if (atributo.getOffset() != 0) {
                linea("addi " + registro + ", " + registro + ", " + atributo.getOffset());
            }
            return;
        }
        if (operando instanceof OperandoIndice) {
            OperandoIndice indice = (OperandoIndice) operando;
            cargarValor(indice.getIndice(), "t2");
            cargarValor(indice.getBase(), registro);
            int tamaño = tamañoElementoIndice(indice);
            linea("li t4, " + tamaño);
            linea("mul t2, t2, t4");
            linea("add " + registro + ", " + registro + ", t2");
            return;
        }
        if (operando instanceof OperandoLiteral) {
            OperandoLiteral literal = (OperandoLiteral) operando;
            if (literal.getEtiqueta() != null) {
                linea("la " + registro + ", " + literal.getEtiqueta());
            }
            return;
        }
        linea("addi " + registro + ", fp, " + offset(operando));
    }

    private void baseDeCampo(Operando base, String registro) {
        if (base == null) {
            return;
        }
        if (esPorValor(base)) {
            linea("addi " + registro + ", fp, " + offset(base));
        } else {
            cargarValor(base, registro);
        }
    }

    private boolean esPorValor(Operando operando) {
        Tipo tipo = (operando != null) ? operando.getTipo() : null;
        if (tipo instanceof TipoStructura) {
            return !((TipoStructura) tipo).esPorReferencia();
        }
        return false;
    }

    private void cargarLiteral(OperandoLiteral literal, String registro) {
        if (literal.getEtiqueta() != null) {
            linea("la " + registro + ", " + literal.getEtiqueta());
            linea("lw " + registro + ", 0(" + registro + ")");
            return;
        }
        linea("li " + registro + ", " + valorDeLiteral(literal));
    }

    // ------------------------------------------------------------------
    // Utilidades
    // ------------------------------------------------------------------

    private void linea(String instruccion) {
        unidad.append("    ").append(instruccion).append("\n");
    }

    private void cargarDesdeDireccion(String registro, String direccion, int tamaño) {
        linea(cargaInstruccion(tamaño) + " " + registro + ", 0(" + direccion + ")");
    }

    private void guardarEnDireccion(String registro, String direccion, int tamaño) {
        linea(almacenInstruccion(tamaño) + " " + registro + ", 0(" + direccion + ")");
    }

    private String cargaInstruccion(int tamaño) {
        if (tamaño <= 1) {
            return "lb";
        }
        return "lw";
    }

    private String almacenInstruccion(int tamaño) {
        if (tamaño <= 1) {
            return "sb";
        }
        return "sw";
    }

    private String instruccionAritmetica(OperadorCuarteta operador) {
        switch (operador) {
            case SUMA:
                return "add";
            case RESTA:
                return "sub";
            case MULTIPLICACION:
                return "mul";
            case DIVISION:
                return "div";
            case MODULO:
                return "rem";
            default:
                return "add";
        }
    }

    private boolean esDecimal(Operando operando) {
        return operando != null && operando.getTipo() != null
                && operando.getTipo().getTipoDato() == TipoDato.DECIMAL;
    }

    private boolean esEntero(Operando operando) {
        if (operando == null || operando.getTipo() == null) {
            return false;
        }
        TipoDato dato = operando.getTipo().getTipoDato();
        return dato == TipoDato.ENTERO || dato == TipoDato.CHAR || dato == TipoDato.BOOLEAN;
    }

    private int tamañoElemento(Operando destino) {
        if (destino instanceof OperandoIndice) {
            return tamañoElementoIndice((OperandoIndice) destino);
        }
        return tamañoAlmacen(destino);
    }

    private int tamañoElementoIndice(OperandoIndice indice) {
        int tamaño = indice.getTamañoElemento();
        if (tamaño > 0) {
            return tamaño;
        }
        Operando base = indice.getBase();
        if (base != null && base.getTipo() instanceof TipoArreglo) {
            Tipo elemento = ((TipoArreglo) base.getTipo()).getTipoBase();
            if (elemento != null) {
                if (elemento.esPorReferencia()) {
                    return 4;
                }
                int bytes = elemento.tamañoBytes();
                return (bytes <= 4) ? 4 : 4;
            }
        }
        return tamañoAlmacen(indice);
    }

    private int tamañoAlmacen(Operando operando) {
        if (operando == null || operando.getTipo() == null) {
            return 4;
        }
        if (operando.getTipo().esPorReferencia()) {
            return 4;
        }
        int tamaño = operando.getTipo().tamañoBytes();
        if (tamaño <= 1) {
            return 1;
        }
        if (tamaño <= 4) {
            return 4;
        }
        return 4;
    }

    private int bytesDeObjeto(Operando destino, Operando cantidad) {
        if (destino == null || destino.getTipo() == null) {
            return 4;
        }
        if (destino.getTipo() instanceof TipoArreglo) {
            TipoArreglo arreglo = (TipoArreglo) destino.getTipo();
            Tipo base = arreglo.getTipoBase();
            int tamañoBase = (base instanceof TipoStructura || base instanceof TipoArreglo)
                    ? 4
                    : base.tamañoBytes();
            int cuenta = arreglo.getTotalElementos();
            if (cantidad != null && cantidad.getTipo() != null
                    && cantidad.getTipo().getTipoDato() == TipoDato.ENTERO
                    && cantidad instanceof OperandoLiteral) {
                cuenta = (int) valorDeLiteral((OperandoLiteral) cantidad);
            }
            return Math.max(1, cuenta) * tamañoBase;
        }
        if (destino.getTipo() instanceof TipoStructura) {
            return ((TipoStructura) destino.getTipo()).getTamañoHeap();
        }
        return 4;
    }

    private int valorDeLiteral(OperandoLiteral literal) {
        String texto = literal.getTexto();
        TipoOperando categoria = literal.getCategoria();
        if (categoria == TipoOperando.BOOLEANO_VERDADERO) {
            return 1;
        }
        if (categoria == TipoOperando.BOOLEANO_FALSO) {
            return 0;
        }
        if (categoria == TipoOperando.NULO) {
            return 0;
        }
        if (categoria == TipoOperando.CONSTANTE_CARACTER) {
            String limpio = texto.replace("'", "");
            if (limpio.startsWith("\\")) {
                switch (limpio) {
                    case "\\n":
                        return '\n';
                    case "\\t":
                        return '\t';
                    case "\\r":
                        return '\r';
                    case "\\0":
                        return 0;
                    default:
                        return limpio.isEmpty() ? 0 : limpio.charAt(1);
                }
            }
            return limpio.isEmpty() ? 0 : limpio.charAt(0);
        }
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException ex) {
            try {
                return (int) Double.parseDouble(texto.trim());
            } catch (NumberFormatException ex2) {
                return 0;
            }
        }
    }
}
