package leepans.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.sustentacao.SustentacaoRequestDTO;
import leepans.dto.sustentacao.SustentacaoResponseDTO;
import leepans.model.Material;
import leepans.model.Sustentacao;
import leepans.repository.MaterialRepository;

@ApplicationScoped
public class SustentacaoMapper {
    
    @Inject
    MaterialRepository materialRepository;

    public Sustentacao toEntity (SustentacaoRequestDTO dto){
        if(dto == null) return null;

        Sustentacao sustentacao = new Sustentacao();
        sustentacao.setPeso(dto.peso());
        List<Material> materiais = dto.idsMateriais() == null ? null : dto.idsMateriais().stream()
                .map(materialRepository::findById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        sustentacao.setMateriais(materiais);
        sustentacao.setTamanhoEmCm(dto.tamanhoEmCm());
        sustentacao.setQuantidade(dto.quantidade());
        sustentacao.setTipoSustentacao(dto.tipoSustentacao());
        if(dto.version() != null){
            sustentacao.setVersion(dto.version());
        }

        return sustentacao;
    }

    public SustentacaoResponseDTO toResponseDTO (Sustentacao sustentacao){
        if(sustentacao == null) return null;

        return new SustentacaoResponseDTO(
            sustentacao.getId(),
            sustentacao.getPeso(),
            MaterialMapper.toResponseDTO(sustentacao.getMateriais()),
            sustentacao.getTamanhoEmCm(),
            sustentacao.getQuantidade(),
            sustentacao.getTipoSustentacao(),
            sustentacao.getDataCadastro(),
            sustentacao.getVersion()
        );
    }
}
