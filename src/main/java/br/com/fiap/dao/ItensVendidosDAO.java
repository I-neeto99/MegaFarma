package br.com.fiap.dao;

import br.com.fiap.to.ItensVendidosTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItensVendidosDAO implements IItensVendidosDAO {

    // ---------- métodos auxiliares ----------

    /** Monta um ItensVendidosTO a partir de uma linha do banco. */
    private ItensVendidosTO montaItem(ResultSet rs) throws SQLException {
        ItensVendidosTO item = new ItensVendidosTO();
        item.setCodVenda(rs.getLong("codvenda"));
        item.setCodRemedio(rs.getLong("codremedio"));
        item.setQuantidade(rs.getInt("quantidade"));
        return item;
    }

    // ---------- CRUD ----------

    public ArrayList<ItensVendidosTO> findAll() {
        ArrayList<ItensVendidosTO> itens = new ArrayList<>();
        String sql = "select * from ddd_itensvendidos order by codvenda, codremedio";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itens.add(montaItem(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens vendidos: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }

        return itens;
    }

    /** Lista todos os remédios de uma venda. */
    public ArrayList<ItensVendidosTO> findByVenda(Long codVenda) {
        ArrayList<ItensVendidosTO> itens = new ArrayList<>();
        String sql = "select * from ddd_itensvendidos where codvenda = ? order by codremedio";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codVenda);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itens.add(montaItem(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens da venda: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }

        return itens;
    }

    /** A chave é composta, por isso recebe os dois códigos. */
    public ItensVendidosTO findByCodigo(Long codVenda, Long codRemedio) {
        String sql = "select * from ddd_itensvendidos where codvenda = ? and codremedio = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codVenda);
            ps.setLong(2, codRemedio);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return montaItem(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar item vendido: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public ItensVendidosTO save(ItensVendidosTO item) {
        // Aqui os três campos vão no insert: a chave não é gerada pelo banco,
        // ela vem da venda e do remédio que o usuário informou.
        String sql = "insert into ddd_itensvendidos (codvenda, codremedio, quantidade) "
                + "values (?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, item.getCodVenda());
            ps.setLong(2, item.getCodRemedio());
            ps.setInt(3, item.getQuantidade());

            if (ps.executeUpdate() > 0) {
                return item;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar item vendido: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public ItensVendidosTO update(ItensVendidosTO item) {
        // Só a quantidade muda: alterar a chave seria trocar de item, não atualizar.
        String sql = "update ddd_itensvendidos set quantidade = ? "
                + "where codvenda = ? and codremedio = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setInt(1, item.getQuantidade());
            ps.setLong(2, item.getCodVenda());
            ps.setLong(3, item.getCodRemedio());

            if (ps.executeUpdate() > 0) {
                return item;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar item vendido: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public boolean delete(Long codVenda, Long codRemedio) {
        String sql = "delete from ddd_itensvendidos where codvenda = ? and codremedio = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codVenda);
            ps.setLong(2, codRemedio);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar item vendido: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }
    }
}
