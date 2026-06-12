package leepans.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.panela.PanelaEcommerceDTO;
import leepans.dto.panela.PanelaRequestDTO;
import leepans.dto.panela.PanelaResponseDTO;
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

    @Inject
    ColecaoMapper colecaoMapper;

    @Inject
    SustentacaoMapper sustentacaoMapper;

    @Inject
    TampaMapper tampaMapper;

    @Inject
    FundoMapper fundoMapper;

    
    public Panela toEntity(PanelaRequestDTO dto){
        if(dto==null) return null;

        Panela panela = new Panela();
        panela.setModelo(dto.modelo());
        panela.setPreco(dto.preco());
        panela.setPeso(dto.peso());
        panela.setCapacidadeLitros(dto.capacidadeLitros());
        panela.setDescricao(dto.descricao());
        panela.setIsInducao(dto.isInducao());
        panela.setTamanho(dto.tamanho());
        panela.setColecao(colecaoRepository.findById(dto.idColecao()));
        panela.setCategoria(categoriaRepository.findById(dto.idCategoria()));
        panela.setFornecedor(fornecedorRepository.findById(dto.idFornecedor()));
        panela.setTampa(tampaRepository.findById(dto.idTampa()));
        panela.setFundo(fundoRepository.findById(dto.idFundo()));
        panela.setSustentacao(sustentacaoRepository.findById(dto.idSustentacao()));
        if(dto.version() != null){
            panela.setVersion(dto.version());
        }

        return panela;
    }
    
    public PanelaResponseDTO toResponseDTO (Panela panela){
        if(panela==null) return null;
        
        return new PanelaResponseDTO(
                panela.getId(),
                panela.getModelo(),
                panela.getDataCadastro(),
                panela.getPreco(),
                panela.getPeso(),
                panela.getCapacidadeLitros(),
                panela.getDescricao(),
                panela.getIsInducao(),
                panela.getTamanho(),
                CategoriaMapper.toResponse(panela.getCategoria()),
                colecaoMapper.toResponseDTO(panela.getColecao()),
                FornecedorMapper.toResponse(panela.getFornecedor()),
                tampaMapper.toResponseDTO(panela.getTampa()),
                fundoMapper.toResponseDTO(panela.getFundo()),
                sustentacaoMapper.toResponseDTO(panela.getSustentacao()),
                panela.getVersion()
        );
    }

    public PanelaEcommerceDTO toEcommerceDTO (Panela panela){
        if(panela==null) return null;
        
        return new PanelaEcommerceDTO(
                panela.getId(),
                panela.getModelo(),
                panela.getPreco(),
                panela.getCapacidadeLitros(),
                panela.getIsInducao(),
                panela.getTamanho(),
                MaterialMapper.toEcommerceDTO(panela.getMaterialPrincipal()),
                CorMapper.toEcommerceDTO(panela.getCor()),
                CategoriaMapper.toEcommerceDTO(panela.getCategoria()),
                colecaoMapper.toEcommerceDTO(panela.getColecao()),
                tampaMapper.toEcommerceDTO(panela.getTampa()),
                fundoMapper.toEcommerceDTO(panela.getFundo()),
                sustentacaoMapper.toEcommerceDTO(panela.getSustentacao()),
                panela.getVersion()
        );
    }
}
