/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.educacionit.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import edu.educacionit.model.Curso;
import edu.educacionit.model.Usuario;
import edu.educacionit.service.ServicioCurso;
import edu.educacionit.service.ServicioUsuario;
import edu.educacionit.utiles.UriParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author EducaciónIT
 */
public class CursoServlet extends HttpServlet{
    
    private Curso transformarDesdeJson(InputStream is) throws IOException, JsonSyntaxException {
        
        BufferedReader lxl = 
                new BufferedReader(new InputStreamReader(is));
        
        StringBuilder todoElJson = new StringBuilder();
        for (String s = lxl.readLine(); s != null; s = lxl.readLine()) {
            todoElJson.append(s);
        }
        
        Curso curs = new Gson().fromJson(todoElJson.toString(), Curso.class);
        return curs;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        
        ServicioCurso serv = new ServicioCurso();
        
        String uri = req.getRequestURI();
        String qs = req.getQueryString();
        
        resp.setHeader("X-MIHEADER", "EN LA PROXIMA VAS A NECESITAR AUTENTICACION");
        
        UriParser up = new UriParser(uri, qs);
        if (up.validarQueTengaId()) {
            String rsp = "";
            Optional<Curso> optCurs = serv.obtenerCurso(up.obtenerId());
            if (optCurs.isPresent()) {
                rsp = new Gson().toJson(optCurs.get());
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
            PrintWriter out = resp.getWriter();
            out.println(rsp);
            return;
        }
        
        Map<String, String> mapaParams = new HashMap<String, String>();
        
        Enumeration<String> params = req.getParameterNames();
        while (params.hasMoreElements()) {
            String keyParam = params.nextElement();
            String valParam = req.getParameter(keyParam);
            mapaParams.put(keyParam, valParam);
        }
        
        List<Curso> lstCurs = serv.filtrarCursos(mapaParams);
        String rsp = new Gson().toJson(lstCurs);
        
        PrintWriter out = resp.getWriter();
        out.println(rsp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServicioCurso serv = new ServicioCurso();
        
        try {
            Curso curs = transformarDesdeJson(req.getInputStream());
            resp
            .getWriter()
            .println(String.valueOf(curs));
            serv.agregarCurso(curs);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        }
        catch (JsonSyntaxException jse) {
            resp
            .getWriter()
            .println(String.join("No se pudo leer json"));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        catch (IOException ioe) {
            throw new ServletException();
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String qs = req.getQueryString();
        ServicioCurso serv = new ServicioCurso();
        
        UriParser up = new UriParser(uri, qs);
        if (!up.validarQueTengaId()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        try {
            Curso curs = transformarDesdeJson(req.getInputStream());
                    
            Optional<Curso> optCurs;
            optCurs = serv.obtenerCurso(up.obtenerId());
            if (!optCurs.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            optCurs.get().setNombre(curs.getNombre());
        }
        catch (JsonSyntaxException jse) {
            resp
            .getWriter()
            .println(String.join("No se pudo leer json"));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        catch (IOException ioe) {
            throw new ServletException();
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String qs = req.getQueryString();
        
        UriParser up = new UriParser(uri, qs);
        if (!up.validarQueTengaId()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        resp
            .getWriter()
            .println(String.join(" ", "Se borro el curso con id", up.obtenerId()));
        
        ServicioCurso serv = new ServicioCurso();
        serv.borrarCurso(up.obtenerId());
    }
    
    
}
