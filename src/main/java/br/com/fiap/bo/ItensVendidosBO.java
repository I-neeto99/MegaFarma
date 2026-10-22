package br.com.fiap.bo;

import br.com.fiap.dao.IItensVendidosDAO;
import br.com.fiap.dao.ItensVendidosDAO;
import br.com.fiap.dao.ItensVendidosDAOMock;
import br.com.fiap.to.ItensVendidosTO;

import java.util.ArrayList;

public class ItensVendidosBO {

    // false -> Oracle de verdade (padrão). Os testes ligam o mock sozinhos.
    private static final boolean USAR_MOCK =
            Boolean.parseBoolean(System.getProperty("megafarma.mock", "false"));

    private IItensVendidosDAO itensVendidosDAO;

    /** Devolve o DAO escolhido pela chave acima. */
    private IItensVendidosDAO getDAO() {
        return USAR_MOCK ? new ItensVendidosDAOMock() : new ItensVendidosDAO();
    }

    public ArrayList<ItensVendidosTO> findAll() {
        itensVendidosDAO = getDAO();
        // aqui se implementa as regras de negocios
        return itensVendidosDAO.findAll();
    }
    public ArrayList<ItensVendidosTO> findByVenda(Long codVenda) {
        itensVendidosDAO = getDAO();
        // aqui se implementa a regra de negócios
        return itensVendidosDAO.findByVenda(codVenda);
    }
    public ItensVendidosTO findByCodigo(Long codVenda, Long codRemedio) {
        itensVendidosDAO = getDAO();
        // aqui se implementa a regra de negócio
        return itensVendidosDAO.findByCodigo(codVenda, codRemedio);
    }
    public ItensVendidosTO save(ItensVendidosTO item) {
        itensVendidosDAO = getDAO();
        // aqui se implementa a regra de negócios
        return itensVendidosDAO.save(item);
    }
    public boolean delete(Long codVenda, Long codRemedio) {
        itensVendidosDAO = getDAO();
        // aqui se implementa a regra de negócios
        return itensVendidosDAO.delete(codVenda, codRemedio);
    }
    public ItensVendidosTO update(ItensVendidosTO item) {
        itensVendidosDAO = getDAO();
        // aqui se implementa a regra de negócios
        return itensVendidosDAO.update(item);
    }
}
