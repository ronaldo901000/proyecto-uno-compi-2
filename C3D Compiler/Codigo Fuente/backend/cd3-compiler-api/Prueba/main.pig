import Prueba.Punto.z
import Prueba.Matematica.z
import Prueba.Contenedor.z
import Prueba.Nodo.z
import Prueba.Pila.z
import Prueba.utils.pruebas.y

VARIABILES>
esto p1 : novus Punto(3, 4);
esto p2 : novus Punto();
esto p3 : novus Punto(10, 20);
esto mate : novus Matematica();
esto cont : novus Contenedor();
esto cont2 : novus Contenedor();
esto n1 : novus Nodo(1);
esto n2 : novus Nodo(2);
esto n3 : novus Nodo(3);
esto pila : novus Pila();
series lista[4] : numerus {1, 2, 3, 4};
series notasBase[3] : numerus {70, 80, 90};
esto ana : Persona {"Ana", 20, {"Calle 1", 10}, notasBase};
esto valor : numerus 5;
esto resultado : numerus 0;
esto i : numerus 100;
esto dato : numerus 42;
esto tamanio : numerus 7;
esto cima : numerus 0;

MAIOR>
>> "== 1. constructores sobrecargados ==";
>> p1.getX() >> "," >> p1.getY();
>> p2.getX() >> "," >> p2.getY();

>> "== 2. objeto como parametro ==";
>> p1.restarX(p3);
>> p3.restarX(p1);
>> p1.sumar(5);
>> p1.sumar(p3);
>> p1.esIgual(p3);
>> p1.esIgual(p1);

>> "== 3. recursividad ==";
>> mate.factorial(5);
>> mate.fibonacci(5);
>> mate.getLlamadas();
>> mate.esPar(10);
>> mate.esImpar(7);
>> mate.esPar(7);

>> "== 4. arreglos y objetos ==";
cont.agregar(10);
cont.agregar(20);
cont.agregar(30);
cont2.agregar(1);
>> cont.sumarDatos();
>> cont2.sumarDatos();
cont.llenarMatriz();
>> cont.getMatriz(1, 3);
>> cont.getMatriz(0, 3);
>> cont.hayPunto(0);
cont.guardarPunto(0, p1);
>> cont.hayPunto(0);
>> cont.getPunto(0).getX();
p1.x = 99;
>> cont.getPunto(0).getX();

>> "== 5. cadena de objetos ==";
n1.setSiguiente(n2);
n2.setSiguiente(n3);
>> n1.siguiente.siguiente.dato;
n1.siguiente.siguiente.dato = 30;
>> n3.getDato();

>> "== 6. pila y colision de nombres ==";
>> pila.obtenerCima();
pila.apilar(1);
pila.apilar(2);
pila.apilar(3);
>> pila.toString();
resultado = pila.desapilar();
>> resultado;
>> pila.toString();
>> pila.obtenerTamanio();
>> dato >> " " >> tamanio >> " " >> cima;

>> "== 7. funciones .y ==";
>> sumarArreglo(lista, 4);
>> i;
duplicar(lista, 4);
>> sumarArreglo(lista, 4);
>> lista[3];
resultado = incrementarValor(valor);
>> valor >> " " >> resultado;
>> potencia(2, 10);
>> controles();
>> contarHasta(5);
>> nombreOpcion(2);
>> nombreOpcion(9);
>> describir(7);
>> esHola("hola");
>> esHola("adios");
>> copiaPersona();
cumplirAnios(ana);
>> ana.edad;
>> ana.domicilio.numero;
>> promedioNotas(ana);
>> ana.notas[1];
FINIS;
