package br.com.fiap.dao;

import br.com.fiap.to.ClienteTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * DAO de mentira: guarda os clientes numa lista em memória,
 * sem precisar de Oracle nenhum.
 *
 * ATENÇÃO: os dados somem quando a aplicação para. É só para teste.
 */
public class ClienteDAOMock implements IClienteDAO {

    // static = a lista é a mesma para todas as requisições.
    // Sem o static, cada "new ClienteDAOMock()" criaria uma lista vazia
    // e o cliente salvo no POST sumiria no GET seguinte.
    private static final ArrayList<ClienteTO> CLIENTES = new ArrayList<>();

    // Faz o papel da sequence do Oracle: gera 1, 2, 3...
    private static final AtomicLong GERADOR_DE_ID = new AtomicLong(0);

    // Três clientes já cadastrados, para o primeiro GET não vir vazio.
    static {
        novoCliente("Maria Silva",  "12345678901", "maria@email.com",  LocalDate.of(1990, 5, 20));
        novoCliente("João Pereira", "98765432100", "joao@email.com",   LocalDate.of(1985, 11, 3));
        novoCliente("Ana Souza",    "45678912300", "ana@email.com",    LocalDate.of(2000, 1, 15));
    }

    private static void novoCliente(String nome, String cpf, String email, LocalDate nascimento) {
        CLIENTES.add(new ClienteTO(GERADOR_DE_ID.incrementAndGet(), nome, cpf, email, nascimento));
    }

    @Override
    public ArrayList<ClienteTO> findAll() {
        return new ArrayList<>(CLIENTES);
    }

    @Override
    public ClienteTO findByIdCliente(Long idCliente) {
        for (ClienteTO cliente : CLIENTES) {
            if (cliente.getIdCliente().equals(idCliente)) {
                return cliente;
            }
        }
        return null;
    }

    @Override
    public ClienteTO save(ClienteTO cliente) {
        cliente.setIdCliente(GERADOR_DE_ID.incrementAndGet());
        CLIENTES.add(cliente);
        return cliente;
    }

    @Override
    public ClienteTO update(ClienteTO cliente) {
        for (int i = 0; i < CLIENTES.size(); i++) {
            if (CLIENTES.get(i).getIdCliente().equals(cliente.getIdCliente())) {
                CLIENTES.set(i, cliente);
                return cliente;
            }
        }
        return null;
    }

    @Override
    public boolean delete(Long idCliente) {
        return CLIENTES.removeIf(cliente -> cliente.getIdCliente().equals(idCliente));
    }
}
