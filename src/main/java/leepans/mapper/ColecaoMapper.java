package leepans.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import leepans.dto.colecao.ColecaoRequestDTO;
import leepans.dto.colecao.ColecaoResponseDTO;
import leepans.model.Colecao;

@ApplicationScoped
public class ColecaoMapper {

    public Colecao toEntity(ColecaoRequestDTO dto){
        if(dto==null) return null;

        Colecao colecao = new Colecao();
        colecao.setNome(dto.nome());

        return colecao;
    }

    public ColecaoResponseDTO toResponseDTO (Colecao colecao){
        if(colecao==null) return null;

        return new ColecaoResponseDTO(
            colecao.getId(),
            colecao.getNome());
    }
}
