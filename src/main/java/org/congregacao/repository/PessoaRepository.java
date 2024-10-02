package org.congregacao.repository;

import org.congregacao.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    long countByPrivilegio_Nome(String nomePrivilegio);

    long countByPioneiro_Tipo(String tipoPioneiro);

    List<Pessoa> findByNomeContaining(String nome);

    List<Pessoa> findByGrupoId(Long grupoId);

    List<Pessoa> findByNomeContainingAndGrupoId(String nome, Long grupoId);
}
