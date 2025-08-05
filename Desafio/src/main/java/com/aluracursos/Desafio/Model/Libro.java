package com.aluracursos.Desafio.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Double numeroDeDescargas;
    @ManyToOne
    private Autor autor;

    public Libro() {}

    public Libro(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        // Tomamos solo el primer idioma de la lista, como se especifica en el desafío
        this.idioma = datosLibros.idiomas().get(0);
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return """
               ----------- LIBRO -----------
               Título: %s
               Autor: %s
               Idioma: %s
               Número de descargas: %f
               -----------------------------
               """.formatted(titulo, autor.getNombre(), idioma, numeroDeDescargas);
    }
}