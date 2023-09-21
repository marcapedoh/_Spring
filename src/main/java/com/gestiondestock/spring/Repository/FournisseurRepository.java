package com.gestiondestock.spring.Repository;

import com.gestiondestock.spring.models.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FournisseurRepository extends JpaRepository<Fournisseur,Integer> {
    Optional<Fournisseur> findFournisseurByNom(String nom);
    Optional<Fournisseur> findAllByLivraisonId(Integer id);
}
