package com.example.rastreador2.entidades;

public class Usuario {
    private Integer id;
    private String nombre;
    private String phone_number;
    private Integer activo;
    private String image_path;

    public Usuario(Integer id, String nombre, String phone_number, Integer activo, String image_path) {
        this.id = id;
        this.phone_number = phone_number;
        this.nombre = nombre;
        this.activo = activo;
        this.image_path = image_path;
    }

    public Usuario() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getImage_path() {
        return image_path;
    }
}
