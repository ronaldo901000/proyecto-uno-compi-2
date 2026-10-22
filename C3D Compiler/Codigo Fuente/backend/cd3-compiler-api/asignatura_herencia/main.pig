##
    Importaciones de los otros lenguajes
    Las clases vienen del lenguaje Zetariano (paquete modelo)
    Funciones.y trae las utilidades de calculo sobre arreglos de notas
##
import asignatura_herencia.modelo.Persona.z
import asignatura_herencia.modelo.Estudiante.z
import asignatura_herencia.modelo.Docente.z
import asignatura_herencia.modelo.Asignatura.z
import asignatura_herencia.utilidades.Funciones.y

##
    Sección de variables globales
##
VARIABILES>
esto curso : novus Asignatura("Matematicas");
esto titular : novus Docente("Carlos Perez", 40, "Algebra");
esto opcion : numerus 0;
esto continuar_programa : falsus;
esto nombreTemp : textum "";
esto edadTemp : numerus 0;
esto indice : numerus 0;
esto notaTemp : numerus 0;

##
    Seccion de funcion principal
    Esta sección es obligatoria
##
MAIOR>
curso.setTitular(titular);
>> "Bienvenido al sistema de gestion de la asignatura " >> curso.getNombre();

dum (continuar_programa == falsus) {

    >> "-------------------------------------";
    >> "1. Agregar estudiante";
    >> "2. Ver promedio de un estudiante";
    >> "3. Ver nota mas alta y mas baja de un estudiante";
    >> "4. Ver promedio general del curso";
    >> "5. Listar estudiantes";
    >> "6. Ver resumen de un estudiante (usando Funciones.y)";
    >> "7. Ver docente titular y un estudiante (polimorfismo)";
    >> "8. Salir";
    >> "Ingresa una opcion" ;
    opcion <<

    si (opcion == 1) {
        >> "Ingresa el nombre del estudiante" ;
        nombreTemp <<
        >> "Ingresa la edad del estudiante" ;
        edadTemp <<

        esto nuevoEstudiante : novus Estudiante(nombreTemp, edadTemp);

        per (esto j : numerus 0; j < 5; j++) {
            >> "Ingresa la nota numero" >> j;
            notaTemp <<
            nuevoEstudiante.asignarNota(j, notaTemp);
        }

        curso.agregarEstudiante(nuevoEstudiante);
        >> "Estudiante agregado con exito";
    } aliter (opcion == 2) {
        >> "Ingresa el indice del estudiante (0 a 4)" ;
        indice <<
        >> "El promedio del estudiante es: " >> curso.obtenerEstudiante(indice).calcularPromedio();
    } aliter (opcion == 3) {
        >> "Ingresa el indice del estudiante (0 a 4)" ;
        indice <<
        >> "La nota mas alta es: " >> curso.obtenerEstudiante(indice).notaMasAlta();
        >> "La nota mas baja es: " >> curso.obtenerEstudiante(indice).notaMasBaja();
    } aliter (opcion == 4) {
        >> "El promedio general del curso es: " >> curso.promedioGeneral();
    } aliter (opcion == 5) {
        curso.mostrarEstudiantes();
    } aliter (opcion == 6) {
        >> "Ingresa el indice del estudiante (0 a 4)" ;
        indice <<
        series notasEstudiante[5] : numerus;
        notasEstudiante = curso.obtenerEstudiante(indice).obtenerNotas();
        >> "Promedio (Funciones.y): " >> calcularPromedioArreglo(notasEstudiante);
        >> "Nota maxima (Funciones.y): " >> encontrarMaxima(notasEstudiante);
        >> "Nota minima (Funciones.y): " >> encontrarMinima(notasEstudiante);
    } aliter (opcion == 7) {
        >> "Ingresa el indice del estudiante (0 a 4)" ;
        indice <<
        esto p1 : novus Persona();
        p1 = curso.getTitular();
        curso.presentar(p1);
        p1 = curso.obtenerEstudiante(indice);
        curso.presentar(p1);
    } aliter (opcion == 8) {
        continuar_programa = verum;
        >> "Hasta luego cadete";
    } aliter {
        >> "Opcion no valida, intenta de nuevo";
    } finis;

} finis;

FINIS;
