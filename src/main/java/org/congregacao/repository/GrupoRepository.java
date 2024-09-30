package org.congregacao.repository;

import org.congregacao.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    List<Grupo> findBySuperintendenteId(Long superintendenteId);
}