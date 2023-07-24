package com.gestiondestock.spring.Controller.API;

import com.gestiondestock.spring.DAO.ClientDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gestiondestock.spring.Constants.Utils.APP_ROOT;
@Api(APP_ROOT + "/client")
public interface ClientAPI {
    @PostMapping(value = APP_ROOT+"/client/create", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "enregistrer un client", notes=" cette methode permet d'enregistrer et modifier un client",response = ClientDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "l'object client a été bien crée ou modifer")
    })
    ClientDAO save(@RequestBody ClientDAO clientDAO);
    @GetMapping(value = APP_ROOT+"/client/findById/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un client", notes=" cette methode permet de rechercher un client par son ID",response = ClientDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "le client a ete trouvé dans la base de donnée"),
            @ApiResponse(code=404,message = "aucun client n'est trouvé dans la base de donnée")
    })
    ClientDAO findById(@PathVariable("idClient") Integer id);
    @GetMapping(value = APP_ROOT+"/client/findByNom/{nomClient}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un client", notes=" cette methode permet de rechercher un client par son code",response = ClientDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "le client a été trouvé dans la base de donnée"),
            @ApiResponse(code=400,message = "aucun client n'est trouvé dans la base de donnée")
    })
    ClientDAO findByNom(@PathVariable("nomClient") String nom);

    @GetMapping(value = APP_ROOT+"/client/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des clients", notes=" cette methode permet de rechercher un client avec tous ses attributs",responseContainer = "List<ClientDAO>")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "liste des Clients/liste vide")
    })
    List<ClientDAO> findAll();
    @DeleteMapping(value = APP_ROOT+"/client/delete/{idClient}")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "supprimer un client")
    })
    void delete(@PathVariable("idClient") Integer id);
}
