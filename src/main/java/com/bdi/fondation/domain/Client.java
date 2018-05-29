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
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_creat")
    private LocalDate dateCreat;

    @Column(name = "lieu_resid")
    private String lieuResid;

    @Column(name = "type_resid")
    private String typeResid;

    @Column(name = "arrond_resid")
    private String arrondResid;

    @Column(name = "nom_personne_contact")
    private String nomPersonneContact;

    @Column(name = "tel_personne_contact")
    private String telPersonneContact;

    @Column(name = "adress_personne_contact")
    private String adressPersonneContact;

    @Column(name = "type_client")
    private String typeClient;

    @Column(name = "points_fidel")
    private Double pointsFidel;

    @Column(name = "date_maj")
    private LocalDate dateMaj;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Compte> clients = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pret> clients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreat() {
        return dateCreat;
    }

    public Client dateCreat(LocalDate dateCreat) {
        this.dateCreat = dateCreat;
        return this;
    }

    public void setDateCreat(LocalDate dateCreat) {
        this.dateCreat = dateCreat;
    }

    public String getLieuResid() {
        return lieuResid;
    }

    public Client lieuResid(String lieuResid) {
        this.lieuResid = lieuResid;
        return this;
    }

    public void setLieuResid(String lieuResid) {
        this.lieuResid = lieuResid;
    }

    public String getTypeResid() {
        return typeResid;
    }

    public Client typeResid(String typeResid) {
        this.typeResid = typeResid;
        return this;
    }

    public void setTypeResid(String typeResid) {
        this.typeResid = typeResid;
    }

    public String getArrondResid() {
        return arrondResid;
    }

    public Client arrondResid(String arrondResid) {
        this.arrondResid = arrondResid;
        return this;
    }

    public void setArrondResid(String arrondResid) {
        this.arrondResid = arrondResid;
    }

    public String getNomPersonneContact() {
        return nomPersonneContact;
    }

    public Client nomPersonneContact(String nomPersonneContact) {
        this.nomPersonneContact = nomPersonneContact;
        return this;
    }

    public void setNomPersonneContact(String nomPersonneContact) {
        this.nomPersonneContact = nomPersonneContact;
    }

    public String getTelPersonneContact() {
        return telPersonneContact;
    }

    public Client telPersonneContact(String telPersonneContact) {
        this.telPersonneContact = telPersonneContact;
        return this;
    }

    public void setTelPersonneContact(String telPersonneContact) {
        this.telPersonneContact = telPersonneContact;
    }

    public String getAdressPersonneContact() {
        return adressPersonneContact;
    }

    public Client adressPersonneContact(String adressPersonneContact) {
        this.adressPersonneContact = adressPersonneContact;
        return this;
    }

    public void setAdressPersonneContact(String adressPersonneContact) {
        this.adressPersonneContact = adressPersonneContact;
    }

    public String getTypeClient() {
        return typeClient;
    }

    public Client typeClient(String typeClient) {
        this.typeClient = typeClient;
        return this;
    }

    public void setTypeClient(String typeClient) {
        this.typeClient = typeClient;
    }

    public Double getPointsFidel() {
        return pointsFidel;
    }

    public Client pointsFidel(Double pointsFidel) {
        this.pointsFidel = pointsFidel;
        return this;
    }

    public void setPointsFidel(Double pointsFidel) {
        this.pointsFidel = pointsFidel;
    }

    public LocalDate getDateMaj() {
        return dateMaj;
    }

    public Client dateMaj(LocalDate dateMaj) {
        this.dateMaj = dateMaj;
        return this;
    }

    public void setDateMaj(LocalDate dateMaj) {
        this.dateMaj = dateMaj;
    }

    public Set<Compte> getClients() {
        return clients;
    }

    public Client clients(Set<Compte> comptes) {
        this.clients = comptes;
        return this;
    }

    public Client addClient(Compte compte) {
        this.clients.add(compte);
        compte.setClient(this);
        return this;
    }

    public Client removeClient(Compte compte) {
        this.clients.remove(compte);
        compte.setClient(null);
        return this;
    }

    public void setClients(Set<Compte> comptes) {
        this.clients = comptes;
    }

    public Set<Pret> getClients() {
        return clients;
    }

    public Client clients(Set<Pret> prets) {
        this.clients = prets;
        return this;
    }

    public Client addClient(Pret pret) {
        this.clients.add(pret);
        pret.setClient(this);
        return this;
    }

    public Client removeClient(Pret pret) {
        this.clients.remove(pret);
        pret.setClient(null);
        return this;
    }

    public void setClients(Set<Pret> prets) {
        this.clients = prets;
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
        Client client = (Client) o;
        if (client.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), client.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", dateCreat='" + getDateCreat() + "'" +
            ", lieuResid='" + getLieuResid() + "'" +
            ", typeResid='" + getTypeResid() + "'" +
            ", arrondResid='" + getArrondResid() + "'" +
            ", nomPersonneContact='" + getNomPersonneContact() + "'" +
            ", telPersonneContact='" + getTelPersonneContact() + "'" +
            ", adressPersonneContact='" + getAdressPersonneContact() + "'" +
            ", typeClient='" + getTypeClient() + "'" +
            ", pointsFidel=" + getPointsFidel() +
            ", dateMaj='" + getDateMaj() + "'" +
            "}";
    }
}
