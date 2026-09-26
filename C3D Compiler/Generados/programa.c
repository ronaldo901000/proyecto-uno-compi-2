/* Generado automaticamente a partir de cuartetas */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
struct Asignatura {
	struct Estudiante** asignados;
	char* nombre;
	bool hayAsignados;
	int totalEstudiantes;
	struct Estudiante** matriz;
};
struct Resumen {
	int maxNota;
	int minNota;
	double promedioNotas;
};
struct Estudiante {
	char* nombre;
	int edad;
	int* notas;
};

double fun_calcularPromedioArreglo(int* notas);
int fun_encontrarMaxima(int* notas);
int fun_encontrarMinima(int* notas);
struct Resumen fun_generarResumen(int* notas);
void metodo_Estudiante_asignarNota_1(struct Estudiante* this, int posicion, int valor);
int* metodo_Estudiante_obtenerNotas_2(struct Estudiante* this);
double metodo_Estudiante_calcularPromedio_3(struct Estudiante* this);
int metodo_Estudiante_notaMasAlta_4(struct Estudiante* this);
int metodo_Estudiante_notaMasBaja_5(struct Estudiante* this);
void metodo_Estudiante_mostrarDatos_6(struct Estudiante* this);
void constructor_Estudiante_1(struct Estudiante* this, char* nombreParametro, int edadParametro);
void constructor_Estudiante_2(struct Estudiante* this);
void metodo_Asignatura_llenarMatriz_1(struct Asignatura* this);
bool metodo_Asignatura_agregarEstudiante_2(struct Asignatura* this, struct Estudiante* nuevo);
double metodo_Asignatura_promedioGeneral_3(struct Asignatura* this);
void metodo_Asignatura_mostrarEstudiantes_4(struct Asignatura* this);
void constructor_Asignatura_1(struct Asignatura* this, char* nombreParametro);
void constructor_Asignatura_2(struct Asignatura* this);

int i;
int opcion;
bool continuar_programa;
char* nombreTemp;
int edadTemp;
int indice;
int notaTemp;
int j;
struct Estudiante** matriz;
int* notasEstudiante;
struct Asignatura* curso;
struct Estudiante* nuevoEstudiante;
struct Estudiante* e;
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


double fun_calcularPromedioArreglo(int* notas) {
	bool t1;
	int t2;
	int t3;
	int t4;
	int suma;
	double promedio;
	int i;
	
	suma = 0;
	i = 0;
	L1:;
	t1 = i < 5;
	if (t1) goto L2;
	goto L4;
	L2:;
	t2 = notas[(int)((int)i)];
	t3 = suma + t2;
	suma = t3;
	L3:;
	i++;
	goto L1;
	L4:;
	t4 = suma / 5;
	promedio = t4;
	return promedio;
}

int fun_encontrarMaxima(int* notas) {
	int t5;
	bool t6;
	int t7;
	bool t8;
	int t9;
	int mayor;
	int i;
	
	t5 = notas[(int)((int)0)];
	mayor = t5;
	i = 1;
	L5:;
	t6 = i < 5;
	if (t6) goto L6;
	goto L8;
	L6:;
	t7 = notas[(int)((int)i)];
	t8 = t7 > mayor;
	if (t8) goto L10;
	goto L9;
	L10:;
	t9 = notas[(int)((int)i)];
	mayor = t9;
	L9:;
	L7:;
	i++;
	goto L5;
	L8:;
	return mayor;
}

int fun_encontrarMinima(int* notas) {
	int t10;
	bool t11;
	int t12;
	bool t13;
	int t14;
	int menor;
	int i;
	
	t10 = notas[(int)((int)0)];
	menor = t10;
	i = 1;
	L11:;
	t11 = i < 5;
	if (t11) goto L12;
	goto L14;
	L12:;
	t12 = notas[(int)((int)i)];
	t13 = t12 < menor;
	if (t13) goto L16;
	goto L15;
	L16:;
	t14 = notas[(int)((int)i)];
	menor = t14;
	L15:;
	L13:;
	i++;
	goto L11;
	L14:;
	return menor;
}

struct Resumen fun_generarResumen(int* notas) {
	int t15;
	int t16;
	double t17;
	struct Resumen r;
	
	t15 = fun_encontrarMaxima(notas);
	r.maxNota = t15;
	t16 = fun_encontrarMinima(notas);
	r.minNota = t16;
	t17 = fun_calcularPromedioArreglo(notas);
	r.promedioNotas = t17;
	return r;
}

void metodo_Estudiante_asignarNota_1(struct Estudiante* this, int posicion, int valor) {
	this->notas[(int)(posicion)] = this->notas[(int)((int)posicion)];
	this->notas[(int)(posicion)] = valor;
	return;
}

