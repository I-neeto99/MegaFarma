package br.com.fiap.bo;

import br.com.fiap.dao.ClienteDAO;
import br.com.fiap.dao.ClienteDAOMock;
import br.com.fiap.dao.IClienteDAO;
import br.com.fiap.to.ClienteTO;

import java.util.ArrayList;

public class ClienteBO {

    // ============================================================
    //  A CHAVE DO MOCK
    //
    //  false -> usa o ClienteDAO     (Oracle de verdade)  <- padrão
    //  true  -> usa o ClienteDAOMock (lista em memória, sem banco)
    //
    //  Para ligar o mock sem mexer no código, rode assim:
    //      ./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dmegafarma.mock=true"
    //
    //  Os testes automáticos ligam sozinhos.
    // ============================================================
    private static final boolean USAR_MOCK =
            Boolean.parseBoolean(System.getProperty("megafarma.mock", "false"));

    private IClienteDAO clienteDAO;

    /** Devolve o DAO escolhido pela chave acima. */
    private IClienteDAO getDAO() {
        return USAR_MOCK ? new ClienteDAOMock() : new ClienteDAO();
    }

    public ArrayList<ClienteTO> findAll() {
        clienteDAO = getDAO();
        return clienteDAO.findAll();
    }
    public ClienteTO findByIdCliente(Long idCliente){
        clienteDAO = getDAO();
        return clienteDAO.findByIdCliente(idCliente);
    }
    public ClienteTO save(ClienteTO cliente){
        clienteDAO = getDAO();
        return clienteDAO.save(cliente);
    }
    public boolean delete(Long idCliente){
        clienteDAO = getDAO();
        return clienteDAO.delete(idCliente);
    }
    public ClienteTO update(ClienteTO cliente){
        clienteDAO = getDAO();
        return clienteDAO.update(cliente);
    }
}
