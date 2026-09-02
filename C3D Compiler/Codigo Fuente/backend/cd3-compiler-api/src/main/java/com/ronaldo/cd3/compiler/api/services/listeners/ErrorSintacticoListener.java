package com.ronaldo.cd3.compiler.api.services.listeners;

import com.ronaldo.cd3.compiler.api.dtos.error.analisis.ErrorAnalisis;
import com.ronaldo.cd3.compiler.api.dtos.error.analisis.ErrorSintactico;
import java.util.List;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.IntervalSet;

/**
 *
 * @author ronaldo
 */
public class ErrorSintacticoListener extends BaseErrorListener {

    private List<ErrorAnalisis> errores;
    private String rutaArchivo;

    public ErrorSintacticoListener(List<ErrorAnalisis> errores, String rutaArchivo) {
        this.errores = errores;
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
            Object offendingSymbol,
            int line,
            int charPositionInLine,
            String msg,
            RecognitionException e) {

        String lexemaErroneo = "";

        if (offendingSymbol instanceof Token) {
            Token token = (Token) offendingSymbol;
            lexemaErroneo = token.getText();
        }

        String tokensEsperados = "";
        if (recognizer instanceof Parser) {
            Parser parser = (Parser) recognizer;
            IntervalSet esperados = parser.getExpectedTokens();
            tokensEsperados = esperados.toString(parser.getVocabulary());
        }
        

        errores.add(
                new ErrorSintactico(
                        line,
                        charPositionInLine + 1,
                        lexemaErroneo,
                        "Se esperaban: " + tokensEsperados,
                        rutaArchivo
                ));
    }

}
