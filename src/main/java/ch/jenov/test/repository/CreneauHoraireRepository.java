package ch.jenov.test.repository;

import ch.jenov.test.domain.CreneauHoraire;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CreneauHoraire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreneauHoraireRepository extends JpaRepository<CreneauHoraire, Long> {
}
