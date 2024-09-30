package org.congregacao.repository;

import org.congregacao.model.Assistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AssistenciaRepository extends JpaRepository<Assistencia, Long>, JpaSpecificationExecutor<Assistencia> {
}
