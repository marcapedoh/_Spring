package com.gestiondestock.spring.Controller;

import com.gestiondestock.spring.Controller.API.MvtStkAPI;
import com.gestiondestock.spring.DAO.MvtStkDAO;
import com.gestiondestock.spring.Services.MvtStkServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
@RestController
@CrossOrigin(origins = "*")
public class MvtStkController implements MvtStkAPI {
    private MvtStkServices mvtStkServices;
    @Autowired
    public MvtStkController(MvtStkServices mvtStkServices) {
        this.mvtStkServices = mvtStkServices;
    }

    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        return this.mvtStkServices.stockReelArticle(idArticle);
    }

    @Override
    public List<MvtStkDAO> mvtStkArticle(Integer id) {
        return this.mvtStkServices.mvtStkArticle(id);
    }

    @Override
    public MvtStkDAO entreeStock(MvtStkDAO mvtStkDAO) {
        return this.mvtStkServices.entreeStock(mvtStkDAO);
    }

    @Override
    public MvtStkDAO sortieStock(MvtStkDAO mvtStkDAO) {
        return this.mvtStkServices.sortieStock(mvtStkDAO);
    }

    @Override
    public MvtStkDAO correctionStockPos(MvtStkDAO mvtStkDAO) {
        return this.mvtStkServices.correctionStockPos(mvtStkDAO);
    }

    @Override
    public List<MvtStkDAO> findAll() {
        return this.mvtStkServices.findAll();
    }

    @Override
    public MvtStkDAO correctionStockNeg(MvtStkDAO mvtStkDAO) {
        return this.mvtStkServices.correctionStockNeg(mvtStkDAO);
    }
}
