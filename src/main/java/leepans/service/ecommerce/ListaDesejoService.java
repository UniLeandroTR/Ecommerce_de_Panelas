package leepans.service.ecommerce;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import org.hibernate.Hibernate;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import leepans.dto.listaDesejo.ListaDesejoRequestDTO;
import leepans.model.ListaDesejo;
import leepans.model.Panela;
import leepans.model.Usuario;
import leepans.repository.ListaDesejoRepository;
import leepans.repository.PanelaRepository;
import leepans.repository.UsuarioRepository;

@ApplicationScoped
public class ListaDesejoService implements ListaDesejoServiceInter {

    @Inject
    ListaDesejoRepository repository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    PanelaRepository panelaRepository;

    public ListaDesejo findWishList(JsonWebToken jwt) {
        String usuarioLogin = jwt.getName();
        ListaDesejo lista = repository.findByUsuarioLogin(usuarioLogin).singleResult();
        if (lista != null && lista.getProdutos() != null) {
            lista.getProdutos().forEach(panela -> {
                initializePanela(panela);
            });
        }
        return lista;
    }

    public Usuario findUsuario(JsonWebToken jwt) {
        String usuarioLogin = jwt.getName();
        return usuarioRepository.findByLogin(usuarioLogin).singleResult();
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
    public ListaDesejo findByUsuarioLogin(String usuarioLogin) {
        return repository.findByUsuarioLogin(usuarioLogin).singleResult();
    }

    @Override
    @Transactional
    public ListaDesejo create(ListaDesejo listaDesejo) {
        listaDesejo.getProdutos().forEach(panela -> {
            initializePanela(panela);
        });
        repository.persist(listaDesejo);
        return listaDesejo;
    }

    @Transactional
    public ListaDesejo create(List<Long> idProdutos, JsonWebToken jwt) {
        ListaDesejo listaDesejo = new ListaDesejo();
        listaDesejo.setUsuario(findUsuario(jwt));
        listaDesejo.setProdutos(idProdutos.stream()
                .map(id -> {
                    Panela panela = panelaRepository.findById(id);
                    if (panela != null) {
                        initializePanela(panela);
                    }
                    return panela;
                })
                .filter(panela -> panela != null)
                .toList());
        repository.persist(listaDesejo);
        return listaDesejo;
    }

    @Override
    @Transactional
    public void update(Long id, ListaDesejoRequestDTO dto) {
        ListaDesejo listaDesejo = repository.findById(id);

        if (listaDesejo.getVersion() != dto.version()) {
            throw new OptimisticLockException(
                    "Conflito de concorrência: a lista de desejo foi alterada por outra transação.");
        }

        if (dto.idPanelas() != null && !dto.idPanelas().isEmpty()) {
            listaDesejo.getProdutos().clear();
            for (Long panelaId : dto.idPanelas()) {
                Panela panela = panelaRepository.findById(panelaId);
                if (panela != null) {
                    listaDesejo.getProdutos().add(panela);
                }
            }
        }

        repository.persist(listaDesejo);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void adicionarProduto(String usuarioLogin, Long panelaId) {
        ListaDesejo listaDesejo = repository.findByUsuarioLogin(usuarioLogin).firstResult();
        Panela panela = panelaRepository.findById(panelaId);

        if (listaDesejo != null && panela != null && !listaDesejo.getProdutos().contains(panela)) {
            listaDesejo.getProdutos().add(panela);
            repository.persist(listaDesejo);
        }
    }

    @Override
    @Transactional
    public void removerProduto(String usuarioLogin, Long panelaId) {
        ListaDesejo listaDesejo = repository.findByUsuarioLogin(usuarioLogin).firstResult();
        Panela panela = panelaRepository.findById(panelaId);

        if (listaDesejo != null && panela != null) {
            listaDesejo.getProdutos().remove(panela);
            repository.persist(listaDesejo);
        }
    }

    public static void initializePanela(Panela panela) {
        if (panela.getTampa() != null) {
            Hibernate.initialize(panela.getTampa().getMateriais());
            panela.getTampa().getMateriais()
                    .forEach(material -> Hibernate.initialize(material.getQualidades()));
        }
        if (panela.getFundo() != null) {
            Hibernate.initialize(panela.getFundo().getMateriais());
            panela.getFundo().getMateriais()
                    .forEach(material -> Hibernate.initialize(material.getQualidades()));
        }
        if (panela.getSustentacao() != null) {
            Hibernate.initialize(panela.getSustentacao().getMateriais());
            panela.getSustentacao().getMateriais()
                    .forEach(material -> Hibernate.initialize(material.getQualidades()));
        }
    }
}
