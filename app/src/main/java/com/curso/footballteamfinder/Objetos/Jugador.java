package com.curso.footballteamfinder.Objetos;


import java.util.List;
import java.util.Vector;


public class Jugador {
    String nombre;
    String apellido;
    String apodo;
    int edad;
    int valoracion;

    public Jugador() {
    }

    public Jugador(String nombre, String apellido, String apodo, int edad, int valoracion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.apodo = apodo;
        this.edad = edad;
        this.valoracion = valoracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }
}
