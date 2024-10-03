package projeto.pizzaria.repository;

import projeto.pizzaria.model.ItemPedidoRequestDTO;

import java.util.List;

public interface ItemPedidoRepository {
    void save(ItemPedidoRequestDTO itemPedidoDTO);

    List<ItemPedidoRequestDTO> findByPedidoId(Long idPedido);

    void deleteById(Long idItemPedido);
}
