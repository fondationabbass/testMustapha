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
 * A Chapitre.
 */
@Entity
@Table(name = "chapitre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Chapitre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "lib_chapitre")
    private String libChapitre;

    @Column(name = "categorie_compte")
    private String categorieCompte;

    @OneToMany(mappedBy = "chapitre")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Compte> chapitres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibChapitre() {
        return libChapitre;
    }

    public Chapitre libChapitre(String libChapitre) {
        this.libChapitre = libChapitre;
        return this;
    }

    public void setLibChapitre(String libChapitre) {
        this.libChapitre = libChapitre;
    }

    public String getCategorieCompte() {
        return categorieCompte;
    }

    public Chapitre categorieCompte(String categorieCompte) {
        this.categorieCompte = categorieCompte;
        return this;
    }

    public void setCategorieCompte(String categorieCompte) {
        this.categorieCompte = categorieCompte;
    }

    public Set<Compte> getChapitres() {
        return chapitres;
    }

    public Chapitre chapitres(Set<Compte> comptes) {
        this.chapitres = comptes;
        return this;
    }

    public Chapitre addChapitre(Compte compte) {
        this.chapitres.add(compte);
        compte.setChapitre(this);
        return this;
    }

    public Chapitre removeChapitre(Compte compte) {
        this.chapitres.remove(compte);
        compte.setChapitre(null);
        return this;
    }

    public void setChapitres(Set<Compte> comptes) {
        this.chapitres = comptes;
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
        Chapitre chapitre = (Chapitre) o;
        if (chapitre.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chapitre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Chapitre{" +
            "id=" + getId() +
            ", libChapitre='" + getLibChapitre() + "'" +
            ", categorieCompte='" + getCategorieCompte() + "'" +
            "}";
    }
}
