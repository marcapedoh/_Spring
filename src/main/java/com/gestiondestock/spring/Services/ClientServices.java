package com.gestiondestock.spring.Services;

import com.gestiondestock.spring.DAO.ClientDAO;

import java.util.List;

public interface ClientServices {
    ClientDAO save(ClientDAO clientDAO);
    ClientDAO findById(Integer id);
    ClientDAO findByNom(String nom);
    List<ClientDAO> findAll();
    void delete(Integer id);
}
