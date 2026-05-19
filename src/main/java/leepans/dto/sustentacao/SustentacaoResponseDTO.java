package leepans.dto.sustentacao;

import java.time.LocalDateTime;
import java.util.List;

import leepans.dto.material.MaterialResponseDTO;
import leepans.model.TipoSustentacao;

public record SustentacaoResponseDTO(Long id, Double peso, List<MaterialResponseDTO> materiais, Integer tamanhoEmCm, Integer quantidade, TipoSustentacao tipoSustentacao, LocalDateTime dataCadastro, Integer version) {
    
}
