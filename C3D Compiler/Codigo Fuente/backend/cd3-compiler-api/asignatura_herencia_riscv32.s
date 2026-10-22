.data
.align 2
str_0: .asciiz "Nombre: "
.align 2
str_1: .asciiz "Edad: "
.align 2
str_2: .asciiz "Sin nombre"
.align 2
str_3: .asciiz "[Estudiante]"
.align 2
str_4: .asciiz "Promedio: "
.align 2
str_5: .asciiz "[Docente]"
.align 2
str_6: .asciiz "Especialidad: "
.align 2
str_7: .asciiz "Sin docente"
.align 2
str_8: .asciiz "Sin especialidad"
.align 2
str_9: .asciiz "El curso ya esta lleno, no se pueden agregar mas estudiantes"
.align 2
dbl_0: .word 0
.align 2
str_10: .asciiz "--- Estudiante "
.align 2
str_11: .asciiz " ---"
.align 2
str_12: .asciiz "Sin asignar"
.align 2
str_13: .asciiz "Matematicas"
.align 2
str_14: .asciiz "Carlos Perez"
.align 2
str_15: .asciiz "Algebra"
.align 2
str_16: .asciiz ""
.align 2
str_17: .asciiz "Bienvenido al sistema de gestion de la asignatura "
.align 2
str_18: .asciiz "-------------------------------------"
.align 2
str_19: .asciiz "1. Agregar estudiante"
.align 2
str_20: .asciiz "2. Ver promedio de un estudiante"
.align 2
str_21: .asciiz "3. Ver nota mas alta y mas baja de un estudiante"
.align 2
str_22: .asciiz "4. Ver promedio general del curso"
.align 2
str_23: .asciiz "5. Listar estudiantes"
.align 2
str_24: .asciiz "6. Ver resumen de un estudiante (usando Funciones.y)"
.align 2
str_25: .asciiz "7. Ver docente titular y un estudiante (polimorfismo)"
.align 2
str_26: .asciiz "8. Salir"
.align 2
str_27: .asciiz "Ingresa una opcion"
.align 2
str_28: .asciiz "Ingresa el nombre del estudiante"
.align 2
str_29: .asciiz "Ingresa la edad del estudiante"
.align 2
str_30: .asciiz "Ingresa la nota numero"
.align 2
str_31: .asciiz "Estudiante agregado con exito"
.align 2
str_32: .asciiz "Ingresa el indice del estudiante (0 a 4)"
.align 2
str_33: .asciiz "El promedio del estudiante es: "
.align 2
str_34: .asciiz "La nota mas alta es: "
.align 2
str_35: .asciiz "La nota mas baja es: "
.align 2
str_36: .asciiz "El promedio general del curso es: "
.align 2
str_37: .asciiz "Promedio (Funciones.y): "
.align 2
str_38: .asciiz "Nota maxima (Funciones.y): "
.align 2
str_39: .asciiz "Nota minima (Funciones.y): "
.align 2
str_40: .asciiz "Hasta luego cadete"
.align 2
str_41: .asciiz "Opcion no valida, intenta de nuevo"
.align 2
__vtable_Asignatura:
    .word 69304
    .word 69364
    .word 69424
    .word 69484
    .word 69544
    .word 69608
    .word 69780
    .word 69856
    .word 70120
    .word 70424
    .word 70652
.align 2
__vtable_Persona:
    .word 66652
    .word 66712
    .word 66776
    .word 66836
    .word 66932
.align 2
__vtable_Estudiante:
    .word 66652
    .word 66712
    .word 66776
    .word 66836
    .word 68284
    .word 67228
    .word 67372
    .word 67432
    .word 67640
    .word 67908
.align 2
__vtable_Docente:
    .word 66652
    .word 66712
    .word 66776
    .word 66836
    .word 68872
    .word 68748
    .word 68808
.text
.globl main
main:
    call __entrada_main
    call z_exit

fun_calcularPromedioArreglo:
    addi sp, sp, -40
    sw ra, 36(sp)
    sw fp, 32(sp)
    addi fp, sp, 40
    sw a0, -12(fp)
    li t1, 0
    sw t1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
L1:
    lw t1, -20(fp)
    li t2, 5
    slt t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L2
    j L4
L2:
    lw t2, -20(fp)
    lw t1, -12(fp)
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
L3:
    lw t1, -20(fp)
    addi t3, t1, 1
    sw t3, -20(fp)
    sw t3, -20(fp)
    j L1
L4:
    lw t1, -16(fp)
    li t2, 5
    div t3, t1, t2
    sw t3, -36(fp)
    lw t1, -36(fp)
    sw t1, -40(fp)
    lw a0, -40(fp)
    j __epilogo_fun_calcularPromedioArreglo
    j __epilogo_fun_calcularPromedioArreglo
__epilogo_fun_calcularPromedioArreglo:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 40
    mv fp, t0
    ret

