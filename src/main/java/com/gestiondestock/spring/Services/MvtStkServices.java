package com.gestiondestock.spring.Services;

import com.gestiondestock.spring.DAO.MvtStkDAO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

public interface MvtStkServices {
    BigDecimal stockReelArticle(Integer idArticle);
    List<MvtStkDAO> mvtStkArticle(Integer id);
    MvtStkDAO entreeStock(MvtStkDAO mvtStkDAO);
    MvtStkDAO sortieStock(MvtStkDAO mvtStkDAO);

    MvtStkDAO correctionStockPos(MvtStkDAO mvtStkDAO);

    MvtStkDAO correctionStockNeg(MvtStkDAO mvtStkDAO);
}
