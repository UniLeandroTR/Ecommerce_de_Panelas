package leepans.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import leepans.dto.fundo.FundoRequestDTO;
import leepans.dto.fundo.FundoResponseDTO;
import leepans.model.Fundo;
import leepans.model.Material;
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
        List<Material> materiais = dto.idsMateriais() == null ? null : dto.idsMateriais().stream()
                .map(materialRepository::findById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        fundo.setMateriais(materiais);
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
            MaterialMapper.toResponseDTO(fundo.getMateriais()),
            CorMapper.toResponseDTO(fundo.getCor()),
            fundo.getEspessura(),
            fundo.getIsAntiaderente()
        );
    }
}
