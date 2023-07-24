package com.gestiondestock.spring.Repository;

import com.gestiondestock.spring.models.CommandeClient;
import com.gestiondestock.spring.models.CommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur,Integer> {
    Optional<CommandeFournisseur> findCommandeFournisseurByCode(String code);
    List<CommandeFournisseur> findAllByFournisseurId(Integer id);
}
