#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

struct Nodo {
	int dato;
	struct Nodo* siguiente;
};
struct Pila {
	struct Nodo* cima;
	int tamanio;
};

/* prototipos: todos reciben this */
int  metodo_Nodo_getDato_1(struct Nodo* this);
void metodo_Nodo_setDato_2(struct Nodo* this, int dato1);
struct Nodo* metodo_Nodo_getSiguiente_3(struct Nodo* this);
void metodo_Nodo_setSiguiente_4(struct Nodo* this, struct Nodo* siguiente1);
void constructor_Nodo_1(struct Nodo* this, int dato1);

void metodo_Pila_apilar_1(struct Pila* this, int dato);
int  metodo_Pila_desapilar_2(struct Pila* this);
bool metodo_Pila_estaVacia_4(struct Pila* this);
void constructor_Pila_1(struct Pila* this);

/* ---------- Nodo ---------- */
int metodo_Nodo_getDato_1(struct Nodo* this) {
	return this->dato;
}

void metodo_Nodo_setDato_2(struct Nodo* this, int dato1) {
	this->dato = dato1;
}

struct Nodo* metodo_Nodo_getSiguiente_3(struct Nodo* this) {
	return this->siguiente;
}

void metodo_Nodo_setSiguiente_4(struct Nodo* this, struct Nodo* siguiente1) {
	this->siguiente = siguiente1;
}

void constructor_Nodo_1(struct Nodo* this, int dato1) {
	this->dato = dato1;
	this->siguiente = 0;
}

/* ---------- Pila ---------- */
void constructor_Pila_1(struct Pila* this) {
	this->cima = 0;
	this->tamanio = 0;
}

void metodo_Pila_apilar_1(struct Pila* this, int dato) {   /* dato es LOCAL */
	struct Nodo* t7;
	struct Nodo* nuevo;                                    /* local */

	t7 = (struct Nodo*)calloc(1, sizeof(struct Nodo));     /* heap */
	constructor_Nodo_1(t7, dato);                          /* inicializa t7 de verdad */
	nuevo = t7;
	metodo_Nodo_setSiguiente_4(nuevo, this->cima);
	this->cima = nuevo;
	this->tamanio++;
}

int metodo_Pila_desapilar_2(struct Pila* this) {
	int dato;                                              /* local, ya no choca con Nodo.dato */
	struct Nodo* t10;

	dato = metodo_Nodo_getDato_1(this->cima);
	t10 = metodo_Nodo_getSiguiente_3(this->cima);
	this->cima = t10;
	this->tamanio--;
	return dato;
}

bool metodo_Pila_estaVacia_4(struct Pila* this) {
	return this->cima == 0;
}
