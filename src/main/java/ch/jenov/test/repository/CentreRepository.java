package ch.jenov.test.repository;

import ch.jenov.test.domain.Centre;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Centre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentreRepository extends JpaRepository<Centre, Long> {
}
