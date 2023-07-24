package com.gestiondestock.spring.Services;

import com.gestiondestock.spring.DAO.FournisseurDAO;

import java.util.List;

public interface FournisseurServices {
    FournisseurDAO save(FournisseurDAO fournisseurDAO);

    FournisseurDAO findById(Integer id);
    FournisseurDAO findByNom(String nom);
    List<FournisseurDAO> findAll();
    void delete(Integer id);
}
