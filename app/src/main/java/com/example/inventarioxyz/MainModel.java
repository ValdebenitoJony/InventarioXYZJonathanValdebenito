package com.example.inventarioxyz;
public class MainModel {
    String Nombre, Precio, Stock, imgURL;

    public MainModel() {
    }

    public MainModel(String nombre, String precio, String stock, String imgURL) {
        Nombre = nombre;
        Precio = precio;
        Stock = stock;
        this.imgURL = imgURL;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
