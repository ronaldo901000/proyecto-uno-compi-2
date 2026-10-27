.data
.align 2
str_0: .asciz "Turno "
.align 2
str_1: .asciz ": "
.align 2
str_2: .asciz "Jugada invalida: termina la partida"
.align 2
str_3: .asciz "Gana "
.align 2
str_4: .asciz "Empate"
.align 2
str_5: .asciz ""
.align 2
str_6: .asciz "|"
.align 2
str_7: .asciz "X"
.align 2
str_8: .asciz "O"
.align 2
str_9: .asciz "."
.align 2
str_10: .asciz "Ana"
.align 2
str_11: .asciz "Bot"
.align 2
str_12: .asciz "Bot1"
.align 2
str_13: .asciz "Bot2"
.align 2
str_14: .asciz "=== PARTIDA 1: Ana (fijo) vs Bot (IA) ==="
.align 2
str_15: .asciz "=== PARTIDA 2: Bot1 vs Bot2 ==="
.align 2
str_16: .asciz "Victorias Ana: "
.align 2
str_17: .asciz "Victorias Bot: "
.align 2
str_18: .asciz "Victorias Bot1: "
.align 2
str_19: .asciz "Victorias Bot2: "
.align 2
__vtable_Tablero:
    .word metodo_Tablero_colocar_1
    .word metodo_Tablero_obtener_2
    .word metodo_Tablero_lleno_3
    .word metodo_Tablero_ganador_4
    .word metodo_Tablero_dibujar_5
.align 2
__vtable_JugadorIA:
    .word metodo_Jugador_getNombre_1
    .word metodo_Jugador_ganar_2
    .word metodo_Jugador_getVictorias_3
    .word metodo_JugadorIA_jugar_1
.align 2
__vtable_Partida:
    .word metodo_Partida_jugar_1
.align 2
__vtable_JugadorFijo:
    .word metodo_Jugador_getNombre_1
    .word metodo_Jugador_ganar_2
    .word metodo_Jugador_getVictorias_3
    .word metodo_JugadorFijo_jugar_2
    .word metodo_JugadorFijo_agregarJugada_1
.align 2
__vtable_Jugador:
    .word metodo_Jugador_getNombre_1
    .word metodo_Jugador_ganar_2
    .word metodo_Jugador_getVictorias_3
    .word metodo_Jugador_jugar_4
.text
.globl main
main:
    call __entrada_main
    call z_exit

metodo_Jugador_getNombre_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    j __epilogo_metodo_Jugador_getNombre_1
    j __epilogo_metodo_Jugador_getNombre_1
__epilogo_metodo_Jugador_getNombre_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Jugador_ganar_2:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t3, 0(t6)
    j __epilogo_metodo_Jugador_ganar_2
__epilogo_metodo_Jugador_ganar_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Jugador_getVictorias_3:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw a0, 0(t6)
    j __epilogo_metodo_Jugador_getVictorias_3
    j __epilogo_metodo_Jugador_getVictorias_3
__epilogo_metodo_Jugador_getVictorias_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Jugador_jugar_4:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    sw a1, -16(fp)
    li a0, 0
    j __epilogo_metodo_Jugador_jugar_4
    j __epilogo_metodo_Jugador_jugar_4
__epilogo_metodo_Jugador_jugar_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

constructor_Jugador_1:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
    j __epilogo_constructor_Jugador_1
__epilogo_constructor_Jugador_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Partida_jugar_1:
    addi sp, sp, -132
    sw ra, 128(sp)
    sw fp, 124(sp)
    addi fp, sp, 132
    sw a0, -12(fp)
    li t1, 1
    sw t1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
