package com.gestiondestock.spring.Services.ServiceImpl;

import com.gestiondestock.spring.DAO.*;
import com.gestiondestock.spring.Exception.EntityNotFoundException;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidEntityException;
import com.gestiondestock.spring.Exception.InvalidOperationException;
import com.gestiondestock.spring.Repository.*;
import com.gestiondestock.spring.Services.CommandeClientServices;
import com.gestiondestock.spring.Services.MvtStkServices;
import com.gestiondestock.spring.Validator.ArticleValidator;
import com.gestiondestock.spring.Validator.CommandeClientValidator;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@EnableWebMvc
@EnableAutoConfiguration
public class CommandeClientServiceImpl implements CommandeClientServices {
    private CommandeFournisseurRepository commandeFournisseurRepository;

    private LigneDeCommandeClientRepository ligneDeCommandeClientRepository;
    private CommandeClientRepository commandeClientRepository;
    private ClientRepository clientRepository;
    private ArticleRepository articleRepository;
    private MvtStkServices mvtStkServices;
    @Autowired
    public CommandeClientServiceImpl(CommandeClientRepository commandeClientRepository,ClientRepository clientRepository,ArticleRepository articleRepository,
                               CommandeFournisseurRepository commandeFournisseurRepository,LigneDeCommandeClientRepository ligneDeCommandeClientRepository,MvtStkServices mvtStkServices){
        this.clientRepository=clientRepository;
        this.ligneDeCommandeClientRepository=ligneDeCommandeClientRepository;
        this.commandeClientRepository=commandeClientRepository;
        this.articleRepository=articleRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.mvtStkServices=mvtStkServices;
    }
    @Override
    public CommandeClientDAO save(CommandeClientDAO commandeClientDAO) {
        List<String>errors= CommandeClientValidator.validate(commandeClientDAO);
        if(!errors.isEmpty()){
            log.error("vous passer {} nul en parametre pour qu'on l'enregistre vous etes bizzare aussi hein",commandeClientDAO);
        }
        if(commandeClientDAO.getId()!=null && commandeClientDAO.isCommandeLivree()){
            throw new InvalidOperationException("Impossible de modifer la commande lorsqu'elle est livree",ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        Optional<Client> client=clientRepository.findById(commandeClientDAO.getClient().getId());
        if(client.isEmpty()){
            log.warn("aucun client n'est trouvé dans la base de donnée pour cet id {}",commandeClientDAO.getClient().getId());
            throw new EntityNotFoundException("object non trouvé",ErrorCodes.Client_Not_Found);
        }
        List<String>articlesErrors=new ArrayList<>();
        if(commandeClientDAO.getListeCommandeClient()!=null){
            commandeClientDAO.getListeCommandeClient().forEach(ligCmdClt->{
                if(ligCmdClt!=null){
                    Optional<Article>article=articleRepository.findById(ligCmdClt.getArticle().getId());
                    if(article.isEmpty()){
                        articlesErrors.add("l'article aek ID "+ligCmdClt.getArticle().getId()+" n'existe pas");
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
        CommandeClient commandeClient = CommandeClientDAO.toEntity(commandeClientDAO);
        Client clientFound = client.orElseThrow(() -> new EntityNotFoundException("Client non trouvé"));
        commandeClient.setClient(clientFound);
        //CommandeClient commandeClient=commandeClientRepository.save(CommandeClientDAO.toEntity(commandeClientDAO));
        if(commandeClientDAO.getListeCommandeClient()!=null){
            commandeClientDAO.getListeCommandeClient().forEach(ligneDeCommandeClient -> {
                LigneDeCommandeClient ligneDeCommandeClient1= LigneDeCommandeClientDAO.toEntity(ligneDeCommandeClient);
                ligneDeCommandeClient1.setCommandeClient(commandeClient);
                LigneDeCommandeClient ligneDeCommandeClient2= ligneDeCommandeClientRepository.save(ligneDeCommandeClient1);
                effectuerSortie(ligneDeCommandeClient2);
            });
        }
        return CommandeClientDAO.fromEntity(commandeClientRepository.save( commandeClient));
    }

    @Override
    public CommandeClientDAO updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if(!StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("l'état de la commande client est null");
            throw new InvalidOperationException("Impossible de modifier l'état de la commande avec un etat null",ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        CommandeClientDAO commandeClientDAO= checkEtatCommande(idCommande);
        commandeClientDAO.setEtatCommande(etatCommande);
        CommandeClient commandeClientDAO1=commandeClientRepository.save(CommandeClientDAO.toEntity(commandeClientDAO));
        if(commandeClientDAO.isCommandeLivree()){
            updateMvtStk(idCommande);
        }

        return CommandeClientDAO.fromEntity(commandeClientDAO1);
    }

    private void updateMvtStk(Integer idCommande) {
        List<LigneDeCommandeClient> ligneCommandeClientList=ligneDeCommandeClientRepository.findAllByCommandeClientId(idCommande);
        ligneCommandeClientList.forEach(ligneDeCommandeClient -> {
            effectuerSortie(ligneDeCommandeClient);
        });
    }

    private void checkIdCommande(Integer idCommande) {
        if(idCommande==null){
            log.error("Comande client est null");
            throw new InvalidOperationException("Impossible de modifier l'état de la commande avec une ligne de commande null",ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);

        }
    }

    @Override
    public CommandeClientDAO updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            log.error("L'ID de la ligne commande est NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une quantite null ou ZERO",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        CommandeClientDAO commandeClient = checkEtatCommande(idCommande);
        Optional<LigneDeCommandeClient>ligneDeCommandeClientOptional = findLigneCommandeClient(idLigneCommande);

        LigneDeCommandeClient ligneDeCommandeClient = ligneDeCommandeClientOptional.get();
        ligneDeCommandeClient.setQuantite(quantite);
        ligneDeCommandeClientRepository.save(ligneDeCommandeClient);

        return commandeClient;
    }

    private Optional<LigneDeCommandeClient> findLigneCommandeClient(Integer idLigneCommande) {
        Optional<LigneDeCommandeClient> ligneDeCommandeClientOptional=ligneDeCommandeClientRepository.findById(idLigneCommande);
        if(ligneDeCommandeClientOptional.isEmpty()){
            throw new EntityNotFoundException("Aucune ligen commmande n'a été trouvé avec l'ID "+idLigneCommande,ErrorCodes.Commande_Client_Not_Found);
        }
        return ligneDeCommandeClientOptional;
    }

    private CommandeClientDAO checkEtatCommande(Integer idCommande) {
        CommandeClientDAO commandeClientDAO=findById(idCommande);
        if(commandeClientDAO.isCommandeLivree()){
            throw new InvalidOperationException("impossible de modifier la commande lorsqu'elle est livrée",ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        return commandeClientDAO;
    }

    private void checkIdLigneCommande(Integer idLigneCommande) {
        if(idLigneCommande==null){
            log.error("l'ID de la ligne de commande est null");
            throw new InvalidOperationException("Impossible de modifier l'état de la commande avec une ligne de commande null",ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    @Override
    public CommandeClientDAO updateClient(Integer idCommande, Integer idClient) {
        checkIdCommande(idCommande);
        if(idClient==null){
            log.error("l'ID du client est null");
            throw new InvalidOperationException("Impossible de modifier l'état  de la commande avec un ID client null",ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        CommandeClientDAO commandeClientDAO=checkEtatCommande(idCommande);
        Optional<Client> clientOptional=clientRepository.findById(idClient);
        if(clientOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun client n'a été trouvé avec l'ID "+idClient,ErrorCodes.Client_Not_Found);
        }
        commandeClientDAO.setClient(ClientDAO.fromEntity(clientOptional.get()));
        return CommandeClientDAO.fromEntity(commandeClientRepository.save(CommandeClientDAO.toEntity(commandeClientDAO)));
    }

    @Override
    public CommandeClientDAO updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle,"nouveau");
        CommandeClientDAO commandeClientDAO=checkEtatCommande(idCommande);
        Optional<LigneDeCommandeClient> ligneDeCommandeClientOptional=findLigneCommandeClient(idLigneCommande);
        Optional<Article> articleOptional=articleRepository.findById(idArticle);
        if(articleOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun article n'a été trouvé avec l'ID "+idArticle,ErrorCodes.Article_Not_Found);

        }
        List<String> errors= ArticleValidator.validate(ArticleDAO.fromEntity(articleOptional.get()));
        if(!errors.isEmpty()){
            throw new InvalidEntityException("Article invalid",ErrorCodes.Article_Not_Valid,errors);
        }
        LigneDeCommandeClient ligneDeCommandeClient=ligneDeCommandeClientOptional.get();
        ligneDeCommandeClient.setArticle(articleOptional.get());
        ligneDeCommandeClientRepository.save(ligneDeCommandeClient);
        return commandeClientDAO;
    }

    private void checkIdArticle(Integer idArticle, String message) {
        if(idArticle==null){
            log.error("l'ID de "+message+" Article est nulle");
            throw new InvalidOperationException("Impossible de modifier l'état dela commande avec un "+message+" ID article null",ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    @Override
    public CommandeClientDAO deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        CommandeClientDAO commandeClientDAO=checkEtatCommande(idCommande);
        findLigneCommandeClient(idLigneCommande);
        ligneDeCommandeClientRepository.deleteById(idLigneCommande);
        return commandeClientDAO;
    }

    @Override
    public CommandeClientDAO findById(Integer id) {
        if(id==null){
            log.error("l'ID de la commande du client est null");
            return null;
        }
        return commandeClientRepository.findById(id)
                .map(CommandeClientDAO::fromEntity)
                .orElseThrow(()->new EntityNotFoundException(
                        "Aucune commande client n'a été trouvé avec l'ID "+id,ErrorCodes.Commande_Client_Not_Found
                ));
    }

    @Override
    public CommandeClientDAO findByCode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("le code de la commande du client est null");
            return null;
        }
        return commandeClientRepository.findCommandeClientByCode(code)
                .map(CommandeClientDAO::fromEntity)
                .orElseThrow(()->new EntityNotFoundException(
                        "Aucune commande client n'a été trouvé avec le code "+code,ErrorCodes.Commande_Client_Not_Found
                ));
    }

    @Override
    public List<CommandeClientDAO> findAll() {
        return commandeClientRepository.findAll().stream()
                .map(CommandeClientDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneDeCommandeClientDAO> findAllLignesCommandesClientByCommandeClientId(Integer idCommande) {
        return ligneDeCommandeClientRepository.findAllByCommandeClientId(idCommande).stream()
                .map(LigneDeCommandeClientDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error("l'ID de la commande du client est null");
        }
        commandeClientRepository.deleteById(id);
    }
    private  void effectuerSortie(LigneDeCommandeClient ligneDeCommandeClient){
        MvtStkDAO mvtStkDAO= MvtStkDAO.builder()
                .article(ArticleDAO.fromEntity(ligneDeCommandeClient.getArticle()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.Sortie)
                .sourceMvtStk(SourceMvtStk.COMMANDE_CLIENT)
                .quantite(ligneDeCommandeClient.getQuantite())
                .build();
        mvtStkServices.sortieStock(mvtStkDAO);
    }
}
