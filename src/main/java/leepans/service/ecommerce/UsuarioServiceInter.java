package leepans.service.ecommerce;

import leepans.dto.endereco.EnderecoRequestDTO;
import leepans.dto.usuario.CadastroCompletoDTO;
import leepans.dto.usuario.CadastroSimplesDTO;
import leepans.dto.usuario.EditarDadosDTO;
import leepans.dto.usuario.UsuarioRequestDTO;
import leepans.model.Usuario;

import java.util.List;

public interface UsuarioServiceInter {

    List<Usuario> findAll();
    Usuario findById(Long id);
    Usuario findByLogin(String login);
    Usuario create(Usuario usuario);
    Usuario create(CadastroSimplesDTO dto);
    Usuario create(CadastroCompletoDTO dto);
    void update(String login, EditarDadosDTO dto);
    void setEndereco(String login, EnderecoRequestDTO endereco);
    void setPassword(String login, String token, String novaSenha);
    void resetPassword(String token, String novaSenha);
    void update(Long id, UsuarioRequestDTO dto);
    void delete(Long id);
}
