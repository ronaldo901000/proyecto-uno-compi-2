package com.ronaldo.cd3.compiler.api.modelos.clasesZ.verificadores;

import com.ronaldo.cd3.compiler.api.modelos.clasesZ.ClaseZ;
import com.ronaldo.cd3.compiler.api.modelos.clasesZ.ConstructorZ;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;

/**
 * Verifica los constructores de una clase.
 *
 * @author ronaldo
 */
public class VerificadorConstructores {

    private final ClaseZ clase;
    private final VerificadorContenidoMiembro verificadorCuerpo;

    public VerificadorConstructores(ClaseZ clase, VerificadorContenidoMiembro verificadorCuerpo) {
        this.clase = clase;
        this.verificadorCuerpo = verificadorCuerpo;
    }

    public void verificar(Contexto contexto) {
        if (clase.getConstructores() == null) {
            return;
        }
        for (ConstructorZ constructor : clase.getConstructores()) {
            if (constructor.getNombre().equals(clase.getNombre())) {
                verificarConstructor(contexto, constructor);
            }
        }
    }

    private void verificarConstructor(Contexto contexto, ConstructorZ constructor) {
        
        verificadorCuerpo.verificar(contexto, "constructor_" + constructor.getNombre(),
                constructor.getParametros(), contexto.getTablaTipos().getVoid(),
                constructor.getCuerpo());
        
    }

}
