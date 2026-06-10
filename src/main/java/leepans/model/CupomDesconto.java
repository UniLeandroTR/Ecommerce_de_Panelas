package leepans.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class CupomDesconto extends DefaultEntity {

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;
    @Column(name = "valor_desconto", precision = 19, scale = 2)
    private BigDecimal valorDesconto;
    @Column(name = "percentual_desconto", precision = 5, scale = 2)
    private BigDecimal percentualDesconto;
    @Column(name = "valor_minimo_compra", precision = 19, scale = 2)
    private BigDecimal valorMinimoCompra;
    private LocalDateTime dataValidade;
    private Integer quantidadeDisponivel;
    private boolean ativo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getPercentualDesconto() {
        return percentualDesconto;
    }

    public void setPercentualDesconto(BigDecimal percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }

    public BigDecimal getValorMinimoCompra() {
        return valorMinimoCompra;
    }

    public void setValorMinimoCompra(BigDecimal valorMinimoCompra) {
        this.valorMinimoCompra = valorMinimoCompra;
    }

    public LocalDateTime getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDateTime dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
