package edu.educacionit.controllers;

import edu.educacionit.components.AuthorizationUtil;
import edu.educacionit.repository.RepositoryAuth;
import edu.educacionit.utiles.JWTUtil;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Auth extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authHeaderValue = req.getHeader("authorization");
        String header = "authorization : " + authHeaderValue;
        AuthorizationUtil autil = new AuthorizationUtil(new RepositoryAuth());
        if (autil.isAuthBasic(header)) {
            if (autil.validarUsuarioPassword(header)) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter()
                        .println(new JWTUtil().crearJwt(autil.getUsername(header)));
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return;
        // ir contra la base de datos para ver si el usuario y password es correcto.
        // si es correcto generar un token
        // retornar 200
        // retorn el JWT
    }
}
