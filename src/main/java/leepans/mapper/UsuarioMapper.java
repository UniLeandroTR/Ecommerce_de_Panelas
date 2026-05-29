package leepans.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.usuario.UsuarioRequestDTO;
import leepans.dto.usuario.UsuarioResponseDTO;
import leepans.model.Usuario;
import leepans.repository.EnderecoRepository;

@ApplicationScoped
public class UsuarioMapper {

    @Inject
    private EnderecoRepository enderecoRepository;

    public Usuario toEntity(UsuarioRequestDTO dto){
        if(dto==null) return null;

        Usuario usuario = new Usuario();
        usuario.setLogin(dto.login());
        usuario.setSenhaHash(dto.senha());
        usuario.setPerfil(dto.perfil());
        usuario.setVersion(dto.version());
        usuario.setEndereco(enderecoRepository.findById(dto.idEndereco()));

        return usuario;
    }

    public UsuarioResponseDTO toResponseDTO(Usuario usuario){
        if(usuario==null) return null;

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getNome(),
                usuario.getPerfil(),
                EnderecoMapper.toResponse(usuario.getEndereco())
        );
    }
}
