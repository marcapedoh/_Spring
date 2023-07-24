package com.gestiondestock.spring.Repository;

import com.gestiondestock.spring.models.LigneDeCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneDeCommandeFournisseurRepository extends JpaRepository<LigneDeCommandeFournisseur,Integer> {
    List<LigneDeCommandeFournisseur> findAllByCommandeFournisseursId(Integer idCommande);

    List<LigneDeCommandeFournisseur> findAllByArticleId(Integer idCommande);
}
