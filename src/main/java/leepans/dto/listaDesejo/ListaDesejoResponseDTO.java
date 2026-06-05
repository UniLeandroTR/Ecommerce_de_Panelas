package leepans.dto.listaDesejo;

import java.util.List;

import leepans.dto.panela.PanelaEcommerceDTO;
import leepans.dto.usuario.UsuarioResponseDTO;

public record ListaDesejoResponseDTO(Long id, UsuarioResponseDTO usuario, List<PanelaEcommerceDTO> panelas, Integer version) {
}
