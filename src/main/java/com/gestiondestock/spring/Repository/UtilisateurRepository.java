package com.gestiondestock.spring.Repository;

import com.gestiondestock.spring.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {
    Optional<Utilisateur> findUtilisateurByNom(String nom);
    Optional<Utilisateur>findUtilisateurByMailAndMotDePasse(String mail,String password);
    Optional<Utilisateur>findUtilisateurByMail(String mail);
}
