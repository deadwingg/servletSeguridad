package edu.educacionit.utiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class InvalidFormatException extends RuntimeException {}

class Usuario {
    public String id;
    public String nombre;
    public String apellido;
}

public class UriParser {
    private String uri;
    private String queryString;

    public UriParser(String uri, String queryString) {
        this.uri = uri;
        this.queryString = queryString;
    }
    
    public boolean validarQueTengaId() {
        String[] arrStrings = uri.split("/");
        if (arrStrings.length == 0) {
            return false;
        }
        String s = arrStrings[arrStrings.length-1];
        
        if (s.equals("usuario")) {
            return false;
        }
                
        if (s.length() != 36) {
            return false;
        }
        return true;
    }
    public String obtenerId() {
        String[] arrStrings = uri.split("/");
        return arrStrings[arrStrings.length-1];
    }
    public Map<String, String> generarMapaQueryString() {
        HashMap<String, String> retMap = new HashMap<String, String>();
        String[] keyValueArr = queryString.split("&");
        if (keyValueArr.length == 0) {
            return retMap;
        }
        for (String kv : keyValueArr) {
            String[] arrKv = kv.split("=");
            if (arrKv.length != 2) {
                continue;
            }
            if (arrKv[1].isEmpty()) {
                continue;
            }
            retMap.put(arrKv[0], arrKv[1]);
        }
        return retMap;
    }
    public void parseUri() {   
        if (validarQueTengaId()) {
            // filtrar derecho por id y si no se encuentra 404
        }
        
        // si viene en la uri detras de usuario un id ?
        // entonces la busqueda se realiza por id y los resultados
        // posibles son:
        // 200 con el usuario
        // 404 sin respuesta
        // retrurn
        
        // si viene un queryString tengo que analizar
        // que variables vienen y filtrar por esas variables
        /*
        usuarios
                .Stream()
                .filter(usu -> {
                    if (nombre != null) && (!nombre.isEmpty())
                    usu.getNombre().equals(nombre)
                    else 
                    return true;
                })
                .filter(usu -> {
                    if (apellido != null) && (!apellido.isEmpty())
                    usu.getApellido().equals(apellido)
                    else 
                    return true;
                })
                .collect(Collectors.ToList());
        */
        // status 200;
        // voy a responder los usuarios filtrados.
    }
    
    public static void main(String[] args) {
        
        UriParser up = new UriParser("/curso-java-web/usuario/222", 
                "nombre=loquese&apellido=");
        
        try {
            up.validarQueTengaId();
            up.obtenerId();
            return;
        }
        catch (Exception ex) {
            System.out.println("Hay que sacarlo con 404");
            // hay que sacarlo con 404
        }
        
        Map<String, String> mapaQS = up.generarMapaQueryString();
        for (String s : mapaQS.keySet()) {
            System.out.println(s);
            System.out.println(mapaQS.get(s));
        }
        
        List<Usuario> listUsuario = new ArrayList<Usuario>();
        
        listUsuario
                .stream()
                .filter(usu -> {
                    if (mapaQS.get("nombre") == null) {
                        return true;
                    }
                    return usu.nombre.equals(mapaQS.get("nombre"));
                })
                .filter(usu -> {
                    if (mapaQS.get("apellido") == null) {
                        return true;
                    }
                    return usu.nombre.equals(mapaQS.get("apellido"));
                })
                .collect(Collectors.toList());
        
        System.out.println("Hasta aca esta bien");
        
    }
}
