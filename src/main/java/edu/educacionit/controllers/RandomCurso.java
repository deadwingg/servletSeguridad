/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.educacionit.controllers;

import com.google.gson.Gson;
import edu.educacionit.service.ServicioCurso;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author EducaciónIT
 */
public class RandomCurso extends HttpServlet {
@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        
        ServicioCurso serv = new ServicioCurso();
        String rsp = new Gson().toJson(serv.generarCursoRandom());
        resp.setStatus(HttpServletResponse.SC_OK);
        
        
        PrintWriter out = resp.getWriter();
        out.println(rsp);
    }
}