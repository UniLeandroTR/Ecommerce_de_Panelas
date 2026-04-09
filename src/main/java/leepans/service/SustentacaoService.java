package leepans.service;

import java.util.List;

import org.hibernate.Hibernate;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.SustentacaoRequestDTO;
import leepans.model.Sustentacao;
import leepans.repository.CorRepository;
import leepans.repository.MaterialRepository;
import leepans.repository.SustentacaoRepository;

@ApplicationScoped
public class SustentacaoService implements SustentacaoServiceInter{

    @Inject
    SustentacaoRepository repository;

    @Inject
    CorRepository corRepository;

    @Inject
    MaterialRepository materialRepository;

    @Override
    public List<Sustentacao> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Sustentacao findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Sustentacao create(Sustentacao sustentacao) {
        repository.persist(sustentacao);
        if(sustentacao.getMaterial() != null) {
            Hibernate.initialize(sustentacao.getMaterial().getQualidades());
        }
        return sustentacao;
    }

    @Override
    public void update(Long id, SustentacaoRequestDTO dto) {
        Sustentacao sustentacao = repository.findById(id);
        if (sustentacao != null) {
            sustentacao.setPeso(dto.peso());
            sustentacao.setMaterial(materialRepository.findById(dto.idMaterial()));
            sustentacao.setCor(corRepository.findById(dto.idCor()));
            sustentacao.setQuantidade(dto.quantidade());
            sustentacao.setTamanhoEmCm(dto.tamanhoEmCm());
            sustentacao.setTipoSustentacao(dto.tipoSustentacao());
            repository.persist(sustentacao);
        }
    }

    @Override
    public void delete(Long id) {
        Sustentacao sustentacao = repository.findById(id);
        if (sustentacao != null) {
            repository.delete(sustentacao);
        }
    }    
}
