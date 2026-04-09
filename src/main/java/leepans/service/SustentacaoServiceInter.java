package leepans.service;

import java.util.List;

import leepans.dto.SustentacaoRequestDTO;
import leepans.model.Sustentacao;

public interface SustentacaoServiceInter {
    
    List<Sustentacao> findAll();
    Sustentacao findById(Long id);
    Sustentacao create(Sustentacao sustentacao);
    void update(Long id, SustentacaoRequestDTO dto);
    void delete(Long id);
}