fun_encontrarMaxima:
    addi sp, sp, -40
    sw ra, 36(sp)
    sw fp, 32(sp)
    addi fp, sp, 40
    sw a0, -12(fp)
    li t2, 0
    lw t1, -12(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -16(fp)
    lw t1, -16(fp)
    sw t1, -20(fp)
    li t1, 1
    sw t1, -24(fp)
L5:
    lw t1, -24(fp)
    li t2, 5
    slt t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L6
    j L8
L6:
    lw t2, -24(fp)
    lw t1, -12(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -32(fp)
    lw t1, -32(fp)
    lw t2, -20(fp)
    slt t3, t2, t1
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L10
    j L9
L10:
    lw t2, -24(fp)
    lw t1, -12(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -40(fp)
    lw t1, -40(fp)
    sw t1, -20(fp)
L9:
L7:
    lw t1, -24(fp)
    addi t3, t1, 1
    sw t3, -24(fp)
    sw t3, -24(fp)
    j L5
L8:
    lw a0, -20(fp)
    j __epilogo_fun_encontrarMaxima
    j __epilogo_fun_encontrarMaxima
__epilogo_fun_encontrarMaxima:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 40
    mv fp, t0
    ret

fun_encontrarMinima:
    addi sp, sp, -40
    sw ra, 36(sp)
    sw fp, 32(sp)
    addi fp, sp, 40
    sw a0, -12(fp)
    li t2, 0
    lw t1, -12(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -16(fp)
    lw t1, -16(fp)
    sw t1, -20(fp)
    li t1, 1
    sw t1, -24(fp)
L11:
    lw t1, -24(fp)
    li t2, 5
    slt t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L12
    j L14
L12:
    lw t2, -24(fp)
    lw t1, -12(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -32(fp)
    lw t1, -32(fp)
    lw t2, -20(fp)
    slt t3, t1, t2
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L16
    j L15
L16:
    lw t2, -24(fp)
    lw t1, -12(fp)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -40(fp)
    lw t1, -40(fp)
    sw t1, -20(fp)
L15:
L13:
    lw t1, -24(fp)
    addi t3, t1, 1
    sw t3, -24(fp)
    sw t3, -24(fp)
    j L11
L14:
    lw a0, -20(fp)
    j __epilogo_fun_encontrarMinima
    j __epilogo_fun_encontrarMinima
__epilogo_fun_encontrarMinima:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 40
    mv fp, t0
    ret

fun_generarResumen:
    addi sp, sp, -40
    sw ra, 36(sp)
    sw fp, 32(sp)
    addi fp, sp, 40
    sw a0, -12(fp)
    lw a0, -12(fp)
    call fun_encontrarMaxima
    sw a0, -16(fp)
    lw t1, -16(fp)
    addi t6, fp, -32
    sw t1, 0(t6)
    lw a0, -12(fp)
    call fun_encontrarMinima
    sw a0, -36(fp)
    lw t1, -36(fp)
    addi t6, fp, -32
    addi t6, t6, 4
    sw t1, 0(t6)
    lw a0, -12(fp)
    call fun_calcularPromedioArreglo
    sw a0, -40(fp)
    lw t1, -40(fp)
    addi t6, fp, -32
    addi t6, t6, 8
    sw t1, 0(t6)
    lw a0, -32(fp)
    j __epilogo_fun_generarResumen
    j __epilogo_fun_generarResumen
__epilogo_fun_generarResumen:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 40
    mv fp, t0
    ret

metodo_Persona_getNombre_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw a0, 0(t6)
    j __epilogo_metodo_Persona_getNombre_1
    j __epilogo_metodo_Persona_getNombre_1
__epilogo_metodo_Persona_getNombre_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Persona_setNombre_2:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    j __epilogo_metodo_Persona_setNombre_2
__epilogo_metodo_Persona_setNombre_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Persona_getEdad_3:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw a0, 0(t6)
    j __epilogo_metodo_Persona_getEdad_3
    j __epilogo_metodo_Persona_getEdad_3
__epilogo_metodo_Persona_getEdad_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Persona_setEdad_4:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t1, -16(fp)
    li t2, 0
    slt t3, t1, t2
    xori t3, t3, 1
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L18
    j L17
L18:
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
L17:
    j __epilogo_metodo_Persona_setEdad_4
__epilogo_metodo_Persona_setEdad_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Persona_mostrarDatos_5:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    la t1, str_0
    lw t1, 0(t1)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t2, 0(t6)
    add t3, t1, t2
    sw t3, -16(fp)
    lw a0, -16(fp)
    call z_print_cadena
    call z_print_salto
    la t1, str_1
    lw t1, 0(t1)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw t2, 0(t6)
    add t3, t1, t2
    sw t3, -20(fp)
    lw a0, -20(fp)
    call z_print_cadena
    call z_print_salto
    j __epilogo_metodo_Persona_mostrarDatos_5
__epilogo_metodo_Persona_mostrarDatos_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

constructor_Persona_1:
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
    addi t6, t6, 16
    sw t1, 0(t6)
    j __epilogo_constructor_Persona_1
__epilogo_constructor_Persona_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

constructor_Persona_2:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    la t1, str_2
    lw t1, 0(t1)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
    j __epilogo_constructor_Persona_2
__epilogo_constructor_Persona_2:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Estudiante_asignarNota_1:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw a0, -12(fp)
    lw a1, -16(fp)
    call metodo_Estudiante_posicionValida_6
    sw a0, -24(fp)
    lw t1, -24(fp)
    bnez t1, L20
    j L19
L20:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t1, -20(fp)
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t6, 0(t6)
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
L19:
    j __epilogo_metodo_Estudiante_asignarNota_1
__epilogo_metodo_Estudiante_asignarNota_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

metodo_Estudiante_obtenerNotas_2:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw a0, 0(t6)
    j __epilogo_metodo_Estudiante_obtenerNotas_2
    j __epilogo_metodo_Estudiante_obtenerNotas_2
__epilogo_metodo_Estudiante_obtenerNotas_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Estudiante_calcularPromedio_3:
    addi sp, sp, -40
    sw ra, 36(sp)
    sw fp, 32(sp)
    addi fp, sp, 40
    sw a0, -12(fp)
    li t1, 0
    sw t1, -16(fp)
    li t1, 0
    sw t1, -20(fp)
    li t1, 0
    sw t1, -20(fp)
L21:
    lw t1, -20(fp)
    li t2, 5
    slt t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L22
    j L24
L22:
    lw t2, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
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
L23:
    lw t1, -20(fp)
    addi t3, t1, 1
    sw t3, -20(fp)
    sw t3, -20(fp)
    j L21
L24:
    lw t1, -16(fp)
    li t2, 5
    div t3, t1, t2
    sw t3, -36(fp)
    lw t1, -36(fp)
    sw t1, -40(fp)
    lw a0, -40(fp)
    j __epilogo_metodo_Estudiante_calcularPromedio_3
    j __epilogo_metodo_Estudiante_calcularPromedio_3
__epilogo_metodo_Estudiante_calcularPromedio_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 40
    mv fp, t0
    ret

metodo_Estudiante_notaMasAlta_4:
    addi sp, sp, -40
    sw ra, 36(sp)
    sw fp, 32(sp)
    addi fp, sp, 40
    sw a0, -12(fp)
    li t2, 0
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -16(fp)
    lw t1, -16(fp)
    sw t1, -20(fp)
    li t1, 0
    sw t1, -24(fp)
    li t1, 1
    sw t1, -24(fp)
L25:
    lw t1, -24(fp)
    li t2, 5
    slt t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L26
    j L28
L26:
    lw t2, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -32(fp)
    lw t1, -32(fp)
    lw t2, -20(fp)
    slt t3, t2, t1
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L30
    j L29
L30:
    lw t2, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -40(fp)
    lw t1, -40(fp)
    sw t1, -20(fp)
L29:
L27:
    lw t1, -24(fp)
    addi t3, t1, 1
    sw t3, -24(fp)
    sw t3, -24(fp)
    j L25
L28:
    lw a0, -20(fp)
    j __epilogo_metodo_Estudiante_notaMasAlta_4
    j __epilogo_metodo_Estudiante_notaMasAlta_4
__epilogo_metodo_Estudiante_notaMasAlta_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 40
    mv fp, t0
    ret

metodo_Estudiante_notaMasBaja_5:
    addi sp, sp, -40
    sw ra, 36(sp)
    sw fp, 32(sp)
    addi fp, sp, 40
    sw a0, -12(fp)
    li t2, 0
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -16(fp)
    lw t1, -16(fp)
    sw t1, -20(fp)
    li t1, 0
    sw t1, -24(fp)
    li t1, 1
    sw t1, -24(fp)
L31:
    lw t1, -24(fp)
    li t2, 5
    slt t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L32
    j L34
L32:
    lw t2, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -32(fp)
    lw t1, -32(fp)
    lw t2, -20(fp)
    slt t3, t1, t2
    sw t3, -36(fp)
    lw t1, -36(fp)
    bnez t1, L36
    j L35
L36:
    lw t2, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -40(fp)
    lw t1, -40(fp)
    sw t1, -20(fp)
L35:
L33:
    lw t1, -24(fp)
    addi t3, t1, 1
    sw t3, -24(fp)
    sw t3, -24(fp)
    j L31
L34:
    lw a0, -20(fp)
    j __epilogo_metodo_Estudiante_notaMasBaja_5
    j __epilogo_metodo_Estudiante_notaMasBaja_5
__epilogo_metodo_Estudiante_notaMasBaja_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 40
    mv fp, t0
    ret

metodo_Estudiante_posicionValida_6:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t1, -16(fp)
    li t2, 0
    slt t3, t1, t2
    xori t3, t3, 1
    sw t3, -20(fp)
    lw t1, -16(fp)
    li t2, 5
    slt t3, t1, t2
    sw t3, -24(fp)
    lw t1, -20(fp)
    lw t2, -24(fp)
    and t3, t1, t2
    sw t3, -28(fp)
    lw a0, -28(fp)
    j __epilogo_metodo_Estudiante_posicionValida_6
    j __epilogo_metodo_Estudiante_posicionValida_6
__epilogo_metodo_Estudiante_posicionValida_6:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

metodo_Estudiante_mostrarDatos_7:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    la a0, str_3
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    la t1, str_0
    lw t1, 0(t1)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t2, 0(t6)
    add t3, t1, t2
    sw t3, -16(fp)
    lw a0, -16(fp)
    call z_print_cadena
    call z_print_salto
    lw a0, -12(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -20(fp)
    la t1, str_1
    lw t1, 0(t1)
    lw t2, -20(fp)
    add t3, t1, t2
    sw t3, -24(fp)
    lw a0, -24(fp)
    call z_print_cadena
    call z_print_salto
    lw a0, -12(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -28(fp)
    la t1, str_4
    lw t1, 0(t1)
    lw t2, -28(fp)
    add t3, t1, t2
    sw t3, -32(fp)
    lw a0, -32(fp)
    call z_print_cadena
    call z_print_salto
    j __epilogo_metodo_Estudiante_mostrarDatos_7
__epilogo_metodo_Estudiante_mostrarDatos_7:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

constructor_Estudiante_1:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    lw a0, -12(fp)
    lw a1, -20(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -24(fp)
    li a1, 20
    call z_alloc
    sw a0, -28(fp)
    lw t1, -28(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j __epilogo_constructor_Estudiante_1
__epilogo_constructor_Estudiante_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

constructor_Estudiante_2:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    la t1, str_2
    lw t1, 0(t1)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    lw a0, -12(fp)
    li a1, 0
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -16(fp)
    li a1, 20
    call z_alloc
    sw a0, -20(fp)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j __epilogo_constructor_Estudiante_2
__epilogo_constructor_Estudiante_2:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

metodo_Docente_getEspecialidad_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw a0, 0(t6)
    j __epilogo_metodo_Docente_getEspecialidad_1
    j __epilogo_metodo_Docente_getEspecialidad_1
__epilogo_metodo_Docente_getEspecialidad_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Docente_setEspecialidad_2:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j __epilogo_metodo_Docente_setEspecialidad_2
__epilogo_metodo_Docente_setEspecialidad_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Docente_mostrarDatos_3:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    la a0, str_5
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    la t1, str_0
    lw t1, 0(t1)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t2, 0(t6)
    add t3, t1, t2
    sw t3, -16(fp)
    lw a0, -16(fp)
    call z_print_cadena
    call z_print_salto
    lw a0, -12(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 8(t0)
    jalr t0
    sw a0, -20(fp)
    la t1, str_1
    lw t1, 0(t1)
    lw t2, -20(fp)
    add t3, t1, t2
    sw t3, -24(fp)
    lw a0, -24(fp)
    call z_print_cadena
    call z_print_salto
    la t1, str_6
    lw t1, 0(t1)
    lw t6, -12(fp)
    addi t6, t6, 24
    lw t2, 0(t6)
    add t3, t1, t2
    sw t3, -28(fp)
    lw a0, -28(fp)
    call z_print_cadena
    call z_print_salto
    j __epilogo_metodo_Docente_mostrarDatos_3
__epilogo_metodo_Docente_mostrarDatos_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

constructor_Docente_1:
    addi sp, sp, -28
    sw ra, 24(sp)
    sw fp, 20(sp)
    addi fp, sp, 28
    sw a0, -12(fp)
    sw a1, -16(fp)
    sw a2, -20(fp)
    sw a3, -24(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    lw a0, -12(fp)
    lw a1, -20(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -28(fp)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j __epilogo_constructor_Docente_1
__epilogo_constructor_Docente_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 28
    mv fp, t0
    ret

constructor_Docente_2:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    la t1, str_7
    lw t1, 0(t1)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    lw a0, -12(fp)
    li a1, 0
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -16(fp)
    la t1, str_8
    lw t1, 0(t1)
    lw t6, -12(fp)
    addi t6, t6, 24
    sw t1, 0(t6)
    j __epilogo_constructor_Docente_2
__epilogo_constructor_Docente_2:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Asignatura_getNombre_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    lw a0, 0(t6)
    j __epilogo_metodo_Asignatura_getNombre_1
    j __epilogo_metodo_Asignatura_getNombre_1
__epilogo_metodo_Asignatura_getNombre_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Asignatura_getTotalEstudiantes_2:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    lw a0, 0(t6)
    j __epilogo_metodo_Asignatura_getTotalEstudiantes_2
    j __epilogo_metodo_Asignatura_getTotalEstudiantes_2
__epilogo_metodo_Asignatura_getTotalEstudiantes_2:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Asignatura_getHayAsignados_3:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 24
    lb a0, 0(t6)
    j __epilogo_metodo_Asignatura_getHayAsignados_3
    j __epilogo_metodo_Asignatura_getHayAsignados_3
__epilogo_metodo_Asignatura_getHayAsignados_3:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Asignatura_getTitular_4:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 32
    lw a0, 0(t6)
    j __epilogo_metodo_Asignatura_getTitular_4
    j __epilogo_metodo_Asignatura_getTitular_4
__epilogo_metodo_Asignatura_getTitular_4:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_Asignatura_setTitular_5:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 32
    sw t1, 0(t6)
    j __epilogo_metodo_Asignatura_setTitular_5
__epilogo_metodo_Asignatura_setTitular_5:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Asignatura_obtenerEstudiante_6:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t1, -16(fp)
    li t2, 0
    slt t3, t1, t2
    xori t3, t3, 1
    sw t3, -20(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -24(fp)
    lw t1, -20(fp)
    lw t2, -24(fp)
    and t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L38
    j L37
L38:
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -32(fp)
    lw a0, -32(fp)
    j __epilogo_metodo_Asignatura_obtenerEstudiante_6
L37:
    li a0, 0
    j __epilogo_metodo_Asignatura_obtenerEstudiante_6
    j __epilogo_metodo_Asignatura_obtenerEstudiante_6
__epilogo_metodo_Asignatura_obtenerEstudiante_6:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
    mv fp, t0
    ret

metodo_Asignatura_hayCupo_7:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    lw t1, 0(t6)
    li t2, 5
    slt t3, t1, t2
    sw t3, -16(fp)
    lw a0, -16(fp)
    j __epilogo_metodo_Asignatura_hayCupo_7
    j __epilogo_metodo_Asignatura_hayCupo_7
__epilogo_metodo_Asignatura_hayCupo_7:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

metodo_Asignatura_agregarEstudiante_8:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw a0, -12(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 24(t0)
    jalr t0
    sw a0, -20(fp)
    lw t1, -20(fp)
    seqz t3, t1
    sw t3, -24(fp)
    lw t1, -24(fp)
    bnez t1, L41
    j L40
L41:
    la a0, str_9
    lw a0, 0(a0)
    call z_print_cadena
    j L39
L40:
    lw t6, -12(fp)
    addi t6, t6, 28
    lw t2, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    lw t2, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t6, 0(t6)
    li t4, 4
    mul t2, t2, t4
    add t6, t6, t2
    sw t1, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 28
    lw t1, 0(t6)
    addi t3, t1, 1
    lw t6, -12(fp)
    addi t6, t6, 28
    sw t3, 0(t6)
    lw t6, -12(fp)
    addi t6, t6, 28
    sw t3, 0(t6)
    li t1, 1
    lw t6, -12(fp)
    addi t6, t6, 24
    sb t1, 0(t6)
L39:
    lw t6, -12(fp)
    addi t6, t6, 24
    lb a0, 0(t6)
    j __epilogo_metodo_Asignatura_agregarEstudiante_8
    j __epilogo_metodo_Asignatura_agregarEstudiante_8
__epilogo_metodo_Asignatura_agregarEstudiante_8:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

metodo_Asignatura_promedioGeneral_9:
    addi sp, sp, -48
    sw ra, 44(sp)
    sw fp, 40(sp)
    addi fp, sp, 48
    sw a0, -12(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    lw t1, 0(t6)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -16(fp)
    lw t1, -16(fp)
    bnez t1, L43
    j L42
L43:
    la a0, dbl_0
    lw a0, 0(a0)
    j __epilogo_metodo_Asignatura_promedioGeneral_9
L42:
    la t1, dbl_0
    lw t1, 0(t1)
    sw t1, -20(fp)
    li t1, 0
    sw t1, -24(fp)
    li t1, 0
    sw t1, -24(fp)
L44:
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -28(fp)
    lw t1, -28(fp)
    bnez t1, L45
    j L47
L45:
    lw t2, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -32(fp)
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -36(fp)
    lw t1, -20(fp)
    lw t2, -36(fp)
    add t3, t1, t2
    sw t3, -40(fp)
    lw t1, -40(fp)
    sw t1, -20(fp)
L46:
    lw t1, -24(fp)
    addi t3, t1, 1
    sw t3, -24(fp)
    sw t3, -24(fp)
    j L44
L47:
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    lw t2, 0(t6)
    div t3, t1, t2
    sw t3, -44(fp)
    lw t1, -44(fp)
    sw t1, -48(fp)
    lw a0, -48(fp)
    j __epilogo_metodo_Asignatura_promedioGeneral_9
    j __epilogo_metodo_Asignatura_promedioGeneral_9
__epilogo_metodo_Asignatura_promedioGeneral_9:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 48
    mv fp, t0
    ret

metodo_Asignatura_mostrarEstudiantes_10:
    addi sp, sp, -36
    sw ra, 32(sp)
    sw fp, 28(sp)
    addi fp, sp, 36
    sw a0, -12(fp)
    li t1, 0
    sw t1, -16(fp)
    li t1, 0
    sw t1, -16(fp)
L48:
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 28
    lw t2, 0(t6)
    slt t3, t1, t2
    sw t3, -20(fp)
    lw t1, -20(fp)
    bnez t1, L49
    j L51
L49:
    la t1, str_10
    lw t1, 0(t1)
    lw t2, -16(fp)
    add t3, t1, t2
    sw t3, -24(fp)
    lw t1, -24(fp)
    la t2, str_11
    lw t2, 0(t2)
    add t3, t1, t2
    sw t3, -28(fp)
    lw a0, -28(fp)
    call z_print_cadena
    call z_print_salto
    lw t2, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    lw t1, 0(t6)
    li t3, 4
    mul t2, t2, t3
    add t1, t1, t2
    lw t3, 0(t1)
    sw t3, -32(fp)
    lw a0, -32(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -36(fp)
L50:
    lw t1, -16(fp)
    addi t3, t1, 1
    sw t3, -16(fp)
    sw t3, -16(fp)
    j L48
L51:
    j __epilogo_metodo_Asignatura_mostrarEstudiantes_10
__epilogo_metodo_Asignatura_mostrarEstudiantes_10:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 36
    mv fp, t0
    ret

metodo_Asignatura_presentar_11:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw a0, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -20(fp)
    j __epilogo_metodo_Asignatura_presentar_11
__epilogo_metodo_Asignatura_presentar_11:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

constructor_Asignatura_1:
    addi sp, sp, -24
    sw ra, 20(sp)
    sw fp, 16(sp)
    addi fp, sp, 24
    sw a0, -12(fp)
    sw a1, -16(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
    li a1, 20
    call z_alloc
    sw a0, -20(fp)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 24
    sb t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 28
    sw t1, 0(t6)
    li a1, 32
    call z_alloc
    sw a0, -24(fp)
    lw t6, -24(fp)
    la t1, __vtable_Docente
    sw t1, 0(t6)
    lw a0, -24(fp)
    call constructor_Docente_2
    sw a0, -24(fp)
    lw t1, -24(fp)
    lw t6, -12(fp)
    addi t6, t6, 32
    sw t1, 0(t6)
    j __epilogo_constructor_Asignatura_1
__epilogo_constructor_Asignatura_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 24
    mv fp, t0
    ret

constructor_Asignatura_2:
    addi sp, sp, -20
    sw ra, 16(sp)
    sw fp, 12(sp)
    addi fp, sp, 20
    sw a0, -12(fp)
    la t1, str_12
    lw t1, 0(t1)
    lw t6, -12(fp)
    addi t6, t6, 16
    sw t1, 0(t6)
    li a1, 20
    call z_alloc
    sw a0, -16(fp)
    lw t1, -16(fp)
    lw t6, -12(fp)
    addi t6, t6, 8
    sw t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 24
    sb t1, 0(t6)
    li t1, 0
    lw t6, -12(fp)
    addi t6, t6, 28
    sw t1, 0(t6)
    li a1, 32
    call z_alloc
    sw a0, -20(fp)
    lw t6, -20(fp)
    la t1, __vtable_Docente
    sw t1, 0(t6)
    lw a0, -20(fp)
    call constructor_Docente_2
    sw a0, -20(fp)
    lw t1, -20(fp)
    lw t6, -12(fp)
    addi t6, t6, 32
    sw t1, 0(t6)
    j __epilogo_constructor_Asignatura_2
__epilogo_constructor_Asignatura_2:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 20
    mv fp, t0
    ret

__entrada_main:
    addi sp, sp, -196
    sw ra, 192(sp)
    sw fp, 188(sp)
    addi fp, sp, 196
    li a1, 40
    call z_alloc
    sw a0, -12(fp)
    lw t6, -12(fp)
    la t1, __vtable_Asignatura
    sw t1, 0(t6)
    lw a0, -12(fp)
    la a1, str_13
    lw a1, 0(a1)
    call constructor_Asignatura_1
    sw a0, -12(fp)
    lw t1, -12(fp)
    sw t1, -16(fp)
    li a1, 32
    call z_alloc
    sw a0, -20(fp)
    lw t6, -20(fp)
    la t1, __vtable_Docente
    sw t1, 0(t6)
    lw a0, -20(fp)
    la a1, str_14
    lw a1, 0(a1)
    li a2, 40
    la a3, str_15
    lw a3, 0(a3)
    call constructor_Docente_1
    sw a0, -20(fp)
    lw t1, -20(fp)
    sw t1, -24(fp)
    li t1, 0
    sw t1, -28(fp)
    li t1, 0
    sw t1, -32(fp)
    la t1, str_16
    lw t1, 0(t1)
    sw t1, -36(fp)
    li t1, 0
    sw t1, -40(fp)
    li t1, 0
    sw t1, -44(fp)
    li t1, 0
    sw t1, -48(fp)
    lw a0, -16(fp)
    lw a1, -24(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 16(t0)
    jalr t0
    sw a0, -52(fp)
    la a0, str_17
    lw a0, 0(a0)
    call z_print_cadena
    lw a0, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -56(fp)
    lw a0, -56(fp)
    call z_print_cadena
    call z_print_salto
L52:
    lw t1, -32(fp)
    li t2, 0
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -60(fp)
    lw t1, -60(fp)
    bnez t1, L53
    j L54
L53:
    la a0, str_18
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    la a0, str_19
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    la a0, str_20
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    la a0, str_21
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    la a0, str_22
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    la a0, str_23
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    la a0, str_24
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    la a0, str_25
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    la a0, str_26
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    la a0, str_27
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -28(fp)
    lw t1, -28(fp)
    li t2, 1
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -64(fp)
    lw t1, -64(fp)
    bnez t1, L57
    j L56
L57:
    la a0, str_28
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    li a0, 256
    call z_alloc
    mv a1, a0
    li a2, 256
    call z_leer_cadena
    mv a0, a1
    sw a0, -36(fp)
    la a0, str_29
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -40(fp)
    li a1, 32
    call z_alloc
    sw a0, -68(fp)
    lw t6, -68(fp)
    la t1, __vtable_Estudiante
    sw t1, 0(t6)
    lw a0, -68(fp)
    lw a1, -36(fp)
    lw a2, -40(fp)
    call constructor_Estudiante_1
    sw a0, -68(fp)
    lw t1, -68(fp)
    sw t1, -72(fp)
    li t1, 0
    sw t1, -76(fp)
L58:
    lw t1, -76(fp)
    li t2, 5
    slt t3, t1, t2
    sw t3, -80(fp)
    lw t1, -80(fp)
    bnez t1, L59
    j L61
L59:
    la a0, str_30
    lw a0, 0(a0)
    call z_print_cadena
    lw a0, -76(fp)
    call z_print_entero
    call z_print_salto
    call z_leer_entero
    sw a0, -48(fp)
    lw a0, -72(fp)
    lw a1, -76(fp)
    lw a2, -48(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -84(fp)
L60:
    lw t1, -76(fp)
    addi t3, t1, 1
    sw t3, -76(fp)
    sw t3, -76(fp)
    j L58
L61:
    lw a0, -16(fp)
    lw a1, -72(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -88(fp)
    la a0, str_31
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    j L55
L56:
    lw t1, -28(fp)
    li t2, 2
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -92(fp)
    lw t1, -92(fp)
    bnez t1, L63
    j L62
L63:
    la a0, str_32
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -44(fp)
    la a0, str_33
    lw a0, 0(a0)
    call z_print_cadena
    lw a0, -16(fp)
    lw a1, -44(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -96(fp)
    lw a0, -96(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 28(t0)
    jalr t0
    sw a0, -100(fp)
    lw a0, -100(fp)
    call z_print_decimal
    call z_print_salto
    j L55
L62:
    lw t1, -28(fp)
    li t2, 3
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -104(fp)
    lw t1, -104(fp)
    bnez t1, L65
    j L64
L65:
    la a0, str_32
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -44(fp)
    la a0, str_34
    lw a0, 0(a0)
    call z_print_cadena
    lw a0, -16(fp)
    lw a1, -44(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -108(fp)
    lw a0, -108(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 32(t0)
    jalr t0
    sw a0, -112(fp)
    lw a0, -112(fp)
    call z_print_entero
    call z_print_salto
    la a0, str_35
    lw a0, 0(a0)
    call z_print_cadena
    lw a0, -16(fp)
    lw a1, -44(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -116(fp)
    lw a0, -116(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 36(t0)
    jalr t0
    sw a0, -120(fp)
    lw a0, -120(fp)
    call z_print_entero
    call z_print_salto
    j L55
L64:
    lw t1, -28(fp)
    li t2, 4
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -124(fp)
    lw t1, -124(fp)
    bnez t1, L67
    j L66
L67:
    la a0, str_36
    lw a0, 0(a0)
    call z_print_cadena
    lw a0, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 32(t0)
    jalr t0
    sw a0, -128(fp)
    lw a0, -128(fp)
    call z_print_decimal
    call z_print_salto
    j L55
L66:
    lw t1, -28(fp)
    li t2, 5
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -132(fp)
    lw t1, -132(fp)
    bnez t1, L69
    j L68
L69:
    lw a0, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 36(t0)
    jalr t0
    sw a0, -136(fp)
    j L55
L68:
    lw t1, -28(fp)
    li t2, 6
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -140(fp)
    lw t1, -140(fp)
    bnez t1, L71
    j L70
L71:
    la a0, str_32
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -44(fp)
    li a1, 20
    call z_alloc
    sw a0, -144(fp)
    lw a0, -16(fp)
    lw a1, -44(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -148(fp)
    lw a0, -148(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 24(t0)
    jalr t0
    sw a0, -152(fp)
    lw t1, -152(fp)
    sw t1, -144(fp)
    la a0, str_37
    lw a0, 0(a0)
    call z_print_cadena
    lw a0, -144(fp)
    call fun_calcularPromedioArreglo
    sw a0, -156(fp)
    lw a0, -156(fp)
    call z_print_decimal
    call z_print_salto
    la a0, str_38
    lw a0, 0(a0)
    call z_print_cadena
    lw a0, -144(fp)
    call fun_encontrarMaxima
    sw a0, -160(fp)
    lw a0, -160(fp)
    call z_print_entero
    call z_print_salto
    la a0, str_39
    lw a0, 0(a0)
    call z_print_cadena
    lw a0, -144(fp)
    call fun_encontrarMinima
    sw a0, -164(fp)
    lw a0, -164(fp)
    call z_print_entero
    call z_print_salto
    j L55
L70:
    lw t1, -28(fp)
    li t2, 7
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -168(fp)
    lw t1, -168(fp)
    bnez t1, L73
    j L72
L73:
    la a0, str_32
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    call z_leer_entero
    sw a0, -44(fp)
    li a1, 24
    call z_alloc
    sw a0, -172(fp)
    lw t6, -172(fp)
    la t1, __vtable_Persona
    sw t1, 0(t6)
    lw a0, -172(fp)
    call constructor_Persona_2
    sw a0, -172(fp)
    lw t1, -172(fp)
    sw t1, -176(fp)
    lw a0, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 12(t0)
    jalr t0
    sw a0, -180(fp)
    lw t1, -180(fp)
    sw t1, -176(fp)
    lw a0, -16(fp)
    lw a1, -176(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 40(t0)
    jalr t0
    sw a0, -184(fp)
    lw a0, -16(fp)
    lw a1, -44(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 20(t0)
    jalr t0
    sw a0, -188(fp)
    lw t1, -188(fp)
    sw t1, -176(fp)
    lw a0, -16(fp)
    lw a1, -176(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 40(t0)
    jalr t0
    sw a0, -192(fp)
    j L55
L72:
    lw t1, -28(fp)
    li t2, 8
    sub t3, t1, t2
    seqz t3, t3
    sw t3, -196(fp)
    lw t1, -196(fp)
    bnez t1, L75
    j L74
L75:
    li t1, 1
    sw t1, -32(fp)
    la a0, str_40
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
    j L55
L74:
    la a0, str_41
    lw a0, 0(a0)
    call z_print_cadena
    call z_print_salto
L55:
    j L52
L54:
__epilogo_main:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 196
    mv fp, t0
    ret

    j z_exit

.data
__z_nulo: .asciiz "(nulo)"
__z_heap: .word 0x10002000
__z_buf: .word 0
    .word 0
    .word 0
    .word 0
    .word 0
    .word 0
    .word 0
    .word 0
    .word 0
    .word 0
    .word 0
    .word 0
    .word 0
    .word 0
    .word 0
    .word 0
.text

# ---------------------------------------------------------
# z_print_salto: imprime newline (caracter 10)
# ---------------------------------------------------------
z_print_salto:
    li a0, 11
    li a1, 10
    ecall
    ret

# ---------------------------------------------------------
# z_print_cadena: print_string(a0)
# ---------------------------------------------------------
z_print_cadena:
    mv a1, a0
    li a0, 4
    ecall
    ret

# ---------------------------------------------------------
# z_print_entero: print_int(a0)
# ---------------------------------------------------------
z_print_entero:
    mv a1, a0
    li a0, 1
    ecall
    ret

# ---------------------------------------------------------
# z_print_decimal: print_int(a0) — punto fijo escala 1
# ---------------------------------------------------------
z_print_decimal:
    mv a1, a0
    li a0, 1
    ecall
    ret

# ---------------------------------------------------------
# z_print_caracter: print_char(a0)
# ---------------------------------------------------------
z_print_caracter:
    mv a1, a0
    li a0, 11
    ecall
    ret

# ---------------------------------------------------------
# z_print_booleano: print_int(a0) — imprime 0 o 1
# ---------------------------------------------------------
z_print_booleano:
    mv a1, a0
    li a0, 1
    ecall
    ret

# ---------------------------------------------------------
# z_print_nulo: print_string(__z_nulo)
# ---------------------------------------------------------
z_print_nulo:
    li a0, 4
    la a1, __z_nulo
    ecall
    ret

# ---------------------------------------------------------
# z_leer_entero: read_int (ecall 5) -> a0
# ---------------------------------------------------------
z_leer_entero:
    li a0, 5
    ecall
    ret

# ---------------------------------------------------------
# z_leer_decimal: read_int (ecall 5) -> a0, punto fijo
# ---------------------------------------------------------
z_leer_decimal:
    li a0, 5
    ecall
    ret

# ---------------------------------------------------------
# z_leer_caracter: read_char -> a0
# ---------------------------------------------------------
z_leer_caracter:
    li a0, 12
    ecall
    ret

# ---------------------------------------------------------
# z_leer_cadena: read_string (a1 = buffer, a2 = max)
# ---------------------------------------------------------
z_leer_cadena:
    li a0, 8
    ecall
    ret

# ---------------------------------------------------------
# z_alloc: sbrk (a1 = bytes) -> a0 puntero
# ---------------------------------------------------------
z_alloc:
    li a0, 9
    ecall
    ret

# ---------------------------------------------------------
# z_exit: exit
# ---------------------------------------------------------
z_exit:
    li a0, 10
    ecall
