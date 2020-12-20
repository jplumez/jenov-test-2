package ch.jenov.test.web.rest;

import ch.jenov.test.JenovTest2App;
import ch.jenov.test.domain.Localite;
import ch.jenov.test.repository.LocaliteRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LocaliteResource} REST controller.
 */
@SpringBootTest(classes = JenovTest2App.class)
@AutoConfigureMockMvc
@WithMockUser
public class LocaliteResourceIT {

    private static final String DEFAULT_NPA = "AAAAAAAAAA";
    private static final String UPDATED_NPA = "BBBBBBBBBB";

    private static final String DEFAULT_COMMUNE = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNE = "BBBBBBBBBB";

    @Autowired
    private LocaliteRepository localiteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocaliteMockMvc;

    private Localite localite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localite createEntity(EntityManager em) {
        Localite localite = new Localite()
            .npa(DEFAULT_NPA)
            .commune(DEFAULT_COMMUNE);
        return localite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localite createUpdatedEntity(EntityManager em) {
        Localite localite = new Localite()
            .npa(UPDATED_NPA)
            .commune(UPDATED_COMMUNE);
        return localite;
    }

    @BeforeEach
    public void initTest() {
        localite = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalite() throws Exception {
        int databaseSizeBeforeCreate = localiteRepository.findAll().size();
        // Create the Localite
        restLocaliteMockMvc.perform(post("/api/localites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localite)))
            .andExpect(status().isCreated());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeCreate + 1);
        Localite testLocalite = localiteList.get(localiteList.size() - 1);
        assertThat(testLocalite.getNpa()).isEqualTo(DEFAULT_NPA);
        assertThat(testLocalite.getCommune()).isEqualTo(DEFAULT_COMMUNE);
    }

    @Test
    @Transactional
    public void createLocaliteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localiteRepository.findAll().size();

        // Create the Localite with an existing ID
        localite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocaliteMockMvc.perform(post("/api/localites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localite)))
            .andExpect(status().isBadRequest());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLocalites() throws Exception {
        // Initialize the database
        localiteRepository.saveAndFlush(localite);

        // Get all the localiteList
        restLocaliteMockMvc.perform(get("/api/localites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localite.getId().intValue())))
            .andExpect(jsonPath("$.[*].npa").value(hasItem(DEFAULT_NPA)))
            .andExpect(jsonPath("$.[*].commune").value(hasItem(DEFAULT_COMMUNE)));
    }
    
    @Test
    @Transactional
    public void getLocalite() throws Exception {
        // Initialize the database
        localiteRepository.saveAndFlush(localite);

        // Get the localite
        restLocaliteMockMvc.perform(get("/api/localites/{id}", localite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(localite.getId().intValue()))
            .andExpect(jsonPath("$.npa").value(DEFAULT_NPA))
            .andExpect(jsonPath("$.commune").value(DEFAULT_COMMUNE));
    }
    @Test
    @Transactional
    public void getNonExistingLocalite() throws Exception {
        // Get the localite
        restLocaliteMockMvc.perform(get("/api/localites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalite() throws Exception {
        // Initialize the database
        localiteRepository.saveAndFlush(localite);

        int databaseSizeBeforeUpdate = localiteRepository.findAll().size();

        // Update the localite
        Localite updatedLocalite = localiteRepository.findById(localite.getId()).get();
        // Disconnect from session so that the updates on updatedLocalite are not directly saved in db
        em.detach(updatedLocalite);
        updatedLocalite
            .npa(UPDATED_NPA)
            .commune(UPDATED_COMMUNE);

        restLocaliteMockMvc.perform(put("/api/localites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocalite)))
            .andExpect(status().isOk());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeUpdate);
        Localite testLocalite = localiteList.get(localiteList.size() - 1);
        assertThat(testLocalite.getNpa()).isEqualTo(UPDATED_NPA);
        assertThat(testLocalite.getCommune()).isEqualTo(UPDATED_COMMUNE);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalite() throws Exception {
        int databaseSizeBeforeUpdate = localiteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocaliteMockMvc.perform(put("/api/localites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localite)))
            .andExpect(status().isBadRequest());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocalite() throws Exception {
        // Initialize the database
        localiteRepository.saveAndFlush(localite);

        int databaseSizeBeforeDelete = localiteRepository.findAll().size();

        // Delete the localite
        restLocaliteMockMvc.perform(delete("/api/localites/{id}", localite.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
