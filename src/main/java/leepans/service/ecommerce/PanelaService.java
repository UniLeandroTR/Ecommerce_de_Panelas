package leepans.service.ecommerce;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Hibernate;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import leepans.dto.panela.PanelaRequestDTO;
import leepans.model.Panela;
import leepans.repository.CategoriaRepository;
import leepans.repository.ColecaoRepository;
import leepans.repository.FornecedorRepository;
import leepans.repository.FundoRepository;
import leepans.repository.PanelaRepository;
import leepans.repository.SustentacaoRepository;
import leepans.repository.TampaRepository;

@ApplicationScoped
public class PanelaService implements PanelaServiceInter {

    @Inject
    PanelaRepository repository;

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    TampaRepository tampaRepository;

    @Inject
    FundoRepository fundoRepository;

    @Inject
    SustentacaoRepository sustentacaoRepository;

    @Inject
    ColecaoRepository colecaoRepository;

    @Override
    public List<Panela> findAll() {
        List<Panela> panelas = repository.findAll().list();
        panelas.forEach(panela -> {
            Hibernate.initialize(panela.getTampa().getMateriais());
            Hibernate.initialize(panela.getFundo().getMateriais());
            Hibernate.initialize(panela.getSustentacao().getMateriais());

            panela.getTampa().getMateriais()
                    .forEach(material -> Hibernate.initialize(material.getQualidades()));
            panela.getFundo().getMateriais()
                    .forEach(material -> Hibernate.initialize(material.getQualidades()));
            panela.getSustentacao().getMateriais()
                    .forEach(material -> Hibernate.initialize(material.getQualidades()));
        });
        return panelas;
    }

    @Override
    public Panela findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Panela> findByCategoria(Long idcategoria) {
        return repository.findByCategoria(idcategoria).list();
    }

    @Override
    public List<Panela> findByColecao(Long idcolecao) {
        return repository.findByColecao(idcolecao).list();
    }

    @Override
    @Transactional
    public Panela create(Panela panela) {
        repository.persist(panela);
        panela.getFundo().getMateriais().forEach(material -> Hibernate.initialize(material.getQualidades()));
        panela.getTampa().getMateriais().forEach(material -> Hibernate.initialize(material.getQualidades()));
        panela.getSustentacao().getMateriais().forEach(material -> Hibernate.initialize(material.getQualidades()));
        return panela;
    }

    @Override
    @Transactional
    public void setPrice(Long id, BigDecimal preco) {
        Panela panela = repository.findById(id);
        panela.setPreco(preco);
        repository.persist(panela);
    }

    @Override
    @Transactional
    public void update(Long id, PanelaRequestDTO dto) {
        Panela panela = repository.findById(id);
        if (panela.getVersion() != dto.version()) {
            throw new OptimisticLockException(
                    "Conflito de concorrência: a panela foi alterada por outra transação.");
        }
        panela.setModelo(dto.modelo());
        panela.setPreco(dto.preco());
        panela.setPeso(dto.peso());
        panela.setCapacidadeLitros(dto.capacidadeLitros());
        panela.setDescricaco(dto.descricao());
        panela.setIsInducao(dto.isInducao());
        panela.setTamanho(dto.tamanho());
        panela.setColecao(colecaoRepository.findById(dto.idColecao()));
        panela.setCategoria(categoriaRepository.findById(dto.idCategoria()));
        panela.setFornecedor(fornecedorRepository.findById(dto.idFornecedor()));
        panela.setTampa(tampaRepository.findById(dto.idTampa()));
        panela.setFundo(fundoRepository.findById(dto.idFundo()));
        panela.setSustentacao(sustentacaoRepository.findById(dto.idSustentacao()));

        repository.persist(panela);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
