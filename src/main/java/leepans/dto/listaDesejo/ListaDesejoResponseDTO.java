package leepans.dto.listaDesejo;

import leepans.dto.panela.PanelaResponseDTO;
import leepans.dto.usuario.UsuarioResponseDTO;

import java.util.List;

public record ListaDesejoResponseDTO(Long id, UsuarioResponseDTO usuario, List<PanelaResponseDTO> panelas, Integer version) {
}
