// definición de estructuras globales, la sección es opcional
%estructuras
estructura Resumen:
    entero maxNota
    entero minNota
    flotante promedioNotas

// definición de funciones, la seccion es obligatoria
%funciones

// Calcula el promedio de un arreglo de 5 notas, recibido por referencia
definir calcularPromedioArreglo([] entero notas) -> flotante :
    entero suma
    suma = 0
    entero i
    para(entero i = 0; i < 5; i++):
        suma = suma + notas[i]
    flotante promedio
    promedio = suma / 5
    retornar promedio

// Encuentra la nota mas alta dentro de un arreglo de 5 notas
definir encontrarMaxima([] entero notas) -> entero :
    entero mayor
    mayor = notas[0]
    entero i
    para(entero i = 1; i < 5; i++):
        si(notas[i] > mayor) entonces
            mayor = notas[i]
    retornar mayor

// Encuentra la nota mas baja dentro de un arreglo de 5 notas
definir encontrarMinima([] entero notas) -> entero :
    entero menor
    menor = notas[0]
    entero i
    para(entero i = 1; i < 5; i++):
        si(notas[i] < menor) entonces
            menor = notas[i]
    retornar menor

// Genera un resumen completo (maximo, minimo y promedio) de una sola vez
definir generarResumen([] entero notas) -> Resumen :
    Resumen r
    r.maxNota = encontrarMaxima(notas)
    r.minNota = encontrarMinima(notas)
    r.promedioNotas = calcularPromedioArreglo(notas)
    retornar r
