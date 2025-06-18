package com.exemple.facturation.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemple.facturation.model.Client;
import com.exemple.facturation.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;  // Injection du repository pour accéder aux clients
	
	// Récupérer tous les clients en base
	public List<Client> getAllClients(){
		return clientRepository.findAll();
	}
	
	// Créer un nouveau client avec validation simple
	public Client createClient(Client client) {
		// Vérifier que nom, email et siret ne sont pas vides
		if(client.getNom() == null || client.getNom().isEmpty()
				|| client.getEmail() == null || client.getEmail().isEmpty() ||
				client.getSiret() == null || client.getSiret().isEmpty()) {
				throw new IllegalArgumentException("tous les champs sont obligatoire") ;
		}
		// Si pas de date de création, on met la date du jour
		if (client.getDateCreation() == null) {
			client.setDateCreation(LocalDate.now());
		}
		// Sauvegarder le client en base
		return clientRepository.save(client);
	}
	
	// Chercher un client par son id
	public Optional<Client> getClientById(Long id){
		return clientRepository.findById(id);
	}
}
