package leepans.dto.material;

import java.time.LocalDateTime;
import java.util.List;

public record MaterialResponseDTO(Long id, String nome, List<String> qualidades, LocalDateTime dataCadastro, Integer version) {
    
}
