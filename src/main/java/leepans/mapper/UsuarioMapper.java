package leepans.mapper;

import leepans.dto.usuario.UsuarioRequestDTO;
import leepans.dto.usuario.UsuarioResponseDTO;
import leepans.model.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequestDTO dto){
        if(dto==null) return null;

        Usuario usuario = new Usuario();
        usuario.setLogin(dto.login());
        usuario.setSenhaHash(dto.senha());
        usuario.setPerfil(dto.perfil());

        return usuario;
    }

    public static UsuarioResponseDTO toResponseDTO(Usuario usuario){
        if(usuario==null) return null;

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getSenhaHash(),
                usuario.getPerfil());
    }
}
