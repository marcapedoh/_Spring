package com.gestiondestock.spring.Controller.API;

import com.gestiondestock.spring.DAO.FournisseurDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gestiondestock.spring.Constants.Utils.APP_ROOT;
@Api(APP_ROOT+"/fournisseur")
public interface FournisseurAPI {
    @PostMapping(value = APP_ROOT+"/fournisseur/create", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "enregistrer un fournisseur", notes=" cette methode permet d'enregistrer et modifier un fournisseur",response = FournisseurDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "l'object fournisseur a été bien crée ou modifer")
    })
    FournisseurDAO save(@RequestBody FournisseurDAO fournisseurDAO);
    @GetMapping(value = APP_ROOT+"fournisseur/findById/{idFournisseur}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un fournisseur", notes=" cette methode permet de rechercher un fournisseur par son ID",response =FournisseurDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "le fournisseur a ete trouvé dans la base de donnée"),
            @ApiResponse(code=404,message = "aucune fournisseur n'est trouvé dans la base de donnée")
    })
    FournisseurDAO findById(@PathVariable("idFournisseur") Integer id);
    @GetMapping(value = APP_ROOT+"fournisseur/findByNom{nomClient}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un fournisseur", notes=" cette methode permet de rechercher un fournisseur par son code",response = FournisseurDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "le fournisseur a été trouvé dans la base de donnée"),
            @ApiResponse(code=400,message = "aucun fournisseur n'est trouvé dans la base de donnée")
    })
    FournisseurDAO findByNom(@PathVariable("nomClient") String nom);
    @GetMapping(value = APP_ROOT+"fournisseur/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des fourniseurs", notes=" cette methode permet de rechercher une entreprise avec tous ses attributs",responseContainer = "List<FournisseurDAO>")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "liste des fournisseurs/liste vide")
    })
    List<FournisseurDAO> findAll();
    @DeleteMapping(value = APP_ROOT+"fournisseur/delete/{idFournisseur}")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "supprimer un fournisseur")
    })
    void delete(@PathVariable("idFournisseur") Integer id);
}
