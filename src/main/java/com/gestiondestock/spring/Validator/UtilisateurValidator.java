package com.gestiondestock.spring.Validator;

import com.gestiondestock.spring.DAO.UtilisateurDAO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurValidator {
    public static List<String> validate(UtilisateurDAO utilisateurDAO){
        List<String>errors=new ArrayList<>();

        if(utilisateurDAO==null){
            errors.add("Vous devez ajouter un nom!");
            errors.add("Vous devez ajouter un prenom !");
            errors.add("Vous devez ajouter un mail!");
            errors.add("Vous devez ajouter un Mot de passe!");
            errors.add("Vous devez ajouter un adresse à l'utilisateur !");
            errors.add("Vous devez ajouter la premiere adresse !");
            errors.add("Vous devez ajouter le champs de la ville c'est obligatoire !");
            errors.add("Vous devez ajouter un code postale !");
            errors.add("Vous devez ajouter un utilisateur!");
            return errors;
        }
        if(!StringUtils.hasLength(utilisateurDAO.getNom())){
            errors.add("Vous devez ajouter un nom!");
        }
        if(!StringUtils.hasLength(utilisateurDAO.getPrenom())){
            errors.add("Vous devez ajouter un prenom !");
        }
        if(!StringUtils.hasLength(utilisateurDAO.getMail())){
            errors.add("Vous devez ajouter un mail!");
        }
        if(!StringUtils.hasLength(utilisateurDAO.getMotDePasse())){
            errors.add("Vous devez ajouter un Mot de passe!");
        }
        if(utilisateurDAO.getVille() ==null ){
            errors.add("Vous devez ajouter un Ville à l'utilisateur !");
        }else{
            if(!StringUtils.hasLength(utilisateurDAO.getPays())){
                errors.add("Vous devez ajouter votre pays d'origine !");
            }
        }
        return errors;
    }
}
