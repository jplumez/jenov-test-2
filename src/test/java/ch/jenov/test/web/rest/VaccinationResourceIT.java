package ch.jenov.test.web.rest;

import ch.jenov.test.JenovTest2App;
import ch.jenov.test.domain.Vaccination;
import ch.jenov.test.repository.VaccinationRepository;

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
 * Integration tests for the {@link VaccinationResource} REST controller.
 */
@SpringBootTest(classes = JenovTest2App.class)
@AutoConfigureMockMvc
@WithMockUser
public class VaccinationResourceIT {

    private static final Instant DEFAULT_DATE_CREATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_RENDEZ_VOUS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_RENDEZ_VOUS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_VACCIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_VACCIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private VaccinationRepository vaccinationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVaccinationMockMvc;

    private Vaccination vaccination;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vaccination createEntity(EntityManager em) {
        Vaccination vaccination = new Vaccination()
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateRendezVous(DEFAULT_DATE_RENDEZ_VOUS)
            .dateVaccin(DEFAULT_DATE_VACCIN);
        return vaccination;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vaccination createUpdatedEntity(EntityManager em) {
        Vaccination vaccination = new Vaccination()
            .dateCreation(UPDATED_DATE_CREATION)
            .dateRendezVous(UPDATED_DATE_RENDEZ_VOUS)
            .dateVaccin(UPDATED_DATE_VACCIN);
        return vaccination;
    }

    @BeforeEach
    public void initTest() {
        vaccination = createEntity(em);
    }

    @Test
    @Transactional
    public void createVaccination() throws Exception {
        int databaseSizeBeforeCreate = vaccinationRepository.findAll().size();
        // Create the Vaccination
        restVaccinationMockMvc.perform(post("/api/vaccinations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vaccination)))
            .andExpect(status().isCreated());

        // Validate the Vaccination in the database
        List<Vaccination> vaccinationList = vaccinationRepository.findAll();
        assertThat(vaccinationList).hasSize(databaseSizeBeforeCreate + 1);
        Vaccination testVaccination = vaccinationList.get(vaccinationList.size() - 1);
        assertThat(testVaccination.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testVaccination.getDateRendezVous()).isEqualTo(DEFAULT_DATE_RENDEZ_VOUS);
        assertThat(testVaccination.getDateVaccin()).isEqualTo(DEFAULT_DATE_VACCIN);
    }

    @Test
    @Transactional
    public void createVaccinationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vaccinationRepository.findAll().size();

        // Create the Vaccination with an existing ID
        vaccination.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVaccinationMockMvc.perform(post("/api/vaccinations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vaccination)))
            .andExpect(status().isBadRequest());

        // Validate the Vaccination in the database
        List<Vaccination> vaccinationList = vaccinationRepository.findAll();
        assertThat(vaccinationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVaccinations() throws Exception {
        // Initialize the database
        vaccinationRepository.saveAndFlush(vaccination);

        // Get all the vaccinationList
        restVaccinationMockMvc.perform(get("/api/vaccinations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vaccination.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].dateRendezVous").value(hasItem(DEFAULT_DATE_RENDEZ_VOUS.toString())))
            .andExpect(jsonPath("$.[*].dateVaccin").value(hasItem(DEFAULT_DATE_VACCIN.toString())));
    }
    
    @Test
    @Transactional
    public void getVaccination() throws Exception {
        // Initialize the database
        vaccinationRepository.saveAndFlush(vaccination);

        // Get the vaccination
        restVaccinationMockMvc.perform(get("/api/vaccinations/{id}", vaccination.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vaccination.getId().intValue()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.dateRendezVous").value(DEFAULT_DATE_RENDEZ_VOUS.toString()))
            .andExpect(jsonPath("$.dateVaccin").value(DEFAULT_DATE_VACCIN.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingVaccination() throws Exception {
        // Get the vaccination
        restVaccinationMockMvc.perform(get("/api/vaccinations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVaccination() throws Exception {
        // Initialize the database
        vaccinationRepository.saveAndFlush(vaccination);

        int databaseSizeBeforeUpdate = vaccinationRepository.findAll().size();

        // Update the vaccination
        Vaccination updatedVaccination = vaccinationRepository.findById(vaccination.getId()).get();
        // Disconnect from session so that the updates on updatedVaccination are not directly saved in db
        em.detach(updatedVaccination);
        updatedVaccination
            .dateCreation(UPDATED_DATE_CREATION)
            .dateRendezVous(UPDATED_DATE_RENDEZ_VOUS)
            .dateVaccin(UPDATED_DATE_VACCIN);

        restVaccinationMockMvc.perform(put("/api/vaccinations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVaccination)))
            .andExpect(status().isOk());

        // Validate the Vaccination in the database
        List<Vaccination> vaccinationList = vaccinationRepository.findAll();
        assertThat(vaccinationList).hasSize(databaseSizeBeforeUpdate);
        Vaccination testVaccination = vaccinationList.get(vaccinationList.size() - 1);
        assertThat(testVaccination.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testVaccination.getDateRendezVous()).isEqualTo(UPDATED_DATE_RENDEZ_VOUS);
        assertThat(testVaccination.getDateVaccin()).isEqualTo(UPDATED_DATE_VACCIN);
    }

    @Test
    @Transactional
    public void updateNonExistingVaccination() throws Exception {
        int databaseSizeBeforeUpdate = vaccinationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVaccinationMockMvc.perform(put("/api/vaccinations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vaccination)))
            .andExpect(status().isBadRequest());

        // Validate the Vaccination in the database
        List<Vaccination> vaccinationList = vaccinationRepository.findAll();
        assertThat(vaccinationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVaccination() throws Exception {
        // Initialize the database
        vaccinationRepository.saveAndFlush(vaccination);

        int databaseSizeBeforeDelete = vaccinationRepository.findAll().size();

        // Delete the vaccination
        restVaccinationMockMvc.perform(delete("/api/vaccinations/{id}", vaccination.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vaccination> vaccinationList = vaccinationRepository.findAll();
        assertThat(vaccinationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
