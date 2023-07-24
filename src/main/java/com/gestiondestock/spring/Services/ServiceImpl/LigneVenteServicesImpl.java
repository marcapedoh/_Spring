package com.gestiondestock.spring.Services.ServiceImpl;

import com.gestiondestock.spring.DAO.CategoryDAO;
import com.gestiondestock.spring.DAO.LigneVenteDAO;
import com.gestiondestock.spring.Exception.EntityNotFoundException;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidEntityException;
import com.gestiondestock.spring.Exception.InvalidOperationException;
import com.gestiondestock.spring.Repository.ArticleRepository;
import com.gestiondestock.spring.Repository.LigneVenteRepository;
import com.gestiondestock.spring.Services.LigneVenteServices;
import com.gestiondestock.spring.Validator.CategoryValidator;
import com.gestiondestock.spring.Validator.LigneVenteValidator;
import com.gestiondestock.spring.models.Article;
import com.gestiondestock.spring.models.Category;
import com.gestiondestock.spring.models.LigneVente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LigneVenteServicesImpl implements LigneVenteServices {
    private LigneVenteRepository ligneVenteRepository;
    private ArticleRepository articleRepository;
    @Autowired
    public LigneVenteServicesImpl(LigneVenteRepository ligneVenteRepository, ArticleRepository articleRepository){
        this.ligneVenteRepository=ligneVenteRepository;
        this.articleRepository=articleRepository;
    }
    @Override
    public LigneVenteDAO save(LigneVenteDAO ligneVenteDAO) {
        List<String> errors= LigneVenteValidator.validate(ligneVenteDAO);
        if(!errors.isEmpty()){
            log.error("Ligne Vente non valide {}",ligneVenteDAO);
            throw new InvalidEntityException("la ligne de vente n'est pas valide", ErrorCodes.LIGNE_VENTE_NOT_VALID,errors);
        }
        return LigneVenteDAO.fromEntity(
                ligneVenteRepository.save(
                        LigneVenteDAO.toEntity(ligneVenteDAO)
                )
        );
    }

    @Override
    public LigneVenteDAO findById(Integer id) {
        if(id==null){
            log.error("vous ne pouvez cherchez une Ligne de vente qui est null");
            return null;
        }
        Optional<LigneVente> ligneVente=ligneVenteRepository.findById(id);
//
        return ligneVente.map(LigneVenteDAO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("aucune Ligne Vente ne correspond à votre id passer en parametre", ErrorCodes.Ligne_Vente_Not_Found));

    }


    @Override
    public List<LigneVenteDAO> findAll() {
        return ligneVenteRepository.findAll().stream()
                .map(LigneVenteDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error("vous ne pouvez cherchez une Ligne de vente pour le supprimer si le champ id est null");
        }
        List<Article> articles = articleRepository.findAllByCategoryId(id);
        if (!articles.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer cette Ligne Vente qui est deja utilise",
                    ErrorCodes.Ligne_VENTE_ALREADY_IN_USE);
        }
        assert id != null;
        ligneVenteRepository.deleteById(id);

    }
}
