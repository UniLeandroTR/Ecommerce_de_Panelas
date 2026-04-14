package leepans.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.SustentacaoRequestDTO;
import leepans.dto.SustentacaoResponseDTO;
import leepans.model.Sustentacao;
import leepans.repository.CorRepository;
import leepans.repository.MaterialRepository;

@ApplicationScoped
public class SustentacaoMapper {
    
    @Inject
    MaterialRepository materialRepository;

    @Inject
    CorRepository corRepository;

    public Sustentacao toEntity (SustentacaoRequestDTO dto){
        if(dto == null) return null;

        Sustentacao sustentacao = new Sustentacao();
        sustentacao.setPeso(dto.peso());
        sustentacao.setMaterial(materialRepository.findById(dto.idMaterial()));
        sustentacao.setCor(corRepository.findById(dto.idCor()));
        sustentacao.setTamanhoEmCm(dto.tamanhoEmCm());
        sustentacao.setQuantidade(dto.quantidade());
        sustentacao.setTipoSustentacao(dto.tipoSustentacao());

        return sustentacao;
    }

    public SustentacaoResponseDTO toResponseDTO (Sustentacao sustentacao){
        if(sustentacao == null) return null;

        return new SustentacaoResponseDTO(
            sustentacao.getId(),
            sustentacao.getPeso(),
            sustentacao.getMaterial().getNome(),
            sustentacao.getCor().getNome(),
            sustentacao.getTamanhoEmCm(),
            sustentacao.getQuantidade(),
            sustentacao.getTipoSustentacao()
        );
    }
}
