package leepans.service.ecommerce;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import leepans.dto.endereco.EnderecoRequestDTO;
import leepans.dto.usuario.CadastroCompletoDTO;
import leepans.dto.usuario.CadastroSimplesDTO;
import leepans.dto.usuario.UsuarioRequestDTO;
import leepans.mapper.EnderecoMapper;
import leepans.model.Endereco;
import leepans.model.Perfil;
import leepans.model.Usuario;
import leepans.repository.EnderecoRepository;
import leepans.repository.UsuarioRepository;
import leepans.service.auth.HashService;

@ApplicationScoped
public class UsuarioService implements UsuarioServiceInter {

    @Inject
    private UsuarioRepository repository;

    @Inject
    private EnderecoRepository enderecoRepository;

    @Inject
    private HashService hashService;

    @Override
    public List<Usuario> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Usuario findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Usuario findByLogin(String login) {
        return repository.findByLogin(login).firstResult();
    }

    @Override
    @Transactional
    public Usuario create(Usuario usuario) {
        usuario.setSenhaHash(hashService.bcrypt(usuario.getSenhaHash()));
        repository.persist(usuario);
        return usuario;
    }

    @Override
    @Transactional
    public Usuario create(CadastroSimplesDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setLogin(dto.login());
        usuario.setSenhaHash(hashService.bcrypt(dto.senha()));
        usuario.setPerfil(Perfil.CLIENTE);
        repository.persist(usuario);
        return usuario;
    }

    @Override
    @Transactional
    public Usuario create(CadastroCompletoDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setLogin(dto.login());
        usuario.setSenhaHash(hashService.bcrypt(dto.senha()));
        usuario.setPerfil(Perfil.CLIENTE);
        Endereco endereco = EnderecoMapper.toEntity(dto.endereco());
        usuario.setEndereco(endereco);
        enderecoRepository.persist(endereco);
        repository.persist(usuario);
        return usuario;
    }

    @Override
    @Transactional
    public void setEndereco(String login, EnderecoRequestDTO endereco) {
        Usuario usuario = repository.findByLogin(login).firstResult();
        usuario.setEndereco(EnderecoMapper.toEntity(endereco));
        repository.persist(usuario);
    }

    @Override
    @Transactional
    public void update(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = repository.findById(id);
        if (usuario.getVersion() != dto.version()) {
            throw new OptimisticLockException(
                    "Conflito de concorrência: o usuário foi alterado por outra transação.");
        }
        usuario.setLogin(dto.login());
        usuario.setSenhaHash(dto.senha());
        usuario.setPerfil(dto.perfil());
        repository.persist(usuario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
