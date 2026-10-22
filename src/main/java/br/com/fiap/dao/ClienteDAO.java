package br.com.fiap.dao;

import br.com.fiap.to.ClienteTO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ClienteDAO {

    // ---------- métodos auxiliares ----------

    /** Monta um ClienteTO a partir de uma linha do banco, tratando campos nulos. */
    private ClienteTO montaCliente(ResultSet rs) throws SQLException {
        ClienteTO cliente = new ClienteTO();
        cliente.setIdCliente(rs.getLong("id_cliente"));
        cliente.setNome(rs.getString("nome"));
        cliente.setCpf(rs.getString("cpf"));
        cliente.setEmail(rs.getString("email"));
        cliente.setDataDeNascimento(toLocalDate(rs.getDate("data_de_nascimento")));
        return cliente;
    }

    /** Converte Date do banco para LocalDate, aceitando nulo. */
    private LocalDate toLocalDate(Date data) {
        return data != null ? data.toLocalDate() : null;
    }

    /** Converte LocalDate para Date do banco, aceitando nulo. */
    private Date toSqlDate(LocalDate data) {
        return data != null ? Date.valueOf(data) : null;
    }

    // ---------- CRUD ----------

    public ArrayList<ClienteTO> findAll() {
        ArrayList<ClienteTO> clientes = new ArrayList<>();
        String sql = "select * from ddd_clientes order by id_cliente";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clientes.add(montaCliente(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }

        return clientes;
    }

    public ClienteTO findByIdCliente(Long idCliente) {
        String sql = "select * from ddd_clientes where id_cliente = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return montaCliente(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cliente: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public ClienteTO save(ClienteTO cliente) {
        String sql = "insert into ddd_clientes (nome, cpf, email, data_de_nascimento) "
                + "values (?, ?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getEmail());
            ps.setDate(4, toSqlDate(cliente.getDataDeNascimento()));

            if (ps.executeUpdate() > 0) {
                return cliente;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public ClienteTO update(ClienteTO cliente) {
        String sql = "update ddd_clientes set nome = ?, cpf = ?, "
                + "email = ?, data_de_nascimento = ? where id_cliente = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getEmail());
            ps.setDate(4, toSqlDate(cliente.getDataDeNascimento()));
            ps.setLong(5, cliente.getIdCliente());

            if (ps.executeUpdate() > 0) {
                return cliente;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cliente: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public boolean delete(Long idCliente) {
        String sql = "delete from ddd_clientes where id_cliente = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idCliente);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar cliente: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }
    }
}
