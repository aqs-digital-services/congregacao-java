package org.congregacao.repository;

import org.congregacao.model.Assistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AssistenciaRepository extends JpaRepository<Assistencia, Long> {

    Optional<Assistencia> findByData(LocalDate data);
}
