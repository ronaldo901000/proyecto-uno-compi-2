package com.ronaldo.cd3.compiler.api.services.analisis.pig;

import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import com.ronaldo.cd3.compiler.api.dtos.error.analisis.ErrorAnalisis;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.RespuestaDTO;
import com.ronaldo.cd3.compiler.api.interfaces.Analizable;
import com.ronaldo.cd3.compiler.api.pig.LenguajePigLexer;
import com.ronaldo.cd3.compiler.api.pig.LenguajePigParser;
import com.ronaldo.cd3.compiler.api.services.listeners.ErrorLexicoListener;
import com.ronaldo.cd3.compiler.api.services.listeners.ErrorSintacticoListener;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 *
 * @author ronaldo
 */
public class AnalizadorLenguajePig implements Analizable {

    @Override
    public void analizar(List<ArchivoDTO> archivos, RespuestaDTO respuesta) {

        ArchivoDTO archivo = archivos.get(0);
        List<ErrorAnalisis> erroresEncontrados = new ArrayList<>();

        //LEXER
        LenguajePigLexer lexer = new LenguajePigLexer(CharStreams.fromString(archivo.getContenido()));
        lexer.removeErrorListeners();
        ErrorLexicoListener listenerLexer = new ErrorLexicoListener(
                erroresEncontrados,
                archivo.getRuta());

        lexer.addErrorListener(listenerLexer);

        //TOKENS
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        LenguajePigParser parser = new LenguajePigParser(tokens);
        parser.removeErrorListeners();

        ErrorSintacticoListener listenerParser = new ErrorSintacticoListener(
                erroresEncontrados, archivo.getRuta());

        parser.addErrorListener(listenerParser);

        ParseTree arbol = parser.pig();

        if (!erroresEncontrados.isEmpty() || parser.getNumberOfSyntaxErrors() > 0) {
            respuesta.agregarListaErrores(erroresEncontrados);
            respuesta.setHayErrores(true);
            return;
        }
        
        //analisis semantico
    }

}
