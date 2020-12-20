package ch.jenov.test.web.rest;

import ch.jenov.test.JenovTest2App;
import ch.jenov.test.domain.Centre;
import ch.jenov.test.repository.CentreRepository;

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
 * Integration tests for the {@link CentreResource} REST controller.
 */
@SpringBootTest(classes = JenovTest2App.class)
@AutoConfigureMockMvc
@WithMockUser
public class CentreResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALITE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALITE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CentreRepository centreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCentreMockMvc;

    private Centre centre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Centre createEntity(EntityManager em) {
        Centre centre = new Centre()
            .code(DEFAULT_CODE)
            .localite(DEFAULT_LOCALITE)
            .description(DEFAULT_DESCRIPTION);
        return centre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Centre createUpdatedEntity(EntityManager em) {
        Centre centre = new Centre()
            .code(UPDATED_CODE)
            .localite(UPDATED_LOCALITE)
            .description(UPDATED_DESCRIPTION);
        return centre;
    }

    @BeforeEach
    public void initTest() {
        centre = createEntity(em);
    }

    @Test
    @Transactional
    public void createCentre() throws Exception {
        int databaseSizeBeforeCreate = centreRepository.findAll().size();
        // Create the Centre
        restCentreMockMvc.perform(post("/api/centres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centre)))
            .andExpect(status().isCreated());

        // Validate the Centre in the database
        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeCreate + 1);
        Centre testCentre = centreList.get(centreList.size() - 1);
        assertThat(testCentre.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCentre.getLocalite()).isEqualTo(DEFAULT_LOCALITE);
        assertThat(testCentre.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCentreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = centreRepository.findAll().size();

        // Create the Centre with an existing ID
        centre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentreMockMvc.perform(post("/api/centres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centre)))
            .andExpect(status().isBadRequest());

        // Validate the Centre in the database
        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = centreRepository.findAll().size();
        // set the field null
        centre.setCode(null);

        // Create the Centre, which fails.


        restCentreMockMvc.perform(post("/api/centres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centre)))
            .andExpect(status().isBadRequest());

        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocaliteIsRequired() throws Exception {
        int databaseSizeBeforeTest = centreRepository.findAll().size();
        // set the field null
        centre.setLocalite(null);

        // Create the Centre, which fails.


        restCentreMockMvc.perform(post("/api/centres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centre)))
            .andExpect(status().isBadRequest());

        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCentres() throws Exception {
        // Initialize the database
        centreRepository.saveAndFlush(centre);

        // Get all the centreList
        restCentreMockMvc.perform(get("/api/centres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centre.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].localite").value(hasItem(DEFAULT_LOCALITE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getCentre() throws Exception {
        // Initialize the database
        centreRepository.saveAndFlush(centre);

        // Get the centre
        restCentreMockMvc.perform(get("/api/centres/{id}", centre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(centre.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.localite").value(DEFAULT_LOCALITE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingCentre() throws Exception {
        // Get the centre
        restCentreMockMvc.perform(get("/api/centres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCentre() throws Exception {
        // Initialize the database
        centreRepository.saveAndFlush(centre);

        int databaseSizeBeforeUpdate = centreRepository.findAll().size();

        // Update the centre
        Centre updatedCentre = centreRepository.findById(centre.getId()).get();
        // Disconnect from session so that the updates on updatedCentre are not directly saved in db
        em.detach(updatedCentre);
        updatedCentre
            .code(UPDATED_CODE)
            .localite(UPDATED_LOCALITE)
            .description(UPDATED_DESCRIPTION);

        restCentreMockMvc.perform(put("/api/centres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCentre)))
            .andExpect(status().isOk());

        // Validate the Centre in the database
        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeUpdate);
        Centre testCentre = centreList.get(centreList.size() - 1);
        assertThat(testCentre.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCentre.getLocalite()).isEqualTo(UPDATED_LOCALITE);
        assertThat(testCentre.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCentre() throws Exception {
        int databaseSizeBeforeUpdate = centreRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentreMockMvc.perform(put("/api/centres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centre)))
            .andExpect(status().isBadRequest());

        // Validate the Centre in the database
        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCentre() throws Exception {
        // Initialize the database
        centreRepository.saveAndFlush(centre);

        int databaseSizeBeforeDelete = centreRepository.findAll().size();

        // Delete the centre
        restCentreMockMvc.perform(delete("/api/centres/{id}", centre.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Centre> centreList = centreRepository.findAll();
        assertThat(centreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
