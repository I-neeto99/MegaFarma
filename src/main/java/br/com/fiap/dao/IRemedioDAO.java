package br.com.fiap.dao;

import br.com.fiap.to.RemedioTO;

import java.util.ArrayList;

/**
 * Contrato do DAO de remédio.
 *
 * Existem duas implementações:
 *   - RemedioDAO     -> grava de verdade no Oracle
 *   - RemedioDAOMock -> guarda numa lista em memória (para testar sem banco)
 */
public interface IRemedioDAO {
    ArrayList<RemedioTO> findAll();
    RemedioTO findByCodigo(Long codigo);
    RemedioTO save(RemedioTO remedio);
    RemedioTO update(RemedioTO remedio);
    boolean delete(Long codigo);
}
