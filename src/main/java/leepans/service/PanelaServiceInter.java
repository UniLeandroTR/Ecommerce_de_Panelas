package leepans.service;

import java.util.List;

import leepans.dto.PanelaRequestDTO;
import leepans.model.Panela;

public interface PanelaServiceInter {
    
    List<Panela> findAll();
    Panela findById(Long id);
    Panela create(Panela panela);
    void update(Long id, PanelaRequestDTO dto);
    void delete(Long id);
}
