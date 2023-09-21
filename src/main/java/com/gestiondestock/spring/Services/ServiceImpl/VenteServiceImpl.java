package com.gestiondestock.spring.Services.ServiceImpl;

import com.gestiondestock.spring.DAO.*;
import com.gestiondestock.spring.Exception.EntityNotFoundException;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidEntityException;
import com.gestiondestock.spring.Exception.InvalidOperationException;
import com.gestiondestock.spring.Repository.ArticleRepository;
import com.gestiondestock.spring.Repository.LigneVenteRepository;
import com.gestiondestock.spring.Repository.VenteRepository;
import com.gestiondestock.spring.Services.MvtStkServices;
import com.gestiondestock.spring.Services.VenteServices;
import com.gestiondestock.spring.Validator.VenteValidator;
import com.gestiondestock.spring.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@EnableWebMvc
@EnableAutoConfiguration
public class VenteServiceImpl implements VenteServices {
    private ArticleRepository articleRepository;
    private VenteRepository ventesRepository;
    private LigneVenteRepository ligneVenteRepository;
    private MvtStkServices mvtStkService;

    @Autowired
    public VenteServiceImpl(ArticleRepository articleRepository, VenteRepository ventesRepository,
                            LigneVenteRepository ligneVenteRepository, MvtStkServices mvtStkService) {
        this.articleRepository = articleRepository;
        this.ventesRepository = ventesRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mvtStkService = mvtStkService;
    }
    @Override
    public VenteDAO save(VenteDAO dto) {
        List<String> errors = VenteValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Ventes n'est pas valide");
            throw new InvalidEntityException("L'objet vente n'est pas valide", ErrorCodes.Vente_Not_Valid, errors);
        }
        List<String> articleErrors = new ArrayList<>();

        dto.getLigneVente().forEach(ligneVenteDto -> {
            Optional<Article> article = articleRepository.findById(ligneVenteDto.getArticle().getId());
            if (article.isEmpty()) {
                articleErrors.add("Aucun article avec l'ID " + ligneVenteDto.getArticle().getId() + " n'a ete trouve dans la BDD");
            }
        });

        if (!articleErrors.isEmpty()) {
            log.error("One or more articles were not found in the DB, {}", errors);
            throw new InvalidEntityException("Un ou plusieurs articles n'ont pas ete trouve dans la BDD", ErrorCodes.Vente_Not_Valid, errors);
        }

        Vente savedVentes = ventesRepository.save(VenteDAO.toEntity(dto));

        dto.getLigneVente().forEach(ligneVenteDto -> {
            LigneVente ligneVente = LigneVenteDAO.toEntity(ligneVenteDto);
            ligneVente.setVentes(savedVentes);
            LigneVente ligneVente1= ligneVenteRepository.save(ligneVente);
            updateMvtStk(ligneVente1);
        });
        return VenteDAO.fromEntity(savedVentes); }

    private void updateMvtStk(LigneVente ligneVente) {
        MvtStkDAO mvtStkDAO = MvtStkDAO.builder()
                .article(ArticleDAO.fromEntity(ligneVente.getArticle()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.Sortie)
                .sourceMvtStk(SourceMvtStk.VENTE)
                .quantite(ligneVente.getQuantite())
                .build();
        mvtStkService.sortieStock(mvtStkDAO);
    }

    @Override
    public VenteDAO findById(Integer id) {
        if (id == null) {
            log.error("Ventes ID is NULL");
            return null;
        }
        return ventesRepository.findById(id)
                .map(VenteDAO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucun vente n'a ete trouve dans la BDD", ErrorCodes.Vente_Not_Found));

    }

    @Override
    public VenteDAO findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Vente CODE is NULL");
            return null;
        }
        return ventesRepository.findVenteByCode(code)
                .map(VenteDAO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune vente client n'a ete trouve avec le CODE " + code, ErrorCodes.Vente_Not_Found
                ));
    }

    @Override
    public List<VenteDAO> findAll() {
        return ventesRepository.findAll().stream()
                .map(VenteDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Vente ID is NULL");
            return;
        }
        Optional<LigneVente> ligneVentes = ligneVenteRepository.findAllByVentesId(id);
        if (!ligneVentes.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer une vente alors qu'elle est liée à des ligne de vente...",
                    ErrorCodes.VENTE_ALREADY_IN_USE);
        }
        ventesRepository.deleteById(id);
    }
}
