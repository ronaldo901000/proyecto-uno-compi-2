package com.ronaldo.cd3.compiler.api.services.analisis.z;

import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import com.ronaldo.cd3.compiler.api.dtos.error.analisis.ErrorAnalisis;
import com.ronaldo.cd3.compiler.api.dtos.error.analisis.ErrorSemantico;
import com.ronaldo.cd3.compiler.api.dtos.respuesta.RespuestaDTO;
import com.ronaldo.cd3.compiler.api.interfaces.Analizable;
import com.ronaldo.cd3.compiler.api.modelos.clasesZ.ClaseZ;
import com.ronaldo.cd3.compiler.api.modelos.cuarteta.ListaCuartetas;
import com.ronaldo.cd3.compiler.api.modelos.tabla.TablaSimbolos;
import com.ronaldo.cd3.compiler.api.modelos.tipos.TablaTipos;
import com.ronaldo.cd3.compiler.api.services.analisis.semantico.AnalizadorSemanticoZ;
import com.ronaldo.cd3.compiler.api.services.listeners.ErrorLexicoListener;
import com.ronaldo.cd3.compiler.api.services.listeners.ErrorSintacticoListener;
import com.ronaldo.cd3.compiler.api.services.visitors.ZVisitor;
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
        List<ClaseZ> clases = new ArrayList<>();
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

            ParseTree arbol = parser.programa();

            if (!erroresEncontrados.isEmpty() || parser.getNumberOfSyntaxErrors() > 0) {
                respuesta.agregarListaErrores(erroresEncontrados);
                respuesta.setHayErrores(true);

                continue;
            }

            ZVisitor visitor = new ZVisitor();
            LenguajeZParser.ProgramaContext programa = (LenguajeZParser.ProgramaContext) arbol;
            LenguajeZParser.ClaseContext claseCtx = programa.clase();
            String nombreClase = claseCtx.ID().getText();

            if (nombreClase.equals(nombreBaseArchivo(archivo.getNombre()))) {
                ClaseZ ast = (ClaseZ) visitor.visitClase(claseCtx);
                ast.setArchivo(archivo);
                clases.add(ast);
            } else {
                ErrorSemantico error = new ErrorSemantico(
                        claseCtx.start.getLine(),
                        claseCtx.start.getCharPositionInLine(),
                        nombreClase,
                        "La clase '" + nombreClase + "' debe llamarse igual que su archivo '"
                        + archivo.getNombre() + "'",
                        archivo.getRuta());
                respuesta.agregarUnError(error);
                respuesta.setHayErrores(true);
            }

        }

        AnalizadorSemanticoZ analizadorSemanticoZ = new AnalizadorSemanticoZ();
        analizadorSemanticoZ.analizar(clases, respuesta, tablaTipos, tablaSimbolos, cuartetas);
    }

    private String nombreBaseArchivo(String nombre) {
        if (nombre == null) {
            return "";
        }
        String base = nombre;
        int separador = nombre.lastIndexOf('/');
        if (separador < 0) {
            separador = nombre.lastIndexOf('\\');
        }
        if (separador >= 0) {
            base = nombre.substring(separador + 1);
        }
        if (base.toLowerCase().endsWith(".z")) {
            base = base.substring(0, base.length() - 2);
        }
        return base;
    }

}
