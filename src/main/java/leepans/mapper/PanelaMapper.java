package leepans.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.PanelaRequestDTO;
import leepans.dto.PanelaResponseDTO;
import leepans.model.Panela;
import leepans.repository.CategoriaRepository;
import leepans.repository.ColecaoRepository;
import leepans.repository.FornecedorRepository;
import leepans.repository.FundoRepository;
import leepans.repository.SustentacaoRepository;
import leepans.repository.TampaRepository;

@ApplicationScoped
public class PanelaMapper {
    
    @Inject
    ColecaoRepository colecaoRepository;

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    TampaRepository tampaRepository;

    @Inject
    FundoRepository fundoRepository;

    @Inject
    SustentacaoRepository sustentacaoRepository;

    
    public Panela toEntity(PanelaRequestDTO dto){
        if(dto==null) return null;

        Panela panela = new Panela();
        panela.setModelo(dto.modelo());
        panela.setPreco(dto.preco());
        panela.setPeso(dto.peso());
        panela.setCapacidadeLitros(dto.capacidadeLitros());
        panela.setFuncionalidade(dto.funcionalidades());
        panela.setIsInducao(dto.isInducao());
        panela.setTamanho(dto.tamanho());
        panela.setColecao(colecaoRepository.findById(dto.idColecao()));
        panela.setCategoria(categoriaRepository.findById(dto.idCategoria()));
        panela.setFornecedor(fornecedorRepository.findById(dto.idFornecedor()));
        panela.setTampa(tampaRepository.findById(dto.idTampa()));
        panela.setFundo(fundoRepository.findById(dto.idFundo()));
        panela.setSustentacao(sustentacaoRepository.findById(dto.idSustentacao()));
        return panela;
    }
    
    public PanelaResponseDTO toResponseDTO (Panela panela){
        if(panela==null) return null;
        
        return new PanelaResponseDTO(
                panela.getId(),
                panela.getModelo(),
                panela.getColecao().getNome(),
                panela.getCategoria().getTipo(),
                panela.getTamanho(),
                panela.getPreco(),
                panela.getPeso(),
                panela.getCapacidadeLitros(),
                panela.getFuncionalidade(),
                panela.getIsInducao(),
                panela.getFornecedor().getNome(),
                panela.getTampa().getId(),
                panela.getFundo().getId(),
                panela.getSustentacao().getId()
        );
    }
}
