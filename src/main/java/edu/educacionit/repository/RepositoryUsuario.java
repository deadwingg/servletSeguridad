package edu.educacionit.repository;

import edu.educacionit.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class RepositoryUsuario {
    public static List<Usuario> usuarios;
    static 
    {
        usuarios = new ArrayList<Usuario>();
    }
}