int* metodo_Estudiante_obtenerNotas_2(struct Estudiante* this) {
	return this->notas;
}

double metodo_Estudiante_calcularPromedio_3(struct Estudiante* this) {
	bool t18;
	int t19;
	int t20;
	int t21;
	int suma;
	double promedio;
	int i;
	
	suma = 0;
	i = 0;
	i = 0;
	L17:;
	t18 = i < 5;
	if (t18) goto L18;
	goto L20;
	L18:;
	t19 = this->notas[(int)((int)i)];
	t20 = suma + t19;
	suma = t20;
	L19:;
	i++;
	goto L17;
	L20:;
	t21 = suma / 5;
	promedio = t21;
	return promedio;
}

int metodo_Estudiante_notaMasAlta_4(struct Estudiante* this) {
	int t22;
	bool t23;
	int t24;
	bool t25;
	int t26;
	int mayor;
	int i;
	
	t22 = this->notas[(int)((int)0)];
	mayor = t22;
	i = 0;
	i = 1;
	L21:;
	t23 = i < 5;
	if (t23) goto L22;
	goto L24;
	L22:;
	t24 = this->notas[(int)((int)i)];
	t25 = t24 > mayor;
	if (t25) goto L26;
	goto L25;
	L26:;
	t26 = this->notas[(int)((int)i)];
	mayor = t26;
	L25:;
	L23:;
	i++;
	goto L21;
	L24:;
	return mayor;
}

int metodo_Estudiante_notaMasBaja_5(struct Estudiante* this) {
	int t27;
	bool t28;
	int t29;
	bool t30;
	int t31;
	int menor;
	int i;
	
	t27 = this->notas[(int)((int)0)];
	menor = t27;
	i = 0;
	i = 1;
	L27:;
	t28 = i < 5;
	if (t28) goto L28;
	goto L30;
	L28:;
	t29 = this->notas[(int)((int)i)];
	t30 = t29 < menor;
	if (t30) goto L32;
	goto L31;
	L32:;
	t31 = this->notas[(int)((int)i)];
	menor = t31;
	L31:;
	L29:;
	i++;
	goto L27;
	L30:;
	return menor;
}

void metodo_Estudiante_mostrarDatos_6(struct Estudiante* this) {
	char* t32;
	char* t33;
	double t34;
	char* t35;
	
	t32 = cad_concat("Nombre: ", this->nombre);
	printf("%s\n", t32);
	t33 = cad_concat("Edad: ", cad_numero(this->edad));
	printf("%s\n", t33);
	t34 = metodo_Estudiante_calcularPromedio_3(this);
	t35 = cad_concat("Promedio: ", cad_numero(t34));
	printf("%s\n", t35);
	return;
}

void constructor_Estudiante_1(struct Estudiante* this, char* nombreParametro, int edadParametro) {
	
	this->nombre = nombreParametro;
	this->edad = edadParametro;
	this->notas = (int*)calloc(5, sizeof(int));
	return;
}

void constructor_Estudiante_2(struct Estudiante* this) {
	
	this->nombre = "Sin nombre";
	this->edad = 0;
	this->notas = (int*)calloc(5, sizeof(int));
	return;
}

void metodo_Asignatura_llenarMatriz_1(struct Asignatura* this) {
	struct Estudiante* t39;
	struct Estudiante* t40;
	struct Estudiante* t41;
	struct Estudiante* t42;
	
	this->matriz = (struct Estudiante**)calloc(4, sizeof(struct Estudiante*));
	t39 = (struct Estudiante*)calloc(1, sizeof(struct Estudiante));
	constructor_Estudiante_1(t39, "A", 10);
	this->matriz[(int)((0 * 2))] = this->matriz[(int)(((int)0 * 2))];
	this->matriz[(int)((0 * 2) + 0)] = this->matriz[(int)((int)((0 * 2)) + (int)0)];
	this->matriz[(int)((0 * 2) + 0)] = t39;
	t40 = (struct Estudiante*)calloc(1, sizeof(struct Estudiante));
	constructor_Estudiante_1(t40, "B", 20);
	this->matriz[(int)((0 * 2))] = this->matriz[(int)(((int)0 * 2))];
	this->matriz[(int)((0 * 2) + 1)] = this->matriz[(int)((int)((0 * 2)) + (int)1)];
	this->matriz[(int)((0 * 2) + 1)] = t40;
	t41 = (struct Estudiante*)calloc(1, sizeof(struct Estudiante));
	constructor_Estudiante_1(t41, "C", 30);
	this->matriz[(int)((1 * 2))] = this->matriz[(int)(((int)1 * 2))];
	this->matriz[(int)((1 * 2) + 0)] = this->matriz[(int)((int)((1 * 2)) + (int)0)];
	this->matriz[(int)((1 * 2) + 0)] = t41;
	t42 = (struct Estudiante*)calloc(1, sizeof(struct Estudiante));
	constructor_Estudiante_1(t42, "D", 40);
	this->matriz[(int)((1 * 2))] = this->matriz[(int)(((int)1 * 2))];
	this->matriz[(int)((1 * 2) + 1)] = this->matriz[(int)((int)((1 * 2)) + (int)1)];
	this->matriz[(int)((1 * 2) + 1)] = t42;
	return;
}

