package com.aluracursos.challenge_books.repository;

import com.aluracursos.challenge_books.model.Autor;
import com.aluracursos.challenge_books.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    @Query("SELECT a FROM Libro l JOIN l.autores a WHERE a.anoNacimiento <= :anio AND (a.anoFallecimiento IS NULL OR a.anoFallecimiento >= :anio)")
    List<Autor> encontrarAutoresVivosEnAnio(int anio);

    @Query("SELECT l FROM Libro l WHERE :idioma MEMBER OF l.idiomas")
    List<Libro> encontrarLibrosPorIdioma(String idioma);
}
