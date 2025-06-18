package com.exemple.facturation.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exemple.facturation.model.Client;
@Repository
public interface ClientRepository extends JpaRepository<Client , Long>{

}
