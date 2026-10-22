package br.com.fiap.dao;

import br.com.fiap.to.VendasTO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;

public class VendasDAO implements IVendasDAO {

    //  métodos auxiliares

    /** Monta um VendasTO a partir de uma linha do banco, tratando campos nulos. */
    private VendasTO montaVenda(ResultSet rs) throws SQLException {
        VendasTO venda = new VendasTO();
        venda.setCodigo(rs.getLong("codigo"));
        venda.setDataDaVenda(toLocalDate(rs.getDate("data_da_venda")));

        // getLong devolve 0 quando a coluna é nula, por isso o wasNull.
        long codCliente = rs.getLong("codcliente");
        venda.setCodCliente(rs.wasNull() ? null : codCliente);

        return venda;
    }

    /** Converte Date do banco para LocalDate, aceitando nulo. */
    private LocalDate toLocalDate(Date data) {
        return data != null ? data.toLocalDate() : null;
    }

    /** Converte LocalDate para Date do banco, aceitando nulo. */
    private Date toSqlDate(LocalDate data) {
        return data != null ? Date.valueOf(data) : null;
    }

    /** Preenche um número na consulta, aceitando nulo. */
    private void setLongOuNulo(PreparedStatement ps, int posicao, Long valor) throws SQLException {
        if (valor != null) {
            ps.setLong(posicao, valor);
        } else {
            ps.setNull(posicao, Types.NUMERIC);
        }
    }



    public ArrayList<VendasTO> findAll() {
        ArrayList<VendasTO> vendas = new ArrayList<>();
        String sql = "select * from ddd_vendas order by codigo";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vendas.add(montaVenda(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vendas: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }

        return vendas;
    }

    public VendasTO findByCodigo(Long codigo) {
        String sql = "select * from ddd_vendas where codigo = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return montaVenda(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar venda: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public VendasTO save(VendasTO venda) {
        String sql = "insert into ddd_vendas (data_da_venda, codcliente) "
                + "values (?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setDate(1, toSqlDate(venda.getDataDaVenda()));
            setLongOuNulo(ps, 2, venda.getCodCliente());

            if (ps.executeUpdate() > 0) {
                return venda;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar venda: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public VendasTO update(VendasTO venda) {
        String sql = "update ddd_vendas set data_da_venda = ?, "
                + "codcliente = ? where codigo = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setDate(1, toSqlDate(venda.getDataDaVenda()));
            setLongOuNulo(ps, 2, venda.getCodCliente());
            ps.setLong(3, venda.getCodigo());

            if (ps.executeUpdate() > 0) {
                return venda;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar venda: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public boolean delete(Long codigo) {
        String sql = "delete from ddd_vendas where codigo = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codigo);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar venda: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }
    }
}