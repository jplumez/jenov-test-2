package ch.jenov.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProfessionnelSante.
 */
@Entity
@Table(name = "professionnel_sante")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProfessionnelSante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @OneToMany(mappedBy = "executant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Vaccination> vaccinations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "professionnelSantes", allowSetters = true)
    private Localite localite;

    @ManyToOne
    @JsonIgnoreProperties(value = "professionnelSantes", allowSetters = true)
    private Centre centre;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public ProfessionnelSante nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Vaccination> getVaccinations() {
        return vaccinations;
    }

    public ProfessionnelSante vaccinations(Set<Vaccination> vaccinations) {
        this.vaccinations = vaccinations;
        return this;
    }

    public ProfessionnelSante addVaccination(Vaccination vaccination) {
        this.vaccinations.add(vaccination);
        vaccination.setExecutant(this);
        return this;
    }

    public ProfessionnelSante removeVaccination(Vaccination vaccination) {
        this.vaccinations.remove(vaccination);
        vaccination.setExecutant(null);
        return this;
    }

    public void setVaccinations(Set<Vaccination> vaccinations) {
        this.vaccinations = vaccinations;
    }

    public Localite getLocalite() {
        return localite;
    }

    public ProfessionnelSante localite(Localite localite) {
        this.localite = localite;
        return this;
    }

    public void setLocalite(Localite localite) {
        this.localite = localite;
    }

    public Centre getCentre() {
        return centre;
    }

    public ProfessionnelSante centre(Centre centre) {
        this.centre = centre;
        return this;
    }

    public void setCentre(Centre centre) {
        this.centre = centre;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfessionnelSante)) {
            return false;
        }
        return id != null && id.equals(((ProfessionnelSante) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfessionnelSante{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
