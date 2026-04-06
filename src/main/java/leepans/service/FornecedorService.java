package leepans.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.FornecedorRequestDTO;
import leepans.model.Fornecedor;
import leepans.repository.FornecedorRepository;

import java.util.List;

@ApplicationScoped
public class FornecedorService implements GenericService<Fornecedor, FornecedorRequestDTO> {

    @Inject
    FornecedorRepository repository;

    @Override
    public List<Fornecedor> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Fornecedor findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Fornecedor create(Fornecedor entity) {
        repository.persist(entity);
        return entity;
    }

    @Override
    public void update(Long id, FornecedorRequestDTO dto) {
        Fornecedor fornecedor = repository.findById(id);
        fornecedor.setNome(dto.nome());
        fornecedor.setTelefone(dto.telefone());
        fornecedor.setCnpj(dto.cnpj());
        repository.persist(fornecedor);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
