package leepans.service.ecommerce;

import java.util.List;

import leepans.dto.sustentacao.SustentacaoRequestDTO;
import leepans.model.Sustentacao;

public interface SustentacaoServiceInter {
    
    List<Sustentacao> findAll();
    Sustentacao findById(Long id);
    List<Sustentacao> findByMaterial(Long idmaterial);
    Sustentacao create(Sustentacao sustentacao);
    void update(Long id, SustentacaoRequestDTO dto);
    void delete(Long id);
}
