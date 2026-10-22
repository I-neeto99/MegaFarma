package br.com.fiap.bo;

import br.com.fiap.dao.IItensVendidosDAO;
import br.com.fiap.dao.ItensVendidosDAO;
import br.com.fiap.dao.ItensVendidosDAOMock;
import br.com.fiap.to.ItensVendidosTO;

import java.util.ArrayList;

public class ItensVendidosBO {
    private static final boolean USAR_MOCK =
            Boolean.parseBoolean(System.getProperty("megafarma.mock", "false"));

    private IItensVendidosDAO itensVendidosDAO;

    private IItensVendidosDAO getDAO() {
        return USAR_MOCK ? new ItensVendidosDAOMock() : new ItensVendidosDAO();
    }

    public ArrayList<ItensVendidosTO> findAll() {
        itensVendidosDAO = getDAO();
        return itensVendidosDAO.findAll();
    }

    public ArrayList<ItensVendidosTO> findByVenda(Long codVenda) {
        itensVendidosDAO = getDAO();
        return itensVendidosDAO.findByVenda(codVenda);
    }

    public ItensVendidosTO findByCodigo(Long codVenda, Long codRemedio) {
        itensVendidosDAO = getDAO();

        return itensVendidosDAO.findByCodigo(codVenda, codRemedio);
    }

    public ItensVendidosTO save(ItensVendidosTO item) {
        itensVendidosDAO = getDAO();
        return itensVendidosDAO.save(item);
    }

    public boolean delete(Long codVenda, Long codRemedio) {
        itensVendidosDAO = getDAO();
        return itensVendidosDAO.delete(codVenda, codRemedio);
    }

    public ItensVendidosTO update(ItensVendidosTO item) {
        itensVendidosDAO = getDAO();
        return itensVendidosDAO.update(item);
    }
}