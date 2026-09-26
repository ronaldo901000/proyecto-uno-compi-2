package com.ronaldo.cd3.compiler.api.services.analisis.semantico;

import com.ronaldo.cd3.compiler.api.dtos.archivo.ArchivoDTO;
import com.ronaldo.cd3.compiler.api.enums.ExtensionArchivos;
import com.ronaldo.cd3.compiler.api.modelos.contexto.Contexto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Acceso;
import com.ronaldo.cd3.compiler.api.modelos.expresion.ExpIndice;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Expresion;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Lectura;
import com.ronaldo.cd3.compiler.api.modelos.expresion.LiteralStructura;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Llamada;
import com.ronaldo.cd3.compiler.api.modelos.expresion.NewObjeto;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Operacion;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Ternaria;
import com.ronaldo.cd3.compiler.api.modelos.expresion.Unario;
import com.ronaldo.cd3.compiler.api.modelos.estructurasY.EstructuraDef;
import com.ronaldo.cd3.compiler.api.modelos.funcionesY.FuncionDef;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Asignacion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Imprimir;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Instruccion;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.Retorno;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo.Ciclo;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo.CicloHacerMientras;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo.CicloMientras;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.ciclo.CicloPara;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.condicional.InstSi;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.condicional.RamaSino;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.DeclaracionArreglo;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.DeclaracionEstructura;
import com.ronaldo.cd3.compiler.api.modelos.instruccion.declar.DeclaracionVariable;
import com.ronaldo.cd3.compiler.api.modelos.programaPig.ImportacionPig;
import com.ronaldo.cd3.compiler.api.modelos.programaPig.ProgramaPig;
import com.ronaldo.cd3.compiler.api.modelos.programaY.ProgramaY;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ronaldo
 */
public class VerificadorImportesPig {

    private static final Set<String> TIPOS_PRIMITIVOS = new HashSet<>();

    static {
        TIPOS_PRIMITIVOS.add("numerus");
        TIPOS_PRIMITIVOS.add("decimalis");
        TIPOS_PRIMITIVOS.add("textum");
        TIPOS_PRIMITIVOS.add("littera");
        TIPOS_PRIMITIVOS.add("bool");
        TIPOS_PRIMITIVOS.add("entero");
        TIPOS_PRIMITIVOS.add("flotante");
        TIPOS_PRIMITIVOS.add("cadena");
        TIPOS_PRIMITIVOS.add("caracter");
        TIPOS_PRIMITIVOS.add("boolean");
        TIPOS_PRIMITIVOS.add("int");
        TIPOS_PRIMITIVOS.add("double");
        TIPOS_PRIMITIVOS.add("string");
        TIPOS_PRIMITIVOS.add("char");
        TIPOS_PRIMITIVOS.add("void");
        TIPOS_PRIMITIVOS.add("nulo");
        TIPOS_PRIMITIVOS.add("null");
    }

    private final Set<String> rutasArchivosImportados = new HashSet<>();

    public Set<String> getRutasArchivosImportados() {
        return rutasArchivosImportados;
    }

    public void verificar(Contexto contexto, ProgramaPig programa,
            List<ArchivoDTO> archivosImportables, List<ProgramaY> programasY) {

        if (programa == null) {
            return;
        }

        Set<String> importados = new HashSet<>();
        if (programa.getImports() != null) {
            for (ImportacionPig importacion : programa.getImports()) {
                ArchivoDTO archivo = buscarArchivo(importacion.getRuta(), archivosImportables);
                if (archivo == null) {
                    contexto.agregarError(importacion.getFila(), importacion.getColumna(),
                            importacion.getRuta(),
                            "La ruta del import '" + importacion.getRuta() + "' no existe");
                    continue;
                }
                rutasArchivosImportados.add(archivo.getRuta());
                importados.addAll(simbolosDe(archivo, programasY));
            }
        }

        List<Instruccion> todas = new ArrayList<>();
        if (programa.getDeclaraciones() != null) {
            todas.addAll(programa.getDeclaraciones());
        }
        if (programa.getBloqueMaior() != null) {
            todas.addAll(programa.getBloqueMaior());
        }
        for (Instruccion inst : todas) {
            verificarInstruccion(contexto, inst, importados);
        }
    }

