package com.gestiondestock.spring.Services.ServiceImpl;

import com.gestiondestock.spring.DAO.ArticleDAO;
import com.gestiondestock.spring.DAO.LivraisonDAO;
import com.gestiondestock.spring.Exception.EntityNotFoundException;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidEntityException;
import com.gestiondestock.spring.Exception.InvalidOperationException;
import com.gestiondestock.spring.Repository.CommandeFournisseurRepository;
import com.gestiondestock.spring.Repository.FournisseurRepository;
import com.gestiondestock.spring.Repository.LivraisonRepository;
import com.gestiondestock.spring.Services.LivraisonServices;
import com.gestiondestock.spring.Validator.ArticleValidator;
import com.gestiondestock.spring.Validator.LivraisonValidator;
import com.gestiondestock.spring.models.Article;
import com.gestiondestock.spring.models.CommandeFournisseur;
import com.gestiondestock.spring.models.Fournisseur;
import com.gestiondestock.spring.models.Livraison;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LivraisonServiceImpl implements LivraisonServices {
    private LivraisonRepository livraisonRepository;
    private CommandeFournisseurRepository commandeFournisseurRepository;
    private FournisseurRepository fournisseurRepository;
    @Autowired
    public LivraisonServiceImpl(LivraisonRepository livraisonRepository,CommandeFournisseurRepository commandeFournisseurRepository,FournisseurRepository fournisseurRepository) {
        this.livraisonRepository = livraisonRepository;
        this.commandeFournisseurRepository=commandeFournisseurRepository;
        this.fournisseurRepository=fournisseurRepository;
    }

    @Override
    public LivraisonDAO save(LivraisonDAO livraisonDAO) {
        List<String> errors= LivraisonValidator.validate(livraisonDAO);
        if(!errors.isEmpty()){
            log.error("Livraison non valid {}",livraisonDAO);
            throw new InvalidEntityException("L'article n'est pas valid", ErrorCodes.Article_Not_Valid,errors);
        }
        return LivraisonDAO.fromEntity(
                livraisonRepository.save(
                        LivraisonDAO.toEntity(livraisonDAO)
                )
        );
    }

    @Override
    public LivraisonDAO findByCode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("le code de la livraison est null");
            return null;
        }
        Optional<Livraison> livraison= livraisonRepository.findLivraisonByCode(code);
        return livraison.map(LivraisonDAO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("aucune Livraison ne correspond à votre code passer en parametre", ErrorCodes.LIVRAISON_NOT_FOUND));

    }

    @Override
    public List<LivraisonDAO> findAll() {
        return livraisonRepository.findAll().stream()
                .map(LivraisonDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer idLivraison) {
        if(idLivraison==null){
            log.warn("l'id de la livraison est null");
        }
        Optional<CommandeFournisseur> commandeFournisseur= commandeFournisseurRepository.findAllByLivraisonId(idLivraison);
        if(!commandeFournisseur.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une livraison deja utilié dans des commandes fournisseur", ErrorCodes.LIVRAISON_IS_ALREADY_USE);
        }
        Optional<Fournisseur> fournisseur=fournisseurRepository.findAllByLivraisonId(idLivraison);
        if(!fournisseur.isEmpty()){
            throw  new InvalidOperationException("Impossible de supprimer une livraison qui est lié à des fournisseurs",ErrorCodes.FOURNISSEUR_ALREADY_IN_USE);
        }
        if(idLivraison!=null){
            livraisonRepository.deleteById(idLivraison);
        }

    }
}
