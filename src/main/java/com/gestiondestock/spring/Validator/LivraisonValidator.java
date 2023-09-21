package com.gestiondestock.spring.Validator;

import com.gestiondestock.spring.DAO.LivraisonDAO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class LivraisonValidator {

    public static List<String> validate(LivraisonDAO livraisonDAO){
        List<String> errors=new ArrayList<>();

        if(livraisonDAO==null){
            errors.add("tu ne peux pas passer un object presque vide pour l'enregistrement");
            errors.add("le code de la livraison ne peut pas etre null");
            errors.add("la date de la livraison ne peut pas etre null");
            errors.add("le type de livraison ne doit pas etre null");
        }
        if(livraisonDAO.getId()==null){
            errors.add("tu ne peux pas passer un object presque vide pour l'enregistrement");
        }
        if(!StringUtils.hasLength(livraisonDAO.getCode())){
            errors.add("le code de la livraison ne peut pas etre null");
        }
        return errors;
    }
}
