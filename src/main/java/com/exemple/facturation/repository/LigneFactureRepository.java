package com.exemple.facturation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exemple.facturation.model.LigneFacture;
@Repository
public interface LigneFactureRepository extends JpaRepository<LigneFacture, Long>{

}
