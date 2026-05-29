package leepans.service.ecommerce;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import leepans.dto.endereco.EnderecoRequestDTO;
import leepans.exception.ValidationException;
import leepans.model.Endereco;
import leepans.repository.EnderecoRepository;

@ApplicationScoped
public class EnderecoService implements EnderecoServiceInter {

    @Inject
    EnderecoRepository repository;

    @Override
    public List<Endereco> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Endereco findById(Long id) {
        Endereco endereco = repository.findById(id);
        if (endereco == null) {
            throw new ValidationException("Endereço com id " + id + " não encontrado.", "id");
        }
        return endereco;
    }

    @Override
    public Endereco findByUsuario(String login) {
        return repository.findByUsuario(login).firstResult();
    }

    @Override
    public List<Endereco> findByCidade(String cidade) {
        return repository.findByCidade(cidade).list();
    }

    @Override
    public List<Endereco> findByEstado(String estado) {
        return repository.findByEstado(estado).list();
    }

    @Override
    @Transactional
    public Endereco create(Endereco endereco) {
        repository.persist(endereco);
        return endereco;
    }

    @Override
    @Transactional
    public void update(Long id, EnderecoRequestDTO dto) {
        Endereco endereco = findById(id);

        // Controle de concorrência com Version
        if (dto.version() != null && !endereco.getVersion().equals(dto.version())) {
            throw new ValidationException(
                    "Conflito de concorrência: o endereço foi alterado por outra transação.",
                    "version");
        }

        endereco.setRua(dto.rua());
        endereco.setNumero(dto.numero());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());
        repository.persist(endereco);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        findById(id); // Valida se o endereço existe
        repository.deleteById(id);
    }
}
