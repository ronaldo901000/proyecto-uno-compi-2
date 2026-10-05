package com.ronaldo.cd3.compiler.api.modelos.clasesZ;

import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.funcionesY.FuncionDef;
import com.ronaldo.cd3.compiler.api.modelos.semantica.reglas.Reglas;
import com.ronaldo.cd3.compiler.api.modelos.tipos.Tipo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ResolutorTipoRetorno {

    private final Reglas reglas;

    public ResolutorTipoRetorno(Reglas reglas) {
        this.reglas = reglas;
    }

    public Tipo resolver(Contexto contexto, FuncionDef metodo) {
        if (metodo.getTipoRetorno() == null) {
            return contexto.getTablaTipos().getVoid();
        }

        Tipo tipo = reglas.resolverTipo(contexto, metodo.getTipoRetorno(),
                metodo.getFila(), metodo.getColumna());

        if (metodo.getDimensionesRetorno() > 0 && !reglas.esError(tipo)) {
            List<Integer> dims = new ArrayList<>();
            for (int i = 0; i < metodo.getDimensionesRetorno(); i++) {
                dims.add(0);
            }
            return contexto.getTablaTipos().getArreglo(tipo, dims);
        }
        return tipo;
    }
}
