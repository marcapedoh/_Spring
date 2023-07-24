package com.gestiondestock.spring.DAO;

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
                .ventes(VenteDAO.fromEntity(ligneVente.getVentes()))
                .article(ArticleDAO.fromEntity(ligneVente.getArticle()))
                .quantite(ligneVente.getQuantite())
                .prixUnitaire(ligneVente.getPrixUnitaire())
                .build();
    }

    public static LigneVente toEntity(LigneVenteDAO ligneVenteDAO){
        if(ligneVenteDAO==null){
            return null;
        }
        return LigneVente.builder()
                .ventes(VenteDAO.toEntity(ligneVenteDAO.getVentes()))
                .article(ArticleDAO.toEntity(ligneVenteDAO.getArticle()))
                .quantite(ligneVenteDAO.getQuantite())
                .prixUnitaire(ligneVenteDAO.getPrixUnitaire())
                .build();

    }
}
