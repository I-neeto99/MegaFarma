package br.com.fiap.bo;

import br.com.fiap.dao.IVendasDAO;
import br.com.fiap.dao.VendasDAO;
import br.com.fiap.dao.VendasDAOMock;
import br.com.fiap.to.VendasTO;

import java.util.ArrayList;

public class VendasBO {

    // false -> Oracle de verdade (padrão). Os testes ligam o mock sozinhos.
    private static final boolean USAR_MOCK =
            Boolean.parseBoolean(System.getProperty("megafarma.mock", "false"));

    private IVendasDAO vendasDAO;

    /** Devolve o DAO escolhido pela chave acima. */
    private IVendasDAO getDAO() {
        return USAR_MOCK ? new VendasDAOMock() : new VendasDAO();
    }
    public ArrayList<VendasTO> findAll() {
        vendasDAO = getDAO();
        // aqui se implementa as regras de negocios
        return vendasDAO.findAll();
    }
    public VendasTO findByCodigo(Long codigo){
        vendasDAO = getDAO();
        // aqui se implementa a regra de negócio
        return vendasDAO.findByCodigo(codigo);
    }
    public VendasTO save(VendasTO venda){
        vendasDAO = getDAO();
        // aqui se implementa a regra de negócios
        return vendasDAO.save(venda);
    }
    public boolean delete(Long codigo){
        vendasDAO = getDAO();
        // aqui se implementa a regra de negócios
        return vendasDAO.delete(codigo);
    }
    public VendasTO update(VendasTO venda){
        vendasDAO = getDAO();
        // aqui se implementa a regra de negócios
        return vendasDAO.update(venda);
    }
}