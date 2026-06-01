package leepans.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.tampa.TampaEcommerceDTO;
import leepans.dto.tampa.TampaRequestDTO;
import leepans.dto.tampa.TampaResponseDTO;
import leepans.model.Material;
import leepans.model.Tampa;
import leepans.repository.MaterialRepository;

@ApplicationScoped
public class TampaMapper {
    
    @Inject
    MaterialRepository materialRepository;

    public Tampa toEntity (TampaRequestDTO dto){
        if(dto == null) return null;

        Tampa tampa = new Tampa();
        tampa.setPeso(dto.peso());
        List<Material> materiais = dto.idsMateriais() == null ? null : dto.idsMateriais().stream()
                .map(materialRepository::findById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        tampa.setMateriais(materiais);
        tampa.setIsDePressao(dto.isDePressao());
        if(dto.version() != null){
            tampa.setVersion(dto.version());
        }

        return tampa;
    }

    public TampaResponseDTO toResponseDTO (Tampa tampa){
        if(tampa == null) return null;

        return new TampaResponseDTO(
            tampa.getId(),
            tampa.getPeso(),
            MaterialMapper.toResponseDTO(tampa.getMateriais()),
            tampa.getIsDePressao(),
            tampa.getDataCadastro(),
            tampa.getVersion()
        );
    }

    public TampaEcommerceDTO toEcommerceDTO(Tampa tampa) {
        if (tampa == null) return null;

        return new TampaEcommerceDTO(
            tampa.getId(),
            MaterialMapper.toEcommerceDTO(tampa.getMateriais()),
            tampa.getIsDePressao()
         );
    }
}
