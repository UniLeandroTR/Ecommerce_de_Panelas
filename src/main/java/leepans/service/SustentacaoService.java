package leepans.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.sustentacao.SustentacaoRequestDTO;
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
        if(sustentacao.getMateriais() != null) {
            sustentacao.getMateriais().forEach(material -> Hibernate.initialize(material.getQualidades()));
        }
        return sustentacao;
    }

    @Override
    public void update(Long id, SustentacaoRequestDTO dto) {
        Sustentacao sustentacao = repository.findById(id);
        if (sustentacao != null) {
            sustentacao.setPeso(dto.peso());
            sustentacao.setMateriais(dto.idsMateriais() == null ? null : dto.idsMateriais().stream()
                    .map(materialRepository::findById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
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
