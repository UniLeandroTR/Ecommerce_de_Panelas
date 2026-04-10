package leepans.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.ColecaoRequestDTO;
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
    public Colecao create(Colecao colecao) {
        repository.persist(colecao);
        return colecao;
    }

    @Override
    public void update(Long id, ColecaoRequestDTO dto) {
        Colecao colecao = repository.findById(id);

        colecao.setNome(dto.nome());
        colecao.setPanelas(dto.panelas());

        repository.persist(colecao);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
}
