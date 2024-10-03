package projeto.pizzaria.repository;

import projeto.pizzaria.model.AccountRequestDTO;

public interface AccountRepository {

    void save(AccountRequestDTO requestDTO);

    void update(AccountRequestDTO usuarioExistente);

    boolean verifyCredentials(String cpf, String senha);
    AccountRequestDTO findById(Long id);
    boolean cpfExists(String cpf);
    boolean emailExists(String email);

    Long getUserIdByCredentials(String cpf, String senha);
}