bool metodo_Asignatura_agregarEstudiante_2(struct Asignatura* this, struct Estudiante* nuevo) {
	bool t43;
	
	t43 = this->totalEstudiantes == 5;
	if (t43) goto L35;
	goto L34;
	L35:;
	printf("%s", "El curso ya esta lleno, no se pueden agregar mas estudiantes");
	goto L33;
	L34:;
	this->asignados[(int)(this->totalEstudiantes)] = this->asignados[(int)((int)this->totalEstudiantes)];
	this->asignados[(int)(this->totalEstudiantes)] = nuevo;
	this->totalEstudiantes++;
	this->hayAsignados = 1;
	L33:;
	return this->hayAsignados;
}

double metodo_Asignatura_promedioGeneral_3(struct Asignatura* this) {
	bool t44;
	struct Estudiante* t45;
	double t46;
	double t47;
	double t48;
	double suma;
	double resultado;
	int i;
	
	suma = 0.0;
	i = 0;
	i = 0;
	L36:;
	t44 = i < this->totalEstudiantes;
	if (t44) goto L37;
	goto L39;
	L37:;
	t45 = this->asignados[(int)((int)i)];
	t46 = metodo_Estudiante_calcularPromedio_3(t45);
	t47 = suma + t46;
	suma = t47;
	L38:;
	i++;
	goto L36;
	L39:;
	t48 = suma / this->totalEstudiantes;
	resultado = t48;
	return resultado;
}

void metodo_Asignatura_mostrarEstudiantes_4(struct Asignatura* this) {
	bool t49;
	char* t50;
	char* t51;
	struct Estudiante* t52;
	int i;
	
	i = 0;
	i = 0;
	L40:;
	t49 = i < this->totalEstudiantes;
	if (t49) goto L41;
	goto L43;
	L41:;
	t50 = cad_concat("--- Estudiante ", cad_numero(i));
	t51 = cad_concat(t50, " ---");
	printf("%s\n", t51);
	t52 = this->asignados[(int)((int)i)];
	metodo_Estudiante_mostrarDatos_6(t52);
	L42:;
	i++;
	goto L40;
	L43:;
	return;
}

void constructor_Asignatura_1(struct Asignatura* this, char* nombreParametro) {
	
	this->nombre = nombreParametro;
	this->asignados = (struct Estudiante**)calloc(5, sizeof(struct Estudiante*));
	this->hayAsignados = 0;
	this->totalEstudiantes = 0;
	return;
}

void constructor_Asignatura_2(struct Asignatura* this) {
	
	this->nombre = "Sin asignar";
	this->asignados = (struct Estudiante**)calloc(5, sizeof(struct Estudiante*));
	this->hayAsignados = 0;
	this->totalEstudiantes = 0;
	return;
}

