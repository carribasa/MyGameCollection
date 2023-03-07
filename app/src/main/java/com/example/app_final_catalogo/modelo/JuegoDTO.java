package com.example.app_final_catalogo.modelo;

/**
 * Clase que sirve para definir cada juego con sus correspondientes atributos
 */
public class JuegoDTO {

    // atributos
    private String nombre;
    private String plataforma;
    private String genero;
    private Integer nota;
    private boolean completado;
    private String uriImagen = "";

    // constructor
    public JuegoDTO() {

    }

    public JuegoDTO(String nombre, String plataforma, String genero, Integer nota,
                    boolean completado, String uriImagen) {
        this.nombre = nombre;
        this.plataforma = plataforma;
        this.genero = genero;
        this.nota = nota;
        this.completado = completado;
        this.uriImagen = uriImagen;
    }

    // metodos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public String getUriImagen() {
        return uriImagen;
    }

    public void setUriImagen(String uriImagen) {
        this.uriImagen = uriImagen;
    }

    @Override
    public String toString() {
        return this.nombre + "::" + this.plataforma + "::" + this.genero + "::" + this.nota + "::" + this.completado + "::" + this.uriImagen + "\n";
    }
}
