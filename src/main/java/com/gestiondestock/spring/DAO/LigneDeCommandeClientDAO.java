package com.gestiondestock.spring.DAO;

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
                .commandeClient(CommandeClientDAO.fromEntity(ligneDeCommandeClient.getCommandeClient()))
                .quantite(ligneDeCommandeClient.getQuantite())
                .prixUnitaire(ligneDeCommandeClient.getPrixUnitaire())
                .build();
    }
    public static LigneDeCommandeClient toEntity(LigneDeCommandeClientDAO ligneDeCommandeClientDAO){
        if(ligneDeCommandeClientDAO==null){
            return null;
        }
        return LigneDeCommandeClient.builder()
                .article(ArticleDAO.toEntity(ligneDeCommandeClientDAO.getArticle()))
                .commandeClient(CommandeClientDAO.toEntity(ligneDeCommandeClientDAO.getCommandeClient()))
                .quantite(ligneDeCommandeClientDAO.getQuantite())
                .prixUnitaire(ligneDeCommandeClientDAO.getPrixUnitaire())
                .build();
    }
}
