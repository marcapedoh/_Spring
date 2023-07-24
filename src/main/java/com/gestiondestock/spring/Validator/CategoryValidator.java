package com.gestiondestock.spring.Validator;

import com.gestiondestock.spring.DAO.CategoryDAO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryValidator {
    public static List<String> validate(CategoryDAO categoryDAO){
        List<String>errors=new ArrayList<>();

        if(categoryDAO==null ){
            errors.add("Veuillez saisir le code de la categorie!");
        }
        assert categoryDAO != null;
        if(!StringUtils.hasLength(categoryDAO.getCode())){
            errors.add("Veuillez saisir le code de la categorie!");
        }
        return errors;
    }
}
