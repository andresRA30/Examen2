package com.example.examen2.model;

public class Estudiante {

    private long id;
    private String nombre;
    private String edad;
    private String correo;
    private String foto;

    public Estudiante() {
    }

    public Estudiante(String nombre, String edad, String correo, String foto) {
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
        this.foto = foto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String image) {
        this.foto = foto;
    }


}
