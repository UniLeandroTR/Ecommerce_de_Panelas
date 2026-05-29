package leepans.mapper;

import leepans.dto.endereco.EnderecoRequestDTO;
import leepans.dto.endereco.EnderecoResponseDTO;
import leepans.model.Endereco;

public class EnderecoMapper {

    public static Endereco toEntity(EnderecoRequestDTO dto) {
        if (dto == null) return null;

        Endereco endereco = new Endereco();
        endereco.setRua(dto.rua());
        endereco.setNumero(dto.numero());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());
        if (dto.version() != null) {
            endereco.setVersion(dto.version());
        }
        return endereco;
    }

    public static EnderecoResponseDTO toResponse(Endereco endereco) {
        if (endereco == null) return null;

        return new EnderecoResponseDTO(
                endereco.getId(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getDataCadastro(),
                endereco.getVersion()
        );
    }
}
