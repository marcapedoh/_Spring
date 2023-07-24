package com.gestiondestock.spring.Services;

import com.gestiondestock.spring.DAO.LigneVenteDAO;

import java.util.List;

public interface LigneVenteServices {
    LigneVenteDAO save(LigneVenteDAO ligneVenteDAO);
    LigneVenteDAO findById(Integer id);
    List<LigneVenteDAO> findAll();
    void delete(Integer id);
}
