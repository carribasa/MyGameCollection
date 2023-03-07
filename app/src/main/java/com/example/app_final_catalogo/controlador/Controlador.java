package com.example.app_final_catalogo.controlador;

import com.example.app_final_catalogo.modelo.ColeccionDAO;

public class Controlador {

    private static ColeccionDAO coleccion;

    public Controlador() {

        this.coleccion = new ColeccionDAO();

    }
}
