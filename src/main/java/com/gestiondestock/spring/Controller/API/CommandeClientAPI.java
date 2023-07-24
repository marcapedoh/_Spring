package com.gestiondestock.spring.Controller.API;

import com.gestiondestock.spring.DAO.CommandeClientDAO;
import com.gestiondestock.spring.DAO.LigneDeCommandeClientDAO;
import com.gestiondestock.spring.models.EtatCommande;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.gestiondestock.spring.Constants.Utils.APP_ROOT;
@Api(APP_ROOT + "/commandeClient")
public interface CommandeClientAPI {
    @PostMapping(value = APP_ROOT+"/commandeClient/create", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "enregistrer une commande client", notes=" cette methode permet d'enregistrer et modifier une commande client",response = CommandeClientDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "l'object commande client a été bien crée ou modifer")
    })
    CommandeClientDAO save(@RequestBody CommandeClientDAO commandeClientDAO);
    @PatchMapping(APP_ROOT + "/commandesclients/update/etat/{idCommande}/{etatCommande}")
    ResponseEntity<CommandeClientDAO> updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(APP_ROOT + "/commandesclients/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    ResponseEntity<CommandeClientDAO> updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande,
                                                             @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping(APP_ROOT + "/commandesclients/update/client/{idCommande}/{idClient}")
    ResponseEntity<CommandeClientDAO> updateClient(@PathVariable("idCommande") Integer idCommande, @PathVariable("idClient") Integer idClient);

    @PatchMapping(APP_ROOT + "/commandesclients/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    ResponseEntity<CommandeClientDAO> updateArticle(@PathVariable("idCommande") Integer idCommande,
                                                    @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("idArticle") Integer idArticle);

    @DeleteMapping(APP_ROOT + "/commandesclients/delete/article/{idCommande}/{idLigneCommande}")
    ResponseEntity<CommandeClientDAO> deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande);
    @GetMapping(value = APP_ROOT+"/commandeClient/{idCommandeClient}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une commande client", notes=" cette methode permet de rechercher une commande client par son ID",response =CommandeClientDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "la commande client a ete trouvé dans la base de donnée"),
            @ApiResponse(code=404,message = "aucune commande client n'est trouvé dans la base de donnée")
    })
    CommandeClientDAO findById(@PathVariable("idCommandeClient") Integer id);
    @GetMapping(value = APP_ROOT+"/commandeClient/{codeCommandeClient}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une commande client", notes=" cette methode permet de rechercher une commande client par son code",response = CommandeClientDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "la commande client a été trouvé dans la base de donnée"),
            @ApiResponse(code=400,message = "aucune commande client n'est trouvé dans la base de donnée")
    })
    CommandeClientDAO findByCode(@PathVariable("codeCommandeClient") String code);
    @GetMapping(value = APP_ROOT+"/commandeClient/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des commandes clients", notes=" cette methode permet de rechercher une commande client avec tous ses attributs",responseContainer = "List<CommandeClientDAO>")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "liste des commandes Client/liste vide")
    })
    List<CommandeClientDAO> findAll();
    @DeleteMapping(value = APP_ROOT+"/commandeClient/delete/{idCommandeClient}")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "supprimer une commande client")
    })
    void delete(@PathVariable("idCommandeClient") Integer id);
    @GetMapping(APP_ROOT + "/commandesclients/lignesCommande/{idCommande}")
    ResponseEntity<List<LigneDeCommandeClientDAO>> findAllLignesCommandesClientByCommandeClientId(@PathVariable("idCommande") Integer idCommande);
}
