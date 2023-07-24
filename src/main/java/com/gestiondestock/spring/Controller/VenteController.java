package com.gestiondestock.spring.Controller;

import com.gestiondestock.spring.Controller.API.VenteAPI;
import com.gestiondestock.spring.DAO.VenteDAO;
import com.gestiondestock.spring.Services.VenteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class VenteController implements VenteAPI {
    private VenteServices venteServices;
    @Autowired
    public VenteController(VenteServices venteServices){
        this.venteServices=venteServices;
    }

    @Override
    public VenteDAO save(VenteDAO venteDAO) {
        return venteServices.save(venteDAO);
    }
    @Override
    public VenteDAO findById(Integer id) {
        return venteServices.findById(id);
    }

    @Override
    public VenteDAO findByCode(String code) {
        return venteServices.findByCode(code);
    }

    @Override
    public List<VenteDAO> findAll() {
        return venteServices.findAll();
    }

    @Override
    public void delete(Integer id) {
        venteServices.delete(id);
    }
}
