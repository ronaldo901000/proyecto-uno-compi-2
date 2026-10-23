.data
.align 2
dbl_0: .word 0
.align 2
dbl_1: .word 100000
.align 2
str_0: .asciz "Novato"
.align 2
str_1: .asciz "Aprendiz"
.align 2
str_2: .asciz "Maestro del ahorcado"
.align 2
str_3: .asciz "----- MARCADOR -----"
.align 2
str_4: .asciz "Partidas: "
.align 2
str_5: .asciz " | Ganadas: "
.align 2
str_6: .asciz " | Perdidas: "
.align 2
str_7: .asciz "Racha actual: "
.align 2
str_8: .asciz " | Mejor racha: "
.align 2
str_9: .asciz "Victorias: "
.align 2
str_10: .asciz "%"
.align 2
str_11: .asciz "      |"
.align 2
str_12: .asciz "  O   |"
.align 2
str_13: .asciz "  |   |"
.align 2
str_14: .asciz " -|   |"
.align 2
str_15: .asciz " -|-  |"
.align 2
str_16: .asciz " ^    |"
.align 2
str_17: .asciz " ^ ^  |"
.align 2
str_18: .asciz "  +---+"
.align 2
str_19: .asciz "========="
.align 2
str_20: .asciz "Errores: "
.align 2
str_21: .asciz "/"
.align 2
str_22: .asciz " | Intentos restantes: "
.align 2
str_23: .asciz "Letras usadas: "
.align 2
str_24: .asciz " "
.align 2
str_25: .asciz ""
.align 2
str_26: .asciz "Lenguaje con nombre de serpiente"
.align 2
str_27: .asciz "Traduce codigo fuente a otro lenguaje"
.align 2
str_28: .asciz "El enemigo de la resistencia"
.align 2
str_29: .asciz "Civilizacion aliada"
.align 2
str_30: .asciz "Codigo intermedio ejecutable"
.align 2
str_31: .asciz "Indice fuera de rango, se usa la palabra 0"
.align 2
str_32: .asciz "_ "
.align 2
str_33: .asciz "Jugador"
.align 2
str_34: .asciz "=============================="
.align 2
str_35: .asciz "     AHORCADO ZETARIANO       "
.align 2
str_36: .asciz "Ingresa tu nombre:"
.align 2
str_37: .asciz "Elige una palabra (0 a 4):"
.align 2
str_38: .asciz "Pista: "
.align 2
str_39: .asciz "Ingresa una letra minuscula:"
.align 2
str_40: .asciz "Entrada invalida, solo letras de la a a la z"
.align 2
str_41: .asciz "Ya usaste la letra "
.align 2
str_42: .asciz "Bien! La letra "
.align 2
str_43: .asciz " esta en la palabra"
.align 2
str_44: .asciz "Fallaste! La letra "
.align 2
str_45: .asciz " no esta"
.align 2
str_46: .asciz "GANASTE! La palabra era:"
.align 2
str_47: .asciz "PERDISTE. La palabra era:"
.align 2
str_48: .asciz "Puntaje de la partida: "
.align 2
str_49: .asciz "Jugar otra vez? (1 = si, 0 = no)"
.align 2
str_50: .asciz "Puntaje total: "
.align 2
str_51: .asciz "Titulo obtenido: "
.align 2
str_52: .asciz "Gracias por jugar, "
.align 2
__vtable_Ahorcado:
    .word metodo_Ahorcado_yaUsada_1
    .word metodo_Ahorcado_intentar_2
    .word metodo_Ahorcado_gano_3
    .word metodo_Ahorcado_perdio_4
    .word metodo_Ahorcado_terminoJuego_5
    .word metodo_Ahorcado_intentosRestantes_6
    .word metodo_Ahorcado_dibujar_7
    .word metodo_Ahorcado_mostrarEstado_8
.align 2
__vtable_Palabra:
    .word metodo_Palabra_agregarLetra_1
    .word metodo_Palabra_contiene_2
    .word metodo_Palabra_revelar_3
    .word metodo_Palabra_estaCompleta_4
    .word metodo_Palabra_revelarTodo_5
    .word metodo_Palabra_imprimirProgreso_6
.align 2
__vtable_Diccionario:
    .word metodo_Diccionario_obtener_1
.text
.globl main
main:
    call __entrada_main
    call z_exit

fun_validarLetra:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    lw t1, -12(fp)
    li t2, 97
    slt t3, t1, t2
    sw t3, -16(fp)
    lw t1, -12(fp)
    li t2, 122
    slt t3, t2, t1
    sw t3, -20(fp)
    lw t1, -16(fp)
    lw t2, -20(fp)
    or t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    seqz t3, t1
    sw t3, -28(fp)
    lw a0, -28(fp)
    j __epilogo_fun_validarLetra
    j __epilogo_fun_validarLetra
__epilogo_fun_validarLetra:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

