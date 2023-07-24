package com.gestiondestock.spring.Controller;

import com.gestiondestock.spring.Controller.API.CommandeFournisseurAPI;
import com.gestiondestock.spring.DAO.CommandeFournisseurDAO;
import com.gestiondestock.spring.DAO.LigneDeCommandeFournisseurDAO;
import com.gestiondestock.spring.Services.CommandeFournisseurServices;
import com.gestiondestock.spring.models.EtatCommande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CommandeFournisseurController implements CommandeFournisseurAPI {

    private CommandeFournisseurServices commandeFournisseurServices;
    @Autowired
    public CommandeFournisseurController(CommandeFournisseurServices commandeFournisseurServices){
        this.commandeFournisseurServices=commandeFournisseurServices;
    }

    @Override
    public CommandeFournisseurDAO save(CommandeFournisseurDAO commandeFournisseurDAO) {
       return commandeFournisseurServices.save(commandeFournisseurDAO);
    }

    @Override
    public CommandeFournisseurDAO updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        return commandeFournisseurServices.updateEtatCommande(idCommande, etatCommande);
    }

    @Override
    public CommandeFournisseurDAO updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        return commandeFournisseurServices.updateQuantiteCommande(idCommande, idLigneCommande, quantite);
    }

    @Override
    public CommandeFournisseurDAO updateFournisseur(Integer idCommande, Integer idFournisseur) {
        return commandeFournisseurServices.updateFournisseur(idCommande, idFournisseur);
    }

    @Override
    public CommandeFournisseurDAO updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        return commandeFournisseurServices.updateArticle(idCommande, idLigneCommande, idArticle);
    }

    @Override
    public CommandeFournisseurDAO deleteArticle(Integer idCommande, Integer idLigneCommande) {
        return commandeFournisseurServices.deleteArticle(idCommande, idLigneCommande);
    }

    @Override
    public CommandeFournisseurDAO findById(Integer id) {
        return commandeFournisseurServices.findById(id);
    }

    @Override
    public CommandeFournisseurDAO findByCode(String code) {
        return commandeFournisseurServices.findByCode(code);
    }

    @Override
    public List<CommandeFournisseurDAO> findAll() {
        return commandeFournisseurServices.findAll();
    }

    @Override
    public List<LigneDeCommandeFournisseurDAO> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande) {
        return commandeFournisseurServices.findAllLignesCommandesFournisseurByCommandeFournisseurId(idCommande);
    }

    @Override
    public void delete(Integer id) {
        commandeFournisseurServices.delete(id);
    }
}
