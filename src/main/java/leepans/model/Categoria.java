package leepans.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Panela> panelas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Panela> getPanelas() {
        return panelas;
    }

    public void setPanelas(List<Panela> panelas) {
        this.panelas = panelas;
    }
}