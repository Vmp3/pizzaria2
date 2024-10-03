package projeto.pizzaria.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.pizzaria.model.SaboresRequestDTO;
import projeto.pizzaria.service.SaboresService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/sabores")
public class SaboresController {

    private final SaboresService saboresService;

    public SaboresController(SaboresService saboresService) {
        this.saboresService = saboresService;
    }

    @PostMapping("/adicionar")
    public ResponseEntity<?> adicionarSabor(@RequestBody SaboresRequestDTO saborDTO) {
        try {
            String mensagem = saboresService.adicionarSabor(saborDTO);
            return ResponseEntity.ok(mensagem);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Erro ao adicionar sabor de pizza: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar sabor de pizza: " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<SaboresRequestDTO>> listarSabores() {
        try {
            List<SaboresRequestDTO> sabores = saboresService.listarSabores();
            return ResponseEntity.ok(sabores);
        } catch (Exception e) {
            System.err.println("Erro ao listar sabores de pizza: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarSabor(@PathVariable Long id, @RequestBody SaboresRequestDTO saborDTO) {
        try {
            String mensagem = saboresService.atualizarSabor(id, saborDTO);
            return ResponseEntity.ok(mensagem);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar sabor de pizza: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar sabor de pizza: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaboresRequestDTO> buscarSabor(@PathVariable Long id) {
        try {
            SaboresRequestDTO sabor = saboresService.buscarSaborPorId(id);
            if (sabor != null) {
                return ResponseEntity.ok(sabor);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
