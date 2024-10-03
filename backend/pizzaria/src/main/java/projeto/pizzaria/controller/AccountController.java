package projeto.pizzaria.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.pizzaria.model.AccountRequestDTO;
import projeto.pizzaria.model.LoginRequestDTO;
import projeto.pizzaria.service.AccountService;

@CrossOrigin(origins = "*")
@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @PostMapping("/criar-conta")
    public ResponseEntity<?> criarConta(@RequestBody AccountRequestDTO requestDTO) {
        try {
            String mensagem = accountService.criarConta(requestDTO);
            return ResponseEntity.ok(mensagem);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Erro ao criar conta: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO requestDTO) {
        try {
            Long userId = accountService.login(requestDTO);
            return ResponseEntity.ok(userId);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(401).body("Erro ao realizar login: " + e.getMessage());
        }
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Long id) {
        try {
            AccountRequestDTO usuario = accountService.buscarUsuarioPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/usuarios/{userId}/editar")
    public ResponseEntity<?> editarUsuario(@PathVariable Long userId, @RequestBody AccountRequestDTO requestDTO) {
        try {
            logger.info("Editando usuário com ID: {}", userId);
            String mensagem = accountService.editarUsuario(userId, requestDTO);
            logger.info(mensagem);
            return ResponseEntity.ok(mensagem);
        } catch (IllegalStateException e) {
            logger.error("Erro ao atualizar usuário", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar usuário: " + e.getMessage());
        }
    }
}
