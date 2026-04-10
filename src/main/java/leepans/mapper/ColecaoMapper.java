package leepans.mapper;

import leepans.dto.ColecaoRequestDTO;
import leepans.dto.ColecaoResponseDTO;
import leepans.model.Colecao;

public class ColecaoMapper {
    
    public static Colecao toEntity(ColecaoRequestDTO dto){
        if(dto==null) return null;

        Colecao colecao = new Colecao();
        colecao.setNome(dto.nome());
        colecao.setPanelas(dto.panelas());

        return colecao;
    }

    public static ColecaoResponseDTO toResponseDTO (Colecao colecao){
        if(colecao==null) return null;

        return new ColecaoResponseDTO(
            colecao.getId(),
            colecao.getNome(),
            colecao.getPanelas());
    }
}
