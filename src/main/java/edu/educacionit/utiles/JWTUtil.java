package edu.educacionit.utiles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.educacionit.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;

public class JWTUtil {
    private Long obtenerTimeStampActual() {
        return System.currentTimeMillis() / 1000;
    }
    public String crearJwt(String usuario) {
        
        byte[] firma = new byte[] {
            (byte)214,(byte)316,
            (byte)22,(byte)66,(byte)54,(byte)32,(byte)32,(byte)21,
            (byte)44,(byte)55,(byte)65,(byte)43,(byte)23,(byte)56,(byte)98,(byte)76,
            (byte)32,(byte)223,(byte)54,(byte)32,(byte)32,(byte)15,(byte)11
        };
        
        Long emision = obtenerTimeStampActual();
        Long expiracion = obtenerTimeStampActual() + 120;
        
        String jwt = Jwts
                .builder()
                .signWith(SignatureAlgorithm.HS256, firma)
                .setHeaderParam("type", "jwt")
                .setHeaderParam("alg", "HS256")
                .setSubject(usuario)
                .claim("iat", String.valueOf(emision))
                .claim("exp", String.valueOf(expiracion))
                .claim("usuario", usuario)
                .claim("company", "educacacionit")
                .claim("idCliente", usuario)
                .compact();
        
        return jwt;
    }
    private Object obtenerKey(String token, String key) {
        String[] splittedToken = token.split("\\.");
        
        RuntimeException tokenNoValido = 
                new RuntimeException("El token no es valido");
        RuntimeException keyInvalida = 
                new RuntimeException(String.join(" ", "La key",key,"no es valida"));
        
        if (splittedToken.length != 3) {
            throw tokenNoValido;
        }
        
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        
        byte[] payload = Base64.decodeBase64(splittedToken[1]);
        try {
            String segundoCuerpo = new String(payload ,"UTF-8");
            System.out.println(segundoCuerpo);
            Map<String, Object> mapa = gson.fromJson(segundoCuerpo, Map.class);
            
            if (!mapa.containsKey(key)) {
                throw keyInvalida;
            }
            return mapa.get(key);
        }
        catch (Exception ex) {
            throw tokenNoValido;
        }
    }
    public String obtenerIdCliente(String token) {
        return obtenerKey(token, "idCliente").toString();
    }
    public boolean verificarSiEstaVencido(String token) {
        Long tsExp = Long.parseLong(obtenerKey(token, "exp").toString());
        System.out.println(tsExp);
        Long tsActual = obtenerTimeStampActual();
        
        return (tsActual < tsExp);
    }
}
