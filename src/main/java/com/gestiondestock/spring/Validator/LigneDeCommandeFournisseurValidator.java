package com.gestiondestock.spring.Validator;

import com.gestiondestock.spring.DAO.LigneDeCommandeFournisseurDAO;

import java.util.ArrayList;
import java.util.List;

public class LigneDeCommandeFournisseurValidator {
    public static List<String> validate(LigneDeCommandeFournisseurDAO ligneDeCommandeFournisseurDAO){
        List<String> errors=new ArrayList<>();

        if(ligneDeCommandeFournisseurDAO==null){
            errors.add("le code est un champ obligatoire");
            errors.add("le date de commande est un champ obligatoire");
            return errors;
        }
        if(ligneDeCommandeFournisseurDAO.getQuantite()==null){
            errors.add("la quantité de commande est un champ obligatoire");
        }
        if(ligneDeCommandeFournisseurDAO.getPrixUnitaire()==null){
            errors.add("le prix unitaire de la commande est un champ obligatoire");
        }
        return errors;
    }
}
