package br.com.fiap.dao;

import br.com.fiap.to.ClienteTO;

import java.util.ArrayList;

/**
 * Contrato do DAO de cliente.
 *
 * Existem duas implementações:
 *   - ClienteDAO     -> grava de verdade no Oracle
 *   - ClienteDAOMock -> guarda numa lista em memória (para testar sem banco)
 *
 * Quem troca entre as duas é o ClienteBO.
 */
public interface IClienteDAO {
    ArrayList<ClienteTO> findAll();
    ClienteTO findByIdCliente(Long idCliente);
    ClienteTO save(ClienteTO cliente);
    ClienteTO update(ClienteTO cliente);
    boolean delete(Long idCliente);
}
