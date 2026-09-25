// Utilidades.y
// Libreria de estructuras y funciones para el contacto Zetariano
// Prueba: recursividad simple, recursividad mutua, arreglos por
// referencia, matrices aplanadas 3x3, estructuras anidadas.

%estructuras
estructura Punto3D:
	entero x
	entero y
	entero z

estructura Persona:
	cadena nombre
	entero edad
	entero notas[5]

estructura Caja:
	Punto3D esquina
	entero volumen

%funciones

// ---------- Recursividad simple ----------
definir factorial(entero n) -> entero :
	si(n < 2) entonces
		retornar 1
	contrario
		retornar n * factorial(n - 1)

definir fibonacci(entero n) -> entero :
	si(n < 2) entonces
		retornar n
	contrario
		retornar fibonacci(n - 1) + fibonacci(n - 2)

definir mcd(entero a, entero b) -> entero :
	si(b == 0) entonces
		retornar a
	contrario
		retornar mcd(b, a * b)

// ---------- Recursividad mutua ----------
definir esPar(entero n) -> bool :
	si(n == 0) entonces
		retornar verdadero
	contrario
		retornar esImpar(n - 1)

definir esImpar(entero n) -> bool :
	si(n == 0) entonces
		retornar falso
	contrario
		retornar esPar(n - 1)

// ---------- Arreglos pasados por referencia + recursividad ----------
definir sumaRecursiva([] entero arr, entero n) -> entero :
	si(n == 0) entonces
		retornar 0
	contrario
		retornar arr[n - 1] + sumaRecursiva(arr, n - 1)

definir busquedaBinaria([] entero arr, entero izq, entero der, entero objetivo) -> entero :
	si(izq > der) entonces
		retornar -1
	entero medio
	medio = (izq + der) / 2
	si(arr[medio] == objetivo) entonces
		retornar medio
	sino (arr[medio] < objetivo) entonces
		retornar busquedaBinaria(arr, medio + 1, der, objetivo)
	contrario
		retornar busquedaBinaria(arr, izq, medio - 1, objetivo)

definir maximoRecursivo([] entero arr, entero n) -> entero :
	si(n == 1) entonces
		retornar arr[0]
	entero maximoResto
	maximoResto = maximoRecursivo(arr, n - 1)
	si(arr[n - 1] > maximoResto) entonces
		retornar arr[n - 1]
	contrario
		retornar maximoResto

// ---------- Matrices aplanadas (arreglo 1D representando NxN) ----------
definir llenarMatriz([] entero m, entero filas, entero columnas):
	entero i
	entero j
	para(entero i = 0; i < filas; i++):
		para(entero j = 0; j < columnas; j++):
			m[(i * columnas) + j] = (i + 1) * (j + 1)

definir sumarMatrices([] entero a, [] entero b, [] entero resultado, entero total):
	entero i
	para(entero i = 0; i < total; i++):
		resultado[i] = a[i] + b[i]

definir multiplicarMatrices([] entero a, [] entero b, [] entero resultado, entero n):
	entero i
	entero j
	entero k
	entero suma
	para(entero i = 0; i < n; i++):
		para(entero j = 0; j < n; j++):
			suma = 0
			para(entero k = 0; k < n; k++):
				suma = suma + (a[(i * n) + k] * b[(k * n) + j])
			resultado[(i * n) + j] = suma

definir trazaMatriz([] entero m, entero n) -> entero :
	entero suma
	entero i
	suma = 0
	para(entero i = 0; i < n; i++):
		suma = suma + m[(i * n) + i]
	retornar suma

// ---------- Estructuras por referencia + recursividad ----------
definir promedioNotas({} Persona p) -> flotante :
	entero total
	entero i
	total = 0
	para(entero i = 0; i < 5; i++):
		total = total + p.notas[i]
	retornar total / 5

definir pasosHaciaOrigen({} Punto3D punto, entero pasos) -> entero :
	si(punto.x == 0 && punto.y == 0 && punto.z == 0) entonces
		retornar pasos
	si(punto.x != 0) entonces
		punto.x = punto.x - 1
	sino (punto.y != 0) entonces
		punto.y = punto.y - 1
	contrario
		punto.z = punto.z - 1
	retornar pasosHaciaOrigen(punto, pasos + 1)

// ---------- Ciclos y control de flujo variados ----------
definir contarConSaltos(entero limite) -> entero :
	entero total
	entero i
	total = 0
	i = 0
	mientras(i < limite) hacer
		i++
		si(i == 3) entonces
			continuar
		si(i == 9) entonces
			romper
		total = total + i

	entero intentos
	intentos = 0
	hacer:
		intentos++
		si(intentos == 2) entonces
			romper
	mientras(intentos < 5)

	retornar total + intentos

definir clasificarOpcion(entero opcion) -> cadena :
	cadena resultado
	elegir(opcion) :
		caso 1:
			resultado = "uno"
			romper
		caso 2:
			resultado = "dos"
			romper
		siempre:
			resultado = "desconocida"
			romper
	retornar resultado
