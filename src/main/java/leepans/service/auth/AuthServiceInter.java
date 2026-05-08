package leepans.service.auth;

import leepans.dto.auth.AuthRequestDTO;
import leepans.dto.auth.AuthResponseDTO;

public interface AuthServiceInter {

    /**
     * Autentica o usuario e retorna um token JWT em caso de sucesso.
     */
    AuthResponseDTO login(AuthRequestDTO dto);
}
