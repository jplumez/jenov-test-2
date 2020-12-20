package ch.jenov.test.web.rest;

import ch.jenov.test.JenovTest2App;
import ch.jenov.test.domain.Campagne;
import ch.jenov.test.repository.CampagneRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CampagneResource} REST controller.
 */
@SpringBootTest(classes = JenovTest2App.class)
@AutoConfigureMockMvc
@WithMockUser
public class CampagneResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NB_JOURS_RAPPEL = 1;
    private static final Integer UPDATED_NB_JOURS_RAPPEL = 2;

    private static final String DEFAULT_REMARQUES = "AAAAAAAAAA";
    private static final String UPDATED_REMARQUES = "BBBBBBBBBB";

    @Autowired
    private CampagneRepository campagneRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCampagneMockMvc;

    private Campagne campagne;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Campagne createEntity(EntityManager em) {
        Campagne campagne = new Campagne()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .debut(DEFAULT_DEBUT)
            .fin(DEFAULT_FIN)
            .nbJoursRappel(DEFAULT_NB_JOURS_RAPPEL)
            .remarques(DEFAULT_REMARQUES);
        return campagne;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Campagne createUpdatedEntity(EntityManager em) {
        Campagne campagne = new Campagne()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .debut(UPDATED_DEBUT)
            .fin(UPDATED_FIN)
            .nbJoursRappel(UPDATED_NB_JOURS_RAPPEL)
            .remarques(UPDATED_REMARQUES);
        return campagne;
    }

    @BeforeEach
    public void initTest() {
        campagne = createEntity(em);
    }

    @Test
    @Transactional
    public void createCampagne() throws Exception {
        int databaseSizeBeforeCreate = campagneRepository.findAll().size();
        // Create the Campagne
        restCampagneMockMvc.perform(post("/api/campagnes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(campagne)))
            .andExpect(status().isCreated());

        // Validate the Campagne in the database
        List<Campagne> campagneList = campagneRepository.findAll();
        assertThat(campagneList).hasSize(databaseSizeBeforeCreate + 1);
        Campagne testCampagne = campagneList.get(campagneList.size() - 1);
        assertThat(testCampagne.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCampagne.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCampagne.getDebut()).isEqualTo(DEFAULT_DEBUT);
        assertThat(testCampagne.getFin()).isEqualTo(DEFAULT_FIN);
        assertThat(testCampagne.getNbJoursRappel()).isEqualTo(DEFAULT_NB_JOURS_RAPPEL);
        assertThat(testCampagne.getRemarques()).isEqualTo(DEFAULT_REMARQUES);
    }

    @Test
    @Transactional
    public void createCampagneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = campagneRepository.findAll().size();

        // Create the Campagne with an existing ID
        campagne.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampagneMockMvc.perform(post("/api/campagnes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(campagne)))
            .andExpect(status().isBadRequest());

        // Validate the Campagne in the database
        List<Campagne> campagneList = campagneRepository.findAll();
        assertThat(campagneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCampagnes() throws Exception {
        // Initialize the database
        campagneRepository.saveAndFlush(campagne);

        // Get all the campagneList
        restCampagneMockMvc.perform(get("/api/campagnes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campagne.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].debut").value(hasItem(DEFAULT_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())))
            .andExpect(jsonPath("$.[*].nbJoursRappel").value(hasItem(DEFAULT_NB_JOURS_RAPPEL)))
            .andExpect(jsonPath("$.[*].remarques").value(hasItem(DEFAULT_REMARQUES)));
    }
    
    @Test
    @Transactional
    public void getCampagne() throws Exception {
        // Initialize the database
        campagneRepository.saveAndFlush(campagne);

        // Get the campagne
        restCampagneMockMvc.perform(get("/api/campagnes/{id}", campagne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(campagne.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.debut").value(DEFAULT_DEBUT.toString()))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN.toString()))
            .andExpect(jsonPath("$.nbJoursRappel").value(DEFAULT_NB_JOURS_RAPPEL))
            .andExpect(jsonPath("$.remarques").value(DEFAULT_REMARQUES));
    }
    @Test
    @Transactional
    public void getNonExistingCampagne() throws Exception {
        // Get the campagne
        restCampagneMockMvc.perform(get("/api/campagnes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCampagne() throws Exception {
        // Initialize the database
        campagneRepository.saveAndFlush(campagne);

        int databaseSizeBeforeUpdate = campagneRepository.findAll().size();

        // Update the campagne
        Campagne updatedCampagne = campagneRepository.findById(campagne.getId()).get();
        // Disconnect from session so that the updates on updatedCampagne are not directly saved in db
        em.detach(updatedCampagne);
        updatedCampagne
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .debut(UPDATED_DEBUT)
            .fin(UPDATED_FIN)
            .nbJoursRappel(UPDATED_NB_JOURS_RAPPEL)
            .remarques(UPDATED_REMARQUES);

        restCampagneMockMvc.perform(put("/api/campagnes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCampagne)))
            .andExpect(status().isOk());

        // Validate the Campagne in the database
        List<Campagne> campagneList = campagneRepository.findAll();
        assertThat(campagneList).hasSize(databaseSizeBeforeUpdate);
        Campagne testCampagne = campagneList.get(campagneList.size() - 1);
        assertThat(testCampagne.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCampagne.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCampagne.getDebut()).isEqualTo(UPDATED_DEBUT);
        assertThat(testCampagne.getFin()).isEqualTo(UPDATED_FIN);
        assertThat(testCampagne.getNbJoursRappel()).isEqualTo(UPDATED_NB_JOURS_RAPPEL);
        assertThat(testCampagne.getRemarques()).isEqualTo(UPDATED_REMARQUES);
    }

    @Test
    @Transactional
    public void updateNonExistingCampagne() throws Exception {
        int databaseSizeBeforeUpdate = campagneRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampagneMockMvc.perform(put("/api/campagnes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(campagne)))
            .andExpect(status().isBadRequest());

        // Validate the Campagne in the database
        List<Campagne> campagneList = campagneRepository.findAll();
        assertThat(campagneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCampagne() throws Exception {
        // Initialize the database
        campagneRepository.saveAndFlush(campagne);

        int databaseSizeBeforeDelete = campagneRepository.findAll().size();

        // Delete the campagne
        restCampagneMockMvc.perform(delete("/api/campagnes/{id}", campagne.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Campagne> campagneList = campagneRepository.findAll();
        assertThat(campagneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
