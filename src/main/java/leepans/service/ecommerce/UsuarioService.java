package leepans.service.ecommerce;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;
import leepans.dto.endereco.EnderecoRequestDTO;
import leepans.dto.usuario.CadastroCompletoDTO;
import leepans.dto.usuario.CadastroSimplesDTO;
import leepans.dto.usuario.EditarDadosDTO;
import leepans.dto.usuario.UsuarioRequestDTO;
import leepans.mapper.EnderecoMapper;
import leepans.model.Endereco;
import leepans.model.Perfil;
import leepans.model.Usuario;
import leepans.repository.EnderecoRepository;
import leepans.repository.UsuarioRepository;
import leepans.service.auth.CacheService;
import leepans.service.auth.HashService;

@ApplicationScoped
public class UsuarioService implements UsuarioServiceInter {

    @Inject
    private UsuarioRepository repository;

    @Inject
    private EnderecoRepository enderecoRepository;

    @Inject
    private HashService hashService;

    @Inject
    private CacheService cacheService;

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
        usuario.setSenhaHash(hashService.Argon2(usuario.getSenhaHash()));
        repository.persist(usuario);
        return usuario;
    }

    @Override
    @Transactional
    public Usuario create(CadastroSimplesDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setLogin(dto.login());
        usuario.setSenhaHash(hashService.Argon2(dto.senha()));
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
        usuario.setSenhaHash(hashService.Argon2(dto.senha()));
        usuario.setPerfil(Perfil.CLIENTE);
        Endereco endereco = EnderecoMapper.toEntity(dto.endereco());
        usuario.setEndereco(endereco);
        enderecoRepository.persist(endereco);
        repository.persist(usuario);
        return usuario;
    }

    @Override
    @Transactional
    public void update(String login, EditarDadosDTO dto){
        Usuario usuario = repository.findByLogin(login).firstResult();
        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        if(dto.endereco() != null){
            Endereco novoEndereco = EnderecoMapper.toEntity(dto.endereco());
            enderecoRepository.persist(novoEndereco);
            usuario.setEndereco(novoEndereco);
        }
        repository.persist(usuario);
    }

    @Override
    @Transactional
    public void setEndereco(String login, EnderecoRequestDTO endereco) {
        Usuario usuario = repository.findByLogin(login).firstResult();
        Endereco novoEndereco = EnderecoMapper.toEntity(endereco);
        enderecoRepository.persist(novoEndereco);
        usuario.setEndereco(novoEndereco);
        repository.persist(usuario);
    }

    @Override
    @Transactional
    public void setPassword(String login, String token, String novaSenha) {
        Usuario usuario = repository.findByLogin(login).firstResult();
        if(!cacheService.checkToken(login, token)) {
            throw new WebApplicationException("Token inválido ou expirado", Status.BAD_REQUEST);
        }
        usuario.setSenhaHash(hashService.Argon2(novaSenha));
        cacheService.invalidateToken(token);
        repository.persist(usuario);
    }

    @Override
    @Transactional
    public void resetPassword(String token, String novaSenha) {
        String login = cacheService.getLoginByToken(token);
        if (login == null) {
            throw new WebApplicationException("Token inválido ou expirado", Status.BAD_REQUEST);
        }
        Usuario usuario = repository.findByLogin(login).firstResult();
        usuario.setSenhaHash(hashService.Argon2(novaSenha));
        cacheService.invalidateToken(token);
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
        usuario.setSenhaHash(hashService.Argon2(dto.senha()));
        usuario.setPerfil(dto.perfil());
        repository.persist(usuario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }}