    private void verificarInstruccion(Contexto contexto, Instruccion inst,
            Set<String> importados) {
        if (inst == null) {
            return;
        }
        if (inst instanceof DeclaracionVariable) {
            DeclaracionVariable d = (DeclaracionVariable) inst;
            verificarTipo(contexto, d.getTipoDato(), d.getFila(), d.getColumna(), importados);
            verificarExpresion(contexto, d.getValorInicial(), importados);
            return;
        }
        if (inst instanceof DeclaracionArreglo) {
            DeclaracionArreglo d = (DeclaracionArreglo) inst;
            verificarTipo(contexto, d.getTipoDato(), d.getFila(), d.getColumna(), importados);
            for (Expresion e : listaSegura(d.getDimensiones())) {
                verificarExpresion(contexto, e, importados);
            }
            for (Expresion e : listaSegura(d.getValoresIniciales())) {
                verificarExpresion(contexto, e, importados);
            }
            return;
        }
        if (inst instanceof DeclaracionEstructura) {
            DeclaracionEstructura d = (DeclaracionEstructura) inst;
            verificarTipo(contexto, d.getTipoDato(), d.getFila(), d.getColumna(), importados);
            verificarExpresion(contexto, d.getValorExpresion(), importados);
            for (Expresion e : listaSegura(d.getValoresIniciales())) {
                verificarExpresion(contexto, e, importados);
            }
            return;
        }
        if (inst instanceof Asignacion) {
            Asignacion a = (Asignacion) inst;
            verificarExpresion(contexto, a.getObjetivo(), importados);
            verificarExpresion(contexto, a.getValor(), importados);
            return;
        }
        if (inst instanceof Imprimir) {
            for (Expresion e : ((Imprimir) inst).getValores()) {
                verificarExpresion(contexto, e, importados);
            }
            return;
        }
        if (inst instanceof Lectura) {
            verificarExpresion(contexto, ((Lectura) inst).getArgumento(), importados);
            return;
        }
        if (inst instanceof Llamada) {
            verificarExpresion(contexto, (Expresion) inst, importados);
            return;
        }
        if (inst instanceof Retorno) {
            verificarExpresion(contexto, ((Retorno) inst).getExpresion(), importados);
            return;
        }
        if (inst instanceof CicloPara) {
            CicloPara c = (CicloPara) inst;
            if (c.getIterador() != null) {
                verificarTipo(contexto, c.getIterador().getTipoDato(),
                        c.getIterador().getFila(), c.getIterador().getColumna(), importados);
                verificarExpresion(contexto, c.getIterador().getValorInicial(), importados);
            }
            verificarExpresion(contexto, c.getCondicion(), importados);
            verificarInstruccion(contexto, c.getActualizacion(), importados);
            verificarInstrucciones(contexto, c.getInstruccionesInternas(), importados);
            return;
        }
        if (inst instanceof CicloMientras || inst instanceof CicloHacerMientras) {
            Ciclo c = (Ciclo) inst;
            verificarExpresion(contexto, c.getCondicion(), importados);
            verificarInstrucciones(contexto, c.getInstruccionesInternas(), importados);
            return;
        }
        if (inst instanceof InstSi) {
            InstSi i = (InstSi) inst;
            verificarExpresion(contexto, i.getCondicion(), importados);
            verificarInstrucciones(contexto, i.getInstruccionesInternasSi(), importados);
            if (i.getRamasSino() != null) {
                for (RamaSino rama : i.getRamasSino()) {
                    verificarExpresion(contexto, rama.getCondicion(), importados);
                    verificarInstrucciones(contexto, rama.getInstruccionesInternas(), importados);
                }
            }
            verificarInstrucciones(contexto, i.getInstruccionesInternasContrario(), importados);
        }
    }

    private void verificarInstrucciones(Contexto contexto, List<Instruccion> instrucciones,
            Set<String> importados) {
        for (Instruccion inst : listaInstruccionesSegura(instrucciones)) {
            verificarInstruccion(contexto, inst, importados);
        }
    }

