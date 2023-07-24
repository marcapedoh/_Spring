package com.gestiondestock.spring.Validator;

import com.gestiondestock.spring.DAO.LigneVenteDAO;

import java.util.ArrayList;
import java.util.List;

public class LigneVenteValidator {
    public static List<String> validate(LigneVenteDAO ligneVenteDAO){
        List<String> errors=new ArrayList<>();

        if(ligneVenteDAO==null){
            errors.add("la quantité de commande est un champ obligatoire");
            errors.add("le prix unitaire de la commande est un champ obligatoire");
            return errors;
        }
        if(ligneVenteDAO.getQuantite()==null){
            errors.add("la quantité de commande est un champ obligatoire");
        }
        if(ligneVenteDAO.getPrixUnitaire()==null){
            errors.add("le prix unitaire de la commande est un champ obligatoire");
        }
        return errors;
    }
}
