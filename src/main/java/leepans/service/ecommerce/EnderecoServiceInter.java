package leepans.service.ecommerce;

import java.util.List;

import leepans.dto.endereco.EnderecoRequestDTO;
import leepans.model.Endereco;

public interface EnderecoServiceInter {

    List<Endereco> findAll();

    Endereco findById(Long id);

    Endereco findByUsuario(String login);

    List<Endereco> findByCidade(String cidade);

    List<Endereco> findByEstado(String estado);

    Endereco create(Endereco endereco);

    void update(Long id, EnderecoRequestDTO dto);

    void delete(Long id);
}
