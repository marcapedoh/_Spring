package com.gestiondestock.spring.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestiondestock.spring.models.LigneVente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneVenteDAO {
    private Integer id;
    @JsonIgnore
    private VenteDAO ventes;
    private ArticleDAO article;
    private BigDecimal quantite;
    private BigDecimal prixUnitaire;

    public static LigneVenteDAO fromEntity(LigneVente ligneVente){
        if(ligneVente==null){
            return null;
        }
        return LigneVenteDAO.builder()
                .id(ligneVente.getId())
                .article(ArticleDAO.fromEntity(ligneVente.getArticle()))
                .quantite(ligneVente.getQuantite())
                .prixUnitaire(ligneVente.getPrixUnitaire())
                .build();
    }

    public static LigneVente toEntity(LigneVenteDAO ligneVenteDAO){
        if(ligneVenteDAO==null){
            return null;
        }
        LigneVente ligneVente=new LigneVente();
        ligneVente.setId(ligneVenteDAO.getId());
        ligneVente.setArticle(ArticleDAO.toEntity(ligneVenteDAO.getArticle()));
        ligneVente.setQuantite(ligneVenteDAO.getQuantite());
        ligneVente.setPrixUnitaire(ligneVenteDAO.getPrixUnitaire());
        return ligneVente;

    }
}
