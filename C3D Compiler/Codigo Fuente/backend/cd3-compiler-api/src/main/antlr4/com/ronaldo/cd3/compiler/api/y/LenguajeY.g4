grammar LenguajeY;

tokens {INDENT, DEDENT}
    
/**ANALISIS SINTACTICO**/
lenguaje
    : bloque_estructuras? bloque_funciones
    ;

/**BLOQUE DE ESTRUCTURAS**/
bloque_estructuras
    : ESTRUCTURAS NEWLINE+ estructura*
    ;

estructura
    : ESTRUCTURA ID_STRUCTURA DOS_P atributo_struct* NEWLINE*
    ;

atributo_struct
    : NEWLINE+ tipo_dato_general ID (CORCH_A NUM_ENTERO CORCH_C)?
    ;

tipo_dato_general
    : tipo_dato_primitivo
    | ID_STRUCTURA
    ;

/**BLOQUE DE FUNCIONES**/
bloque_funciones
    : FUNCIONES NEWLINE+ funcion+
    ;

funcion
    : funcion_void NEWLINE*
    | funcion_retorno NEWLINE*
    ;

funcion_void
    : DEFINIR ID PAR_A params? PAR_C DOS_P bloque
    ;

funcion_retorno
    : DEFINIR ID PAR_A params? PAR_C FLECHA tipo_dato_general DOS_P bloque
    ;

params
    : params COMA param
    | param
    ;

param
    : tipo_dato_primitivo ID
    | CORCH_A CORCH_C tipo_dato_primitivo ID
    | LLAVE_A LLAVE_C ID_STRUCTURA ID
    ;

instruccion
    : declaracion NEWLINE
    | fun_leer NEWLINE
    | asignacion NEWLINE
    | suma_resta_abrev NEWLINE
    | inst_si
    | inst_elegir 
    | ciclo_para
    | ciclo_mientras
    | ciclo_hacer NEWLINE
    | fun_imprimir NEWLINE
    | ROMPER NEWLINE
    | CONTINUAR NEWLINE
    | retorno NEWLINE
    ;

bloque
    : NEWLINE INDENT instruccion+ DEDENT
    ;

/**DECLARACION DE VARIABLES: SIMPLES, ARRAYS Y ESTRUCTURAS**/
declaracion
    : dec_var_simple
    | dec_array
    | dec_estruct
    ;

dec_var_simple
    : tipo_dato_primitivo ID (EQ expresion)?
    ;

dec_array
    : tipo_dato_primitivo ID (CORCH_A expresion CORCH_C)+ (EQ LLAVE_A valores_iniciales LLAVE_C)?
    ;

dec_estruct
    : ID_STRUCTURA ID (EQ LLAVE_A valores_iniciales LLAVE_C)?
    ;

/**ASIGNACION**/
asignacion
    : expresion EQ expresion
    ;

valores_iniciales
    : valores_iniciales COMA expresion
    | expresion
    ;

/**CONDICIONAL SI**/
inst_si
    : SI PAR_A expresion PAR_C ENTONCES bloque (bifurcacion)?
    ;

bifurcacion
    : SINO PAR_A expresion PAR_C ENTONCES bloque (bifurcacion)?
    | CONTRARIO bloque
    ;

/**ELEGIR (SWITCH) **/
inst_elegir
    : ELEGIR PAR_A expresion PAR_C DOS_P casos
    ;

casos
    :  NEWLINE INDENT caso+ DEDENT
    ;

caso
    : CASO expresion DOS_P bloque
    | SIEMPRE DOS_P bloque
    ;

/**CICLOS**/

/**PARA (FOR)**/
ciclo_para
    : PARA PAR_A 
            dec_iterador P_COMA expresion P_COMA (suma_resta_abrev | asignacion) 
      PAR_C DOS_P bloque
    ;

dec_iterador
    : tipo_dato_primitivo ID EQ expresion
    | ID EQ expresion
    ;

/**MIENTRAS (WHILE)**/
ciclo_mientras
    : MIENTRAS PAR_A expresion PAR_C HACER bloque
    ;

/**HACER MIENTRAS (DO-WHILE)**/
ciclo_hacer
    : HACER DOS_P bloque MIENTRAS PAR_A expresion PAR_C
    ;

