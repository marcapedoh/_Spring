package com.gestiondestock.spring.Repository;

import com.gestiondestock.spring.models.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LigneVenteRepository extends JpaRepository<LigneVente,Integer> {
    Optional<LigneVente> findAllByArticleId(Integer idArticle);
    Optional<LigneVente> findAllByVentesId(Integer id);
}
