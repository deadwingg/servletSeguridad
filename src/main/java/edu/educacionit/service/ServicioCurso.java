/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.educacionit.service;

import edu.educacionit.model.Curso;
import edu.educacionit.repository.RepositoryCurso;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author EducaciónIT
 */
public class ServicioCurso {
    
    public List<Curso> filtrarCursos(Map<String, String> map) {
        
        List<Curso> retList= new ArrayList<>();
        
        RepositoryCurso.cursos.stream().filter(curs -> {
           if(map.containsKey("nombre")) {
               return(map.get("nombre").equals(curs.getNombre()));
           }
           return true;
        }).forEachOrdered(curs -> retList.add(curs));
        return retList;
    }
    
    public Curso generarCursoRandom() {
        com.github.javafaker.Faker fkr = 
                new com.github.javafaker.Faker();
        
        Curso curs = new Curso();
        curs.setId(UUID.randomUUID().toString());
        curs.setNombre(fkr.job().title());
        return curs;
    }
    public void agregarCurso() {
        Curso curs = generarCursoRandom();
        RepositoryCurso.cursos.add(curs);
    }
    public void agregarCurso(Curso curs) {
        RepositoryCurso.cursos.add(curs);
    }
    public List<Curso> obtenerCursos() {
        return RepositoryCurso.cursos;
    }
    public Optional<Curso> obtenerCurso(String id) {
        Optional<Curso> optUsuario = RepositoryCurso.cursos
                .stream()
                .filter(curs -> curs.getId().equalsIgnoreCase(id))
                .findFirst();
        
        return optUsuario;
    }
    public void borrarCurso(String id) {
        RepositoryCurso.cursos = RepositoryCurso.cursos
                .stream()
                .filter(usu -> usu.getId().equalsIgnoreCase(id) == false)
                .collect(Collectors.toList());
    }
}