/**FUNCIONES ESPECIALES**/
fun_imprimir
    :IMPRIMIR PAR_A expresion PAR_C 
    ;

fun_leer
    : LEER PAR_A expresion PAR_C
    ;

suma_resta_abrev
    : ID MAS_MAS
    | ID MENOS_MENOS
    ;

retorno
    : RETORNAR expresion
    ;


/**GENERALES**/
expresion
    : PAR_A expresion PAR_C                         # expParentesis
    | expresion PUNTO ID                            # expAcceso
    | expresion CORCH_A expresion CORCH_C           # expIndice
    | fun_leer                                      # expFunLeer
    | NOT expresion                                 # expNot
    | MENOS expresion                               # expNegativo
    | expresion (MULTI | DIV) expresion             # expMultDiv
    | expresion (MAS | MENOS) expresion             # expSuma
    | expresion (MENOR_Q | MENOR_EQ_Q
               | MAYOR_Q | MAYOR_EQ_Q) expresion    # expRelacional
    | expresion (EQ_EQ | NO_EQ) expresion           # expIgualdad
    | expresion AND expresion                       # expAnd
    | expresion OR expresion                        # expOr
    | NUM_ENTERO                                    # expEntero
    | NUM_DECIMAL                                   # expDecimal
    | LIT_CADENA                                    # expCadena
    | CHAR                                          # expChar
    | VERDADERO                                     # expVerdadero
    | FALSO                                         # expFalso
    | ID                                            # expId
    ;

tipo_dato_primitivo
    : ENTERO
    | FLOTANTE
    | CADENA
    | CARACTER
    | BOOL
    ;



/**ANALISIS LEXICO**/

/**Palabras reservadas**/
ESTRUCTURAS: '%estructuras';
FUNCIONES: '%funciones';
ESTRUCTURA: 'estructura';
ENTERO: 'entero';
CADENA: 'cadena';
FLOTANTE: 'flotante';
CARACTER: 'caracter';
BOOL: 'bool';
VERDADERO: 'verdadero';
FALSO: 'falso';
DEFINIR: 'definir';
RETORNAR: 'retornar';
SINO: 'sino';
SI: 'si';
CONTRARIO: 'contrario';
ENTONCES: 'entonces';
IMPRIMIR: 'imprimir';
ELEGIR: 'elegir';
CASO: 'caso';
SIEMPRE: 'siempre';
ROMPER: 'romper';
PARA: 'para';
CONTINUAR: 'continuar';
MIENTRAS: 'mientras';
HACER: 'hacer';
LEER: 'leer';

/**simbolos**/
FLECHA: '->';
MAS:    '+';
MENOS:  '-';
MULTI:  '*';
DIV:    '/';
EQ: '=';
EQ_EQ:  '==';
NO_EQ:  '!=';
MAYOR_Q: '>';
MAYOR_EQ_Q: '>=';
MENOR_Q: '<';
MENOR_EQ_Q: '<=';
AND:    '&&';
OR:     '||';
NOT:    '!';
MAS_MAS:    '++';
MENOS_MENOS: '--';
PUNTO:  '.';
COMA:   ',';
DOS_P:  ':';
P_COMA: ';';
LLAVE_A:    '{';
LLAVE_C:    '}';
CORCH_A:    '[';
CORCH_C:    ']';
PAR_A:  '(';
PAR_C:  ')';

/**Expresiones regulares**/
ID_STRUCTURA: [A-Z][a-zA-Z0-9_]* ;
ID: [a-zA-Z_][a-zA-Z0-9_]* ;
NUM_ENTERO: [0-9]+ ;
NUM_DECIMAL: [0-9]+ '.' [0-9]+;
LIT_CADENA : '"' .*? '"' ;
CHAR : '\'' . '\'' ;



NEWLINE: ('\r'? '\n' | '\r') [ \t]*;
WS: [ \t]+ -> skip;
COMENTARIO_LINEA : '//' ~[\r\n]* -> channel(HIDDEN) ;
COMENTARIO_BLOQUE : '/*' .*? '*/' -> channel(HIDDEN) ;