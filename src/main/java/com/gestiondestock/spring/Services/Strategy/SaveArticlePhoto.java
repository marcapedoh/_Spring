package com.gestiondestock.spring.Services.Strategy;

import com.flickr4java.flickr.FlickrException;
import com.gestiondestock.spring.DAO.ArticleDAO;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidOperationException;
import com.gestiondestock.spring.Services.ArticleServices;
import com.gestiondestock.spring.Services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
@Service("articleStrategy")
@Slf4j
public class SaveArticlePhoto implements Strategy<ArticleDAO>{
    private FlickrService flickrService;
    private ArticleServices articleServices;
    @Autowired
    public SaveArticlePhoto(FlickrService flickrService, ArticleServices articleServices) {
        this.flickrService = flickrService;
        this.articleServices = articleServices;
    }
    @Override
    public ArticleDAO savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        ArticleDAO articleDAO=articleServices.findById(id);
        String urlPhoto= flickrService.savePhoto(photo,titre);
        if(!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("erreur lors de l'enregistrement de la photo de l'article", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        articleDAO.setPhoto(urlPhoto);
        return articleServices.save(articleDAO);
    }
}
