package leepans.service;

import java.util.List;

import leepans.dto.FornecedorRequestDTO;
import leepans.model.Fornecedor;

public interface FornecedorServiceInter {
    
    List<Fornecedor> findAll();
    Fornecedor findById(Long id);
    Fornecedor create(Fornecedor fornecedor);
    void update(Long id, FornecedorRequestDTO dto);
    void delete(Long id);

}
