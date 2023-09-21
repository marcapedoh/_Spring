package com.gestiondestock.spring.Services;

import com.gestiondestock.spring.DAO.AutoCompleteDAO;
import com.gestiondestock.spring.Repository.AutoCompleteReposirory;
import com.gestiondestock.spring.models.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AutoCompleteService {

    private AutoCompleteReposirory autoCompleteReposirory;
    @Autowired
    public AutoCompleteService(AutoCompleteReposirory autoCompleteReposirory) {
        this.autoCompleteReposirory = autoCompleteReposirory;
    }
    public List<AutoCompleteDAO> getAllDataForAutoComplete(String codeArticle){
        List<Article> articleList=autoCompleteReposirory.findAllByCodeArticle(codeArticle);
        List<AutoCompleteDAO>  autoCompleteDAOList=new ArrayList<>();
        for(Article autoComplete: articleList){
            autoCompleteDAOList.add(new AutoCompleteDAO(autoComplete.getCodeArticle(),autoComplete.getDesignation()));
        }
        return autoCompleteDAOList;
    }
}
