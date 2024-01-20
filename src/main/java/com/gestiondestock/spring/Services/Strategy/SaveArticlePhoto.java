package com.gestiondestock.spring.Services.Strategy;

import com.flickr4java.flickr.FlickrException;
import java.io.InputStream;

import com.gestiondestock.spring.DAO.ArticleDAO;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidOperationException;
import com.gestiondestock.spring.Services.ArticleServices;
import com.gestiondestock.spring.Services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("articleStrategy")
@Slf4j
public class SaveArticlePhoto implements Strategy<ArticleDAO> {

    private FlickrService flickrService;
    private ArticleServices articleService;

    @Autowired
    public SaveArticlePhoto(FlickrService flickrService, ArticleServices articleService) {
        this.flickrService = flickrService;
        this.articleService = articleService;
    }

    @Override
    public ArticleDAO savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        ArticleDAO article = articleService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de photo de l'article", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        article.setPhoto(urlPhoto);
        return articleService.save(article);
    }
}