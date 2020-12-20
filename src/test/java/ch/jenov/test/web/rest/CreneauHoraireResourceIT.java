package ch.jenov.test.web.rest;

import ch.jenov.test.JenovTest2App;
import ch.jenov.test.domain.CreneauHoraire;
import ch.jenov.test.repository.CreneauHoraireRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CreneauHoraireResource} REST controller.
 */
@SpringBootTest(classes = JenovTest2App.class)
@AutoConfigureMockMvc
@WithMockUser
public class CreneauHoraireResourceIT {

    private static final Integer DEFAULT_CAPACITE = 1;
    private static final Integer UPDATED_CAPACITE = 2;

    private static final Instant DEFAULT_HEURE_DEBUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HEURE_DEBUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HEURE_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HEURE_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CreneauHoraireRepository creneauHoraireRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreneauHoraireMockMvc;

    private CreneauHoraire creneauHoraire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreneauHoraire createEntity(EntityManager em) {
        CreneauHoraire creneauHoraire = new CreneauHoraire()
            .capacite(DEFAULT_CAPACITE)
            .heureDebut(DEFAULT_HEURE_DEBUT)
            .heureFin(DEFAULT_HEURE_FIN);
        return creneauHoraire;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreneauHoraire createUpdatedEntity(EntityManager em) {
        CreneauHoraire creneauHoraire = new CreneauHoraire()
            .capacite(UPDATED_CAPACITE)
            .heureDebut(UPDATED_HEURE_DEBUT)
            .heureFin(UPDATED_HEURE_FIN);
        return creneauHoraire;
    }

    @BeforeEach
    public void initTest() {
        creneauHoraire = createEntity(em);
    }

    @Test
    @Transactional
    public void createCreneauHoraire() throws Exception {
        int databaseSizeBeforeCreate = creneauHoraireRepository.findAll().size();
        // Create the CreneauHoraire
        restCreneauHoraireMockMvc.perform(post("/api/creneau-horaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creneauHoraire)))
            .andExpect(status().isCreated());

        // Validate the CreneauHoraire in the database
        List<CreneauHoraire> creneauHoraireList = creneauHoraireRepository.findAll();
        assertThat(creneauHoraireList).hasSize(databaseSizeBeforeCreate + 1);
        CreneauHoraire testCreneauHoraire = creneauHoraireList.get(creneauHoraireList.size() - 1);
        assertThat(testCreneauHoraire.getCapacite()).isEqualTo(DEFAULT_CAPACITE);
        assertThat(testCreneauHoraire.getHeureDebut()).isEqualTo(DEFAULT_HEURE_DEBUT);
        assertThat(testCreneauHoraire.getHeureFin()).isEqualTo(DEFAULT_HEURE_FIN);
    }

    @Test
    @Transactional
    public void createCreneauHoraireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = creneauHoraireRepository.findAll().size();

        // Create the CreneauHoraire with an existing ID
        creneauHoraire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreneauHoraireMockMvc.perform(post("/api/creneau-horaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creneauHoraire)))
            .andExpect(status().isBadRequest());

        // Validate the CreneauHoraire in the database
        List<CreneauHoraire> creneauHoraireList = creneauHoraireRepository.findAll();
        assertThat(creneauHoraireList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCreneauHoraires() throws Exception {
        // Initialize the database
        creneauHoraireRepository.saveAndFlush(creneauHoraire);

        // Get all the creneauHoraireList
        restCreneauHoraireMockMvc.perform(get("/api/creneau-horaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creneauHoraire.getId().intValue())))
            .andExpect(jsonPath("$.[*].capacite").value(hasItem(DEFAULT_CAPACITE)))
            .andExpect(jsonPath("$.[*].heureDebut").value(hasItem(DEFAULT_HEURE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].heureFin").value(hasItem(DEFAULT_HEURE_FIN.toString())));
    }
    
    @Test
    @Transactional
    public void getCreneauHoraire() throws Exception {
        // Initialize the database
        creneauHoraireRepository.saveAndFlush(creneauHoraire);

        // Get the creneauHoraire
        restCreneauHoraireMockMvc.perform(get("/api/creneau-horaires/{id}", creneauHoraire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(creneauHoraire.getId().intValue()))
            .andExpect(jsonPath("$.capacite").value(DEFAULT_CAPACITE))
            .andExpect(jsonPath("$.heureDebut").value(DEFAULT_HEURE_DEBUT.toString()))
            .andExpect(jsonPath("$.heureFin").value(DEFAULT_HEURE_FIN.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCreneauHoraire() throws Exception {
        // Get the creneauHoraire
        restCreneauHoraireMockMvc.perform(get("/api/creneau-horaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCreneauHoraire() throws Exception {
        // Initialize the database
        creneauHoraireRepository.saveAndFlush(creneauHoraire);

        int databaseSizeBeforeUpdate = creneauHoraireRepository.findAll().size();

        // Update the creneauHoraire
        CreneauHoraire updatedCreneauHoraire = creneauHoraireRepository.findById(creneauHoraire.getId()).get();
        // Disconnect from session so that the updates on updatedCreneauHoraire are not directly saved in db
        em.detach(updatedCreneauHoraire);
        updatedCreneauHoraire
            .capacite(UPDATED_CAPACITE)
            .heureDebut(UPDATED_HEURE_DEBUT)
            .heureFin(UPDATED_HEURE_FIN);

        restCreneauHoraireMockMvc.perform(put("/api/creneau-horaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCreneauHoraire)))
            .andExpect(status().isOk());

        // Validate the CreneauHoraire in the database
        List<CreneauHoraire> creneauHoraireList = creneauHoraireRepository.findAll();
        assertThat(creneauHoraireList).hasSize(databaseSizeBeforeUpdate);
        CreneauHoraire testCreneauHoraire = creneauHoraireList.get(creneauHoraireList.size() - 1);
        assertThat(testCreneauHoraire.getCapacite()).isEqualTo(UPDATED_CAPACITE);
        assertThat(testCreneauHoraire.getHeureDebut()).isEqualTo(UPDATED_HEURE_DEBUT);
        assertThat(testCreneauHoraire.getHeureFin()).isEqualTo(UPDATED_HEURE_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingCreneauHoraire() throws Exception {
        int databaseSizeBeforeUpdate = creneauHoraireRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreneauHoraireMockMvc.perform(put("/api/creneau-horaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(creneauHoraire)))
            .andExpect(status().isBadRequest());

        // Validate the CreneauHoraire in the database
        List<CreneauHoraire> creneauHoraireList = creneauHoraireRepository.findAll();
        assertThat(creneauHoraireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCreneauHoraire() throws Exception {
        // Initialize the database
        creneauHoraireRepository.saveAndFlush(creneauHoraire);

        int databaseSizeBeforeDelete = creneauHoraireRepository.findAll().size();

        // Delete the creneauHoraire
        restCreneauHoraireMockMvc.perform(delete("/api/creneau-horaires/{id}", creneauHoraire.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CreneauHoraire> creneauHoraireList = creneauHoraireRepository.findAll();
        assertThat(creneauHoraireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
