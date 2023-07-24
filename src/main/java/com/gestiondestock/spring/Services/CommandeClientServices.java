package com.gestiondestock.spring.Services;

import com.gestiondestock.spring.DAO.CommandeClientDAO;
import com.gestiondestock.spring.DAO.LigneDeCommandeClientDAO;
import com.gestiondestock.spring.models.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeClientServices {
    CommandeClientDAO save(CommandeClientDAO commandeClientDAO);
    CommandeClientDAO updateEtatCommande(Integer idCommande, EtatCommande etatCommande);
    CommandeClientDAO updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);
    CommandeClientDAO updateClient(Integer idCommande, Integer idClient);
    CommandeClientDAO updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle);
    CommandeClientDAO deleteArticle(Integer idCommande, Integer idLigneCommande);
    CommandeClientDAO findById(Integer id);
    CommandeClientDAO findByCode(String code);
    List<CommandeClientDAO> findAll();
    List<LigneDeCommandeClientDAO> findAllLignesCommandesClientByCommandeClientId(Integer idCommande);
    void delete(Integer id);
}
