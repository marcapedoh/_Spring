package com.gestiondestock.spring.Validator;

import com.gestiondestock.spring.DAO.MvtStkDAO;

import java.util.ArrayList;
import java.util.List;

public class MvtStkValidator {
    public static List<String> validate(MvtStkDAO mvtStkDAO){
        List<String> errors=new ArrayList<>();

        if(mvtStkDAO==null){
            errors.add("la quantité de commande est un champ obligatoire");
            errors.add("la date de mise en stock est un champ obligatoire");
            return errors;
        }
        if(mvtStkDAO.getQuantite()==null){
            errors.add("la quantité de commande est un champ obligatoire");
        }
        if(mvtStkDAO.getDateMvt()==null){
            errors.add("la date de mise en stock est un champ obligatoire");
        }
        return errors;
    }
}
