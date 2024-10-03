package projeto.pizzaria.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.pizzaria.model.PedidoRequestDTO;
import projeto.pizzaria.service.PedidoService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/carrinho")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/adicionar")
    public ResponseEntity<?> adicionarPedido(@RequestBody PedidoRequestDTO pedidoDTO) {
        try {
            Long idPedido = pedidoService.adicionarPedido(pedidoDTO);
            return ResponseEntity.ok(idPedido);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Erro ao adicionar ao carrinho: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao adicionar ao carrinho: " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarPedidos() {
        try {
            List<PedidoRequestDTO> pedidos = pedidoService.listarPedidos();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar pedidos: " + e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> removerPedido(@PathVariable Long id) {
        try {
            pedidoService.removerPedido(id);
            return ResponseEntity.ok("Pedido removido do carrinho com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao remover do carrinho: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarPedido(@PathVariable Long id, @RequestBody PedidoRequestDTO pedidoDTO) {
        try {
            pedidoService.atualizarPedido(id, pedidoDTO);
            return ResponseEntity.ok("Pedido atualizado no carrinho com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro ao atualizar no carrinho: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar no carrinho: " + e.getMessage());
        }
    }
}
