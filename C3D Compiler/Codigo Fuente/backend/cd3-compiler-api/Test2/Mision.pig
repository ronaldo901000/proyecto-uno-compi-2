##
	Importaciones de los otros lenguajes
##
import Test2.funciones.Funciones.y
import Test2.clases.Explorador.z

##
	Seccion opcional de variables
	Variables, arreglos o estructuras globales
##
VARIABILES>
esto comandante : textum "Comandante Rivas";
esto edadComandante : numerus 0;
esto poderTotal : numerus 0;
esto activado : falsus;
esto miNave : Nave {"Halcon Fugaz", 75, 62.5, {1, 2, 3, 4, 5}, {10, 20}};
series listaFactoriales[5] : numerus {0, 0, 0, 0, 0};
esto miExplorador : novus Explorador("Kael", 120);
esto explorador2 : novus Explorador();
esto sumaRegistros : numerus 0;
esto contador : numerus 0;

##
	Seccion de funcion principal
	Esta seccion es obligatoria
##
MAIOR>
>> "Iniciando protocolo de contacto Zetariano..." ;
>> "Ingresa tu edad, comandante" ;
edadComandante <<

si (edadComandante >= 18) {
	activado = verum;
	>> "Acceso concedido" ;
} aliter {
	activado = falsus;
	>> "Acceso denegado, eres muy joven" ;
} finis ;

poderTotal = calcularPoder(miNave);
>> "El poder de la nave es: " >> poderTotal ;

per (esto i : numerus 0; i < 5; i++) {
	listaFactoriales[i] = factorial(i);
	>> "Factorial calculado: " >> listaFactoriales[i] ;
}

miExplorador.activar();
miExplorador.establecerRegistro(0, 42);
miExplorador.establecerRegistro(1, 58);

sumaRegistros = miExplorador.sumarRegistros();
>> "Suma de registros del explorador: " >> sumaRegistros ;

si (miExplorador.esCompatibleCon(explorador2)) {
	>> "Los exploradores son compatibles" ;
} aliter {
	>> "Los exploradores no son compatibles" ;
} finis ;

dum (contador < 10) {
	contador = contador + 1;
	si (contador == 5) {
		perge;
	} finis ;
	si (contador == 8) {
		interrumpe;
	} finis ;
} finis ;

>> "Contador final: " >> contador ;
>> "La puerta esta cifrada?" >> activado ;

FINIS;
