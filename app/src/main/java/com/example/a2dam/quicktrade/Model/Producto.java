package com.example.a2dam.quicktrade.Model;


public class Producto {

    private String nombre,descripcion,usuario,precio,categoria;

    public Producto(){

    }


    public Producto(String nombre, String usuario, String descripcion, String precio, String categoria) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.descripcion=descripcion;
        this.precio=precio;
        this.categoria=categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", usuario='" + usuario + '\'' +
                ", precio='" + precio + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
