package ch.jenov.test.web.rest;

import ch.jenov.test.domain.ProfessionnelSante;
import ch.jenov.test.repository.ProfessionnelSanteRepository;
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
 * REST controller for managing {@link ch.jenov.test.domain.ProfessionnelSante}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProfessionnelSanteResource {

    private final Logger log = LoggerFactory.getLogger(ProfessionnelSanteResource.class);

    private static final String ENTITY_NAME = "professionnelSante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfessionnelSanteRepository professionnelSanteRepository;

    public ProfessionnelSanteResource(ProfessionnelSanteRepository professionnelSanteRepository) {
        this.professionnelSanteRepository = professionnelSanteRepository;
    }

    /**
     * {@code POST  /professionnel-santes} : Create a new professionnelSante.
     *
     * @param professionnelSante the professionnelSante to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new professionnelSante, or with status {@code 400 (Bad Request)} if the professionnelSante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/professionnel-santes")
    public ResponseEntity<ProfessionnelSante> createProfessionnelSante(@RequestBody ProfessionnelSante professionnelSante) throws URISyntaxException {
        log.debug("REST request to save ProfessionnelSante : {}", professionnelSante);
        if (professionnelSante.getId() != null) {
            throw new BadRequestAlertException("A new professionnelSante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfessionnelSante result = professionnelSanteRepository.save(professionnelSante);
        return ResponseEntity.created(new URI("/api/professionnel-santes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /professionnel-santes} : Updates an existing professionnelSante.
     *
     * @param professionnelSante the professionnelSante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated professionnelSante,
     * or with status {@code 400 (Bad Request)} if the professionnelSante is not valid,
     * or with status {@code 500 (Internal Server Error)} if the professionnelSante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/professionnel-santes")
    public ResponseEntity<ProfessionnelSante> updateProfessionnelSante(@RequestBody ProfessionnelSante professionnelSante) throws URISyntaxException {
        log.debug("REST request to update ProfessionnelSante : {}", professionnelSante);
        if (professionnelSante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfessionnelSante result = professionnelSanteRepository.save(professionnelSante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, professionnelSante.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /professionnel-santes} : get all the professionnelSantes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of professionnelSantes in body.
     */
    @GetMapping("/professionnel-santes")
    public List<ProfessionnelSante> getAllProfessionnelSantes() {
        log.debug("REST request to get all ProfessionnelSantes");
        return professionnelSanteRepository.findAll();
    }

    /**
     * {@code GET  /professionnel-santes/:id} : get the "id" professionnelSante.
     *
     * @param id the id of the professionnelSante to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the professionnelSante, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/professionnel-santes/{id}")
    public ResponseEntity<ProfessionnelSante> getProfessionnelSante(@PathVariable Long id) {
        log.debug("REST request to get ProfessionnelSante : {}", id);
        Optional<ProfessionnelSante> professionnelSante = professionnelSanteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(professionnelSante);
    }

    /**
     * {@code DELETE  /professionnel-santes/:id} : delete the "id" professionnelSante.
     *
     * @param id the id of the professionnelSante to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/professionnel-santes/{id}")
    public ResponseEntity<Void> deleteProfessionnelSante(@PathVariable Long id) {
        log.debug("REST request to delete ProfessionnelSante : {}", id);
        professionnelSanteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
