package ch.jenov.test.repository;

import ch.jenov.test.domain.Vaccination;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Vaccination entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {
}
