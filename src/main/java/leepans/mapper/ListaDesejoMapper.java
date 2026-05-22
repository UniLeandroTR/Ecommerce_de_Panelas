package leepans.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.listaDesejo.ListaDesejoRequestDTO;
import leepans.dto.listaDesejo.ListaDesejoResponseDTO;
import leepans.dto.panela.PanelaResponseDTO;
import leepans.model.ListaDesejo;
import leepans.model.Panela;
import leepans.repository.PanelaRepository;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ListaDesejoMapper {

    @Inject
    PanelaRepository panelaRepository;

    @Inject
    PanelaMapper panelaMapper;

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

        List<PanelaResponseDTO> panelasDTOs = listaDesejo.getProdutos() != null
            ? listaDesejo.getProdutos().stream()
                .map(panelaMapper::toResponseDTO)
                .toList()
            : new ArrayList<>();

        return new ListaDesejoResponseDTO(
            listaDesejo.getId(),
                UsuarioMapper.toResponseDTO(listaDesejo.getUsuario()),
            panelasDTOs,
            listaDesejo.getVersion()
        );
    }
}
