package com.gestiondestock.spring.Services.ServiceImpl;

import com.gestiondestock.spring.DAO.LigneDeCommandeClientDAO;
import com.gestiondestock.spring.DAO.LigneDeCommandeFournisseurDAO;
import com.gestiondestock.spring.Exception.EntityNotFoundException;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidEntityException;
import com.gestiondestock.spring.Exception.InvalidOperationException;
import com.gestiondestock.spring.Repository.ArticleRepository;
import com.gestiondestock.spring.Repository.LigneDeCommandeClientRepository;
import com.gestiondestock.spring.Repository.LigneDeCommandeFournisseurRepository;
import com.gestiondestock.spring.Services.LigneDeCommandeFournisseurServices;
import com.gestiondestock.spring.Validator.LigneDeCommandeClientValidator;
import com.gestiondestock.spring.Validator.LigneDeCommandeFournisseurValidator;
import com.gestiondestock.spring.models.Article;
import com.gestiondestock.spring.models.LigneDeCommandeClient;
import com.gestiondestock.spring.models.LigneDeCommandeFournisseur;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LigneDeCommandeFournisseurServiceImpl implements LigneDeCommandeFournisseurServices {
    private LigneDeCommandeFournisseurRepository ligneDeCommandeFournisseurRepository;

    private ArticleRepository articleRepository;

    @Autowired
    public LigneDeCommandeFournisseurServiceImpl(LigneDeCommandeFournisseurRepository ligneDeCommandeFournisseurRepository, ArticleRepository articleRepository) {
        this.ligneDeCommandeFournisseurRepository = ligneDeCommandeFournisseurRepository;
        this.articleRepository=articleRepository;
    }
    @Override
    public LigneDeCommandeFournisseurDAO save(LigneDeCommandeFournisseurDAO ligneDeCommandeFournisseurDAO) {
        List<String>errors= LigneDeCommandeFournisseurValidator.validate(ligneDeCommandeFournisseurDAO);
        if(!errors.isEmpty()){
            log.error("vous passez une ligne De Commande fournisseurs {} null ",ligneDeCommandeFournisseurDAO);
            throw new InvalidEntityException("Le ligne de commande fournisseur est null ce qui renvoi une entité invalid", ErrorCodes.Ligne_Commande_Client_Not_VALID,errors);
        }
        return LigneDeCommandeFournisseurDAO.fromEntity(
                ligneDeCommandeFournisseurRepository.save(
                        LigneDeCommandeFournisseurDAO.toEntity(ligneDeCommandeFournisseurDAO)
                ));
    }

    @Override
    public LigneDeCommandeFournisseurDAO findById(Integer id) {
        if(id==null){
            log.error("vous ne pouvez pas trouver un object en passant un id null");
        }
        Optional<LigneDeCommandeFournisseur> ligneDeCommandeFournisseur=ligneDeCommandeFournisseurRepository.findById(id);
        return ligneDeCommandeFournisseur.map(LigneDeCommandeFournisseurDAO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("aucun ligne de commande Fournisseur ne correspond à votre id passer en parametre", ErrorCodes.Category_Not_Found));

    }

    @Override
    public List<LigneDeCommandeFournisseurDAO> findAll() {
        return ligneDeCommandeFournisseurRepository.findAll().stream()
                .map(LigneDeCommandeFournisseurDAO::fromEntity)
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
                    ErrorCodes.Ligne_Commande_Fournisseur_ALREADY_IN_USE);
        }
        ligneDeCommandeFournisseurRepository.deleteById(id);
    }
}
