package com.gestiondestock.spring.Repository;

import com.gestiondestock.spring.models.Vente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VenteRepository extends JpaRepository<Vente,Integer> {
    Optional<Vente> findVenteByCode(String code);
}
