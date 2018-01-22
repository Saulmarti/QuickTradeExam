package com.example.a2dam.quicktrade.Model;

/**
 * Created by 2dam on 22/01/2018.
 */

public class Favoritos {

    String usuario;
    String nombre;

    public Favoritos(){

    }

    public Favoritos(String nombre, String usuario) {
        this.usuario = usuario;
        this.nombre = nombre;
    }

    public String getUsuario() {

        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
