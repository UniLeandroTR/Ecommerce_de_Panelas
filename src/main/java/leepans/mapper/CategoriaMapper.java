package leepans.mapper;

import leepans.dto.categoria.CategoriaRequestDTO;
import leepans.dto.categoria.CategoriaResponseDTO;
import leepans.model.Categoria;

public class CategoriaMapper {

    public static Categoria toEntity(CategoriaRequestDTO dto){
        if(dto==null) return null;

        Categoria categoria = new Categoria();
        categoria.setTipo(dto.tipo());
        if(dto.version() != null){
            categoria.setVersion(dto.version());
        }
        return categoria;
    }

    public static CategoriaResponseDTO toResponse(Categoria categoria){
        if(categoria==null) return null;

        return new CategoriaResponseDTO(
          categoria.getId(),
          categoria.getTipo(),
          categoria.getDataCadastro(),
          categoria.getVersion()
        );
    }
}
