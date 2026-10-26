package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.Operador;
import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.Direccion;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.semantica.reglas.Reglas;

/**
 * 
 * @author ronaldo
 */
public class IncDecExpresion extends Expresion {

    private final Reglas reglas = new Reglas();
    private final Direccion direccion = new Direccion();
    private final Expresion objetivo;
    private final Operador operador;
    private final boolean prefijo;

    public IncDecExpresion(Expresion objetivo, Operador operador, boolean prefijo,
            int fila, int columna) {
        super(fila, columna);
        this.objetivo = objetivo;
        this.operador = operador;
        this.prefijo = prefijo;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (objetivo == null) {
            return;
        }
        objetivo.verificarSemantica(contexto);
        if (!reglas.esLvalue(objetivo)) {
            contexto.agregarError(fila, columna, null,
                    "No se puede incrementar/decrementar, debe ser una variable, "
                    + "un campo o una posicion de arreglo");
        }
        if (!reglas.esNumerico(objetivo.getTipo())) {
            contexto.agregarError(fila, columna, String.valueOf(operador),
                    "El operador '" + operador + "' solo se puede usar con valores numericos, "
                    + "pero '" + objetivo.getTipo() + "' no es numerico.");
        }
        setTipo(objetivo.getTipo());
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        String dir = direccion.lvalue(objetivo, contexto, cuartetas);
        OperadorCuarteta op = (operador == Operador.DECREMENTO)
                ? OperadorCuarteta.DECREMENTO
                : OperadorCuarteta.INCREMENTO;
        String temporal = cuartetas.nuevoTemporal();
        cuartetas.registrarTipoTemporal(temporal, objetivo.getTipo());

        if (prefijo) {
            cuartetas.agregar(op, dir, null, dir, fila, columna);
            cuartetas.agregar(OperadorCuarteta.ASIGNACION, dir, null, temporal, fila, columna);
        } else {
            cuartetas.agregar(OperadorCuarteta.ASIGNACION, dir, null, temporal, fila, columna);
            cuartetas.agregar(op, dir, null, dir, fila, columna);
        }
        return temporal;
    }
}