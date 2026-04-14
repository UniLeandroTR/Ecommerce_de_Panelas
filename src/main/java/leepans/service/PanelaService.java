package leepans.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.PanelaRequestDTO;
import leepans.model.Panela;
import leepans.repository.CategoriaRepository;
import leepans.repository.FornecedorRepository;
import leepans.repository.FundoRepository;
import leepans.repository.PanelaRepository;
import leepans.repository.SustentacaoRepository;
import leepans.repository.TampaRepository;

@ApplicationScoped
public class PanelaService implements PanelaServiceInter{
    
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

    @Override
    public List<Panela> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Panela findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Panela create(Panela panela) {
        repository.persist(panela);
        return panela;
    }

    @Override
    public void update(Long id, PanelaRequestDTO dto) {
        Panela panela = repository.findById(id);

        panela.setModelo(dto.modelo());
        panela.setPreco(dto.preco());
        panela.setPeso(dto.peso());
        panela.setCapacidadeLitros(dto.capacidadeLitros());
        panela.setFuncionalidade(dto.funcionalidades());
        panela.setIsInducao(dto.isInducao());
        panela.setTamanho(dto.tamanho());
        panela.setCategoria(categoriaRepository.findById(dto.idCategoria()));
        panela.setFornecedor(fornecedorRepository.findById(dto.idFornecedor()));
        panela.setTampa(tampaRepository.findById(dto.idTampa()));
        panela.setFundo(fundoRepository.findById(dto.idFundo()));
        panela.setSustentacao(sustentacaoRepository.findById(dto.idSustentacao()));

        repository.persist(panela);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
