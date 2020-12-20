package ch.jenov.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Vaccination.
 */
@Entity
@Table(name = "vaccination")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Vaccination implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_creation")
    private Instant dateCreation;

    @Column(name = "date_rendez_vous")
    private Instant dateRendezVous;

    @Column(name = "date_vaccin")
    private Instant dateVaccin;

    @ManyToOne
    @JsonIgnoreProperties(value = "vaccinations", allowSetters = true)
    private CreneauHoraire creneau;

    @ManyToOne
    @JsonIgnoreProperties(value = "vaccinations", allowSetters = true)
    private LotVaccin stockVaccin;

    @ManyToOne
    @JsonIgnoreProperties(value = "vaccinations", allowSetters = true)
    private ProfessionnelSante executant;

    @OneToMany(mappedBy = "vaccination")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Patient> patients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateCreation() {
        return dateCreation;
    }

    public Vaccination dateCreation(Instant dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(Instant dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Instant getDateRendezVous() {
        return dateRendezVous;
    }

    public Vaccination dateRendezVous(Instant dateRendezVous) {
        this.dateRendezVous = dateRendezVous;
        return this;
    }

    public void setDateRendezVous(Instant dateRendezVous) {
        this.dateRendezVous = dateRendezVous;
    }

    public Instant getDateVaccin() {
        return dateVaccin;
    }

    public Vaccination dateVaccin(Instant dateVaccin) {
        this.dateVaccin = dateVaccin;
        return this;
    }

    public void setDateVaccin(Instant dateVaccin) {
        this.dateVaccin = dateVaccin;
    }

    public CreneauHoraire getCreneau() {
        return creneau;
    }

    public Vaccination creneau(CreneauHoraire creneauHoraire) {
        this.creneau = creneauHoraire;
        return this;
    }

    public void setCreneau(CreneauHoraire creneauHoraire) {
        this.creneau = creneauHoraire;
    }

    public LotVaccin getStockVaccin() {
        return stockVaccin;
    }

    public Vaccination stockVaccin(LotVaccin lotVaccin) {
        this.stockVaccin = lotVaccin;
        return this;
    }

    public void setStockVaccin(LotVaccin lotVaccin) {
        this.stockVaccin = lotVaccin;
    }

    public ProfessionnelSante getExecutant() {
        return executant;
    }

    public Vaccination executant(ProfessionnelSante professionnelSante) {
        this.executant = professionnelSante;
        return this;
    }

    public void setExecutant(ProfessionnelSante professionnelSante) {
        this.executant = professionnelSante;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public Vaccination patients(Set<Patient> patients) {
        this.patients = patients;
        return this;
    }

    public Vaccination addPatient(Patient patient) {
        this.patients.add(patient);
        patient.setVaccination(this);
        return this;
    }

    public Vaccination removePatient(Patient patient) {
        this.patients.remove(patient);
        patient.setVaccination(null);
        return this;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vaccination)) {
            return false;
        }
        return id != null && id.equals(((Vaccination) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vaccination{" +
            "id=" + getId() +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateRendezVous='" + getDateRendezVous() + "'" +
            ", dateVaccin='" + getDateVaccin() + "'" +
            "}";
    }
}
