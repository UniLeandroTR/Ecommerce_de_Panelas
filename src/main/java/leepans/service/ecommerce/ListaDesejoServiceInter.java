package leepans.service.ecommerce;

import leepans.dto.listaDesejo.ListaDesejoRequestDTO;
import leepans.model.ListaDesejo;
import java.util.List;

public interface ListaDesejoServiceInter {
    
    List<ListaDesejo> findAll();
    ListaDesejo findById(Long id);
    ListaDesejo findByUsuarioLogin(String usuarioLogin);
    ListaDesejo create(ListaDesejo listaDesejo);
    void update(Long id, ListaDesejoRequestDTO dto);
    void delete(Long id);
    void adicionarProduto(Long listaDesejoId, Long panelaId);
    void removerProduto(Long listaDesejoId, Long panelaId);
}
