package com.ronaldo.cd3.compiler.api.modelos.instruccion.elegir;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.nodo.Nodo;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class InstElegir extends Nodo implements Instruccion {

    private Expresion valorEvaluado;
    private List<CasoSwitch> casos;

    public InstElegir(Expresion valorEvaluado, List<CasoSwitch> casos, int fila, int columna) {
        super(fila, columna);
        this.valorEvaluado = valorEvaluado;
        this.casos = casos;
    }

    public Expresion getValorEvaluado() {
        return valorEvaluado;
    }

    public void setValorEvaluado(Expresion valorEvaluado) {
        this.valorEvaluado = valorEvaluado;
    }

    public List<CasoSwitch> getCasos() {
        return casos;
    }

    public void setCasos(List<CasoSwitch> casos) {
        this.casos = casos;
    }

    @Override
    public void verificarSemantica(Contexto contexto) {
        if (valorEvaluado != null) {
            valorEvaluado.verificarSemantica(contexto);
        }
        Tipo tipoEvaluado = (valorEvaluado != null) ? valorEvaluado.getTipo() : null;
        
        contexto.setdentroDeSwitch(true);
        if (casos != null) {
            for (CasoSwitch caso : casos) {
                caso.verificarSemantica(contexto, tipoEvaluado);
            }
        }
        contexto.setdentroDeSwitch(false);
    }

    /**
     * 
     * @param contexto
     * @param cuartetas
     * @return 
     */
    @Override
    public String generarCuartetas(Contexto contexto, ListaCuartetas cuartetas) {
        
        String etiquetaSalida = cuartetas.nuevaEtiqueta();
        
        contexto.entrarNivelGeneracionCiclo(etiquetaSalida, etiquetaSalida);

        String dirValor = (valorEvaluado != null)
                ? valorEvaluado.generarCuartetas(contexto, cuartetas)
                : null;

        String etiquetaDefecto = null;
        
        List<String> etiquetasCuerpo = new ArrayList<>();
        
        if (casos != null) {
            
            for (CasoSwitch caso : casos) {
                if (caso.getValor() != null) {
                    
                    String dirCaso = caso.getValor()
                            .generarCuartetas(contexto, cuartetas);
                    
                    String temporalIgual = cuartetas.nuevoTemporal();
                    
                    cuartetas.agregar(OperadorCuarteta.IGUAL, dirValor,
                            dirCaso, temporalIgual, fila, columna);
                    
                    String etiquetaCuerpo = cuartetas.nuevaEtiqueta();
                    
                    cuartetas.agregar(OperadorCuarteta.IF_VERDADERO,
                            temporalIgual, etiquetaCuerpo, null, fila, columna);
                    
                    etiquetasCuerpo.add(etiquetaCuerpo);
                    
                } else {
                    
                    etiquetaDefecto = cuartetas.nuevaEtiqueta();
                    etiquetasCuerpo.add(etiquetaDefecto);
                    
                }
            }
            
        }
        cuartetas.agregar(OperadorCuarteta.GOTO,
                (etiquetaDefecto != null) ? etiquetaDefecto : etiquetaSalida,
                null, null, fila, columna);

        if (casos != null) {
            for (int i = 0; i < casos.size(); i++) {
                CasoSwitch caso = casos.get(i);
                cuartetas.agregarEtiqueta(etiquetasCuerpo.get(i), fila, columna);
                if (caso.getIntruccionesInternas() != null) {
                    for (Instruccion instruccion : caso.getIntruccionesInternas()) {
                        instruccion.generarCuartetas(contexto, cuartetas);
                    }
                }
            }
        }
        cuartetas.agregarEtiqueta(etiquetaSalida, fila, columna);

        contexto.salirNivelGeneracionCiclo();
        return null;
    }
    

}