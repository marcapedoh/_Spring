package com.gestiondestock.spring.Services.ServiceImpl;

import com.gestiondestock.spring.DAO.ArticleDAO;
import com.gestiondestock.spring.DAO.LigneDeCommandeClientDAO;
import com.gestiondestock.spring.DAO.LigneDeCommandeFournisseurDAO;
import com.gestiondestock.spring.DAO.LigneVenteDAO;
import com.gestiondestock.spring.Exception.EntityNotFoundException;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidEntityException;
import com.gestiondestock.spring.Exception.InvalidOperationException;
import com.gestiondestock.spring.Repository.ArticleRepository;
import com.gestiondestock.spring.Repository.LigneDeCommandeClientRepository;
import com.gestiondestock.spring.Repository.LigneDeCommandeFournisseurRepository;
import com.gestiondestock.spring.Repository.LigneVenteRepository;
import com.gestiondestock.spring.Services.ArticleServices;
import com.gestiondestock.spring.Validator.ArticleValidator;
import com.gestiondestock.spring.models.Article;
import com.gestiondestock.spring.models.LigneDeCommandeClient;
import com.gestiondestock.spring.models.LigneDeCommandeFournisseur;
import com.gestiondestock.spring.models.LigneVente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@EnableWebMvc
@EnableAutoConfiguration
public class ArticleServiceImpl implements ArticleServices {
    private ArticleRepository articleRepository;
    private LigneVenteRepository venteRepository;
    private LigneDeCommandeFournisseurRepository commandeFournisseurRepository;
    private LigneDeCommandeClientRepository commandeClientRepository;
    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, LigneVenteRepository venteRepository, LigneDeCommandeFournisseurRepository commandeFournisseurRepository, LigneDeCommandeClientRepository commandeClientRepository) {
        this.articleRepository = articleRepository;
        this.venteRepository = venteRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.commandeClientRepository = commandeClientRepository;
    }
    @Override
    public ArticleDAO save(ArticleDAO articleDAO) {
        List<String> errors= ArticleValidator.validate(articleDAO);
        if(!errors.isEmpty()){
            log.error("Article non valid {}",articleDAO);
            throw new InvalidEntityException("L'article n'est pas valid", ErrorCodes.Article_Not_Valid,errors);
        }
        return ArticleDAO.fromEntity(
                articleRepository.save(
                        ArticleDAO.toEntity(articleDAO)
                )
        );
    }



    @Override
    public ArticleDAO findById(Integer id) {
        if(id==null){
            log.error("l'id de l'article est null");
            return null;
        }
        Optional<Article> article= articleRepository.findById(id);

        return article.map(ArticleDAO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("aucune article ne correspond à votre id passer en parametre", ErrorCodes.Article_Not_Valid));

    }

    @Override
    public ArticleDAO findByCodeArticle(String codeArticle) {
        if(!StringUtils.hasLength(codeArticle)){
            log.error("le code de l'article est null");
            return null;
        }
        Optional<Article> article= articleRepository.findArticleByCodeArticle(codeArticle);
        return article.map(ArticleDAO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("aucune article ne correspond à votre codeArticle passer en parametre", ErrorCodes.Article_Not_Valid));

    }

    @Override
    public List<ArticleDAO> findAll() {
        return articleRepository.findAll().stream()
                .map(ArticleDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneVenteDAO> findHistoriqueVentes(Integer idArticle) {
        return venteRepository.findAllByArticleId(idArticle).stream()
                .map(LigneVenteDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneDeCommandeClientDAO> findHistoriqueCommandeClient(Integer idArticle) {
        return commandeClientRepository.findAllByArticleId(idArticle).stream()
                .map(LigneDeCommandeClientDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneDeCommandeFournisseurDAO> findHistoriqueCommandeFournisseur(Integer idArticle) {
        return commandeFournisseurRepository.findAllByArticleId(idArticle).stream()
                .map(LigneDeCommandeFournisseurDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDAO> findAllArticleByIdCategory(Integer idCategory) {
        return articleRepository.findAllByCategoryId(idCategory).stream()
                .map(ArticleDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error("l'id de l'article est null");
        }
        List<LigneDeCommandeClient> ligneCommandeClients = commandeClientRepository.findAllByArticleId(id);
        if (!ligneCommandeClients.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un article deja utilié dans des commandes client", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        List<LigneDeCommandeFournisseur> ligneCommandeFournisseurs = commandeFournisseurRepository.findAllByArticleId(id);
        if (!ligneCommandeFournisseurs.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des commandes fournisseur",
                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        Optional<LigneVente> ligneVentes = venteRepository.findAllByArticleId(id);
        if (!ligneVentes.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des ventes",
                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        assert id != null;
        articleRepository.deleteById(id);
    }
}
