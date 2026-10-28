.data
.align 2
str_0: .asciz "S - Leyenda zetariana"
.align 2
str_1: .asciz "A - Explorador experto"
.align 2
str_2: .asciz "B - Aprendiz"
.align 2
str_3: .asciz "C - Los cerdos te atraparon"
.align 2
str_4: .asciz "===== RESUMEN DE MISION ====="
.align 2
str_5: .asciz "Ultimo jugador: "
.align 2
str_6: .asciz "Partidas jugadas: "
.align 2
str_7: .asciz "Partida "
.align 2
str_8: .asciz ": "
.align 2
str_9: .asciz " puntos"
.align 2
str_10: .asciz "Mejor puntaje: "
.align 2
str_11: .asciz "Jugador: "
.align 2
str_12: .asciz " ("
.align 2
str_13: .asciz ") | Puntos: "
.align 2
str_14: .asciz " | Vidas: "
.align 2
str_15: .asciz " | Movimientos: "
.align 2
str_16: .asciz "Anonimo"
.align 2
str_17: .asciz "["
.align 2
str_18: .asciz "] "
.align 2
str_19: .asciz " (danio "
.align 2
str_20: .asciz ", "
.align 2
str_21: .asciz " paso(s)/turno)"
.align 2
str_22: .asciz " #"
.align 2
str_23: .asciz " en ("
.align 2
str_24: .asciz ","
.align 2
str_25: .asciz ")"
.align 2
str_26: .asciz "Sin nombre"
.align 2
str_27: .asciz "Posicion invalida: "
.align 2
str_28: .asciz "  "
.align 2
str_29: .asciz " "
.align 2
str_30: .asciz ". "
.align 2
str_31: .asciz "# "
.align 2
str_32: .asciz "$ "
.align 2
str_33: .asciz "X "
.align 2
str_34: .asciz "* "
.align 2
str_35: .asciz "? "
.align 2
str_36: .asciz "Slot "
.align 2
str_37: .asciz ": vacio (null)"
.align 2
str_38: .asciz " | "
.align 2
str_39: .asciz " te atrapo!"
.align 2
str_40: .asciz "Direccion invalida"
.align 2
str_41: .asciz "Choque contra una pared"
.align 2
str_42: .asciz "Encontraste un tesoro! Quedan "
.align 2
str_43: .asciz "Pisaste una trampa! Vidas: "
.align 2
str_44: .asciz "Gema bonus!"
.align 2
str_45: .asciz "Avanzas a ("
.align 2
str_46: .asciz " -- Te quedaste sin vidas"
.align 2
str_47: .asciz " -- Recogiste todos los tesoros"
.align 2
str_48: .asciz "Mision cumplida"
.align 2
str_49: .asciz "Mision fallida"
.align 2
str_50: .asciz "Cerdo Pepe"
.align 2
str_51: .asciz "Fantasma Boo"
.align 2
str_52: .asciz "Comienza la mision"
.align 2
str_53: .asciz "Cadete"
.align 2
str_54: .asciz "Temporal"
.align 2
str_55: .asciz "Spectro"
.align 2
str_56: .asciz "Nadie"
.align 2
str_57: .asciz "=== LABERINTO DE TESOROS v2 ==="
.align 2
str_58: .asciz "Leyenda: P jugador, # pared, $ tesoro, X trampa, * gema, C cerdo, F fantasma"
.align 2
str_59: .asciz "Prueba de polimorfismo: "
.align 2
str_60: .asciz "sospechoso es null (correcto)"
.align 2
str_61: .asciz "\n"
.align 2
str_62: .asciz "=== RONDA \n ronda \n ==="
.align 2
str_63: .asciz "Ingresa tu nombre:"
.align 2
str_64: .asciz "Celdas accesibles desde el inicio: "
.align 2
str_65: .asciz "Tesoros en el tablero: "
.align 2
str_66: .asciz "Camino libre a la derecha: "
.align 2
str_67: .asciz "Mover (1=arriba 2=abajo 3=izq 4=der, 0=salir):"
.align 2
str_68: .asciz "Limite de turnos alcanzado"
.align 2
str_69: .asciz "--- Fin de la ronda ---"
.align 2
str_70: .asciz "Veredicto: "
.align 2
str_71: .asciz "Calificacion: "
.align 2
__vtable_Tablero:
    .word metodo_Tablero_getFilas_1
    .word metodo_Tablero_getColumnas_2
    .word metodo_Tablero_getTesorosRestantes_3
    .word metodo_Tablero_esValida_4
    .word metodo_Tablero_esPared_5
    .word metodo_Tablero_inicializar_7
    .word metodo_Tablero_puntosPorTipo_8
    .word metodo_Tablero_recoger_9
    .word metodo_Tablero_contarTipo_10
    .word metodo_Tablero_primeraColumnaLibre_11
    .word metodo_Tablero_distanciaAPared_12
    .word metodo_Tablero_contarAccesibles_14
    .word metodo_Tablero_imprimir_16
.align 2
__vtable_Fantasma:
    .word metodo_Entidad_configurar_1
    .word metodo_Entidad_getId_2
    .word metodo_Entidad_getNombre_3
    .word metodo_Entidad_getFila_4
    .word metodo_Entidad_getColumna_5
    .word metodo_Entidad_getPasosDados_6
    .word metodo_Entidad_colocarEn_7
    .word metodo_Entidad_estaEn_8
    .word metodo_Fantasma_simbolo_2
    .word metodo_Cerdo_describir_5
    .word metodo_Cerdo_actuar_6
    .word metodo_Cerdo_getDanio_1
    .word metodo_Fantasma_pasosPorTurno_1
    .word metodo_Cerdo_atrapa_3
.align 2
__vtable_Cerdo:
    .word metodo_Entidad_configurar_1
    .word metodo_Entidad_getId_2
    .word metodo_Entidad_getNombre_3
    .word metodo_Entidad_getFila_4
    .word metodo_Entidad_getColumna_5
    .word metodo_Entidad_getPasosDados_6
    .word metodo_Entidad_colocarEn_7
    .word metodo_Entidad_estaEn_8
    .word metodo_Cerdo_simbolo_4
    .word metodo_Cerdo_describir_5
    .word metodo_Cerdo_actuar_6
    .word metodo_Cerdo_getDanio_1
    .word metodo_Cerdo_pasosPorTurno_2
    .word metodo_Cerdo_atrapa_3
.align 2
__vtable_Juego:
    .word metodo_Juego_getTablero_1
    .word metodo_Juego_getJugador_2
    .word metodo_Juego_estaTerminado_3
    .word metodo_Juego_getMensaje_4
    .word metodo_Juego_imprimirTablero_5
    .word metodo_Juego_listarEnemigos_6
    .word metodo_Juego_mover_8
    .word metodo_Juego_obtenerVeredicto_9
.align 2
__vtable_Entidad:
    .word metodo_Entidad_configurar_1
    .word metodo_Entidad_getId_2
    .word metodo_Entidad_getNombre_3
    .word metodo_Entidad_getFila_4
    .word metodo_Entidad_getColumna_5
    .word metodo_Entidad_getPasosDados_6
    .word metodo_Entidad_colocarEn_7
    .word metodo_Entidad_estaEn_8
    .word metodo_Entidad_simbolo_9
    .word metodo_Entidad_describir_10
    .word metodo_Entidad_actuar_11
.align 2
__vtable_Jugador:
    .word metodo_Entidad_configurar_1
    .word metodo_Entidad_getId_2
    .word metodo_Entidad_getNombre_3
    .word metodo_Entidad_getFila_4
    .word metodo_Entidad_getColumna_5
    .word metodo_Entidad_getPasosDados_6
    .word metodo_Entidad_colocarEn_7
    .word metodo_Entidad_estaEn_8
    .word metodo_Jugador_simbolo_8
    .word metodo_Jugador_describir_9
    .word metodo_Entidad_actuar_11
    .word metodo_Jugador_getPuntos_1
    .word metodo_Jugador_getVidas_2
    .word metodo_Jugador_getMovimientos_3
    .word metodo_Jugador_sumarPuntos_4
    .word metodo_Jugador_perderVida_5
    .word metodo_Jugador_estaVivo_6
    .word metodo_Jugador_mover_7
    .word metodo_Jugador_mostrarEstado_10
.text
.globl main
main:
    call __entrada_main
    call z_exit

fun_calificar:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t1, -12(fp)
    li t2, 199
    slt t3, t2, t1
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L3
    j L2
L3:
    la a0, str_0
    j __epilogo_fun_calificar
    j L1
L2:
    lw t1, -12(fp)
    li t2, 99
    slt t3, t2, t1
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L5
    j L4
L5:
    la a0, str_1
    j __epilogo_fun_calificar
    j L1
L4:
    lw t1, -12(fp)
    li t2, 0
    slt t3, t2, t1
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L7
    j L6
L7:
    la a0, str_2
    j __epilogo_fun_calificar
    j L1
L6:
    la a0, str_3
    j __epilogo_fun_calificar
L1:
    j __epilogo_fun_calificar
__epilogo_fun_calificar:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

