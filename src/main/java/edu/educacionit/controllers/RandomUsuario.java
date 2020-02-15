package edu.educacionit.controllers;

import com.google.gson.Gson;
import edu.educacionit.service.ServicioUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RandomUsuario extends HttpServlet {
@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        
        ServicioUsuario serv = new ServicioUsuario();
        String rsp = new Gson().toJson(serv.generarUsuarioRandom());
        resp.setStatus(HttpServletResponse.SC_OK);
        
        
        PrintWriter out = resp.getWriter();
        out.println(rsp);
    }
}
