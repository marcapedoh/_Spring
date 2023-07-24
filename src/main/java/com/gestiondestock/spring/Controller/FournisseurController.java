package com.gestiondestock.spring.Controller;

import com.gestiondestock.spring.Controller.API.FournisseurAPI;
import com.gestiondestock.spring.DAO.FournisseurDAO;
import com.gestiondestock.spring.Services.FournisseurServices;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class FournisseurController implements FournisseurAPI {
    private FournisseurServices fournisseurServices;

    public FournisseurController(FournisseurServices fournisseurServices){
        this.fournisseurServices=fournisseurServices;
    }

    @Override
    public FournisseurDAO save(FournisseurDAO fournisseurDAO) {
        return fournisseurServices.save(fournisseurDAO);
    }

    @Override
    public FournisseurDAO findById(Integer id) {
        return fournisseurServices.findById(id);
    }

    @Override
    public FournisseurDAO findByNom(String nom) {
        return fournisseurServices.findByNom(nom);
    }

    @Override
    public List<FournisseurDAO> findAll() {
        return fournisseurServices.findAll();
    }

    @Override
    public void delete(Integer id) {
        fournisseurServices.delete(id);
    }
}
