// definición de estructuras globales
%estructuras

estructura Direccion:
    cadena calle
    entero numero

estructura Persona:
    cadena nombre
    entero edad
    flotante promedio
    caracter inicial
    entero notas[5]
    Direccion domicilio

%funciones

// Función sin retorno para actualizar datos del usuario
definir actualizarEdad (entero edadNueva):
    edadNueva = edadNueva + 1

// Función para calcular un total con retorno de entero
definir calcularPoder (entero fuerza) -> entero :
    entero resultado
    resultado = fuerza * 2
    retornar resultado

// Función demostrativa con estructuras de control y arreglos
definir procesarDatos (entero limite) -> entero :
    entero contador = 0
    entero numeros[5] = {10, 20, 30, 40, 50}
    
    si(limite > 0) entonces
        imprimir("Procesando datos...")
    sino (limite == 0) entonces
        imprimir("Limite en cero")
    contrario
        imprimir("Limite negativo")
        

    mientras (contador < 5) hacer
        si(contador == 3) entonces
            contador++
            continuar
        numeros[contador] = numeros[contador] * 2
        contador++
        
    elegir (limite):
        caso 1:
            imprimir("Opcion 1")
            romper
        caso 2:
            imprimir("Opcion 2")
            romper
        siempre:
            imprimir("Opcion por defecto")
            romper

    retornar contador
