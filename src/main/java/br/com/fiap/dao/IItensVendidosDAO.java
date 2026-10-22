package br.com.fiap.dao;

import br.com.fiap.to.ItensVendidosTO;

import java.util.ArrayList;

/**
 * Contrato do DAO de itens vendidos.
 *
 *   - ItensVendidosDAO     -> grava de verdade no Oracle
 *   - ItensVendidosDAOMock -> guarda numa lista em memória (para testar sem banco)
 */
public interface IItensVendidosDAO {
    ArrayList<ItensVendidosTO> findAll();
    ArrayList<ItensVendidosTO> findByVenda(Long codVenda);
    ItensVendidosTO findByCodigo(Long codVenda, Long codRemedio);
    ItensVendidosTO save(ItensVendidosTO item);
    ItensVendidosTO update(ItensVendidosTO item);
    boolean delete(Long codVenda, Long codRemedio);
}
