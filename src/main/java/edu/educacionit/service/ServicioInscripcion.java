package edu.educacionit.service;

import edu.educacionit.errores.CursoNotFoundException;
import edu.educacionit.errores.UsuarioNotFoundException;
import edu.educacionit.model.Curso;
import edu.educacionit.model.Inscripcion;
import edu.educacionit.model.Usuario;
import edu.educacionit.repository.RepositoryCurso;
import edu.educacionit.repository.RepositoryInscripcion;
import edu.educacionit.repository.RepositoryUsuario;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;

public class ServicioInscripcion {

    public List<Inscripcion> obtenerInscripciones(){
        return RepositoryInscripcion.inscripciones;
    }

    /*
    public Optional<Inscripcion> obtenerInscripcion(String id){
        return RepositoryInscripcion.inscripciones
                .stream()
                .filter(inscripcion -> inscripcion.getCursoId().equalsIgnoreCase(id))
                .findFirst();
    }
    */

    
    public boolean borrarInscripcion(String cursoID, String userID){
        return false;
        /*
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
        */
    }
    

    public void agregarInscripcion(String cursoID, String userID){
        //Checkeo si esta en la lista de cursos
        
        System.out.println(cursoID);
        System.out.println(userID);
        
        Optional<String> cursoAAgregar = RepositoryCurso.cursos
                .stream()
                .filter(curso -> curso.getId().equalsIgnoreCase(cursoID))
                .findFirst()
                .map( curso -> {
                    System.out.println("Aca voy a mostrar el map de curso");
                    System.out.println(curso);
                    return curso.getId();
                });
        
        Optional<String> usuarioAAgregar = RepositoryUsuario.usuarios
                .stream()
                .filter(usuario -> usuario.getId().equalsIgnoreCase(userID))
                .findFirst()
                .map( usu -> {
                    System.out.println("Aca voy a poner el id del usuario");
                    System.out.println(usu);
                    return usu.getId();
                } );

        if (cursoAAgregar.isPresent() == false) {
            throw new CursoNotFoundException();
        }
        
        if (usuarioAAgregar.isPresent() == false) {
            throw new UsuarioNotFoundException();
        }
        
        Inscripcion nuevaInscripcion 
                = new Inscripcion(cursoAAgregar.get(), usuarioAAgregar.get());
        
        RepositoryInscripcion.inscripciones.add(nuevaInscripcion);
    }
}
