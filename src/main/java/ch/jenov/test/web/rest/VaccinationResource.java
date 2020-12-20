package ch.jenov.test.web.rest;

import ch.jenov.test.domain.Vaccination;
import ch.jenov.test.repository.VaccinationRepository;
import ch.jenov.test.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ch.jenov.test.domain.Vaccination}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VaccinationResource {

    private final Logger log = LoggerFactory.getLogger(VaccinationResource.class);

    private static final String ENTITY_NAME = "vaccination";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VaccinationRepository vaccinationRepository;

    public VaccinationResource(VaccinationRepository vaccinationRepository) {
        this.vaccinationRepository = vaccinationRepository;
    }

    /**
     * {@code POST  /vaccinations} : Create a new vaccination.
     *
     * @param vaccination the vaccination to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vaccination, or with status {@code 400 (Bad Request)} if the vaccination has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vaccinations")
    public ResponseEntity<Vaccination> createVaccination(@RequestBody Vaccination vaccination) throws URISyntaxException {
        log.debug("REST request to save Vaccination : {}", vaccination);
        if (vaccination.getId() != null) {
            throw new BadRequestAlertException("A new vaccination cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vaccination result = vaccinationRepository.save(vaccination);
        return ResponseEntity.created(new URI("/api/vaccinations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vaccinations} : Updates an existing vaccination.
     *
     * @param vaccination the vaccination to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vaccination,
     * or with status {@code 400 (Bad Request)} if the vaccination is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vaccination couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vaccinations")
    public ResponseEntity<Vaccination> updateVaccination(@RequestBody Vaccination vaccination) throws URISyntaxException {
        log.debug("REST request to update Vaccination : {}", vaccination);
        if (vaccination.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vaccination result = vaccinationRepository.save(vaccination);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vaccination.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vaccinations} : get all the vaccinations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vaccinations in body.
     */
    @GetMapping("/vaccinations")
    public List<Vaccination> getAllVaccinations() {
        log.debug("REST request to get all Vaccinations");
        return vaccinationRepository.findAll();
    }

    /**
     * {@code GET  /vaccinations/:id} : get the "id" vaccination.
     *
     * @param id the id of the vaccination to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vaccination, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vaccinations/{id}")
    public ResponseEntity<Vaccination> getVaccination(@PathVariable Long id) {
        log.debug("REST request to get Vaccination : {}", id);
        Optional<Vaccination> vaccination = vaccinationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vaccination);
    }

    /**
     * {@code DELETE  /vaccinations/:id} : delete the "id" vaccination.
     *
     * @param id the id of the vaccination to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vaccinations/{id}")
    public ResponseEntity<Void> deleteVaccination(@PathVariable Long id) {
        log.debug("REST request to delete Vaccination : {}", id);
        vaccinationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
