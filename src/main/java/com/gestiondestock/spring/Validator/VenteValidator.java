package com.gestiondestock.spring.Validator;

import com.gestiondestock.spring.DAO.VenteDAO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VenteValidator {

    public static List<String> validate(VenteDAO venteDAO){
        List<String> errors=new ArrayList<>();

        if(venteDAO==null){
            errors.add("le code est un champ obligatoire");
            errors.add("le date de commande est un champ obligatoire");
            return errors;
        }
        if(!StringUtils.hasLength(String.valueOf(venteDAO.getId()))){
            errors.add("l'ID de la vente est un champ obligatoire");
        }
        if(!StringUtils.hasLength(String.valueOf(venteDAO.getDateVente()))){
            errors.add("la Date de la commande est un champ obligatoire");
        }
        return errors;
    }
}
