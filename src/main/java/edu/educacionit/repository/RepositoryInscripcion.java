package edu.educacionit.repository;

import edu.educacionit.model.Inscripcion;

import java.util.ArrayList;
import java.util.List;

public class RepositoryInscripcion {
    public static List<Inscripcion> inscripciones;

    static {
        inscripciones = new ArrayList<>();
    }
}
