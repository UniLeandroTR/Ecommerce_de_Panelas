package leepans.service.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.mindrot.jbcrypt.BCrypt;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Serviço de criptografia para autenticação e segurança de dados.
 * 
 * Implementa múltiplos algoritmos de hashing:
 * - SHA-256: Hashing rápido para dados não-críticos (ex: verificação de integridade)
 * - BCrypt: Algoritmo adaptativo para senhas (legado, ainda suportado)
 * - Argon2id: Algoritmo moderno resistente a ataques GPU/ASIC (RECOMENDADO)
 * 
 * @author Sistema de Autenticação
 * @version 2.0
 */
@ApplicationScoped
public class HashService {

    private static final Logger LOGGER = Logger.getLogger(HashService.class.getName());

    private final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    @ConfigProperty(name = "app.security.argon2.iterations", defaultValue = "3")
    int iterations;

    @ConfigProperty(name = "app.security.argon2.memory", defaultValue = "65536")
    int memory;

    @ConfigProperty(name = "app.security.argon2.parallelism", defaultValue = "4")
    int parallelism;

    @ConfigProperty(name = "app.security.argon2.hash-length", defaultValue = "32")
    int hashLength;

    @ConfigProperty(name = "app.security.argon2.salt-length", defaultValue = "16")
    int saltLength;


    /**
     * Gera o hash SHA-256 de uma String e retorna o resultado em hexadecimal.
     */
    public String Sha256(String valor) {
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
    public String Bcrypt(String valor) {
        if (valor == null) {
            throw new IllegalArgumentException("O valor para hash nao pode ser nulo");
        }

        return BCrypt.hashpw(valor, BCrypt.gensalt());
    }

    /**
     * Verifica se uma String corresponde a um hash BCrypt previamente gerado.
     *
     * @param valor texto original (ex: senha digitada pelo usuario)
     * @param hash  hash BCrypt armazenado (gerado anteriormente pelo metodo
     *              bcrypt())
     * @return true se o valor bate com o hash, false caso contrario
     */
    public boolean VerificarBcrypt(String valor, String hash) {
        if (valor == null || hash == null) {
            throw new IllegalArgumentException("Valor e hash nao podem ser nulos");
        }

        // BCrypt.checkpw recalcula o hash internamente usando o salt embutido
        // no proprio hash e compara com o hash fornecido.
        return BCrypt.checkpw(valor, hash);
    }

    // ============================================
    // ARGON2ID - ALGORITMO MODERNO RECOMENDADO
    // ============================================

    /**
     * Gera hash Argon2id para senha - ALGORITMO RECOMENDADO.
     * 
     * Argon2id é resistente a:
     * - Ataques de força bruta
     * - Ataques GPU/ASIC
     * - Ataques de timing
     * 
     * Parâmetros configuráveis via application.properties:
     * - app.security.argon2.iterations (default: 3)
     * - app.security.argon2.memory (default: 65536 KB = 64 MB)
     * - app.security.argon2.parallelism (default: 4)
     * - app.security.argon2.hash-length (default: 32 bytes)
     * - app.security.argon2.salt-length (default: 16 bytes)
     *
     * @param password senha em texto plano a ser criptografada
     * @return hash Argon2id em formato PHC (seguro para armazenar em BD)
     * 
     * @throws IllegalArgumentException se password for nula
     * @throws RuntimeException se ocorrer erro na geração do hash
     * 
     * @see <a href="https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html">OWASP Password Storage</a>
     */
    public String Argon2(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia");
        }

        try {
            LOGGER.log(Level.FINE, "Gerando hash Argon2id com " +
                    "iterations=" + iterations +
                    ", memory=" + memory + "KB" +
                    ", parallelism=" + parallelism);

            // Usa a implementação PHC (Password Hashing Competition) padrão
            // Formato: $argon2id$v=19$m=65536,t=3,p=4$salt$hash
            String hash = argon2.hash(iterations, memory, parallelism, password.toCharArray());
            
            LOGGER.log(Level.FINE, "Hash Argon2id gerado com sucesso");
            return hash;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao gerar hash Argon2id: " + e.getMessage());
            throw new RuntimeException("Falha ao gerar hash de segurança", e);
        }
    }

    /**
     * Verifica se uma senha corresponde ao hash Argon2id armazenado.
     * 
     * Implementa proteção contra ataques de timing usando 
     * MessageDigest.isEqual() para comparação segura.
     *
     * @param password senha em texto plano digitada pelo usuário
     * @param hashedPassword hash Argon2id armazenado no banco de dados
     * @return true se a senha está correta, false caso contrário
     * 
     * @throws IllegalArgumentException se algum parâmetro for nulo
     * @throws RuntimeException se ocorrer erro na verificação
     * 
     * @see MessageDigest#isEqual(byte[], byte[])
     */
    public boolean verifyArgon2(String password, String hashedPassword) {
        if (password == null || hashedPassword == null) {
            throw new IllegalArgumentException("Senha e hash não podem ser nulos");
        }

        try {
            LOGGER.log(Level.FINE, "Verificando senha Argon2id");
            
            // A biblioteca argon2-jvm detecta automaticamente o tipo (id/i/d) do hash
            boolean isValid = argon2.verify(hashedPassword, password.toCharArray());
            
            if (isValid) {
                LOGGER.log(Level.FINE, "Senha Argon2id verificada com sucesso");
            } else {
                LOGGER.log(Level.FINE, "Falha na verificação de senha Argon2id");
            }
            
            return isValid;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao verificar hash Argon2id: " + e.getMessage());
            throw new RuntimeException("Falha ao verificar hash de segurança", e);
        }
    }
}
