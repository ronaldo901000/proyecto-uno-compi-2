// Funciones.y
// Estructuras y funciones de apoyo para la mision de contacto Zetariano

%estructuras
estructura Coordenada:
	entero x
	entero y

estructura Nave:
	cadena nombre
	entero poder
	flotante velocidad
	entero tripulantes[5]
	Coordenada posicion

%funciones

// Funcion sin retorno, con un parametro por valor
definir incrementar(entero valor):
	valor = valor + 1

// Funcion con retorno de tipo entero, calculada de forma recursiva
definir factorial(entero n) -> entero:
	si(n <= 1) entonces
		retornar 1
	contrario
		retornar n * factorial(n - 1)

// Funcion que recibe un arreglo por referencia y devuelve la suma
definir sumarArreglo([] entero numeros) -> entero:
	entero suma = 0
	entero i
	para(i = 0; i < 5; i++):
		suma = suma + numeros[i]
	retornar suma

// Funcion que recibe una estructura por referencia
definir calcularPoder({} Nave nave) -> entero:
	entero poderTotal
	poderTotal = nave.poder * 2
	si(nave.velocidad > 50.0) entonces
		poderTotal = poderTotal + 10
	retornar poderTotal

// Funcion con ciclo mientras y control de flujo (continuar / romper)
definir contarHastaN(entero n) -> entero:
	entero contador = 0
	mientras(contador < n) hacer
		contador++
		si(contador == 3) entonces
			continuar
		si(contador == 8) entonces
			romper
	retornar contador

// Funcion booleana simple para validar edad
definir esMayorDeEdad(entero edad) -> entero:
	entero resultado
	si(edad >= 18) entonces
		resultado = 1
	contrario
		resultado = 0
	retornar resultado
