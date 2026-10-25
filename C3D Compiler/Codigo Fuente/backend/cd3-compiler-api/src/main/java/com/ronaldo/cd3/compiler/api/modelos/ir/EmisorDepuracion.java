package com.ronaldo.cd3.compiler.api.modelos.ir;

import com.ronaldo.cd3.compiler.api.enums.OperadorCuarteta;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.Operando;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoAtributo;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoIndice;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoLiteral;
import java.util.ArrayList;
import java.util.List;

public class EmisorDepuracion implements EmisorIR {

    private final AmbienteIR ambiente;
    private final List<String> registro;

    public EmisorDepuracion(AmbienteIR ambiente) {
        this.ambiente = ambiente;
        this.registro = new ArrayList<>();
    }

    @Override
    public AmbienteIR getAmbiente() {
        return ambiente;
    }

    @Override
    public void emitirAsignacion(Operando destino, Operando origen) {   
        registrar("ASIGNACION", destino, origen);
    }

    @Override
    public void emitirAritmetica(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado) {
        registrar("ARITMETICA:" + operador, resultado, izquierda, derecha);
    }

    @Override
    public void emitirComparacion(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado) {
        registrar("COMPARACION:" + operador, resultado, izquierda, derecha);
    }

    @Override
    public void emitirIgualdad(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado) {
        registrar("IGUALDAD:" + operador, resultado, izquierda, derecha);
    }

    @Override
    public void emitirLogicaBinaria(OperadorCuarteta operador, Operando izquierda,
            Operando derecha, Operando resultado) {
        registrar("LOGICA:" + operador, resultado, izquierda, derecha);
    }

    @Override
    public void emitirNegacion(OperadorCuarteta operador, Operando operando,
            Operando resultado) {
        registrar("NEGACION:" + operador, resultado, operando);
    }

    @Override
    public void emitirIncrementoDecremento(OperadorCuarteta operador,
            Operando objetivo, Operando resultado) {
        registrar("INCDEC:" + operador, objetivo, resultado);
    }

    @Override
    public void emitirEtiqueta(Operando etiqueta) {
        registrar("ETIQUETA", etiqueta);
    }

    @Override
    public void emitirSalto(Operando etiqueta) {
        registrar("GOTO", etiqueta);
    }

    @Override
    public void emitirCondicional(OperadorCuarteta operador, Operando condicion,
            Operando etiqueta) {
        registrar("CONDICIONAL:" + operador, condicion, etiqueta);
    }

    @Override
    public void emitirImprimir(Operando valor, boolean conSaltoLinea) {
        registro.add("IMPRIMIR:" + conSaltoLinea + " | " + describir(valor));
    }

    @Override
    public void emitirLeer(Operando destino) {
        registrar("LEER", destino);
    }

    @Override
    public void emitirPunteroInicio(Operando destino, Operando cantidad) {
        registrar("PUNTERO_INICIO", destino, cantidad);
    }

    @Override
    public void emitirAccesoIndice(Operando base, Operando indice,
            Operando destino) {
        registrar("ACCESO_INDICE", destino, base, indice);
    }

    @Override
    public void emitirParametro(Operando argumento) {
        registrar("PARAMETRO", argumento);
    }

    @Override
    public void emitirLlamada(Operando funcion, Operando resultado) {
        registrar("LLAMADA", resultado, funcion);
    }

    @Override
    public void emitirLlamadaVirtual(Operando receptor, int slot, Operando resultado) {
        registro.add("LLAMADA_VIRTUAL:slot=" + slot + " | "
                + describir(receptor) + " | " + describir(resultado));
    }

    @Override
    public void emitirFijarVtable(Operando objeto, String nombreClase) {
        registro.add("FIJAR_VTABLE:" + nombreClase + " | " + describir(objeto));
    }

    @Override
    public void emitirRetorno(Operando valor) {
        registrar("RETORNO", valor);
    }

    public List<String> getRegistro() {
        return registro;
    }


    private void registrar(String operacion, Operando... operandos) {
        StringBuilder sb = new StringBuilder(operacion);
        for (Operando operando : operandos) {
            sb.append(" | ").append(describir(operando));
        }
        registro.add(sb.toString());
    }

    private String describir(Operando operando) {
        if (operando == null) {
            return "-";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(operando.getTexto()).append('{')
                .append(operando.getClass().getSimpleName()).append(',')
                .append(operando.getCategoria()).append(',')
                .append(operando.getTipo() != null ? operando.getTipo() : "?")
                .append(',').append(operando.getRol());
        if (operando instanceof OperandoAtributo) {
            sb.append(",off=").append(((OperandoAtributo) operando).getOffset());
        }
        if (operando instanceof OperandoIndice) {
            sb.append(",tam=").append(((OperandoIndice) operando).getTamañoElemento());
        }
        if (operando instanceof OperandoLiteral
                && ((OperandoLiteral) operando).esEnMemoria()) {
            sb.append(",etiq=").append(((OperandoLiteral) operando).getEtiqueta());
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public void emitirNuevoArreglo2D(Operando destino, Operando filas,
            Operando columnas) {
        registrar("PUNTERO_INICIO_2D", destino, filas, columnas);
    }
}
