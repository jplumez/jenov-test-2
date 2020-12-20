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
 * A CreneauHoraire.
 */
@Entity
@Table(name = "creneau_horaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CreneauHoraire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "capacite")
    private Integer capacite;

    @Column(name = "heure_debut")
    private Instant heureDebut;

    @Column(name = "heure_fin")
    private Instant heureFin;

    @OneToMany(mappedBy = "creneau")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Vaccination> vaccinations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "creneauHoraires", allowSetters = true)
    private Centre centre;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public CreneauHoraire capacite(Integer capacite) {
        this.capacite = capacite;
        return this;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public Instant getHeureDebut() {
        return heureDebut;
    }

    public CreneauHoraire heureDebut(Instant heureDebut) {
        this.heureDebut = heureDebut;
        return this;
    }

    public void setHeureDebut(Instant heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Instant getHeureFin() {
        return heureFin;
    }

    public CreneauHoraire heureFin(Instant heureFin) {
        this.heureFin = heureFin;
        return this;
    }

    public void setHeureFin(Instant heureFin) {
        this.heureFin = heureFin;
    }

    public Set<Vaccination> getVaccinations() {
        return vaccinations;
    }

    public CreneauHoraire vaccinations(Set<Vaccination> vaccinations) {
        this.vaccinations = vaccinations;
        return this;
    }

    public CreneauHoraire addVaccination(Vaccination vaccination) {
        this.vaccinations.add(vaccination);
        vaccination.setCreneau(this);
        return this;
    }

    public CreneauHoraire removeVaccination(Vaccination vaccination) {
        this.vaccinations.remove(vaccination);
        vaccination.setCreneau(null);
        return this;
    }

    public void setVaccinations(Set<Vaccination> vaccinations) {
        this.vaccinations = vaccinations;
    }

    public Centre getCentre() {
        return centre;
    }

    public CreneauHoraire centre(Centre centre) {
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
        if (!(o instanceof CreneauHoraire)) {
            return false;
        }
        return id != null && id.equals(((CreneauHoraire) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreneauHoraire{" +
            "id=" + getId() +
            ", capacite=" + getCapacite() +
            ", heureDebut='" + getHeureDebut() + "'" +
            ", heureFin='" + getHeureFin() + "'" +
            "}";
    }
}
