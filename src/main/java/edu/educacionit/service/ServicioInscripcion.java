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
        return RepositoryInscripcion.inscripciones
                .removeIf(inscripcion ->
                        inscripcion.usuarioId.equalsIgnoreCase(userID)
                            &&
                            inscripcion.cursoId.equalsIgnoreCase(cursoID));
    }
    

    public void agregarInscripcion(String cursoID, String userID){
        //Checkeo si esta en la lista de cursos
        Optional<String> cursoAAgregar = RepositoryCurso.cursos
                .stream()
                .filter(curso -> curso.getId().equalsIgnoreCase(cursoID))
                .findFirst()
                .map(Curso::getId);
        Optional<String> usuarioAAgregar = RepositoryUsuario.usuarios
                .stream()
                .filter(usuario -> usuario.getId().equalsIgnoreCase(userID))
                .findFirst()
                .map(Usuario::getId);

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
