package com.gestiondestock.spring.Validator;

import com.gestiondestock.spring.DAO.CommandeFournisseurDAO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeFournisseurValidator {
    public static List<String> validate(CommandeFournisseurDAO FournisseurDAO){
        List<String> errors=new ArrayList<>();

        if(FournisseurDAO==null){
            errors.add("le code est un champ obligatoire");
            errors.add("le date de commande est un champ obligatoire");
            return errors;
        }
        if(!StringUtils.hasLength(FournisseurDAO.getCode())){
            errors.add("le code est un champ obligatoire");
        }
        if(FournisseurDAO.getDateCommande()==null){
            errors.add("le date de commande est un champ obligatoire");
        }
        return errors;
    }
}
