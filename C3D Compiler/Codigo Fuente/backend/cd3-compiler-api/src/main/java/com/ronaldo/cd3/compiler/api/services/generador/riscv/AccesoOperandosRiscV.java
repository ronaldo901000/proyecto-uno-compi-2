package com.ronaldo.cd3.compiler.api.services.generador.riscv;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.enums.TipoOperando;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.Operando;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoAtributo;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoIndice;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoLiteral;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoParametro;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoVariable;

/**
 * 
 * @author ronaldo
 */
public class AccesoOperandosRiscV {

    private final UnidadRiscV unidad;
    private final FrameRiscV frame;
    private final TiposRiscV tipos;

    public AccesoOperandosRiscV(UnidadRiscV unidad, FrameRiscV frame, TiposRiscV tipos) {
        this.unidad = unidad;
        this.frame = frame;
        this.tipos = tipos;
    }


    public void cargarValor(Operando operando, String registro) {
        if (operando == null) {
            return;
        }
        if (operando instanceof OperandoLiteral) {
            cargarLiteral((OperandoLiteral) operando, registro);
        } else if (operando instanceof OperandoAtributo
                || operando instanceof OperandoIndice) {
            cargarDireccion(operando, "t6");
            cargarDesdeDireccion(registro, "t6", tipos.tamañoAlmacen(operando));
        } else {
            unidad.linea("lw " + registro + ", " + frame.offset(operando) + "(fp)");
        }
    }


    public void guardarValor(Operando destino, String registro) {
        if (destino == null || destino instanceof OperandoLiteral) {
            return;
        }
        if (destino instanceof OperandoAtributo
                || destino instanceof OperandoIndice) {
            cargarDireccion(destino, "t6");
            guardarEnDireccion(registro, "t6", tipos.tamañoAlmacen(destino));
            return;
        }
        unidad.linea("sw " + registro + ", " + frame.offset(destino) + "(fp)");
    }


    public void cargarDireccion(Operando operando, String registro) {
        if (operando instanceof OperandoAtributo) {
            OperandoAtributo atributo = (OperandoAtributo) operando;
            baseDeCampo(atributo.getBase(), registro);
            if (atributo.getOffset() != 0) {
                unidad.linea("addi " + registro + ", " + registro + ", " + atributo.getOffset());
            }
            return;
        }
        if (operando instanceof OperandoIndice) {
            cargarDireccionIndice((OperandoIndice) operando, registro);
            return;
        }
        if (operando instanceof OperandoLiteral) {
            OperandoLiteral literal = (OperandoLiteral) operando;
            if (literal.getEtiqueta() != null) {
                unidad.linea("la " + registro + ", " + literal.getEtiqueta());
            }
            return;
        }
        if (operando instanceof OperandoParametro && tipos.esStructPorValor(operando)) {

            cargarValor(operando, registro);
            return;
        }
        unidad.linea("addi " + registro + ", fp, " + frame.offset(operando));
    }

    private void cargarDireccionIndice(OperandoIndice indice, String registro) {
        Operando base = indice.getBase();
        if (base instanceof OperandoIndice) {
            cargarPunteroDeFila((OperandoIndice) base, registro);
        } else {
            cargarValor(base, registro);
        }
        unidad.linea("addi sp, sp, -4");
        unidad.linea("sw " + registro + ", 0(sp)");
        cargarValor(indice.getIndice(), "t2");
        unidad.linea("lw " + registro + ", 0(sp)");
        unidad.linea("addi sp, sp, 4");
        unidad.linea("li t4, " + tipos.tamañoElementoIndice(indice));
        unidad.linea("mul t2, t2, t4");
        unidad.linea("add " + registro + ", " + registro + ", t2");
    }


    public void cargarPunteroDeFila(OperandoIndice fila, String registro) {
        Operando base = fila.getBase();
        if (base instanceof OperandoIndice) {
            cargarPunteroDeFila((OperandoIndice) base, registro);
        } else {
            cargarValor(base, registro);
        }
        unidad.linea("addi sp, sp, -4");
        unidad.linea("sw " + registro + ", 0(sp)");
        cargarValor(fila.getIndice(), "t2");
        unidad.linea("lw " + registro + ", 0(sp)");
        unidad.linea("addi sp, sp, 4");
        unidad.linea("slli t2, t2, 2");
        unidad.linea("add " + registro + ", " + registro + ", t2");
        unidad.linea("lw " + registro + ", 0(" + registro + ")");
    }


    private void baseDeCampo(Operando base, String registro) {
        if (base == null) {
            return;
        }
        if (base instanceof OperandoVariable && tipos.esStructPorValor(base)) {
            unidad.linea("addi " + registro + ", fp, " + frame.offset(base));
        } else {
            cargarValor(base, registro);
        }
    }


    private void cargarLiteral(OperandoLiteral literal, String registro) {
        if (literal.getEtiqueta() != null) {
            // Cadena (.asciz): solo la direccion. Numerico (.word): su valor.
            unidad.linea("la " + registro + ", " + literal.getEtiqueta());
            boolean esCadena = literal.getTipo() != null
                    && literal.getTipo().getTipoDato() == TipoDato.CADENA;
            if (!esCadena) {
                unidad.linea("lw " + registro + ", 0(" + registro + ")");
            }
            return;
        }
        unidad.linea("li " + registro + ", " + valorDeLiteral(literal));
    }


    public int valorDeLiteral(OperandoLiteral literal) {
        String texto = literal.getTexto();
        TipoOperando categoria = literal.getCategoria();
        if (categoria == TipoOperando.BOOLEANO_VERDADERO) {
            return 1;
        }
        if (categoria == TipoOperando.BOOLEANO_FALSO || categoria == TipoOperando.NULO) {
            return 0;
        }
        if (categoria == TipoOperando.CONSTANTE_CARACTER) {
            return valorDeCaracter(texto);
        }
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException ex) {
            try {
                double d = Double.parseDouble(texto.trim());
                if (literal.getTipo() != null
                        && literal.getTipo().getTipoDato() == TipoDato.DECIMAL) {
                    return (int) Math.round(d * 1000.0);
                }
                return (int) d;
            } catch (NumberFormatException ex2) {
                return 0;
            }
        }
    }

    private int valorDeCaracter(String texto) {
        String limpio = texto.replace("'", "");
        if (limpio.isEmpty()) {
            return 0;
        }
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
                    return limpio.charAt(1);
            }
        }
        return limpio.charAt(0);
    }


    public void cargarDesdeDireccion(String registro, String direccion, int tamaño) {
        unidad.linea(((tamaño <= 1) ? "lb" : "lw") + " " + registro + ", 0(" + direccion + ")");
    }

    public void guardarEnDireccion(String registro, String direccion, int tamaño) {
        unidad.linea(((tamaño <= 1) ? "sb" : "sw") + " " + registro + ", 0(" + direccion + ")");
    }
}