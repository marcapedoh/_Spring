package com.gestiondestock.spring.Repository;

import com.gestiondestock.spring.models.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LivraisonRepository extends JpaRepository<Livraison,Integer> {

    Optional<Livraison> findLivraisonByCode(String code);
}
