package br.com.fiap.dao;

import br.com.fiap.to.VendasTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * DAO de mentira: guarda as vendas numa lista em memória,
 * sem precisar de Oracle nenhum.
 *
 * ATENÇÃO: os dados somem quando a aplicação para. É só para teste.
 */
public class VendasDAOMock implements IVendasDAO {

    // static = a lista é a mesma para todas as requisições.
    private static final ArrayList<VendasTO> VENDAS = new ArrayList<>();

    // Faz o papel da sequence do Oracle: gera 1, 2, 3...
    private static final AtomicLong GERADOR_DE_ID = new AtomicLong(0);

    // Duas vendas já cadastradas, para o primeiro GET não vir vazio.
    static {
        novaVenda(LocalDate.of(2026, 10, 20), 1L);
        novaVenda(LocalDate.of(2026, 10, 21), 2L);
    }

    private static void novaVenda(LocalDate data, Long codCliente) {
        VENDAS.add(new VendasTO(GERADOR_DE_ID.incrementAndGet(), data, codCliente));
    }

    @Override
    public ArrayList<VendasTO> findAll() {
        return new ArrayList<>(VENDAS);
    }

    @Override
    public VendasTO findByCodigo(Long codigo) {
        for (VendasTO venda : VENDAS) {
            if (venda.getCodigo().equals(codigo)) {
                return venda;
            }
        }
        return null;
    }

    @Override
    public VendasTO save(VendasTO venda) {
        venda.setCodigo(GERADOR_DE_ID.incrementAndGet());
        VENDAS.add(venda);
        return venda;
    }

    @Override
    public VendasTO update(VendasTO venda) {
        for (int i = 0; i < VENDAS.size(); i++) {
            if (VENDAS.get(i).getCodigo().equals(venda.getCodigo())) {
                VENDAS.set(i, venda);
                return venda;
            }
        }
        return null;
    }

    @Override
    public boolean delete(Long codigo) {
        return VENDAS.removeIf(venda -> venda.getCodigo().equals(codigo));
    }
}
