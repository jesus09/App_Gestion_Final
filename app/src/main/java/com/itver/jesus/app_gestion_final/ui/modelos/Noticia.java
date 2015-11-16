package com.itver.jesus.app_gestion_final.ui.modelos;

import android.util.Log;

import com.itver.jesus.app_gestion_final.R;

import java.util.ArrayList;

/**
 * Clase modelo de una Noticia.
 * Created by Jesus on 04/10/2015.
 */
public class Noticia {

    private String id;
    private int imagen;
    private String titulo;
    private String autor;
    private String fecha;
    private String contenido;
    private int departamento;
    private int categoria;
    private boolean visto;

    public Noticia() {
        // El departamento 0 es público.
        // La categoría 0 es general.
    }

    public Noticia(String id, int imagen, String titulo, String autor, String fecha, String contenido, int departamento, int categoria, boolean visto) {
        setImagen(imagen);
        this.id = id;
        this.imagen = getImagen();
        this.titulo = titulo;
        this.autor = autor;
        this.fecha = fecha;
        this.contenido = contenido;
        this.departamento = departamento;
        this.categoria = categoria;
        this.visto = visto;
    }

    public Noticia(String id, int imagen, String titulo, String autor, String fecha, String contenido, int departamento, int categoria) {
        this(imagen, titulo, autor, fecha, contenido, departamento, categoria);
        this.id = id;
    }

    public Noticia(int imagen, String titulo, String autor, String fecha, String contenido, int departamento, int categoria) {
        setImagen(imagen);
        this.imagen = getImagen();
        this.titulo = titulo;
        this.autor = autor;
        this.fecha = fecha;
        this.contenido = contenido;
        this.departamento = departamento;
        this.categoria = categoria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        if (imagen == 1) {
            this.imagen = R.drawable.itver;
        } else if (imagen == 2) {
            this.imagen = R.drawable.logo;
        } else if (imagen == 3) {
            this.imagen = R.drawable.itver;
        } else {
            this.imagen = R.drawable.itver;
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }
}