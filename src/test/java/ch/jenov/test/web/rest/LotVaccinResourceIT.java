package ch.jenov.test.web.rest;

import ch.jenov.test.JenovTest2App;
import ch.jenov.test.domain.LotVaccin;
import ch.jenov.test.repository.LotVaccinRepository;

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
 * Integration tests for the {@link LotVaccinResource} REST controller.
 */
@SpringBootTest(classes = JenovTest2App.class)
@AutoConfigureMockMvc
@WithMockUser
public class LotVaccinResourceIT {

    private static final Long DEFAULT_STOCK_INITIAL = 1L;
    private static final Long UPDATED_STOCK_INITIAL = 2L;

    private static final Long DEFAULT_STOCK_ACTUEL = 1L;
    private static final Long UPDATED_STOCK_ACTUEL = 2L;

    private static final Instant DEFAULT_HEURE_INVENTAIRE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HEURE_INVENTAIRE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private LotVaccinRepository lotVaccinRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLotVaccinMockMvc;

    private LotVaccin lotVaccin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LotVaccin createEntity(EntityManager em) {
        LotVaccin lotVaccin = new LotVaccin()
            .stockInitial(DEFAULT_STOCK_INITIAL)
            .stockActuel(DEFAULT_STOCK_ACTUEL)
            .heureInventaire(DEFAULT_HEURE_INVENTAIRE);
        return lotVaccin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LotVaccin createUpdatedEntity(EntityManager em) {
        LotVaccin lotVaccin = new LotVaccin()
            .stockInitial(UPDATED_STOCK_INITIAL)
            .stockActuel(UPDATED_STOCK_ACTUEL)
            .heureInventaire(UPDATED_HEURE_INVENTAIRE);
        return lotVaccin;
    }

    @BeforeEach
    public void initTest() {
        lotVaccin = createEntity(em);
    }

    @Test
    @Transactional
    public void createLotVaccin() throws Exception {
        int databaseSizeBeforeCreate = lotVaccinRepository.findAll().size();
        // Create the LotVaccin
        restLotVaccinMockMvc.perform(post("/api/lot-vaccins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lotVaccin)))
            .andExpect(status().isCreated());

        // Validate the LotVaccin in the database
        List<LotVaccin> lotVaccinList = lotVaccinRepository.findAll();
        assertThat(lotVaccinList).hasSize(databaseSizeBeforeCreate + 1);
        LotVaccin testLotVaccin = lotVaccinList.get(lotVaccinList.size() - 1);
        assertThat(testLotVaccin.getStockInitial()).isEqualTo(DEFAULT_STOCK_INITIAL);
        assertThat(testLotVaccin.getStockActuel()).isEqualTo(DEFAULT_STOCK_ACTUEL);
        assertThat(testLotVaccin.getHeureInventaire()).isEqualTo(DEFAULT_HEURE_INVENTAIRE);
    }

    @Test
    @Transactional
    public void createLotVaccinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lotVaccinRepository.findAll().size();

        // Create the LotVaccin with an existing ID
        lotVaccin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLotVaccinMockMvc.perform(post("/api/lot-vaccins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lotVaccin)))
            .andExpect(status().isBadRequest());

        // Validate the LotVaccin in the database
        List<LotVaccin> lotVaccinList = lotVaccinRepository.findAll();
        assertThat(lotVaccinList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLotVaccins() throws Exception {
        // Initialize the database
        lotVaccinRepository.saveAndFlush(lotVaccin);

        // Get all the lotVaccinList
        restLotVaccinMockMvc.perform(get("/api/lot-vaccins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lotVaccin.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockInitial").value(hasItem(DEFAULT_STOCK_INITIAL.intValue())))
            .andExpect(jsonPath("$.[*].stockActuel").value(hasItem(DEFAULT_STOCK_ACTUEL.intValue())))
            .andExpect(jsonPath("$.[*].heureInventaire").value(hasItem(DEFAULT_HEURE_INVENTAIRE.toString())));
    }
    
    @Test
    @Transactional
    public void getLotVaccin() throws Exception {
        // Initialize the database
        lotVaccinRepository.saveAndFlush(lotVaccin);

        // Get the lotVaccin
        restLotVaccinMockMvc.perform(get("/api/lot-vaccins/{id}", lotVaccin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lotVaccin.getId().intValue()))
            .andExpect(jsonPath("$.stockInitial").value(DEFAULT_STOCK_INITIAL.intValue()))
            .andExpect(jsonPath("$.stockActuel").value(DEFAULT_STOCK_ACTUEL.intValue()))
            .andExpect(jsonPath("$.heureInventaire").value(DEFAULT_HEURE_INVENTAIRE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingLotVaccin() throws Exception {
        // Get the lotVaccin
        restLotVaccinMockMvc.perform(get("/api/lot-vaccins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLotVaccin() throws Exception {
        // Initialize the database
        lotVaccinRepository.saveAndFlush(lotVaccin);

        int databaseSizeBeforeUpdate = lotVaccinRepository.findAll().size();

        // Update the lotVaccin
        LotVaccin updatedLotVaccin = lotVaccinRepository.findById(lotVaccin.getId()).get();
        // Disconnect from session so that the updates on updatedLotVaccin are not directly saved in db
        em.detach(updatedLotVaccin);
        updatedLotVaccin
            .stockInitial(UPDATED_STOCK_INITIAL)
            .stockActuel(UPDATED_STOCK_ACTUEL)
            .heureInventaire(UPDATED_HEURE_INVENTAIRE);

        restLotVaccinMockMvc.perform(put("/api/lot-vaccins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLotVaccin)))
            .andExpect(status().isOk());

        // Validate the LotVaccin in the database
        List<LotVaccin> lotVaccinList = lotVaccinRepository.findAll();
        assertThat(lotVaccinList).hasSize(databaseSizeBeforeUpdate);
        LotVaccin testLotVaccin = lotVaccinList.get(lotVaccinList.size() - 1);
        assertThat(testLotVaccin.getStockInitial()).isEqualTo(UPDATED_STOCK_INITIAL);
        assertThat(testLotVaccin.getStockActuel()).isEqualTo(UPDATED_STOCK_ACTUEL);
        assertThat(testLotVaccin.getHeureInventaire()).isEqualTo(UPDATED_HEURE_INVENTAIRE);
    }

    @Test
    @Transactional
    public void updateNonExistingLotVaccin() throws Exception {
        int databaseSizeBeforeUpdate = lotVaccinRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLotVaccinMockMvc.perform(put("/api/lot-vaccins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lotVaccin)))
            .andExpect(status().isBadRequest());

        // Validate the LotVaccin in the database
        List<LotVaccin> lotVaccinList = lotVaccinRepository.findAll();
        assertThat(lotVaccinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLotVaccin() throws Exception {
        // Initialize the database
        lotVaccinRepository.saveAndFlush(lotVaccin);

        int databaseSizeBeforeDelete = lotVaccinRepository.findAll().size();

        // Delete the lotVaccin
        restLotVaccinMockMvc.perform(delete("/api/lot-vaccins/{id}", lotVaccin.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LotVaccin> lotVaccinList = lotVaccinRepository.findAll();
        assertThat(lotVaccinList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
