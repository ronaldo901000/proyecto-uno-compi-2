.data
.align 2
str_0: .asciz "Campeon de la arena"
.align 2
str_1: .asciz "Gladiador"
.align 2
str_2: .asciz "Novato"
.align 2
str_3: .asciz "Carne de leon"
.align 2
str_4: .asciz "Jugador: "
.align 2
str_5: .asciz " | Victorias: "
.align 2
str_6: .asciz "Arquero"
.align 2
str_7: .asciz "Guerrero"
.align 2
str_8: .asciz "Robin"
.align 2
str_9: .asciz "Maximus"
.align 2
str_10: .asciz "Merlin"
.align 2
str_11: .asciz " | Ya no quedan rivales"
.align 2
str_12: .asciz " | Entra "
.align 2
str_13: .asciz " "
.align 2
str_14: .asciz " inflige "
.align 2
str_15: .asciz " descansa"
.align 2
str_16: .asciz " | "
.align 2
str_17: .asciz " cae!"
.align 2
str_18: .asciz " -- Has caido"
.align 2
str_19: .asciz "Bienvenido a la arena"
.align 2
str_20: .asciz "Combatiente"
.align 2
str_21: .asciz " | Vida: "
.align 2
str_22: .asciz " | Energia: "
.align 2
str_23: .asciz "/"
.align 2
str_24: .asciz "Anonimo"
.align 2
str_25: .asciz "Mago"
.align 2
str_26: .asciz "Heroe"
.align 2
str_27: .asciz "Nadie"
.align 2
str_28: .asciz "=== ARENA DE COMBATE ==="
.align 2
str_29: .asciz "Tu nombre:"
.align 2
str_30: .asciz "Clase: 1=Guerrero 2=Mago 3=Arquero"
.align 2
str_31: .asciz "Rival: "
.align 2
str_32: .asciz "1=Atacar 2=Descansar 0=Rendirse"
.align 2
str_33: .asciz "Rango: "
.align 2
__vtable_Combatiente:
    .word metodo_Combatiente_configurar_1
    .word metodo_Combatiente_getNombre_2
    .word metodo_Combatiente_getVida_3
    .word metodo_Combatiente_getEnergia_4
    .word metodo_Combatiente_estaVivo_5
    .word metodo_Combatiente_recibirDanio_6
    .word metodo_Combatiente_gastar_7
    .word metodo_Combatiente_recuperar_8
    .word metodo_Combatiente_descansar_9
    .word metodo_Combatiente_clase_10
    .word metodo_Combatiente_atacar_11
    .word metodo_Combatiente_estado_12
.align 2
__vtable_Arquero:
    .word metodo_Combatiente_configurar_1
    .word metodo_Combatiente_getNombre_2
    .word metodo_Combatiente_getVida_3
    .word metodo_Combatiente_getEnergia_4
    .word metodo_Combatiente_estaVivo_5
    .word metodo_Combatiente_recibirDanio_6
    .word metodo_Combatiente_gastar_7
    .word metodo_Combatiente_recuperar_8
    .word metodo_Combatiente_descansar_9
    .word metodo_Arquero_clase_1
    .word metodo_Arquero_atacar_2
    .word metodo_Combatiente_estado_12
.align 2
__vtable_Mago:
    .word metodo_Combatiente_configurar_1
    .word metodo_Combatiente_getNombre_2
    .word metodo_Combatiente_getVida_3
    .word metodo_Combatiente_getEnergia_4
    .word metodo_Combatiente_estaVivo_5
    .word metodo_Combatiente_recibirDanio_6
    .word metodo_Combatiente_gastar_7
    .word metodo_Combatiente_recuperar_8
    .word metodo_Mago_descansar_3
    .word metodo_Mago_clase_1
    .word metodo_Mago_atacar_2
    .word metodo_Combatiente_estado_12
