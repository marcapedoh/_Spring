package com.gestiondestock.spring.Validator;

import com.gestiondestock.spring.DAO.FournisseurDAO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FournisseurValidator {
    public static List<String> validate(FournisseurDAO fournisseurDAO){
        List<String> errors=new ArrayList<>();

        if(fournisseurDAO==null){
            errors.add("le nom est un champ obligatoire");
            errors.add("le prenom est un champ obligatoire");
            errors.add("le mail est un champ obligatoire");
            errors.add("le numero de telephone est un champ obligatoire");
            return errors;
        }
        if(!StringUtils.hasLength(fournisseurDAO.getNom())){
            errors.add("le nom est un champ obligatoire");
        }
        if(!StringUtils.hasLength(fournisseurDAO.getPrenom())){
            errors.add("le prenom est un champ obligatoire");
        }
        if(!StringUtils.hasLength(fournisseurDAO.getEmail())){
            errors.add("le mail est un champ obligatoire");
        }
        if(!StringUtils.hasLength(fournisseurDAO.getNumTel())){
            errors.add("le numero de telephone est un champ obligatoire");
        }
        return errors;
    }
}
