grammar LenguajeZ;


/**ANALISIS SINTACTICO**/
programa
    : clase EOF
    ;

clase
    : PUBLIC CLASS ID LLAVE_A contenido LLAVE_C
    ;
    
contenido
    : (atributo | constructor |metodo)*
    ;


/**ATRIBUTO**/
atributo
    : declaracion P_COMA
    ;

declaracion
    : dec_var_simple
    | dec_array
    ;

dec_var_simple
    : tipo_dato_general ID (EQ expresion)?
    ;

dec_array
    : tipo_dato_general 
    (CORCH_A expresion? CORCH_C)+ 
    ID 
    (EQ ( LLAVE_A valores_iniciales? LLAVE_C 
        | NEW tipo_dato_general (CORCH_A expresion CORCH_C)+ ))?
    ;

valores_iniciales
    : expresion (COMA expresion)*
    ;

/**CONSTRUCTOR**/
constructor
    : PUBLIC ID PAR_A parametros? PAR_C LLAVE_A instruccion* LLAVE_C
    ;


instruccion
    :
    (declaracion 
    | asignacion
    | suma_resta_abrev
    | llamada_metodo
    | llamada_metodo_objeto
    | funcion_especial
    | BREAK
    | CONTINUE  
    | return
    ) P_COMA+ 
    | inst_if
    | inst_switch
    | ciclo_for
    | ciclo_while
    | ciclo_do_while
    ;

asignacion
    : lvalue (EQ | MAS_EQ | MENOS_EQ | MULTI_EQ) expresion
    ;

lvalue
    : ID
    | lvalue CORCH_A expresion CORCH_C
    | lvalue PUNTO ID
    ;

return
    : RETURN (expresion)?
    ;


llamada_metodo_objeto
    : expresion PUNTO llamada_metodo
    ;

/**CONDICIONALES**/
inst_if
    : IF PAR_A expresion PAR_C cuerpo_if bifurcacion?
    ;

cuerpo_if
    : instruccion
    | LLAVE_A instruccion* LLAVE_C
    ;

bifurcacion
    : ELSE IF PAR_A expresion PAR_C cuerpo_if bifurcacion?
    | ELSE cuerpo_if
    ;


/**SWITCH**/
inst_switch
    : SWITCH PAR_A expresion PAR_C LLAVE_A casos? LLAVE_C
    ;

casos
    : CASE expresion DOS_P instruccion* casos?
    | DEFAULT DOS_P instruccion*
    ;



/**CICLOS**/
ciclo_for
    : 
    FOR PAR_A 
        dec_iterador? P_COMA 
        expresion? P_COMA 
        (suma_resta_abrev | asignacion)?  
    PAR_C LLAVE_A instruccion* LLAVE_C
    ;

dec_iterador
    : declaracion
    | asignacion
    ;

ciclo_while
    : WHILE PAR_A expresion PAR_C LLAVE_A instruccion* LLAVE_C
    ;

ciclo_do_while
    : DO LLAVE_A instruccion* LLAVE_C WHILE PAR_A expresion PAR_C P_COMA
    ;

/**METODO**/
metodo
    : funcion
    | procedimiento
    ;

/**void**/
procedimiento
    : PUBLIC VOID ID PAR_A parametros? PAR_C LLAVE_A instruccion*  LLAVE_C
    ;

funcion
    : PUBLIC tipo_dato_general ID PAR_A parametros? PAR_C LLAVE_A instruccion* LLAVE_C
    ;

parametros
    : parametro (COMA parametro)*
    ;

parametro
    : tipo_dato_general (CORCH_A  CORCH_C)* ID 
    ;

llamada_metodo
    : ID PAR_A argumentos? PAR_C
    ;

argumentos
    : expresion (COMA expresion)*
    ;


/**FUNCIONES ESPECIALES**/

funcion_especial
    : fun_leer
    | fun_imprimir_con_ln
    | fun_imprimir_sin_ln
    ;

fun_leer
    : READLN PAR_A expresion? PAR_C
    ;

fun_imprimir_con_ln
    : PRINTLN PAR_A expresion? PAR_C
    ;

fun_imprimir_sin_ln
    : PRINT PAR_A expresion PAR_C
    ;  

