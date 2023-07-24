package com.gestiondestock.spring.Services;

import com.gestiondestock.spring.DAO.LigneDeCommandeClientDAO;

import java.util.List;

public interface LigneDeCommandeClientServices {
    LigneDeCommandeClientDAO save(LigneDeCommandeClientDAO ligneDeCommandeClientDAO);
    LigneDeCommandeClientDAO findById(Integer id);
    List<LigneDeCommandeClientDAO> findAll();
    void delete(Integer id);
}