    private void verificarTipo(Contexto contexto, String tipoDato,
            int fila, int columna, Set<String> importados) {
        if (tipoDato == null || tipoDato.isEmpty()) {
            return;
        }
        String normalizado = tipoDato.toLowerCase();
        if (TIPOS_PRIMITIVOS.contains(normalizado)) {
            return;
        }
        if (importados.contains(normalizado)) {
            return;
        }
        contexto.agregarError(fila, columna, tipoDato,
                "El tipo '" + tipoDato + "' debe ser importado para poder utilizarse "
                + "en el archivo .pig");
    }

    private void verificarExpresion(Contexto contexto, Expresion exp,
            Set<String> importados) {
        if (exp == null) {
            return;
        }
        if (exp instanceof NewObjeto) {
            NewObjeto nuevo = (NewObjeto) exp;
            String clase = nuevo.getNombreClase();
            if (!importados.contains(clase.toLowerCase())) {
                contexto.agregarError(nuevo.getFila(), nuevo.getColumna(), clase,
                        "La clase '" + clase + "' debe ser importada para poder "
                        + "utilizarse en el archivo .pig");
            }
            for (Expresion argumento : listaSegura(nuevo.getArgumentos())) {
                verificarExpresion(contexto, argumento, importados);
            }
            return;
        }
        if (exp instanceof Llamada) {
            Llamada llamada = (Llamada) exp;
            verificarExpresion(contexto, llamada.getObjetivo(), importados);
            if (llamada.getObjetivo() == null) {
                String funcion = llamada.getNombreFuncion();
                if (funcion != null && !importados.contains(funcion.toLowerCase())) {
                    contexto.agregarError(llamada.getFila(), llamada.getColumna(), funcion,
                            "La función '" + funcion + "' debe ser importada para poder "
                            + "utilizarse en el archivo .pig");
                }
            }
            for (Expresion argumento : listaSegura(llamada.getArgumentos())) {
                verificarExpresion(contexto, argumento, importados);
            }
            return;
        }
        if (exp instanceof Operacion) {
            Operacion op = (Operacion) exp;
            verificarExpresion(contexto, op.getIzquierda(), importados);
            verificarExpresion(contexto, op.getDerecha(), importados);
            return;
        }
        if (exp instanceof Unario) {
            verificarExpresion(contexto, ((Unario) exp).getExp(), importados);
            return;
        }
        if (exp instanceof Ternaria) {
            Ternaria t = (Ternaria) exp;
            verificarExpresion(contexto, t.getCondicion(), importados);
            verificarExpresion(contexto, t.getVerdadero(), importados);
            verificarExpresion(contexto, t.getFalso(), importados);
            return;
        }
        if (exp instanceof Acceso) {
            verificarExpresion(contexto, ((Acceso) exp).getObjeto(), importados);
            return;
        }
        if (exp instanceof ExpIndice) {
            ExpIndice e = (ExpIndice) exp;
            verificarExpresion(contexto, e.getArreglo(), importados);
            verificarExpresion(contexto, e.getIndice(), importados);
            return;
        }
        if (exp instanceof Lectura) {
            verificarExpresion(contexto, ((Lectura) exp).getArgumento(), importados);
            return;
        }
        if (exp instanceof LiteralStructura) {
            for (Expresion valor : listaSegura(((LiteralStructura) exp).getValores())) {
                verificarExpresion(contexto, valor, importados);
            }
        }
    }

    private ArchivoDTO buscarArchivo(String importe, List<ArchivoDTO> importables) {
        if (importe == null) {
            return null;
        }
        String importeNorm = normalizar(importe);
        for (ArchivoDTO archivo : importables) {
            if (coincidePorRuta(archivo, importeNorm)) {
                return archivo;
            }
        }
        for (ArchivoDTO archivo : importables) {
            if (coincidePorNombre(archivo, importeNorm)) {
                return archivo;
            }
        }
        return null;
    }

    private boolean coincidePorRuta(ArchivoDTO archivo, String importeNorm) {
        String ruta = normalizar(archivo.getRuta());
        return ruta.equals(importeNorm) || ruta.endsWith("/" + importeNorm);
    }

