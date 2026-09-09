package com.ronaldo.cd3.compiler.api.services.analisis.z;

import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import com.ronaldo.cd3.compiler.api.dtos.error.analisis.ErrorAnalisis;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.RespuestaDTO;
import com.ronaldo.cd3.compiler.api.interfaces.Analizable;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import com.ronaldo.cd3.compiler.api.services.listeners.ErrorLexicoListener;
import com.ronaldo.cd3.compiler.api.services.listeners.ErrorSintacticoListener;
import com.ronaldo.cd3.compiler.api.zetariano.LenguajeZLexer;
import com.ronaldo.cd3.compiler.api.zetariano.LenguajeZParser;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 *
 * @author ronaldo
 */
public class AnalizadorLenguajeZ implements Analizable {

    @Override
    public void analizar(List<ArchivoDTO> archivos, RespuestaDTO respuesta,
            TablaTipos tablaTipos, TablaSimbolos tablaSimbolos,
            ListaCuartetas cuartetas) {
        for (ArchivoDTO archivo : archivos) {
            List<ErrorAnalisis> erroresEncontrados = new ArrayList<>();

            //LEXER
            LenguajeZLexer lexer = new LenguajeZLexer(
                    CharStreams.fromString(archivo.getContenido())
            );

            lexer.removeErrorListeners();

            ErrorLexicoListener listenerLexer = new ErrorLexicoListener(
                    erroresEncontrados,
                    archivo.getRuta());

            lexer.addErrorListener(listenerLexer);

            //TOKENS
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            //PARSER
            LenguajeZParser parser = new LenguajeZParser(tokens);

            parser.removeErrorListeners();

            ErrorSintacticoListener listenerParser = new ErrorSintacticoListener(
                    erroresEncontrados, archivo.getRuta());

            parser.addErrorListener(listenerParser);

            ParseTree arbol = parser.clase();

            if (!erroresEncontrados.isEmpty() || parser.getNumberOfSyntaxErrors() > 0) {
                respuesta.agregarListaErrores(erroresEncontrados);
                respuesta.setHayErrores(true);

                continue;
            }

        }
    }

}
