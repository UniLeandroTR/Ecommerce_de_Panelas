package leepans.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Pagamento extends DefaultEntity {

    @OneToOne(optional = false)
    private Pedido pedido;

    @Column(name = "data_processado")
    private LocalDateTime dataProcessado;
    private Double valor;

    @Column(name = "codigo_tipo_pagamento", nullable = false)
    private TipoPagamento tipoPagamento;

    @Column(name = "codigo_status_pagamento", nullable = false)
    private StatusPagamento statusPagamento;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public LocalDateTime getDataProcessado() {
        return dataProcessado;
    }

    public void setDataProcessado(LocalDateTime dataProcessado) {
        this.dataProcessado = dataProcessado;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }
}