int main() {
	struct Asignatura* t56;
	bool t57;
	bool t58;
	struct Estudiante* t59;
	bool t60;
	bool t62;
	bool t63;
	struct Estudiante* t64;
	double t65;
	bool t66;
	struct Estudiante* t67;
	int t68;
	struct Estudiante* t69;
	int t70;
	bool t71;
	double t72;
	bool t73;
	bool t75;
	struct Estudiante* t76;
	int* t77;
	double t78;
	int t79;
	int t80;
	bool t81;
	bool t83;
	bool t84;
	struct Estudiante** t85;
	struct Estudiante* t86;
	
	t56 = (struct Asignatura*)calloc(1, sizeof(struct Asignatura));
	constructor_Asignatura_1(t56, "Matematicas");
	curso = t56;
	opcion = 0;
	continuar_programa = 0;
	nombreTemp = "";
	edadTemp = 0;
	indice = 0;
	notaTemp = 0;
	printf("%s\n", "Bienvenido al sistema de gestion de la asignatura Matematicas");
	L44:;
	t57 = continuar_programa == 0;
	if (t57) goto L45;
	goto L46;
	L45:;
	printf("%s\n", "-------------------------------------");
	printf("%s\n", "1. Agregar estudiante");
	printf("%s\n", "2. Ver promedio de un estudiante");
	printf("%s\n", "3. Ver nota mas alta y mas baja de un estudiante");
	printf("%s\n", "4. Ver promedio general del curso");
	printf("%s\n", "5. Listar estudiantes");
	printf("%s\n", "6. Ver resumen de un estudiante (usando Funciones.y)");
	printf("%s\n", "7. Salir");
	printf("%s\n", "Ingresa una opcion");
	scanf("%d", &opcion);
	t58 = opcion == 1;
	if (t58) goto L49;
	goto L48;
	L49:;
	printf("%s\n", "Ingresa el nombre del estudiante");
	nombreTemp = (char*)malloc(256 * sizeof(char));
	scanf("%s", nombreTemp);
	printf("%s\n", "Ingresa la edad del estudiante");
	scanf("%d", &edadTemp);
	t59 = (struct Estudiante*)calloc(1, sizeof(struct Estudiante));
	constructor_Estudiante_1(t59, nombreTemp, edadTemp);
	nuevoEstudiante = t59;
	j = 0;
	L50:;
	t60 = j < 5;
	if (t60) goto L51;
	goto L53;
	L51:;
	printf("%s", "Ingresa la nota numero");
	printf("%d\n", j);
	scanf("%d", &notaTemp);
	metodo_Estudiante_asignarNota_1(nuevoEstudiante, j, notaTemp);
	L52:;
	j++;
	goto L50;
	L53:;
	t62 = metodo_Asignatura_agregarEstudiante_2(curso, nuevoEstudiante);
	printf("%s\n", "Estudiante agregado con exito");
	goto L47;
	L48:;
	t63 = opcion == 2;
	if (t63) goto L55;
	goto L54;
	L55:;
	printf("%s\n", "Ingresa el indice del estudiante (0 a 4)");
	scanf("%d", &indice);
	printf("%s", "El promedio del estudiante es: ");
	t64 = curso->asignados[(int)((int)indice)];
	t65 = metodo_Estudiante_calcularPromedio_3(t64);
	printf("%g\n", t65);
	goto L47;
	L54:;
	t66 = opcion == 3;
	if (t66) goto L57;
	goto L56;
	L57:;
	printf("%s\n", "Ingresa el indice del estudiante (0 a 4)");
	scanf("%d", &indice);
	printf("%s", "La nota mas alta es: ");
	t67 = curso->asignados[(int)((int)indice)];
	t68 = metodo_Estudiante_notaMasAlta_4(t67);
	printf("%d\n", t68);
	printf("%s", "La nota mas baja es: ");
	t69 = curso->asignados[(int)((int)indice)];
	t70 = metodo_Estudiante_notaMasBaja_5(t69);
	printf("%d\n", t70);
	goto L47;
	L56:;
	t71 = opcion == 4;
	if (t71) goto L59;
	goto L58;
	L59:;
	printf("%s", "El promedio general del curso es: ");
	t72 = metodo_Asignatura_promedioGeneral_3(curso);
	printf("%g\n", t72);
	goto L47;
	L58:;
	t73 = opcion == 5;
	if (t73) goto L61;
	goto L60;
	L61:;
	metodo_Asignatura_mostrarEstudiantes_4(curso);
	goto L47;
	L60:;
	t75 = opcion == 6;
	if (t75) goto L63;
	goto L62;
	L63:;
	printf("%s\n", "Ingresa el indice del estudiante (0 a 4)");
	scanf("%d", &indice);
	t76 = curso->asignados[(int)((int)indice)];
	t77 = metodo_Estudiante_obtenerNotas_2(t76);
	notasEstudiante = t77;
	printf("%s", "Promedio (Funciones.y): ");
	t78 = fun_calcularPromedioArreglo(notasEstudiante);
	printf("%g\n", t78);
	printf("%s", "Nota maxima (Funciones.y): ");
	t79 = fun_encontrarMaxima(notasEstudiante);
	printf("%d\n", t79);
	printf("%s", "Nota minima (Funciones.y): ");
	t80 = fun_encontrarMinima(notasEstudiante);
	printf("%d\n", t80);
	goto L47;
	L62:;
	t81 = opcion == 7;
	if (t81) goto L65;
	goto L64;
	L65:;
	continuar_programa = 1;
	printf("%s\n", "Hasta luego cadete");
	goto L47;
	L64:;
	printf("%s\n", "Opcion no valida, intenta de nuevo");
	L47:;
	goto L44;
	L46:;
	metodo_Asignatura_llenarMatriz_1(curso);
	matriz = curso->matriz;
	i = 0;
	L66:;
	t83 = i < 2;
	if (t83) goto L67;
	goto L69;
	L67:;
	j = 0;
	L70:;
	t84 = j < 2;
	if (t84) goto L71;
	goto L73;
	L71:;
	t85 = matriz + (int)(((int)i * 2));
	t86 = t85[(int)j];
	e = t86;
	metodo_Estudiante_mostrarDatos_6(e);
	L72:;
	j++;
	goto L70;
	L73:;
	L68:;
	i++;
	goto L66;
	L69:;
	return 0;
}

