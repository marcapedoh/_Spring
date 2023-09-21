package com.gestiondestock.spring.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestiondestock.spring.models.CommandeFournisseur;
import com.gestiondestock.spring.models.Fournisseur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FournisseurDAO {
    private Integer id;
    private String nom;
    private  String prenom;
    private String ville;

    private String codePostale;

    private String pays;
    private String email;

    private String photo;

    private String numTel;

    @JsonIgnore
    private List<CommandeFournisseur> commandeFournisseurs;

    public static FournisseurDAO fromEntity(Fournisseur fournisseur){
        if(fournisseur==null){
            return null;
        }
        return FournisseurDAO.builder()
                .id(fournisseur.getId())
                .nom(fournisseur.getNom())
                .prenom(fournisseur.getPrenom())
                .ville(fournisseur.getVille())
                .codePostale(fournisseur.getCodePostale())
                .pays(fournisseur.getPays())
                .email(fournisseur.getEmail())
                .photo(fournisseur.getPhoto())
                .numTel(fournisseur.getNumTel())
                .build();
    }

    public static Fournisseur toEntity(FournisseurDAO fournisseurDAO){
        if(fournisseurDAO==null){
            return null;
        }
        Fournisseur fournisseur =new Fournisseur();
        fournisseur.setId(fournisseurDAO.getId());
        fournisseur.setNom(fournisseurDAO.getNom());
        fournisseur.setEmail(fournisseurDAO.getEmail());
        fournisseur.setPays(fournisseurDAO.getPays());
        fournisseur.setVille(fournisseurDAO.getVille());
        fournisseur.setPhoto(fournisseurDAO.getPhoto());
        fournisseur.setPrenom(fournisseurDAO.getPrenom());
        fournisseur.setNumTel(fournisseurDAO.getNumTel());
        fournisseur.setCodePostale(fournisseurDAO.getCodePostale());
        return fournisseur;
    }

}
