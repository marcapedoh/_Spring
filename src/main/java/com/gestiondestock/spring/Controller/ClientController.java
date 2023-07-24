package com.gestiondestock.spring.Controller;

import com.gestiondestock.spring.Controller.API.ClientAPI;
import com.gestiondestock.spring.DAO.ClientDAO;
import com.gestiondestock.spring.Services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@CrossOrigin(origins = "*")
public class ClientController implements ClientAPI {
    private ClientServices clientServices;

    @Autowired
    public ClientController(ClientServices clientServices) {
        this.clientServices=clientServices;
    }
    @Override
    public ClientDAO save(ClientDAO clientDAO) {
        return clientServices.save(clientDAO);
    }

    @Override
    public ClientDAO findById(Integer id) {
        return clientServices.findById(id);
    }

    @Override
    public ClientDAO findByNom(String nom) {
        return clientServices.findByNom(nom);
    }

    @Override
    public List<ClientDAO> findAll() {
        return clientServices.findAll();
    }

    @Override
    public void delete(Integer id) {
        clientServices.delete(id);
    }
}
