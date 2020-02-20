package edu.educacionit.model;

import edu.educacionit.repository.RepositoryCurso;
import edu.educacionit.repository.RepositoryUsuario;

import java.util.Optional;

public class Inscripcion {
    public String idInscripcion;
    public String idCurso;
    public String idUsuario;
    public String nombreCurso;
    public String nombreUsuario;

    public Inscripcion(String idInscripcion, String idCurso, String idUsuario) {
        this.idInscripcion = idInscripcion;
        this.idCurso = idCurso;
        Optional<String> busquedaCurso = buscarNombreCurso(idCurso);
        if (busquedaCurso.isPresent()){
            this.nombreCurso = busquedaCurso.get();
        }
        Optional<String> busquedaUsuario = buscarNombreUsuario(idUsuario);
        if (busquedaUsuario.isPresent()){
            this.nombreUsuario = busquedaUsuario.get();
        }
        this.idUsuario = idUsuario;
    }

    private Optional<String> buscarNombreCurso(String cursoId) {
        return RepositoryCurso.cursos
                .stream()
                .filter(curso -> curso.getId().equalsIgnoreCase(cursoId))
                .findFirst()
                .map(Curso::getNombre);
    }

    private Optional<String> buscarNombreUsuario(String usuarioId) {
        return RepositoryUsuario.usuarios
                .stream()
                .filter(usuario -> usuario.getId().equalsIgnoreCase(usuarioId))
                .findFirst()
                .map(Usuario::getNombre);
    }
}
