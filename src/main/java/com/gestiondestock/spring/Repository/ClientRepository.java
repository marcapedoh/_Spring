package com.gestiondestock.spring.Repository;

import com.gestiondestock.spring.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Integer> {
    Optional<Client> findClientByNom(String nom);
}
