package ch.jenov.test.web.rest;

import ch.jenov.test.domain.Localite;
import ch.jenov.test.repository.LocaliteRepository;
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
 * REST controller for managing {@link ch.jenov.test.domain.Localite}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LocaliteResource {

    private final Logger log = LoggerFactory.getLogger(LocaliteResource.class);

    private static final String ENTITY_NAME = "localite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocaliteRepository localiteRepository;

    public LocaliteResource(LocaliteRepository localiteRepository) {
        this.localiteRepository = localiteRepository;
    }

    /**
     * {@code POST  /localites} : Create a new localite.
     *
     * @param localite the localite to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new localite, or with status {@code 400 (Bad Request)} if the localite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/localites")
    public ResponseEntity<Localite> createLocalite(@RequestBody Localite localite) throws URISyntaxException {
        log.debug("REST request to save Localite : {}", localite);
        if (localite.getId() != null) {
            throw new BadRequestAlertException("A new localite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Localite result = localiteRepository.save(localite);
        return ResponseEntity.created(new URI("/api/localites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /localites} : Updates an existing localite.
     *
     * @param localite the localite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localite,
     * or with status {@code 400 (Bad Request)} if the localite is not valid,
     * or with status {@code 500 (Internal Server Error)} if the localite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/localites")
    public ResponseEntity<Localite> updateLocalite(@RequestBody Localite localite) throws URISyntaxException {
        log.debug("REST request to update Localite : {}", localite);
        if (localite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Localite result = localiteRepository.save(localite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, localite.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /localites} : get all the localites.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of localites in body.
     */
    @GetMapping("/localites")
    public List<Localite> getAllLocalites() {
        log.debug("REST request to get all Localites");
        return localiteRepository.findAll();
    }

    /**
     * {@code GET  /localites/:id} : get the "id" localite.
     *
     * @param id the id of the localite to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the localite, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/localites/{id}")
    public ResponseEntity<Localite> getLocalite(@PathVariable Long id) {
        log.debug("REST request to get Localite : {}", id);
        Optional<Localite> localite = localiteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(localite);
    }

    /**
     * {@code DELETE  /localites/:id} : delete the "id" localite.
     *
     * @param id the id of the localite to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/localites/{id}")
    public ResponseEntity<Void> deleteLocalite(@PathVariable Long id) {
        log.debug("REST request to delete Localite : {}", id);
        localiteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
