package com.ronaldo.cd3.compiler.api.services.listeners;

import com.ronaldo.cd3.compiler.api.dtos.error.analisis.ErrorAnalisis;
import com.ronaldo.cd3.compiler.api.dtos.error.analisis.ErrorLexico;
import java.util.List;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 *
 * @author ronaldo
 */
public class ErrorLexicoListener extends BaseErrorListener {

    private List<ErrorAnalisis> errores;
    private String rutaArchivo;

    public ErrorLexicoListener(List<ErrorAnalisis> errores, String rutaArchivo) {
        this.errores = errores;
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
            int line, int charPositionInLine, String msg, RecognitionException e) {

        String lexema = "";

        if (recognizer instanceof Lexer) {
            Lexer lexer = (Lexer) recognizer;
            CharStream input = lexer.getInputStream();
            int index = lexer.getCharIndex();

            try {
                lexema = input.getText(org.antlr.v4.runtime.misc.Interval.of(index - 1, index - 1));
            } catch (Exception ex) {
                lexema = "?";
            }
        }

        errores.add(new ErrorLexico(line, charPositionInLine + 1, lexema, "Simbolo desconocido", rutaArchivo));
    }

}
