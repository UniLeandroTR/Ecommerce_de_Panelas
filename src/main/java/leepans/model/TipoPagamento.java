package leepans.model;

public enum TipoPagamento {
    CARTAO_CREDITO(1L, "Cartão de Crédito"),
    CARTAO_DEBITO(2L, "Cartão de Débito"),
    BOLETO(3L, "Boleto"),
    PIX(4L, "Pix");

    private final Long ID;
    private final String NOME;

    TipoPagamento(Long id, String nome) {
        this.ID = id;
        this.NOME = nome;
    }

    public static TipoPagamento valueOf(Long id) {
        for (TipoPagamento tipo : TipoPagamento.values()) {
            if (tipo.getID().equals(id)) {
                return tipo;
            }
        }
        return null;
    }

    public Long getID() {
        return ID;
    }

    public String getNOME() {
        return NOME;
    }
}
