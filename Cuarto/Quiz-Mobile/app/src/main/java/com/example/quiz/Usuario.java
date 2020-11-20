package com.example.quiz;

public class Usuario implements Comparable<Usuario>{

    private String nombre;
    private String email;
    private String password;
    private int puntuacion;

    public Usuario() {
    }

    public Usuario(String email, String password,String nombre, int puntuacion) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public int compareTo(Usuario o) {
        return this.puntuacion - o.puntuacion;
    }
}
