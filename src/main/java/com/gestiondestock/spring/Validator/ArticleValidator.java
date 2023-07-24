package com.gestiondestock.spring.Validator;

import com.gestiondestock.spring.DAO.ArticleDAO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ArticleValidator {
    public static List<String> validate(ArticleDAO articleDAO){
        List<String> errors=new ArrayList<>();

        if(articleDAO==null){
            errors.add("entrez le code de l'article");
            errors.add("champ designation obligatoire");
            errors.add("champ prix taxe hors obligatoire");
            errors.add("champs TVA obligatoire");
            errors.add("le prix ne peux pas être null");
            errors.add("veuillez selectionner une categorie");
            return errors;
        }

        if(!StringUtils.hasLength(articleDAO.getCodeArticle())){
            errors.add("entrez le code de l'article");
        }
        if(!StringUtils.hasLength(articleDAO.getDesignation())){
            errors.add("champ designation obligatoire");
        }
        if(articleDAO.getPrixUnitaireHT()==null){
            errors.add("champ prix taxe hors obligatoire");
        }
        if(articleDAO.getTauxTVA()==null){
            errors.add("champs TVA obligatoire");
        }
        if(articleDAO.getPrixUnitaireTTC()==null){
            errors.add("le prix ne peux pas être null");
        }
        if(articleDAO.getCategory()==null){
            errors.add("veuillez selectionner une categorie");
        }
        return errors;
    }
}
