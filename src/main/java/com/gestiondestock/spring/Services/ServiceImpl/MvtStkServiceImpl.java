package com.gestiondestock.spring.Services.ServiceImpl;

import com.gestiondestock.spring.DAO.LivraisonDAO;
import com.gestiondestock.spring.DAO.MvtStkDAO;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidEntityException;
import com.gestiondestock.spring.Repository.MvtStkRepository;
import com.gestiondestock.spring.Services.ArticleServices;
import com.gestiondestock.spring.Services.MvtStkServices;
import com.gestiondestock.spring.Validator.MvtStkValidator;
import com.gestiondestock.spring.models.TypeMvtStk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MvtStkServiceImpl implements MvtStkServices {
    private MvtStkRepository mvtStkRepository;
    private ArticleServices articleServices;

    @Autowired
    public MvtStkServiceImpl(MvtStkRepository mvtStkRepository, ArticleServices articleServices) {
        this.mvtStkRepository = mvtStkRepository;
        this.articleServices = articleServices;
    }

    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        if(idArticle==null){
            log.warn("l'id de l'article est null");
            return BigDecimal.valueOf(-1);
        }
        articleServices.findById(idArticle);
        return mvtStkRepository.stockReelArticle(idArticle);
    }

    @Override
    public List<MvtStkDAO> mvtStkArticle(Integer id) {
        return mvtStkRepository.findAllByArticleId(id)
                .stream()
                .map(MvtStkDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MvtStkDAO entreeStock(MvtStkDAO mvtStkDAO) {
        List<String> error= MvtStkValidator.validate(mvtStkDAO);
        if(!error.isEmpty()){
            log.error("mvtstk not valid {}",mvtStkDAO);
            throw new InvalidEntityException("le mouvement de stock est non valide", ErrorCodes.MVT_STK_Not_Valid,error);
        }
        mvtStkDAO.setQuantite(BigDecimal.valueOf(Math.abs(mvtStkDAO.getQuantite().doubleValue())));
        mvtStkDAO.setTypeMvt(TypeMvtStk.Entree);
        return MvtStkDAO.fromEntity(mvtStkRepository.save(MvtStkDAO.toEntity(mvtStkDAO)));
    }

    @Override
    public MvtStkDAO sortieStock(MvtStkDAO mvtStkDAO) {
        List<String> error= MvtStkValidator.validate(mvtStkDAO);
        if(!error.isEmpty()){
            log.error("mvtstk not valid {}",mvtStkDAO);
            throw new InvalidEntityException("le mouvement de stock est non valide", ErrorCodes.MVT_STK_Not_Valid,error);
        }
        mvtStkDAO.setQuantite(BigDecimal.valueOf(Math.abs(mvtStkDAO.getQuantite().doubleValue())*(-1)));
        mvtStkDAO.setTypeMvt(TypeMvtStk.Sortie);
        return MvtStkDAO.fromEntity(mvtStkRepository.save(MvtStkDAO.toEntity(mvtStkDAO)));
    }

    @Override
    public MvtStkDAO correctionStockPos(MvtStkDAO mvtStkDAO) {
        List<String> error= MvtStkValidator.validate(mvtStkDAO);
        if(!error.isEmpty()){
            log.error("mouvement not valid {}",mvtStkDAO);
            throw new InvalidEntityException("le mouvement de stock est non valide", ErrorCodes.MVT_STK_Not_Valid,error);
        }
        mvtStkDAO.setQuantite(BigDecimal.valueOf(Math.abs(mvtStkDAO.getQuantite().doubleValue())));
        mvtStkDAO.setTypeMvt(TypeMvtStk.CORRECTION_POS);
        return MvtStkDAO.fromEntity(mvtStkRepository.save(MvtStkDAO.toEntity(mvtStkDAO)));
    }

    @Override
    public List<MvtStkDAO> findAll() {
        return mvtStkRepository.findAll().stream()
                .map(MvtStkDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MvtStkDAO correctionStockNeg(MvtStkDAO mvtStkDAO) {
        List<String> error= MvtStkValidator.validate(mvtStkDAO);
        if(!error.isEmpty()){
            log.error("mouvement not valid {}",mvtStkDAO);
            throw new InvalidEntityException("le mouvement de stock est non valide", ErrorCodes.MVT_STK_Not_Valid,error);
        }
        mvtStkDAO.setQuantite(BigDecimal.valueOf(Math.abs(mvtStkDAO.getQuantite().doubleValue()*-1)));
        mvtStkDAO.setTypeMvt(TypeMvtStk.CORRECTION_NEG);
        return MvtStkDAO.fromEntity(mvtStkRepository.save(MvtStkDAO.toEntity(mvtStkDAO)));
    }
}
