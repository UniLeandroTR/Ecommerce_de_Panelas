package leepans.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ElementCollection
    private List<String> qualidades;

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

    public List<String> getQualidades() {
        return qualidades;
    }

    public void setQualidades(List<String> qualidades) {
        this.qualidades = qualidades;
    }
}