package leepans.dto.categoria;

import java.time.LocalDateTime;

public record CategoriaResponseDTO(Long id, String tipo, LocalDateTime dataCadastro, Integer version) {
}
