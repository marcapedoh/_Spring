package com.gestiondestock.spring.Controller.API;

import com.gestiondestock.spring.DAO.CommandeFournisseurDAO;
import com.gestiondestock.spring.DAO.LigneDeCommandeFournisseurDAO;
import com.gestiondestock.spring.models.EtatCommande;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.gestiondestock.spring.Constants.Utils.APP_ROOT;
import static com.gestiondestock.spring.Constants.Utils.COMMANDE_FOURNISSEUR_ENDPOINT;
@Api(APP_ROOT+"/commanedeFournisseur")
public interface CommandeFournisseurAPI {
    @PostMapping(value = APP_ROOT+"/commandeFournisseur/create", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "enregistrer une commande fournisseur", notes=" cette methode permet d'enregistrer et modifier une commande fournisseur",response = CommandeFournisseurDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "l'object commande fournisseur a été bien crée ou modifer")
    })
    CommandeFournisseurDAO save(@RequestBody CommandeFournisseurDAO commandeFournisseurDAO);
    @PatchMapping(APP_ROOT + "/commanedeFournisseur/update/etat/{idCommande}/{etatCommande}")
    CommandeFournisseurDAO updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(APP_ROOT + "/commanedeFournisseur/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    CommandeFournisseurDAO updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande,
                                                  @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping(APP_ROOT + "/commanedeFournisseur/update/fournisseur/{idCommande}/{idFournisseur}")
    CommandeFournisseurDAO updateFournisseur(@PathVariable("idCommande") Integer idCommande, @PathVariable("idFournisseur") Integer idFournisseur);

    @PatchMapping(APP_ROOT + "/commanedeFournisseur/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    CommandeFournisseurDAO updateArticle(@PathVariable("idCommande") Integer idCommande,
                                         @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("idArticle") Integer idArticle);

    @DeleteMapping(APP_ROOT + "/commanedeFournisseur/delete/article/{idCommande}/{idLigneCommande}")
    CommandeFournisseurDAO deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande);
    @GetMapping(value = APP_ROOT+"/commandeFournisseur/{idCommandeFournisseur}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une commande fournisseur", notes=" cette methode permet de rechercher une commande fournisseur par son ID",response =CommandeFournisseurDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "la commande fournisseur a ete trouvé dans la base de donnée"),
            @ApiResponse(code=404,message = "aucune commande fournisseur n'est trouvé dans la base de donnée")
    })
    CommandeFournisseurDAO findById(@PathVariable("idCommandeFournisseur") Integer id);
    @GetMapping(value = APP_ROOT+"/commandeFournisseur/{codeCommandeFournisseur}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une commande fournisseur", notes=" cette methode permet de rechercher une commande fournisseur par son code",response = CommandeFournisseurDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "la commande fournisseur a été trouvé dans la base de donnée"),
            @ApiResponse(code=400,message = "aucune commande fournisseur n'est trouvé dans la base de donnée")
    })
    CommandeFournisseurDAO findByCode(@PathVariable("codeCommandeFournisseur") String code);
    @GetMapping(value = APP_ROOT+"/commandeFournisseur/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des commandes fournisseur", notes=" cette methode permet de rechercher une commande client avec tous ses attributs",responseContainer = "List<CommandeFournisseurDAO>")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "liste des commandes fournisseurs/liste vide")
    })
    List<CommandeFournisseurDAO> findAll();

    @GetMapping(APP_ROOT + "/commanedeFournisseur/lignesCommande/{idCommande}")
    List<LigneDeCommandeFournisseurDAO> findAllLignesCommandesFournisseurByCommandeFournisseurId(@PathVariable("idCommande") Integer idCommande);
    @DeleteMapping(value = APP_ROOT+"/commandeFournisseur/delete/{idCommandeFournisseur}")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "supprimer une commande fournisseur")
    })
    void delete(@PathVariable("idCommandeFournisseur") Integer id);
}
