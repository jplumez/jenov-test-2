package ch.jenov.test.repository;

import ch.jenov.test.domain.LotVaccin;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LotVaccin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LotVaccinRepository extends JpaRepository<LotVaccin, Long> {
}
