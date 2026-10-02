/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ronaldo.cd3.compiler.api;

/**
 *
 * @author ronaldo
 */

public class Test {
    
    public void crearObjeto(){
        Test [][] array =
        {
            {new Test(), new Test()},
            {null, null},
            {new Test(), new Test()},
            {new Test(), new Test()},
            {new Test(), new Test()},
            {new Test(), new Test()},
            {new Test(), new Test()},
            {new Test(), new Test()},
        };
    }
    // public: accesible desde cualquier clase de cualquier paquete
    public String nombre;

    // protected: accesible desde el mismo paquete y desde subclases (aunque estén en otro paquete)
    protected int edad;

    // default (package-private): solo accesible desde clases del mismo paquete
    String ciudad;

    // private: solo accesible dentro de esta misma clase
    private double saldo;

    // Constructor public: cualquiera puede crear un Test
    public Test(String nombre, int edad, String ciudad, double saldo) {
        this.nombre = nombre;
        this.edad = edad;
        this.ciudad = ciudad;
        this.saldo = saldo;
    }

    // Constructor private: solo se puede invocar desde dentro de la clase
    // (útil en patrones como Singleton o métodos de fábrica)
    private Test() {
        this("Anónimo", 0, "N/A", 0.0);
    }

    // Constructor private: solo se puede invocar desde dentro de la clase
    // (útil en patrones como Singleton o métodos de fábrica)
    protected Test(int a) {
        this("Anónimo", 0, "N/A", 0.0);
    }

    
    Test(int a, int b) {
        this("Anónimo", 0, "N/A", 0.0);
    }

    // Método public: forma "oficial" de interactuar con el objeto
    public double getSaldo() {
        return saldo; // dentro de la clase sí puedo acceder al private
    }

    // Método protected: pensado para que lo usen o sobrescriban las subclases
    protected void mostrarEdad() {
        System.out.println("Edad: " + edad);
    }

    // Método default: utilidad interna del paquete
    void mostrarCiudad() {
        System.out.println("Ciudad: " + ciudad);
    }

    // Método private: detalle interno, nadie de afuera lo ve
    private void validarSaldo() {
        if (saldo < 0) {
            System.out.println("Saldo negativo");
        }
    }

    // Método public que usa el private (encapsulamiento)
    public void depositar(double monto) {
        saldo += monto;
        validarSaldo();
    }

    // Método de fábrica que usa el constructor private
    public static Test crearAnonimo() {
        return new Test();
    }
}
