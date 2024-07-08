package com.aluracursos.challenge_books.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Autor {
    @JsonAlias("name")
    private String nombre;
    @JsonAlias("birth_year")
    private String anoNacimiento;
    @JsonAlias("death_year")
    private String anoFallecimiento;

    public Autor() {}

    public Autor(String nombre, String anoNacimiento, String anoFallecimiento) {
        this.nombre = nombre;
        this.anoNacimiento = anoNacimiento;
        this.anoFallecimiento = anoFallecimiento;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(String anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public String getAnoFallecimiento() {
        return anoFallecimiento;
    }

    public void setAnoFallecimiento(String anoFallecimiento) {
        this.anoFallecimiento = anoFallecimiento;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "nombre='" + nombre + '\'' +
                ", anoNacimiento='" + anoNacimiento + '\'' +
                ", anoFallecimiento='" + anoFallecimiento + '\'' +
                '}';
    }
}
