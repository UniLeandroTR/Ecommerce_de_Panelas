package leepans.dto.fundo;

import java.time.LocalDateTime;
import java.util.List;

import leepans.dto.material.MaterialResponseDTO;

public record FundoResponseDTO(Long id, Double peso, List<MaterialResponseDTO> materiais, Double espessura, Boolean isAntiaderente, LocalDateTime dataCadastro, Integer version) {
    
}
