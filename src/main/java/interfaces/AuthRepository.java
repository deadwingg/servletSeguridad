package interfaces;

public interface AuthRepository {
    boolean login(String usu, String pass);
    boolean verificarToken(String token, String idCliente);
}
