package leepans.service.ecommerce;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import leepans.dto.usuario.UsuarioRequestDTO;
import leepans.model.Usuario;
import leepans.repository.UsuarioRepository;

import java.util.List;

@ApplicationScoped
public class UsuarioService implements UsuarioServiceInter {

    @Inject
    private UsuarioRepository repository;

    @Override
    public List<Usuario> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Usuario findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Usuario create(Usuario usuario) {
        repository.persist(usuario);
        return usuario;
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
