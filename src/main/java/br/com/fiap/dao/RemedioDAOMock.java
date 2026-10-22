package br.com.fiap.dao;

import br.com.fiap.to.RemedioTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * DAO de mentira: guarda os remédios numa lista em memória.
 * ATENÇÃO: os dados somem quando a aplicação para. É só para teste.
 */
public class RemedioDAOMock implements IRemedioDAO {

    private static final ArrayList<RemedioTO> REMEDIOS = new ArrayList<>();
    private static final AtomicLong GERADOR_DE_ID = new AtomicLong(0);

    static {
        novoRemedio("Dipirona 500mg", 12.90, LocalDate.of(2026, 1, 10), LocalDate.of(2028, 1, 10));
        novoRemedio("Amoxicilina 500mg", 34.50, LocalDate.of(2026, 2, 1), LocalDate.of(2027, 8, 1));
        novoRemedio("Paracetamol 750mg", 9.80, LocalDate.of(2026, 3, 5), LocalDate.of(2029, 3, 5));
    }

    private static void novoRemedio(String nome, Double preco, LocalDate fabricacao, LocalDate validade) {
        REMEDIOS.add(new RemedioTO(GERADOR_DE_ID.incrementAndGet(), nome, preco, fabricacao, validade));
    }

    @Override
    public ArrayList<RemedioTO> findAll() {
        return new ArrayList<>(REMEDIOS);
    }

    @Override
    public RemedioTO findByCodigo(Long codigo) {
        for (RemedioTO remedio : REMEDIOS) {
            if (remedio.getCodigo().equals(codigo)) {
                return remedio;
            }
        }
        return null;
    }

    @Override
    public RemedioTO save(RemedioTO remedio) {
        remedio.setCodigo(GERADOR_DE_ID.incrementAndGet());
        REMEDIOS.add(remedio);
        return remedio;
    }

    @Override
    public RemedioTO update(RemedioTO remedio) {
        for (int i = 0; i < REMEDIOS.size(); i++) {
            if (REMEDIOS.get(i).getCodigo().equals(remedio.getCodigo())) {
                REMEDIOS.set(i, remedio);
                return remedio;
            }
        }
        return null;
    }

    @Override
    public boolean delete(Long codigo) {
        return REMEDIOS.removeIf(remedio -> remedio.getCodigo().equals(codigo));
    }
}
