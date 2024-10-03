package projeto.pizzaria.repository.JDBC;

import org.springframework.stereotype.Repository;
import projeto.pizzaria.model.AccountRequestDTO;
import projeto.pizzaria.model.ItemPedidoRequestDTO;
import projeto.pizzaria.model.PedidoRequestDTO;
import projeto.pizzaria.model.SaboresRequestDTO;
import projeto.pizzaria.repository.PedidoRepository;
import projeto.pizzaria.repository.ItemPedidoRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcPedidoRepository implements PedidoRepository {
    private final DataSource dataSource;
    private final ItemPedidoRepository itemPedidoRepository;

    public JdbcPedidoRepository(DataSource dataSource, ItemPedidoRepository itemPedidoRepository) {
        this.dataSource = dataSource;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Override
    public void save(PedidoRequestDTO pedidoDTO) {
        String sql = "INSERT INTO pedidos (id_cliente, data_pedido, status, total) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ZoneId gmtMinus3 = ZoneId.of("America/Sao_Paulo");
            LocalDateTime dataPedido = pedidoDTO.getDataPedido();
            ZonedDateTime dataPedidoGMTMinus3 = dataPedido.atZone(ZoneId.systemDefault())
                    .withZoneSameInstant(gmtMinus3);

            Long idCliente = pedidoDTO.getIdCliente() != null ? pedidoDTO.getIdCliente() : 1L;
            statement.setLong(1, idCliente);
            statement.setTimestamp(2, Timestamp.valueOf(dataPedidoGMTMinus3.toLocalDateTime()));
            statement.setString(3, pedidoDTO.getStatus());
            statement.setBigDecimal(4, pedidoDTO.getTotal());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pedidoDTO.setIdPedido(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Falha ao obter o ID gerado para o pedido.");
                }
            }

            for (ItemPedidoRequestDTO item : pedidoDTO.getItensPedido()) {
                item.setPedido(pedidoDTO);
                itemPedidoRepository.save(item);
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao salvar pedido.", e);
        }
    }

    @Override
    public void update(PedidoRequestDTO pedidoDTO) {
        String sql = "UPDATE pedidos SET idCliente = ?, dataPedido = ?, status = ?, total = ? WHERE idPedido = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            Long idCliente = pedidoDTO.getIdCliente() != null ? pedidoDTO.getIdCliente() : 1L;
            statement.setLong(1, idCliente);
            statement.setTimestamp(2, Timestamp.valueOf(pedidoDTO.getDataPedido()));
            statement.setString(3, pedidoDTO.getStatus());
            statement.setBigDecimal(4, pedidoDTO.getTotal()); // Utiliza setBigDecimal para BigDecimal
            statement.setLong(5, pedidoDTO.getIdPedido());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao atualizar pedido.", e);
        }
    }

    @Override
    public List<PedidoRequestDTO> findAll() {
        List<PedidoRequestDTO> pedidos = new ArrayList<>();
        String sql = "SELECT " +
                "pedidos.id_pedido, " +
                "pedidos.id_cliente, " +
                "pedidos.data_pedido, " +
                "pedidos.status, " +
                "pedidos.total, " +
                "itens_pedido.id_item, " +
                "itens_pedido.tipo, " +
                "sabores.idsabor, " +
                "sabores.sabor, " +
                "accounts.id as account_id, " +
                "accounts.cpf as account_cpf, " +
                "accounts.email as account_email, " +
                "accounts.nome as account_nome, " +
                "accounts.cep as account_cep, " +
                "accounts.endereco as account_endereco, " +
                "accounts.numero as account_numero " +
                "FROM pedidos " +
                "INNER JOIN itens_pedido ON pedidos.id_pedido = itens_pedido.id_pedido " +
                "INNER JOIN sabores ON itens_pedido.id_sabor = sabores.idsabor " +
                "INNER JOIN accounts ON pedidos.id_cliente = accounts.id " +
                "ORDER BY pedidos.id_pedido, itens_pedido.id_item";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            Map<Long, PedidoRequestDTO> pedidosMap = new LinkedHashMap<>();

            while (resultSet.next()) {
                Long idPedido = resultSet.getLong("id_pedido");

                PedidoRequestDTO pedido = pedidosMap.get(idPedido);
                if (pedido == null) {
                    pedido = new PedidoRequestDTO();
                    pedido.setIdPedido(idPedido);
                    pedido.setIdCliente(resultSet.getLong("id_cliente"));
                    pedido.setDataPedido(resultSet.getTimestamp("data_pedido").toLocalDateTime());
                    pedido.setStatus(resultSet.getString("status"));
                    pedido.setTotal(resultSet.getBigDecimal("total"));
                    pedido.setItensPedido(new ArrayList<>());

                    AccountRequestDTO account = new AccountRequestDTO();
                    account.setId(resultSet.getLong("account_id"));
                    account.setCpf(resultSet.getString("account_cpf"));
                    account.setEmail(resultSet.getString("account_email"));
                    account.setNome(resultSet.getString("account_nome"));
                    account.setCep(resultSet.getString("account_cep"));
                    account.setEndereco(resultSet.getString("account_endereco"));
                    account.setNumero(resultSet.getString("account_numero"));

                    pedido.setAccount(account);

                    pedidosMap.put(idPedido, pedido);
                }

                ItemPedidoRequestDTO itemPedido = new ItemPedidoRequestDTO();
                itemPedido.setIdItem(resultSet.getLong("id_item"));
                itemPedido.setTipo(resultSet.getString("tipo"));

                SaboresRequestDTO sabor = new SaboresRequestDTO();
                sabor.setIdsabor(resultSet.getLong("idsabor"));
                sabor.setSabor(resultSet.getString("sabor"));
                itemPedido.setSabor(sabor);

                pedido.getItensPedido().add(itemPedido);
            }

            pedidos.addAll(pedidosMap.values());

        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao listar pedidos.", e);
        }
        return pedidos;
    }

    @Override
    public PedidoRequestDTO findById(Long idPedido) {
        String sql = "SELECT * FROM pedidos WHERE idPedido = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, idPedido);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    PedidoRequestDTO pedido = mapResultSetToPedido(resultSet);
                    pedido.setIdCliente(resultSet.getLong("idCliente"));
                    pedido.setItensPedido(itemPedidoRepository.findByPedidoId(pedido.getIdPedido()));
                    return pedido;
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar pedido por ID.", e);
        }
        return null;
    }

    @Override
    public void deleteById(Long idPedido) {
        String sql = "DELETE FROM pedidos WHERE idPedido = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, idPedido);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao remover pedido.", e);
        }
    }

    private PedidoRequestDTO mapResultSetToPedido(ResultSet resultSet) throws SQLException {
        PedidoRequestDTO pedido = new PedidoRequestDTO();
        pedido.setIdPedido(resultSet.getLong("idPedido"));
        pedido.setDataPedido(resultSet.getTimestamp("dataPedido").toLocalDateTime());
        pedido.setStatus(resultSet.getString("status"));
        pedido.setTotal(resultSet.getBigDecimal("total")); // Utiliza getBigDecimal para BigDecimal
        return pedido;
    }

}
