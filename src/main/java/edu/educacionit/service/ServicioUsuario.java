package edu.educacionit.service;

import edu.educacionit.model.Usuario;
import edu.educacionit.repository.RepositoryUsuario;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ServicioUsuario {
    
    public List<Usuario> filtrarUsuarios(Map<String, String> map) {
        
        List<Usuario> retList= new ArrayList<>();
        
        RepositoryUsuario.usuarios.stream().filter(usu -> {
           if(map.containsKey("nombre")) {
               return(map.get("nombre").equals(usu.getNombre()));
           }
           return true;
        }).filter(usu -> {
            if(map.containsKey("apellido")) {
               return(map.get("apellido").equals(usu.getApellido()));
           }
           return true;
        }).filter(usu -> {
            if(map.containsKey("pais")) {
               return(map.get("pais").equals(usu.getPais()));
           }
           return true;
        }).forEachOrdered(usu -> retList.add(usu));
        return retList;
    }
    public Usuario generarUsuarioRandom() {
        com.github.javafaker.Faker fkr = 
                new com.github.javafaker.Faker();
        
        Usuario u = new Usuario();
        u.setId(UUID.randomUUID().toString());
        u.setNombre(fkr.address().firstName());
        u.setApellido(fkr.address().lastName());
        u.setPais(fkr.address().country());
        
        return u;
    }
    public void agregarUsuario() {
        Usuario u = generarUsuarioRandom();
        RepositoryUsuario.usuarios.add(u);
    }
    public void agregarUsuario(Usuario u) {
        RepositoryUsuario.usuarios.add(u);
    }
    public List<Usuario> obtenerUsuarios() {
        return RepositoryUsuario.usuarios;
    }
    public Optional<Usuario> obtenerUsuario(String id) {
        Optional<Usuario> optUsuario = RepositoryUsuario.usuarios
                .stream()
                .filter(usu -> usu.getId().equalsIgnoreCase(id))
                .findFirst();
        
        return optUsuario;
    }
    public void borrarUsuario(String id) {
        RepositoryUsuario.usuarios = RepositoryUsuario.usuarios
                .stream()
                .filter(usu -> usu.getId().equalsIgnoreCase(id) == false)
                .collect(Collectors.toList());
    }
}
