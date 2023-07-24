package com.gestiondestock.spring.Controller;

import com.gestiondestock.spring.Controller.API.CommandeClientAPI;
import com.gestiondestock.spring.DAO.CommandeClientDAO;
import com.gestiondestock.spring.DAO.LigneDeCommandeClientDAO;
import com.gestiondestock.spring.Services.CommandeClientServices;
import com.gestiondestock.spring.models.EtatCommande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
@RestController
@CrossOrigin(origins = "*")
public class CommandeClientController implements CommandeClientAPI {
    private CommandeClientServices commandeClientServices;

    @Autowired
    public CommandeClientController(CommandeClientServices commandeClientServices){
        this.commandeClientServices=commandeClientServices;
    }
    @Override
    public CommandeClientDAO save(CommandeClientDAO commandeClientDAO) {
        return commandeClientServices.save(commandeClientDAO);
    }

    @Override
    public ResponseEntity<CommandeClientDAO> updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        return ResponseEntity.ok(commandeClientServices.updateEtatCommande(idCommande, etatCommande));
    }

    @Override
    public ResponseEntity<CommandeClientDAO> updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        return ResponseEntity.ok(commandeClientServices.updateQuantiteCommande(idCommande, idLigneCommande, quantite));
    }

    @Override
    public ResponseEntity<CommandeClientDAO> updateClient(Integer idCommande, Integer idClient) {
        return ResponseEntity.ok(commandeClientServices.updateClient(idCommande, idClient));
    }

    @Override
    public ResponseEntity<CommandeClientDAO> updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        return ResponseEntity.ok(commandeClientServices.updateArticle(idCommande, idLigneCommande, idArticle));
    }

    @Override
    public ResponseEntity<CommandeClientDAO> deleteArticle(Integer idCommande, Integer idLigneCommande) {
        return ResponseEntity.ok(commandeClientServices.deleteArticle(idCommande, idLigneCommande));
    }

    @Override
    public CommandeClientDAO findById(Integer id) {
        return commandeClientServices.findById(id);
    }

    @Override
    public CommandeClientDAO findByCode(String code) {
        return commandeClientServices.findByCode(code);
    }

    @Override
    public List<CommandeClientDAO> findAll() {
        return commandeClientServices.findAll();
    }

    @Override
    public void delete(Integer id) {
        commandeClientServices.delete(id);
    }

    @Override
    public ResponseEntity<List<LigneDeCommandeClientDAO>> findAllLignesCommandesClientByCommandeClientId(Integer idCommande) {
        return ResponseEntity.ok(commandeClientServices.findAllLignesCommandesClientByCommandeClientId(idCommande));
    }
}
