package leepans.dto.colecao;

import java.time.LocalDateTime;

public record ColecaoResponseDTO(
    Long id,
    String nome,
    LocalDateTime dataCadastro,
    Integer version
) {
    
}
