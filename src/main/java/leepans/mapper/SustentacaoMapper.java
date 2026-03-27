package leepans.mapper;

import leepans.dto.SustentacaoResponseDTO;
import leepans.model.Sustentacao;

public class SustentacaoMapper {
    
    public static Sustentacao toEntity (SustentacaoResponseDTO dto){
        if(dto == null) return null;

        Sustentacao sustentacao = new Sustentacao();
        sustentacao.setId(dto.id());
        sustentacao.setPeso(dto.peso());
        sustentacao.setMaterial(dto.material());
        sustentacao.setCor(dto.cor());
        sustentacao.setTamanhoEmCm(dto.tamanhoEmCm());
        sustentacao.setQuantidade(dto.quantidade());
        sustentacao.setTipoSustentacao(dto.tipoSustentacao());

        return sustentacao;
    }

    public static SustentacaoResponseDTO toResponseDTO (Sustentacao sustentacao){
        if(sustentacao == null) return null;

        return new SustentacaoResponseDTO(
            sustentacao.getId(),
            sustentacao.getPeso(),
            sustentacao.getMaterial(),
            sustentacao.getCor(),
            sustentacao.getTamanhoEmCm(),
            sustentacao.getQuantidade(),
            sustentacao.getTipoSustentacao()
        );
    }
}
