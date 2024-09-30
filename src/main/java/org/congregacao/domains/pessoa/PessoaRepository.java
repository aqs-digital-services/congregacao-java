package org.congregacao.domains.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, JpaSpecificationExecutor<Pessoa> {

    /**
     * Busca pessoas cujo nome contém a string fornecida (case-insensitive).
     *
     * @param nome O trecho do nome a ser buscado.
     * @param pageable Informações de paginação.
     * @return Página de pessoas que correspondem à busca.
     */
    Page<Pessoa> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    /**
     * Opcional: Busca pessoas cujo nome começa com a string fornecida (case-insensitive).
     *
     * @param nome O prefixo do nome a ser buscado.
     * @param pageable Informações de paginação.
     * @return Página de pessoas que correspondem à busca.
     */
    Page<Pessoa> findByNomeStartingWithIgnoreCase(String nome, Pageable pageable);
}
