package com.exemple.facturation.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Client {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String nom;
private String email;
private String siret;
private LocalDate dateCreation;

public Client() {
	
}

public Client(Long id , String nom , String siret , LocalDate dateCreation) {
	this.id=id;
	this.nom=nom;
	this.email=email;
	this.dateCreation=dateCreation;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getSiret() {
	return siret;
}
public void setSiret(String siret) {
	this.siret = siret;
}
public LocalDate getDateCreation() {
	return dateCreation;
}
public void setDateCreation(LocalDate dateCreation) {
	this.dateCreation = dateCreation;
}

}
