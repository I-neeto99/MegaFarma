package br.com.fiap.bo;

import br.com.fiap.dao.VendasDAO;
import br.com.fiap.to.VendasTO;

import java.util.ArrayList;

public class VendasBO {
    private VendasDAO vendasDAO;
    public ArrayList<VendasTO> findAll() {
        vendasDAO = new VendasDAO();
        // aqui se implementa as regras de negocios
        return vendasDAO.findAll();
    }
    public VendasTO findByCodigo(Long codigo){
        vendasDAO = new VendasDAO();
        // aqui se implementa a regra de negócio
        return vendasDAO.findByCodigo(codigo);
    }
    public VendasTO save(VendasTO venda){
        vendasDAO = new VendasDAO();
        // aqui se implementa a regra de negócios
        return vendasDAO.save(venda);
    }
    public boolean delete(Long codigo){
        vendasDAO = new VendasDAO();
        // aqui se implementa a regra de negócios
        return vendasDAO.delete(codigo);
    }
    public VendasTO update(VendasTO venda){
        vendasDAO = new VendasDAO();
        // aqui se implementa a regra de negócios
        return vendasDAO.update(venda);
    }
}