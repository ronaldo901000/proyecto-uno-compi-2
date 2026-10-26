.data
.align 2
str_0: .asciz "Hola desde A"
.align 2
str_1: .asciz "Hola desde B"
.align 2
__vtable_A:
    .word metodo_A_saludar_1
.align 2
__vtable_B:
    .word metodo_B_saludar_1
.text
.globl main
main:
    call __entrada_main
    call z_exit

metodo_A_saludar_1:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    la t1, str_0
    sw t1, -16(fp)
    lw a0, -16(fp)
    call z_print_cadena
    call z_print_salto
    j __epilogo_metodo_A_saludar_1
__epilogo_metodo_A_saludar_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

constructor_A_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    j __epilogo_constructor_A_1
__epilogo_constructor_A_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

metodo_B_saludar_1:
    addi sp, sp, -16
    sw ra, 12(sp)
    sw fp, 8(sp)
    addi fp, sp, 16
    sw a0, -12(fp)
    la t1, str_1
    sw t1, -16(fp)
    lw a0, -16(fp)
    call z_print_cadena
    call z_print_salto
    j __epilogo_metodo_B_saludar_1
__epilogo_metodo_B_saludar_1:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 16
    mv fp, t0
    ret

constructor_B_1:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw fp, 4(sp)
    addi fp, sp, 12
    sw a0, -12(fp)
    j __epilogo_constructor_B_1
__epilogo_constructor_B_1:
    lw a0, -12(fp)
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 12
    mv fp, t0
    ret

__entrada_main:
    addi sp, sp, -32
    sw ra, 28(sp)
    sw fp, 24(sp)
    addi fp, sp, 32
    li a0, 8
    call z_alloc
    sw a0, -12(fp)
    lw t6, -12(fp)
    la t1, __vtable_A
    sw t1, 0(t6)
    lw a0, -12(fp)
    call constructor_A_1
    sw a0, -12(fp)
    lw t1, -12(fp)
    sw t1, -16(fp)
    li a0, 8
    call z_alloc
    sw a0, -20(fp)
    lw t6, -20(fp)
    la t1, __vtable_B
    sw t1, 0(t6)
    lw a0, -20(fp)
    call constructor_B_1
    sw a0, -20(fp)
    lw t1, -20(fp)
    sw t1, -24(fp)
    lw a0, -16(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -28(fp)
    lw a0, -24(fp)
    mv t0, a0
    lw t0, 0(t0)
    lw t0, 0(t0)
    jalr t0
    sw a0, -32(fp)
__epilogo_main:
    lw t0, -8(fp)
    lw ra, -4(fp)
    addi sp, sp, 32
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
