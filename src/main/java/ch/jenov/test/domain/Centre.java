package ch.jenov.test.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Centre.
 */
@Entity
@Table(name = "centre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Centre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "localite", nullable = false)
    private String localite;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "centre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CreneauHoraire> creneauHoraires = new HashSet<>();

    @OneToMany(mappedBy = "centre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<LotVaccin> lotVaccins = new HashSet<>();

    @OneToMany(mappedBy = "centre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ProfessionnelSante> professionnelSantes = new HashSet<>();

    @OneToMany(mappedBy = "centre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Campagne> campagnes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Centre code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocalite() {
        return localite;
    }

    public Centre localite(String localite) {
        this.localite = localite;
        return this;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public String getDescription() {
        return description;
    }

    public Centre description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CreneauHoraire> getCreneauHoraires() {
        return creneauHoraires;
    }

    public Centre creneauHoraires(Set<CreneauHoraire> creneauHoraires) {
        this.creneauHoraires = creneauHoraires;
        return this;
    }

    public Centre addCreneauHoraire(CreneauHoraire creneauHoraire) {
        this.creneauHoraires.add(creneauHoraire);
        creneauHoraire.setCentre(this);
        return this;
    }

    public Centre removeCreneauHoraire(CreneauHoraire creneauHoraire) {
        this.creneauHoraires.remove(creneauHoraire);
        creneauHoraire.setCentre(null);
        return this;
    }

    public void setCreneauHoraires(Set<CreneauHoraire> creneauHoraires) {
        this.creneauHoraires = creneauHoraires;
    }

    public Set<LotVaccin> getLotVaccins() {
        return lotVaccins;
    }

    public Centre lotVaccins(Set<LotVaccin> lotVaccins) {
        this.lotVaccins = lotVaccins;
        return this;
    }

    public Centre addLotVaccin(LotVaccin lotVaccin) {
        this.lotVaccins.add(lotVaccin);
        lotVaccin.setCentre(this);
        return this;
    }

    public Centre removeLotVaccin(LotVaccin lotVaccin) {
        this.lotVaccins.remove(lotVaccin);
        lotVaccin.setCentre(null);
        return this;
    }

    public void setLotVaccins(Set<LotVaccin> lotVaccins) {
        this.lotVaccins = lotVaccins;
    }

    public Set<ProfessionnelSante> getProfessionnelSantes() {
        return professionnelSantes;
    }

    public Centre professionnelSantes(Set<ProfessionnelSante> professionnelSantes) {
        this.professionnelSantes = professionnelSantes;
        return this;
    }

    public Centre addProfessionnelSante(ProfessionnelSante professionnelSante) {
        this.professionnelSantes.add(professionnelSante);
        professionnelSante.setCentre(this);
        return this;
    }

    public Centre removeProfessionnelSante(ProfessionnelSante professionnelSante) {
        this.professionnelSantes.remove(professionnelSante);
        professionnelSante.setCentre(null);
        return this;
    }

    public void setProfessionnelSantes(Set<ProfessionnelSante> professionnelSantes) {
        this.professionnelSantes = professionnelSantes;
    }

    public Set<Campagne> getCampagnes() {
        return campagnes;
    }

    public Centre campagnes(Set<Campagne> campagnes) {
        this.campagnes = campagnes;
        return this;
    }

    public Centre addCampagne(Campagne campagne) {
        this.campagnes.add(campagne);
        campagne.setCentre(this);
        return this;
    }

    public Centre removeCampagne(Campagne campagne) {
        this.campagnes.remove(campagne);
        campagne.setCentre(null);
        return this;
    }

    public void setCampagnes(Set<Campagne> campagnes) {
        this.campagnes = campagnes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Centre)) {
            return false;
        }
        return id != null && id.equals(((Centre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Centre{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", localite='" + getLocalite() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
