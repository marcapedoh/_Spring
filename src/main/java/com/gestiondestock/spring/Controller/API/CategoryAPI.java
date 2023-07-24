package com.gestiondestock.spring.Controller.API;

import com.gestiondestock.spring.DAO.CategoryDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gestiondestock.spring.Constants.Utils.APP_ROOT;
@Api(APP_ROOT + "/category")
public interface CategoryAPI {
    @GetMapping(value = APP_ROOT+"/category/findById/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une category", notes=" cette methode permet de rechercher une category par son ID", response = CategoryDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "la category a ete trouvé dans la base de donnée"),
            @ApiResponse(code=404,message = "aucune category n'est trouvé dans la base de donnée")
    })
    CategoryDAO findById(@PathVariable("idCategory") Integer id);
    @PostMapping(value = APP_ROOT+"/category/create", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "enregistrer une category", notes=" cette methode permet d'enregistrer et modifier un article",response = CategoryDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "l'object category a ete bien crée ou modifer")
    })
    CategoryDAO save(@RequestBody CategoryDAO categoryDAO);
    @GetMapping(value = APP_ROOT+"/category/findByCode/{codeCategory}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une category", notes=" cette methode permet de rechercher une category par son code",response = CategoryDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "la categorie a ete trouvé dans la base de donnée"),
            @ApiResponse(code=400,message = "aucune categorie n'est trouvé dans la base de donnée")
    })
    CategoryDAO findByCode(@PathVariable("codeCategory") String code);

    @GetMapping(value = APP_ROOT+"/category/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des categorie", notes=" cette methode permet de rechercher une categorie avec tous ses attributs",responseContainer = "List<CategoryDAO>")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "liste des categorie/liste vide")
    })
    List<CategoryDAO> findAll();

    @ApiOperation(value = "Supprimer une category", notes="cette methode permet de supprimer une category avec tous ses attributs",response = CategoryDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "supprimer une categorie")
    })
    @DeleteMapping(value = APP_ROOT+"/category/delete/{idCategory}")
    void delete(@PathVariable("idCategory") Integer id);
}
