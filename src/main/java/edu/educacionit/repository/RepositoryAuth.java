package edu.educacionit.repository;

import edu.educacionit.utiles.JWTUtil;
import interfaces.AuthRepository;

public class RepositoryAuth implements AuthRepository {
    public boolean login(String usu, String pass){
        if (usu.equals("max") == false) {
            return false;
        }
        // System.out.println(passwordEncriptada); max33
        if (pass.equalsIgnoreCase("D4F372AE8B8B32A959A788B0216FA210670937E30882615998C94F56F6A2CECE") == false) {
            return false;
        }
        return true;
    }

    @Override
    public boolean verificarToken(String token, String idCliente) {
        return true;
    }
}
