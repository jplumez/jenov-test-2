package ch.jenov.test.web.rest;

import ch.jenov.test.JenovTest2App;
import ch.jenov.test.domain.Patient;
import ch.jenov.test.repository.PatientRepository;

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

import ch.jenov.test.domain.enumeration.Sexe;
import ch.jenov.test.domain.enumeration.GroupeRisque;
/**
 * Integration tests for the {@link PatientResource} REST controller.
 */
@SpringBootTest(classes = JenovTest2App.class)
@AutoConfigureMockMvc
@WithMockUser
public class PatientResourceIT {

    private static final String DEFAULT_NO_AVS = "AAAAAAAAAA";
    private static final String UPDATED_NO_AVS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final Sexe DEFAULT_SEXE = Sexe.M;
    private static final Sexe UPDATED_SEXE = Sexe.F;

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_ASSURE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_ASSURE = "BBBBBBBBBB";

    private static final GroupeRisque DEFAULT_GROUPE_RISQUE = GroupeRisque.PERSONNE_VULNERABLE;
    private static final GroupeRisque UPDATED_GROUPE_RISQUE = GroupeRisque.CORPS_MEDICAL;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatientMockMvc;

    private Patient patient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patient createEntity(EntityManager em) {
        Patient patient = new Patient()
            .noAvs(DEFAULT_NO_AVS)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .sexe(DEFAULT_SEXE)
            .adresse(DEFAULT_ADRESSE)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL)
            .numeroAssure(DEFAULT_NUMERO_ASSURE)
            .groupeRisque(DEFAULT_GROUPE_RISQUE);
        return patient;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patient createUpdatedEntity(EntityManager em) {
        Patient patient = new Patient()
            .noAvs(UPDATED_NO_AVS)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .sexe(UPDATED_SEXE)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .numeroAssure(UPDATED_NUMERO_ASSURE)
            .groupeRisque(UPDATED_GROUPE_RISQUE);
        return patient;
    }

    @BeforeEach
    public void initTest() {
        patient = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatient() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();
        // Create the Patient
        restPatientMockMvc.perform(post("/api/patients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isCreated());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate + 1);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getNoAvs()).isEqualTo(DEFAULT_NO_AVS);
        assertThat(testPatient.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testPatient.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPatient.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testPatient.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testPatient.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testPatient.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testPatient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPatient.getNumeroAssure()).isEqualTo(DEFAULT_NUMERO_ASSURE);
        assertThat(testPatient.getGroupeRisque()).isEqualTo(DEFAULT_GROUPE_RISQUE);
    }

    @Test
    @Transactional
    public void createPatientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // Create the Patient with an existing ID
        patient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientMockMvc.perform(post("/api/patients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNoAvsIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setNoAvs(null);

        // Create the Patient, which fails.


        restPatientMockMvc.perform(post("/api/patients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPatients() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList
        restPatientMockMvc.perform(get("/api/patients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patient.getId().intValue())))
            .andExpect(jsonPath("$.[*].noAvs").value(hasItem(DEFAULT_NO_AVS)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].numeroAssure").value(hasItem(DEFAULT_NUMERO_ASSURE)))
            .andExpect(jsonPath("$.[*].groupeRisque").value(hasItem(DEFAULT_GROUPE_RISQUE.toString())));
    }
    
    @Test
    @Transactional
    public void getPatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patient.getId().intValue()))
            .andExpect(jsonPath("$.noAvs").value(DEFAULT_NO_AVS))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.numeroAssure").value(DEFAULT_NUMERO_ASSURE))
            .andExpect(jsonPath("$.groupeRisque").value(DEFAULT_GROUPE_RISQUE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPatient() throws Exception {
        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient
        Patient updatedPatient = patientRepository.findById(patient.getId()).get();
        // Disconnect from session so that the updates on updatedPatient are not directly saved in db
        em.detach(updatedPatient);
        updatedPatient
            .noAvs(UPDATED_NO_AVS)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .sexe(UPDATED_SEXE)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .numeroAssure(UPDATED_NUMERO_ASSURE)
            .groupeRisque(UPDATED_GROUPE_RISQUE);

        restPatientMockMvc.perform(put("/api/patients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatient)))
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getNoAvs()).isEqualTo(UPDATED_NO_AVS);
        assertThat(testPatient.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testPatient.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPatient.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPatient.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testPatient.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testPatient.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testPatient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPatient.getNumeroAssure()).isEqualTo(UPDATED_NUMERO_ASSURE);
        assertThat(testPatient.getGroupeRisque()).isEqualTo(UPDATED_GROUPE_RISQUE);
    }

    @Test
    @Transactional
    public void updateNonExistingPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientMockMvc.perform(put("/api/patients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeDelete = patientRepository.findAll().size();

        // Delete the patient
        restPatientMockMvc.perform(delete("/api/patients/{id}", patient.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
