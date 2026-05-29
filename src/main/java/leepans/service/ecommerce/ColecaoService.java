package leepans.service.ecommerce;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import leepans.dto.colecao.ColecaoRequestDTO;
import leepans.model.Colecao;
import leepans.repository.ColecaoRepository;

@ApplicationScoped
public class ColecaoService implements ColecaoServiceInter{

    @Inject
    ColecaoRepository repository;

    @Override
    public List<Colecao> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Colecao findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Colecao> findByNome(String nome) {
        return repository.findByNome(nome).list();
    }

    @Override
    @Transactional
    public Colecao create(Colecao colecao) {
        repository.persist(colecao);
        return colecao;
    }

    @Override
    @Transactional
    public void update(Long id, ColecaoRequestDTO dto) {
        Colecao colecao = repository.findById(id);

        if (colecao.getVersion() != dto.version()) {
            throw new OptimisticLockException(
                "Conflito de concorrência: a colecao foi alterado por outra transação."
            );
        }

        colecao.setNome(dto.nome());

        repository.persist(colecao);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
}
