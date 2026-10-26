package com.ronaldo.cd3.compiler.api.services.generador.riscv;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.ir.AmbienteIR;
import com.ronaldo.cd3.compiler.api.modelos.ir.EmisorIR;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.Operando;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class EmisorRiscV implements EmisorIR {

    private final AmbienteIR ambiente;

    private final UnidadRiscV unidad;
    private final AritmeticaRiscV aritmetica;
    private final CadenasRiscV cadenas;
    private final LlamadasRiscV llamadas;
    private final MemoriaRiscV memoria;
    private final ControlFlujoRiscV control;

    public EmisorRiscV(AmbienteIR ambiente) {
        this.ambiente = ambiente;
        ListaCuartetas cuartetas = ambiente.getCuartetas();

        TiposRiscV tipos = new TiposRiscV(cuartetas);
        FrameRiscV frame = new FrameRiscV();
        List<Operando> parametrosPendientes = new ArrayList<>();

        this.unidad = new UnidadRiscV(cuartetas, frame, parametrosPendientes);
        AccesoOperandosRiscV acceso = new AccesoOperandosRiscV(unidad, frame, tipos);

        this.aritmetica = new AritmeticaRiscV(unidad, acceso, tipos);
        this.cadenas = new CadenasRiscV(unidad, acceso, tipos);
        this.llamadas = new LlamadasRiscV(cuartetas, unidad, frame, acceso,
                aritmetica, tipos, parametrosPendientes);
        this.memoria = new MemoriaRiscV(unidad, acceso, aritmetica, tipos);
        this.control = new ControlFlujoRiscV(ambiente, unidad, acceso);
    }

    public String getCuerpo() {
        return unidad.getCuerpo();
    }

    public void finalizar() {
        unidad.cerrar();
    }

    @Override
    public AmbienteIR getAmbiente() {
        return ambiente;
    }

    @Override
    public void emitirAsignacion(Operando destino, Operando origen) {
        unidad.asegurar();
        memoria.emitirAsignacion(destino, origen);
    }

    @Override
    public void emitirAritmetica(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado) {
        unidad.asegurar();
        // Si hay cadenas, la operacion es una concatenacion
        if (!aritmetica.emitirAritmetica(operador, izquierda, derecha, resultado)) {
            cadenas.emitirConcatena(izquierda, derecha, resultado);
        }
    }

    @Override
    public void emitirComparacion(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado) {
        unidad.asegurar();
        aritmetica.emitirComparacion(operador, izquierda, derecha, resultado);
    }

    @Override
    public void emitirIgualdad(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado) {
        unidad.asegurar();
        aritmetica.emitirIgualdad(operador, izquierda, derecha, resultado);
    }

    @Override
    public void emitirLogicaBinaria(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado) {
        unidad.asegurar();
        aritmetica.emitirLogicaBinaria(operador, izquierda, derecha, resultado);
    }

    @Override
    public void emitirNegacion(OperadorCuarteta operador, Operando operando,
            Operando resultado) {
        unidad.asegurar();
        aritmetica.emitirNegacion(operador, operando, resultado);
    }

    @Override
    public void emitirIncrementoDecremento(OperadorCuarteta operador, Operando objetivo,
            Operando resultado) {
        unidad.asegurar();
        aritmetica.emitirIncrementoDecremento(operador, objetivo, resultado);
    }

    @Override
    public void emitirEtiqueta(Operando etiqueta) {
        ambiente.getCuartetas().entrarUnidadEmision(etiqueta.getTexto());
        control.emitirEtiqueta(etiqueta);
    }

    @Override
    public void emitirSalto(Operando etiqueta) {
        unidad.asegurar();
        control.emitirSalto(etiqueta);
    }

    @Override
    public void emitirCondicional(OperadorCuarteta operador, Operando condicion,
            Operando etiqueta) {
        unidad.asegurar();
        control.emitirCondicional(condicion, etiqueta);
    }

    @Override
    public void emitirImprimir(Operando valor, boolean conSaltoLinea) {
        unidad.asegurar();
        cadenas.emitirImprimir(valor, conSaltoLinea);
    }

    @Override
    public void emitirLeer(Operando destino) {
        unidad.asegurar();
        cadenas.emitirLeer(destino);
    }

    @Override
    public void emitirPunteroInicio(Operando destino, Operando cantidad) {
        unidad.asegurar();
        memoria.emitirPunteroInicio(destino, cantidad);
    }

    @Override
    public void emitirNuevoArreglo2D(Operando destino, Operando filas, Operando columnas) {
        unidad.asegurar();
        memoria.emitirNuevoArreglo2D(destino, filas, columnas);
    }

    @Override
    public void emitirAccesoIndice(Operando base, Operando indice, Operando destino) {
        unidad.asegurar();
        memoria.emitirAccesoIndice(base, indice, destino);
    }

    @Override
    public void emitirParametro(Operando operando) {
        llamadas.emitirParametro(operando);
    }

    @Override
    public void emitirLlamada(Operando funcion, Operando resultado) {
        unidad.asegurar();
        llamadas.emitirLlamada(funcion, resultado);
    }

    @Override
    public void emitirLlamadaVirtual(Operando receptor, int slot, Operando resultado) {
        unidad.asegurar();
        llamadas.emitirLlamadaVirtual(receptor, slot, resultado);
    }

    @Override
    public void emitirFijarVtable(Operando objeto, String nombreClase) {
        unidad.asegurar();
        llamadas.emitirFijarVtable(objeto, nombreClase);
    }

    @Override
    public void emitirRetorno(Operando valor) {
        unidad.asegurar();
        llamadas.emitirRetorno(valor);
    }
}
