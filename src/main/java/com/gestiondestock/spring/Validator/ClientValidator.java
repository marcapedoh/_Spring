package com.gestiondestock.spring.Validator;

import com.gestiondestock.spring.DAO.ClientDAO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    public static List<String> validate(ClientDAO clientDAO){
        List<String> errors=new ArrayList<>();

        if(clientDAO==null){
            errors.add("le nom est un champ obligatoire");
            errors.add("le prenom est un champ obligatoire");
            errors.add("le mail est un champ obligatoire");
            errors.add("le numero de telephone est un champ obligatoire");
            return errors;
        }
        if(!StringUtils.hasLength(clientDAO.getNom())){
            errors.add("le nom est un champ obligatoire");
        }
        if(!StringUtils.hasLength(clientDAO.getPrenom())){
            errors.add("le prenom est un champ obligatoire");
        }
        if(!StringUtils.hasLength(clientDAO.getEmail())){
            errors.add("le mail est un champ obligatoire");
        }
        if(!StringUtils.hasLength(clientDAO.getNumTel())){
            errors.add("le numero de telephone est un champ obligatoire");
        }
        return errors;
    }
}
