/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.educacionit.model;

/**
 *
 * @author EducaciónIT
 */
public class Curso {
    private String nombre;
    private String id;

    public Curso() {
    }
    
    public Curso(String name, String id) {
        this.nombre = name;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Curso{" + "nombre=" + nombre + ", id=" + id + '}';
    }
    
    
        
}
