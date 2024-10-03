package projeto.pizzaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.pizzaria.model.SaboresRequestDTO;
import projeto.pizzaria.repository.SaboresRepository;

import java.util.List;

@Service
public class SaboresService {

    @Autowired
    private final SaboresRepository saboresRepository;

    public SaboresService(SaboresRepository saboresRepository) {
        this.saboresRepository = saboresRepository;
    }

    public String adicionarSabor(SaboresRequestDTO saborDTO) {
        if (saborDTO.getSabor() == null || saborDTO.getDescricao() == null || saborDTO.getValor() == null) {
            throw new IllegalStateException("Sabor, descrição e valor são obrigatórios.");
        }

        saboresRepository.save(saborDTO);
        return "Sabor de pizza adicionado com sucesso!";
    }

    public List<SaboresRequestDTO> listarSabores() {
        return saboresRepository.findAll();
    }

    public String atualizarSabor(Long id, SaboresRequestDTO saborDTO) {
        if (saborDTO.getSabor() == null || saborDTO.getDescricao() == null || saborDTO.getValor() == null) {
            throw new IllegalStateException("Sabor, descrição e valor são obrigatórios.");
        }

        SaboresRequestDTO existingSabor = saboresRepository.findById(id);
        if (existingSabor == null) {
            throw new IllegalStateException("Sabor não encontrado.");
        }

        existingSabor.setSabor(saborDTO.getSabor());
        existingSabor.setDescricao(saborDTO.getDescricao());
        existingSabor.setValor(saborDTO.getValor());
        existingSabor.setTamanho(saborDTO.getTamanho());
        existingSabor.setImagem(saborDTO.getImagem());

        saboresRepository.update(existingSabor);
        return "Sabor de pizza atualizado com sucesso!";
    }

    public SaboresRequestDTO buscarSaborPorId(Long id) {
        return saboresRepository.findById(id);
    }
}
