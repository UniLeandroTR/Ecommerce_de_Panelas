package leepans.mapper;

import leepans.dto.fornecedor.FornecedorRequestDTO;
import leepans.dto.fornecedor.FornecedorResponseDTO;
import leepans.model.Fornecedor;

public class FornecedorMapper {

    public static Fornecedor toEntity(FornecedorRequestDTO dto){
        if(dto==null) return null;

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dto.nome());
        fornecedor.setCnpj(dto.cnpj());
        fornecedor.setTelefone(dto.telefone());
        if(dto.version() != null){
            fornecedor.setVersion(dto.version());
        }

        return fornecedor;
    }

    public static FornecedorResponseDTO toResponse(Fornecedor fornecedor){
        if(fornecedor==null) return null;

        return new FornecedorResponseDTO(
          fornecedor.getId(),
          fornecedor.getNome(),
          fornecedor.getTelefone(),
          fornecedor.getCnpj(),
          fornecedor.getDataCadastro(),
          fornecedor.getVersion()
        );
    }
}