fun_registrarPartida:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t2, 3
    slt t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L9
    j L8
L9:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t2, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t2, 0(t6)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
L8:
    lw t1, -16(fp)
    lw t6, -12(fp)
    sw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t3, 0(t6)
    j __epilogo_fun_registrarPartida
__epilogo_fun_registrarPartida:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

fun_mejorPuntaje:
    addi sp, sp, -36
    sw ra, 32(sp)
    sw fp, 28(sp)
    addi fp, sp, 36
    sw a0, -12(fp)
    li t1, 0
    sw t1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
L10:
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L11
    j L13
L11:
    lw t2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -28(fp)
    lw t1, -28(fp)
    lw t2, -16(fp)
    slt t3, t2, t1
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L15
    j L14
L15:
    lw t2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -36(fp)
    lw t1, -36(fp)
    sw t1, -16(fp)
L14:
L12:
    lw t1, -20(fp)
    addi t3, t1, 1
    sw t3, -20(fp)
    j L10
L13:
    lw a0, -16(fp)
    j __epilogo_fun_mejorPuntaje
    j __epilogo_fun_mejorPuntaje
__epilogo_fun_mejorPuntaje:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 36
    mv fp, t0
    ret

fun_imprimirResumen:
    addi sp, sp, -60
    sw ra, 56(sp)
    sw fp, 52(sp)
    addi fp, sp, 60
    sw a0, -12(fp)
    la a0, str_4
    call z_print_cadena
    call z_print_salto
    la t1, str_5
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
    lw a0, -16(fp)
    call z_print_cadena
    call z_print_salto
    la t1, str_6
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
    sw a0, -20(fp)
    lw a0, -20(fp)
    call z_print_cadena
    call z_print_salto
    li t1, 0
    sw t1, -24(fp)
L16:
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L17
    j L19
L17:
    lw t1, -24(fp)
    li t2, 1
    add t3, t1, t2
    sw t3, -32(fp)
    la t1, str_7
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
    lw t2, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -44(fp)
    lw t1, -40(fp)
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
    lw t1, -48(fp)
    la t2, str_9
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
L18:
    lw t1, -24(fp)
    addi t3, t1, 1
    sw t3, -24(fp)
    j L16
L19:
    lw a0, -12(fp)
    call fun_mejorPuntaje
    sw a0, -56(fp)
    la t1, str_10
    lw t2, -56(fp)
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
    sw a0, -60(fp)
    lw a0, -60(fp)
    call z_print_cadena
    call z_print_salto
    j __epilogo_fun_imprimirResumen
__epilogo_fun_imprimirResumen:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 60
    mv fp, t0
    ret

metodo_Jugador_getPuntos_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw a0, 0(t6)
    j __epilogo_metodo_Jugador_getPuntos_1
    j __epilogo_metodo_Jugador_getPuntos_1
__epilogo_metodo_Jugador_getPuntos_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Jugador_getVidas_2:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    lw a0, 0(t6)
    j __epilogo_metodo_Jugador_getVidas_2
    j __epilogo_metodo_Jugador_getVidas_2
__epilogo_metodo_Jugador_getVidas_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Jugador_getMovimientos_3:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 32
    lw a0, 0(t6)
    j __epilogo_metodo_Jugador_getMovimientos_3
    j __epilogo_metodo_Jugador_getMovimientos_3
__epilogo_metodo_Jugador_getMovimientos_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Jugador_sumarPuntos_4:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    lw t2, -16(fp)
    add t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j __epilogo_metodo_Jugador_sumarPuntos_4
__epilogo_metodo_Jugador_sumarPuntos_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Jugador_perderVida_5:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    lw t1, 0(t6)
    addi t3, t1, -1
    lw t6, -12(fp)
    addi t6, t6, 28
    sw t3, 0(t6)
    j __epilogo_metodo_Jugador_perderVida_5
__epilogo_metodo_Jugador_perderVida_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Jugador_estaVivo_6:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    lw t1, 0(t6)
    li t2, 0
    slt t3, t2, t1
    sw t3, -16(fp)
    lw a0, -16(fp)
    j __epilogo_metodo_Jugador_estaVivo_6
    j __epilogo_metodo_Jugador_estaVivo_6
