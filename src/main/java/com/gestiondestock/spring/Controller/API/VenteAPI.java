package com.gestiondestock.spring.Controller.API;

import com.gestiondestock.spring.DAO.VenteDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gestiondestock.spring.Constants.Utils.APP_ROOT;

@Api(APP_ROOT+"/vente")
public interface VenteAPI {
    @PostMapping(value = APP_ROOT+"/vente/create", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "enregistrer une vente", notes=" cette methode permet d'enregistrer et modifier une vente", response = VenteDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "l'object vente a été bien crée ou modifer")
    })
    VenteDAO save(@RequestBody VenteDAO venteDAO);
    @GetMapping(value = APP_ROOT+"/vente/findById/{idVente}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une vente", notes=" cette methode permet de rechercher une vente par son ID", response = VenteDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "la vente a ete trouvé dans la base de donnée"),
            @ApiResponse(code=404,message = "aucune vente n'est trouvé dans la base de donnée")
    })
    VenteDAO findById(@PathVariable("idVente") Integer id);
    @GetMapping(value = APP_ROOT+"/vente/findByCode/{codeVente}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une vente", notes=" cette methode permet de rechercher une vente par son nom",response = VenteDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "la vente a été trouvé dans la base de donnée"),
            @ApiResponse(code=400,message = "aucune vente n'est trouvé dans la base de donnée")
    })
    VenteDAO findByCode(@PathVariable("codeVente") String code);
    @GetMapping(value = APP_ROOT+"/vente/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des ventes", notes=" cette methode permet de rechercher une vente avec tous ses attributs",responseContainer = "List<VenteDAO>")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "liste des ventes/liste vide")
    })
    List<VenteDAO> findAll();
    @DeleteMapping(value = APP_ROOT+"/vente/delete/{idVente}")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "supprimer une vente")
    })
    void delete(@PathVariable("idVente") Integer id);
}
