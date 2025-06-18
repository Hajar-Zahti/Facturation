package com.exemple.facturation.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemple.facturation.model.Client;
import com.exemple.facturation.model.Facture;
import com.exemple.facturation.model.LigneFacture;
import com.exemple.facturation.repository.ClientRepository;
import com.exemple.facturation.repository.FactureRepository;

@Service
public class FactureService {

    @Autowired
    private FactureRepository factureRepository;  // Pour gérer les factures en base
    @Autowired
    private ClientRepository clientRepository;    // Pour vérifier les clients
    
    // Ensemble des taux TVA acceptés
    private static final Set<BigDecimal> TAUX_TVA_VALIDES = Set.of(
        BigDecimal.ZERO,
        new BigDecimal("0.055"),
        new BigDecimal("0.10"),
        new BigDecimal("0.20")
    );

    // Récupérer toutes les factures
    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    // Créer une facture avec validation des règles métier
    public Facture createFacture(Facture facture) {
        // La facture doit contenir au moins une ligne
        if (facture.getLignes() == null || facture.getLignes().isEmpty()) {
            throw new IllegalArgumentException("Une facture doit avoir au moins une ligne.");
        }

        // Vérifier que la facture a un client associé avec un id valide
        if (facture.getClient() == null || facture.getClient().getId() == null) {
            throw new IllegalArgumentException("Un client doit être associé à la facture.");
        }
        // Récupérer le client en base, sinon erreur
        Client client = clientRepository.findById(facture.getClient().getId())
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé avec l'ID: " + facture.getClient().getId()));
        facture.setClient(client);

        BigDecimal totalHt = BigDecimal.ZERO;  // Total hors taxe
        BigDecimal totalTva = BigDecimal.ZERO; // Total TVA

        // Parcourir les lignes pour valider et calculer les totaux
        for (LigneFacture ligne : facture.getLignes()) {
            // Vérifier que les champs essentiels sont bien renseignés et positifs
            if (ligne.getDescription() == null || ligne.getDescription().isEmpty() ||
                ligne.getQuantite() <= 0 ||
                ligne.getPrixUnitaireHt() == null || ligne.getPrixUnitaireHt().compareTo(BigDecimal.ZERO) <= 0 ||
                ligne.getTauxTva() == null) {
                throw new IllegalArgumentException("Tous les champs de la ligne de facture doivent être valides.");
            }

            // Vérifier que le taux de TVA est dans la liste autorisée
            if (!TAUX_TVA_VALIDES.contains(ligne.getTauxTva())) {
                throw new IllegalArgumentException("Taux de TVA invalide : " + ligne.getTauxTva() + ". Les taux acceptés sont : 0%, 5.5%, 10%, 20%.");
            }

            ligne.setFacture(facture); // Pour bien lier la ligne à la facture (relation bidirectionnelle)

            // Calcul du montant HT et de la TVA pour la ligne
            BigDecimal montantHtLigne = ligne.getPrixUnitaireHt().multiply(new BigDecimal(ligne.getQuantite()));
            BigDecimal montantTvaLigne = montantHtLigne.multiply(ligne.getTauxTva());

            totalHt = totalHt.add(montantHtLigne);
            totalTva = totalTva.add(montantTvaLigne);
        }

        // Mettre à jour les totaux de la facture
        facture.setTotalHt(totalHt);
        facture.setTotalTva(totalTva);
        facture.setTotalTtc(totalHt.add(totalTva));

        // Sauvegarder la facture en base
        return factureRepository.save(facture);
    }

    // Chercher une facture par son ID
    public Optional<Facture> getFactureById(Long id) {
        return factureRepository.findById(id);
    }
    
    // Chercher les factures d'un client donné
    public List<Facture> searchFacturesByClient(Long clientId) {
        return factureRepository.findByClientId(clientId);
    }

    // Chercher les factures d'une date précise
    public List<Facture> searchFacturesByDate(LocalDate date) {
        return factureRepository.findByDate(date);
    }
}
