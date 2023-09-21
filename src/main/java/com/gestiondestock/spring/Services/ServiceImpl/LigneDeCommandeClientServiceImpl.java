package com.gestiondestock.spring.Services.ServiceImpl;

import com.gestiondestock.spring.DAO.FournisseurDAO;
import com.gestiondestock.spring.DAO.LigneDeCommandeClientDAO;
import com.gestiondestock.spring.Exception.EntityNotFoundException;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidEntityException;
import com.gestiondestock.spring.Exception.InvalidOperationException;
import com.gestiondestock.spring.Repository.ArticleRepository;
import com.gestiondestock.spring.Repository.LigneDeCommandeClientRepository;
import com.gestiondestock.spring.Services.LigneDeCommandeClientServices;
import com.gestiondestock.spring.Validator.LigneDeCommandeClientValidator;
import com.gestiondestock.spring.models.Article;
import com.gestiondestock.spring.models.LigneDeCommandeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@EnableWebMvc
@EnableAutoConfiguration
@Transactional
public class LigneDeCommandeClientServiceImpl implements LigneDeCommandeClientServices {
    private LigneDeCommandeClientRepository ligneDeCommandeClientRepository;
    private ArticleRepository articleRepository;
    @Autowired
    public LigneDeCommandeClientServiceImpl(LigneDeCommandeClientRepository ligneDeCommandeClientRepository,ArticleRepository articleRepository) {
        this.ligneDeCommandeClientRepository = ligneDeCommandeClientRepository;
        this.articleRepository=articleRepository;
    }
    @Override
    public LigneDeCommandeClientDAO save(LigneDeCommandeClientDAO ligneDeCommandeClientDAO) {
        List<String>errors= LigneDeCommandeClientValidator.validate(ligneDeCommandeClientDAO);
        if(!errors.isEmpty()){
            log.error("vous passez une ligne De Commande Client {} null ",ligneDeCommandeClientDAO);
            throw new InvalidEntityException("Le ligne de commande client est null ce qui renvoi une entité invalid", ErrorCodes.Ligne_Commande_Client_Not_VALID,errors);
        }
        return LigneDeCommandeClientDAO.fromEntity(
                ligneDeCommandeClientRepository.save(
                        LigneDeCommandeClientDAO.toEntity(ligneDeCommandeClientDAO)
                ));
    }

    @Override
    public LigneDeCommandeClientDAO findById(Integer id) {
        if(id==null){
            log.error("vous ne pouvez pas trouver un object en passant un id null");
        }
        Optional<LigneDeCommandeClient> ligneDeCommandeClient=ligneDeCommandeClientRepository.findById(id);
        return ligneDeCommandeClient.map(LigneDeCommandeClientDAO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("aucun ligne de commande Fournisseur ne correspond à votre id passer en parametre", ErrorCodes.Category_Not_Found));

    }

    @Override
    public List<LigneDeCommandeClientDAO> findAll() {
        return ligneDeCommandeClientRepository.findAll().stream()
                .map(LigneDeCommandeClientDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error("vous ne pouvez cherchez une category si le champ est null");
        }
        List<Article> articles = articleRepository.findAllByCategoryId(id);
        if (!articles.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer cette categorie qui est deja utilise",
                    ErrorCodes.Ligne_Commande_client_ALREADY_IN_USE);
        }
        ligneDeCommandeClientRepository.deleteById(id);
    }
}
