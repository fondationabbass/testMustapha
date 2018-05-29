package com.bdi.fondation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Candidat.
 */
@Entity
@Table(name = "candidat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Candidat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "lieu_naissance")
    private String lieuNaissance;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "tel")
    private String tel;

    @Column(name = "situation")
    private String situation;

    @OneToOne
    @JoinColumn(unique = true)
    private Client candidat;

    @OneToMany(mappedBy = "candidat")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Candidature> candidats = new HashSet<>();

    @OneToMany(mappedBy = "candidat")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExperienceCandidat> candidats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Candidat nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Candidat prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Candidat dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public Candidat lieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
        return this;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public Candidat adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public Candidat tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSituation() {
        return situation;
    }

    public Candidat situation(String situation) {
        this.situation = situation;
        return this;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public Client getCandidat() {
        return candidat;
    }

    public Candidat candidat(Client client) {
        this.candidat = client;
        return this;
    }

    public void setCandidat(Client client) {
        this.candidat = client;
    }

    public Set<Candidature> getCandidats() {
        return candidats;
    }

    public Candidat candidats(Set<Candidature> candidatures) {
        this.candidats = candidatures;
        return this;
    }

    public Candidat addCandidat(Candidature candidature) {
        this.candidats.add(candidature);
        candidature.setCandidat(this);
        return this;
    }

    public Candidat removeCandidat(Candidature candidature) {
        this.candidats.remove(candidature);
        candidature.setCandidat(null);
        return this;
    }

    public void setCandidats(Set<Candidature> candidatures) {
        this.candidats = candidatures;
    }

    public Set<ExperienceCandidat> getCandidats() {
        return candidats;
    }

    public Candidat candidats(Set<ExperienceCandidat> experienceCandidats) {
        this.candidats = experienceCandidats;
        return this;
    }

    public Candidat addCandidat(ExperienceCandidat experienceCandidat) {
        this.candidats.add(experienceCandidat);
        experienceCandidat.setCandidat(this);
        return this;
    }

    public Candidat removeCandidat(ExperienceCandidat experienceCandidat) {
        this.candidats.remove(experienceCandidat);
        experienceCandidat.setCandidat(null);
        return this;
    }

    public void setCandidats(Set<ExperienceCandidat> experienceCandidats) {
        this.candidats = experienceCandidats;
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
        Candidat candidat = (Candidat) o;
        if (candidat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Candidat{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", lieuNaissance='" + getLieuNaissance() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", tel='" + getTel() + "'" +
            ", situation='" + getSituation() + "'" +
            "}";
    }
}
