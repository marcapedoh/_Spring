package com.gestiondestock.spring.DAO;

import com.gestiondestock.spring.models.ERoles;
import com.gestiondestock.spring.models.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDAO {
    private Integer id;
    private String nom;
    private String prenom;
    private String mail;
    private String motDePasse;
    private String photo;
    private String ville;
    private String codePostale;
    private boolean active;
    private String pays;
    private ERoles roles;

    public static UtilisateurDAO fromEntity(Utilisateur utilisateur){
        if(utilisateur==null){
            return null;
        }
        return UtilisateurDAO.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .mail(utilisateur.getMail())
                .motDePasse(utilisateur.getMotDePasse())
                .photo(utilisateur.getPhoto())
                .ville(utilisateur.getVille())
                .active(utilisateur.isActive())
                .roles(utilisateur.getRoles())
                .codePostale(utilisateur.getCodePostale())
                .pays(utilisateur.getPays())
                .build();
    }

    public static Utilisateur toEntity(UtilisateurDAO utilisateurDAO){
        if(utilisateurDAO==null){
            return null;
        }
        return Utilisateur.builder()
                .nom(utilisateurDAO.getNom())
                .prenom(utilisateurDAO.getPrenom())
                .mail(utilisateurDAO.getMail())
                .motDePasse(utilisateurDAO.getMotDePasse())
                .photo(utilisateurDAO.getPhoto())
                .ville(utilisateurDAO.getVille())
                .active(utilisateurDAO.active)
                .roles(utilisateurDAO.getRoles())
                .codePostale(utilisateurDAO.getCodePostale())
                .pays(utilisateurDAO.getPays())
                .build();

    }
}
