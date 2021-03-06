package edu.educacionit.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import edu.educacionit.errores.CursoNotFoundException;
import edu.educacionit.errores.UsuarioNotFoundException;
import edu.educacionit.service.ServicioInscripcion;
import edu.educacionit.utiles.ParDeIds;
import edu.educacionit.utiles.UriParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.Optional;

public class InscripcionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServicioInscripcion serv = new ServicioInscripcion();
        PrintWriter out = resp.getWriter();
        String data = new Gson().toJson(serv.obtenerInscripciones());
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        out.println(data);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServicioInscripcion serv = new ServicioInscripcion();
        PrintWriter out = resp.getWriter();
        try {
            ParDeIds i = transformarDesdeJSON(req.getInputStream());
            System.out.println(i);
            serv.agregarInscripcion(i.getCurso(), i.getUsuario());
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } 
        catch (JsonSyntaxException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("No se pudo leer el JSON: " + e.getLocalizedMessage());
        } 
        catch (CursoNotFoundException cnfe) {
            out.println("El curso no se encontro");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (UsuarioNotFoundException unfe) {
            out.println("El usuario no se encontro");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (IOException e){
            throw new ServletException();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServicioInscripcion serv = new ServicioInscripcion();
        PrintWriter out = resp.getWriter();
        UriParser parser = new UriParser(req.getRequestURI(), req.getQueryString());
        if (!parser.validarQueTengaId()){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Url must end in /UUIDInscripcion");
            return;
        }

        String idInscripcion = parser.obtenerId();

        if (serv.borrarInscripcion(idInscripcion)){
            resp.setStatus(HttpServletResponse.SC_OK);
            out.println("Se borro la inscripcion: " + idInscripcion);
            return;
        } else {
            throw new ServletException();
        }
    }

    private ParDeIds transformarDesdeJSON(InputStream in) throws IOException, JsonSyntaxException{

        StringBuilder todoElJSON = new StringBuilder();

        BufferedReader lxl = new BufferedReader(new InputStreamReader(in));
        for (String s = lxl.readLine(); s != null; s = lxl.readLine()){
            todoElJSON.append(s);
        }
        ParDeIds nuevaInscripcion = new Gson().fromJson(todoElJSON.toString(), ParDeIds.class);
        if (nuevaInscripcion.getCurso().length() != 36 || nuevaInscripcion.getUsuario().length() != 36) {
            throw new JsonSyntaxException("Los datos ingresados no son id validas");
        }
        return nuevaInscripcion;
    }
}
