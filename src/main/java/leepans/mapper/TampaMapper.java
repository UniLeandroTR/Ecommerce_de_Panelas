package leepans.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import leepans.dto.tampa.TampaRequestDTO;
import leepans.dto.tampa.TampaResponseDTO;
import leepans.model.Material;
import leepans.model.Tampa;
import leepans.repository.CorRepository;
import leepans.repository.MaterialRepository;

@ApplicationScoped
public class TampaMapper {
    
    @Inject
    MaterialRepository materialRepository;

    @Inject
    CorRepository corRepository;

    public Tampa toEntity (TampaRequestDTO dto){
        if(dto == null) return null;

        Tampa tampa = new Tampa();
        tampa.setPeso(dto.peso());
        List<Material> materiais = dto.idsMateriais() == null ? null : dto.idsMateriais().stream()
                .map(materialRepository::findById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        tampa.setMateriais(materiais);
        tampa.setCor(corRepository.findById(dto.idCor()));
        tampa.setIsDePressao(dto.isDePressao());

        return tampa;
    }

    public TampaResponseDTO toResponseDTO (Tampa tampa){
        if(tampa == null) return null;

        return new TampaResponseDTO(
            tampa.getId(),
            tampa.getPeso(),
            MaterialMapper.toResponseDTO(tampa.getMateriais()),
            CorMapper.toResponseDTO(tampa.getCor()),
            tampa.getIsDePressao()
        );
    }
}
