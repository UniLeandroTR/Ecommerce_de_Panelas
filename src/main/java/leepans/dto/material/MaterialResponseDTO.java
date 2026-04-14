package leepans.dto.material;

import java.util.List;

public record MaterialResponseDTO(Long id, String nome, List<String> qualidades) {
    
}
