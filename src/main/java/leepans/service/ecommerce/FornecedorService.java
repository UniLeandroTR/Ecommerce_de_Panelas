package leepans.service.ecommerce;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import leepans.dto.fornecedor.FornecedorRequestDTO;
import leepans.model.Fornecedor;
import leepans.repository.FornecedorRepository;

@ApplicationScoped
public class FornecedorService implements FornecedorServiceInter {

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
    public List<Fornecedor> findByNome(String nome) {
        return repository.findByNome(nome).list();
    }

    @Override
    @Transactional
    public Fornecedor create(Fornecedor fornecedor) {
        repository.persist(fornecedor);
        return fornecedor;
    }

    @Override
    @Transactional
    public void update(Long id, FornecedorRequestDTO dto) {
        Fornecedor fornecedor = repository.findById(id);
        if (fornecedor.getVersion() != dto.version()) {
            throw new OptimisticLockException(
                "Conflito de concorrência: o fornecedor foi alterado por outra transação."
            );
        }
        fornecedor.setNome(dto.nome());
        fornecedor.setTelefone(dto.telefone());
        fornecedor.setCnpj(dto.cnpj());
        repository.persist(fornecedor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
