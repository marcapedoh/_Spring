package com.gestiondestock.spring.Services.Strategy;

import com.flickr4java.flickr.FlickrException;
import com.gestiondestock.spring.DAO.FournisseurDAO;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidOperationException;
import com.gestiondestock.spring.Services.FlickrService;
import com.gestiondestock.spring.Services.FournisseurServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
@Service("fournisseurStrategy")
@Slf4j
public class SaveFournisseurPhoto implements Strategy<FournisseurDAO>{
    private FlickrService flickrService;
    private FournisseurServices fournisseurServices;
    @Autowired
    public SaveFournisseurPhoto(FlickrService flickrService, FournisseurServices fournisseurServices) {
        this.flickrService = flickrService;
        this.fournisseurServices = fournisseurServices;
    }

    @Override
    public FournisseurDAO savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        FournisseurDAO fournisseurDAO=fournisseurServices.findById(id);
        String urlPhoto=flickrService.savePhoto(photo,titre);
        if(!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("erreur lors de l'enregistrement de la photo du fournisseur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        fournisseurDAO.setPhoto(urlPhoto);
        return fournisseurServices.save(fournisseurDAO);
    }
}
