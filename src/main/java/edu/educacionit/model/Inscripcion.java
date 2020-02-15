package edu.educacionit.model;

import java.util.ArrayList;
import java.util.List;

public class Inscripcion {
    String cursoId;
    List<Usuario> inscriptos;
    {
        inscriptos = new ArrayList<>();
    }

    public Inscripcion(String cursoId) {
        this.cursoId = cursoId;
    }

    public Inscripcion() {
    }

    public String getCursoId() {
        return cursoId;
    }

    public void setCursoId(String cursoId) {
        this.cursoId = cursoId;
    }

    public List<Usuario> getInscriptos() {
        return inscriptos;
    }

    public void setInscriptos(List<Usuario> inscriptos) {
        this.inscriptos = inscriptos;
    }

    @Override
    public String toString() {
        return "Inscripcion{" +
                "cursoId='" + cursoId + '\'' +
                ", inscriptos=" + inscriptos +
                '}';
    }
}
