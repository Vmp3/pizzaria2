package projeto.pizzaria.repository.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import projeto.pizzaria.model.AccountRequestDTO;
import projeto.pizzaria.repository.AccountRepository;

@Repository
public class JdbcAccountRepository implements AccountRepository {

    private final DataSource dataSource;

    public JdbcAccountRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(AccountRequestDTO requestDTO) {
        String sql = "INSERT INTO accounts (cpf, nome, cep, endereco, numero, email, senha) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Verificar se o email já existe
            if (emailExists(requestDTO.getEmail())) {
                throw new IllegalStateException("O email já está cadastrado.");
            }

            // Verificar se o CPF já existe
            if (cpfExists(requestDTO.getCpf())) {
                throw new IllegalStateException("O CPF já está cadastrado.");
            }

            // Inserir o novo registro
            statement.setString(1, requestDTO.getCpf());
            statement.setString(2, requestDTO.getNome());
            statement.setString(3, requestDTO.getCep());
            statement.setString(4, requestDTO.getEndereco());
            statement.setString(5, requestDTO.getNumero());
            statement.setString(6, requestDTO.getEmail());
            statement.setString(7, requestDTO.getSenha());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao criar conta.", e);
        }
    }

    @Override
    public void update(AccountRequestDTO usuarioExistente) {
        String sql = "UPDATE accounts SET cep = ?, endereco = ?, numero = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, usuarioExistente.getCep());
            statement.setString(2, usuarioExistente.getEndereco());
            statement.setString(3, usuarioExistente.getNumero());
            statement.setLong(4, usuarioExistente.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new IllegalStateException("Falha ao atualizar o usuário. Nenhum registro atualizado.");
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao atualizar usuário.", e);
        }
    }


    @Override
    public boolean verifyCredentials(String cpf, String senha) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE cpf = ? AND senha = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cpf);
            statement.setString(2, senha);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao verificar credenciais.", e);
        }

        return false;
    }

    @Override
    public AccountRequestDTO findById(Long id) {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToAccount(resultSet);
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar conta por ID.", e);
        }

        return null;
    }

    @Override
    public boolean cpfExists(String cpf) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE cpf = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cpf);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao verificar se CPF existe.", e);
        }

        return false;
    }

    @Override
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao verificar se email existe.", e);
        }

        return false;
    }

    private AccountRequestDTO mapResultSetToAccount(ResultSet resultSet) throws SQLException {
        AccountRequestDTO account = new AccountRequestDTO();
        account.setId(resultSet.getLong("id"));
        account.setCpf(resultSet.getString("cpf"));
        account.setNome(resultSet.getString("nome"));
        account.setCep(resultSet.getString("cep"));
        account.setEndereco(resultSet.getString("endereco"));
        account.setNumero(resultSet.getString("numero"));
        account.setEmail(resultSet.getString("email"));
        account.setSenha(resultSet.getString("senha"));
        return account;
    }

    @Override
    public Long getUserIdByCredentials(String cpf, String senha) {
        String sql = "SELECT id FROM accounts WHERE cpf = ? AND senha = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cpf);
            statement.setString(2, senha);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("id");
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar ID do usuário por credenciais.", e);
        }

        return null;
    }

}
