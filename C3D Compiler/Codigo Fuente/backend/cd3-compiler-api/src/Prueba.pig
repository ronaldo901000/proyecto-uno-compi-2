##
    Archivo de prueba: cubre todos los casos de la gramatica de Pig Latin
##
import mundo.Personajes.z
import mundo.Utilidades.y

VARIABILES>
esto edad : numerus 20;
esto fuerza : numerus 10;
esto gravedad : decimalis 9.81;
esto nombre : textum "Estudiante X";
esto inicial : littera 'a';
esto cifrado : falsus;
esto activo : verum;
series numeros[5] : numerus {1, 2, 3, 4, 5};
series pesos[3] : decimalis;
series nombres[2] : textum {"Hola", "Adios"};
esto miDireccion : Direccion {"Calle Real", 42};
esto ciudadano : Persona {"Valeria", 25, miDireccion};
series resistencia[3] : Persona;
esto miObjeto : novus Persona(12, "Profesor");
esto otroObjeto : novus Persona(12, miObjeto, novus Persona());
series misObjetos[10] : Persona;

MAIOR>

>> "Hola comandante!" ;
>> "Ingresa tu nombre por favor" ;
nombre 
>> "Bienvenido" >> nombre ;

>> "Ingresa tu edad" ;
edad 


si (edad >= 18 && cifrado == falsus) {
    cifrado = verum;
    activo = non cifrado;
} aliter (edad == 18) {
    >> "Justo tiene 18" ;
} aliter {
    >> "Es menor de edad" ;
} finis ;

esto contador : numerus 0;
dum (contador < 10) {
    contador++;
    si (contador == 5) {
        perge;
    } finis ;
    si (contador == 8) {
        interrumpe;
    } finis ;
} finis ;

esto intentos : numerus 0;
facere {
    intentos++;
    >> "Intento numero" >> intentos ;
} dum (intentos < 3);

per (esto i : numerus 0; i < 5; i++) {
    numeros[i] = numeros[i] * 2;
    >> numeros[i] ;
}

miObjeto.nombre = "Yennifer";
misObjetos[9].saludar();
nombre = miObjeto.getNombre();
ciudadano.miDireccion.numero = 100;

esto suma : numerus (numeros[0] + numeros[1]) * 2 - 1;
esto comparacion : falsus;
comparacion = (suma > 10) && (edad <= 30) || non cifrado;
esto negativo : numerus -suma;

esto poder : numerus 0;
poder = calcularPoder(fuerza, edad);

>> "Resultado final:" >> suma >> comparacion ;

reddere;

FINIS;
