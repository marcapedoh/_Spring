package com.gestiondestock.spring.Services.ServiceImpl;

import com.gestiondestock.spring.DAO.CategoryDAO;
import com.gestiondestock.spring.DAO.FournisseurDAO;
import com.gestiondestock.spring.Exception.EntityNotFoundException;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidEntityException;
import com.gestiondestock.spring.Exception.InvalidOperationException;
import com.gestiondestock.spring.Repository.ArticleRepository;
import com.gestiondestock.spring.Repository.CommandeFournisseurRepository;
import com.gestiondestock.spring.Repository.FournisseurRepository;
import com.gestiondestock.spring.Repository.LigneDeCommandeClientRepository;
import com.gestiondestock.spring.Services.FournisseurServices;
import com.gestiondestock.spring.Validator.FournisseurValidator;
import com.gestiondestock.spring.models.CommandeFournisseur;
import com.gestiondestock.spring.models.Fournisseur;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@EnableAutoConfiguration
@EnableWebMvc
public class FournisseurServiceImpl implements FournisseurServices {
    private CommandeFournisseurRepository commandeFournisseurRepository;
    private LigneDeCommandeClientRepository ligneDeCommandeClientRepository;
    private FournisseurRepository fournisseurRepository;
    private ArticleRepository articleRepository;
    @Autowired
    public  FournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository,LigneDeCommandeClientRepository ligneDeCommandeClientRepository,FournisseurRepository fournisseurRepository,ArticleRepository articleRepository){
        this.commandeFournisseurRepository=commandeFournisseurRepository;
        this.ligneDeCommandeClientRepository=ligneDeCommandeClientRepository;
        this.fournisseurRepository=fournisseurRepository;
        this.articleRepository=articleRepository;

    }
    @Override
    public FournisseurDAO save(FournisseurDAO fournisseurDAO) {
        List<String>errors= FournisseurValidator.validate(fournisseurDAO);
        if(!errors.isEmpty()){
            log.error("vous passez un fournisseur {} null ",fournisseurDAO);
            throw new InvalidEntityException("Le fournisseur est null ce qui renvoi une entité invalid", ErrorCodes.Fournisseur_Not_Valid,errors);
        }

        return FournisseurDAO.fromEntity(
                fournisseurRepository.save(
                        FournisseurDAO.toEntity(fournisseurDAO)
                )
        );
    }

    @Override
    public FournisseurDAO findById(Integer id) {
        if(id==null){
            log.error("vous ne pouvez pas chercher un object en passant un id null");
        }
        Optional<Fournisseur> fournisseur=fournisseurRepository.findById(id);
        return fournisseur.map(FournisseurDAO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("aucun fournisseur ne correspond à votre id passer en parametre", ErrorCodes.Category_Not_Found));

    }

    @Override
    public FournisseurDAO findByNom(String nom) {
        if(!StringUtils.hasLength(nom)){
            log.error("Vous essayer de chercher une entité et vous passez une nom null ce n'est as normal");
        }
        Optional<Fournisseur>fournisseur=fournisseurRepository.findFournisseurByNom(nom);
        return fournisseur.map(FournisseurDAO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("aucun fournisseur ne correspond à votre nom passer en parametre", ErrorCodes.Category_Not_Found));

    }

    @Override
    public List<FournisseurDAO> findAll() {
        return fournisseurRepository.findAll().stream()
                .map(FournisseurDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error("comment vous voulez-vous supprimer un fournisseur si vous passez un id vide");
        }
        List<CommandeFournisseur> commandeFournisseur = commandeFournisseurRepository.findAllByFournisseurId(id);
        if (!commandeFournisseur.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un fournisseur qui a deja des commandes",
                    ErrorCodes.FOURNISSEUR_ALREADY_IN_USE);
        }
        fournisseurRepository.deleteById(id);
    }
}