.align 2
__vtable_Guerrero:
    .word metodo_Combatiente_configurar_1
    .word metodo_Combatiente_getNombre_2
    .word metodo_Combatiente_getVida_3
    .word metodo_Combatiente_getEnergia_4
    .word metodo_Combatiente_estaVivo_5
    .word metodo_Combatiente_recibirDanio_6
    .word metodo_Combatiente_gastar_7
    .word metodo_Combatiente_recuperar_8
    .word metodo_Combatiente_descansar_9
    .word metodo_Guerrero_clase_1
    .word metodo_Guerrero_atacar_2
    .word metodo_Combatiente_estado_12
.align 2
__vtable_Arena:
    .word metodo_Arena_getHeroe_1
    .word metodo_Arena_getRival_2
    .word metodo_Arena_getVictorias_3
    .word metodo_Arena_estaTerminada_4
    .word metodo_Arena_getUltimoLog_5
    .word metodo_Arena_turno_8
.text
.globl main
main:
    call __entrada_main
    call z_exit

fun_clasificar:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    lw t1, -12(fp)
    li t2, 3
    slt t3, t1, t2
    xori t3, t3, 1
    sw t3, -16(fp)
    lw t1, -16(fp)
    bnez t1, L3
    j L2
L3:
    la a0, str_0
    j __epilogo_fun_clasificar
    j L1
L2:
    lw t1, -12(fp)
    li t2, 2
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L5
    j L4
L5:
    la a0, str_1
    j __epilogo_fun_clasificar
    j L1
