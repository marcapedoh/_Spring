package com.gestiondestock.spring.Repository;

import com.gestiondestock.spring.DAO.AutoCompleteDAO;
import com.gestiondestock.spring.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutoCompleteReposirory extends JpaRepository<Article,Integer> {
    List<Article> findAllByCodeArticle(String codeArticle);
}
