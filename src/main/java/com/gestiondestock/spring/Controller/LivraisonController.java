package com.gestiondestock.spring.Controller;

import com.gestiondestock.spring.Controller.API.LivraisonAPI;
import com.gestiondestock.spring.DAO.LivraisonDAO;
import com.gestiondestock.spring.Services.LivraisonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@CrossOrigin(origins = "*")
public class LivraisonController implements LivraisonAPI {
    private LivraisonServices livraisonServices;
    @Autowired
    public LivraisonController(LivraisonServices livraisonServices) {
        this.livraisonServices = livraisonServices;
    }

    @Override
    public LivraisonDAO save(LivraisonDAO livraisonDAO) {
        return this.livraisonServices.save(livraisonDAO);
    }

    @Override
    public LivraisonDAO findByCode(String code) {
        return this.livraisonServices.findByCode(code);
    }

    @Override
    public List<LivraisonDAO> findAll() {
        return this.livraisonServices.findAll();
    }

    @Override
    public void delete(Integer idLivraison) {
        this.livraisonServices.delete(idLivraison);
    }
}
