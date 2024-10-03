package projeto.pizzaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.pizzaria.model.AccountRequestDTO;
import projeto.pizzaria.model.LoginRequestDTO;
import projeto.pizzaria.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String criarConta(AccountRequestDTO requestDTO) {
        if (accountRepository.cpfExists(requestDTO.getCpf())) {
            throw new IllegalStateException("CPF já está cadastrado.");
        }

        if (accountRepository.emailExists(requestDTO.getEmail())) {
            throw new IllegalStateException("Email já está cadastrado.");
        }

        accountRepository.save(requestDTO);
        return "Conta criada com sucesso!";
    }

    public Long login(LoginRequestDTO requestDTO) {
        Long userId = accountRepository.getUserIdByCredentials(requestDTO.getCpf(), requestDTO.getSenha());
        if (userId == null) {
            throw new IllegalStateException("Credenciais inválidas.");
        }
        return userId;
    }

    public AccountRequestDTO buscarUsuarioPorId(Long id) {
        AccountRequestDTO usuario = accountRepository.findById(id);
        if (usuario == null) {
            throw new IllegalStateException("Usuário não encontrado.");
        }
        return usuario;
    }

    public String editarUsuario(Long userId, AccountRequestDTO requestDTO) {
        AccountRequestDTO usuarioExistente = accountRepository.findById(userId);
        if (usuarioExistente == null) {
            throw new IllegalStateException("Usuário não encontrado.");
        }

        if (!usuarioExistente.getEmail().equals(requestDTO.getEmail())) {
            throw new IllegalStateException("Não é permitido alterar o email do usuário.");
        }

        usuarioExistente.setCep(requestDTO.getCep());
        usuarioExistente.setEndereco(requestDTO.getEndereco());
        usuarioExistente.setNumero(requestDTO.getNumero());

        accountRepository.update(usuarioExistente);

        return "Usuário atualizado com sucesso!";
    }
}
