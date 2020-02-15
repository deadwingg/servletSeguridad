package edu.educacionit.service;

import edu.educacionit.model.Curso;
import edu.educacionit.model.Inscripcion;
import edu.educacionit.model.Usuario;
import edu.educacionit.repository.RepositoryCurso;
import edu.educacionit.repository.RepositoryInscripcion;
import edu.educacionit.repository.RepositoryUsuario;

import java.util.List;
import java.util.Optional;

public class ServicioInscripcion {

    public List<Inscripcion> obtenerInscripciones(){
        return RepositoryInscripcion.inscripciones;
    }

    public Optional<Inscripcion> obtenerInscripcion(String id){
        return RepositoryInscripcion.inscripciones
                .stream()
                .filter(inscripcion -> inscripcion.getCursoId().equalsIgnoreCase(id))
                .findFirst();
    }

    public boolean borrarInscripcion(String cursoID, String userID){
        Optional<Inscripcion> target = RepositoryInscripcion.inscripciones
                .stream()
                .filter(inscripcion -> inscripcion.getCursoId().equalsIgnoreCase(cursoID))
                .findFirst();
        if (target.isPresent()){
            if (target.get().getInscriptos().removeIf(usuario -> usuario.getId().equalsIgnoreCase(userID))) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    public void agregarInscripcion(String cursoID, String userID){
        //Checkeo si esta en la lista de cursos
        Optional<Curso> cursoAAgregar = RepositoryCurso.cursos
                .stream()
                .filter(curso -> curso.getId().equalsIgnoreCase(cursoID))
                .findFirst();
        Optional<Usuario> usuarioAAgregar = RepositoryUsuario.usuarios
                .stream()
                .filter(usuario -> usuario.getId().equalsIgnoreCase(userID))
                .findFirst();

        Optional<Inscripcion> inscripcionAAgregar = RepositoryInscripcion.inscripciones
                .stream()
                .filter(inscripcion -> inscripcion.getCursoId().equalsIgnoreCase(cursoID))
                .findFirst();

        //si el curso y usuario existen
        if (cursoAAgregar.isPresent() && usuarioAAgregar.isPresent()){
            //si la inscripcion ya esta en la lista(curso ya tiene alumnos)
            if (inscripcionAAgregar.isPresent()){
                //agrego el usuario
                inscripcionAAgregar.get().getInscriptos().add(usuarioAAgregar.get());
            } else {
                //sino creo un nuevo objeto inscripcion
                Inscripcion nuevaInscripcion = new Inscripcion(cursoAAgregar.get().getId());
                nuevaInscripcion.getInscriptos().add(usuarioAAgregar.get());
                RepositoryInscripcion.inscripciones.add(nuevaInscripcion);
            }
        }
        /*if (inscripcionAAgregar.isPresent()){
            inscripcionAAgregar.get().getInscriptos().add(usuarioAAgregar.get());
        } else if (cursoAAgregar.isPresent() && usuarioAAgregar.isPresent()){
            Inscripcion nuevaInscripcion = new Inscripcion(
                    cursoAAgregar.get().getId());
            nuevaInscripcion.getInscriptos().add(usuarioAAgregar.get());
        }*/
    }
}
