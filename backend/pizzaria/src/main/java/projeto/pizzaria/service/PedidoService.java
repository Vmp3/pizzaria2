package projeto.pizzaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.pizzaria.model.PedidoRequestDTO;
import projeto.pizzaria.repository.PedidoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Long adicionarPedido(PedidoRequestDTO pedidoDTO) {
        if (pedidoDTO.getIdCliente() == null) {
            throw new IllegalStateException("Cliente não especificado para o pedido.");
        }

        if (pedidoDTO.getDataPedido() == null) {
            pedidoDTO.setDataPedido(LocalDateTime.now());
        }

        pedidoRepository.save(pedidoDTO);
        return pedidoDTO.getIdPedido();
    }

    public List<PedidoRequestDTO> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public void removerPedido(Long id) {
        pedidoRepository.deleteById(id);
    }

    public void atualizarPedido(Long id, PedidoRequestDTO pedidoDTO) {
        PedidoRequestDTO existingPedido = pedidoRepository.findById(id);
        if (existingPedido == null) {
            throw new IllegalStateException("Pedido não encontrado.");
        }

        if (pedidoDTO.getIdCliente() == null) {
            pedidoDTO.setIdCliente(1L);
        }

        pedidoDTO.setIdPedido(id);
        pedidoRepository.update(pedidoDTO);
    }
}
