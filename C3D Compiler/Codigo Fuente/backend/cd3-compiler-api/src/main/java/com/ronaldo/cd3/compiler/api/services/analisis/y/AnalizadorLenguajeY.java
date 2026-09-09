package com.ronaldo.cd3.compiler.api.services.analisis.y;

import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import com.ronaldo.cd3.compiler.api.dtos.error.analisis.ErrorAnalisis;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.RespuestaDTO;
import com.ronaldo.cd3.compiler.api.interfaces.Analizable;
import com.ronaldo.cd3.compiler.api.modelos.programaY.ProgramaY;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import com.ronaldo.cd3.compiler.api.services.listeners.ErrorLexicoListener;
import com.ronaldo.cd3.compiler.api.services.listeners.ErrorSintacticoListener;
import com.ronaldo.cd3.compiler.api.services.visitors.YVisitor;
import com.ronaldo.cd3.compiler.api.y.LenguajeYLexer;
import com.ronaldo.cd3.compiler.api.y.LenguajeYParser;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 *
 * @author ronaldo
 */
public class AnalizadorLenguajeY implements Analizable{

    @Override
    public void analizar(List<ArchivoDTO> archivosY, RespuestaDTO respuesta) {

        for (ArchivoDTO archivo : archivosY) {
            List<ErrorAnalisis> erroresArchivo = new ArrayList<>();

            LenguajeYLexer lexer = new LenguajeYLexer(
                    CharStreams.fromString(archivo.getContenido())
            );

            lexer.removeErrorListeners();
            ErrorLexicoListener listenerLexico = new ErrorLexicoListener(erroresArchivo, archivo.getRuta());
            lexer.addErrorListener(listenerLexico);
            lexer.setTokenFactory(CommonTokenFactory.DEFAULT);

            CommonTokenStream tokens = new CommonTokenStream(lexer);

            LenguajeYParser parser = new LenguajeYParser(tokens);
            parser.removeErrorListeners();

            ErrorSintacticoListener listenerParser = new ErrorSintacticoListener(
                    erroresArchivo, archivo.getRuta()
            );
            parser.addErrorListener(listenerParser);

            ParseTree arbol = parser.lenguaje();

            if (!erroresArchivo.isEmpty() || parser.getNumberOfSyntaxErrors() > 0) {
                respuesta.agregarListaErrores(erroresArchivo);
                respuesta.setHayErrores(true);

                continue;
            }
            
            YVisitor visitor = new YVisitor();
            ProgramaY ast = (ProgramaY) visitor.visit(arbol);

            TablaTipos tablaTipos = new TablaTipos();

        }
    }
}
