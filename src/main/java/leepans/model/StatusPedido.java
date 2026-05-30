package leepans.model;

public enum StatusPedido {
    ENTREGUE(1L, "Entregue"),
    PENDENTE(2L, "Pendente");

    private final Long id;
    private final String nome;

    StatusPedido(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public static StatusPedido valueOf(Long id){
        for(StatusPedido status : StatusPedido.values()){
            if(status.getId().equals(id)){
                return status;
            }
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
