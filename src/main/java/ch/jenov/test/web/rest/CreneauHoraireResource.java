package ch.jenov.test.web.rest;

import ch.jenov.test.domain.CreneauHoraire;
import ch.jenov.test.repository.CreneauHoraireRepository;
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
 * REST controller for managing {@link ch.jenov.test.domain.CreneauHoraire}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CreneauHoraireResource {

    private final Logger log = LoggerFactory.getLogger(CreneauHoraireResource.class);

    private static final String ENTITY_NAME = "creneauHoraire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreneauHoraireRepository creneauHoraireRepository;

    public CreneauHoraireResource(CreneauHoraireRepository creneauHoraireRepository) {
        this.creneauHoraireRepository = creneauHoraireRepository;
    }

    /**
     * {@code POST  /creneau-horaires} : Create a new creneauHoraire.
     *
     * @param creneauHoraire the creneauHoraire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new creneauHoraire, or with status {@code 400 (Bad Request)} if the creneauHoraire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/creneau-horaires")
    public ResponseEntity<CreneauHoraire> createCreneauHoraire(@RequestBody CreneauHoraire creneauHoraire) throws URISyntaxException {
        log.debug("REST request to save CreneauHoraire : {}", creneauHoraire);
        if (creneauHoraire.getId() != null) {
            throw new BadRequestAlertException("A new creneauHoraire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreneauHoraire result = creneauHoraireRepository.save(creneauHoraire);
        return ResponseEntity.created(new URI("/api/creneau-horaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /creneau-horaires} : Updates an existing creneauHoraire.
     *
     * @param creneauHoraire the creneauHoraire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creneauHoraire,
     * or with status {@code 400 (Bad Request)} if the creneauHoraire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the creneauHoraire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/creneau-horaires")
    public ResponseEntity<CreneauHoraire> updateCreneauHoraire(@RequestBody CreneauHoraire creneauHoraire) throws URISyntaxException {
        log.debug("REST request to update CreneauHoraire : {}", creneauHoraire);
        if (creneauHoraire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CreneauHoraire result = creneauHoraireRepository.save(creneauHoraire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creneauHoraire.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /creneau-horaires} : get all the creneauHoraires.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of creneauHoraires in body.
     */
    @GetMapping("/creneau-horaires")
    public List<CreneauHoraire> getAllCreneauHoraires() {
        log.debug("REST request to get all CreneauHoraires");
        return creneauHoraireRepository.findAll();
    }

    /**
     * {@code GET  /creneau-horaires/:id} : get the "id" creneauHoraire.
     *
     * @param id the id of the creneauHoraire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the creneauHoraire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/creneau-horaires/{id}")
    public ResponseEntity<CreneauHoraire> getCreneauHoraire(@PathVariable Long id) {
        log.debug("REST request to get CreneauHoraire : {}", id);
        Optional<CreneauHoraire> creneauHoraire = creneauHoraireRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(creneauHoraire);
    }

    /**
     * {@code DELETE  /creneau-horaires/:id} : delete the "id" creneauHoraire.
     *
     * @param id the id of the creneauHoraire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/creneau-horaires/{id}")
    public ResponseEntity<Void> deleteCreneauHoraire(@PathVariable Long id) {
        log.debug("REST request to delete CreneauHoraire : {}", id);
        creneauHoraireRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
