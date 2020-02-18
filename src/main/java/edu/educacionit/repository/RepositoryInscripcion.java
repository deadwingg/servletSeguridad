package edu.educacionit.repository;

import edu.educacionit.model.Inscripcion;

import java.util.ArrayList;
import java.util.List;

public class RepositoryInscripcion {
    public static List<Inscripcion> inscripciones;

    static {
        inscripciones = new ArrayList<>();
        inscripciones.add(new Inscripcion("3c12e417-848a-42db-9898-cfd148fa8302","2e8cc669-4adc-4dc0-ac3b-aabeb7daf149"));
        inscripciones.add(new Inscripcion("30bf7fb2-c315-4f45-9d20-580091b73fa2","e29a6535-dad2-47b7-bf20-a6743a0377b4"));
    }
}
