package br.com.fiap.dao;

import br.com.fiap.to.RemedioTO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class RemedioDAO {

    // métodos auxiliares

    /** Monta um RemedioTO a partir de uma linha do banco, tratando campos nulos. */
    private RemedioTO montaRemedio(ResultSet rs) throws SQLException {
        RemedioTO remedio = new RemedioTO();
        remedio.setCodigo(rs.getLong("codigo"));
        remedio.setNome(rs.getString("nome"));
        remedio.setPreco(rs.getDouble("preco"));
        remedio.setDataDeFabricacao(toLocalDate(rs.getDate("data_de_fabricacao")));
        remedio.setDataDeValidade(toLocalDate(rs.getDate("data_de_validade")));
        return remedio;
    }

    /** Converte Date do banco para LocalDate, aceitando nulo. */
    private LocalDate toLocalDate(Date data) {
        return data != null ? data.toLocalDate() : null;
    }

    /** Converte LocalDate para Date do banco, aceitando nulo. */
    private Date toSqlDate(LocalDate data) {
        return data != null ? Date.valueOf(data) : null;
    }



    public ArrayList<RemedioTO> findAll() {
        ArrayList<RemedioTO> remedios = new ArrayList<>();
        String sql = "select * from ddd_remedios order by codigo";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                remedios.add(montaRemedio(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar remédios: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }

        return remedios;
    }

    public RemedioTO findByCodigo(Long codigo) {
        String sql = "select * from ddd_remedios where codigo = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return montaRemedio(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar remédio: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public RemedioTO save(RemedioTO remedio) {
        String sql = "insert into ddd_remedios (nome, preco, data_de_fabricacao, data_de_validade) "
                + "values (?, ?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, remedio.getNome());
            ps.setDouble(2, remedio.getPreco());
            ps.setDate(3, toSqlDate(remedio.getDataDeFabricacao()));
            ps.setDate(4, toSqlDate(remedio.getDataDeValidade()));

            if (ps.executeUpdate() > 0) {
                return remedio;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar remédio: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public RemedioTO update(RemedioTO remedio) {
        String sql = "update ddd_remedios set nome = ?, preco = ?, "
                + "data_de_fabricacao = ?, data_de_validade = ? where codigo = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, remedio.getNome());
            ps.setDouble(2, remedio.getPreco());
            ps.setDate(3, toSqlDate(remedio.getDataDeFabricacao()));
            ps.setDate(4, toSqlDate(remedio.getDataDeValidade()));
            ps.setLong(5, remedio.getCodigo());

            if (ps.executeUpdate() > 0) {
                return remedio;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar remédio: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public boolean delete(Long codigo) {
        String sql = "delete from ddd_remedios where codigo = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codigo);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar remédio: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection();
        }
    }
}