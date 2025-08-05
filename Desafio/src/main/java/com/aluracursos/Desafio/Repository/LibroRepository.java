package com.aluracursos.Desafio.Repository;
import com.aluracursos.Desafio.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByIdioma(String idioma);

    // Metodo para contar libros por idioma (Derived Query)
    long countByIdioma(String idioma);

    // Nuevo metodo para encontrar el Top 10 de libros más descargados
    List<Libro> findTop10ByOrderByNumeroDeDescargasDesc();
}