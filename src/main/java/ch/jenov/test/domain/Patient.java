package ch.jenov.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import ch.jenov.test.domain.enumeration.Sexe;

import ch.jenov.test.domain.enumeration.GroupeRisque;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "no_avs", nullable = false)
    private String noAvs;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe")
    private Sexe sexe;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @Column(name = "numero_assure")
    private String numeroAssure;

    @Enumerated(EnumType.STRING)
    @Column(name = "groupe_risque")
    private GroupeRisque groupeRisque;

    @ManyToOne
    @JsonIgnoreProperties(value = "patients", allowSetters = true)
    private Vaccination vaccination;

    @ManyToOne
    @JsonIgnoreProperties(value = "patients", allowSetters = true)
    private Localite localite;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoAvs() {
        return noAvs;
    }

    public Patient noAvs(String noAvs) {
        this.noAvs = noAvs;
        return this;
    }

    public void setNoAvs(String noAvs) {
        this.noAvs = noAvs;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Patient dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNom() {
        return nom;
    }

    public Patient nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Patient prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public Patient sexe(Sexe sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getAdresse() {
        return adresse;
    }

    public Patient adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public Patient telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public Patient email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroAssure() {
        return numeroAssure;
    }

    public Patient numeroAssure(String numeroAssure) {
        this.numeroAssure = numeroAssure;
        return this;
    }

    public void setNumeroAssure(String numeroAssure) {
        this.numeroAssure = numeroAssure;
    }

    public GroupeRisque getGroupeRisque() {
        return groupeRisque;
    }

    public Patient groupeRisque(GroupeRisque groupeRisque) {
        this.groupeRisque = groupeRisque;
        return this;
    }

    public void setGroupeRisque(GroupeRisque groupeRisque) {
        this.groupeRisque = groupeRisque;
    }

    public Vaccination getVaccination() {
        return vaccination;
    }

    public Patient vaccination(Vaccination vaccination) {
        this.vaccination = vaccination;
        return this;
    }

    public void setVaccination(Vaccination vaccination) {
        this.vaccination = vaccination;
    }

    public Localite getLocalite() {
        return localite;
    }

    public Patient localite(Localite localite) {
        this.localite = localite;
        return this;
    }

    public void setLocalite(Localite localite) {
        this.localite = localite;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        return id != null && id.equals(((Patient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", noAvs='" + getNoAvs() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            ", numeroAssure='" + getNumeroAssure() + "'" +
            ", groupeRisque='" + getGroupeRisque() + "'" +
            "}";
    }
}
