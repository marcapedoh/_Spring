package com.gestiondestock.spring.Controller.API;

import com.gestiondestock.spring.DAO.MvtStkDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.gestiondestock.spring.Constants.Utils.APP_ROOT;

@Api(APP_ROOT+"/MvtStk")
public interface MvtStkAPI {
    @GetMapping(value = APP_ROOT+"/MvtStk/stockReelArticle/{idArticle}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi le stock reel pour cet article", notes=" cette methode permet de rechercher le chercher le stock reel pour un article",response = BigDecimal.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "stock reel de l'article")
    })
    BigDecimal stockReelArticle(@PathVariable("idArticle") Integer idArticle);
    @GetMapping(value = APP_ROOT+"/MvtStk/mvtStkArticle/{idArticle}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi tous les mouvements de stock", notes=" cette methode retourne tous les mouvements du stock",responseContainer = "List<MvtStkDAO>")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "liste des mouvements trouvé")
    })
    List<MvtStkDAO> mvtStkArticle(@PathVariable("idArticle") Integer id);
    @PostMapping(value = APP_ROOT+"/MvtStk/entreeStock/create",consumes =MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "cette methode de créer un nouvement de stock", notes=" cette methode permet de creer un mouvement de stock", response = MvtStkDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "mouvement de stock entree crée avec succès")
    })
    MvtStkDAO entreeStock(@RequestBody MvtStkDAO mvtStkDAO);
    @PostMapping(value = APP_ROOT+"/sortieStock/create",consumes =MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "cette methode permet de créer un mouvement de stock", notes=" cette methode créer mouvement du stock de sortie", response = MvtStkDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "liste des mouvements trouvé")
    })
    MvtStkDAO sortieStock(@RequestBody MvtStkDAO mvtStkDAO);
    @PutMapping(value = APP_ROOT+"/correctionStock/entree/create" ,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "cette methode permet de modifier un mouvement de stock d'entré", notes=" cette methode permet de modifier mouvement du stock de sortie", response = MvtStkDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "mouvement de stock modifier ")
    })
    MvtStkDAO correctionStockPos(@RequestBody MvtStkDAO mvtStkDAO);
    @GetMapping(value = APP_ROOT+"/MvtStk/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des mouvement", notes=" cette methode retourne tous les mouvement des stocks avec tous ses attributs",responseContainer = "List<MvtStkDAO>")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "liste des Mouvements/liste vide")
    })
    List<MvtStkDAO> findAll();
    @PutMapping(value = APP_ROOT+"/correctionStock/sortie/create" ,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "cette methode permet de créer un mouvement de stock", notes="cette methode permet de modifier mouvement du stock de sortie", response = MvtStkDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "mouvement de stock modifier ")
    })
    MvtStkDAO correctionStockNeg(@RequestBody MvtStkDAO mvtStkDAO);
}
