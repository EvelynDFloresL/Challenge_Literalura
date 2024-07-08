package com.aluracursos.challenge_books.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "libro_autores", joinColumns = @JoinColumn(name = "libro_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "nombre", column = @Column(name = "autor_nombre")),
            @AttributeOverride(name = "anoNacimiento", column = @Column(name = "autor_ano_nacimiento")),
            @AttributeOverride(name = "anoFallecimiento", column = @Column(name = "autor_ano_fallecimiento"))
    })
    private List<Autor> autores;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idiomas;
    private Integer cantidadDescargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.autores = datosLibro.autores().stream()
                .map(datoAutor -> new Autor(datoAutor.nombre(), datoAutor.anoNacimiento(), datoAutor.anoFallecimiento()))
                .collect(Collectors.toList());
        this.idiomas = datosLibro.idiomas();
       this.cantidadDescargas = datosLibro.cantidadDescargas();
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autores='" + autores + '\'' +
                ", idiomas='" + idiomas + '\'' +
                ", cantidadDescargas=" + cantidadDescargas +
                '}';
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(Integer cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }
}
