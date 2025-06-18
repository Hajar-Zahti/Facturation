# Facturation
Mini-module de facturation : Cette application permet de gérer un système de facturation simple avec les fonctionnalités suivantes : 
- Création et gestion des clients
- Émission de factures détaillées avec lignes de factures
- Calcul automatique des montants HT, TVA et TTC
- Export des factures au format JSON
- Utilisation d’une base de données en mémoire H2 pour simplifier le développement



## Technologies utilisées

- Java 17
- Spring Boot (Spring Web, Spring Data JPA, Spring Security)
- Base de données H2 (en mémoire)
- Maven

## Tester l'API

Vous pouvez utiliser **Postman** ou tout autre client HTTP pour tester les endpoints de l’API.

## Endpoints principaux
- `/clients` : gestion des clients (GET, POST, etc.)
- `/factures` : gestion des factures (GET, POST, recherche)
