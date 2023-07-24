package com.gestiondestock.spring.Repository;

import com.gestiondestock.spring.models.MvtStk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MvtStkRepository extends JpaRepository<MvtStk,Integer> {
    @Query("select sum(m.quantite) from MvtStk m where m.article.id= :idArticle")
    BigDecimal stockReelArticle(@Param("idArticle") Integer idArticle);
    List<MvtStk> findAllByArticleId(Integer idArticle);
}
