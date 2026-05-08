package leepans.service.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Servico utilitario para gerar hash criptografico SHA-256.
 */
@ApplicationScoped
public class HashService {


    /**
     * Gera o hash SHA-256 de uma String e retorna o resultado em hexadecimal.
     */
    public String sha256(String valor) {
        if (valor == null) {
            throw new IllegalArgumentException("O valor para hash nao pode ser nulo");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Usa UTF-8 para garantir resultado consistente em qualquer ambiente.
            byte[] hashBytes = digest.digest(valor.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(hashBytes.length * 2);

            // Cada byte vira dois caracteres hexadecimais (00 a ff).
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algoritmo SHA-256 indisponivel", e);
        }
    }

    /**
     * Gera o hash BCrypt de uma String usando salt aleatorio.
     */
    public String bcrypt(String valor) {
        if (valor == null) {
            throw new IllegalArgumentException("O valor para hash nao pode ser nulo");
        }

        return BCrypt.hashpw(valor, BCrypt.gensalt());
    }

    /**
     * Verifica se uma String corresponde a um hash BCrypt previamente gerado.
     *
     * @param valor  texto original (ex: senha digitada pelo usuario)
     * @param hash   hash BCrypt armazenado (gerado anteriormente pelo metodo bcrypt())
     * @return true se o valor bate com o hash, false caso contrario
     */
    public boolean verificarBcrypt(String valor, String hash) {
        if (valor == null || hash == null) {
            throw new IllegalArgumentException("Valor e hash nao podem ser nulos");
        }

        // BCrypt.checkpw recalcula o hash internamente usando o salt embutido
        // no proprio hash e compara com o hash fornecido.
        return BCrypt.checkpw(valor, hash);
    }
}
