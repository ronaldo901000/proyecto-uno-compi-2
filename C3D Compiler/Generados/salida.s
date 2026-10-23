.data
.align 2
str_0: .asciz "Tipo de operacion desconocido"
.align 2
str_1: .asciz "par"
.align 2
str_2: .asciz "impar"
.align 2
str_3: .asciz "===== REGISTRO DE OPERACIONES ====="
.align 2
str_4: .asciz "Operaciones totales: "
.align 2
str_5: .asciz "Inserciones: "
.align 2
str_6: .asciz "Eliminaciones: "
.align 2
str_7: .asciz "Busquedas exitosas: "
.align 2
str_8: .asciz "["
.align 2
str_9: .asciz " <-> "
.align 2
str_10: .asciz "]"
.align 2
str_11: .asciz "=== LISTA DOBLEMENTE ENLAZADA ==="
.align 2
str_12: .asciz "Lista inicial (adelante):"
.align 2
str_13: .asciz "Lista inicial (atras):"
.align 2
str_14: .asciz "Otra lista:"
.align 2
str_15: .asciz ""
.align 2
str_16: .asciz "1. Insertar al inicio    2. Insertar al final    3. Insertar en posicion"
.align 2
str_17: .asciz "4. Eliminar inicio       5. Eliminar final       6. Eliminar posicion"
.align 2
str_18: .asciz "7. Eliminar por valor    8. Buscar valor         9. Invertir"
.align 2
str_19: .asciz "10. Ordenar              11. Imprimir            12. Estadisticas"
.align 2
str_20: .asciz "13. Vaciar               0. Salir"
.align 2
str_21: .asciz "Valor:"
.align 2
str_22: .asciz "Posicion:"
.align 2
str_23: .asciz "Insertado en la posicion "
.align 2
str_24: .asciz "Posicion invalida"
.align 2
str_25: .asciz "Elemento eliminado del inicio"
.align 2
str_26: .asciz "Lista vacia"
.align 2
str_27: .asciz "Elemento eliminado del final"
.align 2
str_28: .asciz "Eliminado el elemento de la posicion "
.align 2
str_29: .asciz "Valor a eliminar:"
.align 2
str_30: .asciz "Valor "
.align 2
str_31: .asciz " eliminado"
.align 2
str_32: .asciz "El valor no existe"
.align 2
str_33: .asciz "Valor a buscar:"
.align 2
str_34: .asciz "No encontrado"
.align 2
str_35: .asciz "Encontrado en el indice "
.align 2
str_36: .asciz "Lista invertida"
.align 2
str_37: .asciz "Lista ordenada"
.align 2
str_38: .asciz "Adelante:"
.align 2
str_39: .asciz "Atras:"
.align 2
str_40: .asciz "La lista esta vacia"
.align 2
str_41: .asciz "Tamano: "
.align 2
str_42: .asciz " ("
.align 2
str_43: .asciz ")"
.align 2
str_44: .asciz "Suma: "
.align 2
str_45: .asciz "Maximo: "
.align 2
str_46: .asciz "Primero: "
.align 2
str_47: .asciz " | Ultimo: "
.align 2
str_48: .asciz "Lista vaciada"
.align 2
str_49: .asciz "Saliendo..."
.align 2
str_50: .asciz "Opcion invalida"
.align 2
__vtable_ListaDoble:
    .word metodo_ListaDoble_estaVacia_1
    .word metodo_ListaDoble_insertarInicio_2
    .word metodo_ListaDoble_insertarFinal_3
    .word metodo_ListaDoble_insertarFinal_4
    .word metodo_ListaDoble_insertarEn_5
    .word metodo_ListaDoble_eliminarInicio_6
    .word metodo_ListaDoble_eliminarFinal_7
    .word metodo_ListaDoble_eliminarEn_8
    .word metodo_ListaDoble_eliminarValor_9
    .word metodo_ListaDoble_vaciar_10
    .word metodo_ListaDoble_obtenerNodo_11
    .word metodo_ListaDoble_obtener_12
    .word metodo_ListaDoble_buscar_13
    .word metodo_ListaDoble_maximo_14
    .word metodo_ListaDoble_sumaRecursiva_15
    .word metodo_ListaDoble_suma_16
    .word metodo_ListaDoble_invertir_17
    .word metodo_ListaDoble_ordenar_18
    .word metodo_ListaDoble_imprimirAdelante_19
    .word metodo_ListaDoble_imprimirAtras_20
.text
.globl main
main:
    call __entrada_main
    call z_exit

