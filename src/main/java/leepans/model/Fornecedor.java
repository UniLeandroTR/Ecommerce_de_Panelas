package leepans.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Fornecedor extends DefaultEntity {

    private String nome;
    private String telefone;
    private String cnpj;

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Panela> panelas;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public List<Panela> getPanelas() {
        return panelas;
    }

    public void setPanelas(List<Panela> panelas) {
        this.panelas = panelas;
    }
}