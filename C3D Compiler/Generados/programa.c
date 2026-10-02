/* Generado automaticamente a partir de cuartetas */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
struct Hola {
	char* mensaje;
};

void fun_saludar();
void fun_saludarA(char* nombre);
int fun_sumar(int a, int b);
void metodo_Hola_decir_1(struct Hola* this);
int metodo_Hola_doble_2(struct Hola* this, int n);
void constructor_Hola_1(struct Hola* this);

char* nombre;
struct Hola* objeto;
char* cad_concat(char* izquierdo, char* derecho) {
	if (izquierdo == NULL) izquierdo = "";
	if (derecho == NULL) derecho = "";
	char* resultado = (char*)malloc(strlen(izquierdo) + strlen(derecho) + 1);
	if (resultado == NULL) return NULL;
	strcpy(resultado, izquierdo);
	strcat(resultado, derecho);
	return resultado;
}
char* cad_numero(double valor) {
	char buffer[64];
	snprintf(buffer, sizeof(buffer), "%g", valor);
	char* resultado = (char*)malloc(strlen(buffer) + 1);
	if (resultado == NULL) return NULL;
	strcpy(resultado, buffer);
	return resultado;
}
int cad_igual(char* izquierdo, char* derecho) {
	if (izquierdo == NULL || derecho == NULL) return izquierdo == derecho;
	return strcmp(izquierdo, derecho) == 0;
}
int cad_distinto(char* izquierdo, char* derecho) {
	if (izquierdo == NULL || derecho == NULL) return izquierdo != derecho;
	return strcmp(izquierdo, derecho) != 0;
}


void fun_saludar() {
	printf("%s\n", "Hola Mundo desde Y?");
	return;
}

void fun_saludarA(char* nombre) {
	char* t1;
	
	t1 = cad_concat("Hola ", nombre);
	printf("%s\n", t1);
	return;
}

int fun_sumar(int a, int b) {
	int t2;
	
	t2 = a + b;
	return t2;
}

void metodo_Hola_decir_1(struct Hola* this) {
	printf("%s\n", this->mensaje);
	return;
}

int metodo_Hola_doble_2(struct Hola* this, int n) {
	int t3;
	
	t3 = n * 2;
	return t3;
}

void constructor_Hola_1(struct Hola* this) {
	this->mensaje = "Hola Mundo desde Zetariano";
	return;
}

int main() {
	struct Hola* t4;
	int t8;
	int t9;
	
	t4 = (struct Hola*)calloc(1, sizeof(struct Hola));
	constructor_Hola_1(t4);
	objeto = t4;
	nombre = "Cadete";
	printf("%s\n", "Hola Mundo desde Pig Latin");
	fun_saludar();
	fun_saludarA(nombre);
	metodo_Hola_decir_1(objeto);
	printf("%s", "2 + 3 = ");
	t8 = fun_sumar(2, 3);
	printf("%d\n", t8);
	printf("%s", "El doble de 21 es ");
	t9 = metodo_Hola_doble_2(objeto, 21);
	printf("%d\n", t9);
	return 0;
}

