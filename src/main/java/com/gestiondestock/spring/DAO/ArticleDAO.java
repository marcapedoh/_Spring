package com.gestiondestock.spring.DAO;

import com.gestiondestock.spring.models.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDAO {
    private Integer id;
    private String codeArticle;
    private String designation;
    private BigDecimal prixUnitaireHT;
    private BigDecimal tauxTVA;
    private BigDecimal prixUnitaireTTC;
    private String photo;
    private CategoryDAO category;

    public static ArticleDAO fromEntity(Article article){
        if(article== null){
            return null;
        }
        return ArticleDAO.builder()
                .id(article.getId())
                .codeArticle(article.getCodeArticle())
                .designation(article.getDesignation())
                .prixUnitaireHT(article.getPrixUnitaire())
                .tauxTVA(article.getTauxTVA())
                .prixUnitaireTTC(article.getPrixUnitaireTTC())
                .photo(article.getPhoto())
                .category(CategoryDAO.fromEntity(article.getCategory()))
                .build();
    }

    public static Article toEntity(ArticleDAO articleDAO){
        if(articleDAO==null){
            return null;
        }
        Article article=new Article();
        article.setCodeArticle(articleDAO.getCodeArticle());
        article.setDesignation(articleDAO.getDesignation());
        article.setId(articleDAO.getId());
        article.setPrixUnitaire(articleDAO.getPrixUnitaireHT());
        article.setTauxTVA(articleDAO.getTauxTVA());
        article.setPrixUnitaireTTC(articleDAO.getPrixUnitaireTTC());
        article.setPhoto(articleDAO.getPhoto());
        article.setCategory(CategoryDAO.toEntity(articleDAO.getCategory()));
        return article;
    }
}

