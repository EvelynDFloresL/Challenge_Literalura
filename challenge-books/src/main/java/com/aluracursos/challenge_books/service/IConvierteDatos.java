package com.aluracursos.challenge_books.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
