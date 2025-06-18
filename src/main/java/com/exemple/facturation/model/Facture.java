package com.exemple.facturation.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity // Cette classe représente la table "facture"
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Génération automatique de l'ID
    private Long id;

    private LocalDate date; // Date de la facture

    @ManyToOne // Une facture est liée à un seul client
    @JoinColumn(name = "client_id", nullable = false) // Clé étrangère vers le client
    private Client client;

    @JsonManagedReference // Gère la relation bidirectionnelle avec LigneFacture (pour éviter les boucles)
    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneFacture> lignes = new ArrayList<>(); // Les lignes de cette facture

    private BigDecimal totalHt;  // Total hors taxes
    private BigDecimal totalTva; // Montant de la TVA
    private BigDecimal totalTtc; // Total TTC (HT + TVA)

    // Getters et setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public List<LigneFacture> getLignes() {
        return lignes;
    }
    public void setLignes(List<LigneFacture> lignes) {
        this.lignes = lignes;
    }

    public BigDecimal getTotalHt() {
        return totalHt;
    }
    public void setTotalHt(BigDecimal totalHt) {
        this.totalHt = totalHt;
    }

    public BigDecimal getTotalTva() {
        return totalTva;
    }
    public void setTotalTva(BigDecimal totalTva) {
        this.totalTva = totalTva;
    }

    public BigDecimal getTotalTtc() {
        return totalTtc;
    }
    public void setTotalTtc(BigDecimal totalTtc) {
        this.totalTtc = totalTtc;
    }
}
