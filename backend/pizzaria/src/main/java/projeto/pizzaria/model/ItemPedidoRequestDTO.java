package projeto.pizzaria.model;

import jakarta.persistence.*;

@Entity
@Table(name = "itens_pedido")
public class ItemPedidoRequestDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private PedidoRequestDTO pedido;

    @ManyToOne
    @JoinColumn(name = "id_sabor", nullable = false)
    private SaboresRequestDTO sabor;

    private String tipo;

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public PedidoRequestDTO getPedido() {
        return pedido;
    }

    public void setPedido(PedidoRequestDTO pedido) {
        this.pedido = pedido;
    }

    public SaboresRequestDTO getSabor() {
        return sabor;
    }

    public void setSabor(SaboresRequestDTO sabor) {
        this.sabor = sabor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
