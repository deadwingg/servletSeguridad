package edu.educacionit;

import edu.educacionit.components.AuthorizationUtil;
import edu.educacionit.utiles.JWTUtil;
import interfaces.AuthRepository;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

class StubAuthRepository implements AuthRepository {
    public boolean login(String usu, String pass) {
        if (usu.equals("max") == false) {
            return false;
        }
        
        System.out.println(pass);
        if (pass.equals("d4f372ae8b8b32a959a788b0216fa210670937e30882615998c94f56f6a2cece") == false) {
            return false;
        }
        return true;
    }

    public boolean verificarToken(String token, String idUsuario) {
        JWTUtil jwtUtil = new JWTUtil();
        String idCli = jwtUtil.obtenerIdCliente(token);
        return (idCli.equals(idUsuario));
    }
}


public class SecurityTest {
    
    public SecurityTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void verificarSiAutEsBasic() {
        String mensajeError = "La autorizacion es Basic con lo cual debe venir true"; 
        AuthorizationUtil autil = new AuthorizationUtil(new StubAuthRepository());
        Boolean b = autil.isAuthBasic("authorization  :  Basic bWF4Om1heDMz");
        Assert.assertEquals(mensajeError, true, b);
    }
    @Test
    public void validarUsuarioPassword() {
        String mensajeError = "La combinacion de usuario y password no es valida";
        AuthorizationUtil autil = new AuthorizationUtil(new StubAuthRepository());
        Boolean b = autil.validarUsuarioPassword("authorization  :  Basic bWF4Om1heDMz");
        Assert.assertEquals(mensajeError, true, b);
    }
    @Test
    public void validarUsuarioPasswordIncorrecta() {
        String mensajeError = "Deberia ser incorrecta !! Atencion";
        AuthorizationUtil autil = new AuthorizationUtil(new StubAuthRepository());
        Boolean b = autil.validarUsuarioPassword("authorization  :  Basic laPeppaPig");
        Assert.assertEquals(mensajeError, false, b);
    }
    @Test
    public void validarTokenActivo() {
        JWTUtil jwtUtil = new JWTUtil();
        String token = jwtUtil.crearJwt("123987");
        System.out.println("El token es: ");
        System.out.println(token);
        Assert.assertEquals("Ya esta vencido", true, jwtUtil.verificarSiEstaVencido(token));
    }
    @Test
    public void validarTokenDeUsuario() {
        JWTUtil jwtUtil = new JWTUtil();
        String token = jwtUtil.crearJwt("123987");
        AuthorizationUtil autil = new AuthorizationUtil(new StubAuthRepository());
        autil.isTokenValid("Bearer " + token);
        Assert.assertEquals("No coincide", "123987", jwtUtil.obtenerIdCliente(token));
    }
}
