.data
.align 2
str_0: .asciz " "
.align 2
str_1: .asciz " HP="
.align 2
str_2: .asciz "/"
.align 2
dbl_0: .word 100000
.align 2
dbl_1: .word 750
.align 2
str_3: .asciz " MP="
.align 2
str_4: .asciz " ORO="
.align 2
dbl_2: .word 1500
.align 2
str_5: .asciz " golpea a "
.align 2
str_6: .asciz " por "
.align 2
str_7: .asciz " (HP "
.align 2
str_8: .asciz ")"
.align 2
str_9: .asciz "  >> "
.align 2
str_10: .asciz " ha caido"
.align 2
str_11: .asciz "=== Ronda "
.align 2
str_12: .asciz " ==="
.align 2
str_13: .asciz "Bajas retiradas: "
.align 2
str_14: .asciz "--- Resumen ---"
.align 2
str_15: .asciz "Heroes vivos: "
.align 2
str_16: .asciz "Enemigos vivos: "
.align 2
str_17: .asciz "Tick global: "
.align 2
dbl_3: .word 50000
.align 2
dbl_4: .word 2000000
.align 2
dbl_5: .word 1000000
.align 2
dbl_6: .word 7500
.align 2
dbl_7: .word 2500
.align 2
dbl_8: .word 4000
.align 2
dbl_9: .word 100
.align 2
dbl_10: .word 0
.align 2
dbl_11: .word 12500
.align 2
dbl_12: .word 3000000
.align 2
dbl_13: .word 2000
.align 2
str_18: .asciz "50*50 = "
.align 2
str_19: .asciz "2000*1000 = "
.align 2
str_20: .asciz "7.5/2.5 = "
.align 2
str_21: .asciz "10/4.0 = "
.align 2
str_22: .asciz "0.1*0.1 = "
.align 2
str_23: .asciz "-12.5*4 = "
.align 2
str_24: .asciz "3000/2.0 = "
.align 2
str_25: .asciz ""
.align 2
str_26: .asciz "Dragon"
.align 2
str_27: .asciz "[JEFE] "
.align 2
str_28: .asciz "Conan"
.align 2
str_29: .asciz "Merlin"
.align 2
str_30: .asciz "Legolas"
.align 2
str_31: .asciz "Grunk"
.align 2
str_32: .asciz "Azog"
.align 2
str_33: .asciz "Estado inicial:"
.align 2
str_34: .asciz "Checksum: "
.align 2
str_35: .asciz "Puntaje arquero: "
.align 2
str_36: .asciz "Suerte mago: "
.align 2
str_37: .asciz "Hechizos: "
.align 2
str_38: .asciz "Oro guerrero: "
.align 2
str_39: .asciz "Comparar: "
.align 2
__vtable_Arquero:
    .word metodo_Entidad_getId_1
    .word metodo_Entidad_getNombre_2
    .word metodo_Entidad_getVida_3
    .word metodo_Entidad_getFila_4
    .word metodo_Entidad_getCol_5
    .word metodo_Entidad_getSimbolo_6
    .word metodo_Entidad_getTurnos_7
    .word metodo_Entidad_estaVivo_8
    .word metodo_Entidad_colocarEn_9
    .word metodo_Arquero_calcularDano_1
    .word metodo_Entidad_atacar_11
    .word metodo_Entidad_recibirDano_12
    .word metodo_Entidad_comparar_14
    .word metodo_Personaje_describir_6
    .word metodo_Entidad_tick_16
    .word metodo_Personaje_getOro_1
    .word metodo_Personaje_getMana_2
    .word metodo_Personaje_getSuerte_3
    .word metodo_Personaje_ganarOro_4
    .word metodo_Arquero_getPuntaje_2
    .word metodo_Arquero_getFlechas_3
.align 2
__vtable_Tablero:
    .word metodo_Tablero_colocar_2
    .word metodo_Tablero_mover_3
    .word metodo_Tablero_en_4
    .word metodo_Tablero_limpiarMuertos_5
    .word metodo_Tablero_contar_6
    .word metodo_Tablero_imprimir_7
.align 2
__vtable_Enemigo:
    .word metodo_Entidad_getId_1
    .word metodo_Entidad_getNombre_2
    .word metodo_Entidad_getVida_3
    .word metodo_Entidad_getFila_4
    .word metodo_Entidad_getCol_5
    .word metodo_Entidad_getSimbolo_6
    .word metodo_Entidad_getTurnos_7
    .word metodo_Entidad_estaVivo_8
    .word metodo_Entidad_colocarEn_9
    .word metodo_Enemigo_calcularDano_2
    .word metodo_Entidad_atacar_11
    .word metodo_Entidad_recibirDano_12
    .word metodo_Entidad_comparar_14
    .word metodo_Entidad_describir_15
    .word metodo_Entidad_tick_16
    .word metodo_Enemigo_getRecompensa_1
.align 2
__vtable_Partida:
    .word metodo_Partida_agregarHeroe_1
    .word metodo_Partida_agregarEnemigo_2
    .word metodo_Partida_jugar_7
    .word metodo_Partida_mostrar_8
    .word metodo_Partida_resumen_9
    .word metodo_Partida_checksum_10
    .word metodo_Partida_pruebaDecimal_11
.align 2
__vtable_Reloj:
    .word metodo_Reloj_avanzar_1
    .word metodo_Reloj_leerVidaDirecta_2
    .word metodo_Reloj_leerTurnosDirecto_3
    .word metodo_Reloj_getGlobal_4
.align 2
__vtable_Guerrero:
    .word metodo_Entidad_getId_1
    .word metodo_Entidad_getNombre_2
    .word metodo_Entidad_getVida_3
    .word metodo_Entidad_getFila_4
    .word metodo_Entidad_getCol_5
    .word metodo_Entidad_getSimbolo_6
    .word metodo_Entidad_getTurnos_7
    .word metodo_Entidad_estaVivo_8
    .word metodo_Entidad_colocarEn_9
    .word metodo_Guerrero_calcularDano_1
    .word metodo_Entidad_atacar_11
    .word metodo_Guerrero_recibirDano_2
    .word metodo_Entidad_comparar_14
    .word metodo_Personaje_describir_6
    .word metodo_Entidad_tick_16
    .word metodo_Personaje_getOro_1
    .word metodo_Personaje_getMana_2
    .word metodo_Personaje_getSuerte_3
    .word metodo_Personaje_ganarOro_4
    .word metodo_Guerrero_subirArmadura_3
.align 2
__vtable_Orco:
    .word metodo_Entidad_getId_1
    .word metodo_Entidad_getNombre_2
    .word metodo_Entidad_getVida_3
    .word metodo_Entidad_getFila_4
    .word metodo_Entidad_getCol_5
    .word metodo_Entidad_getSimbolo_6
    .word metodo_Entidad_getTurnos_7
    .word metodo_Entidad_estaVivo_8
    .word metodo_Entidad_colocarEn_9
    .word metodo_Orco_calcularDano_1
    .word metodo_Entidad_atacar_11
    .word metodo_Entidad_recibirDano_12
    .word metodo_Entidad_comparar_14
    .word metodo_Entidad_describir_15
    .word metodo_Entidad_tick_16
    .word metodo_Enemigo_getRecompensa_1
.align 2
__vtable_Personaje:
    .word metodo_Entidad_getId_1
    .word metodo_Entidad_getNombre_2
    .word metodo_Entidad_getVida_3
    .word metodo_Entidad_getFila_4
    .word metodo_Entidad_getCol_5
    .word metodo_Entidad_getSimbolo_6
    .word metodo_Entidad_getTurnos_7
    .word metodo_Entidad_estaVivo_8
    .word metodo_Entidad_colocarEn_9
    .word metodo_Personaje_calcularDano_5
    .word metodo_Entidad_atacar_11
    .word metodo_Entidad_recibirDano_12
    .word metodo_Entidad_comparar_14
    .word metodo_Personaje_describir_6
    .word metodo_Entidad_tick_16
    .word metodo_Personaje_getOro_1
    .word metodo_Personaje_getMana_2
    .word metodo_Personaje_getSuerte_3
    .word metodo_Personaje_ganarOro_4
.align 2
__vtable_Dragon:
    .word metodo_Entidad_getId_1
    .word metodo_Entidad_getNombre_2
    .word metodo_Entidad_getVida_3
    .word metodo_Entidad_getFila_4
    .word metodo_Entidad_getCol_5
    .word metodo_Entidad_getSimbolo_6
    .word metodo_Entidad_getTurnos_7
    .word metodo_Entidad_estaVivo_8
    .word metodo_Entidad_colocarEn_9
    .word metodo_Dragon_calcularDano_1
    .word metodo_Entidad_atacar_11
    .word metodo_Dragon_recibirDano_2
    .word metodo_Entidad_comparar_14
    .word metodo_Entidad_describir_15
    .word metodo_Entidad_tick_16
    .word metodo_Enemigo_getRecompensa_1
.align 2
__vtable_Ayudante:
    .word metodo_Ayudante_doble_1
