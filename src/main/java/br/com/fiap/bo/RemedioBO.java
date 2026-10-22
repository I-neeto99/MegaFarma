package br.com.fiap.bo;

import br.com.fiap.dao.IRemedioDAO;
import br.com.fiap.dao.RemedioDAO;
import br.com.fiap.dao.RemedioDAOMock;
import br.com.fiap.to.RemedioTO;
import java.util.ArrayList;

public class RemedioBO {

    // false -> Oracle de verdade (padrão). Os testes ligam o mock sozinhos.
    private static final boolean USAR_MOCK =
            Boolean.parseBoolean(System.getProperty("megafarma.mock", "false"));

    private IRemedioDAO remedioDAO;

    /** Devolve o DAO escolhido pela chave acima. */
    private IRemedioDAO getDAO() {
        return USAR_MOCK ? new RemedioDAOMock() : new RemedioDAO();
    }
    public ArrayList <RemedioTO> findAll(){
        remedioDAO = getDAO();
        // aqui se implementa as regras de negocios
        return remedioDAO.findAll();
    }

    public RemedioTO findByCodigo(Long codigo) {
        remedioDAO = getDAO();
        // aqui se implementa a regrra de negócio
        return remedioDAO.findByCodigo(codigo);
    }

    public RemedioTO save(RemedioTO remedio){
        remedioDAO = getDAO();
        //aqui se implementa a regra de negocios
        return remedioDAO.save(remedio);
    }
    public boolean delete(Long codigo) {
        remedioDAO = getDAO();
        // aqui se implementa a regra de negócios
        return remedioDAO.delete(codigo);
    }
    public RemedioTO update(RemedioTO remedio){
        remedioDAO = getDAO();
        // aqui se implementa a regra de negócios
        return remedioDAO.update(remedio);
    }
}