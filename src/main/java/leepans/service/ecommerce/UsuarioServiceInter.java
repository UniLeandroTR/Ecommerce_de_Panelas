package leepans.service.ecommerce;

import leepans.dto.usuario.UsuarioRequestDTO;
import leepans.model.Usuario;

import java.util.List;

public interface UsuarioServiceInter {

    List<Usuario> findAll();
    Usuario findById(Long id);
    Usuario findByLogin(String login);
    Usuario create(Usuario usuario);
    void setEndereco(String login, Long idEndereco);
    void update(Long id, UsuarioRequestDTO dto);
    void delete(Long id);
}
