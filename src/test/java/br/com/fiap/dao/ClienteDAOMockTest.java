package br.com.fiap.dao;

import br.com.fiap.to.ClienteTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa a lógica do CRUD de cliente, sem Spring e sem banco.
 * Cada teste cria o próprio cliente, então a ordem em que rodam não importa.
 */
class ClienteDAOMockTest {

    private final IClienteDAO dao = new ClienteDAOMock();

    /** Cria um cliente novo em memória (ainda sem id). */
    private ClienteTO clienteNovo(String nome) {
        return new ClienteTO(null, nome, "11122233344", "teste@email.com",
                LocalDate.of(1999, 7, 10));
    }

    // ---------- findAll ----------

    @Test
    @DisplayName("findAll: devolve a lista de clientes")
    void findAllDeveDevolverOsClientes() {
        ArrayList<ClienteTO> clientes = dao.findAll();

        assertNotNull(clientes, "findAll nunca pode devolver null");
        assertFalse(clientes.isEmpty(), "o mock começa com 3 clientes cadastrados");
    }

    @Test
    @DisplayName("findAll: a lista cresce depois de salvar")
    void findAllDeveCrescerAposSalvar() {
        int antes = dao.findAll().size();

        dao.save(clienteNovo("Cliente do teste findAll"));

        assertEquals(antes + 1, dao.findAll().size());
    }

    // ---------- save ----------

    @Test
    @DisplayName("save: grava o cliente e gera o id")
    void saveDeveGerarOId() {
        ClienteTO salvo = dao.save(clienteNovo("Cliente do teste save"));

        assertNotNull(salvo, "save deve devolver o cliente salvo");
        assertNotNull(salvo.getIdCliente(), "o id tem que ser gerado");
        assertEquals("Cliente do teste save", salvo.getNome());
    }

    @Test
    @DisplayName("save: ids gerados não se repetem")
    void saveNaoDeveRepetirId() {
        Long primeiro = dao.save(clienteNovo("Primeiro")).getIdCliente();
        Long segundo  = dao.save(clienteNovo("Segundo")).getIdCliente();

        assertNotEquals(primeiro, segundo);
    }

    // ---------- findByIdCliente ----------

    @Test
    @DisplayName("findByIdCliente: acha o cliente que acabou de ser salvo")
    void findByIdDeveAcharOCliente() {
        ClienteTO salvo = dao.save(clienteNovo("Cliente do teste findById"));

        ClienteTO encontrado = dao.findByIdCliente(salvo.getIdCliente());

        assertNotNull(encontrado);
        assertEquals(salvo.getIdCliente(), encontrado.getIdCliente());
        assertEquals("Cliente do teste findById", encontrado.getNome());
    }

    @Test
    @DisplayName("findByIdCliente: devolve null quando o id não existe")
    void findByIdDeveDevolverNullQuandoNaoExiste() {
        assertNull(dao.findByIdCliente(999999L));
    }

    // ---------- update ----------

    @Test
    @DisplayName("update: altera os dados do cliente")
    void updateDeveAlterarOsDados() {
        ClienteTO salvo = dao.save(clienteNovo("Nome antigo"));

        salvo.setNome("Nome novo");
        salvo.setEmail("novo@email.com");
        ClienteTO atualizado = dao.update(salvo);

        assertNotNull(atualizado, "update deve devolver o cliente atualizado");

        ClienteTO doBanco = dao.findByIdCliente(salvo.getIdCliente());
        assertEquals("Nome novo", doBanco.getNome());
        assertEquals("novo@email.com", doBanco.getEmail());
    }

    @Test
    @DisplayName("update: devolve null quando o cliente não existe")
    void updateDeveDevolverNullQuandoNaoExiste() {
        ClienteTO inexistente = clienteNovo("Fantasma");
        inexistente.setIdCliente(999999L);

        assertNull(dao.update(inexistente));
    }

    // ---------- delete ----------

    @Test
    @DisplayName("delete: apaga o cliente e ele some da busca")
    void deleteDeveApagarOCliente() {
        ClienteTO salvo = dao.save(clienteNovo("Cliente do teste delete"));

        assertTrue(dao.delete(salvo.getIdCliente()), "delete deve devolver true");
        assertNull(dao.findByIdCliente(salvo.getIdCliente()), "o cliente não pode mais ser encontrado");
    }

    @Test
    @DisplayName("delete: devolve false quando o id não existe")
    void deleteDeveDevolverFalseQuandoNaoExiste() {
        assertFalse(dao.delete(999999L));
    }
}
