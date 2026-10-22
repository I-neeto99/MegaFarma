package br.com.fiap.dao;

import br.com.fiap.to.VendasTO;

import java.util.ArrayList;

/**
 * Contrato do DAO de venda.
 *
 * Existem duas implementações:
 *   - VendasDAO     -> grava de verdade no Oracle
 *   - VendasDAOMock -> guarda numa lista em memória (para testar sem banco)
 *
 * Quem troca entre as duas é o VendasBO.
 */
public interface IVendasDAO {
    ArrayList<VendasTO> findAll();
    VendasTO findByCodigo(Long codigo);
    VendasTO save(VendasTO venda);
    VendasTO update(VendasTO venda);
    boolean delete(Long codigo);
}
