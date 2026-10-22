package br.com.fiap.dao;

import br.com.fiap.to.ItensVendidosTO;

import java.util.ArrayList;

/**
 * DAO de mentira: guarda os itens numa lista em memória.
 * ATENÇÃO: os dados somem quando a aplicação para. É só para teste.
 */
public class ItensVendidosDAOMock implements IItensVendidosDAO {

    private static final ArrayList<ItensVendidosTO> ITENS = new ArrayList<>();

    static {
        ITENS.add(new ItensVendidosTO(1L, 1L, 2));
        ITENS.add(new ItensVendidosTO(1L, 2L, 1));
        ITENS.add(new ItensVendidosTO(2L, 3L, 5));
    }

    /** O item é identificado pelo PAR de códigos, não por um só. */
    private boolean mesmaChave(ItensVendidosTO item, Long codVenda, Long codRemedio) {
        return item.getCodVenda().equals(codVenda) && item.getCodRemedio().equals(codRemedio);
    }

    @Override
    public ArrayList<ItensVendidosTO> findAll() {
        return new ArrayList<>(ITENS);
    }

    @Override
    public ArrayList<ItensVendidosTO> findByVenda(Long codVenda) {
        ArrayList<ItensVendidosTO> daVenda = new ArrayList<>();
        for (ItensVendidosTO item : ITENS) {
            if (item.getCodVenda().equals(codVenda)) {
                daVenda.add(item);
            }
        }
        return daVenda;
    }

    @Override
    public ItensVendidosTO findByCodigo(Long codVenda, Long codRemedio) {
        for (ItensVendidosTO item : ITENS) {
            if (mesmaChave(item, codVenda, codRemedio)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public ItensVendidosTO save(ItensVendidosTO item) {
        // A chave primária composta não aceita repetido: é o ORA-00001 do Oracle.
        if (findByCodigo(item.getCodVenda(), item.getCodRemedio()) != null) {
            return null;
        }
        ITENS.add(item);
        return item;
    }

    @Override
    public ItensVendidosTO update(ItensVendidosTO item) {
        for (int i = 0; i < ITENS.size(); i++) {
            if (mesmaChave(ITENS.get(i), item.getCodVenda(), item.getCodRemedio())) {
                ITENS.set(i, item);
                return item;
            }
        }
        return null;
    }

    @Override
    public boolean delete(Long codVenda, Long codRemedio) {
        return ITENS.removeIf(item -> mesmaChave(item, codVenda, codRemedio));
    }
}
