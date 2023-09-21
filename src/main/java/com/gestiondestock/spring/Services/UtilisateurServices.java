package com.gestiondestock.spring.Services;

import com.gestiondestock.spring.DAO.ChangerMotDePasseUtilisateurDAO;
import com.gestiondestock.spring.DAO.UtilisateurDAO;

import java.util.List;

public interface UtilisateurServices {
    UtilisateurDAO save(UtilisateurDAO utilisateurDAO);
    UtilisateurDAO findById(Integer id);
    UtilisateurDAO findByNom(String nom);
    UtilisateurDAO findByMailAndMotDePasse(String mail,String password);
    UtilisateurDAO findByMail(String mail);
    List<UtilisateurDAO> findAll();
    void delete(Integer id);
    UtilisateurDAO changerMotDePasse(ChangerMotDePasseUtilisateurDAO dto);
    void desactiverUser(Integer id);
    void activerUser(Integer id);
}
