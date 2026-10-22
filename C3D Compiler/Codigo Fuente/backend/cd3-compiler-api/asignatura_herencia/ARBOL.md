# Arbol de trabajo - Proyecto Asignatura (Zetariano)

asignatura/
├── main.pig                  # programa principal (usa getters, polimorfismo)
├── modelo/                   # paquete "modelo" (default = visible aqui)
│   ├── Persona.z             # superclase: protected nombre, private edad
│   ├── Estudiante.z          # extends Persona, @Override mostrarDatos()
│   ├── Docente.z             # extends Persona, @Override mostrarDatos()
│   └── Asignatura.z          # atributos private, hayCupo() default
└── utilidades/
    └── Funciones.y           # sin cambios

Jerarquia:  Objeto -> Persona -> { Estudiante, Docente }
