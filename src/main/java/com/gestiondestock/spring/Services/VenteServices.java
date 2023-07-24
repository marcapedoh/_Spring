package com.gestiondestock.spring.Services;

import com.gestiondestock.spring.DAO.VenteDAO;

import java.util.List;

public interface VenteServices {
    VenteDAO save(VenteDAO venteDAO);
    VenteDAO findById(Integer id);
    VenteDAO findByCode(String code);
    List<VenteDAO> findAll();
    void delete(Integer id);
}
