package com.gestiondestock.spring.DAO;

import com.gestiondestock.spring.models.CommandeFournisseur;
import com.gestiondestock.spring.models.Fournisseur;
import com.gestiondestock.spring.models.Livraison;
import com.gestiondestock.spring.models.TypeMvtStk;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivraisonDAO {
    private Integer id;
    private String code;
    private String commentaire;
    private Instant dateLivraison;
    private TypeMvtStk typeMvtStk;
    private Fournisseur fournisseur;
    private CommandeFournisseur commandeFournisseur;

    public static LivraisonDAO fromEntity(Livraison livraison){
        if(livraison== null){
            return null;
        }
        return LivraisonDAO.builder()
                .id(livraison.getId())
                .code(livraison.getCode())
                .commentaire(livraison.getCommentaire())
                .dateLivraison(Instant.now())
                .typeMvtStk(livraison.getTypeMvtStk())
                .fournisseur(livraison.getFournisseur())
                .commandeFournisseur(livraison.getCommandeFournisseur())
                .build();
    }
    public static Livraison toEntity(LivraisonDAO livraisonDAO){
        Livraison livraison=new Livraison();
        livraison.setId(livraisonDAO.getId());
        livraison.setCode(livraisonDAO.getCode());
        livraison.setCommentaire(livraisonDAO.getCommentaire());
        livraison.setDateLivraison(livraisonDAO.getDateLivraison());
        livraison.setFournisseur(livraisonDAO.getFournisseur());
        livraison.setCommandeFournisseur(livraisonDAO.getCommandeFournisseur());
        return livraison;
    }
}
