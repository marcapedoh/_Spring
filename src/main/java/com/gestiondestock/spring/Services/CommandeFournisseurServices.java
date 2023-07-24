package com.gestiondestock.spring.Services;

import com.gestiondestock.spring.DAO.CommandeFournisseurDAO;
import com.gestiondestock.spring.DAO.LigneDeCommandeFournisseurDAO;
import com.gestiondestock.spring.models.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeFournisseurServices {

    CommandeFournisseurDAO save(CommandeFournisseurDAO commandeFournisseurDAO);
    CommandeFournisseurDAO updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    CommandeFournisseurDAO updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    CommandeFournisseurDAO updateFournisseur(Integer idCommande, Integer idFournisseur);

    CommandeFournisseurDAO updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle);

    // Delete article ==> delete LigneCommandeFournisseur
    CommandeFournisseurDAO deleteArticle(Integer idCommande, Integer idLigneCommande);
    CommandeFournisseurDAO findById(Integer id);
    CommandeFournisseurDAO findByCode(String code);
    List<CommandeFournisseurDAO> findAll();
    List<LigneDeCommandeFournisseurDAO> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande);
    void delete(Integer id);
}
