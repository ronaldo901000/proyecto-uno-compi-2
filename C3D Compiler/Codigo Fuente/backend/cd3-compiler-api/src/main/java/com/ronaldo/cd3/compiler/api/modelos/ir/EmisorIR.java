package com.ronaldo.cd3.compiler.api.modelos.ir;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.Operando;

public interface EmisorIR {

    AmbienteIR getAmbiente();

    void emitirAsignacion(Operando destino, Operando origen);

    void emitirAritmetica(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado);

    void emitirComparacion(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado);

    void emitirIgualdad(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado);

    void emitirLogicaBinaria(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado);

    void emitirNegacion(OperadorCuarteta operador, Operando operando,
            Operando resultado);

    void emitirIncrementoDecremento(OperadorCuarteta operador, Operando objetivo,
            Operando resultado);

    void emitirEtiqueta(Operando etiqueta);

    void emitirSalto(Operando etiqueta);

    void emitirCondicional(OperadorCuarteta operador, Operando condicion,
            Operando etiqueta);

    void emitirImprimir(Operando valor, boolean conSaltoLinea);

    void emitirLeer(Operando destino);

    void emitirPunteroInicio(Operando destino, Operando cantidad);

    void emitirAccesoIndice(Operando base, Operando indice, Operando destino);

    void emitirParametro(Operando argumento);

    void emitirLlamada(Operando funcion, Operando resultado);

    void emitirLlamadaVirtual(Operando receptor, int slot, Operando resultado);

    void emitirFijarVtable(Operando objeto, String nombreClase);

    void emitirRetorno(Operando valor);
}