suma_resta_abrev
    : lvalue MAS_MAS
    | lvalue MENOS_MENOS
    ;


expresion
    : expr_base                                             # expBase
    | expr_base INTERROGACION expresion DOS_P expresion     # expTernaria
    ;

expr_base
    : PAR_A expresion PAR_C                                 # expParentesis
    | llamada_metodo                                        # expLlamada
    | expr_base PUNTO ID                                    # expAcceso
    | expr_base PUNTO llamada_metodo                        # expLlamadaEncadenada
    | expr_base CORCH_A expresion CORCH_C                   # expIndice
    | fun_leer                                              # expFunLeer
    | NOT expr_base                                         # expNot
    | MENOS expr_base                                       # expNegativo
    | MAS expr_base                                         # expPositivo
    | expr_base (MULTI | DIV | MODULO) expr_base            # expMultDivMod
    | expr_base (MAS | MENOS) expr_base                     # expSumaResta
    | expr_base (MENOR_Q | MENOR_EQ_Q 
               | MAYOR_Q | MAYOR_EQ_Q) expr_base            # expRelacional
    | expr_base (EQ_EQ | NO_EQ) expr_base                   # expIgualdad
    | expr_base AND expr_base                               # expAnd
    | expr_base OR expr_base                                # expOr
    | NEW tipo_dato_general PAR_A argumentos? PAR_C         # expNewObjeto
    | NEW tipo_dato_general (CORCH_A expresion CORCH_C)+    # expNewArreglo
    | ENTERO                                                # expEntero
    | DECIMAL                                               # expDecimal
    | CADENA                                                # expCadena
    | LIT_CHAR                                              # expChar
    | TRUE                                                  # expVerdadero
    | FALSE                                                 # expFalso
    | ID                                                    # expId
    | NULL                                                  # expNull
    ;


tipo_dato_general
    : tipo_dato_primitivo
    | ID
    ;

tipo_dato_primitivo
    : INT
    | DOUBLE
    | STRING
    | CHAR
    | BOOLEAN
    ;


/**ANALISIS LEXICO**/

/**Palabras Reservadas**/
PUBLIC:     'public';
CLASS:      'class';
VOID:       'void';
INT:        'int';
DOUBLE:     'double';
STRING:     'String';
CHAR:       'char';
BOOLEAN:    'boolean';
NEW:        'new';
RETURN:     'return';
IF:         'if';
ELSE:       'else';
TRUE:       'true';
FALSE:      'false';
SWITCH:     'switch';
CASE:       'case';
BREAK:      'break';
CONTINUE:   'continue';
DEFAULT:    'default';
PRINTLN:    'println';
PRINT:      'print';
READLN:     'readln';
FOR:        'for';
WHILE:      'while';
DO:         'do';
NULL:       'null';

/**Simbolos**/
MAS_EQ:     '+=';
MENOS_EQ:   '-=';
MULTI_EQ:   '*=';
MAS:        '+';
MENOS:      '-';
MULTI:      '*';
DIV:        '/';
MODULO:     '%';
EQ:         '=';
EQ_EQ:      '==';
NO_EQ:      '!=';
MAYOR_Q:    '>';
MAYOR_EQ_Q: '>=';
MENOR_Q:    '<';
MENOR_EQ_Q: '<=';
AND:        '&&';
OR:         '||';
NOT:        '!';
MAS_MAS:    '++';
MENOS_MENOS:'--';
PUNTO:      '.';
COMA:       ',';
DOS_P:      ':';
P_COMA:     ';';
LLAVE_A:    '{';
LLAVE_C:    '}';
CORCH_A:    '[';
CORCH_C:    ']';
PAR_A:      '(';
PAR_C:      ')';
INTERROGACION: '?';


/**expresiones regulares**/

ID:         [a-zA-Z_][a-zA-Z0-9_]* ;
ENTERO:     [0-9]+ ;
DECIMAL:    [0-9]+ '.' [0-9]+;
CADENA :    '"' .*? '"' ;
LIT_CHAR :  '\'' . '\'' ;

WS: [ \t\n\r\f\u00A0\u200B] -> skip;
COMENTARIO_LINEA  : '//' ~[\r\n]* -> channel(HIDDEN) ;
COMENTARIO_BLOQUE : '/*' .*? '*/' -> channel(HIDDEN) ;