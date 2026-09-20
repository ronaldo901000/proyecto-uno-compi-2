package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.Operador;
import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;

/**
 *
 * @author ronaldo
 */
public class Unario extends Expresion implements Verificable {

    private final Reglas reglas = new Reglas();
    private Expresion exp;
    private Operador operador;

    public Unario(Expresion exp, Operador operador, int fila, int columna) {
        super(fila, columna);
        this.exp = exp;
        this.operador = operador;
    }

    public Expresion getExp() {
        return exp;
    }

    public Operador getOperador() {
        return operador;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (exp != null) {
            exp.verificarSemantica(contexto);
        }
        TablaTipos tablaTipos = contexto.getTablaTipos();
        Tipo tipoExp = (exp != null) ? exp.getTipo() : null;
        if (reglas.esError(tipoExp)) {
            setTipo(tablaTipos.getError());
            return;
        }
        if (operador == Operador.NOT) {
            if (reglas.esBooleano(tipoExp)) {
                setTipo(tablaTipos.getBooleano());
                return;
            }
        } else if (operador == Operador.NEGATIVO_UNARIO
                || operador == Operador.POSITIVO_UNARIO) {
            if (reglas.esNumerico(tipoExp)) {
                setTipo(tipoExp);
                return;
            }
        } else {
            contexto.agregarError(fila, columna, String.valueOf(operador),
                    "Operador unario no válido");
            setTipo(tablaTipos.getError());
            return;
        }
        contexto.agregarError(fila, columna, String.valueOf(operador),
                "Operando de tipo incompatible con el operador unario '" + operador + "'");
        setTipo(tablaTipos.getError());
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        String dirExp = (exp != null)
                ? exp.generarCuartetas(contexto, cuartetas)
                : "nulo";
        if (operador == Operador.POSITIVO_UNARIO) {
            return dirExp;
        }
        OperadorCuarteta operadorC = (operador == Operador.NOT)
                ? OperadorCuarteta.NOT
                : OperadorCuarteta.NEGATIVO_UNARIO;
        String temporal = cuartetas.nuevoTemporal();
        cuartetas.agregar(operadorC, dirExp, null, temporal, fila, columna);
        cuartetas.registrarTipoTemporal(temporal, getTipo());
        return temporal;
    }

}