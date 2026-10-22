package br.com.fiap.bo;

import br.com.fiap.dao.ItensVendidosDAO;
import br.com.fiap.to.ItensVendidosTO;

import java.util.ArrayList;

public class ItensVendidosBO {
    private ItensVendidosDAO itensVendidosDAO;

    public ArrayList<ItensVendidosTO> findAll() {
        itensVendidosDAO = new ItensVendidosDAO();
        return itensVendidosDAO.findAll();
    }

    public ArrayList<ItensVendidosTO> findByVenda(Long codVenda) {
        itensVendidosDAO = new ItensVendidosDAO();
        return itensVendidosDAO.findByVenda(codVenda);
    }

    public ItensVendidosTO findByCodigo(Long codVenda, Long codRemedio) {
        itensVendidosDAO = new ItensVendidosDAO();

        return itensVendidosDAO.findByCodigo(codVenda, codRemedio);
    }

    public ItensVendidosTO save(ItensVendidosTO item) {
        itensVendidosDAO = new ItensVendidosDAO();
        return itensVendidosDAO.save(item);
    }

    public boolean delete(Long codVenda, Long codRemedio) {
        itensVendidosDAO = new ItensVendidosDAO();
        return itensVendidosDAO.delete(codVenda, codRemedio);
    }

    public ItensVendidosTO update(ItensVendidosTO item) {
        itensVendidosDAO = new ItensVendidosDAO();
        return itensVendidosDAO.update(item);
    }
}