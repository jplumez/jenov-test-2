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
 * A LotVaccin.
 */
@Entity
@Table(name = "lot_vaccin")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LotVaccin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_initial")
    private Long stockInitial;

    @Column(name = "stock_actuel")
    private Long stockActuel;

    @Column(name = "heure_inventaire")
    private Instant heureInventaire;

    @OneToMany(mappedBy = "stockVaccin")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Vaccination> vaccinations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "lotVaccins", allowSetters = true)
    private Centre centre;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockInitial() {
        return stockInitial;
    }

    public LotVaccin stockInitial(Long stockInitial) {
        this.stockInitial = stockInitial;
        return this;
    }

    public void setStockInitial(Long stockInitial) {
        this.stockInitial = stockInitial;
    }

    public Long getStockActuel() {
        return stockActuel;
    }

    public LotVaccin stockActuel(Long stockActuel) {
        this.stockActuel = stockActuel;
        return this;
    }

    public void setStockActuel(Long stockActuel) {
        this.stockActuel = stockActuel;
    }

    public Instant getHeureInventaire() {
        return heureInventaire;
    }

    public LotVaccin heureInventaire(Instant heureInventaire) {
        this.heureInventaire = heureInventaire;
        return this;
    }

    public void setHeureInventaire(Instant heureInventaire) {
        this.heureInventaire = heureInventaire;
    }

    public Set<Vaccination> getVaccinations() {
        return vaccinations;
    }

    public LotVaccin vaccinations(Set<Vaccination> vaccinations) {
        this.vaccinations = vaccinations;
        return this;
    }

    public LotVaccin addVaccination(Vaccination vaccination) {
        this.vaccinations.add(vaccination);
        vaccination.setStockVaccin(this);
        return this;
    }

    public LotVaccin removeVaccination(Vaccination vaccination) {
        this.vaccinations.remove(vaccination);
        vaccination.setStockVaccin(null);
        return this;
    }

    public void setVaccinations(Set<Vaccination> vaccinations) {
        this.vaccinations = vaccinations;
    }

    public Centre getCentre() {
        return centre;
    }

    public LotVaccin centre(Centre centre) {
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
        if (!(o instanceof LotVaccin)) {
            return false;
        }
        return id != null && id.equals(((LotVaccin) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LotVaccin{" +
            "id=" + getId() +
            ", stockInitial=" + getStockInitial() +
            ", stockActuel=" + getStockActuel() +
            ", heureInventaire='" + getHeureInventaire() + "'" +
            "}";
    }
}
