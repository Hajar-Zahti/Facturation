package com.exemple.facturation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.exemple.facturation.model.Client;
import com.exemple.facturation.service.ClientService;

@RestController
@RequestMapping("/clients")  // Toutes les routes commencent par /clients
public class ClientController {

	@Autowired
	private ClientService clientService;  // Service pour gérer les clients
	
	// Récupérer la liste de tous les clients
	@GetMapping
	public List<Client> getAllClients(){
		return clientService.getAllClients();
	}
	
	// Créer un nouveau client
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)  // Retourner 201 Created
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    // Récupérer un client par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)               // Si trouvé, retourne 200 + client
                .orElse(ResponseEntity.notFound().build());  // Sinon 404 Not Found
    }
}
