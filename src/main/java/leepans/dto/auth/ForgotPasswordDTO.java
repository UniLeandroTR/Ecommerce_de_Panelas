package leepans.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordDTO(
    @NotBlank(message = "O login é obrigatório")
    String login,
    @NotBlank(message = "A senha atual é obrigatória")
    String senhaAtual
) {
    
}
