package com.gestiondestock.spring.Services.Strategy;

import com.flickr4java.flickr.FlickrException;
import com.gestiondestock.spring.DAO.ClientDAO;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidOperationException;
import com.gestiondestock.spring.Services.ClientServices;
import com.gestiondestock.spring.Services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
@Service("clientStrategy")
@Slf4j
public class SaveClientPhoto implements Strategy<ClientDAO>{
    private FlickrService flickrService;
    private ClientServices clientServices;

    @Autowired
    public SaveClientPhoto(FlickrService flickrService, ClientServices clientServices) {
        this.flickrService = flickrService;
        this.clientServices = clientServices;
    }
    @Override
    public ClientDAO savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        ClientDAO clientDAO=clientServices.findById(id);
        String urlPhoto=flickrService.savePhoto(photo,titre);
        if(!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("erreur lors de l'enregistrement de la photo du client", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        clientDAO.setPhoto(urlPhoto);
        return clientServices.save(clientDAO);
    }
}
