package edu.educacionit.components;

import edu.educacionit.utiles.JWTUtil;
import interfaces.AuthRepository;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class AuthorizationUtil {
    private AuthRepository authRepository;

    public AuthorizationUtil(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }    
    private String desglosarAuthorization(String header) {
        if (header == null) {
            throw new RuntimeException("header esta en null");
        }
        String[] arrKV = header.split(":");
        if (arrKV.length != 2) {
            throw new RuntimeException("no estan presentes key value");
        }
        return arrKV[1].trim();
    }
    private String[] obtenerUsuarioYPassword(String basicAuth) {
        String usuPassEnBase64 = basicAuth.replace("Basic", "").trim();
        String usuPass = new String(Base64.decodeBase64(usuPassEnBase64));
        String[] arrUsuPass = usuPass.split(":");
        if (arrUsuPass.length != 2) {
            throw new RuntimeException("Debe venir usuario:password");
        }
        return arrUsuPass;
    }
    public boolean isAuthBasic(String header) {
        try {
            String rsp = desglosarAuthorization(header);
            if (rsp.indexOf("Basic") >= 0) {
                return true;
            }
        }
        catch (Exception ex) {
        }
        return false;
    }
    public boolean validarUsuarioPassword(String header) {
        try {
            String basicAuth = desglosarAuthorization(header);
            String[] usuPass = obtenerUsuarioYPassword(basicAuth);
            return authRepository.login(usuPass[0], DigestUtils.sha256Hex(usuPass[1]));
        }
        catch (Exception ex) {
            
        }
        return false;
    }
    public boolean isAuthToken(String header) {
        try {
            String bearerToken = desglosarAuthorization(header);
            String token = bearerToken.replace("Bearer", bearerToken).trim();
            return ((token.split(".").length == 3));
        }
        catch (Exception ex) {}
        return false;
    }
    // Creo que va a ser desde el punto de vista interno
    public boolean isTokenValid(String header) {
        try {
            String bearerToken = desglosarAuthorization(header);
            String token = bearerToken.replace("Bearer", bearerToken).trim();
            if (new JWTUtil().verificarSiEstaVencido(token)) {
                return false;
            }
            String idCliente = new JWTUtil().obtenerIdCliente(token);
            return authRepository.verificarToken(token, idCliente);
        }
        catch (Exception ex) {}
        return false;
    }

    public String getUsername(String header) {
        if (isAuthBasic(header)) {
            String basicAuth = desglosarAuthorization(header);
            String[] usuPass = obtenerUsuarioYPassword(basicAuth);
            return usuPass[1];
        }
        return null;
    }
}
