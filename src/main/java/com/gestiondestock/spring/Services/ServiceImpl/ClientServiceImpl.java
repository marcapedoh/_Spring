package com.gestiondestock.spring.Services.ServiceImpl;

import com.gestiondestock.spring.DAO.CategoryDAO;
import com.gestiondestock.spring.DAO.ClientDAO;
import com.gestiondestock.spring.Exception.EntityNotFoundException;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidEntityException;
import com.gestiondestock.spring.Exception.InvalidOperationException;
import com.gestiondestock.spring.Repository.ClientRepository;
import com.gestiondestock.spring.Repository.CommandeClientRepository;
import com.gestiondestock.spring.Services.ClientServices;
import com.gestiondestock.spring.Validator.ClientValidator;
import com.gestiondestock.spring.models.Client;
import com.gestiondestock.spring.models.CommandeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@EnableWebMvc
@EnableAutoConfiguration
public class ClientServiceImpl implements ClientServices {
    private ClientRepository clientRepository;
    private CommandeClientRepository commandeClientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRpository,CommandeClientRepository commandeClientRepository){
        this.clientRepository=clientRpository;
        this.commandeClientRepository=commandeClientRepository;
    }
    @Override
    public ClientDAO save(ClientDAO clientDAO) {
        List<String>errors= ClientValidator.validate(clientDAO);
        if(!errors.isEmpty()){
            log.error("le client {} est le null",clientDAO);
            throw new InvalidEntityException("le client n'est op valide", ErrorCodes.Client_Not_Valid,errors);
        }
        return ClientDAO.fromEntity(
                clientRepository.save(
                        ClientDAO.toEntity(clientDAO)
                )
        );
    }

    @Override
    public ClientDAO findById(Integer id) {
        if(id==null){
            log.error("l'id ne peut pas être null");
        }
        Optional<Client> client=clientRepository.findById(id);
        return client.map(ClientDAO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("aucune category ne correspond à votre id passer en parametre", ErrorCodes.Category_Not_Found));

    }

    @Override
    public ClientDAO findByNom(String nom) {
        if(!StringUtils.hasLength(nom)){
            log.error("le nom pour la recherche ne doit opas être null");
        }
        Optional<Client> client=clientRepository.findClientByNom(nom);
        return client.map(ClientDAO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("aucune category ne correspond à votre nom passer en parametre", ErrorCodes.Category_Not_Found));

    }

    @Override
    public List<ClientDAO> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error("veuillez entrez un identifiant ");
        }
        List<CommandeClient> commandeClients = commandeClientRepository.findAllByClientId(id);
        if (!commandeClients.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un client qui a deja des commande clients",
                    ErrorCodes.CLIENT_ALREADY_IN_USE);
        }
        clientRepository.deleteById(id);
    }
}
