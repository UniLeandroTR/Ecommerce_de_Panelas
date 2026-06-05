package leepans.mapper;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.listaDesejo.ListaDesejoRequestDTO;
import leepans.dto.listaDesejo.ListaDesejoResponseDTO;
import leepans.dto.panela.PanelaEcommerceDTO;
import leepans.model.ListaDesejo;
import leepans.model.Panela;
import leepans.repository.PanelaRepository;

@ApplicationScoped
public class ListaDesejoMapper {

    @Inject
    PanelaRepository panelaRepository;

    @Inject
    PanelaMapper panelaMapper;

    @Inject
    UsuarioMapper usuarioMapper;

    public ListaDesejo toEntity(ListaDesejoRequestDTO dto) {
        if(dto == null) return null;

        ListaDesejo listaDesejo = new ListaDesejo();
        
        if(dto.version() != null){
            listaDesejo.setVersion(dto.version());
        }
        
        if(dto.idPanelas() != null && !dto.idPanelas().isEmpty()) {
            List<Panela> panelas = new ArrayList<>();
            for(Long id : dto.idPanelas()) {
                Panela panela = panelaRepository.findById(id);
                if(panela != null) {
                    panelas.add(panela);
                }
            }
            listaDesejo.setProdutos(panelas);
        }
        
        return listaDesejo;
    }

    public ListaDesejoResponseDTO toResponse(ListaDesejo listaDesejo){
        if(listaDesejo == null) return null;

        List<PanelaEcommerceDTO> panelasDTOs = listaDesejo.getProdutos() != null
            ? listaDesejo.getProdutos().stream()
                .map(panelaMapper::toEcommerceDTO)
                .toList()
            : new ArrayList<>();

        return new ListaDesejoResponseDTO(
            listaDesejo.getId(),
            usuarioMapper.toResponseDTO(listaDesejo.getUsuario()),
            panelasDTOs,
            listaDesejo.getVersion()
        );
    }
}
