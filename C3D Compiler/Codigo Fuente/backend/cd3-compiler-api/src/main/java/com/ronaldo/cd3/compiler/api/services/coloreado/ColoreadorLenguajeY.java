package com.ronaldo.cd3.compiler.api.services.coloreado;

import com.ronaldo.cd3.compiler.api.dtos.colorToken.ColorTokenDTO;
import com.ronaldo.cd3.compiler.api.modelos.colorToken.ColorToken;
import com.ronaldo.cd3.compiler.api.y.LenguajeYLexer;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author ronaldo
 */
public class ColoreadorLenguajeY {

    /**
     *
     * @param texto
     * @return
     */
    public List<ColorTokenDTO> generarColoreado(String texto) {

        List<ColorToken> lista = new ArrayList<>();

        LenguajeYLexer lexer = new LenguajeYLexer(
                CharStreams.fromString(texto)
        );

        Token token = lexer.nextToken();
        while (token.getType() != Token.EOF) {
            int idToken = token.getType();
            int inicio = token.getStartIndex();
            int fin = token.getStopIndex();

            lista.add(new ColorToken(inicio, fin, idToken));
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
