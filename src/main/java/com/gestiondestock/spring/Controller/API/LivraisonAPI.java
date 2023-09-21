package com.gestiondestock.spring.Controller.API;

import com.gestiondestock.spring.DAO.ArticleDAO;
import com.gestiondestock.spring.DAO.LivraisonDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static com.gestiondestock.spring.Constants.Utils.APP_ROOT;
@Api(value = APP_ROOT+"/livraison")
public interface LivraisonAPI {
    @PostMapping(value = APP_ROOT + "/livraison/create", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "enregistrer une Livraison", notes=" cette methode permet d'enregistrer et modifier un Livraison",response = LivraisonDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "l'object Livraison a ete bien crée ou modifer")
    })
    LivraisonDAO save(@RequestBody LivraisonDAO livraisonDAO);
    @GetMapping(value = APP_ROOT+"/livraison/findByCode/{code}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une livraison", notes=" cette methode permet de rechercher unr livraison par son ID",response = LivraisonDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "la livraison a ete trouvé dans la base de donnée"),
            @ApiResponse(code=404,message = "aucune livraison n'est trouvé dans la base de donnée")
    })
    LivraisonDAO findByCode(@PathVariable("code") String code);
    @GetMapping(value = APP_ROOT+"/livraison/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des article", notes=" cette methode permet de rechercher un article avec tous ses attributs",responseContainer = "List<LivraisonDAO>")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "liste des Livraisons/liste vide")
    })
    List<LivraisonDAO> findAll();

    @DeleteMapping(value = APP_ROOT+"/livraison/delete/{idLivraison}")
    void delete(@PathVariable("idLivraison") Integer idLivraison);
}
