package com.bdi.fondation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Candidature.
 */
@Entity
@Table(name = "candidature")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Candidature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "status")
    private String status;

    @ManyToOne
    private Candidat candidat;

    @OneToOne
    @JoinColumn(unique = true)
    private Projet candidature;

    @OneToOne
    @JoinColumn(unique = true)
    private SessionProjet candidature;

    @OneToMany(mappedBy = "candidature")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Visite> candidatures = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Candidature type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public Candidature status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public Candidature candidat(Candidat candidat) {
        this.candidat = candidat;
        return this;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public Projet getCandidature() {
        return candidature;
    }

    public Candidature candidature(Projet projet) {
        this.candidature = projet;
        return this;
    }

    public void setCandidature(Projet projet) {
        this.candidature = projet;
    }

    public SessionProjet getCandidature() {
        return candidature;
    }

    public Candidature candidature(SessionProjet sessionProjet) {
        this.candidature = sessionProjet;
        return this;
    }

    public void setCandidature(SessionProjet sessionProjet) {
        this.candidature = sessionProjet;
    }

    public Set<Visite> getCandidatures() {
        return candidatures;
    }

    public Candidature candidatures(Set<Visite> visites) {
        this.candidatures = visites;
        return this;
    }

    public Candidature addCandidature(Visite visite) {
        this.candidatures.add(visite);
        visite.setCandidature(this);
        return this;
    }

    public Candidature removeCandidature(Visite visite) {
        this.candidatures.remove(visite);
        visite.setCandidature(null);
        return this;
    }

    public void setCandidatures(Set<Visite> visites) {
        this.candidatures = visites;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidature candidature = (Candidature) o;
        if (candidature.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidature.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Candidature{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
