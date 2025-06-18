package com.exemple.facturation.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.exemple.facturation.model.Facture;
import com.exemple.facturation.service.FactureService;

@RestController
@RequestMapping("/factures")  // Toutes les routes commencent par /factures
public class FactureController {

    @Autowired
    private FactureService factureService;  // Service pour gérer les factures

    // Récupérer toutes les factures
    @GetMapping
    public List<Facture> getAllFactures() {
        return factureService.getAllFactures();
    }

    // Créer une nouvelle facture
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)  // Retourner 201 Created
    public Facture createFacture(@RequestBody Facture facture) {
        return factureService.createFacture(facture);
    }

    // Récupérer une facture par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFactureById(@PathVariable Long id) {
        return factureService.getFactureById(id)
                .map(ResponseEntity::ok)               // Si trouvée, retourner 200 + facture
                .orElse(ResponseEntity.notFound().build());  // Sinon 404 Not Found
    }
    
    // Rechercher des factures par clientId ou par date (au moins un paramètre requis)
    @GetMapping("/search")
    public List<Facture> searchFactures(
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (clientId != null) {
            return factureService.searchFacturesByClient(clientId);
        } else if (date != null) {
            return factureService.searchFacturesByDate(date);
        } else {
            throw new IllegalArgumentException("Veuillez fournir un clientId ou une date pour la recherche.");
        }
    }
}
