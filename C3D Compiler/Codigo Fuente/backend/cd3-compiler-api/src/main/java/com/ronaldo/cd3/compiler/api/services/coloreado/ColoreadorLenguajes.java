package com.ronaldo.cd3.compiler.api.services.coloreado;

import com.ronaldo.cd3.compiler.api.dtos.colorToken.ColorTokenDTO;
import com.ronaldo.cd3.compiler.api.enums.ExtensionArchivos;
import com.ronaldo.cd3.compiler.api.interfaces.Coloreable;
import com.ronaldo.cd3.compiler.api.modelos.colorToken.ColorToken;
import com.ronaldo.cd3.compiler.api.pig.LenguajePigLexer;
import com.ronaldo.cd3.compiler.api.y.LenguajeYLexer;
import com.ronaldo.cd3.compiler.api.zetariano.LenguajeZLexer;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author ronaldo
 */
public class ColoreadorLenguajes implements Coloreable {

    /**
     *
     * @param texto
     * @param opcion
     * @return
     */
    @Override
    public List<ColorTokenDTO> generarColoreado(String texto, String opcion) {

        List<ColorToken> lista = new ArrayList<>();

        Lexer lexer = null;

        if (opcion.equals(ExtensionArchivos.Y.getTexto())) {
            lexer = new LenguajeYLexer(
                    CharStreams.fromString(texto)
            );
        } else if (opcion.equals(ExtensionArchivos.Z.getTexto())) {

            lexer = new LenguajeZLexer(
                    CharStreams.fromString(texto)
            );
        }
        else if(opcion.equals(ExtensionArchivos.PIG.getTexto())){
            lexer = new LenguajePigLexer(
                    CharStreams.fromString(texto)
            );
        }

        Token token = lexer.nextToken();
        while (token.getType() != Token.EOF) {
            int idToken = token.getType();
            int inicio = token.getStartIndex();
            int fin = token.getStopIndex();

            ColorToken colorToken = new ColorToken(inicio, fin, idToken, opcion);
            lista.add(colorToken);

            token = lexer.nextToken();
        }

        List<ColorTokenDTO> tokens = new ArrayList<>();

        for (ColorToken ct : lista) {
            if (ct.getInicio() <= ct.getFin()) {
                tokens.add(new ColorTokenDTO(
                        ct.getInicio(),
                        ct.getFin(),
                        ct.getColor()
                ));
            }
        }

        return tokens;

    }
}
