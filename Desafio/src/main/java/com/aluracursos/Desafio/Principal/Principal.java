package com.aluracursos.Desafio.Principal;

import com.aluracursos.Desafio.Model.Autor;
import com.aluracursos.Desafio.Model.Datos;
import com.aluracursos.Desafio.Model.DatosAutor;
import com.aluracursos.Desafio.Model.DatosLibros;
import com.aluracursos.Desafio.Model.Libro;
import com.aluracursos.Desafio.Repository.AutorRepository;
import com.aluracursos.Desafio.Repository.LibroRepository;
import com.aluracursos.Desafio.Service.ConsumoApi;
import com.aluracursos.Desafio.Service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 8) {
            var menu = """
                -------------------------------------
                Elija la opción a través de su número:
                1 - Buscar libro por título
                2 - Listar libros buscados
                3 - Listar autores buscados
                4 - Listar autores vivos en un determinado año
                5 - Listar libros por idioma
                6 - Ver estadísticas de libros por idioma
                7 - Ver el top 10 de libros mas descargados
                8 - Salir
                -------------------------------------
                """;
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();
            } catch (Exception e) {
                System.out.println("Opción inválida. Por favor, ingrese un número.");
                teclado.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosBuscados();
                    break;
                case 3:
                    listarAutoresBuscados();
                    break;
                case 4:
                    listarAutoresVivosEnAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    exhibirEstadisticasPorIdioma();
                    break;
                case 7:
                    mostrarTop10Libros();
                    break;
                case 8:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }
    //Busca los libros por titulo
    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el título del libro que desea buscar: ");
        var tituloLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibros> libroBuscado = datosBusqueda.Libros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            DatosLibros datosLibros = libroBuscado.get();
            Libro libro = new Libro(datosLibros);

            if (!datosLibros.autor().isEmpty()) {
                DatosAutor datosAutor = datosLibros.autor().get(0);
                Optional<Autor> autorExistente = autorRepository.findByNombreContainingIgnoreCase(datosAutor.nombre());

                Autor autor;
                if (autorExistente.isPresent()) {
                    autor = autorExistente.get();
                } else {
                    autor = new Autor(datosAutor);
                    autorRepository.save(autor);
                }
                libro.setAutor(autor);
            }

            libroRepository.save(libro);
            System.out.println("Libro guardado exitosamente:");
            System.out.println(libro);
        } else {
            System.out.println("Libro no encontrado.");
        }
    }
    // Muestra los libros buscados
    private void listarLibrosBuscados() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }
    //Muestra los autores buscados
    private void listarAutoresBuscados() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }
    //Muestra los autores vivos en un año ingresado por el usuario
    private void listarAutoresVivosEnAnio() {
        System.out.println("Ingrese el año para buscar autores vivos:");
        try {
            var anio = teclado.nextInt();
            teclado.nextLine();
            List<Autor> autores = autorRepository.buscarAutoresVivosEnAnio(anio);
            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores vivos en ese año.");
            } else {
                autores.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Año inválido. Por favor, ingrese un número entero.");
            teclado.nextLine();
        }
    }
    //Muestra los libros por idioma
    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma para buscar (ej. es, en, fr):");
        var idioma = teclado.nextLine();
        List<Libro> librosPorIdioma = libroRepository.findByIdioma(idioma);
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma.");
        } else {
            librosPorIdioma.forEach(System.out::println);
        }
    }
    //Muestra las estadististicas totales
    private void exhibirEstadisticasPorIdioma() {
        System.out.println("\n--- ESTADÍSTICAS DE LIBROS POR IDIOMA ---\n");

        long librosEnIngles = libroRepository.countByIdioma("en");
        long librosEnEspanol = libroRepository.countByIdioma("es");
        long librosEnFrances = libroRepository.countByIdioma("fr");
        long librosEnPortugues = libroRepository.countByIdioma("pt");

        System.out.println("Cantidad de libros en Inglés (en): " + librosEnIngles);
        System.out.println("Cantidad de libros en Español (es): " + librosEnEspanol);
        System.out.println("Cantidad de libros en Francés (fr): " + librosEnFrances);
        System.out.println("Cantidad de libros en Portugués (pt): " + librosEnPortugues);

        System.out.println("\n----------------------------------------\n");
    }
    private void mostrarTop10Libros() {
        System.out.println("\n--- TOP 10 LIBROS MÁS DESCARGADOS ---\n");
        List<Libro> top10Libros = libroRepository.findTop10ByOrderByNumeroDeDescargasDesc();
        top10Libros.forEach(System.out::println);
        System.out.println("\n----------------------------------------\n");
    }
}