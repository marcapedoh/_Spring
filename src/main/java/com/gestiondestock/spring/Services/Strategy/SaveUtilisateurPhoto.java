package com.gestiondestock.spring.Services.Strategy;

import com.flickr4java.flickr.FlickrException;
import com.gestiondestock.spring.DAO.UtilisateurDAO;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidOperationException;
import com.gestiondestock.spring.Services.FlickrService;
import com.gestiondestock.spring.Services.UtilisateurServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
@Service("utilisateurStrategy")
@Slf4j
public class SaveUtilisateurPhoto implements Strategy<UtilisateurDAO>{
    private FlickrService flickrService;
    private UtilisateurServices utilisateurServices;
    @Autowired
    public SaveUtilisateurPhoto(FlickrService flickrService, UtilisateurServices utilisateurServices) {
        this.flickrService = flickrService;
        this.utilisateurServices = utilisateurServices;
    }

    @Override
    public UtilisateurDAO savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        UtilisateurDAO utilisateurDAO=utilisateurServices.findById(id);
        String urlPhoto=flickrService.savePhoto(photo,titre);
        if(!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("erreur lors de l'enregistrement de la photo de l'utilisateur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        utilisateurDAO.setPhoto(urlPhoto);
        return utilisateurServices.save(utilisateurDAO);
    }
}
