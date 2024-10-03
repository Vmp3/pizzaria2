package projeto.pizzaria.repository;

import projeto.pizzaria.model.SaboresRequestDTO;

import java.util.List;

public interface SaboresRepository {
    void save(SaboresRequestDTO pizzaDTO);
    List<SaboresRequestDTO> findAll();
    void update(SaboresRequestDTO pizzaDTO);
    SaboresRequestDTO findById(Long id);
}
