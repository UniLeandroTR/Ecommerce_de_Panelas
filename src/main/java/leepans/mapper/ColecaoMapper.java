package leepans.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import leepans.dto.colecao.ColecaoEcommerceDTO;
import leepans.dto.colecao.ColecaoRequestDTO;
import leepans.dto.colecao.ColecaoResponseDTO;
import leepans.model.Colecao;

@ApplicationScoped
public class ColecaoMapper {

    public Colecao toEntity(ColecaoRequestDTO dto) {
        if (dto == null)
            return null;

        Colecao colecao = new Colecao();
        colecao.setNome(dto.nome());
        if(dto.version() != null){
            colecao.setVersion(dto.version());
        }

        return colecao;
    }

    public ColecaoResponseDTO toResponseDTO(Colecao colecao) {
        if (colecao == null)
            return null;

        return new ColecaoResponseDTO(
            colecao.getId(),
            colecao.getNome(),
            colecao.getDataCadastro(),
            colecao.getVersion());
    }

    public ColecaoEcommerceDTO toEcommerceDTO(Colecao colecao) {
        if (colecao == null) return null;

        return new ColecaoEcommerceDTO(
                colecao.getId(),
                colecao.getNome()
        );
    }
}
