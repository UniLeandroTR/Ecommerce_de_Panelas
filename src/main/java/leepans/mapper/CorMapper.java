package leepans.mapper;

import leepans.dto.cor.CorRequestDTO;
import leepans.dto.cor.CorResponseDTO;
import leepans.model.Cor;

public class CorMapper {
    
    public static Cor toEntity (CorRequestDTO dto){
        if(dto == null) return null;

        Cor cor = new Cor();
        cor.setNome(dto.nome());
        return cor;
    }

    public static CorResponseDTO toResponseDTO (Cor cor){
        if(cor == null) return null;

        return new CorResponseDTO(
            cor.getId(), 
            cor.getNome());
    }
}