fun_registrar:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    sw t3, 0(t6)
    lw t1, -16(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L2
    lw t1, -16(fp)
    li t2, 2
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L3
    lw t1, -16(fp)
    li t2, 3
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L4
    j L5
L2:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t3, 0(t6)
    j L1
L3:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t3, 0(t6)
    j L1
L4:
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t3, 0(t6)
    j L1
L5:
    la a0, str_0
    call z_print_cadena
    call z_print_salto
    j L1
L1:
    j __epilogo_fun_registrar
__epilogo_fun_registrar:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

fun_esPar:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    lw t1, -12(fp)
    li t2, 2
    div t3, t1, t2
    sw t3, -16(fp)
    lw t1, -16(fp)
    li t2, 2
    mul t3, t1, t2
    sw t3, -20(fp)
    lw t1, -12(fp)
    lw t2, -20(fp)
    sub t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -28(fp)
    lw a0, -28(fp)
    j __epilogo_fun_esPar
    j __epilogo_fun_esPar
__epilogo_fun_esPar:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

fun_paridad:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    lw a0, -12(fp)
    call fun_esPar
    sw a0, -16(fp)
    lw t1, -16(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L8
    j L7
L8:
    la a0, str_1
    j __epilogo_fun_paridad
    j L6
L7:
    la a0, str_2
    j __epilogo_fun_paridad
L6:
    j __epilogo_fun_paridad
__epilogo_fun_paridad:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

fun_imprimirRegistro:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
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
    lw t2, 4(sp)
    mv a0, t2
    call z_itoa
    sw a0, 4(sp)
    lw t1, 0(sp)
    lw a1, 4(sp)
    mv a0, t1
    call z_concat
    addi sp, sp, 12
    sw a0, -16(fp)
    lw a0, -16(fp)
    call z_print_cadena
    call z_print_salto
    la t1, str_5
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t2, 0(t6)
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw t2, 4(sp)
    mv a0, t2
    call z_itoa
    sw a0, 4(sp)
    lw t1, 0(sp)
    lw a1, 4(sp)
    mv a0, t1
    call z_concat
    addi sp, sp, 12
    sw a0, -20(fp)
    lw a0, -20(fp)
    call z_print_cadena
    call z_print_salto
    la t1, str_6
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t2, 0(t6)
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw t2, 4(sp)
    mv a0, t2
    call z_itoa
    sw a0, 4(sp)
    lw t1, 0(sp)
    lw a1, 4(sp)
    mv a0, t1
    call z_concat
    addi sp, sp, 12
    sw a0, -24(fp)
    lw a0, -24(fp)
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
    lw t2, 4(sp)
    mv a0, t2
    call z_itoa
    sw a0, 4(sp)
    lw t1, 0(sp)
    lw a1, 4(sp)
    mv a0, t1
    call z_concat
    addi sp, sp, 12
    sw a0, -28(fp)
    lw a0, -28(fp)
    call z_print_cadena
    call z_print_salto
    j __epilogo_fun_imprimirRegistro
__epilogo_fun_imprimirRegistro:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

constructor_Nodo_1:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
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
    j __epilogo_constructor_Nodo_1
__epilogo_constructor_Nodo_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_ListaDoble_estaVacia_1:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -16(fp)
    lw a0, -16(fp)
    j __epilogo_metodo_ListaDoble_estaVacia_1
    j __epilogo_metodo_ListaDoble_estaVacia_1
__epilogo_metodo_ListaDoble_estaVacia_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_ListaDoble_insertarInicio_2:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    sw a1, -16(fp)
    li a0, 16
    call z_alloc
    sw a0, -20(fp)
    lw a0, -20(fp)
    lw a1, -16(fp)
    call constructor_Nodo_1
    sw a0, -20(fp)
    lw t1, -20(fp)
    sw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L11
    j L10
L11:
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    j L9
L10:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    lw t6, -24(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t6, 0(t6)
    addi t6, t6, 12
    sw t1, 0(t6)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
L9:
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t3, 0(t6)
    j __epilogo_metodo_ListaDoble_insertarInicio_2
__epilogo_metodo_ListaDoble_insertarInicio_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

metodo_ListaDoble_insertarFinal_3:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    sw a1, -16(fp)
    li a0, 16
    call z_alloc
    sw a0, -20(fp)
    lw a0, -20(fp)
    lw a1, -16(fp)
    call constructor_Nodo_1
    sw a0, -20(fp)
    lw t1, -20(fp)
    sw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L14
    j L13
L14:
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    j L12
L13:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    lw t6, -24(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t6, 0(t6)
    addi t6, t6, 8
    sw t1, 0(t6)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
L12:
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t3, 0(t6)
    j __epilogo_metodo_ListaDoble_insertarFinal_3
__epilogo_metodo_ListaDoble_insertarFinal_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

metodo_ListaDoble_insertarFinal_4:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -24(fp)
    lw a0, -12(fp)
    lw a1, -20(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -28(fp)
    j __epilogo_metodo_ListaDoble_insertarFinal_4
__epilogo_metodo_ListaDoble_insertarFinal_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

metodo_ListaDoble_insertarEn_5:
    addi sp, sp, -64
    sw ra, 60(sp)
    sw fp, 56(sp)
    addi fp, sp, 64
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw t1, -16(fp)
    li t2, 0
    slt t3, t1, t2
    sw t3, -24(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t2, t1
    sw t3, -28(fp)
    lw t1, -24(fp)
    lw t2, -28(fp)
    or t3, t1, t2
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L16
    j L15
L16:
    li a0, 0
    j __epilogo_metodo_ListaDoble_insertarEn_5
L15:
    lw t1, -16(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L18
    j L17
L18:
    lw a0, -12(fp)
    lw a1, -20(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -40(fp)
    li a0, 1
    j __epilogo_metodo_ListaDoble_insertarEn_5
L17:
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -44(fp)
    lw t1, -44(fp)
    bnez t1, L20
    j L19
L20:
    lw a0, -12(fp)
    lw a1, -20(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -48(fp)
    li a0, 1
    j __epilogo_metodo_ListaDoble_insertarEn_5
L19:
    lw a0, -12(fp)
    lw a1, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 40(t0)
    jalr t0
    sw a0, -52(fp)
    lw t1, -52(fp)
    sw t1, -56(fp)
    li a0, 16
    call z_alloc
    sw a0, -60(fp)
    lw a0, -60(fp)
    lw a1, -20(fp)
    call constructor_Nodo_1
    sw a0, -60(fp)
    lw t1, -60(fp)
    sw t1, -64(fp)
    lw t6, -56(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    lw t6, -64(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
    lw t1, -56(fp)
    lw t6, -64(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    lw t1, -64(fp)
    lw t6, -56(fp)
    addi t6, t6, -1
    sw t1, 0(t6)
    lw t1, -64(fp)
    lw t6, -56(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t3, 0(t6)
    li a0, 1
    j __epilogo_metodo_ListaDoble_insertarEn_5
    j __epilogo_metodo_ListaDoble_insertarEn_5
__epilogo_metodo_ListaDoble_insertarEn_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 64
    mv fp, t0
    ret

metodo_ListaDoble_eliminarInicio_6:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -16(fp)
    lw t1, -16(fp)
    bnez t1, L22
    j L21
L22:
    li a0, 0
    j __epilogo_metodo_ListaDoble_eliminarInicio_6
L21:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t2, 0(t6)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L25
    j L24
L25:
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    j L23
L24:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t6, 0(t6)
    addi t6, t6, 8
    lw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t6, 0(t6)
    addi t6, t6, 12
    sw t1, 0(t6)
L23:
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    addi t3, t1, -1
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t3, 0(t6)
    li a0, 1
    j __epilogo_metodo_ListaDoble_eliminarInicio_6
    j __epilogo_metodo_ListaDoble_eliminarInicio_6
__epilogo_metodo_ListaDoble_eliminarInicio_6:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_ListaDoble_eliminarFinal_7:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -16(fp)
    lw t1, -16(fp)
    bnez t1, L27
    j L26
L27:
    li a0, 0
    j __epilogo_metodo_ListaDoble_eliminarFinal_7
L26:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t2, 0(t6)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L30
    j L29
L30:
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    j L28
L29:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t6, 0(t6)
    addi t6, t6, 12
    lw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t6, 0(t6)
    addi t6, t6, 8
    sw t1, 0(t6)
L28:
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    addi t3, t1, -1
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t3, 0(t6)
    li a0, 1
    j __epilogo_metodo_ListaDoble_eliminarFinal_7
    j __epilogo_metodo_ListaDoble_eliminarFinal_7
__epilogo_metodo_ListaDoble_eliminarFinal_7:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_ListaDoble_eliminarEn_8:
    addi sp, sp, -56
    sw ra, 52(sp)
    sw fp, 48(sp)
    addi fp, sp, 56
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t1, -16(fp)
    li t2, 0
    slt t3, t1, t2
    sw t3, -20(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    xori t3, t3, 1
    sw t3, -24(fp)
    lw t1, -20(fp)
    lw t2, -24(fp)
    or t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L32
    j L31
L32:
    li a0, 0
    j __epilogo_metodo_ListaDoble_eliminarEn_8
L31:
    lw t1, -16(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L34
    j L33
L34:
    lw a0, -12(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -36(fp)
    lw a0, -36(fp)
    j __epilogo_metodo_ListaDoble_eliminarEn_8
L33:
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t2, 1
    sub t3, t1, t2
    sw t3, -40(fp)
    lw t1, -16(fp)
    lw t2, -40(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -44(fp)
    lw t1, -44(fp)
    bnez t1, L36
    j L35
L36:
    lw a0, -12(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 24(t0)
    jalr t0
    sw a0, -48(fp)
    lw a0, -48(fp)
    j __epilogo_metodo_ListaDoble_eliminarEn_8
L35:
    lw a0, -12(fp)
    lw a1, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 40(t0)
    jalr t0
    sw a0, -52(fp)
    lw t1, -52(fp)
    sw t1, -56(fp)
    lw t6, -56(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    lw t6, -56(fp)
    addi t6, t6, -1
    sw t1, 0(t6)
    lw t6, -56(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    lw t6, -56(fp)
    addi t6, t6, -1
    sw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    addi t3, t1, -1
    lw t6, -12(fp)
    addi t6, t6, 12
    sw t3, 0(t6)
    li a0, 1
    j __epilogo_metodo_ListaDoble_eliminarEn_8
    j __epilogo_metodo_ListaDoble_eliminarEn_8
__epilogo_metodo_ListaDoble_eliminarEn_8:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 56
    mv fp, t0
    ret

metodo_ListaDoble_eliminarValor_9:
    addi sp, sp, -36
    sw ra, 32(sp)
    sw fp, 28(sp)
    addi fp, sp, 36
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 48(t0)
    jalr t0
    sw a0, -20(fp)
    lw t1, -20(fp)
    sw t1, -24(fp)
    li t1, 1
    neg t3, t1
    sw t3, -28(fp)
    lw t1, -24(fp)
    lw t2, -28(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L38
    j L37
L38:
    li a0, 0
    j __epilogo_metodo_ListaDoble_eliminarValor_9
L37:
    lw a0, -12(fp)
    lw a1, -24(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -36(fp)
    lw a0, -36(fp)
    j __epilogo_metodo_ListaDoble_eliminarValor_9
    j __epilogo_metodo_ListaDoble_eliminarValor_9
__epilogo_metodo_ListaDoble_eliminarValor_9:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 36
    mv fp, t0
    ret

metodo_ListaDoble_vaciar_10:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    li t1, 0
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
    j __epilogo_metodo_ListaDoble_vaciar_10
__epilogo_metodo_ListaDoble_vaciar_10:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_ListaDoble_obtenerNodo_11:
    addi sp, sp, -56
    sw ra, 52(sp)
    sw fp, 48(sp)
    addi fp, sp, 56
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t1, -16(fp)
    li t2, 0
    slt t3, t1, t2
    sw t3, -20(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t2, 0(t6)
    slt t3, t1, t2
    xori t3, t3, 1
    sw t3, -24(fp)
    lw t1, -20(fp)
    lw t2, -24(fp)
    or t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L40
    j L39
L40:
    li a0, 0
    j __epilogo_metodo_ListaDoble_obtenerNodo_11
L39:
    li t1, 0
    sw t1, -32(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t2, 2
    div t3, t1, t2
    sw t3, -36(fp)
    lw t1, -16(fp)
    lw t2, -36(fp)
    slt t3, t1, t2
    sw t3, -40(fp)
    lw t1, -40(fp)
    bnez t1, L43
    j L42
L43:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    sw t1, -32(fp)
    li t1, 0
    sw t1, -44(fp)
L44:
    lw t1, -44(fp)
    lw t2, -16(fp)
    slt t3, t1, t2
    sw t3, -48(fp)
    lw t1, -48(fp)
    bnez t1, L45
    j L47
L45:
    lw t6, -32(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    sw t1, -32(fp)
L46:
    lw t1, -44(fp)
    addi t3, t1, 1
    sw t3, -44(fp)
    j L44
L47:
    j L41
L42:
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    sw t1, -32(fp)
    lw t6, -12(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t2, 1
    sub t3, t1, t2
    sw t3, -52(fp)
    lw t1, -52(fp)
    sw t1, -44(fp)
L48:
    lw t1, -44(fp)
    lw t2, -16(fp)
    slt t3, t2, t1
    sw t3, -56(fp)
    lw t1, -56(fp)
    bnez t1, L49
    j L51
L49:
    lw t6, -32(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    sw t1, -32(fp)
L50:
    lw t1, -44(fp)
    addi t3, t1, -1
    sw t3, -44(fp)
    j L48
L51:
L41:
    lw a0, -32(fp)
    j __epilogo_metodo_ListaDoble_obtenerNodo_11
    j __epilogo_metodo_ListaDoble_obtenerNodo_11
__epilogo_metodo_ListaDoble_obtenerNodo_11:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 56
    mv fp, t0
    ret

metodo_ListaDoble_obtener_12:
    addi sp, sp, -36
    sw ra, 32(sp)
    sw fp, 28(sp)
    addi fp, sp, 36
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 40(t0)
    jalr t0
    sw a0, -20(fp)
    lw t1, -20(fp)
    sw t1, -24(fp)
    lw t1, -24(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L53
    j L54
L53:
    li t1, 1
    neg t3, t1
    sw t3, -32(fp)
    lw t1, -32(fp)
    sw t1, -36(fp)
    j L52
L54:
    lw t6, -24(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    sw t1, -36(fp)
    j L52
L52:
    lw a0, -36(fp)
    j __epilogo_metodo_ListaDoble_obtener_12
    j __epilogo_metodo_ListaDoble_obtener_12
__epilogo_metodo_ListaDoble_obtener_12:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 36
    mv fp, t0
    ret

metodo_ListaDoble_buscar_13:
    addi sp, sp, -36
    sw ra, 32(sp)
    sw fp, 28(sp)
    addi fp, sp, 36
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    sw t1, -20(fp)
    li t1, 0
    sw t1, -24(fp)
L55:
    lw t1, -20(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L56
    j L57
L56:
    lw t6, -20(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    lw t2, -16(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L59
    j L58
L59:
    lw a0, -24(fp)
    j __epilogo_metodo_ListaDoble_buscar_13
L58:
    lw t6, -20(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    sw t1, -20(fp)
    lw t1, -24(fp)
    addi t3, t1, 1
    sw t3, -24(fp)
    j L55
L57:
    li t1, 1
    neg t3, t1
    sw t3, -36(fp)
    lw a0, -36(fp)
    j __epilogo_metodo_ListaDoble_buscar_13
    j __epilogo_metodo_ListaDoble_buscar_13
__epilogo_metodo_ListaDoble_buscar_13:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 36
    mv fp, t0
    ret

metodo_ListaDoble_maximo_14:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -16(fp)
    lw t1, -16(fp)
    bnez t1, L61
    j L60
L61:
    li a0, 0
    j __epilogo_metodo_ListaDoble_maximo_14
L60:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t6, 0(t6)
    addi t6, t6, 4
    lw t1, 0(t6)
    sw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t6, 0(t6)
    addi t6, t6, 8
    lw t1, 0(t6)
    sw t1, -24(fp)
L62:
    lw t1, -24(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L63
    j L64
L63:
    lw t6, -24(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    lw t2, -20(fp)
    slt t3, t2, t1
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L66
    j L65
L66:
    lw t6, -24(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    sw t1, -20(fp)
L65:
    lw t6, -24(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    sw t1, -24(fp)
    j L62
L64:
    lw a0, -20(fp)
    j __epilogo_metodo_ListaDoble_maximo_14
    j __epilogo_metodo_ListaDoble_maximo_14
__epilogo_metodo_ListaDoble_maximo_14:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

metodo_ListaDoble_sumaRecursiva_15:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t1, -16(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L68
    j L67
L68:
    li a0, 0
    j __epilogo_metodo_ListaDoble_sumaRecursiva_15
L67:
    lw a0, -12(fp)
    lw t6, -16(fp)
    addi t6, t6, 8
    lw a1, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 56(t0)
    jalr t0
    sw a0, -24(fp)
    lw t6, -16(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    lw t2, -24(fp)
    add t3, t1, t2
    sw t3, -28(fp)
    lw a0, -28(fp)
    j __epilogo_metodo_ListaDoble_sumaRecursiva_15
    j __epilogo_metodo_ListaDoble_sumaRecursiva_15
__epilogo_metodo_ListaDoble_sumaRecursiva_15:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

metodo_ListaDoble_suma_16:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    lw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw a1, 0(t6)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 56(t0)
    jalr t0
    sw a0, -16(fp)
    lw a0, -16(fp)
    j __epilogo_metodo_ListaDoble_suma_16
    j __epilogo_metodo_ListaDoble_suma_16
__epilogo_metodo_ListaDoble_suma_16:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_ListaDoble_invertir_17:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    sw t1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
L69:
    lw t1, -16(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L70
    j L71
L70:
    lw t6, -16(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    sw t1, -20(fp)
    lw t6, -16(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    lw t6, -16(fp)
    addi t6, t6, 12
    sw t1, 0(t6)
    lw t1, -20(fp)
    lw t6, -16(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    lw t6, -16(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    sw t1, -16(fp)
    j L69
L71:
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    sw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    j __epilogo_metodo_ListaDoble_invertir_17
__epilogo_metodo_ListaDoble_invertir_17:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

metodo_ListaDoble_ordenar_18:
    addi sp, sp, -40
    sw ra, 36(sp)
    sw fp, 32(sp)
    addi fp, sp, 40
    sw a0, -12(fp)
L72:
    li t1, 0
    sw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    sw t1, -20(fp)
L74:
    lw t1, -20(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -24(fp)
    lw t6, -20(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -28(fp)
    lw t1, -24(fp)
    lw t2, -28(fp)
    and t3, t1, t2
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L75
    j L76
L75:
    lw t6, -20(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    lw t6, -20(fp)
    addi t6, t6, -1
    lw t2, 0(t6)
    slt t3, t2, t1
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L78
    j L77
L78:
    lw t6, -20(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    sw t1, -40(fp)
    lw t6, -20(fp)
    addi t6, t6, -1
    lw t1, 0(t6)
    lw t6, -20(fp)
    addi t6, t6, 4
    sw t1, 0(t6)
    lw t1, -40(fp)
    lw t6, -20(fp)
    addi t6, t6, -1
    sw t1, 0(t6)
    li t1, 1
    sw t1, -16(fp)
L77:
    lw t6, -20(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    sw t1, -20(fp)
    j L74
L76:
    lw t1, -16(fp)
    bnez t1, L72
    j L73
L73:
    j __epilogo_metodo_ListaDoble_ordenar_18
__epilogo_metodo_ListaDoble_ordenar_18:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 40
    mv fp, t0
    ret

metodo_ListaDoble_imprimirAdelante_19:
    addi sp, sp, -40
    sw ra, 36(sp)
    sw fp, 32(sp)
    addi fp, sp, 40
    sw a0, -12(fp)
    la t1, str_8
    sw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 4
    lw t1, 0(t6)
    sw t1, -20(fp)
L79:
    lw t1, -20(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L80
    j L81
L80:
    lw t1, -16(fp)
    lw t6, -20(fp)
    addi t6, t6, 4
    lw t2, 0(t6)
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw t2, 4(sp)
    mv a0, t2
    call z_itoa
    sw a0, 4(sp)
    lw t1, 0(sp)
    lw a1, 4(sp)
    mv a0, t1
    call z_concat
    addi sp, sp, 12
    sw a0, -28(fp)
    lw t1, -28(fp)
    sw t1, -16(fp)
    lw t6, -20(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L83
    j L82
L83:
    lw t1, -16(fp)
    la t2, str_9
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw t1, 0(sp)
    lw t2, 4(sp)
    mv a0, t1
    mv a1, t2
    call z_concat
    addi sp, sp, 12
    sw a0, -36(fp)
    lw t1, -36(fp)
    sw t1, -16(fp)
L82:
    lw t6, -20(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    sw t1, -20(fp)
    j L79
L81:
    lw t1, -16(fp)
    la t2, str_10
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw t1, 0(sp)
    lw t2, 4(sp)
    mv a0, t1
    mv a1, t2
    call z_concat
    addi sp, sp, 12
    sw a0, -40(fp)
    lw t1, -40(fp)
    sw t1, -16(fp)
    lw a0, -16(fp)
    call z_print_cadena
    call z_print_salto
    j __epilogo_metodo_ListaDoble_imprimirAdelante_19
__epilogo_metodo_ListaDoble_imprimirAdelante_19:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 40
    mv fp, t0
    ret

metodo_ListaDoble_imprimirAtras_20:
    addi sp, sp, -40
    sw ra, 36(sp)
    sw fp, 32(sp)
    addi fp, sp, 40
    sw a0, -12(fp)
    la t1, str_8
    sw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    sw t1, -20(fp)
L84:
    lw t1, -20(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L85
    j L86
L85:
    lw t1, -16(fp)
    lw t6, -20(fp)
    addi t6, t6, 4
    lw t2, 0(t6)
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw t2, 4(sp)
    mv a0, t2
    call z_itoa
    sw a0, 4(sp)
    lw t1, 0(sp)
    lw a1, 4(sp)
    mv a0, t1
    call z_concat
    addi sp, sp, 12
    sw a0, -28(fp)
    lw t1, -28(fp)
    sw t1, -16(fp)
    lw t6, -20(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -32(fp)
    lw t1, -32(fp)
    bnez t1, L88
    j L87
L88:
    lw t1, -16(fp)
    la t2, str_9
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw t1, 0(sp)
    lw t2, 4(sp)
    mv a0, t1
    mv a1, t2
    call z_concat
    addi sp, sp, 12
    sw a0, -36(fp)
    lw t1, -36(fp)
    sw t1, -16(fp)
L87:
    lw t6, -20(fp)
    addi t6, t6, 12
    lw t1, 0(t6)
    sw t1, -20(fp)
    j L84
L86:
    lw t1, -16(fp)
    la t2, str_10
    addi sp, sp, -12
    sw t1, 0(sp)
    sw t2, 4(sp)
    sw zero, 8(sp)
    lw t1, 0(sp)
    lw t2, 4(sp)
    mv a0, t1
    mv a1, t2
    call z_concat
    addi sp, sp, 12
    sw a0, -40(fp)
    lw t1, -40(fp)
    sw t1, -16(fp)
    lw a0, -16(fp)
    call z_print_cadena
    call z_print_salto
    j __epilogo_metodo_ListaDoble_imprimirAtras_20
__epilogo_metodo_ListaDoble_imprimirAtras_20:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 40
    mv fp, t0
    ret

constructor_ListaDoble_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    li t1, 0
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
    j __epilogo_constructor_ListaDoble_1
__epilogo_constructor_ListaDoble_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

constructor_ListaDoble_2:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    li t1, 0
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
    lw a0, -12(fp)
    lw a1, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -20(fp)
    j __epilogo_constructor_ListaDoble_2
__epilogo_constructor_ListaDoble_2:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

__entrada_main:
    addi sp, sp, -284
    sw ra, 280(sp)
    sw fp, 276(sp)
    addi fp, sp, 284
    li t1, 0
    sw t1, -12(fp)
    li t1, 0
    sw t1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
    li t1, 0
    sw t1, -24(fp)
    li t1, 0
    sw t1, -28(fp)
    li a0, 16
    call z_alloc
    sw a0, -32(fp)
    lw t6, -32(fp)
    la t1, __vtable_ListaDoble
    sw t1, 0(t6)
    lw a0, -32(fp)
    call constructor_ListaDoble_1
    sw a0, -32(fp)
    lw t1, -32(fp)
    sw t1, -36(fp)
    li a0, 16
    call z_alloc
    sw a0, -40(fp)
    lw t6, -40(fp)
    la t1, __vtable_ListaDoble
    sw t1, 0(t6)
    lw a0, -40(fp)
    li a1, 7
    call constructor_ListaDoble_2
    sw a0, -40(fp)
    lw t1, -40(fp)
    sw t1, -44(fp)
    li t1, 0
    addi t6, fp, -60
    sw t1, 0(t6)
    li t1, 0
    addi t6, fp, -60
    addi t6, t6, 4
    sw t1, 0(t6)
    li t1, 0
    addi t6, fp, -60
    addi t6, t6, 8
    sw t1, 0(t6)
    li t1, 0
    addi t6, fp, -60
    addi t6, t6, 12
    sw t1, 0(t6)
    la a0, str_11
    call z_print_cadena
    call z_print_salto
    li t1, 1
    sw t1, -64(fp)
L89:
    lw t1, -64(fp)
    li t2, 6
    slt t3, t1, t2
    sw t3, -68(fp)
    lw t1, -68(fp)
    bnez t1, L90
    j L92
L90:
    lw t1, -64(fp)
    li t2, 10
    mul t3, t1, t2
    sw t3, -72(fp)
    lw a0, -36(fp)
    lw a1, -72(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -76(fp)
L91:
    lw t1, -64(fp)
    addi t3, t1, 1
    sw t3, -64(fp)
    j L89
L92:
    lw a0, -44(fp)
    li a1, 8
    li a2, 9
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -80(fp)
    la a0, str_12
    call z_print_cadena
    call z_print_salto
    lw a0, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 72(t0)
    jalr t0
    sw a0, -84(fp)
    la a0, str_13
    call z_print_cadena
    call z_print_salto
    lw a0, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 76(t0)
    jalr t0
    sw a0, -88(fp)
    la a0, str_14
    call z_print_cadena
    call z_print_salto
    lw a0, -44(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 72(t0)
    jalr t0
    sw a0, -92(fp)
L93:
    la a0, str_15
    call z_print_cadena
    call z_print_salto
    la a0, str_16
    call z_print_cadena
    call z_print_salto
    la a0, str_17
    call z_print_cadena
    call z_print_salto
    la a0, str_18
    call z_print_cadena
    call z_print_salto
    la a0, str_19
    call z_print_cadena
    call z_print_salto
    la a0, str_20
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -12(fp)
    lw t1, -12(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -96(fp)
    lw t1, -96(fp)
    bnez t1, L97
    j L96
L97:
    la a0, str_21
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -16(fp)
    lw a0, -36(fp)
    lw a1, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 4(t0)
    jalr t0
    sw a0, -100(fp)
    addi a0, fp, -60
    li a1, 1
    call fun_registrar
    sw a0, -104(fp)
    j L95
L96:
    lw t1, -12(fp)
    li t2, 2
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -108(fp)
    lw t1, -108(fp)
    bnez t1, L99
    j L98
L99:
    la a0, str_21
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -16(fp)
    lw a0, -36(fp)
    lw a1, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -112(fp)
    addi a0, fp, -60
    li a1, 1
    call fun_registrar
    sw a0, -116(fp)
    j L95
L98:
    lw t1, -12(fp)
    li t2, 3
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -120(fp)
    lw t1, -120(fp)
    bnez t1, L101
    j L100
L101:
    la a0, str_22
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -20(fp)
    la a0, str_21
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -16(fp)
    lw a0, -36(fp)
    lw a1, -20(fp)
    lw a2, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -124(fp)
    lw t1, -124(fp)
    sw t1, -28(fp)
    lw t1, -28(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -128(fp)
    lw t1, -128(fp)
    bnez t1, L104
    j L103
L104:
    la a0, str_23
    call z_print_cadena
    lw a0, -20(fp)
    call z_print_entero
    call z_print_salto
    addi a0, fp, -60
    li a1, 1
    call fun_registrar
    sw a0, -132(fp)
    j L102
L103:
    la a0, str_24
    call z_print_cadena
    call z_print_salto
L102:
    j L95
L100:
    lw t1, -12(fp)
    li t2, 4
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -136(fp)
    lw t1, -136(fp)
    bnez t1, L106
    j L105
L106:
    lw a0, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -140(fp)
    lw t1, -140(fp)
    bnez t1, L109
    j L108
L109:
    la a0, str_25
    call z_print_cadena
    call z_print_salto
    addi a0, fp, -60
    li a1, 2
    call fun_registrar
    sw a0, -144(fp)
    j L107
L108:
    la a0, str_26
    call z_print_cadena
    call z_print_salto
L107:
    j L95
L105:
    lw t1, -12(fp)
    li t2, 5
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -148(fp)
    lw t1, -148(fp)
    bnez t1, L111
    j L110
L111:
    lw a0, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 24(t0)
    jalr t0
    sw a0, -152(fp)
    lw t1, -152(fp)
    bnez t1, L114
    j L113
L114:
    la a0, str_27
    call z_print_cadena
    call z_print_salto
    addi a0, fp, -60
    li a1, 2
    call fun_registrar
    sw a0, -156(fp)
    j L112
L113:
    la a0, str_26
    call z_print_cadena
    call z_print_salto
L112:
    j L95
L110:
    lw t1, -12(fp)
    li t2, 6
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -160(fp)
    lw t1, -160(fp)
    bnez t1, L116
    j L115
L116:
    la a0, str_22
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -20(fp)
    lw a0, -36(fp)
    lw a1, -20(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -164(fp)
    lw t1, -164(fp)
    sw t1, -28(fp)
    lw t1, -28(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -168(fp)
    lw t1, -168(fp)
    bnez t1, L119
    j L118
L119:
    la a0, str_28
    call z_print_cadena
    lw a0, -20(fp)
    call z_print_entero
    call z_print_salto
    addi a0, fp, -60
    li a1, 2
    call fun_registrar
    sw a0, -172(fp)
    j L117
L118:
    la a0, str_24
    call z_print_cadena
    call z_print_salto
L117:
    j L95
L115:
    lw t1, -12(fp)
    li t2, 7
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -176(fp)
    lw t1, -176(fp)
    bnez t1, L121
    j L120
L121:
    la a0, str_29
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -16(fp)
    lw a0, -36(fp)
    lw a1, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 32(t0)
    jalr t0
    sw a0, -180(fp)
    lw t1, -180(fp)
    sw t1, -28(fp)
    lw t1, -28(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -184(fp)
    lw t1, -184(fp)
    bnez t1, L124
    j L123
L124:
    la a0, str_30
    call z_print_cadena
    lw a0, -16(fp)
    call z_print_entero
    la a0, str_31
    call z_print_cadena
    call z_print_salto
    addi a0, fp, -60
    li a1, 2
    call fun_registrar
    sw a0, -188(fp)
    j L122
L123:
    la a0, str_32
    call z_print_cadena
    call z_print_salto
L122:
    j L95
L120:
    lw t1, -12(fp)
    li t2, 8
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -192(fp)
    lw t1, -192(fp)
    bnez t1, L126
    j L125
L126:
    la a0, str_33
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -16(fp)
    lw a0, -36(fp)
    lw a1, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 48(t0)
    jalr t0
    sw a0, -196(fp)
    lw t1, -196(fp)
    sw t1, -24(fp)
    li t1, 1
    neg t3, t1
    sw t3, -200(fp)
    lw t1, -24(fp)
    lw t2, -200(fp)
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -204(fp)
    lw t1, -204(fp)
    bnez t1, L129
    j L128
L129:
    la a0, str_34
    call z_print_cadena
    call z_print_salto
    j L127
L128:
    la a0, str_35
    call z_print_cadena
    lw a0, -24(fp)
    call z_print_entero
    call z_print_salto
    addi a0, fp, -60
    li a1, 3
    call fun_registrar
    sw a0, -208(fp)
L127:
    j L95
L125:
    lw t1, -12(fp)
    li t2, 9
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -212(fp)
    lw t1, -212(fp)
    bnez t1, L131
    j L130
L131:
    lw a0, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 64(t0)
    jalr t0
    sw a0, -216(fp)
    la a0, str_36
    call z_print_cadena
    call z_print_salto
    lw a0, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 72(t0)
    jalr t0
    sw a0, -220(fp)
    j L95
L130:
    lw t1, -12(fp)
    li t2, 10
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -224(fp)
    lw t1, -224(fp)
    bnez t1, L133
    j L132
L133:
    lw a0, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 68(t0)
    jalr t0
    sw a0, -228(fp)
    la a0, str_37
    call z_print_cadena
    call z_print_salto
    lw a0, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 72(t0)
    jalr t0
    sw a0, -232(fp)
    j L95
L132:
    lw t1, -12(fp)
    li t2, 11
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -236(fp)
    lw t1, -236(fp)
    bnez t1, L135
    j L134
L135:
    la a0, str_38
    call z_print_cadena
    call z_print_salto
    lw a0, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 72(t0)
    jalr t0
    sw a0, -240(fp)
    la a0, str_39
    call z_print_cadena
    call z_print_salto
    lw a0, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 76(t0)
    jalr t0
    sw a0, -244(fp)
    j L95
L134:
    lw t1, -12(fp)
    li t2, 12
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -248(fp)
    lw t1, -248(fp)
    bnez t1, L137
    j L136
L137:
    lw a0, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -252(fp)
    lw t1, -252(fp)
    bnez t1, L140
    j L139
L140:
    la a0, str_40
    call z_print_cadena
    call z_print_salto
    j L138
L139:
    la a0, str_41
    call z_print_cadena
    lw t6, -36(fp)
    addi t6, t6, 12
    lw a0, 0(t6)
    call z_print_entero
    la a0, str_42
    call z_print_cadena
    lw t6, -36(fp)
    addi t6, t6, 12
    lw a0, 0(t6)
    call fun_paridad
    sw a0, -256(fp)
    lw a0, -256(fp)
    call z_print_cadena
    la a0, str_43
    call z_print_cadena
    call z_print_salto
    la a0, str_44
    call z_print_cadena
    lw a0, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 60(t0)
    jalr t0
    sw a0, -260(fp)
    lw a0, -260(fp)
    call z_print_entero
    call z_print_salto
    la a0, str_45
    call z_print_cadena
    lw a0, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 52(t0)
    jalr t0
    sw a0, -264(fp)
    lw a0, -264(fp)
    call z_print_entero
    call z_print_salto
    la a0, str_46
    call z_print_cadena
    la a0, str_47
    call z_print_cadena
    call z_print_salto
L138:
    j L95
L136:
    lw t1, -12(fp)
    li t2, 13
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -268(fp)
    lw t1, -268(fp)
    bnez t1, L142
    j L141
L142:
    lw a0, -36(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 36(t0)
    jalr t0
    sw a0, -272(fp)
    la a0, str_48
    call z_print_cadena
    call z_print_salto
    j L95
L141:
    lw t1, -12(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -276(fp)
    lw t1, -276(fp)
    bnez t1, L144
    j L143
L144:
    la a0, str_49
    call z_print_cadena
    call z_print_salto
    j L95
L143:
    la a0, str_50
    call z_print_cadena
    call z_print_salto
L95:
    lw t1, -12(fp)
    li t2, 0
    sub t3, t1, t2
    snez t3, t3
    sw t3, -280(fp)
    lw t1, -280(fp)
    bnez t1, L93
    j L94
L94:
    addi a0, fp, -60
    call fun_imprimirRegistro
    sw a0, -284(fp)
__epilogo_main:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 284
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