    private boolean coincidePorNombre(ArchivoDTO archivo, String importeNorm) {
        String nombreBase = nombreBase(archivo.getNombre());
        if (nombreBase == null || nombreBase.isEmpty()) {
            return false;
        }
        String extension = (archivo.getExtension() == null)
                ? "" : archivo.getExtension().toLowerCase();
        String nombreExt = (extension.isEmpty())
                ? nombreBase.toLowerCase()
                : nombreBase.toLowerCase() + "." + extension;
        String importeBase = nombreBase(importeNorm);
        String ultimo = (importeBase.contains("/"))
                ? importeBase.substring(importeBase.lastIndexOf('/') + 1) : importeBase;
        if (ultimo.equals(nombreExt) || ultimo.equals(nombreBase)) {
            return true;
        }
        return dosUltimos(importeNorm).equals(nombreExt);
    }

    private String dosUltimos(String importeNorm) {
        if (importeNorm == null || importeNorm.isEmpty()) {
            return "";
        }
        String importeBase = nombreBase(importeNorm);
        String[] partes = importeBase.split("/");
        if (partes.length < 2) {
            return "";
        }
        return partes[partes.length - 2] + "." + partes[partes.length - 1];
    }

    private String normalizar(String ruta) {
        if (ruta == null || ruta.isEmpty()) {
            return "";
        }
        String resultado = ruta.replace('\\', '/');
        String extension = "";
        String menor = resultado.toLowerCase();
        for (String ext : ExtensionArchivos.todas()) {
            if (menor.endsWith("." + ext)) {
                extension = "." + ext;
                resultado = resultado.substring(0, resultado.length() - extension.length());
                break;
            }
        }
        resultado = resultado.replace('.', '/');
        while (resultado.startsWith("/")) {
            resultado = resultado.substring(1);
        }
        return resultado.toLowerCase() + extension;
    }

    private String nombreBase(String nombre) {
        if (nombre == null) {
            return "";
        }
        StringBuilder soporte = new StringBuilder(nombre);
        int separador = Math.max(soporte.lastIndexOf("/"), soporte.lastIndexOf("\\"));
        if (separador >= 0) {
            soporte.delete(0, separador + 1);
        }
        int indice = soporte.lastIndexOf(".");
        if (indice > 0) {
            soporte.delete(indice, soporte.length());
        }
        return soporte.toString();
    }

    private Set<String> simbolosDe(ArchivoDTO archivo, List<ProgramaY> programasY) {
        Set<String> simbolos = new HashSet<>();
        String extension = (archivo.getExtension() == null) ? "" : archivo.getExtension();
        if (extension.equalsIgnoreCase(ExtensionArchivos.Z.getTexto())) {
            String nombreBase = nombreBase(archivo.getNombre());
            if (!nombreBase.isEmpty()) {
                simbolos.add(nombreBase.toLowerCase());
            }
            return simbolos;
        }
        if (extension.equalsIgnoreCase(ExtensionArchivos.Y.getTexto())) {
            ProgramaY programa = buscarPrograma(archivo, programasY);
            if (programa != null) {
                if (programa.getEstructuras() != null) {
                    for (EstructuraDef estructura : programa.getEstructuras()) {
                        if (estructura.getNombre() != null) {
                            simbolos.add(estructura.getNombre().toLowerCase());
                        }
                    }
                }
                if (programa.getFunciones() != null) {
                    for (FuncionDef funcion : programa.getFunciones()) {
                        if (funcion.getNombre() != null) {
                            simbolos.add(funcion.getNombre().toLowerCase());
                        }
                    }
                }
            }
        }
        return simbolos;
    }

    private ProgramaY buscarPrograma(ArchivoDTO archivo, List<ProgramaY> programasY) {
        if (programasY == null) {
            return null;
        }
        for (ProgramaY programa : programasY) {
            if (programa.getArchivo() == archivo) {
                return programa;
            }
        }
        for (ProgramaY programa : programasY) {
            if (programa.getArchivo() != null
                    && programa.getArchivo().getRuta() != null
                    && programa.getArchivo().getRuta().equals(archivo.getRuta())) {
                return programa;
            }
        }
        return null;
    }

    private List<Expresion> listaSegura(List<Expresion> lista) {
        return (lista == null) ? new ArrayList<>() : lista;
    }

    private List<Instruccion> listaInstruccionesSegura(List<Instruccion> lista) {
        return (lista == null) ? new ArrayList<>() : lista;
    }

}