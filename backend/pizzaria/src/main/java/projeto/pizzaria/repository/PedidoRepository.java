package projeto.pizzaria.repository;

import projeto.pizzaria.model.PedidoRequestDTO;

import java.util.List;

public interface PedidoRepository {

    void save(PedidoRequestDTO pedidoDTO);

    List<PedidoRequestDTO> findAll();

    PedidoRequestDTO findById(Long idPedido);

    void update(PedidoRequestDTO pedidoDTO);

    void deleteById(Long idPedido);
}
