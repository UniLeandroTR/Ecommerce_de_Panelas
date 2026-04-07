package leepans.mapper;

import leepans.dto.CategoriaRequestDTO;
import leepans.dto.CategoriaResponseDTO;
import leepans.model.Categoria;

public class CategoriaMapper {

    public static Categoria toEntity(CategoriaRequestDTO dto){
        if(dto==null) return null;

        Categoria categoria = new Categoria();
        categoria.setTipo(dto.tipo());
        return categoria;
    }

    public static CategoriaResponseDTO toResponse(Categoria categoria){
        if(categoria==null) return null;

        return new CategoriaResponseDTO(
          categoria.getId(),
          categoria.getTipo()
        );
    }
}
