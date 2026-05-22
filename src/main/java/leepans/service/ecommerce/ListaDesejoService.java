package leepans.service.ecommerce;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import leepans.dto.listaDesejo.ListaDesejoRequestDTO;
import leepans.model.ListaDesejo;
import leepans.model.Panela;
import leepans.repository.ListaDesejoRepository;
import leepans.repository.PanelaRepository;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@ApplicationScoped
public class ListaDesejoService implements ListaDesejoServiceInter {

    @Inject
    ListaDesejoRepository repository;

    @Inject
    PanelaRepository panelaRepository;

    public ListaDesejo findWishList(JsonWebToken jwt){
        String usuarioId = jwt.getSubject();
        ListaDesejo lista = repository.find("usuarioId", usuarioId).firstResult();
        return lista;
    }

    @Override
    public List<ListaDesejo> findAll() {
        return repository.findAll().list();
    }

    @Override
    public ListaDesejo findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ListaDesejo findByUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    @Override
    public ListaDesejo create(ListaDesejo listaDesejo) {
        repository.persist(listaDesejo);
        return listaDesejo;
    }

    @Override
    public void update(Long id, ListaDesejoRequestDTO dto) {
        ListaDesejo listaDesejo = repository.findById(id);

        if(listaDesejo.getVersion() != dto.version()) {
            throw new OptimisticLockException(
                "Conflito de concorrência: a lista de desejo foi alterada por outra transação."
            );
        }

        if(dto.idPanelas() != null && !dto.idPanelas().isEmpty()) {
            listaDesejo.getProdutos().clear();
            for(Long panelaId : dto.idPanelas()) {
                Panela panela = panelaRepository.findById(panelaId);
                if(panela != null) {
                    listaDesejo.getProdutos().add(panela);
                }
            }
        }

        repository.persist(listaDesejo);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void adicionarProduto(Long listaDesejoId, Long panelaId) {
        ListaDesejo listaDesejo = repository.findById(listaDesejoId);
        Panela panela = panelaRepository.findById(panelaId);

        if(listaDesejo != null && panela != null && !listaDesejo.getProdutos().contains(panela)) {
            listaDesejo.getProdutos().add(panela);
            repository.persist(listaDesejo);
        }
    }

    @Override
    public void removerProduto(Long listaDesejoId, Long panelaId) {
        ListaDesejo listaDesejo = repository.findById(listaDesejoId);
        Panela panela = panelaRepository.findById(panelaId);

        if(listaDesejo != null && panela != null) {
            listaDesejo.getProdutos().remove(panela);
            repository.persist(listaDesejo);
        }
    }
}
