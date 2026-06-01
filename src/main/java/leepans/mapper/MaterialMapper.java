package leepans.mapper;

import java.util.List;
import java.util.stream.Collectors;

import leepans.dto.material.MaterialEcommerceDTO;
import leepans.dto.material.MaterialRequestDTO;
import leepans.dto.material.MaterialResponseDTO;
import leepans.model.Material;

public class MaterialMapper {
    
    public static Material toEntity (MaterialRequestDTO dto){
        if(dto == null) return null;

        Material material = new Material();
        material.setNome(dto.nome());
        material.setQualidades(dto.qualidades());
        if(dto.version() != null){
            material.setVersion(dto.version());
        }
        
        return material;
    }

    public static MaterialResponseDTO toResponseDTO (Material material){
        if(material == null) return null;

        return new MaterialResponseDTO(
            material.getId(), 
            material.getNome(), 
            material.getQualidades(),
            material.getDataCadastro(),
            material.getVersion()
        );
    }

    public static List<MaterialResponseDTO> toResponseDTO(List<Material> materiais) {
        if (materiais == null) return null;
        return materiais.stream()
                .map(MaterialMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public static MaterialEcommerceDTO toEcommerceDTO(Material material) {
        if (material == null) return null;

        return new MaterialEcommerceDTO(
                material.getId(),
                material.getNome()
        );
    }

    public static List<MaterialEcommerceDTO> toEcommerceDTO(List<Material> materiais) {
        if (materiais == null || materiais.isEmpty()) return null;

        return materiais.stream()
            .map(MaterialMapper::toEcommerceDTO)
            .collect(Collectors.toList());
    }
}
