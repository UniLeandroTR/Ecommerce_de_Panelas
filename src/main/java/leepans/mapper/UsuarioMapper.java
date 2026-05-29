package leepans.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import leepans.dto.usuario.CadastroCompletoDTO;
import leepans.dto.usuario.CadastroSimplesDTO;
import leepans.dto.usuario.UsuarioRequestDTO;
import leepans.dto.usuario.UsuarioResponseDTO;
import leepans.model.Perfil;
import leepans.model.Usuario;

@ApplicationScoped
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO dto){
        if(dto==null) return null;

        Usuario usuario = new Usuario();
        usuario.setLogin(dto.login());
        usuario.setSenhaHash(dto.senha());
        usuario.setPerfil(dto.perfil());
        usuario.setVersion(dto.version());
        usuario.setEndereco(EnderecoMapper.toEntity(dto.endereco()));

        return usuario;
    }

    public Usuario toEntity(CadastroSimplesDTO dto){
        if(dto==null) return null;

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setLogin(dto.login());
        usuario.setSenhaHash(dto.senha());

        return usuario;
    }

    public Usuario toEntity(CadastroCompletoDTO dto){
        if(dto==null) return null;

        Usuario usuario = new Usuario();
        usuario.setLogin(dto.login());
        usuario.setSenhaHash(dto.senha());
        usuario.setPerfil(Perfil.CLIENTE);
        usuario.setEndereco(EnderecoMapper.toEntity(dto.endereco()));

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
