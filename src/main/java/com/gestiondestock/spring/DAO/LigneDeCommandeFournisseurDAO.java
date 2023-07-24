package com.gestiondestock.spring.DAO;

import com.gestiondestock.spring.models.LigneDeCommandeFournisseur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneDeCommandeFournisseurDAO {
    private Integer id;
    private ArticleDAO article;
    private CommandeFournisseurDAO commandeFournisseurs;
    private BigDecimal quantite;
    private BigDecimal prixUnitaire;

    public static LigneDeCommandeFournisseurDAO fromEntity(LigneDeCommandeFournisseur ligneDeCommandeFournisseur){
        if(ligneDeCommandeFournisseur==null){
            return null;
        }
        return LigneDeCommandeFournisseurDAO.builder()
                .id(ligneDeCommandeFournisseur.getId())
                .article(ArticleDAO.fromEntity(ligneDeCommandeFournisseur.getArticle()))
                .commandeFournisseurs(CommandeFournisseurDAO.fromEntity(ligneDeCommandeFournisseur.getCommandeFournisseurs()))
                .quantite(ligneDeCommandeFournisseur.getQuantite())
                .prixUnitaire(ligneDeCommandeFournisseur.getPrixUnitaire())
                .build();
    }

    public static LigneDeCommandeFournisseur toEntity(LigneDeCommandeFournisseurDAO ligneDeCommandeFournisseurDAO){
        if(ligneDeCommandeFournisseurDAO==null){
            return null;
        }
        return LigneDeCommandeFournisseur.builder()
                .article(ArticleDAO.toEntity(ligneDeCommandeFournisseurDAO.getArticle()))
                .commandeFournisseurs(CommandeFournisseurDAO.toEntity(ligneDeCommandeFournisseurDAO.getCommandeFournisseurs()))
                .quantite(ligneDeCommandeFournisseurDAO.getQuantite())
                .prixUnitaire(ligneDeCommandeFournisseurDAO.getPrixUnitaire())
                .build();
    }

}
