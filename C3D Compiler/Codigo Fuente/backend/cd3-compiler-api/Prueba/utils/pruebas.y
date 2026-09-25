%estructuras
estructura Direccion:
    cadena calle
    entero numero

estructura Persona:
    cadena nombre
    entero edad
    Direccion domicilio
    entero notas[3]

%funciones
definir sumarArreglo([] entero arr, entero n) -> entero :
    entero suma = 0
    para(entero i = 0; i < n; i++):
        suma = suma + arr[i]
    retornar suma

definir duplicar([] entero arr, entero n):
    para(entero i = 0; i < n; i++):
        arr[i] = arr[i] * 2

definir incrementarValor(entero x) -> entero :
    x = x + 1
    retornar x

definir potencia(entero base, entero expo) -> entero :
    si(expo == 0) entonces
        retornar 1
    retornar base * potencia(base, expo - 1)

definir controles() -> entero :
    entero acumulado = 0
    para(entero i = 0; i < 10; i++):
        si(i == 3) entonces
            continuar
        si(i == 8) entonces
            romper
        acumulado = acumulado + i
    retornar acumulado

definir contarHasta(entero limite) -> entero :
    entero c = 0
    mientras(c < limite) hacer
        c++
    entero intentos = 0
    hacer:
        intentos++
    mientras(intentos < 3)
    retornar c + intentos

definir nombreOpcion(entero opcion) -> cadena :
    cadena r = "ninguna"
    elegir(opcion) :
        caso 1:
            r = "uno"
            romper
        caso 2:
            r = "dos"
            romper
        siempre:
            r = "otra"
            romper
    retornar r

definir describir(entero n) -> cadena :
    retornar "Numero: " + n

definir esHola(cadena s) -> bool :
    retornar s == "hola"

definir copiaPersona() -> entero :
    Persona a
    Persona b
    a.edad = 10
    b = a
    b.edad = 20
    retornar a.edad

definir cumplirAnios({} Persona p):
    p.edad = p.edad + 1
    p.domicilio.numero = 99

definir promedioNotas({} Persona p) -> flotante :
    entero total = 0
    para(entero i = 0; i < 3; i++):
        total = total + p.notas[i]
    retornar total / 3
