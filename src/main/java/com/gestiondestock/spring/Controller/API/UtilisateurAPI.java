package com.gestiondestock.spring.Controller.API;

import com.gestiondestock.spring.DAO.ChangerMotDePasseUtilisateurDAO;
import com.gestiondestock.spring.DAO.UtilisateurDAO;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.gestiondestock.spring.Constants.Utils.APP_ROOT;
import static com.gestiondestock.spring.Constants.Utils.UTILISATEUR_ENDPOINT;

@Api(APP_ROOT + "/utilisateur")
public interface UtilisateurAPI {
    @PostMapping(value = APP_ROOT+"/utilisateur/create", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "enregistrer un utilisateur", notes=" cette methode permet d'enregistrer et modifier un utilisateur",response = UtilisateurDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "l'object utilisateur a été bien crée ou modifer")
    })
    UtilisateurDAO save(@RequestBody UtilisateurDAO utilisateurDAO);
    @GetMapping(value = APP_ROOT+"/utilisateur/findById/{idUtilisateur}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un utilisateur", notes=" cette methode permet de rechercher un utilisateur par son ID",response =UtilisateurDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "l'utilisateur a ete trouvé dans la base de donnée"),
            @ApiResponse(code=404,message = "aucun utilisateur n'est trouvé dans la base de donnée")
    })
    UtilisateurDAO findById(@PathVariable("idUtilisateur") Integer idUtilisateur);
    @GetMapping(value = APP_ROOT+"/utilisateur/findByNom/{nomUtilisateur}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un utilisateur", notes=" cette methode permet de rechercher un utilisateur par son nom",response = UtilisateurDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "l'utilisateur a été trouvé dans la base de donnée"),
            @ApiResponse(code=400,message = "aucun utilisateur n'est trouvé dans la base de donnée")
    })
    UtilisateurDAO findByNom(@PathVariable("nomUtilisateur") String nomUtilisateur);
    @PostMapping(UTILISATEUR_ENDPOINT + "/update/password")
    UtilisateurDAO changerMotDePasse(@RequestBody ChangerMotDePasseUtilisateurDAO dto);

    @GetMapping(value = APP_ROOT+"/utilisateur/findByMailAndMotDePasse/{mail}/{motDePasse}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un utilisateur", notes=" cette methode permet de rechercher un utilisateur par son mail et son mot de passe",response = UtilisateurDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "l'utilisateur a été trouvé dans la base de donnée"),
            @ApiResponse(code=400,message = "aucun utilisateur n'est trouvé dans la base de donnée")
    })
    UtilisateurDAO findByMailAndMotDePasse(@PathVariable("mail") String mail,@PathVariable("motDePasse") String motDePasse);
    @GetMapping(value = APP_ROOT+"/utilisateur/findByMail/{mail}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un utilisateur", notes=" cette methode permet de rechercher un utilisateur par son mail ",response = UtilisateurDAO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "l'utilisateur a été trouvé dans la base de donnée"),
            @ApiResponse(code=400,message = "aucun utilisateur n'est trouvé dans la base de donnée")
    })
    UtilisateurDAO findByMail(@PathVariable("mail") String mail);
    @GetMapping(value = APP_ROOT+"/utilisateur/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des utilisateurs", notes=" cette methode permet de retourner un utilisateur avec tous ses attributs",responseContainer = "List<UtilisateurDAO>")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "liste des Utilisateurs/liste vide")
    })
    List<UtilisateurDAO> findAll();
    @DeleteMapping(value = APP_ROOT+"/utilisateur/delete/{idUtilisateur}")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "supprimer un utilisateur")
    })
    void delete(@PathVariable("idUtilisateur") Integer id);

    @PutMapping(value=APP_ROOT+"/desactiverUser/{idUtilisateur}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "un utilisateur a été désactiver avec succès")
    })
    void desactiverUser(@PathVariable("idUtilisateur") Integer id);
    @PutMapping(value = APP_ROOT+"/activer/{idUtilisateur}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "un utilisateur a été activer avec succès")
    })
    void activerUser(@PathVariable("idUtilisateur") Integer id);
}
