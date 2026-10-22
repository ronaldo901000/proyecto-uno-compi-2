package com.ronaldo.cd3.compiler.api.modelos.ir;

import com.ronaldo.cd3.compiler.api.enums.RolOperando;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.Operando;
import com.ronaldo.cd3.compiler.api.modelos.ir.operando.OperandoLiteral;

public class AmbienteIR {

    private final ListaCuartetas cuartetas;
    private final ResolutorOperandos resolutor;
    private final PoolLiterales poolLiterales;
    private String unidadActual;

    public AmbienteIR(ListaCuartetas cuartetas) {
        this.cuartetas = cuartetas;
        this.resolutor = new ResolutorOperandos(cuartetas);
        this.poolLiterales = new PoolLiterales();
    }

    public PoolLiterales getPoolLiterales() {
        return poolLiterales;
    }

    public ListaCuartetas getCuartetas() {
        return cuartetas;
    }

    public String getUnidadActual() {
        return unidadActual;
    }

    public void setUnidadActual(String unidadActual) {
        this.unidadActual = unidadActual;
        this.resolutor.setUnidadActual(unidadActual);
    }

    public Operando resolver(String texto, RolOperando rol) {
        return enlazarLiteral(resolutor.resolver(texto, rol));
    }

    private Operando enlazarLiteral(Operando operando) {
        if (!(operando instanceof OperandoLiteral)) {
            return operando;
        }
        OperandoLiteral literal = (OperandoLiteral) operando;
        if (!poolLiterales.requiereMemoria(literal.getTipo())) {
            return operando;
        }
        PoolLiterales.EntradaLiteral entrada = poolLiterales.registrar(
                literal.getTipo(), literal.getTexto());
        return new OperandoLiteral(literal.getTexto(), literal.getCategoria(),
                literal.getTipo(), literal.getRol(), entrada.getEtiqueta());
    }

    public Operando resolverEtiqueta(String texto) {
        return resolutor.resolverEtiqueta(texto);
    }

    public Operando resolverFuncion(String texto) {
        return resolutor.resolverFuncion(texto);
    }
}