__epilogo_metodo_Jugador_estaVivo_6:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Jugador_mover_7:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    lw a2, -20(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 24(t0)
    jalr t0
    sw a0, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 32
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 32
    sw t3, 0(t6)
    j __epilogo_metodo_Jugador_mover_7
__epilogo_metodo_Jugador_mover_7:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

metodo_Jugador_simbolo_8:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 36
    lb a0, 0(t6)
    j __epilogo_metodo_Jugador_simbolo_8
    j __epilogo_metodo_Jugador_simbolo_8
__epilogo_metodo_Jugador_simbolo_8:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Jugador_describir_9:
    addi sp, sp, -48
    sw ra, 44(sp)
    sw fp, 40(sp)
    addi fp, sp, 48
    sw a0, -12(fp)
    la t1, str_11
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
    sw a0, -16(fp)
    lw t1, -16(fp)
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
    sw a0, -20(fp)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 36
    lb t2, 0(t6)
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
    sw a0, -24(fp)
    lw t1, -24(fp)
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
    sw a0, -28(fp)
    lw t1, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
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
    lw t6, -12(fp)
    addi t6, t6, 28
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
    sw a0, -44(fp)
    lw t1, -44(fp)
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
    lw a0, -48(fp)
    j __epilogo_metodo_Jugador_describir_9
    j __epilogo_metodo_Jugador_describir_9
__epilogo_metodo_Jugador_describir_9:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 48
    mv fp, t0
    ret

metodo_Jugador_mostrarEstado_10:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    lw a0, -12(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 36(t0)
    jalr t0
    sw a0, -16(fp)
    lw a0, -16(fp)
    call z_print_cadena
    call z_print_salto
    j __epilogo_metodo_Jugador_mostrarEstado_10
__epilogo_metodo_Jugador_mostrarEstado_10:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

constructor_Jugador_1:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    sw a3, -24(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    lw a2, -20(fp)
    lw a3, -24(fp)
    li a4, 1
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -28(fp)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 32
    sw t1, 0(t6)
    li t1, 3
    lw t6, -12(fp)
    addi t6, t6, 28
    sw t1, 0(t6)
    li t1, 80
    lw t6, -12(fp)
    addi t6, t6, 36
    sb t1, 0(t6)
    j __epilogo_constructor_Jugador_1
__epilogo_constructor_Jugador_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

constructor_Jugador_2:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    lw a0, -12(fp)
    la a1, str_16
    li a2, 0
    li a3, 0
    li a4, 1
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -16(fp)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 32
    sw t1, 0(t6)
    li t1, 3
    lw t6, -12(fp)
    addi t6, t6, 28
    sw t1, 0(t6)
    li t1, 80
    lw t6, -12(fp)
    addi t6, t6, 36
    sb t1, 0(t6)
    j __epilogo_constructor_Jugador_2
__epilogo_constructor_Jugador_2:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Fantasma_pasosPorTurno_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    li a0, 2
    j __epilogo_metodo_Fantasma_pasosPorTurno_1
    j __epilogo_metodo_Fantasma_pasosPorTurno_1
__epilogo_metodo_Fantasma_pasosPorTurno_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Fantasma_simbolo_2:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    li a0, 70
    j __epilogo_metodo_Fantasma_simbolo_2
    j __epilogo_metodo_Fantasma_simbolo_2
__epilogo_metodo_Fantasma_simbolo_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

constructor_Fantasma_1:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    sw a3, -24(fp)
    sw a4, -28(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    lw a2, -20(fp)
    lw a3, -24(fp)
    lw a4, -28(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -32(fp)
    li t1, 2
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j __epilogo_constructor_Fantasma_1
__epilogo_constructor_Fantasma_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

metodo_Cerdo_getDanio_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw a0, 0(t6)
    j __epilogo_metodo_Cerdo_getDanio_1
    j __epilogo_metodo_Cerdo_getDanio_1
__epilogo_metodo_Cerdo_getDanio_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Cerdo_pasosPorTurno_2:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    li a0, 1
    j __epilogo_metodo_Cerdo_pasosPorTurno_2
    j __epilogo_metodo_Cerdo_pasosPorTurno_2
__epilogo_metodo_Cerdo_pasosPorTurno_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Cerdo_atrapa_3:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw a0, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -20(fp)
    lw a0, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -24(fp)
    lw a0, -12(fp)
    lw a1, -20(fp)
    lw a2, -24(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -28(fp)
    lw a0, -28(fp)
    j __epilogo_metodo_Cerdo_atrapa_3
    j __epilogo_metodo_Cerdo_atrapa_3
__epilogo_metodo_Cerdo_atrapa_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

metodo_Cerdo_simbolo_4:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    li a0, 67
    j __epilogo_metodo_Cerdo_simbolo_4
    j __epilogo_metodo_Cerdo_simbolo_4
__epilogo_metodo_Cerdo_simbolo_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Cerdo_describir_5:
    addi sp, sp, -52
    sw ra, 48(sp)
    sw fp, 44(sp)
    addi fp, sp, 52
    sw a0, -12(fp)
    lw a0, -12(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 32(t0)
    jalr t0
    sw a0, -16(fp)
    la t1, str_17
    lw t2, -16(fp)
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
    sw a0, -20(fp)
    lw t1, -20(fp)
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
    sw a0, -24(fp)
    lw t1, -24(fp)
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
    sw a0, -28(fp)
    lw t1, -28(fp)
    la t2, str_19
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
    addi t6, t6, 24
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
    la t2, str_20
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
    lw a0, -12(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 48(t0)
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
    call z_itoa
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -48(fp)
    lw t1, -48(fp)
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
    sw a0, -52(fp)
    lw a0, -52(fp)
    j __epilogo_metodo_Cerdo_describir_5
    j __epilogo_metodo_Cerdo_describir_5
__epilogo_metodo_Cerdo_describir_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 52
    mv fp, t0
    ret

metodo_Cerdo_actuar_6:
    addi sp, sp, -136
    sw ra, 132(sp)
    sw fp, 128(sp)
    addi fp, sp, 136
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    li t1, 0
    sw t1, -24(fp)
L20:
    lw a0, -12(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 48(t0)
    jalr t0
    sw a0, -28(fp)
    lw t1, -24(fp)
    lw t2, -28(fp)
    slt t3, t1, t2
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L21
    j L23
L21:
    li t1, 0
    sw t1, -36(fp)
    li t1, 0
    sw t1, -40(fp)
    lw a0, -20(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -44(fp)
    lw t1, -44(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t2, 0(t6)
    slt t3, t2, t1
    sw t3, -48(fp)
    lw t1, -48(fp)
    bnez t1, L26
    j L25
L26:
    li t1, 1
    sw t1, -36(fp)
    j L24
L25:
    lw a0, -20(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -52(fp)
    lw t1, -52(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -56(fp)
    lw t1, -56(fp)
    bnez t1, L28
    j L27
L28:
    li t1, 1
    neg t3, t1
    sw t3, -60(fp)
    lw t1, -60(fp)
    sw t1, -36(fp)
    j L24
L27:
L24:
    lw a0, -20(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -64(fp)
    lw t1, -64(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t2, t1
    sw t3, -68(fp)
    lw t1, -68(fp)
    bnez t1, L31
    j L30
L31:
    li t1, 1
    sw t1, -40(fp)
    j L29
L30:
    lw a0, -20(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -72(fp)
    lw t1, -72(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -76(fp)
    lw t1, -76(fp)
    bnez t1, L33
    j L32
L33:
    li t1, 1
    neg t3, t1
    sw t3, -80(fp)
    lw t1, -80(fp)
    sw t1, -40(fp)
    j L29
L32:
L29:
    lw t1, -36(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -84(fp)
    lw t1, -84(fp)
    bnez t1, L35
    j L36
L35:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    lw t2, -36(fp)
    add t3, t1, t2
    sw t3, -88(fp)
    lw a0, -16(fp)
    lw a1, -88(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw a2, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -92(fp)
    lw t1, -92(fp)
    seqz t3, t1
    sw t3, -96(fp)
    lw t1, -96(fp)
    sw t1, -100(fp)
    j L37
L36:
    li t1, 0
    sw t1, -100(fp)
L37:
    lw t1, -100(fp)
    bnez t1, L39
    j L38
L39:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    lw t2, -36(fp)
    add t3, t1, t2
    sw t3, -104(fp)
    lw a0, -12(fp)
    lw a1, -104(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw a2, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 24(t0)
    jalr t0
    sw a0, -108(fp)
    j L34
L38:
    lw t1, -40(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -112(fp)
    lw t1, -112(fp)
    bnez t1, L40
    j L41
L40:
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    lw t2, -40(fp)
    add t3, t1, t2
    sw t3, -116(fp)
    lw a0, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a1, 0(t6)
    lw a2, -116(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -120(fp)
    lw t1, -120(fp)
    seqz t3, t1
    sw t3, -124(fp)
    lw t1, -124(fp)
    sw t1, -128(fp)
    j L42
L41:
    li t1, 0
    sw t1, -128(fp)
L42:
    lw t1, -128(fp)
    bnez t1, L44
    j L43
L44:
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    lw t2, -40(fp)
    add t3, t1, t2
    sw t3, -132(fp)
    lw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a1, 0(t6)
    lw a2, -132(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 24(t0)
    jalr t0
    sw a0, -136(fp)
    j L34
L43:
L34:
L22:
    lw t1, -24(fp)
    addi t3, t1, 1
    sw t3, -24(fp)
    j L20
L23:
    j __epilogo_metodo_Cerdo_actuar_6
__epilogo_metodo_Cerdo_actuar_6:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 136
    mv fp, t0
    ret

constructor_Cerdo_1:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    sw a3, -24(fp)
    sw a4, -28(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    lw a2, -20(fp)
    lw a3, -24(fp)
    lw a4, -28(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -32(fp)
    li t1, 1
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j __epilogo_constructor_Cerdo_1
__epilogo_constructor_Cerdo_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

metodo_Entidad_configurar_1:
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
    addi t6, t6, 16
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 20
    sw t1, 0(t6)
    j __epilogo_metodo_Entidad_configurar_1
__epilogo_metodo_Entidad_configurar_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

metodo_Entidad_getId_2:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw a0, 0(t6)
    j __epilogo_metodo_Entidad_getId_2
    j __epilogo_metodo_Entidad_getId_2
__epilogo_metodo_Entidad_getId_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Entidad_getNombre_3:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    j __epilogo_metodo_Entidad_getNombre_3
    j __epilogo_metodo_Entidad_getNombre_3
__epilogo_metodo_Entidad_getNombre_3:
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
    addi t6, t6, 8
    lw a0, 0(t6)
    j __epilogo_metodo_Entidad_getFila_4
    j __epilogo_metodo_Entidad_getFila_4
__epilogo_metodo_Entidad_getFila_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Entidad_getColumna_5:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw a0, 0(t6)
    j __epilogo_metodo_Entidad_getColumna_5
    j __epilogo_metodo_Entidad_getColumna_5
__epilogo_metodo_Entidad_getColumna_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Entidad_getPasosDados_6:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    lw a0, 0(t6)
    j __epilogo_metodo_Entidad_getPasosDados_6
    j __epilogo_metodo_Entidad_getPasosDados_6
__epilogo_metodo_Entidad_getPasosDados_6:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Entidad_colocarEn_7:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 20
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 20
    sw t3, 0(t6)
    j __epilogo_metodo_Entidad_colocarEn_7
__epilogo_metodo_Entidad_colocarEn_7:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Entidad_estaEn_8:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    lw t2, -16(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L45
    j L46
L45:
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    lw t2, -20(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -28(fp)
    lw t1, -28(fp)
    sw t1, -32(fp)
    j L47
L46:
    li t1, 0
    sw t1, -32(fp)
L47:
    lw a0, -32(fp)
    j __epilogo_metodo_Entidad_estaEn_8
    j __epilogo_metodo_Entidad_estaEn_8
__epilogo_metodo_Entidad_estaEn_8:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

metodo_Entidad_simbolo_9:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    li a0, 63
    j __epilogo_metodo_Entidad_simbolo_9
    j __epilogo_metodo_Entidad_simbolo_9
__epilogo_metodo_Entidad_simbolo_9:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Entidad_describir_10:
    addi sp, sp, -40
    sw ra, 36(sp)
    sw fp, 32(sp)
    addi fp, sp, 40
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
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
    sw a0, -16(fp)
    lw t1, -16(fp)
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
    sw a0, -20(fp)
    lw t1, -20(fp)
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
    sw a0, -24(fp)
    lw t1, -24(fp)
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
    sw a0, -28(fp)
    lw t1, -28(fp)
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
    sw a0, -32(fp)
    lw t1, -32(fp)
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
    la t2, str_25
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
    lw a0, -40(fp)
    j __epilogo_metodo_Entidad_describir_10
    j __epilogo_metodo_Entidad_describir_10
__epilogo_metodo_Entidad_describir_10:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 40
    mv fp, t0
    ret

metodo_Entidad_actuar_11:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    j __epilogo_metodo_Entidad_actuar_11
__epilogo_metodo_Entidad_actuar_11:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

constructor_Entidad_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    la t1, str_26
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
    sw t1, 0(t6)
    j __epilogo_constructor_Entidad_1
__epilogo_constructor_Entidad_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Tablero_getFilas_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw a0, 0(t6)
    j __epilogo_metodo_Tablero_getFilas_1
    j __epilogo_metodo_Tablero_getFilas_1
__epilogo_metodo_Tablero_getFilas_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Tablero_getColumnas_2:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw a0, 0(t6)
    j __epilogo_metodo_Tablero_getColumnas_2
    j __epilogo_metodo_Tablero_getColumnas_2
__epilogo_metodo_Tablero_getColumnas_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Tablero_getTesorosRestantes_3:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    lw a0, 0(t6)
    j __epilogo_metodo_Tablero_getTesorosRestantes_3
    j __epilogo_metodo_Tablero_getTesorosRestantes_3
__epilogo_metodo_Tablero_getTesorosRestantes_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Tablero_esValida_4:
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
    bnez t1, L54
    j L55
L54:
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    sw t1, -32(fp)
    j L56
L55:
    li t1, 0
    sw t1, -32(fp)
L56:
    lw t1, -32(fp)
    bnez t1, L51
    j L52
L51:
    lw t1, -20(fp)
    li t2, 0
    slt t3, t1, t2
    xori t3, t3, 1
    sw t3, -36(fp)
    lw t1, -36(fp)
    sw t1, -40(fp)
    j L53
L52:
    li t1, 0
    sw t1, -40(fp)
L53:
    lw t1, -40(fp)
    bnez t1, L48
    j L49
L48:
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -44(fp)
    lw t1, -44(fp)
    sw t1, -48(fp)
    j L50
L49:
    li t1, 0
    sw t1, -48(fp)
L50:
    lw a0, -48(fp)
    j __epilogo_metodo_Tablero_esValida_4
    j __epilogo_metodo_Tablero_esValida_4
__epilogo_metodo_Tablero_esValida_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 48
    mv fp, t0
    ret

metodo_Tablero_esPared_5:
    addi sp, sp, -40
    sw ra, 36(sp)
    sw fp, 32(sp)
    addi fp, sp, 40
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    lw a2, -20(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -24(fp)
    lw t1, -24(fp)
    seqz t3, t1
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L58
    j L57
L58:
    li a0, 1
    j __epilogo_metodo_Tablero_esPared_5
L57:
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
    lw t1, -36(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -40(fp)
    lw a0, -40(fp)
    j __epilogo_metodo_Tablero_esPared_5
    j __epilogo_metodo_Tablero_esPared_5
__epilogo_metodo_Tablero_esPared_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 40
    mv fp, t0
    ret

metodo_Tablero_colocar_6:
    addi sp, sp, -48
    sw ra, 44(sp)
    sw fp, 40(sp)
    addi fp, sp, 48
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    sw a3, -24(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    lw a2, -20(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -28(fp)
    lw t1, -28(fp)
    seqz t3, t1
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L60
    j L59
L60:
    la t1, str_27
    lw t2, -16(fp)
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
    sw a0, -44(fp)
    lw a0, -44(fp)
    call z_print_cadena
    call z_print_salto
    j __epilogo_metodo_Tablero_colocar_6
L59:
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
    lw t1, -24(fp)
    li t2, 2
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -48(fp)
    lw t1, -48(fp)
    bnez t1, L62
    j L61
L62:
    lw t6, -12(fp)
    addi t6, t6, 20
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 20
    sw t3, 0(t6)
L61:
    j __epilogo_metodo_Tablero_colocar_6
__epilogo_metodo_Tablero_colocar_6:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 48
    mv fp, t0
    ret

metodo_Tablero_inicializar_7:
    addi sp, sp, -120
    sw ra, 116(sp)
    sw fp, 112(sp)
    addi fp, sp, 120
    sw a0, -12(fp)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 20
    sw t1, 0(t6)
    li t1, 0
    sw t1, -16(fp)
L63:
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L64
    j L66
L64:
    li t1, 0
    sw t1, -24(fp)
L67:
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L68
    j L70
L68:
    lw t1, -16(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L79
    j L78
L78:
    lw t1, -24(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -36(fp)
    lw t1, -36(fp)
    sw t1, -40(fp)
    j L80
L79:
    li t1, 1
    sw t1, -40(fp)
L80:
    lw t1, -40(fp)
    bnez t1, L76
    j L75
L75:
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t2, 1
    sub t3, t1, t2
    sw t3, -44(fp)
    lw t1, -16(fp)
    lw t2, -44(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -48(fp)
    lw t1, -48(fp)
    sw t1, -52(fp)
    j L77
L76:
    li t1, 1
    sw t1, -52(fp)
L77:
    lw t1, -52(fp)
    bnez t1, L73
    j L72
L72:
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t1, 0(t6)
    li t2, 1
    sub t3, t1, t2
    sw t3, -56(fp)
    lw t1, -24(fp)
    lw t2, -56(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -60(fp)
    lw t1, -60(fp)
    sw t1, -64(fp)
    j L74
L73:
    li t1, 1
    sw t1, -64(fp)
L74:
    lw t1, -64(fp)
    bnez t1, L82
    j L81
L82:
    lw t2, -16(fp)
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
    li t1, 1
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
    lw t2, -24(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    j L71
L81:
    lw t2, -16(fp)
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
    li t1, 0
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
    lw t2, -24(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
L71:
L69:
    lw t1, -24(fp)
    addi t3, t1, 1
    sw t3, -24(fp)
    j L67
L70:
L65:
    lw t1, -16(fp)
    addi t3, t1, 1
    sw t3, -16(fp)
    j L63
L66:
    lw a0, -12(fp)
    li a1, 2
    li a2, 2
    li a3, 1
    call metodo_Tablero_colocar_6
    sw a0, -68(fp)
    lw a0, -12(fp)
    li a1, 2
    li a2, 3
    li a3, 1
    call metodo_Tablero_colocar_6
    sw a0, -72(fp)
    lw a0, -12(fp)
    li a1, 2
    li a2, 4
    li a3, 1
    call metodo_Tablero_colocar_6
    sw a0, -76(fp)
    lw a0, -12(fp)
    li a1, 4
    li a2, 4
    li a3, 1
    call metodo_Tablero_colocar_6
    sw a0, -80(fp)
    lw a0, -12(fp)
    li a1, 4
    li a2, 5
    li a3, 1
    call metodo_Tablero_colocar_6
    sw a0, -84(fp)
    lw a0, -12(fp)
    li a1, 4
    li a2, 6
    li a3, 1
    call metodo_Tablero_colocar_6
    sw a0, -88(fp)
    lw a0, -12(fp)
    li a1, 1
    li a2, 7
    li a3, 2
    call metodo_Tablero_colocar_6
    sw a0, -92(fp)
    lw a0, -12(fp)
    li a1, 3
    li a2, 1
    li a3, 2
    call metodo_Tablero_colocar_6
    sw a0, -96(fp)
    lw a0, -12(fp)
    li a1, 5
    li a2, 4
    li a3, 2
    call metodo_Tablero_colocar_6
    sw a0, -100(fp)
    lw a0, -12(fp)
    li a1, 5
    li a2, 7
    li a3, 2
    call metodo_Tablero_colocar_6
    sw a0, -104(fp)
    lw a0, -12(fp)
    li a1, 3
    li a2, 3
    li a3, 3
    call metodo_Tablero_colocar_6
    sw a0, -108(fp)
    lw a0, -12(fp)
    li a1, 3
    li a2, 5
    li a3, 3
    call metodo_Tablero_colocar_6
    sw a0, -112(fp)
    lw a0, -12(fp)
    li a1, 5
    li a2, 2
    li a3, 3
    call metodo_Tablero_colocar_6
    sw a0, -116(fp)
    lw a0, -12(fp)
    li a1, 2
    li a2, 7
    li a3, 4
    call metodo_Tablero_colocar_6
    sw a0, -120(fp)
    j __epilogo_metodo_Tablero_inicializar_7
__epilogo_metodo_Tablero_inicializar_7:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 120
    mv fp, t0
    ret

metodo_Tablero_puntosPorTipo_8:
    addi sp, sp, -44
    sw ra, 40(sp)
    sw fp, 36(sp)
    addi fp, sp, 44
    sw a0, -12(fp)
    sw a1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
    lw t1, -16(fp)
    li t2, 2
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L84
    lw t1, -16(fp)
    li t2, 4
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L85
    lw t1, -16(fp)
    li t2, 3
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L86
    j L87
L84:
    lw t1, -20(fp)
    li t2, 50
    add t3, t1, t2
    sw t3, -36(fp)
    lw t1, -36(fp)
    sw t1, -20(fp)
L85:
    lw t1, -20(fp)
    li t2, 10
    add t3, t1, t2
    sw t3, -40(fp)
    lw t1, -40(fp)
    sw t1, -20(fp)
    j L83
L86:
    lw t1, -20(fp)
    li t2, 20
    sub t3, t1, t2
    sw t3, -44(fp)
    lw t1, -44(fp)
    sw t1, -20(fp)
    j L83
L87:
    li t1, 0
    sw t1, -20(fp)
L83:
    lw a0, -20(fp)
    j __epilogo_metodo_Tablero_puntosPorTipo_8
    j __epilogo_metodo_Tablero_puntosPorTipo_8
__epilogo_metodo_Tablero_puntosPorTipo_8:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 44
    mv fp, t0
    ret

metodo_Tablero_recoger_9:
    addi sp, sp, -48
    sw ra, 44(sp)
    sw fp, 40(sp)
    addi fp, sp, 48
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
    lw t1, -28(fp)
    sw t1, -32(fp)
    lw t1, -32(fp)
    li t2, 2
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L90
    j L89
L90:
    lw t6, -12(fp)
    addi t6, t6, 20
    lw t1, 0(t6)
    addi t3, t1, -1
    lw t6, -12(fp)
    addi t6, t6, 20
    sw t3, 0(t6)
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
    li t1, 0
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
    j L88
L89:
    lw t1, -32(fp)
    li t2, 3
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -40(fp)
    lw t1, -40(fp)
    bnez t1, L92
    j L91
L91:
    lw t1, -32(fp)
    li t2, 4
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -44(fp)
    lw t1, -44(fp)
    sw t1, -48(fp)
    j L93
L92:
    li t1, 1
    sw t1, -48(fp)
L93:
    lw t1, -48(fp)
    bnez t1, L95
    j L94
L95:
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
    li t1, 0
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
    j L88
L94:
L88:
    lw a0, -32(fp)
    j __epilogo_metodo_Tablero_recoger_9
    j __epilogo_metodo_Tablero_recoger_9
__epilogo_metodo_Tablero_recoger_9:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 48
    mv fp, t0
    ret

metodo_Tablero_contarTipo_10:
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
L96:
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L97
    j L99
L97:
    li t1, 0
    sw t1, -32(fp)
L100:
    lw t1, -32(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L101
    j L103
L101:
    lw t2, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -40(fp)
    lw t2, -32(fp)
    lw t1, -40(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -44(fp)
    lw t1, -44(fp)
    lw t2, -16(fp)
    sub t3, t1, t2
    snez t3, t3
    sw t3, -48(fp)
    lw t1, -48(fp)
    bnez t1, L105
    j L104
L105:
    j L102
L104:
    lw t1, -20(fp)
    addi t3, t1, 1
    sw t3, -20(fp)
L102:
    lw t1, -32(fp)
    addi t3, t1, 1
    sw t3, -32(fp)
    j L100
L103:
L98:
    lw t1, -24(fp)
    addi t3, t1, 1
    sw t3, -24(fp)
    j L96
L99:
    lw a0, -20(fp)
    j __epilogo_metodo_Tablero_contarTipo_10
    j __epilogo_metodo_Tablero_contarTipo_10
__epilogo_metodo_Tablero_contarTipo_10:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 48
    mv fp, t0
    ret

metodo_Tablero_primeraColumnaLibre_11:
    addi sp, sp, -40
    sw ra, 36(sp)
    sw fp, 32(sp)
    addi fp, sp, 40
    sw a0, -12(fp)
    sw a1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
L106:
    lw t1, -20(fp)
    addi t3, t1, 1
    sw t3, -20(fp)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L108
    j L109
L108:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -28(fp)
    lw t2, -20(fp)
    lw t1, -28(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -32(fp)
    lw t1, -32(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -36(fp)
    lw t1, -36(fp)
    sw t1, -40(fp)
    j L110
L109:
    li t1, 0
    sw t1, -40(fp)
L110:
    lw t1, -40(fp)
    bnez t1, L106
    j L107
L107:
    lw a0, -20(fp)
    j __epilogo_metodo_Tablero_primeraColumnaLibre_11
    j __epilogo_metodo_Tablero_primeraColumnaLibre_11
__epilogo_metodo_Tablero_primeraColumnaLibre_11:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 40
    mv fp, t0
    ret

metodo_Tablero_distanciaAPared_12:
    addi sp, sp, -72
    sw ra, 68(sp)
    sw fp, 64(sp)
    addi fp, sp, 72
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    sw a3, -24(fp)
    sw a4, -28(fp)
    li t1, 0
    sw t1, -32(fp)
    lw t1, -16(fp)
    sw t1, -36(fp)
    lw t1, -20(fp)
    sw t1, -40(fp)
L111:
    lw t1, -36(fp)
    lw t2, -24(fp)
    add t3, t1, t2
    sw t3, -44(fp)
    lw t1, -44(fp)
    sw t1, -36(fp)
    lw t1, -40(fp)
    lw t2, -28(fp)
    add t3, t1, t2
    sw t3, -48(fp)
    lw t1, -48(fp)
    sw t1, -40(fp)
    lw a0, -12(fp)
    lw a1, -36(fp)
    lw a2, -40(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -52(fp)
    lw t1, -52(fp)
    seqz t3, t1
    sw t3, -56(fp)
    lw t1, -56(fp)
    bnez t1, L117
    j L116
L116:
    lw t2, -36(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -60(fp)
    lw t2, -40(fp)
    lw t1, -60(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -64(fp)
    lw t1, -64(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -68(fp)
    lw t1, -68(fp)
    sw t1, -72(fp)
    j L118
L117:
    li t1, 1
    sw t1, -72(fp)
L118:
    lw t1, -72(fp)
    bnez t1, L119
    j L115
L119:
    j L114
L115:
    lw t1, -32(fp)
    addi t3, t1, 1
    sw t3, -32(fp)
L113:
    j L111
L114:
    lw a0, -32(fp)
    j __epilogo_metodo_Tablero_distanciaAPared_12
    j __epilogo_metodo_Tablero_distanciaAPared_12
__epilogo_metodo_Tablero_distanciaAPared_12:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 72
    mv fp, t0
    ret

metodo_Tablero_explorar_13:
    addi sp, sp, -96
    sw ra, 92(sp)
    sw fp, 88(sp)
    addi fp, sp, 96
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    lw a2, -20(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -24(fp)
    lw t1, -24(fp)
    seqz t3, t1
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L121
    j L120
L121:
    li a0, 0
    j __epilogo_metodo_Tablero_explorar_13
L120:
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
    lw t1, -36(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -40(fp)
    lw t1, -40(fp)
    bnez t1, L123
    j L122
L123:
    li a0, 0
    j __epilogo_metodo_Tablero_explorar_13
L122:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -44(fp)
    lw t2, -20(fp)
    lw t1, -44(fp)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lb t3, 0(t1)
    sw t3, -48(fp)
    lw t1, -48(fp)
    bnez t1, L125
    j L124
L125:
    li a0, 0
    j __epilogo_metodo_Tablero_explorar_13
L124:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 1
    mul t2, t2, t3
    add t1, t1, t2
    lw t2, -20(fp)
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
    lb t1, 0(t6)
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
    slli t2, t2, 2
    add t6, t6, t2
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -20(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 1
    mul t2, t2, t4
    add t6, t6, t2
    sb t1, 0(t6)
    lw t1, -16(fp)
    li t2, 1
    sub t3, t1, t2
    sw t3, -52(fp)
    lw a0, -12(fp)
    lw a1, -52(fp)
    lw a2, -20(fp)
    call metodo_Tablero_explorar_13
    sw a0, -56(fp)
    li t1, 1
    lw t2, -56(fp)
    add t3, t1, t2
    sw t3, -60(fp)
    lw t1, -16(fp)
    li t2, 1
    add t3, t1, t2
    sw t3, -64(fp)
    lw a0, -12(fp)
    lw a1, -64(fp)
    lw a2, -20(fp)
    call metodo_Tablero_explorar_13
    sw a0, -68(fp)
    lw t1, -60(fp)
    lw t2, -68(fp)
    add t3, t1, t2
    sw t3, -72(fp)
    lw t1, -20(fp)
    li t2, 1
    sub t3, t1, t2
    sw t3, -76(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    lw a2, -76(fp)
    call metodo_Tablero_explorar_13
    sw a0, -80(fp)
    lw t1, -72(fp)
    lw t2, -80(fp)
    add t3, t1, t2
    sw t3, -84(fp)
    lw t1, -20(fp)
    li t2, 1
    add t3, t1, t2
    sw t3, -88(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    lw a2, -88(fp)
    call metodo_Tablero_explorar_13
    sw a0, -92(fp)
    lw t1, -84(fp)
    lw t2, -92(fp)
    add t3, t1, t2
    sw t3, -96(fp)
    lw a0, -96(fp)
    j __epilogo_metodo_Tablero_explorar_13
    j __epilogo_metodo_Tablero_explorar_13
__epilogo_metodo_Tablero_explorar_13:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 96
    mv fp, t0
    ret

metodo_Tablero_contarAccesibles_14:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
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
    j Lnew_0_loop
Lnew_0_fin:
    lw a0, 8(sp)
    addi sp, sp, 16
    sw a0, -24(fp)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    lw a0, -12(fp)
    lw a1, -16(fp)
    lw a2, -20(fp)
    call metodo_Tablero_explorar_13
    sw a0, -28(fp)
    lw a0, -28(fp)
    j __epilogo_metodo_Tablero_contarAccesibles_14
    j __epilogo_metodo_Tablero_contarAccesibles_14
__epilogo_metodo_Tablero_contarAccesibles_14:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

metodo_Tablero_simboloEnemigo_15:
    addi sp, sp, -64
    sw ra, 60(sp)
    sw fp, 56(sp)
    addi fp, sp, 64
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    sw a3, -24(fp)
    sw a4, -28(fp)
    li t1, 0
    sw t1, -32(fp)
L126:
    lw t1, -32(fp)
    lw t2, -20(fp)
    slt t3, t1, t2
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L127
    j L129
L127:
    lw t2, -32(fp)
    lw t1, -16(fp)
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
    bnez t1, L131
    j L132
L131:
    lw t2, -32(fp)
    lw t1, -16(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -48(fp)
    lw a0, -48(fp)
    lw a1, -24(fp)
    lw a2, -28(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -52(fp)
    lw t1, -52(fp)
    sw t1, -56(fp)
    j L133
L132:
    li t1, 0
    sw t1, -56(fp)
L133:
    lw t1, -56(fp)
    bnez t1, L134
    j L130
L134:
    lw t2, -32(fp)
    lw t1, -16(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -60(fp)
    lw a0, -60(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 32(t0)
    jalr t0
    sw a0, -64(fp)
    lw a0, -64(fp)
    j __epilogo_metodo_Tablero_simboloEnemigo_15
L130:
L128:
    lw t1, -32(fp)
    addi t3, t1, 1
    sw t3, -32(fp)
    j L126
L129:
    li a0, 32
    j __epilogo_metodo_Tablero_simboloEnemigo_15
    j __epilogo_metodo_Tablero_simboloEnemigo_15
__epilogo_metodo_Tablero_simboloEnemigo_15:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 64
    mv fp, t0
    ret

metodo_Tablero_imprimir_16:
    addi sp, sp, -152
    sw ra, 148(sp)
    sw fp, 144(sp)
    addi fp, sp, 152
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    sw a3, -24(fp)
    la t1, str_28
    sw t1, -28(fp)
    li t1, 0
    sw t1, -32(fp)
L135:
    lw t1, -32(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L136
    j L138
L136:
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
    sw a0, -40(fp)
    lw t1, -40(fp)
    la t2, str_29
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
    sw t1, -28(fp)
L137:
    lw t1, -32(fp)
    addi t3, t1, 1
    sw t3, -32(fp)
    j L135
L138:
    lw a0, -28(fp)
    call z_print_cadena
    call z_print_salto
    li t1, 0
    sw t1, -48(fp)
L139:
    lw t1, -48(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -52(fp)
    lw t1, -52(fp)
    bnez t1, L140
    j L142
L140:
    lw t1, -48(fp)
    la t2, str_29
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw a0, 0(sp)
    call z_itoa
    sw a0, 8(sp)
    lw a0, 4(sp)
    mv a1, a0
    lw a0, 8(sp)
    call z_concat
    addi sp, sp, 12
    sw a0, -56(fp)
    lw t1, -56(fp)
    sw t1, -60(fp)
    li t1, 0
    sw t1, -32(fp)
L143:
    lw t1, -32(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -64(fp)
    lw t1, -64(fp)
    bnez t1, L144
    j L146
L144:
    lw a0, -12(fp)
    lw a1, -20(fp)
    lw a2, -24(fp)
    lw a3, -48(fp)
    lw a4, -32(fp)
    call metodo_Tablero_simboloEnemigo_15
    sw a0, -68(fp)
    lw t1, -68(fp)
    sw t1, -72(fp)
    lw a0, -16(fp)
    lw a1, -48(fp)
    lw a2, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -76(fp)
    lw t1, -76(fp)
    bnez t1, L149
    j L148
L149:
    lw a0, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 32(t0)
    jalr t0
    sw a0, -80(fp)
    lw t1, -60(fp)
    lw t2, -80(fp)
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
    sw a0, -84(fp)
    lw t1, -84(fp)
    la t2, str_29
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
    sw a0, -88(fp)
    lw t1, -88(fp)
    sw t1, -60(fp)
    j L147
L148:
    lw t1, -72(fp)
    li t2, 32
    sub t3, t1, t2
    snez t3, t3
    sw t3, -92(fp)
    lw t1, -92(fp)
    bnez t1, L151
    j L150
L151:
    lw t1, -60(fp)
    lw t2, -72(fp)
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
    sw a0, -96(fp)
    lw t1, -96(fp)
    la t2, str_29
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
    sw a0, -100(fp)
    lw t1, -100(fp)
    sw t1, -60(fp)
    j L147
L150:
    lw t2, -48(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -104(fp)
    lw t2, -32(fp)
    lw t1, -104(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -108(fp)
    lw t1, -108(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -112(fp)
    lw t1, -112(fp)
    bnez t1, L153
    lw t1, -108(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -116(fp)
    lw t1, -116(fp)
    bnez t1, L154
    lw t1, -108(fp)
    li t2, 2
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -120(fp)
    lw t1, -120(fp)
    bnez t1, L155
    lw t1, -108(fp)
    li t2, 3
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -124(fp)
    lw t1, -124(fp)
    bnez t1, L156
    lw t1, -108(fp)
    li t2, 4
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -128(fp)
    lw t1, -128(fp)
    bnez t1, L157
    j L158
L153:
    lw t1, -60(fp)
    la t2, str_30
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
    sw t1, -60(fp)
    j L152
L154:
    lw t1, -60(fp)
    la t2, str_31
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
    lw t1, -136(fp)
    sw t1, -60(fp)
    j L152
L155:
    lw t1, -60(fp)
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
    sw a0, -140(fp)
    lw t1, -140(fp)
    sw t1, -60(fp)
    j L152
L156:
    lw t1, -60(fp)
    la t2, str_33
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
    sw t1, -60(fp)
    j L152
L157:
    lw t1, -60(fp)
    la t2, str_34
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
    sw a0, -148(fp)
    lw t1, -148(fp)
    sw t1, -60(fp)
    j L152
L158:
    lw t1, -60(fp)
    la t2, str_35
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
    sw a0, -152(fp)
    lw t1, -152(fp)
    sw t1, -60(fp)
    j L152
L152:
L147:
L145:
    lw t1, -32(fp)
    addi t3, t1, 1
    sw t3, -32(fp)
    j L143
L146:
    lw a0, -60(fp)
    call z_print_cadena
    call z_print_salto
L141:
    lw t1, -48(fp)
    addi t3, t1, 1
    sw t3, -48(fp)
    j L139
L142:
    j __epilogo_metodo_Tablero_imprimir_16
__epilogo_metodo_Tablero_imprimir_16:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 152
    mv fp, t0
    ret

constructor_Tablero_1:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
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
Lnew_1_loop:
    lw t0, 12(sp)
    lw t1, 0(sp)
    bge t0, t1, Lnew_1_fin
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
    j Lnew_1_loop
Lnew_1_fin:
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
Lnew_2_loop:
    lw t0, 12(sp)
    lw t1, 0(sp)
    bge t0, t1, Lnew_2_fin
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
    j Lnew_2_loop
Lnew_2_fin:
    lw a0, 8(sp)
    addi sp, sp, 16
    sw a0, -28(fp)
    lw t1, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 20
    sw t1, 0(t6)
    j __epilogo_constructor_Tablero_1
__epilogo_constructor_Tablero_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

constructor_Tablero_2:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    li t1, 7
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
    li t1, 9
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
    li t1, 7
    li t2, 9
    addi sp, sp, -16
    sw t1, 0(sp)
    sw t2, 4(sp)
    slli a0, t1, 2
    call z_alloc
    sw a0, 8(sp)
    sw zero, 12(sp)
Lnew_3_loop:
    lw t0, 12(sp)
    lw t1, 0(sp)
    bge t0, t1, Lnew_3_fin
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
    j Lnew_3_loop
Lnew_3_fin:
    lw a0, 8(sp)
    addi sp, sp, 16
    sw a0, -16(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    li t1, 7
    li t2, 9
    addi sp, sp, -16
    sw t1, 0(sp)
    sw t2, 4(sp)
    slli a0, t1, 2
    call z_alloc
    sw a0, 8(sp)
    sw zero, 12(sp)
Lnew_4_loop:
    lw t0, 12(sp)
    lw t1, 0(sp)
    bge t0, t1, Lnew_4_fin
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
    j Lnew_4_loop
Lnew_4_fin:
    lw a0, 8(sp)
    addi sp, sp, 16
    sw a0, -20(fp)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 20
    sw t1, 0(t6)
    j __epilogo_constructor_Tablero_2
__epilogo_constructor_Tablero_2:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Juego_getTablero_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    j __epilogo_metodo_Juego_getTablero_1
    j __epilogo_metodo_Juego_getTablero_1
__epilogo_metodo_Juego_getTablero_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Juego_getJugador_2:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    j __epilogo_metodo_Juego_getJugador_2
    j __epilogo_metodo_Juego_getJugador_2
__epilogo_metodo_Juego_getJugador_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Juego_estaTerminado_3:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 20
    lb a0, 0(t6)
    j __epilogo_metodo_Juego_estaTerminado_3
    j __epilogo_metodo_Juego_estaTerminado_3
__epilogo_metodo_Juego_estaTerminado_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Juego_getMensaje_4:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw a0, 0(t6)
    j __epilogo_metodo_Juego_getMensaje_4
    j __epilogo_metodo_Juego_getMensaje_4
__epilogo_metodo_Juego_getMensaje_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Juego_imprimirTablero_5:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw a2, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw a3, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 48(t0)
    jalr t0
    sw a0, -16(fp)
    j __epilogo_metodo_Juego_imprimirTablero_5
__epilogo_metodo_Juego_imprimirTablero_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Juego_listarEnemigos_6:
    addi sp, sp, -56
    sw ra, 52(sp)
    sw fp, 48(sp)
    addi fp, sp, 56
    sw a0, -12(fp)
    li t1, 0
    sw t1, -16(fp)
L159:
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L160
    j L162
L160:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -24(fp)
    lw t1, -24(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L165
    j L164
L165:
    la t1, str_36
    lw t2, -16(fp)
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
    la t2, str_37
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
    lw a0, -36(fp)
    call z_print_cadena
    call z_print_salto
    j L163
L164:
    la t1, str_36
    lw t2, -16(fp)
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
    sw a0, -44(fp)
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -48(fp)
    lw a0, -48(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 36(t0)
    jalr t0
    sw a0, -52(fp)
    lw t1, -44(fp)
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
L163:
L161:
    lw t1, -16(fp)
    addi t3, t1, 1
    sw t3, -16(fp)
    j L159
L162:
    j __epilogo_metodo_Juego_listarEnemigos_6
__epilogo_metodo_Juego_listarEnemigos_6:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 56
    mv fp, t0
    ret

metodo_Juego_resolverContacto_7:
    addi sp, sp, -76
    sw ra, 72(sp)
    sw fp, 68(sp)
    addi fp, sp, 76
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -20(fp)
    lw t1, -20(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L167
    j L166
L167:
    j __epilogo_metodo_Juego_resolverContacto_7
L166:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -28(fp)
    lw a0, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a1, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 52(t0)
    jalr t0
    sw a0, -32(fp)
    lw t1, -32(fp)
    seqz t3, t1
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L169
    j L168
L169:
    j __epilogo_metodo_Juego_resolverContacto_7
L168:
    li t1, 0
    sw t1, -40(fp)
L170:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -44(fp)
    lw a0, -44(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 44(t0)
    jalr t0
    sw a0, -48(fp)
    lw t1, -40(fp)
    lw t2, -48(fp)
    slt t3, t1, t2
    sw t3, -52(fp)
    lw t1, -52(fp)
    bnez t1, L171
    j L173
L171:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 60(t0)
    jalr t0
    sw a0, -56(fp)
L172:
    lw t1, -40(fp)
    addi t3, t1, 1
    sw t3, -40(fp)
    j L170
L173:
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    la t2, str_38
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
    sw a0, -60(fp)
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -64(fp)
    lw a0, -64(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -68(fp)
    lw t1, -60(fp)
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
    la t2, str_39
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
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    lw t2, -16(fp)
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    j __epilogo_metodo_Juego_resolverContacto_7
__epilogo_metodo_Juego_resolverContacto_7:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 76
    mv fp, t0
    ret

metodo_Juego_mover_8:
    addi sp, sp, -192
    sw ra, 188(sp)
    sw fp, 184(sp)
    addi fp, sp, 192
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -20(fp)
    lw t1, -20(fp)
    sw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -28(fp)
    lw t1, -28(fp)
    sw t1, -32(fp)
    lw t1, -16(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L175
    lw t1, -16(fp)
    li t2, 2
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -40(fp)
    lw t1, -40(fp)
    bnez t1, L176
    lw t1, -16(fp)
    li t2, 3
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -44(fp)
    lw t1, -44(fp)
    bnez t1, L177
    lw t1, -16(fp)
    li t2, 4
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -48(fp)
    lw t1, -48(fp)
    bnez t1, L178
    j L179
L175:
    lw t1, -24(fp)
    addi t3, t1, -1
    sw t3, -24(fp)
    j L174
L176:
    lw t1, -24(fp)
    addi t3, t1, 1
    sw t3, -24(fp)
    j L174
L177:
    lw t1, -32(fp)
    addi t3, t1, -1
    sw t3, -32(fp)
    j L174
L178:
    lw t1, -32(fp)
    addi t3, t1, 1
    sw t3, -32(fp)
    j L174
L179:
    la t1, str_40
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    li a0, 0
    j __epilogo_metodo_Juego_mover_8
L174:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    lw a1, -24(fp)
    lw a2, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -52(fp)
    lw t1, -52(fp)
    seqz t3, t1
    sw t3, -56(fp)
    lw t1, -56(fp)
    bnez t1, L182
    j L181
L181:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    lw a1, -24(fp)
    lw a2, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -60(fp)
    lw t1, -60(fp)
    sw t1, -64(fp)
    j L183
L182:
    li t1, 1
    sw t1, -64(fp)
L183:
    lw t1, -64(fp)
    bnez t1, L184
    j L180
L184:
    la t1, str_41
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    li a0, 0
    j __epilogo_metodo_Juego_mover_8
L180:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    lw a1, -24(fp)
    lw a2, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 68(t0)
    jalr t0
    sw a0, -68(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    lw a1, -24(fp)
    lw a2, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -72(fp)
    lw t1, -72(fp)
    sw t1, -76(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    lw a1, -76(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 24(t0)
    jalr t0
    sw a0, -80(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    lw a1, -80(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 56(t0)
    jalr t0
    sw a0, -84(fp)
    lw t1, -76(fp)
    li t2, 2
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -88(fp)
    lw t1, -88(fp)
    bnez t1, L187
    j L186
L187:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -92(fp)
    la t1, str_42
    lw t2, -92(fp)
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
    sw a0, -96(fp)
    lw t1, -96(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j L185
L186:
    lw t1, -76(fp)
    li t2, 3
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -100(fp)
    lw t1, -100(fp)
    bnez t1, L189
    j L188
L189:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 60(t0)
    jalr t0
    sw a0, -104(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 48(t0)
    jalr t0
    sw a0, -108(fp)
    la t1, str_43
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
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j L185
L188:
    lw t1, -76(fp)
    li t2, 4
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -116(fp)
    lw t1, -116(fp)
    bnez t1, L191
    j L190
L191:
    la t1, str_44
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j L185
L190:
    la t1, str_45
    lw t2, -24(fp)
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
    sw a0, -120(fp)
    lw t1, -120(fp)
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
    sw a0, -124(fp)
    lw t1, -124(fp)
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
    sw a0, -128(fp)
    lw t1, -128(fp)
    la t2, str_25
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
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
L185:
    li t1, 0
    sw t1, -136(fp)
L192:
    lw t1, -136(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -140(fp)
    lw t1, -140(fp)
    bnez t1, L193
    j L195
L193:
    lw a0, -12(fp)
    lw a1, -136(fp)
    call metodo_Juego_resolverContacto_7
    sw a0, -144(fp)
L194:
    lw t1, -136(fp)
    addi t3, t1, 1
    sw t3, -136(fp)
    j L192
L195:
    li t1, 0
    sw t1, -136(fp)
L196:
    lw t1, -136(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -148(fp)
    lw t1, -148(fp)
    bnez t1, L197
    j L199
L197:
    lw t2, -136(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -152(fp)
    lw t1, -152(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -156(fp)
    lw t1, -156(fp)
    bnez t1, L201
    j L200
L201:
    lw t2, -136(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -160(fp)
    lw a0, -160(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a2, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 40(t0)
    jalr t0
    sw a0, -164(fp)
    lw a0, -12(fp)
    lw a1, -136(fp)
    call metodo_Juego_resolverContacto_7
    sw a0, -168(fp)
L200:
L198:
    lw t1, -136(fp)
    addi t3, t1, 1
    sw t3, -136(fp)
    j L196
L199:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 64(t0)
    jalr t0
    sw a0, -172(fp)
    lw t1, -172(fp)
    seqz t3, t1
    sw t3, -176(fp)
    lw t1, -176(fp)
    bnez t1, L204
    j L203
L204:
    li t1, 1
    lw t6, -12(fp)
    addi t6, t6, 20
    sb t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    la t2, str_46
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
    sw a0, -180(fp)
    lw t1, -180(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j L202
L203:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -184(fp)
    lw t1, -184(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -188(fp)
    lw t1, -188(fp)
    bnez t1, L206
    j L205
L206:
    li t1, 1
    lw t6, -12(fp)
    addi t6, t6, 20
    sb t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    la t2, str_47
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
    sw a0, -192(fp)
    lw t1, -192(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j L202
L205:
L202:
    li a0, 1
    j __epilogo_metodo_Juego_mover_8
    j __epilogo_metodo_Juego_mover_8
__epilogo_metodo_Juego_mover_8:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 192
    mv fp, t0
    ret

metodo_Juego_obtenerVeredicto_9:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 44(t0)
    jalr t0
    sw a0, -16(fp)
    lw t1, -16(fp)
    li t2, 100
    slt t3, t1, t2
    xori t3, t3, 1
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L208
    j L209
L208:
    la t1, str_48
    sw t1, -24(fp)
    j L207
L209:
    la t1, str_49
    sw t1, -24(fp)
    j L207
L207:
    lw t1, -24(fp)
    sw t1, -28(fp)
    lw a0, -28(fp)
    j __epilogo_metodo_Juego_obtenerVeredicto_9
    j __epilogo_metodo_Juego_obtenerVeredicto_9
__epilogo_metodo_Juego_obtenerVeredicto_9:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

constructor_Juego_1:
    addi sp, sp, -44
    sw ra, 40(sp)
    sw fp, 36(sp)
    addi fp, sp, 44
    sw a0, -12(fp)
    sw a1, -16(fp)
    li a0, 24
    call z_alloc
    sw a0, -20(fp)
    lw t6, -20(fp)
    la t1, __vtable_Tablero
    sw t1, 0(t6)
    lw a0, -20(fp)
    call constructor_Tablero_2
    sw a0, -20(fp)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -24(fp)
    li a0, 40
    call z_alloc
    sw a0, -28(fp)
    lw t6, -28(fp)
    la t1, __vtable_Jugador
    sw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a0, 0(t6)
    li a1, 1
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 36(t0)
    jalr t0
    sw a0, -32(fp)
    lw a0, -28(fp)
    lw a1, -16(fp)
    li a2, 1
    lw a3, -32(fp)
    call constructor_Jugador_1
    sw a0, -28(fp)
    lw t1, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    li t1, 3
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
    li a0, 12
    call z_alloc
    sw a0, -36(fp)
    lw t1, -36(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
    li a0, 28
    call z_alloc
    sw a0, -40(fp)
    lw t6, -40(fp)
    la t1, __vtable_Cerdo
    sw t1, 0(t6)
    lw a0, -40(fp)
    la a1, str_50
    li a2, 5
    li a3, 6
    li a4, 10
    call constructor_Cerdo_1
    sw a0, -40(fp)
    li t2, 0
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t1, -40(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    li t2, 0
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    li a0, 28
    call z_alloc
    sw a0, -44(fp)
    lw t6, -44(fp)
    la t1, __vtable_Fantasma
    sw t1, 0(t6)
    lw a0, -44(fp)
    la a1, str_51
    li a2, 3
    li a3, 7
    li a4, 11
    call constructor_Fantasma_1
    sw a0, -44(fp)
    li t2, 1
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t1, -44(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t6, 0(t6)
    addi sp, sp, -4
    sw t6, 0(sp)
    li t2, 1
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    li t2, 2
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t6, 0(t6)
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
    lw t6, -12(fp)
    addi t6, t6, 20
    sb t1, 0(t6)
    la t1, str_52
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j __epilogo_constructor_Juego_1
__epilogo_constructor_Juego_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 44
    mv fp, t0
    ret

__entrada_main:
    addi sp, sp, -216
    sw ra, 212(sp)
    sw fp, 208(sp)
    addi fp, sp, 216
    la t1, str_53
    sw t1, -12(fp)
    li t1, 99
    sw t1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
    li t1, 60
    sw t1, -24(fp)
    li a0, 28
    call z_alloc
    sw a0, -28(fp)
    lw t6, -28(fp)
    la t1, __vtable_Juego
    sw t1, 0(t6)
    lw a0, -28(fp)
    la a1, str_54
    call constructor_Juego_1
    sw a0, -28(fp)
    lw t1, -28(fp)
    sw t1, -32(fp)
    li t1, 0
    sw t1, -36(fp)
    li a0, 28
    call z_alloc
    sw a0, -40(fp)
    lw t6, -40(fp)
    la t1, __vtable_Fantasma
    sw t1, 0(t6)
    lw a0, -40(fp)
    la a1, str_55
    li a2, 2
    li a3, 2
    li a4, 99
    call constructor_Fantasma_1
    sw a0, -40(fp)
    lw t1, -40(fp)
    sw t1, -44(fp)
    li a0, 12
    call z_alloc
    sw a0, -48(fp)
    li t1, 0
    lw t6, -48(fp)
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
    lw t6, -48(fp)
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
    lw t6, -48(fp)
    addi sp, sp, -4
    sw t6, 0(sp)
    li t2, 2
    lw t6, 0(sp)
    addi sp, sp, 4
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    la t1, str_56
    addi t6, fp, -60
    sw t1, 0(t6)
    li t1, 0
    addi t6, fp, -60
    addi t6, t6, 4
    sw t1, 0(t6)
    lw t1, -48(fp)
    addi t6, fp, -60
    addi t6, t6, 8
    sw t1, 0(t6)
    la a0, str_57
    call z_print_cadena
    call z_print_salto
    la a0, str_58
    call z_print_cadena
    call z_print_salto
    la a0, str_59
    call z_print_cadena
    lw a0, -44(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 36(t0)
    jalr t0
    sw a0, -64(fp)
    lw a0, -64(fp)
    call z_print_cadena
    call z_print_salto
    lw t1, -36(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -68(fp)
    lw t1, -68(fp)
    bnez t1, L211
    j L210
L211:
    la a0, str_60
    call z_print_cadena
    call z_print_salto
L210:
    li t1, 1
    sw t1, -72(fp)
L212:
    lw t1, -72(fp)
    li t2, 3
    slt t3, t1, t2
    sw t3, -76(fp)
    lw t1, -76(fp)
    bnez t1, L213
    j L215
L213:
    la a0, str_61
    call z_print_cadena
    call z_print_salto
    la a0, str_62
    call z_print_cadena
    call z_print_salto
    la a0, str_63
    call z_print_cadena
    call z_print_salto
    li a0, 256
    call z_alloc
    mv t0, a0
    li a1, 256
    call z_leer_cadena
    mv a0, t0
    sw a0, -12(fp)
    li a0, 28
    call z_alloc
    sw a0, -80(fp)
    lw t6, -80(fp)
    la t1, __vtable_Juego
    sw t1, 0(t6)
    lw a0, -80(fp)
    lw a1, -12(fp)
    call constructor_Juego_1
    sw a0, -80(fp)
    lw t1, -80(fp)
    sw t1, -32(fp)
    la a0, str_64
    call z_print_cadena
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -84(fp)
    lw a0, -84(fp)
    li a1, 1
    li a2, 1
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 44(t0)
    jalr t0
    sw a0, -88(fp)
    lw a0, -88(fp)
    call z_print_entero
    call z_print_salto
    la a0, str_65
    call z_print_cadena
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -92(fp)
    lw a0, -92(fp)
    li a1, 2
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 32(t0)
    jalr t0
    sw a0, -96(fp)
    lw a0, -96(fp)
    call z_print_entero
    call z_print_salto
    la a0, str_66
    call z_print_cadena
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -100(fp)
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -104(fp)
    lw a0, -104(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -108(fp)
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -112(fp)
    lw a0, -112(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -116(fp)
    lw a0, -100(fp)
    lw a1, -108(fp)
    lw a2, -116(fp)
    li a3, 0
    li a4, 1
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 40(t0)
    jalr t0
    sw a0, -120(fp)
    lw a0, -120(fp)
    call z_print_entero
    call z_print_salto
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -124(fp)
    li t1, 99
    sw t1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
L216:
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -128(fp)
    lw t1, -128(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -132(fp)
    lw t1, -132(fp)
    bnez t1, L217
    j L218
L217:
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -136(fp)
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -140(fp)
    lw a0, -140(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 72(t0)
    jalr t0
    sw a0, -144(fp)
    la a0, str_67
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -16(fp)
    lw t1, -16(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -148(fp)
    lw t1, -148(fp)
    bnez t1, L220
    j L219
L220:
    j L218
L219:
    lw a0, -32(fp)
    lw a1, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 24(t0)
    jalr t0
    sw a0, -152(fp)
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -156(fp)
    lw a0, -156(fp)
    call z_print_cadena
    call z_print_salto
    lw t1, -20(fp)
    li t2, 1
    add t3, t1, t2
    sw t3, -160(fp)
    lw t1, -160(fp)
    sw t1, -20(fp)
    lw t1, -20(fp)
    lw t2, -24(fp)
    slt t3, t2, t1
    sw t3, -164(fp)
    lw t1, -164(fp)
    bnez t1, L222
    j L221
L222:
    la a0, str_68
    call z_print_cadena
    call z_print_salto
    j L218
L221:
    j L216
L218:
    la a0, str_69
    call z_print_cadena
    call z_print_salto
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -168(fp)
    lw a0, -168(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 72(t0)
    jalr t0
    sw a0, -172(fp)
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -176(fp)
    la a0, str_70
    call z_print_cadena
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -180(fp)
    lw a0, -180(fp)
    call z_print_cadena
    call z_print_salto
    la a0, str_71
    call z_print_cadena
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -184(fp)
    lw a0, -184(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 44(t0)
    jalr t0
    sw a0, -188(fp)
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -192(fp)
    lw a0, -192(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 52(t0)
    jalr t0
    sw a0, -196(fp)
    lw a0, -188(fp)
    lw a1, -196(fp)
    call fun_calificar
    sw a0, -200(fp)
    lw a0, -200(fp)
    call z_print_cadena
    call z_print_salto
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -204(fp)
    lw a0, -204(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 44(t0)
    jalr t0
    sw a0, -208(fp)
    addi a0, fp, -60
    lw a1, -12(fp)
    lw a2, -208(fp)
    call fun_registrarPartida
    sw a0, -212(fp)
L214:
    lw t1, -72(fp)
    addi t3, t1, 1
    sw t3, -72(fp)
    j L212
L215:
    addi a0, fp, -60
    call fun_imprimirResumen
    sw a0, -216(fp)
__epilogo_main:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 216
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
