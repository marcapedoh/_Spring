package com.gestiondestock.spring.Services;

import com.gestiondestock.spring.DAO.LivraisonDAO;
import com.gestiondestock.spring.models.Livraison;

import java.util.List;

public interface LivraisonServices {
    LivraisonDAO save(LivraisonDAO livraisonDAO);
    LivraisonDAO findByCode(String code);

    List<LivraisonDAO> findAll();
    void delete(Integer idLivraison);
}
