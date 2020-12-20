package ch.jenov.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Campagne.
 */
@Entity
@Table(name = "campagne")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Campagne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "debut")
    private LocalDate debut;

    @Column(name = "fin")
    private LocalDate fin;

    @Column(name = "nb_jours_rappel")
    private Integer nbJoursRappel;

    @Column(name = "remarques")
    private String remarques;

    @ManyToOne
    @JsonIgnoreProperties(value = "campagnes", allowSetters = true)
    private Centre centre;

    @OneToMany(mappedBy = "campagne")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Questionnaire> questionnaires = new HashSet<>();

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

    public Campagne nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public Campagne description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public Campagne debut(LocalDate debut) {
        this.debut = debut;
        return this;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public Campagne fin(LocalDate fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public Integer getNbJoursRappel() {
        return nbJoursRappel;
    }

    public Campagne nbJoursRappel(Integer nbJoursRappel) {
        this.nbJoursRappel = nbJoursRappel;
        return this;
    }

    public void setNbJoursRappel(Integer nbJoursRappel) {
        this.nbJoursRappel = nbJoursRappel;
    }

    public String getRemarques() {
        return remarques;
    }

    public Campagne remarques(String remarques) {
        this.remarques = remarques;
        return this;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    public Centre getCentre() {
        return centre;
    }

    public Campagne centre(Centre centre) {
        this.centre = centre;
        return this;
    }

    public void setCentre(Centre centre) {
        this.centre = centre;
    }

    public Set<Questionnaire> getQuestionnaires() {
        return questionnaires;
    }

    public Campagne questionnaires(Set<Questionnaire> questionnaires) {
        this.questionnaires = questionnaires;
        return this;
    }

    public Campagne addQuestionnaire(Questionnaire questionnaire) {
        this.questionnaires.add(questionnaire);
        questionnaire.setCampagne(this);
        return this;
    }

    public Campagne removeQuestionnaire(Questionnaire questionnaire) {
        this.questionnaires.remove(questionnaire);
        questionnaire.setCampagne(null);
        return this;
    }

    public void setQuestionnaires(Set<Questionnaire> questionnaires) {
        this.questionnaires = questionnaires;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Campagne)) {
            return false;
        }
        return id != null && id.equals(((Campagne) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Campagne{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", debut='" + getDebut() + "'" +
            ", fin='" + getFin() + "'" +
            ", nbJoursRappel=" + getNbJoursRappel() +
            ", remarques='" + getRemarques() + "'" +
            "}";
    }
}
