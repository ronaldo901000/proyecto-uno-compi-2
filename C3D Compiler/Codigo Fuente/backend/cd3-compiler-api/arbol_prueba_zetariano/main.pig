##
	Importaciones de los otros lenguajes
##
import libs.Utilidades.y
import libs.AgenteZetariano.z

##
	Seccion de variables globales, arreglos y estructuras.
	Esta seccion es opcional en la gramatica pero la usamos
	para forzar la resolucion de tipos antes de MAIOR>
##
VARIABILES>
esto comandante : textum "Sin nombre";
esto edadComandante : numerus 0;
esto acumulador : numerus 0;
esto resultadoFactorial : numerus 0;
esto resultadoFibonacci : numerus 0;
esto resultadoMCD : numerus 0;
esto promedio : decimalis 0.0;
esto banderaCombinada : falsus;

series listaNumeros[6] : numerus {12, 4, 55, 3, 90, 23};
series ordenados[6] : numerus {3, 4, 12, 23, 55, 90};
series matrizA[9] : numerus {1, 2, 3, 4, 5, 6, 7, 8, 9};
series matrizB[9] : numerus {9, 8, 7, 6, 5, 4, 3, 2, 1};
series matrizSuma[9] : numerus;
series matrizProducto[9] : numerus;

esto puntoActual : Punto3D {3, 4, 5};
esto alumno : Persona {"Valeria", 20, {70, 80, 90, 60, 100}};

MAIOR>

>> "=== Bienvenido cadete, contacto Zetariano en progreso ===";
>> "Ingresa tu nombre de comandante:";
comandante <<
>> "Bienvenido comandante " >> comandante;

>> "Ingresa tu edad:";
edadComandante <<

si (edadComandante >= 18) {
	>> "Acceso concedido: eres mayor de edad";
} aliter (edadComandante == 17) {
	>> "Acceso condicional: casi mayor de edad";
} aliter {
	>> "Acceso denegado: eres menor de edad";
} finis;

## --- Recursividad simple y mutua desde Utilidades.y --- ##
resultadoFactorial = factorial(6);
>> "Factorial de 6 es: " >> resultadoFactorial;

resultadoFibonacci = fibonacci(10);
>> "Fibonacci de 10 es: " >> resultadoFibonacci;

resultadoMCD = mcd(48, 18);
>> "MCD de 48 y 18 es: " >> resultadoMCD;

si (esPar(24) == verum) {
	>> "24 es par (recursividad mutua confirmada)";
} finis;

## --- Arreglos por referencia + recursividad --- ##
acumulador = sumaRecursiva(listaNumeros, 6);
>> "Suma recursiva del arreglo: " >> acumulador;

esto maximo : numerus maximoRecursivo(listaNumeros, 6);
>> "Maximo recursivo del arreglo: " >> maximo;

esto posicion : numerus busquedaBinaria(ordenados, 0, 5, 55);
>> "Posicion de 55 en el arreglo ordenado: " >> posicion;

## --- Matrices aplanadas --- ##
llenarMatriz(matrizA, 3, 3);
sumarMatrices(matrizA, matrizB, matrizSuma, 9);
>> "Suma de matrices, celda [4]: " >> matrizSuma[4];

multiplicarMatrices(matrizA, matrizB, matrizProducto, 3);
>> "Producto de matrices, celda [0]: " >> matrizProducto[0];

esto traza : numerus trazaMatriz(matrizA, 3);
>> "Traza de matrizA: " >> traza;

## --- Estructuras por referencia + recursividad --- ##
promedio = promedioNotas(alumno);
>> "Promedio de notas de " >> alumno.nombre >> ": " >> promedio;

esto pasosOrigen : numerus pasosHaciaOrigen(puntoActual, 0);
>> "Pasos para llegar al origen desde el punto: " >> pasosOrigen;

## --- Objetos Zetarianos: heap, recursividad, matriz 2D --- ##
esto agente1 : novus AgenteZetariano("Vex-7", 3);
esto agente2 : novus AgenteZetariano("Kroll-2", 5);
esto agente3 : novus AgenteZetariano();

agente1.enlazar(agente2);
agente2.enlazar(agente3);

>> "Poder total de la cadena de agentes: " >> agente1.poderTotalCadena();
>> "Cantidad de agentes en la cadena: " >> agente1.contarCadena();

agente1.llenarMatrizEnergia();
>> "Suma de la diagonal de la matriz de energia del agente1: " >> agente1.sumarDiagonal();
>> "Celda [1][2] de la matriz de energia: " >> agente1.obtenerCelda(1, 2);

esto potenciaAgente : numerus agente2.potenciaRecursiva(2, 8);
>> "Agente2 calculo 2^8 de forma recursiva: " >> potenciaAgente;

si (agente1.esMasFuerteQue(agente2) == verum) {
	>> "El agente1 es mas fuerte que el agente2";
} aliter {
	>> "El agente2 es mas fuerte o igual que el agente1";
} finis;

## --- Los tres tipos de ciclo, con perge/interrumpe --- ##
per (esto i : numerus 0; i < 10; i++) {
	si (i == 3) {
		perge;
	} finis;
	si (i == 8) {
		interrumpe;
	} finis;
	>> "Iterando con for, i vale: " >> i;
}

esto contador : numerus 0;
dum (contador < 5) {
	contador = contador + 1;
	si (contador == 2) {
		perge;
	} finis;
	>> "Iterando con while, contador vale: " >> contador;
} finis;

esto intentos : numerus 0;
facere {
	intentos++;
	>> "Intento numero: " >> intentos;
	si (intentos == 4) {
		interrumpe;
	} finis;
} dum (intentos < 10);

## --- Logicos, asignacion abreviada --- ##
esto edadTexto : textum "menor";
si (edadComandante >= 18) {
	edadTexto = "adulto";
} finis;
>> "Clasificacion del comandante: " >> edadTexto;

banderaCombinada = (edadComandante > 10 && acumulador > 50) || traza == 45;
>> "Bandera combinada resultante: " >> banderaCombinada;

acumulador = acumulador + 100;
acumulador = acumulador - 25;
acumulador = acumulador * 2;
>> "Acumulador final tras operadores de asignacion: " >> acumulador;


>> "=== Fin de la transmision, baliza enviada ===";

FINIS;
