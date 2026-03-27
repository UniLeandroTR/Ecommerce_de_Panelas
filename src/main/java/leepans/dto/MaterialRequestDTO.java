package leepans.dto;

import java.util.List;

public record MaterialRequestDTO(Long id, String nome, List<String> qualidades) {
    
}
