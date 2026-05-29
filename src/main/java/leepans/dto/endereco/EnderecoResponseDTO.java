package leepans.dto.endereco;

import java.time.LocalDateTime;

public record EnderecoResponseDTO(
        Long id,
        String rua,
        String numero,
        String cidade,
        String estado,
        String cep,
        LocalDateTime dataCadastro,
        Integer version
) {
}
