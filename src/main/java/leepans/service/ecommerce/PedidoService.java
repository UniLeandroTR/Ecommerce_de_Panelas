package leepans.service.ecommerce;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import leepans.exception.ValidationException;
import leepans.model.ItemPedido;
import leepans.model.Pedido;
import leepans.model.StatusPedido;
import leepans.model.Usuario;
import leepans.repository.ItemPedidoRepository;
import leepans.repository.PedidoRepository;

@ApplicationScoped
public class PedidoService implements PedidoServiceInter {

    @Inject
    PedidoRepository repository;

    @Inject
    ItemPedidoRepository itemRepository;

    @Inject
    UsuarioService usuarioService;

    @Inject
    EnderecoService enderecoService;

    @Inject
    PanelaService panelaService;

    @Override
    public List<Pedido> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Pedido findById(Long id) {
        Pedido pedido = repository.findById(id);
        if (pedido == null) {
            throw new ValidationException("Pedido com id " + id + " não encontrado.", "id");
        }
        return pedido;
    }

    @Override
    public List<Pedido> findCompras(String usuarioLogin){
        return repository.findCompras(usuarioLogin).list();
    }

    @Override
    public List<Pedido> findByUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId).list();
    }

    @Override
    public List<Pedido> findByStatus(StatusPedido status) {
        return repository.findByStatus(status).list();
    }

    @Override
    public List<Pedido> findByEnderecoCidade(String cidade) {
        return repository.findByEnderecoCidade(cidade).list();
    }

    @Override
    @Transactional
    public Pedido create(Pedido pedido, JsonWebToken jwt) {
        // Validar e salvar itens do pedido
        Double valorTotal = 0.0;
        List<ItemPedido> itensSalvos = new ArrayList<>();

        for (ItemPedido item : pedido.getItens()) {
            if (item.getPanela() == null || item.getPanela().getId() == null) {
                throw new ValidationException("Panela é obrigatória em cada item do pedido.", "item.panela");
            }
            
            panelaService.findById(item.getPanela().getId());

            itemRepository.persist(item);
            itensSalvos.add(item);
            valorTotal += item.getValorUnitario() * item.getQuantidade();
        }
        if(pedido.getCupomDesconto() != null) {
            Double valorDesconto = pedido.getCupomDesconto().getValorDesconto();
            if(pedido.getCupomDesconto().getPercentualDesconto() != null) {
                valorDesconto += valorTotal * (pedido.getCupomDesconto().getPercentualDesconto() / 100);
            }
            valorTotal -= valorDesconto;
            pedido.setValorDesconto(valorDesconto);
        } else {
            pedido.setValorDesconto(0.0);
        }
        String login = jwt.getClaim("upn");
        Usuario usuario = usuarioService.findByLogin(login);
        pedido.setUsuario(usuario);
        pedido.setEndereco(usuario.getEndereco());
        pedido.setItens(itensSalvos);
        pedido.setValorTotal(valorTotal);
        repository.persist(pedido);
        return pedido;
    }

    @Override
    @Transactional
    public void setStatus(Long id, StatusPedido status) {
        Pedido pedido = findById(id);
        pedido.setStatus(status);
        repository.persist(pedido);
    }


    @Override
    @Transactional
    public void delete(Long id) {
        Pedido pedido = findById(id);
        
        // Deletar itens do pedido
        if (pedido.getItens() != null) {
            for (ItemPedido item : pedido.getItens()) {
                itemRepository.deleteById(item.getId());
            }
        }
        
        repository.deleteById(id);
    }
}
