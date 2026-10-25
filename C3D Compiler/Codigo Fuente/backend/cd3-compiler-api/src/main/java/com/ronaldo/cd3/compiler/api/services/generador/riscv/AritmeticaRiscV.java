package com.ronaldo.cd3.compiler.api.services.generador.riscv;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.Operando;

/**
 * 
 * @author ronaldo
 */
public class AritmeticaRiscV {

    private static final int ESCALA_DECIMAL = 1000;

    private final UnidadRiscV unidad;
    private final AccesoOperandosRiscV acceso;
    private final TiposRiscV tipos;

    public AritmeticaRiscV(UnidadRiscV unidad, AccesoOperandosRiscV acceso, TiposRiscV tipos) {
        this.unidad = unidad;
        this.acceso = acceso;
        this.tipos = tipos;
    }


    public boolean emitirAritmetica(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado) {
        if (tipos.esCadena(izquierda) || tipos.esCadena(derecha) || tipos.esCadena(resultado)) {
            return false;
        }
        boolean izqDec = tipos.esDecimal(izquierda);
        boolean derDec = tipos.esDecimal(derecha);

        if (izqDec || derDec) {
            emitirAritmeticaDecimal(operador, izquierda, derecha, resultado, izqDec, derDec);
            return true;
        }

        // Aritmetica entera
        acceso.cargarValor(izquierda, "t1");
        acceso.cargarValor(derecha, "t2");
        unidad.linea(instruccionAritmetica(operador) + " t3, t1, t2");
        acceso.guardarValor(resultado, "t3");
        return true;
    }


    private void emitirAritmeticaDecimal(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado, boolean izqDec, boolean derDec) {
        if (operador == OperadorCuarteta.MULTIPLICACION) {
            acceso.cargarValor(izquierda, "t1");
            acceso.cargarValor(derecha, "t2");
            if (izqDec && derDec) {
                emitirMultiplicacionFija();
            } else {
                unidad.linea("mul t3, t1, t2");
            }
            acceso.guardarValor(resultado, "t3");
            return;
        }
        if (operador == OperadorCuarteta.DIVISION) {
            if (izqDec && !derDec) {
                acceso.cargarValor(izquierda, "t1");
                acceso.cargarValor(derecha, "t2");
                unidad.linea("div t3, t1, t2");
            } else {
                acceso.cargarValor(izquierda, "t1");
                if (!izqDec) {
                    promover("t1");
                }
                acceso.cargarValor(derecha, "t2");
                unidad.linea("div t3, t1, t2");
                unidad.linea("rem t4, t1, t2");
                unidad.linea("li t5, " + ESCALA_DECIMAL);
                unidad.linea("mul t3, t3, t5");
                unidad.linea("mul t4, t4, t5");
                unidad.linea("div t4, t4, t2");
                unidad.linea("add t3, t3, t4");
            }
            acceso.guardarValor(resultado, "t3");
            return;
        }
        // +, -, %
        acceso.cargarValor(izquierda, "t1");
        if (!izqDec) {
            promover("t1");
        }
        acceso.cargarValor(derecha, "t2");
        if (!derDec) {
            promover("t2");
        }
        if (operador == OperadorCuarteta.SUMA) {
            unidad.linea("add t3, t1, t2");
        } else if (operador == OperadorCuarteta.RESTA) {
            unidad.linea("sub t3, t1, t2");
        } else {
            unidad.linea("rem t3, t1, t2");
        }
        acceso.guardarValor(resultado, "t3");
    }

    private void emitirMultiplicacionFija() {
        unidad.linea("li t4, " + ESCALA_DECIMAL);
        unidad.linea("div t5, t1, t4");
        unidad.linea("rem t6, t1, t4");
        unidad.linea("div t0, t2, t4");
        unidad.linea("rem t2, t2, t4");
        unidad.linea("mul t3, t5, t0");
        unidad.linea("mul t3, t3, t4");
        unidad.linea("mul t5, t5, t2");
        unidad.linea("add t3, t3, t5");
        unidad.linea("mul t0, t6, t0");
        unidad.linea("add t3, t3, t0");
        unidad.linea("mul t6, t6, t2");
        unidad.linea("div t6, t6, t4");
        unidad.linea("add t3, t3, t6");
    }


    
    public void promover(String registro) {
        unidad.linea("li t4, " + ESCALA_DECIMAL);
        unidad.linea("mul " + registro + ", " + registro + ", t4");
    }

    private String instruccionAritmetica(OperadorCuarteta operador) {
        switch (operador) {
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


    public void emitirComparacion(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado) {
        cargarComparables(izquierda, derecha);
        emitirSlt(operador);
        acceso.guardarValor(resultado, "t3");
    }

    private void emitirSlt(OperadorCuarteta operador) {
        switch (operador) {
            case MENOR_EQ_Q:
                unidad.linea("slt t3, t2, t1");
                unidad.linea("xori t3, t3, 1");
                break;
            case MAYOR_Q:
                unidad.linea("slt t3, t2, t1");
                break;
            case MAYOR_EQ_Q:
                unidad.linea("slt t3, t1, t2");
                unidad.linea("xori t3, t3, 1");
                break;
            default:
                unidad.linea("slt t3, t1, t2");
                break;
        }
    }

    public void emitirIgualdad(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado) {
        cargarComparables(izquierda, derecha);
        unidad.linea("sub t3, t1, t2");
        unidad.linea(operador == OperadorCuarteta.DISTINTO ? "snez t3, t3" : "seqz t3, t3");
        acceso.guardarValor(resultado, "t3");
    }


    private void cargarComparables(Operando izquierda, Operando derecha) {
        boolean mixto = tipos.esDecimal(izquierda) || tipos.esDecimal(derecha);
        acceso.cargarValor(izquierda, "t1");
        if (mixto && !tipos.esDecimal(izquierda) && tipos.esEntero(izquierda)) {
            promover("t1");
        }
        acceso.cargarValor(derecha, "t2");
        if (mixto && !tipos.esDecimal(derecha) && tipos.esEntero(derecha)) {
            promover("t2");
        }
    }


    public void emitirLogicaBinaria(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado) {
        acceso.cargarValor(izquierda, "t1");
        acceso.cargarValor(derecha, "t2");
        unidad.linea((operador == OperadorCuarteta.AND ? "and" : "or") + " t3, t1, t2");
        acceso.guardarValor(resultado, "t3");
    }

    public void emitirNegacion(OperadorCuarteta operador, Operando operando, Operando resultado) {
        acceso.cargarValor(operando, "t1");
        unidad.linea(operador == OperadorCuarteta.NOT ? "seqz t3, t1" : "neg t3, t1");
        acceso.guardarValor(resultado, "t3");
    }

    public void emitirIncrementoDecremento(OperadorCuarteta operador, Operando objetivo,
            Operando resultado) {
        acceso.cargarValor(objetivo, "t1");
        int paso = tipos.esDecimal(objetivo) ? ESCALA_DECIMAL : 1;
        if (operador == OperadorCuarteta.INCREMENTO) {
            unidad.linea("addi t3, t1, " + paso);
        } else {
            unidad.linea("addi t3, t1, " + (-paso));
        }
        acceso.guardarValor(objetivo, "t3");
        if (resultado != null && !resultado.getTexto().equals(objetivo.getTexto())) {
            acceso.guardarValor(resultado, "t3");
        }
    }
}