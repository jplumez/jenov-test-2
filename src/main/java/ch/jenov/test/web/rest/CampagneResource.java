package ch.jenov.test.web.rest;

import ch.jenov.test.domain.Campagne;
import ch.jenov.test.repository.CampagneRepository;
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
 * REST controller for managing {@link ch.jenov.test.domain.Campagne}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CampagneResource {

    private final Logger log = LoggerFactory.getLogger(CampagneResource.class);

    private static final String ENTITY_NAME = "campagne";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampagneRepository campagneRepository;

    public CampagneResource(CampagneRepository campagneRepository) {
        this.campagneRepository = campagneRepository;
    }

    /**
     * {@code POST  /campagnes} : Create a new campagne.
     *
     * @param campagne the campagne to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campagne, or with status {@code 400 (Bad Request)} if the campagne has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/campagnes")
    public ResponseEntity<Campagne> createCampagne(@RequestBody Campagne campagne) throws URISyntaxException {
        log.debug("REST request to save Campagne : {}", campagne);
        if (campagne.getId() != null) {
            throw new BadRequestAlertException("A new campagne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Campagne result = campagneRepository.save(campagne);
        return ResponseEntity.created(new URI("/api/campagnes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /campagnes} : Updates an existing campagne.
     *
     * @param campagne the campagne to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campagne,
     * or with status {@code 400 (Bad Request)} if the campagne is not valid,
     * or with status {@code 500 (Internal Server Error)} if the campagne couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/campagnes")
    public ResponseEntity<Campagne> updateCampagne(@RequestBody Campagne campagne) throws URISyntaxException {
        log.debug("REST request to update Campagne : {}", campagne);
        if (campagne.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Campagne result = campagneRepository.save(campagne);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, campagne.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /campagnes} : get all the campagnes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campagnes in body.
     */
    @GetMapping("/campagnes")
    public List<Campagne> getAllCampagnes() {
        log.debug("REST request to get all Campagnes");
        return campagneRepository.findAll();
    }

    /**
     * {@code GET  /campagnes/:id} : get the "id" campagne.
     *
     * @param id the id of the campagne to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campagne, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/campagnes/{id}")
    public ResponseEntity<Campagne> getCampagne(@PathVariable Long id) {
        log.debug("REST request to get Campagne : {}", id);
        Optional<Campagne> campagne = campagneRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(campagne);
    }

    /**
     * {@code DELETE  /campagnes/:id} : delete the "id" campagne.
     *
     * @param id the id of the campagne to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/campagnes/{id}")
    public ResponseEntity<Void> deleteCampagne(@PathVariable Long id) {
        log.debug("REST request to delete Campagne : {}", id);
        campagneRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
