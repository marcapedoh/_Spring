package com.gestiondestock.spring.Controller;

import com.gestiondestock.spring.Controller.API.ArticleAPi;
import com.gestiondestock.spring.DAO.ArticleDAO;
import com.gestiondestock.spring.DAO.LigneDeCommandeClientDAO;
import com.gestiondestock.spring.DAO.LigneDeCommandeFournisseurDAO;
import com.gestiondestock.spring.DAO.LigneVenteDAO;
import com.gestiondestock.spring.Services.ArticleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins="*")
public class ArticleController implements ArticleAPi {
    private ArticleServices articleServices;

    @Autowired
    public ArticleController(ArticleServices articleServices){
        this.articleServices=articleServices;
    }

    @Override
    public ArticleDAO save(ArticleDAO articleDAO) {
        return articleServices.save(articleDAO);
    }

    @Override
    public ArticleDAO findById(Integer id) {
        return articleServices.findById(id);
    }
    @Override
    public ArticleDAO findByCodeArticle(String codeArticle) {
        return articleServices.findByCodeArticle(codeArticle);
    }

    @Override
    public List<ArticleDAO> findAll() {
        return articleServices.findAll();
    }

    @Override
    public List<LigneVenteDAO> findHistoriqueVentes(Integer idArticle) {
        return articleServices.findHistoriqueVentes(idArticle);
    }

    @Override
    public List<LigneDeCommandeClientDAO> findHistoriaueCommandeClient(Integer idArticle) {
        return articleServices.findHistoriqueCommandeClient(idArticle);
    }

    @Override
    public List<LigneDeCommandeFournisseurDAO> findHistoriqueCommandeFournisseur(Integer idArticle) {
        return articleServices.findHistoriqueCommandeFournisseur(idArticle);
    }

    @Override
    public List<ArticleDAO> findAllArticleByIdCategory(Integer idCategory) {
        return articleServices.findAllArticleByIdCategory(idCategory);
    }

    @Override
    public void delete(Integer id) {
        articleServices.delete(id);
    }
}
