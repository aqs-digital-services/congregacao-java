package org.congregacao.repository;

import org.congregacao.model.Privilegio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivilegioRepository extends JpaRepository<Privilegio, Long> {
    Optional<Privilegio> findByNome(String nome);
}