.align 2
__vtable_Mago:
    .word metodo_Entidad_getId_1
    .word metodo_Entidad_getNombre_2
    .word metodo_Entidad_getVida_3
    .word metodo_Entidad_getFila_4
    .word metodo_Entidad_getCol_5
    .word metodo_Entidad_getSimbolo_6
    .word metodo_Entidad_getTurnos_7
    .word metodo_Entidad_estaVivo_8
    .word metodo_Entidad_colocarEn_9
    .word metodo_Mago_calcularDano_1
    .word metodo_Entidad_atacar_11
    .word metodo_Entidad_recibirDano_12
    .word metodo_Entidad_comparar_14
    .word metodo_Personaje_describir_6
    .word metodo_Entidad_tick_16
    .word metodo_Personaje_getOro_1
    .word metodo_Personaje_getMana_2
    .word metodo_Personaje_getSuerte_3
    .word metodo_Personaje_ganarOro_4
    .word metodo_Mago_getHechizos_2
.align 2
__vtable_OrcoJefe:
    .word metodo_Entidad_getId_1
    .word metodo_Entidad_getNombre_2
    .word metodo_Entidad_getVida_3
    .word metodo_Entidad_getFila_4
    .word metodo_Entidad_getCol_5
    .word metodo_Entidad_getSimbolo_6
    .word metodo_Entidad_getTurnos_7
    .word metodo_Entidad_estaVivo_8
    .word metodo_Entidad_colocarEn_9
    .word metodo_OrcoJefe_calcularDano_1
    .word metodo_Entidad_atacar_11
    .word metodo_Entidad_recibirDano_12
    .word metodo_Entidad_comparar_14
    .word metodo_OrcoJefe_describir_2
    .word metodo_Entidad_tick_16
    .word metodo_Enemigo_getRecompensa_1
.align 2
__vtable_Entidad:
    .word metodo_Entidad_getId_1
    .word metodo_Entidad_getNombre_2
    .word metodo_Entidad_getVida_3
    .word metodo_Entidad_getFila_4
    .word metodo_Entidad_getCol_5
    .word metodo_Entidad_getSimbolo_6
    .word metodo_Entidad_getTurnos_7
    .word metodo_Entidad_estaVivo_8
    .word metodo_Entidad_colocarEn_9
    .word metodo_Entidad_calcularDano_10
    .word metodo_Entidad_atacar_11
    .word metodo_Entidad_recibirDano_12
    .word metodo_Entidad_comparar_14
    .word metodo_Entidad_describir_15
    .word metodo_Entidad_tick_16
.text
.globl main
main:
    call __entrada_main
    call z_exit

metodo_Ayudante_doble_1:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t1, -16(fp)
    li t2, 2
    mul t3, t1, t2
    sw t3, -20(fp)
    lw a0, -20(fp)
    j __epilogo_metodo_Ayudante_doble_1
    j __epilogo_metodo_Ayudante_doble_1
__epilogo_metodo_Ayudante_doble_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

