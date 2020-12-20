package ch.jenov.test.repository;

import ch.jenov.test.domain.Localite;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Localite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocaliteRepository extends JpaRepository<Localite, Long> {
}
