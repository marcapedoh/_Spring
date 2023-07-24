package com.gestiondestock.spring.DAO;

import com.gestiondestock.spring.models.EtatCommande;
import com.gestiondestock.spring.models.MvtStk;
import com.gestiondestock.spring.models.SourceMvtStk;
import com.gestiondestock.spring.models.TypeMvtStk;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MvtStkDAO {
    private Integer id;
    private ArticleDAO article;
    private Instant dateMvt;
    private BigDecimal quantite;
    private TypeMvtStk typeMvt;
    private SourceMvtStk sourceMvtStk;

    public static MvtStkDAO fromEntity(MvtStk mvtStk){
        if(mvtStk==null){
            return null;
        }
        return MvtStkDAO.builder()
                .id(mvtStk.getId())
                .article(ArticleDAO.fromEntity(mvtStk.getArticle()))
                .dateMvt(mvtStk.getDateMvt())
                .typeMvt(mvtStk.getTypeMvt())
                .sourceMvtStk(mvtStk.getSourceMvtStk())
                .quantite(mvtStk.getQuantite())
                .build();
    }
    public static MvtStk toEntity(MvtStkDAO mvtStkDAO){
        if(mvtStkDAO==null){
            return null;
        }
        return MvtStk.builder()
                .article(ArticleDAO.toEntity(mvtStkDAO.getArticle()))
                .dateMvt(mvtStkDAO.getDateMvt())
                .typeMvt(mvtStkDAO.getTypeMvt())
                .sourceMvtStk(mvtStkDAO.getSourceMvtStk())
                .quantite(mvtStkDAO.getQuantite())
                .build();
    }
    public boolean isCommandeLivreePartiellement() {
        return TypeMvtStk.ENTREE_PARTIEL.equals(this.typeMvt);
    }
}
