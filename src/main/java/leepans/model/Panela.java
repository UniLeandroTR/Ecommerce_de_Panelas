package leepans.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Panela extends DefaultEntity {

    private String modelo;
    @Column(name = "preco", nullable = false, precision = 19, scale = 2)
    private BigDecimal preco;
    private Double peso;
    private Double capacidadeLitros;
    private String descricaco;
    private Boolean isInducao;

    @ManyToOne
    @JoinColumn(name = "id_material_principal")
    private Material materialPrincipal;

    @ManyToOne
    @JoinColumn(name = "id_cor")
    private Cor cor;

    @ManyToOne
    @JoinColumn(name = "id_colecao")
    private Colecao colecao;

    @OneToOne
    @JoinColumn(name = "id_fundo")
    private Fundo fundo;

    @OneToOne
    @JoinColumn(name = "id_sustentacao")
    private Sustentacao sustentacao;

    @OneToOne
    @JoinColumn(name = "id_tampa")
    private Tampa tampa;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;

    @Column(name = "codigo_tamanho")
    private Tamanho tamanho;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getCapacidadeLitros() {
        return capacidadeLitros;
    }

    public void setCapacidadeLitros(Double capacidadeLitros) {
        this.capacidadeLitros = capacidadeLitros;
    }

    public String getDescricaco() {
        return descricaco;
    }

    public void setDescricaco(String descricaco) {
        this.descricaco = descricaco;
    }

    public Boolean getIsInducao() {
        return isInducao;
    }

    public void setIsInducao(Boolean isInducao) {
        this.isInducao = isInducao;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public Material getMaterialPrincipal() {
        return materialPrincipal;
    }

    public void setMaterialPrincipal(Material materialPrincipal) {
        this.materialPrincipal = materialPrincipal;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Colecao getColecao() {
        return colecao;
    }

    public void setColecao(Colecao colecao) {
        this.colecao = colecao;
    }

    public Fundo getFundo() {
        return fundo;
    }

    public void setFundo(Fundo fundo) {
        this.fundo = fundo;
    }

    public Sustentacao getSustentacao() {
        return sustentacao;
    }

    public void setSustentacao(Sustentacao sustentacao) {
        this.sustentacao = sustentacao;
    }

    public Tampa getTampa() {
        return tampa;
    }

    public void setTampa(Tampa tampa) {
        this.tampa = tampa;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

}