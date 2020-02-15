package edu.educacionit.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import edu.educacionit.model.Usuario;
import edu.educacionit.service.ServicioUsuario;
import edu.educacionit.utiles.UriParser;
import java.io.BufferedReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UsuarioServlet extends HttpServlet {
    
    private Usuario transformarDesdeJson(InputStream is) throws IOException, JsonSyntaxException {
        
        BufferedReader lxl = 
                new BufferedReader(new InputStreamReader(is));
        
        StringBuilder todoElJson = new StringBuilder();
        for (String s = lxl.readLine(); s != null; s = lxl.readLine()) {
            todoElJson.append(s);
        }
        
        Usuario u = new Gson().fromJson(todoElJson.toString(), Usuario.class);
        return u;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        
        ServicioUsuario serv = new ServicioUsuario();
        
        String uri = req.getRequestURI();
        String qs = req.getQueryString();
        
        resp.setHeader("X-MIHEADER", "EN LA PROXIMA VAS A NECESITAR AUTENTICACION");
        
        UriParser up = new UriParser(uri, qs);
        if (up.validarQueTengaId()) {
            String rsp = "";
            Optional<Usuario> optUsu = serv.obtenerUsuario(up.obtenerId());
            if (optUsu.isPresent()) {
                rsp = new Gson().toJson(optUsu.get());
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
        
        List<Usuario> lstUsu = serv.filtrarUsuarios(mapaParams);
        String rsp = new Gson().toJson(lstUsu);
        
        PrintWriter out = resp.getWriter();
        out.println(rsp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServicioUsuario serv = new ServicioUsuario();
        
        try {
            Usuario u = transformarDesdeJson(req.getInputStream());
            serv.agregarUsuario(u);
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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String qs = req.getQueryString();
        ServicioUsuario serv = new ServicioUsuario();
        
        UriParser up = new UriParser(uri, qs);
        if (!up.validarQueTengaId()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        try {
            Usuario u = transformarDesdeJson(req.getInputStream());
                    
            Optional<Usuario> optusuario;
            optusuario = serv.obtenerUsuario(up.obtenerId());
            if (!optusuario.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            optusuario.get().setNombre(u.getNombre());
            optusuario.get().setApellido(u.getApellido());
            optusuario.get().setPais(u.getPais());
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
            .println(String.join(" ", "Se borro el usuario con id", up.obtenerId()));
        
        ServicioUsuario serv = new ServicioUsuario();
        serv.borrarUsuario(up.obtenerId());
    }
}
