package com.example.rastreador2.entidades;

public class Usuario {
    private Integer id;
    private String nombre;
    private String phone_number;
    private Boolean activo;

    public Usuario(Integer id, String nombre, String phone_number, Boolean activo) {
        this.id = id;
        this.phone_number = phone_number;
        this.nombre = nombre;
        this.activo = activo;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