L1:
    lw t1, -20(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L4
    j L5
L4:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -28(fp)
    lw t1, -28(fp)
    seqz t3, t1
    sw t3, -32(fp)
    lw t1, -32(fp)
    sw t1, -36(fp)
    j L6
L5:
    li t1, 0
    sw t1, -36(fp)
L6:
    lw t1, -36(fp)
    bnez t1, L2
    j L3
L2:
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t3, 0(t6)
    lw t1, -16(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -40(fp)
    lw t1, -40(fp)
    bnez t1, L9
    j L8
L9:
    la t1, str_0
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
    lw t1, -44(fp)
    la t2, str_1
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
    sw a0, -48(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -52(fp)
    lw t1, -48(fp)
    lw t2, -52(fp)
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
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a1, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -60(fp)
    lw t1, -60(fp)
    sw t1, -64(fp)
    j L7
L8:
    la t1, str_0
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
    sw a0, -68(fp)
    lw t1, -68(fp)
    la t2, str_1
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
    sw a0, -72(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -76(fp)
    lw t1, -72(fp)
    lw t2, -76(fp)
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
    sw a0, -80(fp)
    lw a0, -80(fp)
    call z_print_cadena
    call z_print_salto
    lw t6, -12(fp)
    addi t6, t6, 12
    lw a0, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a1, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -84(fp)
    lw t1, -84(fp)
    sw t1, -64(fp)
L7:
    lw t1, -64(fp)
    seqz t3, t1
    sw t3, -88(fp)
    lw t1, -88(fp)
    bnez t1, L11
    j L10
L11:
    la a0, str_2
    call z_print_cadena
    call z_print_salto
    j L3
L10:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -92(fp)
    lw t1, -92(fp)
    sw t1, -20(fp)
    li t1, 3
    lw t2, -16(fp)
    sub t3, t1, t2
    sw t3, -96(fp)
    lw t1, -96(fp)
    sw t1, -16(fp)
    j L1
L3:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -100(fp)
    lw t1, -20(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -104(fp)
    lw t1, -104(fp)
    bnez t1, L14
    j L13
L14:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -108(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -112(fp)
    la t1, str_3
    lw t2, -112(fp)
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
    sw a0, -116(fp)
    lw a0, -116(fp)
    call z_print_cadena
    call z_print_salto
    j L12
L13:
    lw t1, -20(fp)
    li t2, 2
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -120(fp)
    lw t1, -120(fp)
    bnez t1, L16
    j L15
L16:
    lw t6, -12(fp)
    addi t6, t6, 12
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -124(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -128(fp)
    la t1, str_3
    lw t2, -128(fp)
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
    sw a0, -132(fp)
    lw a0, -132(fp)
    call z_print_cadena
    call z_print_salto
    j L12
L15:
    la a0, str_4
    call z_print_cadena
    call z_print_salto
L12:
    lw a0, -20(fp)
    j __epilogo_metodo_Partida_jugar_1
    j __epilogo_metodo_Partida_jugar_1
__epilogo_metodo_Partida_jugar_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 132
    mv fp, t0
    ret

constructor_Partida_1:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    li a0, 8
    call z_alloc
    sw a0, -24(fp)
    lw t6, -24(fp)
    la t1, __vtable_Tablero
    sw t1, 0(t6)
    lw a0, -24(fp)
    call constructor_Tablero_1
    sw a0, -24(fp)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
    j __epilogo_constructor_Partida_1
__epilogo_constructor_Partida_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

metodo_JugadorFijo_agregarJugada_1:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t2, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t2, 0(t6)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 24
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
    addi t6, t6, 24
    lw t2, 0(t6)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t3, 0(t6)
    j __epilogo_metodo_JugadorFijo_agregarJugada_1
__epilogo_metodo_JugadorFijo_agregarJugada_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_JugadorFijo_jugar_2:
    addi sp, sp, -36
    sw ra, 32(sp)
    sw fp, 28(sp)
    addi fp, sp, 36
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    lw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t2, 0(t6)
    slt t3, t1, t2
    xori t3, t3, 1
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L18
    j L17
L18:
    li a0, 0
    j __epilogo_metodo_JugadorFijo_jugar_2
L17:
    lw t6, -12(fp)
    addi t6, t6, 28
    lw t2, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    lw t2, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 20
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -28(fp)
    lw a0, -16(fp)
    lw a1, -24(fp)
    lw a2, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a3, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -32(fp)
    lw t1, -32(fp)
    sw t1, -36(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 28
    sw t3, 0(t6)
    lw a0, -36(fp)
    j __epilogo_metodo_JugadorFijo_jugar_2
    j __epilogo_metodo_JugadorFijo_jugar_2
__epilogo_metodo_JugadorFijo_jugar_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 36
    mv fp, t0
    ret

constructor_JugadorFijo_1:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    lw a2, -20(fp)
    call constructor_Jugador_1
    li a0, 36
    call z_alloc
    sw a0, -24(fp)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
    li a0, 36
    call z_alloc
    sw a0, -28(fp)
    lw t1, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 28
    sw t1, 0(t6)
    j __epilogo_constructor_JugadorFijo_1
__epilogo_constructor_JugadorFijo_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

metodo_JugadorIA_jugar_1:
    addi sp, sp, -44
    sw ra, 40(sp)
    sw fp, 36(sp)
    addi fp, sp, 44
    sw a0, -12(fp)
    sw a1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
L19:
    lw t1, -20(fp)
    li t2, 3
    slt t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L20
    j L22
L20:
    li t1, 0
    sw t1, -28(fp)
L23:
    lw t1, -28(fp)
    li t2, 3
    slt t3, t1, t2
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L24
    j L26
L24:
    lw a0, -16(fp)
    lw a1, -20(fp)
    lw a2, -28(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -36(fp)
    lw t1, -36(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -40(fp)
    lw t1, -40(fp)
    bnez t1, L28
    j L27
L28:
    lw a0, -16(fp)
    lw a1, -20(fp)
    lw a2, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a3, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -44(fp)
    lw a0, -44(fp)
    j __epilogo_metodo_JugadorIA_jugar_1
L27:
L25:
    lw t1, -28(fp)
    addi t3, t1, 1
    sw t3, -28(fp)
    j L23
L26:
L21:
    lw t1, -20(fp)
    addi t3, t1, 1
    sw t3, -20(fp)
    j L19
L22:
    li a0, 0
    j __epilogo_metodo_JugadorIA_jugar_1
    j __epilogo_metodo_JugadorIA_jugar_1
__epilogo_metodo_JugadorIA_jugar_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 44
    mv fp, t0
    ret

constructor_JugadorIA_1:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    lw a2, -20(fp)
    call constructor_Jugador_1
    j __epilogo_constructor_JugadorIA_1
__epilogo_constructor_JugadorIA_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Tablero_colocar_1:
    addi sp, sp, -64
    sw ra, 60(sp)
    sw fp, 56(sp)
    addi fp, sp, 64
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    sw a3, -24(fp)
    lw t1, -16(fp)
    li t2, 0
    slt t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L37
    j L36
L36:
    lw t1, -16(fp)
    li t2, 2
    slt t3, t2, t1
    sw t3, -32(fp)
    lw t1, -32(fp)
    sw t1, -36(fp)
    j L38
L37:
    li t1, 1
    sw t1, -36(fp)
L38:
    lw t1, -36(fp)
    bnez t1, L34
    j L33
L33:
    lw t1, -20(fp)
    li t2, 0
    slt t3, t1, t2
    sw t3, -40(fp)
    lw t1, -40(fp)
    sw t1, -44(fp)
    j L35
L34:
    li t1, 1
    sw t1, -44(fp)
L35:
    lw t1, -44(fp)
    bnez t1, L31
    j L30
L30:
    lw t1, -20(fp)
    li t2, 2
    slt t3, t2, t1
    sw t3, -48(fp)
    lw t1, -48(fp)
    sw t1, -52(fp)
    j L32
L31:
    li t1, 1
    sw t1, -52(fp)
L32:
    lw t1, -52(fp)
    bnez t1, L39
    j L29
L39:
    li a0, 0
    j __epilogo_metodo_Tablero_colocar_1
L29:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -56(fp)
    lw t2, -20(fp)
    lw t1, -56(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -60(fp)
    lw t1, -60(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -64(fp)
    lw t1, -64(fp)
    bnez t1, L41
    j L40
L41:
    li a0, 0
    j __epilogo_metodo_Tablero_colocar_1
L40:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -16(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -16(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    slli t2, t2, 2
    add t6, t6, t2
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -20(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    li a0, 1
    j __epilogo_metodo_Tablero_colocar_1
    j __epilogo_metodo_Tablero_colocar_1
__epilogo_metodo_Tablero_colocar_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 64
    mv fp, t0
    ret

metodo_Tablero_obtener_2:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -24(fp)
    lw t2, -20(fp)
    lw t1, -24(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -28(fp)
    lw a0, -28(fp)
    j __epilogo_metodo_Tablero_obtener_2
    j __epilogo_metodo_Tablero_obtener_2
__epilogo_metodo_Tablero_obtener_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

metodo_Tablero_lleno_3:
    addi sp, sp, -40
    sw ra, 36(sp)
    sw fp, 32(sp)
    addi fp, sp, 40
    sw a0, -12(fp)
    li t1, 0
    sw t1, -16(fp)
L42:
    lw t1, -16(fp)
    li t2, 3
    slt t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L43
    j L45
L43:
    li t1, 0
    sw t1, -24(fp)
L46:
    lw t1, -24(fp)
    li t2, 3
    slt t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L47
    j L49
L47:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -32(fp)
    lw t2, -24(fp)
    lw t1, -32(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -36(fp)
    lw t1, -36(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -40(fp)
    lw t1, -40(fp)
    bnez t1, L51
    j L50
L51:
    li a0, 0
    j __epilogo_metodo_Tablero_lleno_3
L50:
L48:
    lw t1, -24(fp)
    addi t3, t1, 1
    sw t3, -24(fp)
    j L46
L49:
L44:
    lw t1, -16(fp)
    addi t3, t1, 1
    sw t3, -16(fp)
    j L42
L45:
    li a0, 1
    j __epilogo_metodo_Tablero_lleno_3
    j __epilogo_metodo_Tablero_lleno_3
__epilogo_metodo_Tablero_lleno_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 40
    mv fp, t0
    ret

metodo_Tablero_ganador_4:
    addi sp, sp, -292
    sw ra, 288(sp)
    sw fp, 284(sp)
    addi fp, sp, 292
    sw a0, -12(fp)
    li t1, 0
    sw t1, -16(fp)
L52:
    lw t1, -16(fp)
    li t2, 3
    slt t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L53
    j L55
L53:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -24(fp)
    li t2, 0
    lw t1, -24(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -28(fp)
    lw t1, -28(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L60
    j L61
L60:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -36(fp)
    li t2, 0
    lw t1, -36(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -40(fp)
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -44(fp)
    li t2, 1
    lw t1, -44(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -48(fp)
    lw t1, -40(fp)
    lw t2, -48(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -52(fp)
    lw t1, -52(fp)
    sw t1, -56(fp)
    j L62
L61:
    li t1, 0
    sw t1, -56(fp)
L62:
    lw t1, -56(fp)
    bnez t1, L57
    j L58
L57:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -60(fp)
    li t2, 1
    lw t1, -60(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -64(fp)
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -68(fp)
    li t2, 2
    lw t1, -68(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -72(fp)
    lw t1, -64(fp)
    lw t2, -72(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -76(fp)
    lw t1, -76(fp)
    sw t1, -80(fp)
    j L59
L58:
    li t1, 0
    sw t1, -80(fp)
L59:
    lw t1, -80(fp)
    bnez t1, L63
    j L56
L63:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -84(fp)
    li t2, 0
    lw t1, -84(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -88(fp)
    lw a0, -88(fp)
    j __epilogo_metodo_Tablero_ganador_4
L56:
    li t2, 0
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -92(fp)
    lw t2, -16(fp)
    lw t1, -92(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -96(fp)
    lw t1, -96(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -100(fp)
    lw t1, -100(fp)
    bnez t1, L68
    j L69
L68:
    li t2, 0
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -104(fp)
    lw t2, -16(fp)
    lw t1, -104(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -108(fp)
    li t2, 1
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -112(fp)
    lw t2, -16(fp)
    lw t1, -112(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -116(fp)
    lw t1, -108(fp)
    lw t2, -116(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -120(fp)
    lw t1, -120(fp)
    sw t1, -124(fp)
    j L70
L69:
    li t1, 0
    sw t1, -124(fp)
L70:
    lw t1, -124(fp)
    bnez t1, L65
    j L66
L65:
    li t2, 1
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -128(fp)
    lw t2, -16(fp)
    lw t1, -128(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -132(fp)
    li t2, 2
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -136(fp)
    lw t2, -16(fp)
    lw t1, -136(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -140(fp)
    lw t1, -132(fp)
    lw t2, -140(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -144(fp)
    lw t1, -144(fp)
    sw t1, -148(fp)
    j L67
L66:
    li t1, 0
    sw t1, -148(fp)
L67:
    lw t1, -148(fp)
    bnez t1, L71
    j L64
L71:
    li t2, 0
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -152(fp)
    lw t2, -16(fp)
    lw t1, -152(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -156(fp)
    lw a0, -156(fp)
    j __epilogo_metodo_Tablero_ganador_4
L64:
L54:
    lw t1, -16(fp)
    addi t3, t1, 1
    sw t3, -16(fp)
    j L52
L55:
    li t2, 0
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -160(fp)
    li t2, 0
    lw t1, -160(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -164(fp)
    lw t1, -164(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -168(fp)
    lw t1, -168(fp)
    bnez t1, L76
    j L77
L76:
    li t2, 0
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -172(fp)
    li t2, 0
    lw t1, -172(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -176(fp)
    li t2, 1
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -180(fp)
    li t2, 1
    lw t1, -180(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -184(fp)
    lw t1, -176(fp)
    lw t2, -184(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -188(fp)
    lw t1, -188(fp)
    sw t1, -192(fp)
    j L78
L77:
    li t1, 0
    sw t1, -192(fp)
L78:
    lw t1, -192(fp)
    bnez t1, L73
    j L74
L73:
    li t2, 1
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -196(fp)
    li t2, 1
    lw t1, -196(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -200(fp)
    li t2, 2
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -204(fp)
    li t2, 2
    lw t1, -204(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -208(fp)
    lw t1, -200(fp)
    lw t2, -208(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -212(fp)
    lw t1, -212(fp)
    sw t1, -216(fp)
    j L75
L74:
    li t1, 0
    sw t1, -216(fp)
L75:
    lw t1, -216(fp)
    bnez t1, L79
    j L72
L79:
    li t2, 0
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -220(fp)
    li t2, 0
    lw t1, -220(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -224(fp)
    lw a0, -224(fp)
    j __epilogo_metodo_Tablero_ganador_4
L72:
    li t2, 0
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -228(fp)
    li t2, 2
    lw t1, -228(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -232(fp)
    lw t1, -232(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -236(fp)
    lw t1, -236(fp)
    bnez t1, L84
    j L85
L84:
    li t2, 0
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -240(fp)
    li t2, 2
    lw t1, -240(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -244(fp)
    li t2, 1
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -248(fp)
    li t2, 1
    lw t1, -248(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -252(fp)
    lw t1, -244(fp)
    lw t2, -252(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -256(fp)
    lw t1, -256(fp)
    sw t1, -260(fp)
    j L86
L85:
    li t1, 0
    sw t1, -260(fp)
L86:
    lw t1, -260(fp)
    bnez t1, L81
    j L82
L81:
    li t2, 1
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -264(fp)
    li t2, 1
    lw t1, -264(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -268(fp)
    li t2, 2
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -272(fp)
    li t2, 0
    lw t1, -272(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -276(fp)
    lw t1, -268(fp)
    lw t2, -276(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -280(fp)
    lw t1, -280(fp)
    sw t1, -284(fp)
    j L83
L82:
    li t1, 0
    sw t1, -284(fp)
L83:
    lw t1, -284(fp)
    bnez t1, L87
    j L80
L87:
    li t2, 0
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -288(fp)
    li t2, 2
    lw t1, -288(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -292(fp)
    lw a0, -292(fp)
    j __epilogo_metodo_Tablero_ganador_4
L80:
    li a0, 0
    j __epilogo_metodo_Tablero_ganador_4
    j __epilogo_metodo_Tablero_ganador_4
__epilogo_metodo_Tablero_ganador_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 292
    mv fp, t0
    ret

metodo_Tablero_dibujar_5:
    addi sp, sp, -56
    sw ra, 52(sp)
    sw fp, 48(sp)
    addi fp, sp, 56
    sw a0, -12(fp)
    li t1, 0
    sw t1, -16(fp)
L88:
    lw t1, -16(fp)
    li t2, 3
    slt t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L89
    j L91
L89:
    la t1, str_5
    sw t1, -24(fp)
    li t1, 0
    sw t1, -28(fp)
L92:
    lw t1, -28(fp)
    li t2, 3
    slt t3, t1, t2
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L93
    j L95
L93:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -36(fp)
    lw t2, -28(fp)
    lw t1, -36(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -40(fp)
    lw a0, -12(fp)
    lw a1, -40(fp)
    call metodo_Tablero_simbolo_6
    sw a0, -44(fp)
    lw t1, -24(fp)
    lw t2, -44(fp)
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
    sw a0, -48(fp)
    lw t1, -48(fp)
    sw t1, -24(fp)
    lw t1, -28(fp)
    li t2, 2
    slt t3, t1, t2
    sw t3, -52(fp)
    lw t1, -52(fp)
    bnez t1, L97
    j L96
L97:
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
    sw a0, -56(fp)
    lw t1, -56(fp)
    sw t1, -24(fp)
L96:
L94:
    lw t1, -28(fp)
    addi t3, t1, 1
    sw t3, -28(fp)
    j L92
L95:
    lw a0, -24(fp)
    call z_print_cadena
    call z_print_salto
L90:
    lw t1, -16(fp)
    addi t3, t1, 1
    sw t3, -16(fp)
    j L88
L91:
    j __epilogo_metodo_Tablero_dibujar_5
__epilogo_metodo_Tablero_dibujar_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 56
    mv fp, t0
    ret

metodo_Tablero_simbolo_6:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t1, -16(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L99
    j L98
L99:
    la a0, str_7
    j __epilogo_metodo_Tablero_simbolo_6
L98:
    lw t1, -16(fp)
    li t2, 2
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L101
    j L100
L101:
    la a0, str_8
    j __epilogo_metodo_Tablero_simbolo_6
L100:
    la a0, str_9
    j __epilogo_metodo_Tablero_simbolo_6
    j __epilogo_metodo_Tablero_simbolo_6
__epilogo_metodo_Tablero_simbolo_6:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

constructor_Tablero_1:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    li t1, 3
    li t2, 3
    addi sp, sp, -16
    sw t1, 0(sp)
    sw t2, 4(sp)
    slli a0, t1, 2
    call z_alloc
    sw a0, 8(sp)
    sw zero, 12(sp)
Lnew_0_loop:
    lw t0, 12(sp)
    lw t1, 0(sp)
    bge t0, t1, Lnew_0_fin
    lw a0, 4(sp)
    li t2, 4
    mul a0, a0, t2
    addi a0, a0, 3
    andi a0, a0, -4
    call z_alloc
    lw t3, 8(sp)
    lw t0, 12(sp)
    slli t4, t0, 2
    add t3, t3, t4
    sw a0, 0(t3)
    addi t0, t0, 1
    sw t0, 12(sp)
    j Lnew_0_loop
Lnew_0_fin:
    lw a0, 8(sp)
    addi sp, sp, 16
    sw a0, -16(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    j __epilogo_constructor_Tablero_1
__epilogo_constructor_Tablero_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

__entrada_main:
    addi sp, sp, -92
    sw ra, 88(sp)
    sw fp, 84(sp)
    addi fp, sp, 92
    li a0, 32
    call z_alloc
    sw a0, -12(fp)
    lw t6, -12(fp)
    la t1, __vtable_JugadorFijo
    sw t1, 0(t6)
    lw a0, -12(fp)
    la a1, str_10
    li a2, 1
    call constructor_JugadorFijo_1
    sw a0, -12(fp)
    lw t1, -12(fp)
    sw t1, -16(fp)
    li a0, 16
    call z_alloc
    sw a0, -20(fp)
    lw t6, -20(fp)
    la t1, __vtable_JugadorIA
    sw t1, 0(t6)
    lw a0, -20(fp)
    la a1, str_11
    li a2, 2
    call constructor_JugadorIA_1
    sw a0, -20(fp)
    lw t1, -20(fp)
    sw t1, -24(fp)
    li a0, 16
    call z_alloc
    sw a0, -28(fp)
    lw t6, -28(fp)
    la t1, __vtable_JugadorIA
    sw t1, 0(t6)
    lw a0, -28(fp)
    la a1, str_12
    li a2, 1
    call constructor_JugadorIA_1
    sw a0, -28(fp)
    lw t1, -28(fp)
    sw t1, -32(fp)
    li a0, 16
    call z_alloc
    sw a0, -36(fp)
    lw t6, -36(fp)
    la t1, __vtable_JugadorIA
    sw t1, 0(t6)
    lw a0, -36(fp)
    la a1, str_13
    li a2, 2
    call constructor_JugadorIA_1
    sw a0, -36(fp)
    lw t1, -36(fp)
    sw t1, -40(fp)
    li a0, 20
    call z_alloc
    sw a0, -44(fp)
    lw t6, -44(fp)
    la t1, __vtable_Partida
    sw t1, 0(t6)
    lw a0, -44(fp)
    lw a1, -16(fp)
    lw a2, -24(fp)
    call constructor_Partida_1
    sw a0, -44(fp)
    lw t1, -44(fp)
    sw t1, -48(fp)
    li a0, 20
    call z_alloc
    sw a0, -52(fp)
    lw t6, -52(fp)
    la t1, __vtable_Partida
    sw t1, 0(t6)
    lw a0, -52(fp)
    lw a1, -32(fp)
    lw a2, -40(fp)
    call constructor_Partida_1
    sw a0, -52(fp)
    lw t1, -52(fp)
    sw t1, -56(fp)
    la a0, str_14
    call z_print_cadena
    call z_print_salto
    lw a0, -16(fp)
    li a1, 0
    li a2, 0
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -60(fp)
    lw a0, -16(fp)
    li a1, 1
    li a2, 0
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -64(fp)
    lw a0, -16(fp)
    li a1, 2
    li a2, 0
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -68(fp)
    lw a0, -48(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -72(fp)
    la a0, str_15
    call z_print_cadena
    call z_print_salto
    lw a0, -56(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -76(fp)
    la a0, str_16
    call z_print_cadena
    lw a0, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -80(fp)
    lw a0, -80(fp)
    call z_print_entero
    call z_print_salto
    la a0, str_17
    call z_print_cadena
    lw a0, -24(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -84(fp)
    lw a0, -84(fp)
    call z_print_entero
    call z_print_salto
    la a0, str_18
    call z_print_cadena
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -88(fp)
    lw a0, -88(fp)
    call z_print_entero
    call z_print_salto
    la a0, str_19
    call z_print_cadena
    lw a0, -40(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -92(fp)
    lw a0, -92(fp)
    call z_print_entero
    call z_print_salto
__epilogo_main:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 92
    mv fp, t0
    ret

.data
__z_nulo: .asciz "(nulo)"
__z_heap: .word 0x10002000
__z_buf: .space 256
.text

# Imprime un salto de linea
z_print_salto:
    li a0, 10
    li a7, 11
    ecall
    ret

# Imprime la cadena en a0
z_print_cadena:
    li a7, 4
    ecall
    ret

# Imprime el entero en a0
z_print_entero:
    li a7, 1
    ecall
    ret

# Imprime un decimal (punto fijo x1000) convirtiendolo con z_ftoa
z_print_decimal:
    addi sp, sp, -4
    sw ra, 0(sp)
    call z_ftoa
    call z_print_cadena
    lw ra, 0(sp)
    addi sp, sp, 4
    ret

# Imprime el caracter en a0
z_print_caracter:
    li a7, 11
    ecall
    ret

# Imprime un booleano como 1 o 0
z_print_booleano:
    li a7, 1
    ecall
    ret

# Imprime "(nulo)"
z_print_nulo:
    li a7, 4
    la a0, __z_nulo
    ecall
    ret

# Lee un entero; resultado en a0
z_leer_entero:
    li a7, 5
    ecall
    ret

# Lee un decimal como texto (ej. -2.5) y lo devuelve x1000 en a0
z_leer_decimal:
    la a0, __z_buf
    li a1, 32
    li a7, 8
    ecall
    la t1, __z_buf
    li t6, 0
    lb t2, 0(t1)
    li t3, 45
    bne t2, t3, L_ld_ent
    li t6, 1
    addi t1, t1, 1
L_ld_ent:
    li t0, 0
L_ld_ent_loop:
    lb t2, 0(t1)
    li t3, 48
    blt t2, t3, L_ld_frac_ini
    li t3, 57
    bgt t2, t3, L_ld_frac_ini
    addi t2, t2, -48
    li t3, 10
    mul t0, t0, t3
    add t0, t0, t2
    addi t1, t1, 1
    j L_ld_ent_loop
L_ld_frac_ini:
    li t4, 1000
    mul t0, t0, t4
    li t3, 46
    bne t2, t3, L_ld_signo
    addi t1, t1, 1
    li t4, 100
L_ld_frac_loop:
    beqz t4, L_ld_signo
    lb t2, 0(t1)
    li t3, 48
    blt t2, t3, L_ld_signo
    li t3, 57
    bgt t2, t3, L_ld_signo
    addi t2, t2, -48
    mul t2, t2, t4
    add t0, t0, t2
    li t3, 10
    div t4, t4, t3
    addi t1, t1, 1
    j L_ld_frac_loop
L_ld_signo:
    beqz t6, L_ld_fin
    neg t0, t0
L_ld_fin:
    mv a0, t0
    ret

# Lee un caracter; resultado en a0
z_leer_caracter:
    li a7, 12
    ecall
    ret

# Lee una linea en el buffer a0 (max a1) y quita el '\n' final
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

# Reserva a0 bytes en el heap; devuelve el puntero en a0
z_alloc:
    li a7, 9
    ecall
    ret

# Longitud de la cadena en a0; resultado en a0
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

# Convierte el entero en a0 a una cadena nueva (con '-' si es negativo)
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

# Convierte el caracter en a0 a una cadena de 1 caracter
z_chr:
    mv t1, a0
    li a0, 2
    li a7, 9
    ecall
    sb t1, 0(a0)
    sb zero, 1(a0)
    ret

# Convierte un decimal (punto fijo x1000) en a0 a cadena, ej. 1.5 o 2000.0
z_ftoa:
    mv t1, a0
    li a0, 16
    li a7, 9
    ecall
    addi t3, a0, 15
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
    li t2, 3
    bnez t4, L_ftoa_trim
    li a0, 48
    addi t3, t3, -1
    sb a0, 0(t3)
    j L_ftoa_dot
L_ftoa_trim:
    rem a0, t4, t1
    bnez a0, L_ftoa_digits
    div t4, t4, t1
    addi t2, t2, -1
    j L_ftoa_trim
L_ftoa_digits:
    rem a0, t4, t1
    addi a0, a0, 48
    addi t3, t3, -1
    sb a0, 0(t3)
    div t4, t4, t1
    addi t2, t2, -1
    bnez t2, L_ftoa_digits
L_ftoa_dot:
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

# Concatena las cadenas a0 y a1 en una cadena nueva; resultado en a0
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

# Termina el programa con codigo 0
z_exit:
    li a0, 0
    li a7, 10
    ecall
