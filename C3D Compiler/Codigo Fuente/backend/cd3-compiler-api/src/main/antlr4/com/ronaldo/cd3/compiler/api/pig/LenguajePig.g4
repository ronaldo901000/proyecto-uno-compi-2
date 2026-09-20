grammar LenguajePig;

/** ANALISIS SINTACTICO **/

pig
    : importacion* bloque_variabile? bloque_maior
    ;

importacion
    : IMPORT importe P_COMA?
    ;

importe
    : ID (PUNTO ID)*
    ;

bloque_variabile
    : VARIABILES declaracion*
    ;

declaracion
    : ( dec_var
      | asignacion
      ) P_COMA+
    ;

// Declaraciones de variables, estructuras u objetos
dec_var
    : ESTO ID (DOS_P (tipo_dato | ID)? expresion)?
    | SERIES ID CORCH_A expresion CORCH_C DOS_P? (tipo_dato | ID)? expresion?
    ;

tipo_dato
    : NUMERUS
    | DECIMALIS
    | TEXTUM
    | LITTERA
    | BOOL
    ;

// Asignacion
asignacion
    : lvalue EQ expresion
    ;

lvalue
    : ID
    | lvalue CORCH_A expresion CORCH_C
    | lvalue PUNTO ID
    ;

argumentos
    : expresion (COMA expresion)*
    ;

instanciacion
    : NOVUS ID PAR_A argumentos? PAR_C
    ;

bloque_maior
    : MAIOR instruccion* (FINIS | FINIS_MAY) P_COMA?
    ;

instruccion
    : condicional
    | ciclo_simple
    | ciclo_do_while
    | ciclo_iterador
    | operacion_abrev P_COMA?
    | declaracion
    | asignacion P_COMA?
    | fun_lectura
    | fun_lectura_guardado
    | expresion P_COMA? 
    | fun_impresion
    | PERGE P_COMA?
    | INTERRUMPE P_COMA?
    ;


/** CONDICIONALES **/

condicional
    : SI PAR_A expresion PAR_C LLAVE_A instruccion* LLAVE_C
      rama_aliter*
      (FINIS | FINIS_MAY) P_COMA?
    ;

rama_aliter
    : ALITER PAR_A expresion PAR_C LLAVE_A instruccion* LLAVE_C
    | ALITER LLAVE_A instruccion* LLAVE_C
    ;

/** CICLOS **/

ciclo_simple
    : DUM PAR_A expresion PAR_C LLAVE_A instruccion* LLAVE_C (FINIS | FINIS_MAY) P_COMA?
    ;

ciclo_do_while
    : FACERE LLAVE_A instruccion* LLAVE_C DUM PAR_A expresion PAR_C P_COMA?
    ;

ciclo_iterador
    : PER PAR_A dec_var_sin_pcoma P_COMA expresion P_COMA expresion_iterador PAR_C LLAVE_A instruccion* LLAVE_C
    ;

dec_var_sin_pcoma
    : ESTO ID DOS_P? (tipo_dato | ID)? expresion?
    ;

expresion_iterador
    : expresion
    | operacion_abrev
    | asignacion
    ;

operacion_abrev
    : ID MAS_MAS
    | ID MENOS_MENOS
    ;

llamada_metodo
    : ID PAR_A argumentos? PAR_C
    ;

// Lectura por consola
fun_lectura
    : MENOR_Q MENOR_Q P_COMA?
    ;

fun_lectura_guardado
    : lvalue MENOR_Q MENOR_Q P_COMA?
    ;

// Impresion en consola encadenada
fun_impresion
    : (MAYOR_Q MAYOR_Q expresion)+ P_COMA?
    ;

/** EXPRESIONES **/

expresion
    : PAR_A expresion PAR_C                         # expParentesis
    | llamada_metodo                                # expLlamada
    | expresion PUNTO llamada_metodo                # expLlamadaMetodo
    | expresion PUNTO ID                            # expAcceso
    | expresion CORCH_A expresion CORCH_C           # expIndice
    | instanciacion                                 # expInstanciacion
    | LLAVE_A argumentos? LLAVE_C                   # expArgStruct
    | NON expresion                                 # expNot
    | MENOS expresion                               # expNegativo
    | expresion (MULTI | DIV) expresion             # expMultiDiv
    | expresion (MAS | MENOS) expresion             # expSumaResta
    | expresion (MENOR_Q | MENOR_EQ_Q
               | MAYOR_Q | MAYOR_EQ_Q) expresion    # expRelacional
    | expresion (EQ_EQ | NO_EQ) expresion           # expIgualdad
    | expresion AND expresion                       # expAnd
    | expresion OR expresion                        # expOr
    | ENTERO                                        # expEntero
    | DECIMAL                                       # expDecimal
    | CADENA                                        # expCadena
    | CHAR                                          # expChar
    | VERUM                                         # expVerdadero
    | FALSUS                                        # expFalso
    | ID                                             # expId
    ;

/** ANALISIS LEXICO **/

/** Palabras reservadas **/
VARIABILES: 'VARIABILES>';
MAIOR:      'MAIOR>';
IMPORT:     'import';
ESTO:       'esto';
SERIES:     'series';
NOVUS:      'novus';
NUMERUS:    'numerus';
DECIMALIS:  'decimalis';
TEXTUM:     'textum';
LITTERA:    'littera';
BOOL:       'bool';
VERUM:      'verum';
FALSUS:     'falsus';
SI:         'si';
FINIS:      'finis';
FINIS_MAY:  'FINIS';
ALITER:     'aliter';
DUM:        'dum';
FACERE:     'facere';
PER:        'per';
PERGE:      'perge';
INTERRUMPE: 'interrumpe';
NON:        'non';

/** Simbolos **/
MAS:         '+';
MENOS:       '-';
MULTI:       '*';
DIV:         '/';
EQ:          '=';
EQ_EQ:       '==';
NO_EQ:       '!=';
MAYOR_Q:     '>';
MAYOR_EQ_Q:  '>=';
MENOR_Q:     '<';
MENOR_EQ_Q:  '<=';
AND:         '&&';
OR:          '||';
MAS_MAS:     '++';
MENOS_MENOS: '--';
PUNTO:       '.';
COMA:        ',';
DOS_P:       ':';
P_COMA:      ';';
LLAVE_A:     '{';
LLAVE_C:     '}';
CORCH_A:     '[';
CORCH_C:     ']';
PAR_A:       '(';
PAR_C:       ')';

// Expresiones regulares
ID:                [a-zA-Z_][a-zA-Z0-9_]* ;
ENTERO:            [0-9]+ ;
DECIMAL:           [0-9]+ '.' [0-9]+ ;
CADENA:            '"' .*? '"' ;
CHAR:              '\'' . '\'' ;
COMENTARIO_LINEA:  '//' ~[\r\n]* -> channel(HIDDEN) ;
COMENTARIO_BLOQUE: '##' .*? '##' -> channel(HIDDEN) ;
WS:                [ \t\n\r\f\u00A0\u200B]+ -> skip ;