package leepans.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Pedido extends DefaultEntity {

    @ManyToOne
    private Usuario usuario;

    @ManyToOne(optional = false)
    private Endereco endereco;

    @Column(name = "codigo_status_pedido", nullable = false)
    private StatusPedido status;

    @Column(name = "valor_bruto", nullable = false, precision = 19, scale = 2)
    private BigDecimal valorBruto;

    @Column(name = "valor_desconto", precision = 19, scale = 2)
    private BigDecimal valorDesconto;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ItemPedido> itens;

    @ManyToOne
    @JoinColumn(name = "cupom_desconto_id")
    private CupomDesconto cupomDesconto;

    @OneToOne(mappedBy = "pedido")
    private Pagamento pagamento;

    /**
     * Calcula o valor total dos itens usando o snapshot de preço salvo no item.
     */
    public BigDecimal totalItens() {
        if (this.itens == null || this.itens.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return this.itens.stream()
                .map(item -> item.getValorUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calcula o valor final líquido que deve ser cobrado no pagamento.
     */
    public BigDecimal totalPedido() {
        BigDecimal desconto = this.valorDesconto != null ? this.valorDesconto : BigDecimal.ZERO;
        return totalItens().subtract(desconto);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public BigDecimal getValorBruto() {
        return valorBruto;
    }

    public void setValorBruto(BigDecimal valorTotal) {
        this.valorBruto = valorTotal;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public CupomDesconto getCupomDesconto() {
        return cupomDesconto;
    }

    public void setCupomDesconto(CupomDesconto cupomDesconto) {
        this.cupomDesconto = cupomDesconto;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
}
