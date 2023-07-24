package com.gestiondestock.spring.Services;

import com.gestiondestock.spring.DAO.ArticleDAO;
import com.gestiondestock.spring.DAO.LigneDeCommandeClientDAO;
import com.gestiondestock.spring.DAO.LigneDeCommandeFournisseurDAO;
import com.gestiondestock.spring.DAO.LigneVenteDAO;

import java.util.List;

public interface ArticleServices {
    ArticleDAO save(ArticleDAO articleDAO);
    ArticleDAO findById(Integer id);
    ArticleDAO findByCodeArticle(String codeArticle);
    List<ArticleDAO> findAll();
    List<LigneVenteDAO> findHistoriqueVentes(Integer idArticle);

    List<LigneDeCommandeClientDAO> findHistoriqueCommandeClient(Integer idArticle);

    List<LigneDeCommandeFournisseurDAO> findHistoriqueCommandeFournisseur(Integer idArticle);

    List<ArticleDAO> findAllArticleByIdCategory(Integer idCategory);
    void delete(Integer id);
}
