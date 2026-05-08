package leepans.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Colecao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "colecao", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Panela> panelas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Panela> getPanelas() {
        return panelas;
    }

    public void setPanelas(List<Panela> panelas) {
        this.panelas = panelas;
    }
}