package ch.jenov.test.web.rest;

import ch.jenov.test.domain.Centre;
import ch.jenov.test.repository.CentreRepository;
import ch.jenov.test.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ch.jenov.test.domain.Centre}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CentreResource {

    private final Logger log = LoggerFactory.getLogger(CentreResource.class);

    private static final String ENTITY_NAME = "centre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CentreRepository centreRepository;

    public CentreResource(CentreRepository centreRepository) {
        this.centreRepository = centreRepository;
    }

    /**
     * {@code POST  /centres} : Create a new centre.
     *
     * @param centre the centre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centre, or with status {@code 400 (Bad Request)} if the centre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/centres")
    public ResponseEntity<Centre> createCentre(@Valid @RequestBody Centre centre) throws URISyntaxException {
        log.debug("REST request to save Centre : {}", centre);
        if (centre.getId() != null) {
            throw new BadRequestAlertException("A new centre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Centre result = centreRepository.save(centre);
        return ResponseEntity.created(new URI("/api/centres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /centres} : Updates an existing centre.
     *
     * @param centre the centre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centre,
     * or with status {@code 400 (Bad Request)} if the centre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/centres")
    public ResponseEntity<Centre> updateCentre(@Valid @RequestBody Centre centre) throws URISyntaxException {
        log.debug("REST request to update Centre : {}", centre);
        if (centre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Centre result = centreRepository.save(centre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centre.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /centres} : get all the centres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centres in body.
     */
    @GetMapping("/centres")
    public List<Centre> getAllCentres() {
        log.debug("REST request to get all Centres");
        return centreRepository.findAll();
    }

    /**
     * {@code GET  /centres/:id} : get the "id" centre.
     *
     * @param id the id of the centre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/centres/{id}")
    public ResponseEntity<Centre> getCentre(@PathVariable Long id) {
        log.debug("REST request to get Centre : {}", id);
        Optional<Centre> centre = centreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(centre);
    }

    /**
     * {@code DELETE  /centres/:id} : delete the "id" centre.
     *
     * @param id the id of the centre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/centres/{id}")
    public ResponseEntity<Void> deleteCentre(@PathVariable Long id) {
        log.debug("REST request to delete Centre : {}", id);
        centreRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
