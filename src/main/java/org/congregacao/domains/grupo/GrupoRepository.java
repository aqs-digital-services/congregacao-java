package org.congregacao.domains.grupo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    List<Grupo> findBySuperintendenteId(Long superintendenteId);
}