package ch.jenov.test.web.rest;

import ch.jenov.test.domain.LotVaccin;
import ch.jenov.test.repository.LotVaccinRepository;
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
 * REST controller for managing {@link ch.jenov.test.domain.LotVaccin}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LotVaccinResource {

    private final Logger log = LoggerFactory.getLogger(LotVaccinResource.class);

    private static final String ENTITY_NAME = "lotVaccin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LotVaccinRepository lotVaccinRepository;

    public LotVaccinResource(LotVaccinRepository lotVaccinRepository) {
        this.lotVaccinRepository = lotVaccinRepository;
    }

    /**
     * {@code POST  /lot-vaccins} : Create a new lotVaccin.
     *
     * @param lotVaccin the lotVaccin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lotVaccin, or with status {@code 400 (Bad Request)} if the lotVaccin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lot-vaccins")
    public ResponseEntity<LotVaccin> createLotVaccin(@RequestBody LotVaccin lotVaccin) throws URISyntaxException {
        log.debug("REST request to save LotVaccin : {}", lotVaccin);
        if (lotVaccin.getId() != null) {
            throw new BadRequestAlertException("A new lotVaccin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LotVaccin result = lotVaccinRepository.save(lotVaccin);
        return ResponseEntity.created(new URI("/api/lot-vaccins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lot-vaccins} : Updates an existing lotVaccin.
     *
     * @param lotVaccin the lotVaccin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lotVaccin,
     * or with status {@code 400 (Bad Request)} if the lotVaccin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lotVaccin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lot-vaccins")
    public ResponseEntity<LotVaccin> updateLotVaccin(@RequestBody LotVaccin lotVaccin) throws URISyntaxException {
        log.debug("REST request to update LotVaccin : {}", lotVaccin);
        if (lotVaccin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LotVaccin result = lotVaccinRepository.save(lotVaccin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lotVaccin.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lot-vaccins} : get all the lotVaccins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lotVaccins in body.
     */
    @GetMapping("/lot-vaccins")
    public List<LotVaccin> getAllLotVaccins() {
        log.debug("REST request to get all LotVaccins");
        return lotVaccinRepository.findAll();
    }

    /**
     * {@code GET  /lot-vaccins/:id} : get the "id" lotVaccin.
     *
     * @param id the id of the lotVaccin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lotVaccin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lot-vaccins/{id}")
    public ResponseEntity<LotVaccin> getLotVaccin(@PathVariable Long id) {
        log.debug("REST request to get LotVaccin : {}", id);
        Optional<LotVaccin> lotVaccin = lotVaccinRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lotVaccin);
    }

    /**
     * {@code DELETE  /lot-vaccins/:id} : delete the "id" lotVaccin.
     *
     * @param id the id of the lotVaccin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lot-vaccins/{id}")
    public ResponseEntity<Void> deleteLotVaccin(@PathVariable Long id) {
        log.debug("REST request to delete LotVaccin : {}", id);
        lotVaccinRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
