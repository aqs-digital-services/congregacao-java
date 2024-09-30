package org.congregacao.domains.assistencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AssistenciaRepository extends JpaRepository<Assistencia, Long>, JpaSpecificationExecutor<Assistencia> {
}
