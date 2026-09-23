package com.ronaldo.cd3.compiler.api.modelos.expresion;

import com.ronaldo.cd3.compiler.api.enums.TipoDato;
import com.ronaldo.cd3.compiler.api.interfaces.Verificable;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.semantica.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TipoArreglo;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ExpIndice extends Expresion implements Verificable {

    private final Reglas reglas = new Reglas();
    private Expresion arreglo;
    private Expresion indice;

    public ExpIndice(Expresion arreglo, Expresion indice, int fila, int columna) {
        super(fila, columna);
        this.arreglo = arreglo;
        this.indice = indice;
    }

    public Expresion getArreglo() {
        return arreglo;
    }

    public Expresion getIndice() {
        return indice;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        
        if (arreglo != null) {
            arreglo.verificarSemantica(contexto);
        }
        
        if (indice != null) {
            indice.verificarSemantica(contexto);
        }
        
        Tipo tipoArreglo = (arreglo != null) ? arreglo.getTipo() : null;
        
        if (reglas.esError(tipoArreglo)) {
            setTipo(contexto.getTablaTipos().getError());
            return;
        }
        
        if (indice != null && (indice.getTipo() == null
                || indice.getTipo().getTipoDato() != TipoDato.ENTERO)) {
            
            contexto.agregarError(indice.getFila(), indice.getColumna(),
                    indice.getResultado() != null ? indice.getResultado().toString() : null,
                    "El indice de un arreglo debe ser un valor entero");
            setTipo(contexto.getTablaTipos().getError());
            return;
        }
        
        if (!(tipoArreglo instanceof TipoArreglo)) {
            contexto.agregarError(fila, columna, null,
                    "Se esta indexando un valor que no es un arreglo");
            setTipo(contexto.getTablaTipos().getError());
            return;
        }
        
        TipoArreglo arregloTipado = (TipoArreglo) tipoArreglo;
        verificarLimites(contexto, arregloTipado);
        setTipo(tipoDeIndice(contexto, arregloTipado));
    }

    private void verificarLimites(Contexto contexto, TipoArreglo arregloTipado) {
        if (indice == null || arregloTipado.getNumeroDimensiones() == 0) {
            return;
        }
        Integer constante = reglas.constanteEntera(indice);
        if (constante == null) {
            return;
        }
        int tamaño = arregloTipado.getDimensiones().get(0);
        if (tamaño <= 0) {
            return;
        }
        if (constante < 0 || constante >= tamaño) {
            contexto.agregarError(indice.getFila(), indice.getColumna(),
                    String.valueOf(constante),
                    "Indice " + constante + " fuera de los limites del arreglo "
                    + "(se esperaba un valor entre 0 y " + (tamaño - 1) + ")");
        }
    }

    private Tipo tipoDeIndice(Contexto contexto, TipoArreglo arregloTipado) {
        
        if (arregloTipado.getNumeroDimensiones() <= 1) {
            return arregloTipado.getTipoBase();
        }
        
        List<Integer> restantes = arregloTipado.getDimensiones()
                .subList(1, arregloTipado.getNumeroDimensiones());
        
        return contexto.getTablaTipos().getArreglo(arregloTipado.getTipoBase(), restantes);
    }

    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        String dirArreglo = (arreglo != null)
                ? arreglo.generarCuartetas(contexto, cuartetas)
                : null;
        
        String dirIndice = (indice != null)
                ? indice.generarCuartetas(contexto, cuartetas)
                : null;
        
        String temporal = cuartetas.nuevoTemporal();
        
        cuartetas.agregar(OperadorCuarteta.ACCESO_INDICE, dirArreglo,
                dirIndice, temporal, fila, columna);
        
        cuartetas.registrarTipoTemporal(temporal, getTipo());
        
        return temporal;
    }

}