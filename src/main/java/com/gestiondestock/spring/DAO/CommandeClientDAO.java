package com.gestiondestock.spring.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestiondestock.spring.models.Client;
import com.gestiondestock.spring.models.CommandeClient;
import com.gestiondestock.spring.models.EtatCommande;
import com.gestiondestock.spring.models.LigneDeCommandeClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandeClientDAO {
    private Integer id;
    private String code;
    private Instant dateCommande;
    private ClientDAO client;

    private EtatCommande etatCommande;

    private List<LigneDeCommandeClientDAO> listeCommandeClient;

    public static CommandeClientDAO fromEntity(CommandeClient commandeClient){
        if(commandeClient==null){
            return  null;
        }
        List<LigneDeCommandeClientDAO> ligneCommandeClientDAOList = new ArrayList<>();
        if (commandeClient.getListeCommandeClient() != null) {
            for (LigneDeCommandeClient ligneCommandeClient : commandeClient.getListeCommandeClient()) {
                ligneCommandeClientDAOList.add(LigneDeCommandeClientDAO.fromEntity(ligneCommandeClient));
            }
        }
            return CommandeClientDAO.builder()
                .id(commandeClient.getId())
                .code(commandeClient.getCode())
                .dateCommande(commandeClient.getDateCommande())
                .etatCommande(commandeClient.getEtatCommande())
                .client(ClientDAO.fromEntity(commandeClient.getClient()))
                .listeCommandeClient(ligneCommandeClientDAOList)
                .build();
    }
    public static CommandeClient toEntity(CommandeClientDAO commandeClientDAO){
        if(commandeClientDAO==null){
            return null;
        }
        List<LigneDeCommandeClient> ligneCommandeClientList = new ArrayList<>();
        if (commandeClientDAO.getListeCommandeClient() != null) {
            for (LigneDeCommandeClientDAO ligneCommandeClient : commandeClientDAO.getListeCommandeClient()) {
                ligneCommandeClientList.add(LigneDeCommandeClientDAO.toEntity(ligneCommandeClient));
            }
        }
        CommandeClient commandeClient = new CommandeClient();
        commandeClient.setId(commandeClientDAO.getId());
        commandeClient.setCode(commandeClientDAO.getCode());
        commandeClient.setClient(ClientDAO.toEntity(commandeClientDAO.getClient()));
        commandeClient.setDateCommande(commandeClientDAO.getDateCommande());
        commandeClient.setEtatCommande(commandeClientDAO.getEtatCommande());
        commandeClient.setListeCommandeClient(ligneCommandeClientList);
        return commandeClient;
    }
    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
    public boolean isCommandeValidee() {
        return EtatCommande.VALIDE.equals(this.etatCommande);
    }
}
