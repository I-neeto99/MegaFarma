package br.com.fiap.bo;

import br.com.fiap.dao.ClienteDAO;
import br.com.fiap.to.ClienteTO;

import java.util.ArrayList;

public class ClienteBO {
    private ClienteDAO clienteDAO;
    public ArrayList<ClienteTO> findAll() {
        clienteDAO = new ClienteDAO();
        return clienteDAO.findAll();
    }
    public ClienteTO findByIdCliente(Long idCliente){
        clienteDAO = new ClienteDAO();
        return clienteDAO.findByIdCliente(idCliente);
    }
    public ClienteTO save(ClienteTO cliente){
        clienteDAO = new ClienteDAO();
        return clienteDAO.save(cliente);
    }
    public boolean delete(Long idCliente){
        clienteDAO = new ClienteDAO();
        return clienteDAO.delete(idCliente);
    }
    public ClienteTO update(ClienteTO cliente){
        clienteDAO = new ClienteDAO();
        return clienteDAO.update(cliente);
    }
}
