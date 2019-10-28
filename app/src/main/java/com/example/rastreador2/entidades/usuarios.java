package com.example.rastreador2.entidades;

public class usuarios {

    private Integer ID;
    private String nombre;
    private  String serie;


    public usuarios(Integer ID, String nombre, String serie) {

        this.ID = ID;
        this.nombre = nombre;
        this.serie = serie;
    }


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

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
