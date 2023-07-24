package com.gestiondestock.spring.Services;

import com.gestiondestock.spring.DAO.LigneDeCommandeClientDAO;
import com.gestiondestock.spring.DAO.LigneDeCommandeFournisseurDAO;
import com.gestiondestock.spring.models.LigneDeCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneDeCommandeFournisseurServices {
    LigneDeCommandeFournisseurDAO save(LigneDeCommandeFournisseurDAO ligneDeCommandeClientDAO);
    LigneDeCommandeFournisseurDAO findById(Integer id);
    List<LigneDeCommandeFournisseurDAO> findAll();
    void delete(Integer id);
}
