package com.example.rastreador2.entidades;

public class Herramienta {

    private Integer id;
    private String phone_number;
    private String nombre;
    private  String serie;


    public Herramienta(Integer id, String phone_number, String nombre, String serie) {

        this.id = id;
        this.phone_number = phone_number;
        this.nombre = nombre;
        this.serie = serie;
    }

    public Herramienta(){

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNumber() { return phone_number; }

    public void setPhone_number(String phone_number) { this.phone_number = phone_number; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }
}
