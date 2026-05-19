package leepans.dto.cor;

import java.time.LocalDateTime;

public record CorResponseDTO(Long id, String nome, LocalDateTime dataCadastro, Integer version) {
    
}
