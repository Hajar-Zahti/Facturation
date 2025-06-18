package com.exemple.facturation.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity // Cette classe représente une ligne de facture dans la base de données
public class LigneFacture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-incrémenté
    private Long id;

    private String description; // Description du produit ou service
    private int quantite; // Quantité achetée
    private BigDecimal prixUnitaireHt; // Prix unitaire hors taxe
    private BigDecimal tauxTva; // Taux de TVA (ex : 0.055, 0.10, 0.20)

    @ManyToOne // Plusieurs lignes peuvent être liées à une seule facture
    @JoinColumn(name = "facture_id", nullable = false) // Clé étrangère vers la table facture
    @JsonBackReference // Pour éviter la boucle infinie lors de la sérialisation JSON (relation inverse de @JsonManagedReference)
    private Facture facture;

    // Getters et Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixUnitaireHt() {
        return prixUnitaireHt;
    }
    public void setPrixUnitaireHt(BigDecimal prixUnitaireHt) {
        this.prixUnitaireHt = prixUnitaireHt;
    }

    public BigDecimal getTauxTva() {
        return tauxTva;
    }
    public void setTauxTva(BigDecimal tauxTva) {
        this.tauxTva = tauxTva;
    }

    public Facture getFacture() {
        return facture;
    }
    public void setFacture(Facture facture) {
        this.facture = facture;
    }
}
