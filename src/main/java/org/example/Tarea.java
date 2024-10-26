package org.example;

public class Tarea {
    private String nombre;
    private boolean completada;

    public Tarea (String nombre) {
        this.nombre = nombre;
        this.completada = false;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEstado() {
        return completada ? "Completado" : "Pendiente";
    }

    public void marcarCompletada() {
        this.completada = true;
    }

}
