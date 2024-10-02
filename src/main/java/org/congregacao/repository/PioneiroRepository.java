package org.congregacao.repository;

import org.congregacao.model.Pioneiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PioneiroRepository extends JpaRepository<Pioneiro, Long> {
    Optional<Pioneiro> findByTipo(String tipo);
}
