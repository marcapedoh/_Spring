package com.gestiondestock.spring.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestiondestock.spring.models.Client;
import com.gestiondestock.spring.models.CommandeClient;
import com.gestiondestock.spring.models.EtatCommande;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
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
    @JsonIgnore
    private List<LigneDeCommandeClientDAO> listeCommandeClient;

    public static CommandeClientDAO fromEntity(CommandeClient commandeClient){
        if(commandeClient==null){
            return  null;
        }
        return CommandeClientDAO.builder()
                .id(commandeClient.getId())
                .code(commandeClient.getCode())
                .dateCommande(commandeClient.getDateCommande())
                .etatCommande(commandeClient.getEtatCommande())
                .client(ClientDAO.fromEntity(commandeClient.getClient()))
                .build();
    }
    public static CommandeClient toEntity(CommandeClientDAO commandeClientDAO){
        if(commandeClientDAO==null){
            return null;
        }
        return CommandeClient.builder()
                .code(commandeClientDAO.getCode())
                .dateCommande(commandeClientDAO.getDateCommande())
                .client(ClientDAO.toEntity(commandeClientDAO.getClient()))
                .etatCommande(commandeClientDAO.getEtatCommande())
                .build();
    }
    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
    public boolean isCommandeValidee() {
        return EtatCommande.VALIDE.equals(this.etatCommande);
    }
}