constructor_Ayudante_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    j __epilogo_constructor_Ayudante_1
__epilogo_constructor_Ayudante_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Reloj_avanzar_1:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw a0, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 56(t0)
    jalr t0
    sw a0, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    li a1, 1
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    lw t2, -24(fp)
    add t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    li t2, 1
    sub t3, t1, t2
    sw t3, -32(fp)
    lw t1, -32(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    j __epilogo_metodo_Reloj_avanzar_1
__epilogo_metodo_Reloj_avanzar_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

metodo_Reloj_leerVidaDirecta_2:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -16(fp)
    addi t6, t6, 12
    lw a0, 0(t6)
    j __epilogo_metodo_Reloj_leerVidaDirecta_2
    j __epilogo_metodo_Reloj_leerVidaDirecta_2
__epilogo_metodo_Reloj_leerVidaDirecta_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Reloj_leerTurnosDirecto_3:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -16(fp)
    addi t6, t6, 32
    lw a0, 0(t6)
    j __epilogo_metodo_Reloj_leerTurnosDirecto_3
    j __epilogo_metodo_Reloj_leerTurnosDirecto_3
__epilogo_metodo_Reloj_leerTurnosDirecto_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Reloj_getGlobal_4:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    j __epilogo_metodo_Reloj_getGlobal_4
    j __epilogo_metodo_Reloj_getGlobal_4
__epilogo_metodo_Reloj_getGlobal_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

constructor_Reloj_1:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    li a0, 4
    call z_alloc
    sw a0, -16(fp)
    lw t6, -16(fp)
    la t1, __vtable_Ayudante
    sw t1, 0(t6)
    lw a0, -16(fp)
    call constructor_Ayudante_1
    sw a0, -16(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    j __epilogo_constructor_Reloj_1
__epilogo_constructor_Reloj_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Entidad_getId_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    j __epilogo_metodo_Entidad_getId_1
    j __epilogo_metodo_Entidad_getId_1
__epilogo_metodo_Entidad_getId_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Entidad_getNombre_2:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    j __epilogo_metodo_Entidad_getNombre_2
    j __epilogo_metodo_Entidad_getNombre_2
__epilogo_metodo_Entidad_getNombre_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Entidad_getVida_3:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw a0, 0(t6)
    j __epilogo_metodo_Entidad_getVida_3
    j __epilogo_metodo_Entidad_getVida_3
__epilogo_metodo_Entidad_getVida_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Entidad_getFila_4:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    lw a0, 0(t6)
    j __epilogo_metodo_Entidad_getFila_4
    j __epilogo_metodo_Entidad_getFila_4
__epilogo_metodo_Entidad_getFila_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Entidad_getCol_5:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw a0, 0(t6)
    j __epilogo_metodo_Entidad_getCol_5
    j __epilogo_metodo_Entidad_getCol_5
__epilogo_metodo_Entidad_getCol_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Entidad_getSimbolo_6:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    lb a0, 0(t6)
    j __epilogo_metodo_Entidad_getSimbolo_6
    j __epilogo_metodo_Entidad_getSimbolo_6
__epilogo_metodo_Entidad_getSimbolo_6:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Entidad_getTurnos_7:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 32
    lw a0, 0(t6)
    j __epilogo_metodo_Entidad_getTurnos_7
    j __epilogo_metodo_Entidad_getTurnos_7
__epilogo_metodo_Entidad_getTurnos_7:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Entidad_estaVivo_8:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t2, 0
    slt t3, t2, t1
    sw t3, -16(fp)
    lw a0, -16(fp)
    j __epilogo_metodo_Entidad_estaVivo_8
    j __epilogo_metodo_Entidad_estaVivo_8
__epilogo_metodo_Entidad_estaVivo_8:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Entidad_colocarEn_9:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    sw t1, 0(t6)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j __epilogo_metodo_Entidad_colocarEn_9
__epilogo_metodo_Entidad_colocarEn_9:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Entidad_calcularDano_10:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    li a0, 1
    j __epilogo_metodo_Entidad_calcularDano_10
    j __epilogo_metodo_Entidad_calcularDano_10
__epilogo_metodo_Entidad_calcularDano_10:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Entidad_atacar_11:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw a0, -12(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 36(t0)
    jalr t0
    sw a0, -20(fp)
    lw t1, -20(fp)
    sw t1, -24(fp)
    lw a0, -16(fp)
    lw a1, -24(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 44(t0)
    jalr t0
    sw a0, -28(fp)
    lw a0, -24(fp)
    j __epilogo_metodo_Entidad_atacar_11
    j __epilogo_metodo_Entidad_atacar_11
__epilogo_metodo_Entidad_atacar_11:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

metodo_Entidad_recibirDano_12:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    lw t2, -16(fp)
    sub t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
    lw a0, -12(fp)
    call metodo_Entidad_normalizar_13
    sw a0, -24(fp)
    j __epilogo_metodo_Entidad_recibirDano_12
__epilogo_metodo_Entidad_recibirDano_12:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

metodo_Entidad_normalizar_13:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t2, 0
    slt t3, t1, t2
    sw t3, -16(fp)
    lw t1, -16(fp)
    bnez t1, L2
    j L1
L2:
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
L1:
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    slt t3, t2, t1
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L4
    j L3
L4:
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
L3:
    j __epilogo_metodo_Entidad_normalizar_13
__epilogo_metodo_Entidad_normalizar_13:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Entidad_comparar_14:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw a0, -16(fp)
    call metodo_Entidad_normalizar_13
    sw a0, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    lw t6, -16(fp)
    addi t6, t6, 4
    lw t2, 0(t6)
    sub t3, t1, t2
    sw t3, -24(fp)
    lw a0, -24(fp)
    j __epilogo_metodo_Entidad_comparar_14
    j __epilogo_metodo_Entidad_comparar_14
__epilogo_metodo_Entidad_comparar_14:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

metodo_Entidad_describir_15:
    addi sp, sp, -36
    sw ra, 32(sp)
    sw fp, 28(sp)
    addi fp, sp, 36
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    lb t1, 0(t6)
    la t2, str_0
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
    sw a0, -16(fp)
    lw t1, -16(fp)
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
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -20(fp)
    lw t1, -20(fp)
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
    sw a0, -24(fp)
    lw t1, -24(fp)
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
    sw a0, -28(fp)
    lw t1, -28(fp)
    la t2, str_2
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
    sw a0, -32(fp)
    lw t1, -32(fp)
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
    sw a0, -36(fp)
    lw a0, -36(fp)
    j __epilogo_metodo_Entidad_describir_15
    j __epilogo_metodo_Entidad_describir_15
__epilogo_metodo_Entidad_describir_15:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 36
    mv fp, t0
    ret

metodo_Entidad_tick_16:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 32
    lw t1, 0(t6)
    li t2, 1
    add t3, t1, t2
    sw t3, -16(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 32
    sw t1, 0(t6)
    j __epilogo_metodo_Entidad_tick_16
__epilogo_metodo_Entidad_tick_16:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

constructor_Entidad_1:
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
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
    lw t1, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    sb t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 20
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 32
    sw t1, 0(t6)
    j __epilogo_constructor_Entidad_1
__epilogo_constructor_Entidad_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

metodo_Arquero_calcularDano_1:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 48
    lw t1, 0(t6)
    li t2, 0
    slt t3, t2, t1
    xori t3, t3, 1
    sw t3, -16(fp)
    lw t1, -16(fp)
    bnez t1, L6
    j L5
L6:
    lw a0, -12(fp)
    call metodo_Personaje_calcularDano_5
    sw a0, -20(fp)
    lw a0, -20(fp)
    j __epilogo_metodo_Arquero_calcularDano_1
L5:
    lw t6, -12(fp)
    addi t6, t6, 48
    lw t1, 0(t6)
    li t2, 1
    sub t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 48
    sw t1, 0(t6)
    lw a0, -12(fp)
    call metodo_Personaje_calcularDano_5
    sw a0, -28(fp)
    lw t1, -28(fp)
    li t2, 5
    add t3, t1, t2
    sw t3, -32(fp)
    lw a0, -32(fp)
    j __epilogo_metodo_Arquero_calcularDano_1
    j __epilogo_metodo_Arquero_calcularDano_1
__epilogo_metodo_Arquero_calcularDano_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

metodo_Arquero_getPuntaje_2:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 52
    lw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 44
    lw t2, 0(t6)
    li t4, 1000
    div t5, t1, t4
    rem t6, t1, t4
    div t0, t2, t4
    rem t2, t2, t4
    mul t3, t5, t0
    mul t3, t3, t4
    mul t5, t5, t2
    add t3, t3, t5
    mul t0, t6, t0
    add t3, t3, t0
    mul t6, t6, t2
    div t6, t6, t4
    add t3, t3, t6
    sw t3, -16(fp)
    lw t1, -16(fp)
    la t2, dbl_0
    lw t2, 0(t2)
    li t4, 1000
    div t5, t1, t4
    rem t6, t1, t4
    div t0, t2, t4
    rem t2, t2, t4
    mul t3, t5, t0
    mul t3, t3, t4
    mul t5, t5, t2
    add t3, t3, t5
    mul t0, t6, t0
    add t3, t3, t0
    mul t6, t6, t2
    div t6, t6, t4
    add t3, t3, t6
    sw t3, -20(fp)
    lw a0, -20(fp)
    j __epilogo_metodo_Arquero_getPuntaje_2
    j __epilogo_metodo_Arquero_getPuntaje_2
__epilogo_metodo_Arquero_getPuntaje_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Arquero_getFlechas_3:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 48
    lw a0, 0(t6)
    j __epilogo_metodo_Arquero_getFlechas_3
    j __epilogo_metodo_Arquero_getFlechas_3
__epilogo_metodo_Arquero_getFlechas_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

constructor_Arquero_1:
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
    li a3, 28
    li a4, 65
    li a5, 10
    call constructor_Personaje_1
    li t1, 12
    lw t6, -12(fp)
    addi t6, t6, 48
    sw t1, 0(t6)
    la t1, dbl_1
    lw t1, 0(t1)
    lw t6, -12(fp)
    addi t6, t6, 52
    sw t1, 0(t6)
    j __epilogo_constructor_Arquero_1
__epilogo_constructor_Arquero_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Guerrero_calcularDano_1:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    lw a0, -12(fp)
    call metodo_Personaje_calcularDano_5
    sw a0, -16(fp)
    lw t1, -16(fp)
    li t2, 4
    add t3, t1, t2
    sw t3, -20(fp)
    lw a0, -20(fp)
    j __epilogo_metodo_Guerrero_calcularDano_1
    j __epilogo_metodo_Guerrero_calcularDano_1
__epilogo_metodo_Guerrero_calcularDano_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Guerrero_recibirDano_2:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 48
    lw t2, 0(t6)
    sub t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    sw t1, -24(fp)
    lw t1, -24(fp)
    li t2, 1
    slt t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L8
    j L7
L8:
    li t1, 1
    sw t1, -24(fp)
L7:
    lw a0, -12(fp)
    lw a1, -24(fp)
    call metodo_Entidad_recibirDano_12
    sw a0, -32(fp)
    j __epilogo_metodo_Guerrero_recibirDano_2
__epilogo_metodo_Guerrero_recibirDano_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

metodo_Guerrero_subirArmadura_3:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 48
    lw t1, 0(t6)
    lw t2, -16(fp)
    add t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 48
    sw t1, 0(t6)
    j __epilogo_metodo_Guerrero_subirArmadura_3
__epilogo_metodo_Guerrero_subirArmadura_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

constructor_Guerrero_1:
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
    li a3, 40
    li a4, 71
    li a5, 5
    call constructor_Personaje_1
    li t1, 3
    lw t6, -12(fp)
    addi t6, t6, 48
    sw t1, 0(t6)
    j __epilogo_constructor_Guerrero_1
__epilogo_constructor_Guerrero_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Personaje_getOro_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 40
    lw a0, 0(t6)
    j __epilogo_metodo_Personaje_getOro_1
    j __epilogo_metodo_Personaje_getOro_1
__epilogo_metodo_Personaje_getOro_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Personaje_getMana_2:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 36
    lw a0, 0(t6)
    j __epilogo_metodo_Personaje_getMana_2
    j __epilogo_metodo_Personaje_getMana_2
__epilogo_metodo_Personaje_getMana_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Personaje_getSuerte_3:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 44
    lw a0, 0(t6)
    j __epilogo_metodo_Personaje_getSuerte_3
    j __epilogo_metodo_Personaje_getSuerte_3
__epilogo_metodo_Personaje_getSuerte_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Personaje_ganarOro_4:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 40
    lw t1, 0(t6)
    lw t2, -16(fp)
    add t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 40
    sw t1, 0(t6)
    j __epilogo_metodo_Personaje_ganarOro_4
__epilogo_metodo_Personaje_ganarOro_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Personaje_calcularDano_5:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    lw a0, -12(fp)
    call metodo_Entidad_calcularDano_10
    sw a0, -16(fp)
    lw t1, -16(fp)
    li t2, 2
    add t3, t1, t2
    sw t3, -20(fp)
    lw a0, -20(fp)
    j __epilogo_metodo_Personaje_calcularDano_5
    j __epilogo_metodo_Personaje_calcularDano_5
__epilogo_metodo_Personaje_calcularDano_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Personaje_describir_6:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    lw a0, -12(fp)
    call metodo_Entidad_describir_15
    sw a0, -16(fp)
    lw t1, -16(fp)
    la t2, str_3
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
    addi t6, t6, 36
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
    la t2, str_4
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
    addi t6, t6, 40
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
    j __epilogo_metodo_Personaje_describir_6
    j __epilogo_metodo_Personaje_describir_6
__epilogo_metodo_Personaje_describir_6:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

constructor_Personaje_1:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    sw a3, -24(fp)
    sw a4, -28(fp)
    sw a5, -32(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    lw a2, -20(fp)
    lw a3, -24(fp)
    lw a4, -28(fp)
    call constructor_Entidad_1
    lw t1, -32(fp)
    lw t6, -12(fp)
    addi t6, t6, 36
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 40
    sw t1, 0(t6)
    la t1, dbl_2
    lw t1, 0(t1)
    lw t6, -12(fp)
    addi t6, t6, 44
    sw t1, 0(t6)
    j __epilogo_constructor_Personaje_1
__epilogo_constructor_Personaje_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

metodo_Mago_calcularDano_1:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    lw a0, -12(fp)
    call metodo_Personaje_calcularDano_5
    sw a0, -16(fp)
    lw t1, -16(fp)
    sw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 36
    lw t1, 0(t6)
    li t2, 5
    slt t3, t1, t2
    xori t3, t3, 1
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L10
    j L9
L10:
    lw t6, -12(fp)
    addi t6, t6, 36
    lw t1, 0(t6)
    li t2, 5
    sub t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 36
    sw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 48
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 48
    sw t3, 0(t6)
    lw t1, -20(fp)
    li t2, 8
    add t3, t1, t2
    sw t3, -32(fp)
    lw a0, -32(fp)
    j __epilogo_metodo_Mago_calcularDano_1
L9:
    lw a0, -20(fp)
    j __epilogo_metodo_Mago_calcularDano_1
    j __epilogo_metodo_Mago_calcularDano_1
__epilogo_metodo_Mago_calcularDano_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

metodo_Mago_getHechizos_2:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 48
    lw a0, 0(t6)
    j __epilogo_metodo_Mago_getHechizos_2
    j __epilogo_metodo_Mago_getHechizos_2
__epilogo_metodo_Mago_getHechizos_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

constructor_Mago_1:
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
    li a3, 22
    li a4, 77
    li a5, 30
    call constructor_Personaje_1
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 48
    sw t1, 0(t6)
    j __epilogo_constructor_Mago_1
__epilogo_constructor_Mago_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Partida_agregarHeroe_1:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t2, 1
    add t3, t1, t2
    sw t3, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    lw a0, 0(t6)
    lw a1, -16(fp)
    lw a2, -20(fp)
    li a3, 0
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t3, 0(t6)
    j __epilogo_metodo_Partida_agregarHeroe_1
__epilogo_metodo_Partida_agregarHeroe_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

metodo_Partida_agregarEnemigo_2:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t1, -16(fp)
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
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t1, 0(t6)
    li t2, 1
    add t3, t1, t2
    sw t3, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    lw a0, 0(t6)
    lw a1, -16(fp)
    lw a2, -20(fp)
    li a3, 7
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t3, 0(t6)
    j __epilogo_metodo_Partida_agregarEnemigo_2
__epilogo_metodo_Partida_agregarEnemigo_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

metodo_Partida_azar_3:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    lw t1, 0(t6)
    li t2, 31
    mul t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    li t2, 7
    add t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    li t2, 1009
    rem t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    sw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 28
    lw t1, 0(t6)
    lw t2, -16(fp)
    rem t3, t1, t2
    sw t3, -32(fp)
    lw a0, -32(fp)
    j __epilogo_metodo_Partida_azar_3
    j __epilogo_metodo_Partida_azar_3
__epilogo_metodo_Partida_azar_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

metodo_Partida_elegirVivo_4:
    addi sp, sp, -60
    sw ra, 56(sp)
    sw fp, 52(sp)
    addi fp, sp, 60
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw a0, -12(fp)
    lw a1, -20(fp)
    call metodo_Partida_azar_3
    sw a0, -24(fp)
    lw t1, -24(fp)
    sw t1, -28(fp)
    li t1, 0
    sw t1, -32(fp)
L11:
    lw t1, -32(fp)
    lw t2, -20(fp)
    slt t3, t1, t2
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L12
    j L14
L12:
    lw t1, -28(fp)
    lw t2, -32(fp)
    add t3, t1, t2
    sw t3, -40(fp)
    lw t1, -40(fp)
    lw t2, -20(fp)
    rem t3, t1, t2
    sw t3, -44(fp)
    lw t1, -44(fp)
    sw t1, -48(fp)
    lw t2, -48(fp)
    lw t1, -16(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -52(fp)
    lw a0, -52(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -56(fp)
    lw t1, -56(fp)
    bnez t1, L16
    j L15
L16:
    lw a0, -48(fp)
    j __epilogo_metodo_Partida_elegirVivo_4
L15:
L13:
    lw t1, -32(fp)
    addi t3, t1, 1
    sw t3, -32(fp)
    j L11
L14:
    li t1, 1
    neg t3, t1
    sw t3, -60(fp)
    lw a0, -60(fp)
    j __epilogo_metodo_Partida_elegirVivo_4
    j __epilogo_metodo_Partida_elegirVivo_4
__epilogo_metodo_Partida_elegirVivo_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 60
    mv fp, t0
    ret

metodo_Partida_contarVivos_5:
    addi sp, sp, -52
    sw ra, 48(sp)
    sw fp, 44(sp)
    addi fp, sp, 52
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    sw a3, -24(fp)
    lw t1, -20(fp)
    lw t2, -24(fp)
    slt t3, t1, t2
    xori t3, t3, 1
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L18
    j L17
L18:
    li a0, 0
    j __epilogo_metodo_Partida_contarVivos_5
L17:
    li t1, 0
    sw t1, -32(fp)
    lw t2, -20(fp)
    lw t1, -16(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -36(fp)
    lw a0, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -40(fp)
    lw t1, -40(fp)
    bnez t1, L20
    j L19
L20:
    li t1, 1
    sw t1, -32(fp)
L19:
    lw t1, -20(fp)
    li t2, 1
    add t3, t1, t2
    sw t3, -44(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    lw a2, -44(fp)
    lw a3, -24(fp)
    call metodo_Partida_contarVivos_5
    sw a0, -48(fp)
    lw t1, -32(fp)
    lw t2, -48(fp)
    add t3, t1, t2
    sw t3, -52(fp)
    lw a0, -52(fp)
    j __epilogo_metodo_Partida_contarVivos_5
    j __epilogo_metodo_Partida_contarVivos_5
__epilogo_metodo_Partida_contarVivos_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 52
    mv fp, t0
    ret

metodo_Partida_turnoGrupo_6:
    addi sp, sp, -140
    sw ra, 136(sp)
    sw fp, 132(sp)
    addi fp, sp, 140
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    sw a3, -24(fp)
    sw a4, -28(fp)
    li t1, 0
    sw t1, -32(fp)
L21:
    lw t1, -32(fp)
    lw t2, -20(fp)
    slt t3, t1, t2
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L22
    j L24
L22:
    lw t2, -32(fp)
    lw t1, -16(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -40(fp)
    lw t1, -40(fp)
    sw t1, -44(fp)
    lw a0, -44(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -48(fp)
    lw t1, -48(fp)
    bnez t1, L26
    j L25
L26:
    lw a0, -12(fp)
    lw a1, -24(fp)
    lw a2, -28(fp)
    call metodo_Partida_elegirVivo_4
    sw a0, -52(fp)
    lw t1, -52(fp)
    sw t1, -56(fp)
    lw t1, -56(fp)
    li t2, 0
    slt t3, t1, t2
    sw t3, -60(fp)
    lw t1, -60(fp)
    bnez t1, L28
    j L27
L28:
    j __epilogo_metodo_Partida_turnoGrupo_6
L27:
    lw t2, -56(fp)
    lw t1, -24(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -64(fp)
    lw t1, -64(fp)
    sw t1, -68(fp)
    lw a0, -44(fp)
    lw a1, -68(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 40(t0)
    jalr t0
    sw a0, -72(fp)
    lw t1, -72(fp)
    sw t1, -76(fp)
    lw a0, -44(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -80(fp)
    lw t1, -80(fp)
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
    sw a0, -84(fp)
    lw a0, -68(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -88(fp)
    lw t1, -84(fp)
    lw t2, -88(fp)
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
    sw a0, -92(fp)
    lw t1, -92(fp)
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
    sw a0, -96(fp)
    lw t1, -96(fp)
    lw t2, -76(fp)
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
    sw a0, -100(fp)
    lw t1, -100(fp)
    la t2, str_7
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
    lw a0, -68(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -108(fp)
    lw t1, -104(fp)
    lw t2, -108(fp)
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
    sw a0, -112(fp)
    lw t1, -112(fp)
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
    sw a0, -116(fp)
    lw a0, -116(fp)
    call z_print_cadena
    call z_print_salto
    lw a0, -68(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -120(fp)
    lw t1, -120(fp)
    seqz t3, t1
    sw t3, -124(fp)
    lw t1, -124(fp)
    bnez t1, L30
    j L29
L30:
    lw a0, -68(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -128(fp)
    la t1, str_9
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
    lw t1, -132(fp)
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
    sw a0, -136(fp)
    lw a0, -136(fp)
    call z_print_cadena
    call z_print_salto
L29:
    lw t6, -12(fp)
    addi t6, t6, 24
    lw a0, 0(t6)
    lw a1, -44(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -140(fp)
L25:
L23:
    lw t1, -32(fp)
    addi t3, t1, 1
    sw t3, -32(fp)
    j L21
L24:
    j __epilogo_metodo_Partida_turnoGrupo_6
__epilogo_metodo_Partida_turnoGrupo_6:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 140
    mv fp, t0
    ret

metodo_Partida_jugar_7:
    addi sp, sp, -84
    sw ra, 80(sp)
    sw fp, 76(sp)
    addi fp, sp, 84
    sw a0, -12(fp)
    sw a1, -16(fp)
L31:
    lw t6, -12(fp)
    addi t6, t6, 32
    lw t1, 0(t6)
    lw t2, -16(fp)
    slt t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L37
    j L38
L37:
    lw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a1, 0(t6)
    li a2, 0
    lw t6, -12(fp)
    addi t6, t6, 12
    lw a3, 0(t6)
    call metodo_Partida_contarVivos_5
    sw a0, -24(fp)
    lw t1, -24(fp)
    li t2, 0
    slt t3, t2, t1
    sw t3, -28(fp)
    lw t1, -28(fp)
    sw t1, -32(fp)
    j L39
L38:
    li t1, 0
    sw t1, -32(fp)
L39:
    lw t1, -32(fp)
    bnez t1, L34
    j L35
L34:
    lw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a1, 0(t6)
    li a2, 0
    lw t6, -12(fp)
    addi t6, t6, 16
    lw a3, 0(t6)
    call metodo_Partida_contarVivos_5
    sw a0, -36(fp)
    lw t1, -36(fp)
    li t2, 0
    slt t3, t2, t1
    sw t3, -40(fp)
    lw t1, -40(fp)
    sw t1, -44(fp)
    j L36
L35:
    li t1, 0
    sw t1, -44(fp)
L36:
    lw t1, -44(fp)
    bnez t1, L32
    j L33
L32:
    lw t6, -12(fp)
    addi t6, t6, 32
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 32
    sw t3, 0(t6)
    la t1, str_11
    lw t6, -12(fp)
    addi t6, t6, 32
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
    lw t1, -48(fp)
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
    sw a0, -52(fp)
    lw a0, -52(fp)
    call z_print_cadena
    call z_print_salto
    lw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw a2, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a3, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw a4, 0(t6)
    call metodo_Partida_turnoGrupo_6
    sw a0, -56(fp)
    lw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw a2, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a3, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw a4, 0(t6)
    call metodo_Partida_turnoGrupo_6
    sw a0, -60(fp)
    lw t6, -12(fp)
    addi t6, t6, 32
    lw t1, 0(t6)
    li t2, 3
    rem t3, t1, t2
    sw t3, -64(fp)
    lw t1, -64(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -68(fp)
    lw t1, -68(fp)
    bnez t1, L41
    j L40
L41:
    lw t6, -12(fp)
    addi t6, t6, 20
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -72(fp)
    lw t1, -72(fp)
    sw t1, -76(fp)
    la t1, str_13
    lw t2, -76(fp)
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
    sw a0, -80(fp)
    lw a0, -80(fp)
    call z_print_cadena
    call z_print_salto
    lw t6, -12(fp)
    addi t6, t6, 20
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -84(fp)
L40:
    j L31
L33:
    j __epilogo_metodo_Partida_jugar_7
__epilogo_metodo_Partida_jugar_7:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 84
    mv fp, t0
    ret

metodo_Partida_mostrar_8:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -16(fp)
    j __epilogo_metodo_Partida_mostrar_8
__epilogo_metodo_Partida_mostrar_8:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Partida_resumen_9:
    addi sp, sp, -64
    sw ra, 60(sp)
    sw fp, 56(sp)
    addi fp, sp, 64
    sw a0, -12(fp)
    la a0, str_14
    call z_print_cadena
    call z_print_salto
    li t1, 0
    sw t1, -16(fp)
L42:
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L43
    j L45
L43:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -24(fp)
    lw a0, -24(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 52(t0)
    jalr t0
    sw a0, -28(fp)
    lw a0, -28(fp)
    call z_print_cadena
    call z_print_salto
L44:
    lw t1, -16(fp)
    addi t3, t1, 1
    sw t3, -16(fp)
    j L42
L45:
    li t1, 0
    sw t1, -16(fp)
L46:
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L47
    j L49
L47:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -36(fp)
    lw a0, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 52(t0)
    jalr t0
    sw a0, -40(fp)
    lw a0, -40(fp)
    call z_print_cadena
    call z_print_salto
L48:
    lw t1, -16(fp)
    addi t3, t1, 1
    sw t3, -16(fp)
    j L46
L49:
    lw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a1, 0(t6)
    li a2, 0
    lw t6, -12(fp)
    addi t6, t6, 12
    lw a3, 0(t6)
    call metodo_Partida_contarVivos_5
    sw a0, -44(fp)
    la t1, str_15
    lw t2, -44(fp)
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
    call z_print_cadena
    call z_print_salto
    lw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a1, 0(t6)
    li a2, 0
    lw t6, -12(fp)
    addi t6, t6, 16
    lw a3, 0(t6)
    call metodo_Partida_contarVivos_5
    sw a0, -52(fp)
    la t1, str_16
    lw t2, -52(fp)
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
    sw a0, -56(fp)
    lw a0, -56(fp)
    call z_print_cadena
    call z_print_salto
    lw t6, -12(fp)
    addi t6, t6, 24
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -60(fp)
    la t1, str_17
    lw t2, -60(fp)
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
    sw a0, -64(fp)
    lw a0, -64(fp)
    call z_print_cadena
    call z_print_salto
    j __epilogo_metodo_Partida_resumen_9
__epilogo_metodo_Partida_resumen_9:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 64
    mv fp, t0
    ret

metodo_Partida_checksum_10:
    addi sp, sp, -112
    sw ra, 108(sp)
    sw fp, 104(sp)
    addi fp, sp, 112
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    sw a3, -24(fp)
    sw a4, -28(fp)
    sw a5, -32(fp)
    sw a6, -36(fp)
    sw a7, -40(fp)
    lw t1, 0(fp)
    sw t1, -44(fp)
    lw t1, 4(fp)
    sw t1, -48(fp)
    lw t1, -20(fp)
    li t2, 2
    mul t3, t1, t2
    sw t3, -52(fp)
    lw t1, -16(fp)
    lw t2, -52(fp)
    add t3, t1, t2
    sw t3, -56(fp)
    lw t1, -24(fp)
    li t2, 3
    mul t3, t1, t2
    sw t3, -60(fp)
    lw t1, -56(fp)
    lw t2, -60(fp)
    add t3, t1, t2
    sw t3, -64(fp)
    lw t1, -28(fp)
    li t2, 4
    mul t3, t1, t2
    sw t3, -68(fp)
    lw t1, -64(fp)
    lw t2, -68(fp)
    add t3, t1, t2
    sw t3, -72(fp)
    lw t1, -32(fp)
    li t2, 5
    mul t3, t1, t2
    sw t3, -76(fp)
    lw t1, -72(fp)
    lw t2, -76(fp)
    add t3, t1, t2
    sw t3, -80(fp)
    lw t1, -36(fp)
    li t2, 6
    mul t3, t1, t2
    sw t3, -84(fp)
    lw t1, -80(fp)
    lw t2, -84(fp)
    add t3, t1, t2
    sw t3, -88(fp)
    lw t1, -40(fp)
    li t2, 7
    mul t3, t1, t2
    sw t3, -92(fp)
    lw t1, -88(fp)
    lw t2, -92(fp)
    add t3, t1, t2
    sw t3, -96(fp)
    lw t1, -44(fp)
    li t2, 8
    mul t3, t1, t2
    sw t3, -100(fp)
    lw t1, -96(fp)
    lw t2, -100(fp)
    add t3, t1, t2
    sw t3, -104(fp)
    lw t1, -48(fp)
    li t2, 9
    mul t3, t1, t2
    sw t3, -108(fp)
    lw t1, -104(fp)
    lw t2, -108(fp)
    add t3, t1, t2
    sw t3, -112(fp)
    lw a0, -112(fp)
    j __epilogo_metodo_Partida_checksum_10
    j __epilogo_metodo_Partida_checksum_10
__epilogo_metodo_Partida_checksum_10:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 112
    mv fp, t0
    ret

metodo_Partida_pruebaDecimal_11:
    addi sp, sp, -104
    sw ra, 100(sp)
    sw fp, 96(sp)
    addi fp, sp, 104
    sw a0, -12(fp)
    la t1, dbl_3
    lw t1, 0(t1)
    sw t1, -16(fp)
    lw t1, -16(fp)
    lw t2, -16(fp)
    li t4, 1000
    div t5, t1, t4
    rem t6, t1, t4
    div t0, t2, t4
    rem t2, t2, t4
    mul t3, t5, t0
    mul t3, t3, t4
    mul t5, t5, t2
    add t3, t3, t5
    mul t0, t6, t0
    add t3, t3, t0
    mul t6, t6, t2
    div t6, t6, t4
    add t3, t3, t6
    sw t3, -20(fp)
    lw t1, -20(fp)
    sw t1, -24(fp)
    la t1, dbl_4
    lw t1, 0(t1)
    la t2, dbl_5
    lw t2, 0(t2)
    li t4, 1000
    div t5, t1, t4
    rem t6, t1, t4
    div t0, t2, t4
    rem t2, t2, t4
    mul t3, t5, t0
    mul t3, t3, t4
    mul t5, t5, t2
    add t3, t3, t5
    mul t0, t6, t0
    add t3, t3, t0
    mul t6, t6, t2
    div t6, t6, t4
    add t3, t3, t6
    sw t3, -28(fp)
    lw t1, -28(fp)
    sw t1, -32(fp)
    la t1, dbl_6
    lw t1, 0(t1)
    la t2, dbl_7
    lw t2, 0(t2)
    div t3, t1, t2
    rem t4, t1, t2
    li t5, 1000
    mul t3, t3, t5
    mul t4, t4, t5
    div t4, t4, t2
    add t3, t3, t4
    sw t3, -36(fp)
    lw t1, -36(fp)
    sw t1, -40(fp)
    li t1, 10
    li t4, 1000
    mul t1, t1, t4
    la t2, dbl_8
    lw t2, 0(t2)
    div t3, t1, t2
    rem t4, t1, t2
    li t5, 1000
    mul t3, t3, t5
    mul t4, t4, t5
    div t4, t4, t2
    add t3, t3, t4
    sw t3, -44(fp)
    lw t1, -44(fp)
    sw t1, -48(fp)
    la t1, dbl_9
    lw t1, 0(t1)
    la t2, dbl_9
    lw t2, 0(t2)
    li t4, 1000
    div t5, t1, t4
    rem t6, t1, t4
    div t0, t2, t4
    rem t2, t2, t4
    mul t3, t5, t0
    mul t3, t3, t4
    mul t5, t5, t2
    add t3, t3, t5
    mul t0, t6, t0
    add t3, t3, t0
    mul t6, t6, t2
    div t6, t6, t4
    add t3, t3, t6
    sw t3, -52(fp)
    lw t1, -52(fp)
    sw t1, -56(fp)
    la t1, dbl_10
    lw t1, 0(t1)
    la t2, dbl_11
    lw t2, 0(t2)
    sub t3, t1, t2
    sw t3, -60(fp)
    lw t1, -60(fp)
    la t2, dbl_8
    lw t2, 0(t2)
    li t4, 1000
    div t5, t1, t4
    rem t6, t1, t4
    div t0, t2, t4
    rem t2, t2, t4
    mul t3, t5, t0
    mul t3, t3, t4
    mul t5, t5, t2
    add t3, t3, t5
    mul t0, t6, t0
    add t3, t3, t0
    mul t6, t6, t2
    div t6, t6, t4
    add t3, t3, t6
    sw t3, -64(fp)
    lw t1, -64(fp)
    sw t1, -68(fp)
    la t1, dbl_12
    lw t1, 0(t1)
    la t2, dbl_13
    lw t2, 0(t2)
    div t3, t1, t2
    rem t4, t1, t2
    li t5, 1000
    mul t3, t3, t5
    mul t4, t4, t5
    div t4, t4, t2
    add t3, t3, t4
    sw t3, -72(fp)
    lw t1, -72(fp)
    sw t1, -76(fp)
    la t1, str_18
    lw t2, -24(fp)
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
    sw a0, -80(fp)
    lw a0, -80(fp)
    call z_print_cadena
    call z_print_salto
    la t1, str_19
    lw t2, -32(fp)
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
    sw a0, -84(fp)
    lw a0, -84(fp)
    call z_print_cadena
    call z_print_salto
    la t1, str_20
    lw t2, -40(fp)
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
    sw a0, -88(fp)
    lw a0, -88(fp)
    call z_print_cadena
    call z_print_salto
    la t1, str_21
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
    sw a0, -92(fp)
    lw a0, -92(fp)
    call z_print_cadena
    call z_print_salto
    la t1, str_22
    lw t2, -56(fp)
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
    sw a0, -96(fp)
    lw a0, -96(fp)
    call z_print_cadena
    call z_print_salto
    la t1, str_23
    lw t2, -68(fp)
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
    sw a0, -100(fp)
    lw a0, -100(fp)
    call z_print_cadena
    call z_print_salto
    la t1, str_24
    lw t2, -76(fp)
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
    sw a0, -104(fp)
    lw a0, -104(fp)
    call z_print_cadena
    call z_print_salto
    j __epilogo_metodo_Partida_pruebaDecimal_11
__epilogo_metodo_Partida_pruebaDecimal_11:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 104
    mv fp, t0
    ret

constructor_Partida_1:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    sw t1, 0(t6)
    li a0, 16
    call z_alloc
    sw a0, -20(fp)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    li a0, 16
    call z_alloc
    sw a0, -24(fp)
    lw t1, -24(fp)
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
    addi t6, t6, 32
    sw t1, 0(t6)
    li a0, 20
    call z_alloc
    sw a0, -28(fp)
    lw t6, -28(fp)
    la t1, __vtable_Tablero
    sw t1, 0(t6)
    lw a0, -28(fp)
    li a1, 6
    li a2, 8
    call constructor_Tablero_1
    sw a0, -28(fp)
    lw t1, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    sw t1, 0(t6)
    li a0, 12
    call z_alloc
    sw a0, -32(fp)
    lw t6, -32(fp)
    la t1, __vtable_Reloj
    sw t1, 0(t6)
    lw a0, -32(fp)
    call constructor_Reloj_1
    sw a0, -32(fp)
    lw t1, -32(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j __epilogo_constructor_Partida_1
__epilogo_constructor_Partida_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

metodo_Tablero_dentro_1:
    addi sp, sp, -48
    sw ra, 44(sp)
    sw fp, 40(sp)
    addi fp, sp, 48
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw t1, -16(fp)
    li t2, 0
    slt t3, t1, t2
    xori t3, t3, 1
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L56
    j L57
L56:
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    sw t1, -32(fp)
    j L58
L57:
    li t1, 0
    sw t1, -32(fp)
L58:
    lw t1, -32(fp)
    bnez t1, L53
    j L54
L53:
    lw t1, -20(fp)
    li t2, 0
    slt t3, t1, t2
    xori t3, t3, 1
    sw t3, -36(fp)
    lw t1, -36(fp)
    sw t1, -40(fp)
    j L55
L54:
    li t1, 0
    sw t1, -40(fp)
L55:
    lw t1, -40(fp)
    bnez t1, L50
    j L51
L50:
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -44(fp)
    lw t1, -44(fp)
    sw t1, -48(fp)
    j L52
L51:
    li t1, 0
    sw t1, -48(fp)
L52:
    lw a0, -48(fp)
    j __epilogo_metodo_Tablero_dentro_1
    j __epilogo_metodo_Tablero_dentro_1
__epilogo_metodo_Tablero_dentro_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 48
    mv fp, t0
    ret

metodo_Tablero_colocar_2:
    addi sp, sp, -52
    sw ra, 48(sp)
    sw fp, 44(sp)
    addi fp, sp, 52
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    sw a3, -24(fp)
    lw a0, -12(fp)
    lw a1, -20(fp)
    lw a2, -24(fp)
    call metodo_Tablero_dentro_1
    sw a0, -28(fp)
    lw t1, -28(fp)
    seqz t3, t1
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L60
    j L59
L60:
    li a0, 0
    j __epilogo_metodo_Tablero_colocar_2
L59:
    lw t2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -36(fp)
    lw t2, -24(fp)
    lw t1, -36(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -40(fp)
    lw t1, -40(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -44(fp)
    lw t1, -44(fp)
    bnez t1, L62
    j L61
L62:
    li a0, 0
    j __epilogo_metodo_Tablero_colocar_2
L61:
    lw t2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t2, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -20(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -20(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    slli t2, t2, 2
    add t6, t6, t2
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -24(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    lw a0, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -48(fp)
    lw t2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lw t2, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -20(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 1
    mul t2, t2, t4
    add t6, t6, t2
    lb t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lw t1, -48(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -20(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    slli t2, t2, 2
    add t6, t6, t2
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
    lw a0, -16(fp)
    lw a1, -20(fp)
    lw a2, -24(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 32(t0)
    jalr t0
    sw a0, -52(fp)
    li a0, 1
    j __epilogo_metodo_Tablero_colocar_2
    j __epilogo_metodo_Tablero_colocar_2
__epilogo_metodo_Tablero_colocar_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 52
    mv fp, t0
    ret

metodo_Tablero_mover_3:
    addi sp, sp, -64
    sw ra, 60(sp)
    sw fp, 56(sp)
    addi fp, sp, 64
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    sw a3, -24(fp)
    lw a0, -12(fp)
    lw a1, -20(fp)
    lw a2, -24(fp)
    call metodo_Tablero_dentro_1
    sw a0, -28(fp)
    lw t1, -28(fp)
    seqz t3, t1
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L64
    j L63
L64:
    li a0, 0
    j __epilogo_metodo_Tablero_mover_3
L63:
    lw t2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -36(fp)
    lw t2, -24(fp)
    lw t1, -36(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -40(fp)
    lw t1, -40(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -44(fp)
    lw t1, -44(fp)
    bnez t1, L66
    j L65
L66:
    li a0, 0
    j __epilogo_metodo_Tablero_mover_3
L65:
    lw a0, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -48(fp)
    lw t2, -48(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw a0, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -52(fp)
    lw t2, -52(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -48(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -48(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    slli t2, t2, 2
    add t6, t6, t2
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -52(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    lw a0, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -56(fp)
    lw t2, -56(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lw a0, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -60(fp)
    lw t2, -60(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -56(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 1
    mul t2, t2, t4
    add t6, t6, t2
    lb t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    li t1, 46
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -56(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    slli t2, t2, 2
    add t6, t6, t2
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -60(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 1
    mul t2, t2, t4
    add t6, t6, t2
    sb t1, 0(t6)
    lw a0, -12(fp)
    lw a1, -16(fp)
    lw a2, -20(fp)
    lw a3, -24(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -64(fp)
    lw a0, -64(fp)
    j __epilogo_metodo_Tablero_mover_3
    j __epilogo_metodo_Tablero_mover_3
__epilogo_metodo_Tablero_mover_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 64
    mv fp, t0
    ret

metodo_Tablero_en_4:
    addi sp, sp, -36
    sw ra, 32(sp)
    sw fp, 28(sp)
    addi fp, sp, 36
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    lw a2, -20(fp)
    call metodo_Tablero_dentro_1
    sw a0, -24(fp)
    lw t1, -24(fp)
    seqz t3, t1
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L68
    j L67
L68:
    li a0, 0
    j __epilogo_metodo_Tablero_en_4
L67:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -32(fp)
    lw t2, -20(fp)
    lw t1, -32(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -36(fp)
    lw a0, -36(fp)
    j __epilogo_metodo_Tablero_en_4
    j __epilogo_metodo_Tablero_en_4
__epilogo_metodo_Tablero_en_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 36
    mv fp, t0
    ret

metodo_Tablero_limpiarMuertos_5:
    addi sp, sp, -60
    sw ra, 56(sp)
    sw fp, 52(sp)
    addi fp, sp, 60
    sw a0, -12(fp)
    li t1, 0
    sw t1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
L69:
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L70
    j L72
L70:
    li t1, 0
    sw t1, -28(fp)
L73:
    lw t1, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L74
    j L76
L74:
    lw t2, -20(fp)
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
    lw t1, -40(fp)
    sw t1, -44(fp)
    lw t1, -44(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -48(fp)
    lw t1, -48(fp)
    bnez t1, L78
    j L79
L78:
    lw a0, -44(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -52(fp)
    lw t1, -52(fp)
    seqz t3, t1
    sw t3, -56(fp)
    lw t1, -56(fp)
    sw t1, -60(fp)
    j L80
L79:
    li t1, 0
    sw t1, -60(fp)
L80:
    lw t1, -60(fp)
    bnez t1, L81
    j L77
L81:
    lw t2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t2, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -20(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -20(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    slli t2, t2, 2
    add t6, t6, t2
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -28(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    lw t2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lw t2, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -20(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 1
    mul t2, t2, t4
    add t6, t6, t2
    lb t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    li t1, 120
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -20(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    slli t2, t2, 2
    add t6, t6, t2
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -28(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 1
    mul t2, t2, t4
    add t6, t6, t2
    sb t1, 0(t6)
    lw t1, -16(fp)
    addi t3, t1, 1
    sw t3, -16(fp)
L77:
L75:
    lw t1, -28(fp)
    addi t3, t1, 1
    sw t3, -28(fp)
    j L73
L76:
L71:
    lw t1, -20(fp)
    addi t3, t1, 1
    sw t3, -20(fp)
    j L69
L72:
    lw a0, -16(fp)
    j __epilogo_metodo_Tablero_limpiarMuertos_5
    j __epilogo_metodo_Tablero_limpiarMuertos_5
__epilogo_metodo_Tablero_limpiarMuertos_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 60
    mv fp, t0
    ret

metodo_Tablero_contar_6:
    addi sp, sp, -44
    sw ra, 40(sp)
    sw fp, 36(sp)
    addi fp, sp, 44
    sw a0, -12(fp)
    li t1, 0
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
    li t1, 0
    sw t1, -28(fp)
L86:
    lw t1, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L87
    j L89
L87:
    lw t2, -20(fp)
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
    lw t1, -40(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -44(fp)
    lw t1, -44(fp)
    bnez t1, L91
    j L90
L91:
    lw t1, -16(fp)
    addi t3, t1, 1
    sw t3, -16(fp)
L90:
L88:
    lw t1, -28(fp)
    addi t3, t1, 1
    sw t3, -28(fp)
    j L86
L89:
L84:
    lw t1, -20(fp)
    addi t3, t1, 1
    sw t3, -20(fp)
    j L82
L85:
    lw a0, -16(fp)
    j __epilogo_metodo_Tablero_contar_6
    j __epilogo_metodo_Tablero_contar_6
__epilogo_metodo_Tablero_contar_6:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 44
    mv fp, t0
    ret

metodo_Tablero_imprimir_7:
    addi sp, sp, -44
    sw ra, 40(sp)
    sw fp, 36(sp)
    addi fp, sp, 44
    sw a0, -12(fp)
    li t1, 0
    sw t1, -16(fp)
L92:
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L93
    j L95
L93:
    la t1, str_25
    sw t1, -24(fp)
    li t1, 0
    sw t1, -28(fp)
L96:
    lw t1, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L97
    j L99
L97:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -36(fp)
    lw t2, -28(fp)
    lw t1, -36(fp)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lb t3, 0(t1)
    sw t3, -40(fp)
    lw t1, -24(fp)
    lw t2, -40(fp)
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
    sw a0, -44(fp)
    lw t1, -44(fp)
    sw t1, -24(fp)
L98:
    lw t1, -28(fp)
    addi t3, t1, 1
    sw t3, -28(fp)
    j L96
L99:
    lw a0, -24(fp)
    call z_print_cadena
    call z_print_salto
L94:
    lw t1, -16(fp)
    addi t3, t1, 1
    sw t3, -16(fp)
    j L92
L95:
    j __epilogo_metodo_Tablero_imprimir_7
__epilogo_metodo_Tablero_imprimir_7:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 44
    mv fp, t0
    ret

constructor_Tablero_1:
    addi sp, sp, -44
    sw ra, 40(sp)
    sw fp, 36(sp)
    addi fp, sp, 44
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
    lw t1, -16(fp)
    lw t2, -20(fp)
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
    sw a0, -24(fp)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    lw t1, -16(fp)
    lw t2, -20(fp)
    addi sp, sp, -16
    sw t1, 0(sp)
    sw t2, 4(sp)
    slli a0, t1, 2
    call z_alloc
    sw a0, 8(sp)
    sw zero, 12(sp)
Lnew_1_loop:
    lw t0, 12(sp)
    lw t1, 0(sp)
    bge t0, t1, Lnew_1_fin
    lw a0, 4(sp)
    li t2, 1
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
    j Lnew_1_loop
Lnew_1_fin:
    lw a0, 8(sp)
    addi sp, sp, 16
    sw a0, -28(fp)
    lw t1, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    li t1, 0
    sw t1, -32(fp)
L100:
    lw t1, -32(fp)
    lw t2, -16(fp)
    slt t3, t1, t2
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L101
    j L103
L101:
    li t1, 0
    sw t1, -40(fp)
L104:
    lw t1, -40(fp)
    lw t2, -20(fp)
    slt t3, t1, t2
    sw t3, -44(fp)
    lw t1, -44(fp)
    bnez t1, L105
    j L107
L105:
    lw t2, -32(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lw t2, -40(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -32(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 1
    mul t2, t2, t4
    add t6, t6, t2
    lb t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    li t1, 46
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -32(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    slli t2, t2, 2
    add t6, t6, t2
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -40(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 1
    mul t2, t2, t4
    add t6, t6, t2
    sb t1, 0(t6)
L106:
    lw t1, -40(fp)
    addi t3, t1, 1
    sw t3, -40(fp)
    j L104
L107:
L102:
    lw t1, -32(fp)
    addi t3, t1, 1
    sw t3, -32(fp)
    j L100
L103:
    j __epilogo_constructor_Tablero_1
__epilogo_constructor_Tablero_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 44
    mv fp, t0
    ret

metodo_Dragon_calcularDano_1:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 40
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 40
    sw t3, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 40
    lw t1, 0(t6)
    li t2, 3
    rem t3, t1, t2
    sw t3, -16(fp)
    lw t1, -16(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L109
    j L108
L109:
    li a0, 15
    j __epilogo_metodo_Dragon_calcularDano_1
L108:
    lw a0, -12(fp)
    call metodo_Enemigo_calcularDano_2
    sw a0, -24(fp)
    lw t1, -24(fp)
    li t2, 4
    add t3, t1, t2
    sw t3, -28(fp)
    lw a0, -28(fp)
    j __epilogo_metodo_Dragon_calcularDano_1
    j __epilogo_metodo_Dragon_calcularDano_1
__epilogo_metodo_Dragon_calcularDano_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

metodo_Dragon_recibirDano_2:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t1, -16(fp)
    li t2, 2
    div t3, t1, t2
    sw t3, -20(fp)
    lw a0, -12(fp)
    lw a1, -20(fp)
    call metodo_Entidad_recibirDano_12
    sw a0, -24(fp)
    j __epilogo_metodo_Dragon_recibirDano_2
__epilogo_metodo_Dragon_recibirDano_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

constructor_Dragon_1:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    la a2, str_26
    li a3, 80
    li a4, 68
    li a5, 100
    call constructor_Enemigo_1
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 40
    sw t1, 0(t6)
    j __epilogo_constructor_Dragon_1
__epilogo_constructor_Dragon_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_OrcoJefe_calcularDano_1:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    lw a0, -12(fp)
    call metodo_Orco_calcularDano_1
    sw a0, -16(fp)
    lw t1, -16(fp)
    li t2, 2
    mul t3, t1, t2
    sw t3, -20(fp)
    lw a0, -20(fp)
    j __epilogo_metodo_OrcoJefe_calcularDano_1
    j __epilogo_metodo_OrcoJefe_calcularDano_1
__epilogo_metodo_OrcoJefe_calcularDano_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_OrcoJefe_describir_2:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    lw a0, -12(fp)
    call metodo_Entidad_describir_15
    sw a0, -16(fp)
    la t1, str_27
    lw t2, -16(fp)
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
    lw a0, -20(fp)
    j __epilogo_metodo_OrcoJefe_describir_2
    j __epilogo_metodo_OrcoJefe_describir_2
__epilogo_metodo_OrcoJefe_describir_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

constructor_OrcoJefe_1:
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
    call constructor_Orco_1
    li t1, 60
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
    li t1, 60
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
    li t1, 74
    lw t6, -12(fp)
    addi t6, t6, 28
    sb t1, 0(t6)
    li t1, 50
    lw t6, -12(fp)
    addi t6, t6, 36
    sw t1, 0(t6)
    j __epilogo_constructor_OrcoJefe_1
__epilogo_constructor_OrcoJefe_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Orco_calcularDano_1:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 40
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 40
    sw t3, 0(t6)
    lw a0, -12(fp)
    call metodo_Enemigo_calcularDano_2
    sw a0, -16(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 40
    lw t2, 0(t6)
    add t3, t1, t2
    sw t3, -20(fp)
    lw a0, -20(fp)
    j __epilogo_metodo_Orco_calcularDano_1
    j __epilogo_metodo_Orco_calcularDano_1
__epilogo_metodo_Orco_calcularDano_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

constructor_Orco_1:
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
    li a3, 30
    li a4, 79
    li a5, 10
    call constructor_Enemigo_1
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 40
    sw t1, 0(t6)
    j __epilogo_constructor_Orco_1
__epilogo_constructor_Orco_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Enemigo_getRecompensa_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 36
    lw a0, 0(t6)
    j __epilogo_metodo_Enemigo_getRecompensa_1
    j __epilogo_metodo_Enemigo_getRecompensa_1
__epilogo_metodo_Enemigo_getRecompensa_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Enemigo_calcularDano_2:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    lw a0, -12(fp)
    call metodo_Entidad_calcularDano_10
    sw a0, -16(fp)
    lw t1, -16(fp)
    li t2, 1
    add t3, t1, t2
    sw t3, -20(fp)
    lw a0, -20(fp)
    j __epilogo_metodo_Enemigo_calcularDano_2
    j __epilogo_metodo_Enemigo_calcularDano_2
__epilogo_metodo_Enemigo_calcularDano_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

constructor_Enemigo_1:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    sw a3, -24(fp)
    sw a4, -28(fp)
    sw a5, -32(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    lw a2, -20(fp)
    lw a3, -24(fp)
    lw a4, -28(fp)
    call constructor_Entidad_1
    lw t1, -32(fp)
    lw t6, -12(fp)
    addi t6, t6, 36
    sw t1, 0(t6)
    j __epilogo_constructor_Enemigo_1
__epilogo_constructor_Enemigo_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

__entrada_main:
    addi sp, sp, -152
    sw ra, 148(sp)
    sw fp, 144(sp)
    addi fp, sp, 152
    li t1, 42
    sw t1, -12(fp)
    li t1, 12
    sw t1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
    li a0, 36
    call z_alloc
    sw a0, -24(fp)
    lw t6, -24(fp)
    la t1, __vtable_Partida
    sw t1, 0(t6)
    lw a0, -24(fp)
    lw a1, -12(fp)
    call constructor_Partida_1
    sw a0, -24(fp)
    lw t1, -24(fp)
    sw t1, -28(fp)
    li a0, 52
    call z_alloc
    sw a0, -32(fp)
    lw t6, -32(fp)
    la t1, __vtable_Guerrero
    sw t1, 0(t6)
    lw a0, -32(fp)
    li a1, 1
    la a2, str_28
    call constructor_Guerrero_1
    sw a0, -32(fp)
    lw t1, -32(fp)
    sw t1, -36(fp)
    li a0, 52
    call z_alloc
    sw a0, -40(fp)
    lw t6, -40(fp)
    la t1, __vtable_Mago
    sw t1, 0(t6)
    lw a0, -40(fp)
    li a1, 2
    la a2, str_29
    call constructor_Mago_1
    sw a0, -40(fp)
    lw t1, -40(fp)
    sw t1, -44(fp)
    li a0, 56
    call z_alloc
    sw a0, -48(fp)
    lw t6, -48(fp)
    la t1, __vtable_Arquero
    sw t1, 0(t6)
    lw a0, -48(fp)
    li a1, 3
    la a2, str_30
    call constructor_Arquero_1
    sw a0, -48(fp)
    lw t1, -48(fp)
    sw t1, -52(fp)
    li a0, 44
    call z_alloc
    sw a0, -56(fp)
    lw t6, -56(fp)
    la t1, __vtable_Orco
    sw t1, 0(t6)
    lw a0, -56(fp)
    li a1, 10
    la a2, str_31
    call constructor_Orco_1
    sw a0, -56(fp)
    lw t1, -56(fp)
    sw t1, -60(fp)
    li a0, 44
    call z_alloc
    sw a0, -64(fp)
    lw t6, -64(fp)
    la t1, __vtable_OrcoJefe
    sw t1, 0(t6)
    lw a0, -64(fp)
    li a1, 11
    la a2, str_32
    call constructor_OrcoJefe_1
    sw a0, -64(fp)
    lw t1, -64(fp)
    sw t1, -68(fp)
    li a0, 44
    call z_alloc
    sw a0, -72(fp)
    lw t6, -72(fp)
    la t1, __vtable_Dragon
    sw t1, 0(t6)
    lw a0, -72(fp)
    li a1, 12
    call constructor_Dragon_1
    sw a0, -72(fp)
    lw t1, -72(fp)
    sw t1, -76(fp)
    lw a0, -28(fp)
    lw a1, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -80(fp)
    lw a0, -28(fp)
    lw a1, -44(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -84(fp)
    lw a0, -28(fp)
    lw a1, -52(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -88(fp)
    lw a0, -28(fp)
    lw a1, -60(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -92(fp)
    lw a0, -28(fp)
    lw a1, -68(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -96(fp)
    lw a0, -28(fp)
    lw a1, -76(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -100(fp)
    la a0, str_33
    call z_print_cadena
    call z_print_salto
    lw a0, -28(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -104(fp)
    lw a0, -28(fp)
    lw a1, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -108(fp)
    lw a0, -28(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -112(fp)
    la a0, str_34
    call z_print_cadena
    addi sp, sp, -8
    lw a0, -28(fp)
    li a1, 1
    li a2, 2
    li a3, 3
    li a4, 4
    li a5, 5
    li a6, 6
    li a7, 7
    li t1, 8
    sw t1, 0(sp)
    li t1, 9
    sw t1, 4(sp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    addi sp, sp, 8
    sw a0, -116(fp)
    lw a0, -116(fp)
    call z_print_entero
    call z_print_salto
    la a0, str_35
    call z_print_cadena
    lw a0, -52(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 76(t0)
    jalr t0
    sw a0, -120(fp)
    lw a0, -120(fp)
    call z_print_decimal
    call z_print_salto
    la a0, str_36
    call z_print_cadena
    lw a0, -44(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 68(t0)
    jalr t0
    sw a0, -124(fp)
    lw a0, -124(fp)
    call z_print_decimal
    call z_print_salto
    la a0, str_37
    call z_print_cadena
    lw a0, -44(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 76(t0)
    jalr t0
    sw a0, -128(fp)
    lw a0, -128(fp)
    call z_print_entero
    call z_print_salto
    lw a0, -36(fp)
    li a1, 25
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 72(t0)
    jalr t0
    sw a0, -132(fp)
    la a0, str_38
    call z_print_cadena
    lw a0, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 60(t0)
    jalr t0
    sw a0, -136(fp)
    lw a0, -136(fp)
    call z_print_entero
    call z_print_salto
    lw a0, -36(fp)
    lw a1, -44(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 48(t0)
    jalr t0
    sw a0, -140(fp)
    lw a0, -44(fp)
    lw a1, -52(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 48(t0)
    jalr t0
    sw a0, -144(fp)
    lw t1, -140(fp)
    lw t2, -144(fp)
    add t3, t1, t2
    sw t3, -148(fp)
    lw t1, -148(fp)
    sw t1, -20(fp)
    la a0, str_39
    call z_print_cadena
    lw a0, -20(fp)
    call z_print_entero
    call z_print_salto
    lw a0, -28(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 24(t0)
    jalr t0
    sw a0, -152(fp)
__epilogo_main:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 152
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

# z_print_decimal: imprime punto fijo escala 1000 (usa z_ftoa)
z_print_decimal:
    addi sp, sp, -4
    sw ra, 0(sp)
    call z_ftoa
    call z_print_cadena
    lw ra, 0(sp)
    addi sp, sp, 4
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
# Formato: minimo de decimales, al menos uno (2000.0, 1.5, 0.05)
# Destruye: t0-t6 (todos temporales). Preserva: s0-s4, ra.
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
