package leepans.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ListaDesejo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    @OneToOne
    private Usuario usuario;

    @ManyToMany
    private List<Panela> produtos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Panela> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Panela> produtos) {
        this.produtos = produtos;
    }
}
