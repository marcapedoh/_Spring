package com.gestiondestock.spring.Controller;

import com.gestiondestock.spring.Controller.API.UtilisateurAPI;
import com.gestiondestock.spring.DAO.ChangerMotDePasseUtilisateurDAO;
import com.gestiondestock.spring.DAO.UtilisateurDAO;
import com.gestiondestock.spring.Repository.UtilisateurRepository;
import com.gestiondestock.spring.Services.UtilisateurServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UtilisateurController implements UtilisateurAPI {

    private UtilisateurServices utilisateurServices;

    @Autowired
    public UtilisateurController(UtilisateurServices utilisateurServices){
        this.utilisateurServices=utilisateurServices;
    }
    @Override
    public UtilisateurDAO save(UtilisateurDAO utilisateurDAO) {
        return utilisateurServices.save(utilisateurDAO);
    }

    @Override
    public UtilisateurDAO findById(Integer id) {
        return utilisateurServices.findById(id);
    }

    @Override
    public UtilisateurDAO findByNom(String nom) {
        return utilisateurServices.findByNom(nom);
    }

    @Override
    public UtilisateurDAO changerMotDePasse(ChangerMotDePasseUtilisateurDAO dto) {
        return utilisateurServices.changerMotDePasse(dto);
    }

    @Override
    public UtilisateurDAO findByMailAndMotDePasse(String mail, String password) {
        return utilisateurServices.findByMailAndMotDePasse(mail,password);
    }

    @Override
    public UtilisateurDAO findByMail(String mail) {
        return utilisateurServices.findByMail(mail);
    }

    @Override
    public List<UtilisateurDAO> findAll() {
        return utilisateurServices.findAll();
    }

    @Override
    public void delete(Integer id) {
        utilisateurServices.delete(id);
    }

    @Override
    public void desactiverUser(Integer id) {
        utilisateurServices.desactiverUser(id);
    }

    @Override
    public void activerUser(Integer id) {
        this.utilisateurServices.activerUser(id);
    }
}
