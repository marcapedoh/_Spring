package com.gestiondestock.spring.Services.ServiceImpl;

import com.gestiondestock.spring.DAO.*;
import com.gestiondestock.spring.Exception.EntityNotFoundException;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidEntityException;
import com.gestiondestock.spring.Exception.InvalidOperationException;
import com.gestiondestock.spring.Repository.ArticleRepository;
import com.gestiondestock.spring.Repository.CommandeFournisseurRepository;
import com.gestiondestock.spring.Repository.FournisseurRepository;
import com.gestiondestock.spring.Repository.LigneDeCommandeFournisseurRepository;
import com.gestiondestock.spring.Services.CommandeFournisseurServices;
import com.gestiondestock.spring.Services.MvtStkServices;
import com.gestiondestock.spring.Validator.ArticleValidator;
import com.gestiondestock.spring.Validator.CommandeFournisseurValidator;
import com.gestiondestock.spring.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@EnableWebMvc
@EnableAutoConfiguration
public class CommandeFournisseurServiceImpl implements CommandeFournisseurServices {
    private CommandeFournisseurRepository commandeFournisseurRepository;
    private LigneDeCommandeFournisseurRepository ligneDeCommandeFournisseurRepository;
    private FournisseurRepository fournisseurRepository;
    private ArticleRepository articleRepository;
    private MvtStkServices mvtStkServices;
    @Autowired
    public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository,LigneDeCommandeFournisseurRepository ligneDeCommandeFournisseurRepository,FournisseurRepository fournisseurRepository,ArticleRepository articleRepository,MvtStkServices mvtStkServices) {
        this.fournisseurRepository=fournisseurRepository;
        this.commandeFournisseurRepository=commandeFournisseurRepository;
        this.articleRepository=articleRepository;
        this.ligneDeCommandeFournisseurRepository=ligneDeCommandeFournisseurRepository;
        this.mvtStkServices=mvtStkServices;
    }
    @Override
    public CommandeFournisseurDAO save(CommandeFournisseurDAO commandeFournisseurDAO) {
        List<String> errors= CommandeFournisseurValidator.validate(commandeFournisseurDAO);
        if(!errors.isEmpty()){
            log.error("vous passer {} nul en parametre pour qu'on l'enregistre vous etes bizzare aussi hein",commandeFournisseurDAO);
        }
        if(commandeFournisseurDAO.getId()!=null && commandeFournisseurDAO.isCommandeLivree()){
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree",ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        Optional<Fournisseur> fournisseur=fournisseurRepository.findById(commandeFournisseurDAO.getFournisseur().getId());
        if(fournisseur.isEmpty()){
            log.warn("aucun fournisseur n'est trouvé dans la base de donnée pour cet id {}",commandeFournisseurDAO.getFournisseur().getId());
            throw new EntityNotFoundException("object non trouvé", ErrorCodes.Fournisseur_Not_Found);
        }
        List<String>articlesErrors=new ArrayList<>();
        if(commandeFournisseurDAO.getLigneCommandeFournisseurs()!=null){
            commandeFournisseurDAO.getLigneCommandeFournisseurs().forEach(ligCmdFrs->{
                if(ligCmdFrs!=null){
                    Optional<Article>article=articleRepository.findById(ligCmdFrs.getArticle().getId());
                    if(article.isEmpty()){
                        articlesErrors.add("l'article aek ID "+ligCmdFrs.getArticle().getId()+" n'existe pas");
                    }
                }else{
                    articlesErrors.add("impossible d'enregistrer une commande aek un article NULL");
                }
            });
        }
        if(!articlesErrors.isEmpty()){
            log.warn("");
            throw new InvalidEntityException("l'article n'existe pas dans la base de donnée", ErrorCodes.Article_Not_Valid,articlesErrors);
        }
        CommandeFournisseur commandeFournisseur = CommandeFournisseurDAO.toEntity(commandeFournisseurDAO);
        Fournisseur fournisseur1 = fournisseur.orElseThrow(() -> new EntityNotFoundException("Fourniseur non trouvé"));
        commandeFournisseur.setFournisseur(fournisseur1);
        if(commandeFournisseurDAO.getLigneCommandeFournisseurs()!=null){
            commandeFournisseurDAO.getLigneCommandeFournisseurs().forEach(ligCmdfrs -> {
                LigneDeCommandeFournisseur ligneDeCommandeFournisseur1=LigneDeCommandeFournisseurDAO.toEntity(ligCmdfrs);
                ligneDeCommandeFournisseur1.setCommandeFournisseurs(commandeFournisseur);
                ligneDeCommandeFournisseurRepository.save(ligneDeCommandeFournisseur1);
            });
        }
        return CommandeFournisseurDAO.fromEntity(commandeFournisseurRepository.save(commandeFournisseur));
    }

    @Override
    public CommandeFournisseurDAO updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if (!StringUtils.hasLength(String.valueOf(etatCommande))) {
            log.error("L'etat de la commande fournisseur est NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un etat null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        CommandeFournisseurDAO commandeFournisseur = checkEtatCommande(idCommande);
        commandeFournisseur.setEtatCommande(etatCommande);

        CommandeFournisseur savedCommande = commandeFournisseurRepository.save(CommandeFournisseurDAO.toEntity(commandeFournisseur));
        if (commandeFournisseur.isCommandeLivree()) {
            updateMvtStk(idCommande);
        }

        return CommandeFournisseurDAO.fromEntity(savedCommande);
    }

    private void updateMvtStk(Integer idCommande) {
        List<LigneDeCommandeFournisseur> ligneCommandeFournisseur = ligneDeCommandeFournisseurRepository.findAllByCommandeFournisseursId(idCommande);
        ligneCommandeFournisseur.forEach(lig -> {
            effectuerEntree(lig);
        });
    }

    private void effectuerEntree(LigneDeCommandeFournisseur lig) {
            MvtStkDAO mvtStkDto = MvtStkDAO.builder()
                    .article(ArticleDAO.fromEntity(lig.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvt(TypeMvtStk.Entree)
                    .sourceMvtStk(SourceMvtStk.COMMANDE_FOURNISSEUR)
                    .quantite(lig.getQuantite())
                    .build();
            mvtStkServices.entreeStock(mvtStkDto);
    }

    private CommandeFournisseurDAO checkEtatCommande(Integer idCommande) {
        CommandeFournisseurDAO commandeFournisseur = findById(idCommande);
        if (commandeFournisseur.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        return commandeFournisseur;
    }

    private void checkIdCommande(Integer idCommande) {
        if (idCommande == null) {
            log.error("Commande fournisseur ID est NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    @Override
    public CommandeFournisseurDAO updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            log.error("L'ID de la ligne commande est NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une quantite null ou ZERO",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        CommandeFournisseurDAO commandeFournisseur = checkEtatCommande(idCommande);
        Optional<LigneDeCommandeFournisseur> ligneCommandeFournisseurOptional = findLigneCommandeFournisseur(idLigneCommande);
//gérer l'erreur de NoSuchElementException
        try{
            LigneDeCommandeFournisseur ligneCommandeFounisseur = ligneCommandeFournisseurOptional.get();
        }catch (NoSuchElementException except) {
            System.out.println("erreur de type entity not found exception tu l'object n'est pas trouvé");
            LigneDeCommandeFournisseur ligneCommandeFounisseur = ligneCommandeFournisseurOptional.get();
            ligneCommandeFounisseur.setQuantite(quantite);
            ligneDeCommandeFournisseurRepository.save(ligneCommandeFounisseur);
        }


        return commandeFournisseur;
    }

    private Optional<LigneDeCommandeFournisseur> findLigneCommandeFournisseur(Integer idLigneCommande) {
        Optional<LigneDeCommandeFournisseur> ligneCommandeFournisseurOptional = ligneDeCommandeFournisseurRepository.findById(idLigneCommande);
        if (ligneCommandeFournisseurOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune ligne commande fournisseur n'a ete trouve avec l'ID " + idLigneCommande, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND);
        }
        return ligneCommandeFournisseurOptional;
    }

    private void checkIdLigneCommande(Integer idLigneCommande) {
        if (idLigneCommande == null) {
            log.error("L'ID de la ligne commande est NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ligne de commande null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    @Override
    public CommandeFournisseurDAO updateFournisseur(Integer idCommande, Integer idFournisseur) {
        checkIdCommande(idCommande);
        if (idFournisseur == null) {
            log.error("L'ID du fournisseur is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID fournisseur null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        CommandeFournisseurDAO commandeFournisseur = checkEtatCommande(idCommande);
        Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(idFournisseur);
        if (fournisseurOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucun fournisseur n'a ete trouve avec l'ID " + idFournisseur, ErrorCodes.Fournisseur_Not_Found);
        }
        commandeFournisseur.setFournisseur(FournisseurDAO.fromEntity(fournisseurOptional.get()));

        return CommandeFournisseurDAO.fromEntity(
                commandeFournisseurRepository.save(CommandeFournisseurDAO.toEntity(commandeFournisseur))
        );
    }

    @Override
    public CommandeFournisseurDAO updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouveau");

        CommandeFournisseurDAO commandeFournisseur = checkEtatCommande(idCommande);

        Optional<LigneDeCommandeFournisseur> ligneCommandeFournisseur = findLigneCommandeFournisseur(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if (articleOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune article n'a ete trouve avec l'ID " + idArticle, ErrorCodes.Article_Not_Found);
        }

        List<String> errors = ArticleValidator.validate(ArticleDAO.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Article invalid", ErrorCodes.Article_Not_Valid, errors);
        }

        LigneDeCommandeFournisseur ligneCommandeFournisseurToSaved = ligneCommandeFournisseur.get();
        ligneCommandeFournisseurToSaved.setArticle(articleOptional.get());
        ligneDeCommandeFournisseurRepository.save(ligneCommandeFournisseurToSaved);

        return commandeFournisseur;
    }

    private void checkIdArticle(Integer idArticle, String msg) {
        if (idArticle == null) {
            log.error("L'ID de " + msg + " is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un " + msg + " ID article null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    @Override
    public CommandeFournisseurDAO deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        CommandeFournisseurDAO commandeFournisseur = checkEtatCommande(idCommande);
        // Just to check the LigneCommandeFournisseur and inform the user in case it is absent
        findLigneCommandeFournisseur(idLigneCommande);
        ligneDeCommandeFournisseurRepository.deleteById(idLigneCommande);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDAO findById(Integer id) {
        if(id==null){
            log.error("l'ID de la commande du fournisseur est null");
            return null;
        }

        return commandeFournisseurRepository.findById(id)
                .map(CommandeFournisseurDAO::fromEntity)
                .orElseThrow(()->new EntityNotFoundException(
                        "Aucune commande founisseur n'a été trouvé avec l'ID "+id,ErrorCodes.Commande_Fournisseur_Not_Found
                ));
    }

    @Override
    public CommandeFournisseurDAO findByCode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("le code de la commande du client est null");
            return null;
        }
        return commandeFournisseurRepository.findCommandeFournisseurByCode(code)
                .map(CommandeFournisseurDAO::fromEntity)
                .orElseThrow(()->new EntityNotFoundException(
                        "Aucune commande founisseur n'a été trouvé avec l'ID "+code,ErrorCodes.Commande_Fournisseur_Not_Found
                ));
    }

    @Override
    public List<CommandeFournisseurDAO> findAll() {
        return commandeFournisseurRepository.findAll().stream()
                .map(CommandeFournisseurDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneDeCommandeFournisseurDAO> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande) {
        return ligneDeCommandeFournisseurRepository.findAllByCommandeFournisseursId(idCommande).stream()
                .map(LigneDeCommandeFournisseurDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error("l'ID de la commande du fourisseur est null");
            return;
        }
        List<LigneDeCommandeFournisseur>ligneDeCommandeFournisseurs=ligneDeCommandeFournisseurRepository.findAllByCommandeFournisseursId(id);
        if(!ligneDeCommandeFournisseurs.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une commande fournisseur déjà utilisée",ErrorCodes.COMMANDE_FOURNIISEEUR_ALREADY_IN_USE);
        }
        commandeFournisseurRepository.deleteById(id);
    }
}
