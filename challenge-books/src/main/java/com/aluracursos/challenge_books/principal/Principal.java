package com.aluracursos.challenge_books.principal;


import com.aluracursos.challenge_books.model.Autor;
import com.aluracursos.challenge_books.model.DatosLibro;
import com.aluracursos.challenge_books.model.Libro;
import com.aluracursos.challenge_books.model.Root;
import com.aluracursos.challenge_books.repository.LibroRepository;
import com.aluracursos.challenge_books.service.ConsumoAPI;
import com.aluracursos.challenge_books.service.ConvierteDatos;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibro> datosLibro = new ArrayList<>();
    private LibroRepository repositorio;
    private List<Libro> libros;
    private Optional<Libro> libroBuscado;

    public Principal(LibroRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
    ********** Elija la opcion a traves de su numero **********
                    1 - Buscar libro por titulo 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                                                     
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresPorAno();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }



    }

    private Root getDatosLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE +"?search=" + nombreLibro.replace(" ", "+"));
        System.out.println("JSON recibido: " + json);

        if (json == null || json.isEmpty()) {
            System.err.println("No se recibieron datos de la API.");
            return null;
        }
        Root root = conversor.obtenerDatos(json, Root.class);

        if (root == null) {
            System.err.println("Error al deserializar el JSON.");
            return null;
        }
        return root;
    }

    private void buscarLibroTitulo() {
        Root root = getDatosLibro();
        if (root != null && root.getResults() != null && !root.getResults().isEmpty()) {
            DatosLibro datos = root.getResults().get(0);  // Obtener el primer resultado
            Libro libro = new Libro(datos);
            repositorio.save(libro);  // Descomenta esta línea para guardar el libro en el repositorio
            System.out.println(libro);  // Imprimir el objeto libro
        } else {
            System.out.println("No se encontraron libros con ese título.");
        }

    }

    private void listarLibrosRegistrados(){
        libros = repositorio.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados(){
        libros = repositorio.findAll();
        Set<Autor> autores = new HashSet<>();
        libros.forEach(libro -> autores.addAll(libro.getAutores()));
        autores.forEach(System.out::println);
    }

    private void listarAutoresPorAno(){
        System.out.println("Ingrese el año para buscar autores vivos");
        int anio =Integer.parseInt(teclado.nextLine());
        List<Autor> autoresVivos = repositorio.encontrarAutoresVivosEnAnio(anio);
        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año especificado.");
        } else {
            System.out.println("Autores vivos en el año " + anio + ":");
            autoresVivos.forEach(autor -> {
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Año de nacimiento: " + autor.getAnoNacimiento());
                System.out.println("Año de fallecimiento: " + autor.getAnoFallecimiento());
                System.out.println("******************************************");
            });
        }
    }

    private void listarLibrosPorIdioma(){
        var opcionI = -1;
        while (opcionI != 9) {
            var idiomas = """
                    **** Elija el idioma que desea listar ****
                                    1 - en (Ingles)
                                    2 - es (Español)
                                    3 - fr (Frances)
                                                                              
                                    9 - Menu Anterior
                                    """;
            System.out.println(idiomas);
            opcionI = teclado.nextInt();
            teclado.nextLine();
            String idioma = "";
            switch (opcionI) {
                case 1:
                    idioma = "en";
                    break;
                case 2:
                    idioma = "es";
                    break;
                case 3:
                    idioma = "fr";
                    break;
                case 9:
                    muestraElMenu();
                    break;
                default:
                    System.out.println("Opción inválida");
            }

            List<Libro> librosFiltrados = repositorio.encontrarLibrosPorIdioma(idioma);
            if (librosFiltrados.isEmpty()) {
                System.out.println("No se encontraron libros en el idioma seleccionado.");
            } else {
                System.out.println("Libros en idioma " + idioma + ":");
                librosFiltrados.forEach(libro -> {
                    System.out.println("Título: " + libro.getTitulo());
                    System.out.println("Autores: " + libro.getAutores());
                    System.out.println("Cantidad de Descargas: " + libro.getCantidadDescargas());
                    System.out.println("******************************************");
                });
            }

        }

    }

}
