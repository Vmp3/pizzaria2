package projeto.pizzaria.repository.JDBC;

import org.springframework.stereotype.Repository;
import projeto.pizzaria.model.SaboresRequestDTO;
import projeto.pizzaria.repository.SaboresRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcSaboresRepository implements SaboresRepository {

    private final DataSource dataSource;

    public JdbcSaboresRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(SaboresRequestDTO saborDTO) {
        String sql = "INSERT INTO sabores (sabor, descricao, valor, tamanho, imagem) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, saborDTO.getSabor());
            statement.setString(2, saborDTO.getDescricao());
            statement.setBigDecimal(3, saborDTO.getValor());
            statement.setString(4, saborDTO.getTamanho());
            statement.setString(5, saborDTO.getImagem());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                saborDTO.setIdsabor(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Falha ao obter o ID gerado ao salvar o sabor da pizza.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao adicionar sabor de pizza.", e);
        }
    }

    @Override
    public List<SaboresRequestDTO> findAll() {
        List<SaboresRequestDTO> sabores = new ArrayList<>();
        String sql = "SELECT * FROM sabores";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                SaboresRequestDTO saborDTO = new SaboresRequestDTO();
                saborDTO.setIdsabor(resultSet.getLong("idsabor"));
                saborDTO.setSabor(resultSet.getString("sabor"));
                saborDTO.setDescricao(resultSet.getString("descricao"));
                saborDTO.setValor(resultSet.getBigDecimal("valor"));
                saborDTO.setTamanho(resultSet.getString("tamanho"));
                saborDTO.setImagem(resultSet.getString("imagem"));
                sabores.add(saborDTO);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao listar sabores de pizza.", e);
        }
        return sabores;
    }

    @Override
    public SaboresRequestDTO findById(Long id) {
        String sql = "SELECT * FROM sabores WHERE idsabor = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                SaboresRequestDTO saborDTO = new SaboresRequestDTO();
                saborDTO.setIdsabor(resultSet.getLong("idsabor"));
                saborDTO.setSabor(resultSet.getString("sabor"));
                saborDTO.setDescricao(resultSet.getString("descricao"));
                saborDTO.setValor(resultSet.getBigDecimal("valor"));
                saborDTO.setTamanho(resultSet.getString("tamanho"));
                saborDTO.setImagem(resultSet.getString("imagem"));
                return saborDTO;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar sabor de pizza por ID.", e);
        }
    }

    @Override
    public void update(SaboresRequestDTO saborDTO) {
        String sql = "UPDATE sabores SET sabor = ?, descricao = ?, valor = ?, tamanho = ?, imagem = ? WHERE idsabor = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, saborDTO.getSabor());
            statement.setString(2, saborDTO.getDescricao());
            statement.setBigDecimal(3, saborDTO.getValor());
            statement.setString(4, saborDTO.getTamanho());
            statement.setString(5, saborDTO.getImagem());
            statement.setLong(6, saborDTO.getIdsabor());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao atualizar sabor de pizza.", e);
        }
    }
}
