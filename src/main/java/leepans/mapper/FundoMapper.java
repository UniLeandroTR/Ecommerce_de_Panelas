package leepans.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.FundoRequestDTO;
import leepans.dto.FundoResponseDTO;
import leepans.model.Fundo;
import leepans.repository.CorRepository;
import leepans.repository.MaterialRepository;

@ApplicationScoped
public class FundoMapper {
    
    @Inject
    MaterialRepository materialRepository;

    @Inject
    CorRepository corRepository;

    public Fundo toEntity (FundoRequestDTO dto){
        if(dto == null) return null;

        Fundo fundo = new Fundo();
        fundo.setPeso(dto.peso());
        fundo.setMaterial(materialRepository.findById(dto.idMaterial()));
        fundo.setCor(corRepository.findById(dto.idCor()));
        fundo.setEspessura(dto.espessura());
        fundo.setIsAntiaderente(dto.isAntiaderente());

        return fundo;
    }

    public FundoResponseDTO toResponseDTO (Fundo fundo){
        if(fundo == null) return null;

        return new FundoResponseDTO(
            fundo.getId(),
            fundo.getPeso(),
            fundo.getMaterial(),
            fundo.getCor(),
            fundo.getEspessura(),
            fundo.getIsAntiaderente()
        );
    }
}