L4:
    lw t1, -12(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L7
    j L6
L7:
    la a0, str_2
    j __epilogo_fun_clasificar
    j L1
L6:
    la a0, str_3
    j __epilogo_fun_clasificar
L1:
    j __epilogo_fun_clasificar
__epilogo_fun_clasificar:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

fun_registrar:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    sw t1, 0(t6)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    j __epilogo_fun_registrar
__epilogo_fun_registrar:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

fun_imprimirPuntaje:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
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
    lw a0, -24(fp)
    call z_print_cadena
    call z_print_salto
    j __epilogo_fun_imprimirPuntaje
__epilogo_fun_imprimirPuntaje:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

metodo_Arquero_clase_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    la a0, str_6
    j __epilogo_metodo_Arquero_clase_1
    j __epilogo_metodo_Arquero_clase_1
__epilogo_metodo_Arquero_clase_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Arquero_atacar_2:
    addi sp, sp, -48
    sw ra, 44(sp)
    sw fp, 40(sp)
    addi fp, sp, 48
    sw a0, -12(fp)
    sw a1, -16(fp)
    li t1, 2
    sw t1, -20(fp)
    lw a0, -12(fp)
    li a1, 1
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 24(t0)
    jalr t0
    sw a0, -24(fp)
    lw t1, -24(fp)
    bnez t1, L9
    j L8
L9:
    li t1, 3
    sw t1, -20(fp)
L8:
    li t1, 0
    sw t1, -28(fp)
    li t1, 0
    sw t1, -32(fp)
L10:
    lw t1, -32(fp)
    lw t2, -20(fp)
    slt t3, t1, t2
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L11
    j L13
L11:
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t2, 2
    div t3, t1, t2
    sw t3, -40(fp)
    lw t1, -28(fp)
    lw t2, -40(fp)
    add t3, t1, t2
    sw t3, -44(fp)
    lw t1, -44(fp)
    sw t1, -28(fp)
L12:
    lw t1, -32(fp)
    addi t3, t1, 1
    sw t3, -32(fp)
    j L10
L13:
    lw a0, -16(fp)
    lw a1, -28(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -48(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t3, 0(t6)
    lw a0, -28(fp)
    j __epilogo_metodo_Arquero_atacar_2
    j __epilogo_metodo_Arquero_atacar_2
__epilogo_metodo_Arquero_atacar_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 48
    mv fp, t0
    ret

constructor_Arquero_1:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    li a2, 100
    li a3, 10
    li a4, 6
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -20(fp)
    j __epilogo_constructor_Arquero_1
__epilogo_constructor_Arquero_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Guerrero_clase_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    la a0, str_7
    j __epilogo_metodo_Guerrero_clase_1
    j __epilogo_metodo_Guerrero_clase_1
__epilogo_metodo_Guerrero_clase_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Guerrero_atacar_2:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    sw t1, -20(fp)
    lw a0, -12(fp)
    li a1, 2
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 24(t0)
    jalr t0
    sw a0, -24(fp)
    lw t1, -24(fp)
    bnez t1, L15
    j L14
L15:
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t2, 2
    mul t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    sw t1, -20(fp)
L14:
    lw a0, -16(fp)
    lw a1, -20(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -32(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t3, 0(t6)
    lw a0, -20(fp)
    j __epilogo_metodo_Guerrero_atacar_2
    j __epilogo_metodo_Guerrero_atacar_2
__epilogo_metodo_Guerrero_atacar_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

constructor_Guerrero_1:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    li a2, 120
    li a3, 14
    li a4, 4
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -20(fp)
    j __epilogo_constructor_Guerrero_1
__epilogo_constructor_Guerrero_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Arena_getHeroe_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    j __epilogo_metodo_Arena_getHeroe_1
    j __epilogo_metodo_Arena_getHeroe_1
__epilogo_metodo_Arena_getHeroe_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Arena_getRival_2:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    j __epilogo_metodo_Arena_getRival_2
    j __epilogo_metodo_Arena_getRival_2
__epilogo_metodo_Arena_getRival_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Arena_getVictorias_3:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw a0, 0(t6)
    j __epilogo_metodo_Arena_getVictorias_3
    j __epilogo_metodo_Arena_getVictorias_3
__epilogo_metodo_Arena_getVictorias_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Arena_estaTerminada_4:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    lb a0, 0(t6)
    j __epilogo_metodo_Arena_estaTerminada_4
    j __epilogo_metodo_Arena_estaTerminada_4
__epilogo_metodo_Arena_estaTerminada_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Arena_getUltimoLog_5:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw a0, 0(t6)
    j __epilogo_metodo_Arena_getUltimoLog_5
    j __epilogo_metodo_Arena_getUltimoLog_5
__epilogo_metodo_Arena_getUltimoLog_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Arena_crearRival_6:
    addi sp, sp, -44
    sw ra, 40(sp)
    sw fp, 36(sp)
    addi fp, sp, 44
    sw a0, -12(fp)
    sw a1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
    lw t1, -16(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L17
    lw t1, -16(fp)
    li t2, 2
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L18
    lw t1, -16(fp)
    li t2, 3
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L19
    j L20
L17:
    li a0, 28
    call z_alloc
    sw a0, -36(fp)
    lw t6, -36(fp)
    la t1, __vtable_Arquero
    sw t1, 0(t6)
    lw a0, -36(fp)
    la a1, str_8
    call constructor_Arquero_1
    sw a0, -36(fp)
    lw t1, -36(fp)
    sw t1, -20(fp)
    j L16
L18:
    li a0, 28
    call z_alloc
    sw a0, -40(fp)
    lw t6, -40(fp)
    la t1, __vtable_Guerrero
    sw t1, 0(t6)
    lw a0, -40(fp)
    la a1, str_9
    call constructor_Guerrero_1
    sw a0, -40(fp)
    lw t1, -40(fp)
    sw t1, -20(fp)
    j L16
L19:
    li a0, 28
    call z_alloc
    sw a0, -44(fp)
    lw t6, -44(fp)
    la t1, __vtable_Mago
    sw t1, 0(t6)
    lw a0, -44(fp)
    la a1, str_10
    call constructor_Mago_1
    sw a0, -44(fp)
    lw t1, -44(fp)
    sw t1, -20(fp)
    j L16
L20:
    li t1, 0
    sw t1, -20(fp)
L16:
    lw a0, -20(fp)
    j __epilogo_metodo_Arena_crearRival_6
    j __epilogo_metodo_Arena_crearRival_6
__epilogo_metodo_Arena_crearRival_6:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 44
    mv fp, t0
    ret

metodo_Arena_siguienteRival_7:
    addi sp, sp, -48
    sw ra, 44(sp)
    sw fp, 40(sp)
    addi fp, sp, 48
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t3, 0(t6)
    lw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw a1, 0(t6)
    call metodo_Arena_crearRival_6
    sw a0, -16(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L23
    j L22
L23:
    li t1, 1
    lw t6, -12(fp)
    addi t6, t6, 20
    sb t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    la t2, str_11
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
    sw a0, -24(fp)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j L21
L22:
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    la t2, str_12
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
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 36(t0)
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
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -36(fp)
    lw t1, -36(fp)
    la t2, str_13
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
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -44(fp)
    lw t1, -40(fp)
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
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
L21:
    j __epilogo_metodo_Arena_siguienteRival_7
__epilogo_metodo_Arena_siguienteRival_7:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 48
    mv fp, t0
    ret

metodo_Arena_turno_8:
    addi sp, sp, -144
    sw ra, 140(sp)
    sw fp, 136(sp)
    addi fp, sp, 144
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    lb t1, 0(t6)
    bnez t1, L25
    j L24
L25:
    j __epilogo_metodo_Arena_turno_8
L24:
    li t1, 0
    sw t1, -20(fp)
    lw t1, -16(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L28
    j L27
L28:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a1, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 40(t0)
    jalr t0
    sw a0, -28(fp)
    lw t1, -28(fp)
    sw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -32(fp)
    lw t1, -32(fp)
    la t2, str_14
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
    sw a0, -36(fp)
    lw t1, -36(fp)
    lw t2, -20(fp)
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
    sw a0, -40(fp)
    lw t1, -40(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j L26
L27:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 32(t0)
    jalr t0
    sw a0, -44(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -48(fp)
    lw t1, -48(fp)
    la t2, str_15
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
    sw a0, -52(fp)
    lw t1, -52(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
L26:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -56(fp)
    lw t1, -56(fp)
    seqz t3, t1
    sw t3, -60(fp)
    lw t1, -60(fp)
    bnez t1, L30
    j L29
L30:
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t3, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    la t2, str_16
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
    sw a0, -64(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -68(fp)
    lw t1, -64(fp)
    lw t2, -68(fp)
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
    lw t1, -72(fp)
    la t2, str_17
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
    sw a0, -76(fp)
    lw t1, -76(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    lw a0, -12(fp)
    call metodo_Arena_siguienteRival_7
    sw a0, -80(fp)
    j __epilogo_metodo_Arena_turno_8
L29:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -84(fp)
    lw t1, -84(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -88(fp)
    lw t1, -88(fp)
    bnez t1, L33
    j L32
L33:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 32(t0)
    jalr t0
    sw a0, -92(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    la t2, str_16
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
    sw a0, -96(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -100(fp)
    lw t1, -96(fp)
    lw t2, -100(fp)
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
    sw a0, -104(fp)
    lw t1, -104(fp)
    la t2, str_15
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
    sw a0, -108(fp)
    lw t1, -108(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j L31
L32:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a1, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 40(t0)
    jalr t0
    sw a0, -112(fp)
    lw t1, -112(fp)
    sw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    la t2, str_16
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
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -120(fp)
    lw t1, -116(fp)
    lw t2, -120(fp)
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
    sw a0, -124(fp)
    lw t1, -124(fp)
    la t2, str_14
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
    sw a0, -128(fp)
    lw t1, -128(fp)
    lw t2, -20(fp)
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
    sw a0, -132(fp)
    lw t1, -132(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
L31:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -136(fp)
    lw t1, -136(fp)
    seqz t3, t1
    sw t3, -140(fp)
    lw t1, -140(fp)
    bnez t1, L35
    j L34
L35:
    li t1, 1
    lw t6, -12(fp)
    addi t6, t6, 20
    sb t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    la t2, str_18
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
    sw a0, -144(fp)
    lw t1, -144(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
L34:
    j __epilogo_metodo_Arena_turno_8
__epilogo_metodo_Arena_turno_8:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 144
    mv fp, t0
    ret

constructor_Arena_1:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 20
    sb t1, 0(t6)
    la t1, str_19
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    lw a0, -12(fp)
    call metodo_Arena_siguienteRival_7
    sw a0, -20(fp)
    j __epilogo_constructor_Arena_1
__epilogo_constructor_Arena_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Combatiente_configurar_1:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    sw a3, -24(fp)
    sw a4, -28(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
    lw t1, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    sw t1, 0(t6)
    lw t1, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j __epilogo_metodo_Combatiente_configurar_1
__epilogo_metodo_Combatiente_configurar_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

metodo_Combatiente_getNombre_2:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    j __epilogo_metodo_Combatiente_getNombre_2
    j __epilogo_metodo_Combatiente_getNombre_2
__epilogo_metodo_Combatiente_getNombre_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Combatiente_getVida_3:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    j __epilogo_metodo_Combatiente_getVida_3
    j __epilogo_metodo_Combatiente_getVida_3
__epilogo_metodo_Combatiente_getVida_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Combatiente_getEnergia_4:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw a0, 0(t6)
    j __epilogo_metodo_Combatiente_getEnergia_4
    j __epilogo_metodo_Combatiente_getEnergia_4
__epilogo_metodo_Combatiente_getEnergia_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Combatiente_estaVivo_5:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t2, 0
    slt t3, t2, t1
    sw t3, -16(fp)
    lw a0, -16(fp)
    j __epilogo_metodo_Combatiente_estaVivo_5
    j __epilogo_metodo_Combatiente_estaVivo_5
__epilogo_metodo_Combatiente_estaVivo_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Combatiente_recibirDanio_6:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    lw t2, -16(fp)
    sub t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t2, 0
    slt t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L37
    j L36
L37:
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
L36:
    j __epilogo_metodo_Combatiente_recibirDanio_6
__epilogo_metodo_Combatiente_recibirDanio_6:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

metodo_Combatiente_gastar_7:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t1, 0(t6)
    lw t2, -16(fp)
    slt t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L39
    j L38
L39:
    li a0, 0
    j __epilogo_metodo_Combatiente_gastar_7
L38:
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t1, 0(t6)
    lw t2, -16(fp)
    sub t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
    li a0, 1
    j __epilogo_metodo_Combatiente_gastar_7
    j __epilogo_metodo_Combatiente_gastar_7
__epilogo_metodo_Combatiente_gastar_7:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

metodo_Combatiente_recuperar_8:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t1, 0(t6)
    lw t2, -16(fp)
    add t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 20
    lw t2, 0(t6)
    slt t3, t2, t1
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L41
    j L40
L41:
    lw t6, -12(fp)
    addi t6, t6, 20
    lw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
L40:
    j __epilogo_metodo_Combatiente_recuperar_8
__epilogo_metodo_Combatiente_recuperar_8:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

metodo_Combatiente_descansar_9:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    lw a0, -12(fp)
    li a1, 2
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -16(fp)
    j __epilogo_metodo_Combatiente_descansar_9
__epilogo_metodo_Combatiente_descansar_9:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Combatiente_clase_10:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    la a0, str_20
    j __epilogo_metodo_Combatiente_clase_10
    j __epilogo_metodo_Combatiente_clase_10
__epilogo_metodo_Combatiente_clase_10:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Combatiente_atacar_11:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    sw t1, -20(fp)
    lw a0, -16(fp)
    lw a1, -20(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t3, 0(t6)
    lw a0, -20(fp)
    j __epilogo_metodo_Combatiente_atacar_11
    j __epilogo_metodo_Combatiente_atacar_11
__epilogo_metodo_Combatiente_atacar_11:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

metodo_Combatiente_estado_12:
    addi sp, sp, -48
    sw ra, 44(sp)
    sw fp, 40(sp)
    addi fp, sp, 48
    sw a0, -12(fp)
    lw a0, -12(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 36(t0)
    jalr t0
    sw a0, -16(fp)
    lw t1, -16(fp)
    la t2, str_13
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
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -24(fp)
    lw t1, -24(fp)
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
    lw t1, -32(fp)
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
    sw a0, -36(fp)
    lw t1, -36(fp)
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
    sw a0, -40(fp)
    lw t1, -40(fp)
    la t2, str_23
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
    lw t6, -12(fp)
    addi t6, t6, 20
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
    sw a0, -48(fp)
    lw a0, -48(fp)
    j __epilogo_metodo_Combatiente_estado_12
    j __epilogo_metodo_Combatiente_estado_12
__epilogo_metodo_Combatiente_estado_12:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 48
    mv fp, t0
    ret

constructor_Combatiente_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    la t1, str_24
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    li t1, 100
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    li t1, 10
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
    li t1, 5
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
    li t1, 5
    lw t6, -12(fp)
    addi t6, t6, 20
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j __epilogo_constructor_Combatiente_1
__epilogo_constructor_Combatiente_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Mago_clase_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    la a0, str_25
    j __epilogo_metodo_Mago_clase_1
    j __epilogo_metodo_Mago_clase_1
__epilogo_metodo_Mago_clase_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Mago_atacar_2:
    addi sp, sp, -36
    sw ra, 32(sp)
    sw fp, 28(sp)
    addi fp, sp, 36
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t2, 2
    div t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    sw t1, -24(fp)
    lw a0, -12(fp)
    li a1, 3
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 24(t0)
    jalr t0
    sw a0, -28(fp)
    lw t1, -28(fp)
    bnez t1, L43
    j L42
L43:
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t2, 3
    mul t3, t1, t2
    sw t3, -32(fp)
    lw t1, -32(fp)
    sw t1, -24(fp)
L42:
    lw a0, -16(fp)
    lw a1, -24(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -36(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t3, 0(t6)
    lw a0, -24(fp)
    j __epilogo_metodo_Mago_atacar_2
    j __epilogo_metodo_Mago_atacar_2
__epilogo_metodo_Mago_atacar_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 36
    mv fp, t0
    ret

metodo_Mago_descansar_3:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    lw a0, -12(fp)
    li a1, 4
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -16(fp)
    j __epilogo_metodo_Mago_descansar_3
__epilogo_metodo_Mago_descansar_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

constructor_Mago_1:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    li a2, 80
    li a3, 12
    li a4, 8
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -20(fp)
    j __epilogo_constructor_Mago_1
__epilogo_constructor_Mago_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

__entrada_main:
    addi sp, sp, -124
    sw ra, 120(sp)
    sw fp, 116(sp)
    addi fp, sp, 124
    la t1, str_26
    sw t1, -12(fp)
    li t1, 0
    sw t1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
    li t1, 0
    sw t1, -24(fp)
    li t1, 0
    sw t1, -28(fp)
    li t1, 0
    sw t1, -32(fp)
    la t1, str_27
    addi t6, fp, -40
    sw t1, 0(t6)
    li t1, 0
    addi t6, fp, -40
    addi t6, t6, 4
    sw t1, 0(t6)
    la a0, str_28
    call z_print_cadena
    call z_print_salto
    la a0, str_29
    call z_print_cadena
    call z_print_salto
    li a0, 256
    call z_alloc
    mv t0, a0
    li a1, 256
    call z_leer_cadena
    mv a0, t0
    sw a0, -12(fp)
L44:
    lw t1, -24(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -44(fp)
    lw t1, -44(fp)
    bnez t1, L45
    j L46
L45:
    la a0, str_30
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -16(fp)
    lw t1, -16(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -48(fp)
    lw t1, -48(fp)
    bnez t1, L48
    j L47
L48:
    li a0, 28
    call z_alloc
    sw a0, -52(fp)
    lw t6, -52(fp)
    la t1, __vtable_Guerrero
    sw t1, 0(t6)
    lw a0, -52(fp)
    lw a1, -12(fp)
    call constructor_Guerrero_1
    sw a0, -52(fp)
    lw t1, -52(fp)
    sw t1, -24(fp)
L47:
    lw t1, -16(fp)
    li t2, 2
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -56(fp)
    lw t1, -56(fp)
    bnez t1, L50
    j L49
L50:
    li a0, 28
    call z_alloc
    sw a0, -60(fp)
    lw t6, -60(fp)
    la t1, __vtable_Mago
    sw t1, 0(t6)
    lw a0, -60(fp)
    lw a1, -12(fp)
    call constructor_Mago_1
    sw a0, -60(fp)
    lw t1, -60(fp)
    sw t1, -24(fp)
L49:
    lw t1, -16(fp)
    li t2, 3
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -64(fp)
    lw t1, -64(fp)
    bnez t1, L52
    j L51
L52:
    li a0, 28
    call z_alloc
    sw a0, -68(fp)
    lw t6, -68(fp)
    la t1, __vtable_Arquero
    sw t1, 0(t6)
    lw a0, -68(fp)
    lw a1, -12(fp)
    call constructor_Arquero_1
    sw a0, -68(fp)
    lw t1, -68(fp)
    sw t1, -24(fp)
L51:
    j L44
L46:
    li a0, 28
    call z_alloc
    sw a0, -72(fp)
    lw t6, -72(fp)
    la t1, __vtable_Arena
    sw t1, 0(t6)
    lw a0, -72(fp)
    lw a1, -24(fp)
    call constructor_Arena_1
    sw a0, -72(fp)
    lw t1, -72(fp)
    sw t1, -28(fp)
L53:
    lw a0, -28(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -76(fp)
    lw t1, -76(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -80(fp)
    lw t1, -80(fp)
    bnez t1, L54
    j L55
L54:
    lw a0, -28(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -84(fp)
    lw t1, -84(fp)
    sw t1, -32(fp)
    lw a0, -24(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 44(t0)
    jalr t0
    sw a0, -88(fp)
    lw a0, -88(fp)
    call z_print_cadena
    call z_print_salto
    la a0, str_31
    call z_print_cadena
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 44(t0)
    jalr t0
    sw a0, -92(fp)
    lw a0, -92(fp)
    call z_print_cadena
    call z_print_salto
    la a0, str_32
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -20(fp)
    lw t1, -20(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -96(fp)
    lw t1, -96(fp)
    bnez t1, L57
    j L56
L57:
    j L55
L56:
    lw a0, -28(fp)
    lw a1, -20(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -100(fp)
    lw a0, -28(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -104(fp)
    lw a0, -104(fp)
    call z_print_cadena
    call z_print_salto
    j L53
L55:
    lw a0, -28(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -108(fp)
    addi a0, fp, -40
    lw a1, -12(fp)
    lw a2, -108(fp)
    call fun_registrar
    sw a0, -112(fp)
    addi a0, fp, -40
    call fun_imprimirPuntaje
    sw a0, -116(fp)
    la a0, str_33
    call z_print_cadena
    lw a0, -28(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -120(fp)
    lw a0, -120(fp)
    call fun_clasificar
    sw a0, -124(fp)
    lw a0, -124(fp)
    call z_print_cadena
    call z_print_salto
__epilogo_main:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 124
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

# Lee un entero y lo escala x1000 (punto fijo); resultado en a0
z_leer_decimal:
    li a7, 5
    ecall
    li t1, 1000
    mul a0, a0, t1
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
