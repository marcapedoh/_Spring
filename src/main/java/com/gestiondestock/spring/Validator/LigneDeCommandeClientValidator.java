package com.gestiondestock.spring.Validator;

import com.gestiondestock.spring.DAO.LigneDeCommandeClientDAO;

import java.util.ArrayList;
import java.util.List;

public class LigneDeCommandeClientValidator {
    public static List<String> validate(LigneDeCommandeClientDAO ligneDeCommandeClientDAO){
        List<String> errors=new ArrayList<>();

        if(ligneDeCommandeClientDAO==null){
            errors.add("le code est un champ obligatoire");
            errors.add("le date de commande est un champ obligatoire");
            return errors;
        }
        if(ligneDeCommandeClientDAO.getQuantite()==null){
            errors.add("la quantité de commande est un champ obligatoire");
        }
        if(ligneDeCommandeClientDAO.getPrixUnitaire()==null){
            errors.add("le prix unitaire de la commande est un champ obligatoire");
        }
        return errors;
    }
}
