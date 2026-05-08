package leepans.service.ecommerce;

import java.util.List;

import leepans.dto.fornecedor.FornecedorRequestDTO;
import leepans.model.Fornecedor;

public interface FornecedorServiceInter {
    
    List<Fornecedor> findAll();
    Fornecedor findById(Long id);
    List<Fornecedor> findByNome(String nome);
    Fornecedor create(Fornecedor fornecedor);
    void update(Long id, FornecedorRequestDTO dto);
    void delete(Long id);

}
