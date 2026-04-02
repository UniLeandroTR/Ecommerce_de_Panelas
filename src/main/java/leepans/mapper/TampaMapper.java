package leepans.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.TampaRequestDTO;
import leepans.dto.TampaResponseDTO;
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
        tampa.setMaterial(materialRepository.findById(dto.idMaterial()));
        tampa.setCor(corRepository.findById(dto.idCor()));
        tampa.setIsDePressao(dto.isDePressao());

        return tampa;
    }

    public TampaResponseDTO toResponseDTO (Tampa tampa){
        if(tampa == null) return null;

        return new TampaResponseDTO(
            tampa.getId(),
            tampa.getPeso(),
            tampa.getMaterial(),
            tampa.getCor(),
            tampa.getIsDePressao()
        );
    }
}
