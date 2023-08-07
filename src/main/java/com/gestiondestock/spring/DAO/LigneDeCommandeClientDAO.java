package com.gestiondestock.spring.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestiondestock.spring.models.LigneDeCommandeClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneDeCommandeClientDAO {
    private Integer id;
    private ArticleDAO article;
    @JsonIgnore
    private CommandeClientDAO commandeClient;
    private BigDecimal quantite;
    private BigDecimal prixUnitaire;

    public static LigneDeCommandeClientDAO fromEntity(LigneDeCommandeClient ligneDeCommandeClient){
        if(ligneDeCommandeClient==null){
            return null;
        }
        return LigneDeCommandeClientDAO.builder()
                .id(ligneDeCommandeClient.getId())
                .article(ArticleDAO.fromEntity(ligneDeCommandeClient.getArticle()))
                .quantite(ligneDeCommandeClient.getQuantite())
                .prixUnitaire(ligneDeCommandeClient.getPrixUnitaire())
                .build();
    }
    public static LigneDeCommandeClient toEntity(LigneDeCommandeClientDAO ligneDeCommandeClientDAO){
        if(ligneDeCommandeClientDAO==null){
            return null;
        }
        LigneDeCommandeClient ligneCommandeClient = new LigneDeCommandeClient();
        ligneCommandeClient.setId(ligneDeCommandeClientDAO.getId());
        ligneCommandeClient.setArticle(ArticleDAO.toEntity(ligneDeCommandeClientDAO.getArticle()));
        ligneCommandeClient.setPrixUnitaire(ligneDeCommandeClientDAO.getPrixUnitaire());
        ligneCommandeClient.setQuantite(ligneDeCommandeClientDAO.getQuantite());
        return ligneCommandeClient;
    }
}