fun_calcularPuntaje:
    addi sp, sp, -60
    sw ra, 56(sp)
    sw fp, 52(sp)
    addi fp, sp, 60
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw t1, -12(fp)
    li t2, 10
    mul t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    sw t1, -28(fp)
    lw t1, -16(fp)
    li t2, 5
    mul t3, t1, t2
    sw t3, -32(fp)
    lw t1, -32(fp)
    sw t1, -36(fp)
    li t1, 0
    sw t1, -40(fp)
    lw t1, -16(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -44(fp)
    lw t1, -44(fp)
    bnez t1, L2
    j L1
L2:
    li t1, 50
    sw t1, -40(fp)
L1:
    lw t1, -28(fp)
    lw t2, -36(fp)
    sub t3, t1, t2
    sw t3, -48(fp)
    lw t1, -48(fp)
    lw t2, -40(fp)
    add t3, t1, t2
    sw t3, -52(fp)
    lw t1, -52(fp)
    sw t1, -56(fp)
    lw t1, -56(fp)
    li t2, 0
    slt t3, t1, t2
    sw t3, -60(fp)
    lw t1, -60(fp)
    bnez t1, L4
    j L3
L4:
    li t1, 0
    sw t1, -56(fp)
L3:
    lw a0, -56(fp)
    j __epilogo_fun_calcularPuntaje
    j __epilogo_fun_calcularPuntaje
__epilogo_fun_calcularPuntaje:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 60
    mv fp, t0
    ret

fun_registrarResultado:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw t6, -12(fp)
    lw t1, 0(t6)
    li t2, 5
    slt t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L6
    j L5
L6:
    lw t6, -12(fp)
    lw t2, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 20
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t6, -12(fp)
    lw t2, 0(t6)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
L5:
    lw t6, -12(fp)
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    sw t3, 0(t6)
    lw t1, -16(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L9
    j L8
L9:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t3, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t3, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    slt t3, t2, t1
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L11
    j L10
L11:
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
L10:
    j L7
L8:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t3, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
L7:
    j __epilogo_fun_registrarResultado
__epilogo_fun_registrarResultado:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

fun_sumarPuntajes:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    li t1, 0
    sw t1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
L12:
    lw t1, -20(fp)
    lw t6, -12(fp)
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L13
    j L15
L13:
    lw t2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -28(fp)
    lw t1, -16(fp)
    lw t2, -28(fp)
    add t3, t1, t2
    sw t3, -32(fp)
    lw t1, -32(fp)
    sw t1, -16(fp)
L14:
    lw t1, -20(fp)
    addi t3, t1, 1
    sw t3, -20(fp)
    j L12
L15:
    lw a0, -16(fp)
    j __epilogo_fun_sumarPuntajes
    j __epilogo_fun_sumarPuntajes
__epilogo_fun_sumarPuntajes:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

fun_porcentajeVictorias:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    lw t6, -12(fp)
    lw t1, 0(t6)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -16(fp)
    lw t1, -16(fp)
    bnez t1, L17
    j L16
L17:
    la a0, dbl_0
    lw a0, 0(a0)
    j __epilogo_fun_porcentajeVictorias
L16:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    la t2, dbl_1
    lw t2, 0(t2)
    mul t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    lw t6, -12(fp)
    lw t2, 0(t6)
    div t3, t1, t2
    sw t3, -24(fp)
    lw a0, -24(fp)
    j __epilogo_fun_porcentajeVictorias
    j __epilogo_fun_porcentajeVictorias
__epilogo_fun_porcentajeVictorias:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

fun_titulo:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    lw t1, -12(fp)
    li t2, 100
    div t3, t1, t2
    sw t3, -16(fp)
    lw t1, -16(fp)
    sw t1, -20(fp)
    la t1, str_0
    sw t1, -24(fp)
    lw t1, -20(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L19
    lw t1, -20(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L20
    j L21
L19:
    la t1, str_0
    sw t1, -24(fp)
    j L18
L20:
    la t1, str_1
    sw t1, -24(fp)
    j L18
L21:
    la t1, str_2
    sw t1, -24(fp)
    j L18
L18:
    lw a0, -24(fp)
    j __epilogo_fun_titulo
    j __epilogo_fun_titulo
__epilogo_fun_titulo:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

fun_mostrarMarcador:
    addi sp, sp, -56
    sw ra, 52(sp)
    sw fp, 48(sp)
    addi fp, sp, 56
    sw a0, -12(fp)
    la a0, str_3
    call z_print_cadena
    call z_print_salto
    la t1, str_4
    lw t6, -12(fp)
    lw t2, 0(t6)
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    call z_itoa
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -16(fp)
    lw t1, -16(fp)
    la t2, str_5
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -20(fp)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t2, 0(t6)
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    call z_itoa
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -24(fp)
    lw t1, -24(fp)
    la t2, str_6
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -28(fp)
    lw t1, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t2, 0(t6)
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    call z_itoa
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -32(fp)
    lw a0, -32(fp)
    call z_print_cadena
    call z_print_salto
    la t1, str_7
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    call z_itoa
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -36(fp)
    lw t1, -36(fp)
    la t2, str_8
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -40(fp)
    lw t1, -40(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    call z_itoa
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -44(fp)
    lw a0, -44(fp)
    call z_print_cadena
    call z_print_salto
    lw a0, -12(fp)
    call fun_porcentajeVictorias
    sw a0, -48(fp)
    la t1, str_9
    lw t2, -48(fp)
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    call z_ftoa
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -52(fp)
    lw t1, -52(fp)
    la t2, str_10
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -56(fp)
    lw a0, -56(fp)
    call z_print_cadena
    call z_print_salto
    j __epilogo_fun_mostrarMarcador
__epilogo_fun_mostrarMarcador:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 56
    mv fp, t0
    ret

metodo_Ahorcado_yaUsada_1:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    sw a1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
L22:
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L23
    j L25
L23:
    lw t2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lb t3, 0(t1)
    sw t3, -28(fp)
    lw t1, -28(fp)
    lw t2, -16(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L27
    j L26
L27:
    li a0, 1
    j __epilogo_metodo_Ahorcado_yaUsada_1
L26:
L24:
    lw t1, -20(fp)
    addi t3, t1, 1
    sw t3, -20(fp)
    j L22
L25:
    li a0, 0
    j __epilogo_metodo_Ahorcado_yaUsada_1
    j __epilogo_metodo_Ahorcado_yaUsada_1
__epilogo_metodo_Ahorcado_yaUsada_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

metodo_Ahorcado_intentar_2:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -20(fp)
    lw t1, -20(fp)
    bnez t1, L29
    j L28
L29:
    li a0, 0
    j __epilogo_metodo_Ahorcado_intentar_2
L28:
    lw t6, -12(fp)
    addi t6, t6, 20
    lw t1, 0(t6)
    li t2, 40
    slt t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L31
    j L30
L31:
    lw t6, -12(fp)
    addi t6, t6, 20
    lw t2, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t6, -12(fp)
    addi t6, t6, 20
    lw t2, 0(t6)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 1
    mul t2, t2, t4
    add t6, t6, t2
    sb t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 20
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 20
    sw t3, 0(t6)
L30:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    lw a1, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -28(fp)
    lw t1, -28(fp)
    bnez t1, L33
    j L32
L33:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    lw a1, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -32(fp)
    li a0, 1
    j __epilogo_metodo_Ahorcado_intentar_2
L32:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t3, 0(t6)
    li a0, 2
    j __epilogo_metodo_Ahorcado_intentar_2
    j __epilogo_metodo_Ahorcado_intentar_2
__epilogo_metodo_Ahorcado_intentar_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

metodo_Ahorcado_gano_3:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -16(fp)
    lw a0, -16(fp)
    j __epilogo_metodo_Ahorcado_gano_3
    j __epilogo_metodo_Ahorcado_gano_3
__epilogo_metodo_Ahorcado_gano_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Ahorcado_perdio_4:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    xori t3, t3, 1
    sw t3, -16(fp)
    lw a0, -16(fp)
    j __epilogo_metodo_Ahorcado_perdio_4
    j __epilogo_metodo_Ahorcado_perdio_4
__epilogo_metodo_Ahorcado_perdio_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Ahorcado_terminoJuego_5:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    lw a0, -12(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -16(fp)
    lw a0, -12(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -20(fp)
    lw t1, -16(fp)
    lw t2, -20(fp)
    or t3, t1, t2
    sw t3, -24(fp)
    lw a0, -24(fp)
    j __epilogo_metodo_Ahorcado_terminoJuego_5
    j __epilogo_metodo_Ahorcado_terminoJuego_5
__epilogo_metodo_Ahorcado_terminoJuego_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

metodo_Ahorcado_intentosRestantes_6:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t2, 0(t6)
    sub t3, t1, t2
    sw t3, -16(fp)
    lw a0, -16(fp)
    j __epilogo_metodo_Ahorcado_intentosRestantes_6
    j __epilogo_metodo_Ahorcado_intentosRestantes_6
__epilogo_metodo_Ahorcado_intentosRestantes_6:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Ahorcado_dibujar_7:
    addi sp, sp, -48
    sw ra, 44(sp)
    sw fp, 40(sp)
    addi fp, sp, 48
    sw a0, -12(fp)
    la t1, str_11
    sw t1, -16(fp)
    la t1, str_11
    sw t1, -20(fp)
    la t1, str_11
    sw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t2, 0
    slt t3, t2, t1
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L35
    j L34
L35:
    la t1, str_12
    sw t1, -16(fp)
L34:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t2, 2
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L38
    j L37
L38:
    la t1, str_13
    sw t1, -20(fp)
    j L36
L37:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t2, 3
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L40
    j L39
L40:
    la t1, str_14
    sw t1, -20(fp)
    j L36
L39:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t2, 3
    slt t3, t2, t1
    sw t3, -40(fp)
    lw t1, -40(fp)
    bnez t1, L42
    j L41
L42:
    la t1, str_15
    sw t1, -20(fp)
    j L36
L41:
L36:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t2, 5
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -44(fp)
    lw t1, -44(fp)
    bnez t1, L45
    j L44
L45:
    la t1, str_16
    sw t1, -24(fp)
    j L43
L44:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t2, 5
    slt t3, t2, t1
    sw t3, -48(fp)
    lw t1, -48(fp)
    bnez t1, L47
    j L46
L47:
    la t1, str_17
    sw t1, -24(fp)
    j L43
L46:
L43:
    la a0, str_18
    call z_print_cadena
    call z_print_salto
    la a0, str_13
    call z_print_cadena
    call z_print_salto
    lw a0, -16(fp)
    call z_print_cadena
    call z_print_salto
    lw a0, -20(fp)
    call z_print_cadena
    call z_print_salto
    lw a0, -24(fp)
    call z_print_cadena
    call z_print_salto
    la a0, str_11
    call z_print_cadena
    call z_print_salto
    la a0, str_19
    call z_print_cadena
    call z_print_salto
    j __epilogo_metodo_Ahorcado_dibujar_7
__epilogo_metodo_Ahorcado_dibujar_7:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 48
    mv fp, t0
    ret

metodo_Ahorcado_mostrarEstado_8:
    addi sp, sp, -56
    sw ra, 52(sp)
    sw fp, 48(sp)
    addi fp, sp, 56
    sw a0, -12(fp)
    la t1, str_20
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t2, 0(t6)
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    call z_itoa
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -16(fp)
    lw t1, -16(fp)
    la t2, str_21
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -20(fp)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    call z_itoa
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -24(fp)
    lw t1, -24(fp)
    la t2, str_22
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -28(fp)
    lw a0, -12(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -32(fp)
    lw t1, -28(fp)
    lw t2, -32(fp)
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    call z_itoa
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -36(fp)
    lw a0, -36(fp)
    call z_print_cadena
    call z_print_salto
    la a0, str_23
    call z_print_cadena
    li t1, 0
    sw t1, -40(fp)
L48:
    lw t1, -40(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -44(fp)
    lw t1, -44(fp)
    bnez t1, L49
    j L51
L49:
    lw t2, -40(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lb t3, 0(t1)
    sw t3, -48(fp)
    lw t1, -48(fp)
    la t2, str_24
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    call z_chr
    sw a0, 8(sp)
    lw a0, 4(sp)
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -52(fp)
    lw a0, -52(fp)
    call z_print_cadena
L50:
    lw t1, -40(fp)
    addi t3, t1, 1
    sw t3, -40(fp)
    j L48
L51:
    la a0, str_25
    call z_print_cadena
    call z_print_salto
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -56(fp)
    j __epilogo_metodo_Ahorcado_mostrarEstado_8
__epilogo_metodo_Ahorcado_mostrarEstado_8:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 56
    mv fp, t0
    ret

constructor_Ahorcado_1:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    li a0, 40
    call z_alloc
    sw a0, -24(fp)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 20
    sw t1, 0(t6)
    j __epilogo_constructor_Ahorcado_1
__epilogo_constructor_Ahorcado_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

metodo_Diccionario_obtener_1:
    addi sp, sp, -216
    sw ra, 212(sp)
    sw fp, 208(sp)
    addi fp, sp, 216
    sw a0, -12(fp)
    sw a1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
    lw t1, -16(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L53
    lw t1, -16(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L54
    lw t1, -16(fp)
    li t2, 2
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L55
    lw t1, -16(fp)
    li t2, 3
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L56
    lw t1, -16(fp)
    li t2, 4
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -40(fp)
    lw t1, -40(fp)
    bnez t1, L57
    j L58
L53:
    li a0, 24
    call z_alloc
    sw a0, -44(fp)
    lw t6, -44(fp)
    la t1, __vtable_Palabra
    sw t1, 0(t6)
    lw a0, -44(fp)
    li a1, 6
    la a2, str_26
    call constructor_Palabra_1
    sw a0, -44(fp)
    lw t1, -44(fp)
    sw t1, -20(fp)
    lw a0, -20(fp)
    li a1, 112
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -48(fp)
    lw a0, -20(fp)
    li a1, 121
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -52(fp)
    lw a0, -20(fp)
    li a1, 116
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -56(fp)
    lw a0, -20(fp)
    li a1, 104
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -60(fp)
    lw a0, -20(fp)
    li a1, 111
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -64(fp)
    lw a0, -20(fp)
    li a1, 110
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -68(fp)
    j L52
L54:
    li a0, 24
    call z_alloc
    sw a0, -72(fp)
    lw t6, -72(fp)
    la t1, __vtable_Palabra
    sw t1, 0(t6)
    lw a0, -72(fp)
    li a1, 10
    la a2, str_27
    call constructor_Palabra_1
    sw a0, -72(fp)
    lw t1, -72(fp)
    sw t1, -20(fp)
    lw a0, -20(fp)
    li a1, 99
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -76(fp)
    lw a0, -20(fp)
    li a1, 111
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -80(fp)
    lw a0, -20(fp)
    li a1, 109
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -84(fp)
    lw a0, -20(fp)
    li a1, 112
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -88(fp)
    lw a0, -20(fp)
    li a1, 105
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -92(fp)
    lw a0, -20(fp)
    li a1, 108
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -96(fp)
    lw a0, -20(fp)
    li a1, 97
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -100(fp)
    lw a0, -20(fp)
    li a1, 100
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -104(fp)
    lw a0, -20(fp)
    li a1, 111
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -108(fp)
    lw a0, -20(fp)
    li a1, 114
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -112(fp)
    j L52
L55:
    li a0, 24
    call z_alloc
    sw a0, -116(fp)
    lw t6, -116(fp)
    la t1, __vtable_Palabra
    sw t1, 0(t6)
    lw a0, -116(fp)
    li a1, 5
    la a2, str_28
    call constructor_Palabra_1
    sw a0, -116(fp)
    lw t1, -116(fp)
    sw t1, -20(fp)
    lw a0, -20(fp)
    li a1, 99
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -120(fp)
    lw a0, -20(fp)
    li a1, 101
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -124(fp)
    lw a0, -20(fp)
    li a1, 114
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -128(fp)
    lw a0, -20(fp)
    li a1, 100
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -132(fp)
    lw a0, -20(fp)
    li a1, 111
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -136(fp)
    j L52
L56:
    li a0, 24
    call z_alloc
    sw a0, -140(fp)
    lw t6, -140(fp)
    la t1, __vtable_Palabra
    sw t1, 0(t6)
    lw a0, -140(fp)
    li a1, 9
    la a2, str_29
    call constructor_Palabra_1
    sw a0, -140(fp)
    lw t1, -140(fp)
    sw t1, -20(fp)
    lw a0, -20(fp)
    li a1, 122
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -144(fp)
    lw a0, -20(fp)
    li a1, 101
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -148(fp)
    lw a0, -20(fp)
    li a1, 116
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -152(fp)
    lw a0, -20(fp)
    li a1, 97
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -156(fp)
    lw a0, -20(fp)
    li a1, 114
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -160(fp)
    lw a0, -20(fp)
    li a1, 105
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -164(fp)
    lw a0, -20(fp)
    li a1, 97
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -168(fp)
    lw a0, -20(fp)
    li a1, 110
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -172(fp)
    lw a0, -20(fp)
    li a1, 111
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -176(fp)
    j L52
L57:
    li a0, 24
    call z_alloc
    sw a0, -180(fp)
    lw t6, -180(fp)
    la t1, __vtable_Palabra
    sw t1, 0(t6)
    lw a0, -180(fp)
    li a1, 8
    la a2, str_30
    call constructor_Palabra_1
    sw a0, -180(fp)
    lw t1, -180(fp)
    sw t1, -20(fp)
    lw a0, -20(fp)
    li a1, 98
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -184(fp)
    lw a0, -20(fp)
    li a1, 121
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -188(fp)
    lw a0, -20(fp)
    li a1, 116
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -192(fp)
    lw a0, -20(fp)
    li a1, 101
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -196(fp)
    lw a0, -20(fp)
    li a1, 99
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -200(fp)
    lw a0, -20(fp)
    li a1, 111
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -204(fp)
    lw a0, -20(fp)
    li a1, 100
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -208(fp)
    lw a0, -20(fp)
    li a1, 101
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -212(fp)
    j L52
L58:
    la a0, str_31
    call z_print_cadena
    call z_print_salto
    lw a0, -12(fp)
    li a1, 0
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -216(fp)
    lw t1, -216(fp)
    sw t1, -20(fp)
    j L52
L52:
    lw a0, -20(fp)
    j __epilogo_metodo_Diccionario_obtener_1
    j __epilogo_metodo_Diccionario_obtener_1
__epilogo_metodo_Diccionario_obtener_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 216
    mv fp, t0
    ret

constructor_Diccionario_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    li t1, 5
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    j __epilogo_constructor_Diccionario_1
__epilogo_constructor_Diccionario_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Palabra_agregarLetra_1:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L60
    j L59
L60:
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 1
    mul t2, t2, t4
    add t6, t6, t2
    sb t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 1
    mul t2, t2, t4
    add t6, t6, t2
    sb t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t3, 0(t6)
L59:
    j __epilogo_metodo_Palabra_agregarLetra_1
__epilogo_metodo_Palabra_agregarLetra_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Palabra_contiene_2:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    sw a1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
L61:
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L62
    j L64
L62:
    lw t2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lb t3, 0(t1)
    sw t3, -28(fp)
    lw t1, -28(fp)
    lw t2, -16(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L66
    j L65
L66:
    li a0, 1
    j __epilogo_metodo_Palabra_contiene_2
L65:
L63:
    lw t1, -20(fp)
    addi t3, t1, 1
    sw t3, -20(fp)
    j L61
L64:
    li a0, 0
    j __epilogo_metodo_Palabra_contiene_2
    j __epilogo_metodo_Palabra_contiene_2
__epilogo_metodo_Palabra_contiene_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

metodo_Palabra_revelar_3:
    addi sp, sp, -48
    sw ra, 44(sp)
    sw fp, 40(sp)
    addi fp, sp, 48
    sw a0, -12(fp)
    sw a1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
    li t1, 0
    sw t1, -24(fp)
L67:
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L68
    j L70
L68:
    lw t2, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lb t3, 0(t1)
    sw t3, -32(fp)
    lw t1, -32(fp)
    lw t2, -16(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -36(fp)
    lw t2, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lb t3, 0(t1)
    sw t3, -40(fp)
    lw t1, -40(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -44(fp)
    lw t1, -36(fp)
    lw t2, -44(fp)
    and t3, t1, t2
    sw t3, -48(fp)
    lw t1, -48(fp)
    bnez t1, L72
    j L71
L72:
    lw t2, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    li t1, 1
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -24(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 1
    mul t2, t2, t4
    add t6, t6, t2
    sb t1, 0(t6)
    lw t1, -20(fp)
    addi t3, t1, 1
    sw t3, -20(fp)
L71:
L69:
    lw t1, -24(fp)
    addi t3, t1, 1
    sw t3, -24(fp)
    j L67
L70:
    lw a0, -20(fp)
    j __epilogo_metodo_Palabra_revelar_3
    j __epilogo_metodo_Palabra_revelar_3
__epilogo_metodo_Palabra_revelar_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 48
    mv fp, t0
    ret

metodo_Palabra_estaCompleta_4:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    li t1, 0
    sw t1, -16(fp)
L73:
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L74
    j L76
L74:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lb t3, 0(t1)
    sw t3, -24(fp)
    lw t1, -24(fp)
    seqz t3, t1
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L78
    j L77
L78:
    li a0, 0
    j __epilogo_metodo_Palabra_estaCompleta_4
L77:
L75:
    lw t1, -16(fp)
    addi t3, t1, 1
    sw t3, -16(fp)
    j L73
L76:
    li a0, 1
    j __epilogo_metodo_Palabra_estaCompleta_4
    j __epilogo_metodo_Palabra_estaCompleta_4
__epilogo_metodo_Palabra_estaCompleta_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

metodo_Palabra_revelarTodo_5:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    li t1, 0
    sw t1, -16(fp)
L79:
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L80
    j L81
L80:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    li t1, 1
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -16(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 1
    mul t2, t2, t4
    add t6, t6, t2
    sb t1, 0(t6)
    lw t1, -16(fp)
    addi t3, t1, 1
    sw t3, -16(fp)
    j L79
L81:
    j __epilogo_metodo_Palabra_revelarTodo_5
__epilogo_metodo_Palabra_revelarTodo_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Palabra_imprimirProgreso_6:
    addi sp, sp, -44
    sw ra, 40(sp)
    sw fp, 36(sp)
    addi fp, sp, 44
    sw a0, -12(fp)
    la t1, str_25
    sw t1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
L82:
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L83
    j L85
L83:
    lw t2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lb t3, 0(t1)
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L88
    j L87
L88:
    lw t2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lb t3, 0(t1)
    sw t3, -32(fp)
    lw t1, -16(fp)
    lw t2, -32(fp)
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    call z_chr
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -36(fp)
    lw t1, -36(fp)
    la t2, str_24
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -40(fp)
    lw t1, -40(fp)
    sw t1, -16(fp)
    j L86
L87:
    lw t1, -16(fp)
    la t2, str_32
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    sw a0, 8(sp)
    lw a0, 4(sp)
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -44(fp)
    lw t1, -44(fp)
    sw t1, -16(fp)
L86:
L84:
    lw t1, -20(fp)
    addi t3, t1, 1
    sw t3, -20(fp)
    j L82
L85:
    lw a0, -16(fp)
    call z_print_cadena
    call z_print_salto
    j __epilogo_metodo_Palabra_imprimirProgreso_6
__epilogo_metodo_Palabra_imprimirProgreso_6:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 44
    mv fp, t0
    ret

constructor_Palabra_1:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw t1, -16(fp)
    li t2, 1
    mul a0, t1, t2
    addi a0, a0, 3
    andi a0, a0, -4
    call z_alloc
    sw a0, -24(fp)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    lw t1, -16(fp)
    li t2, 1
    mul a0, t1, t2
    addi a0, a0, 3
    andi a0, a0, -4
    call z_alloc
    sw a0, -28(fp)
    lw t1, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    sw t1, 0(t6)
    j __epilogo_constructor_Palabra_1
__epilogo_constructor_Palabra_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

__entrada_main:
    addi sp, sp, -172
    sw ra, 168(sp)
    sw fp, 164(sp)
    addi fp, sp, 172
    la t1, str_33
    sw t1, -12(fp)
    li t1, 0
    sw t1, -16(fp)
    li t1, 1
    sw t1, -20(fp)
    li t1, 97
    sw t1, -24(fp)
    li t1, 0
    sw t1, -28(fp)
    li t1, 0
    sw t1, -32(fp)
    li t1, 0
    sw t1, -36(fp)
    li t1, 0
    sw t1, -40(fp)
    li a0, 8
    call z_alloc
    sw a0, -44(fp)
    lw t6, -44(fp)
    la t1, __vtable_Diccionario
    sw t1, 0(t6)
    lw a0, -44(fp)
    call constructor_Diccionario_1
    sw a0, -44(fp)
    lw t1, -44(fp)
    sw t1, -48(fp)
    li a0, 24
    call z_alloc
    sw a0, -52(fp)
    lw t6, -52(fp)
    la t1, __vtable_Ahorcado
    sw t1, 0(t6)
    lw a0, -48(fp)
    li a1, 0
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -56(fp)
    lw a0, -52(fp)
    lw a1, -56(fp)
    li a2, 6
    call constructor_Ahorcado_1
    sw a0, -52(fp)
    lw t1, -52(fp)
    sw t1, -60(fp)
    li a0, 20
    call z_alloc
    sw a0, -64(fp)
    li t1, 0
    lw t6, -64(fp)
    addi sp, sp, -4
    sw t6, 0(sp)
    li t2, 0
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    li t1, 0
    lw t6, -64(fp)
    addi sp, sp, -4
    sw t6, 0(sp)
    li t2, 1
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    li t1, 0
    lw t6, -64(fp)
    addi sp, sp, -4
    sw t6, 0(sp)
    li t2, 2
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    li t1, 0
    lw t6, -64(fp)
    addi sp, sp, -4
    sw t6, 0(sp)
    li t2, 3
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    li t1, 0
    lw t6, -64(fp)
    addi sp, sp, -4
    sw t6, 0(sp)
    li t2, 4
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    li t1, 0
    addi t6, fp, -88
    sw t1, 0(t6)
    li t1, 0
    addi t6, fp, -88
    addi t6, t6, 4
    sw t1, 0(t6)
    li t1, 0
    addi t6, fp, -88
    addi t6, t6, 8
    sw t1, 0(t6)
    li t1, 0
    addi t6, fp, -88
    addi t6, t6, 12
    sw t1, 0(t6)
    li t1, 0
    addi t6, fp, -88
    addi t6, t6, 16
    sw t1, 0(t6)
    lw t1, -64(fp)
    addi t6, fp, -88
    addi t6, t6, 20
    sw t1, 0(t6)
    la a0, str_34
    call z_print_cadena
    call z_print_salto
    la a0, str_35
    call z_print_cadena
    call z_print_salto
    la a0, str_34
    call z_print_cadena
    call z_print_salto
    la a0, str_36
    call z_print_cadena
    call z_print_salto
    li a0, 256
    call z_alloc
    mv t0, a0
    li a1, 256
    call z_leer_cadena
    mv a0, t0
    sw a0, -12(fp)
L89:
    la a0, str_37
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -16(fp)
    li a0, 24
    call z_alloc
    sw a0, -92(fp)
    lw t6, -92(fp)
    la t1, __vtable_Ahorcado
    sw t1, 0(t6)
    lw a0, -48(fp)
    lw a1, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -96(fp)
    lw a0, -92(fp)
    lw a1, -96(fp)
    li a2, 6
    call constructor_Ahorcado_1
    sw a0, -92(fp)
    lw t1, -92(fp)
    sw t1, -60(fp)
    la a0, str_38
    call z_print_cadena
    lw t6, -60(fp)
    addi t6, t6, 4
    lw t6, 0(t6)
    addi t6, t6, 20
    lw a0, 0(t6)
    call z_print_cadena
    call z_print_salto
L91:
    lw a0, -60(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -100(fp)
    lw t1, -100(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -104(fp)
    lw t1, -104(fp)
    bnez t1, L92
    j L93
L92:
    lw a0, -60(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 24(t0)
    jalr t0
    sw a0, -108(fp)
    lw a0, -60(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -112(fp)
    la a0, str_39
    call z_print_cadena
    call z_print_salto
    call z_leer_caracter
    sw a0, -24(fp)
    lw a0, -24(fp)
    call fun_validarLetra
    sw a0, -116(fp)
    lw t1, -116(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -120(fp)
    lw t1, -120(fp)
    bnez t1, L95
    j L94
L95:
    la a0, str_40
    call z_print_cadena
    call z_print_salto
    j L91
L94:
    lw a0, -60(fp)
    lw a1, -24(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -124(fp)
    lw t1, -124(fp)
    sw t1, -28(fp)
    lw t1, -28(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -128(fp)
    lw t1, -128(fp)
    bnez t1, L98
    j L97
L98:
    la a0, str_41
    call z_print_cadena
    lw a0, -24(fp)
    call z_print_caracter
    call z_print_salto
    j L96
L97:
    lw t1, -28(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -132(fp)
    lw t1, -132(fp)
    bnez t1, L100
    j L99
L100:
    la a0, str_42
    call z_print_cadena
    lw a0, -24(fp)
    call z_print_caracter
    la a0, str_43
    call z_print_cadena
    call z_print_salto
    j L96
L99:
    la a0, str_44
    call z_print_cadena
    lw a0, -24(fp)
    call z_print_caracter
    la a0, str_45
    call z_print_cadena
    call z_print_salto
L96:
    j L91
L93:
    li t1, 0
    sw t1, -32(fp)
    lw a0, -60(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -136(fp)
    lw t1, -136(fp)
    bnez t1, L103
    j L102
L103:
    li t1, 1
    sw t1, -40(fp)
    lw t6, -60(fp)
    addi t6, t6, 4
    lw t6, 0(t6)
    addi t6, t6, 12
    lw a0, 0(t6)
    lw t6, -60(fp)
    addi t6, t6, 8
    lw a1, 0(t6)
    li a2, 6
    call fun_calcularPuntaje
    sw a0, -140(fp)
    lw t1, -140(fp)
    sw t1, -32(fp)
    la a0, str_46
    call z_print_cadena
    call z_print_salto
    j L101
L102:
    li t1, 0
    sw t1, -40(fp)
    la a0, str_47
    call z_print_cadena
    call z_print_salto
L101:
    lw a0, -60(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 24(t0)
    jalr t0
    sw a0, -144(fp)
    lw t6, -60(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -148(fp)
    lw t6, -60(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -152(fp)
    la a0, str_48
    call z_print_cadena
    lw a0, -32(fp)
    call z_print_entero
    call z_print_salto
    addi a0, fp, -88
    lw a1, -40(fp)
    lw a2, -32(fp)
    call fun_registrarResultado
    sw a0, -156(fp)
    addi a0, fp, -88
    call fun_mostrarMarcador
    sw a0, -160(fp)
    la a0, str_49
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -20(fp)
    lw t1, -20(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -164(fp)
    lw t1, -164(fp)
    bnez t1, L89
    j L90
L90:
    addi a0, fp, -88
    call fun_sumarPuntajes
    sw a0, -168(fp)
    lw t1, -168(fp)
    sw t1, -36(fp)
    la a0, str_50
    call z_print_cadena
    lw a0, -36(fp)
    call z_print_entero
    call z_print_salto
    la a0, str_51
    call z_print_cadena
    lw a0, -36(fp)
    call fun_titulo
    sw a0, -172(fp)
    lw a0, -172(fp)
    call z_print_cadena
    call z_print_salto
    la a0, str_52
    call z_print_cadena
    lw a0, -12(fp)
    call z_print_cadena
    call z_print_salto
__epilogo_main:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 172
    mv fp, t0
    ret

.data
__z_nulo: .asciz "(nulo)"
__z_heap: .word 0x10002000
__z_buf: .space 256
.text

# z_print_salto: imprime newline (caracter 10)
z_print_salto:
    li a0, 10
    li a7, 11
    ecall
    ret

# z_print_cadena: print_string(a0)
z_print_cadena:
    li a7, 4
    ecall
    ret

# z_print_entero: print_int(a0)
z_print_entero:
    li a7, 1
    ecall
    ret

# z_print_decimal: imprime punto fijo escala 1000 como X.YYY
z_print_decimal:
    mv t0, a0
    bgez t0, L_dec_pos
    li a0, 45
    li a7, 11
    ecall
    neg t0, t0
L_dec_pos:
    li t1, 1000
    div t2, t0, t1
    rem t3, t0, t1
    mv a0, t2
    li a7, 1
    ecall
    li a0, 46
    li a7, 11
    ecall
    li t4, 100
    div a0, t3, t4
    rem t3, t3, t4
    addi a0, a0, 48
    li a7, 11
    ecall
    li t4, 10
    div a0, t3, t4
    rem t3, t3, t4
    addi a0, a0, 48
    li a7, 11
    ecall
    addi a0, t3, 48
    li a7, 11
    ecall
    ret

# z_print_caracter: print_char(a0) - ecall 11
z_print_caracter:
    li a7, 11
    ecall
    ret

# z_print_booleano: print_int(a0)
z_print_booleano:
    li a7, 1
    ecall
    ret

# z_print_nulo: print_string(__z_nulo)
z_print_nulo:
    li a7, 4
    la a0, __z_nulo
    ecall
    ret

# z_leer_entero: read_int (ecall 5) -> a0
z_leer_entero:
    li a7, 5
    ecall
    ret

# z_leer_decimal: read_int (ecall 5) -> a0, punto fijo escala 1000
z_leer_decimal:
    li a7, 5
    ecall
    li t1, 1000
    mul a0, a0, t1
    ret

# z_leer_caracter: read_char (ecall 12) -> a0
z_leer_caracter:
    li a7, 12
    ecall
    ret

# z_leer_cadena: a0=buffer, a1=max; quita '\n' y devuelve buffer en a0
z_leer_cadena:
    mv t0, a0
    li a7, 8
    ecall
    mv t1, t0
L_lc_loop:
    lb t2, 0(t1)
    beqz t2, L_lc_fin
    li t3, 10
    beq t2, t3, L_lc_nl
    addi t1, t1, 1
    j L_lc_loop
L_lc_nl:
    sb zero, 0(t1)
L_lc_fin:
    mv a0, t0
    ret

# z_alloc: sbrk (ecall 9) a0=bytes, retorna a0 puntero
z_alloc:
    li a7, 9
    ecall
    ret

# z_strlen: a0=string, retorna a0=longitud
z_strlen:
    mv t1, a0
    li t2, 0
L_strlen_loop:
    lb t3, 0(t1)
    beqz t3, L_strlen_done
    addi t1, t1, 1
    addi t2, t2, 1
    j L_strlen_loop
L_strlen_done:
    mv a0, t2
    ret

# z_itoa: a0=entero, retorna a0=puntero a string nuevo
# rem antes de div. Signo '-' al inicio. Solo t0-t6.
z_itoa:
    mv t1, a0
    li a0, 12
    li a7, 9
    ecall
    addi t3, a0, 11
    sb zero, 0(t3)
    li t6, 0
    bgez t1, L_itoa_loop
    li t6, 1
    neg t1, t1
L_itoa_loop:
    li t4, 10
    rem t5, t1, t4
    div t1, t1, t4
    addi t5, t5, 48
    addi t3, t3, -1
    sb t5, 0(t3)
    bnez t1, L_itoa_loop
    beqz t6, L_itoa_fin
    li t4, 45
    addi t3, t3, -1
    sb t4, 0(t3)
L_itoa_fin:
    mv a0, t3
    ret

# z_chr: a0=caracter, retorna a0=puntero a string de 1 caracter
z_chr:
    mv t1, a0
    li a0, 2
    li a7, 9
    ecall
    sb t1, 0(a0)
    sb zero, 1(a0)
    ret

# z_ftoa: a0=flotante punto fijo escala 1000, retorna a0=puntero a string
# Formato: X.YYY (incluye negativos y ceros a la izquierda)
# Destruye: t0-t6 (todos temporales). Preserva: s0-s4, ra.
z_ftoa:
    mv t1, a0
    li a0, 12
    li a7, 9
    ecall
    addi t3, a0, 11
    sb zero, 0(t3)
    li t6, 0
    bgez t1, L_ftoa_abs
    li t6, 1
    neg t1, t1
L_ftoa_abs:
    li t4, 1000
    div t5, t1, t4
    rem t4, t1, t4
    li t1, 10
    rem a0, t4, t1
    addi a0, a0, 48
    addi t3, t3, -1
    sb a0, 0(t3)
    div t4, t4, t1
    rem a0, t4, t1
    addi a0, a0, 48
    addi t3, t3, -1
    sb a0, 0(t3)
    div t4, t4, t1
    rem a0, t4, t1
    addi a0, a0, 48
    addi t3, t3, -1
    sb a0, 0(t3)
    addi t3, t3, -1
    li a0, 46
    sb a0, 0(t3)
    mv t1, t5
    beqz t1, L_ftoa_zero_int
L_ftoa_int_loop:
    li t4, 10
    rem t5, t1, t4
    div t1, t1, t4
    addi t5, t5, 48
    addi t3, t3, -1
    sb t5, 0(t3)
    bnez t1, L_ftoa_int_loop
    j L_ftoa_sign
L_ftoa_zero_int:
    li a0, 48
    addi t3, t3, -1
    sb a0, 0(t3)
L_ftoa_sign:
    beqz t6, L_ftoa_fin
    li a0, 45
    addi t3, t3, -1
    sb a0, 0(t3)
L_ftoa_fin:
    mv a0, t3
    ret

# z_concat: a0=str1, a1=str2, retorna a0=inicio de la cadena nueva
# Usa z_strlen para medir, z_alloc para reservar.
# Guarda ra y s0-s4 en la pila.
z_concat:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw s0, 16(sp)
    sw s1, 12(sp)
    sw s2, 8(sp)
    sw s3, 4(sp)
    sw s4, 0(sp)
    mv s0, a0
    mv s1, a1
    mv a0, s0
    call z_strlen
    mv s2, a0
    mv a0, s1
    call z_strlen
    add s3, s2, a0
    addi a0, s3, 1
    li a7, 9
    ecall
    mv s4, a0
    mv t0, s4
    mv t1, s0
L_cc1:
    lb t2, 0(t1)
    beqz t2, L_cc1_fin
    sb t2, 0(t0)
    addi t0, t0, 1
    addi t1, t1, 1
    j L_cc1
L_cc1_fin:
    mv t1, s1
L_cc2:
    lb t2, 0(t1)
    beqz t2, L_cc2_fin
    sb t2, 0(t0)
    addi t0, t0, 1
    addi t1, t1, 1
    j L_cc2
L_cc2_fin:
    sb zero, 0(t0)
    mv a0, s4
    lw ra, 20(sp)
    lw s0, 16(sp)
    lw s1, 12(sp)
    lw s2, 8(sp)
    lw s3, 4(sp)
    lw s4, 0(sp)
    addi sp, sp, 24
    ret

# z_exit: exit (ecall 10) con codigo 0
z_exit:
    li a0, 0
    li a7, 10
    ecall
