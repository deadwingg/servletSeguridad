package edu.educacionit.repository;

import edu.educacionit.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class RepositoryUsuario {
    public static List<Usuario> usuarios;
    static 
    {
        usuarios = new ArrayList<Usuario>();
        usuarios.add(
                new Usuario("2e8cc669-4adc-4dc0-ac3b-aabeb7daf149",
                        "Ivan", "Gomez", "Argentina"));
        usuarios.add(
                new Usuario("e29a6535-dad2-47b7-bf20-a6743a0377b4",
                        "Paola", "Espinosa", "Argentina"));
    }
}
