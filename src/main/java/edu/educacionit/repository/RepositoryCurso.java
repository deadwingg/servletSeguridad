package edu.educacionit.repository;

import edu.educacionit.model.Curso;

import java.util.ArrayList;
import java.util.List;

public class RepositoryCurso {
    public static List<Curso> cursos;
    static {
        cursos = new ArrayList<>();
        cursos.add(
                new Curso("3c12e417-848a-42db-9898-cfd148fa8302",
                        "Curso de Liderazgo"));
        cursos.add(
                new Curso("30bf7fb2-c315-4f45-9d20-580091b73fa2",
                        "Java Web Ninja"));
    }
}
