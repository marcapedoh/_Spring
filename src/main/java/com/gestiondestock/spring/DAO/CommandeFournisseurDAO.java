package com.gestiondestock.spring.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestiondestock.spring.models.CommandeFournisseur;
import com.gestiondestock.spring.models.EtatCommande;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandeFournisseurDAO {
    private Integer id;
    private String code;
    private Instant dateCommande;
    private EtatCommande etatCommande;
    private FournisseurDAO fournisseur;

    private List<LigneDeCommandeFournisseurDAO> ligneCommandeFournisseurs;

    public static CommandeFournisseurDAO fromEntity(CommandeFournisseur commandeFournisseur){
        if(commandeFournisseur==null){
            return null;
        }
        return CommandeFournisseurDAO.builder()
                .id(commandeFournisseur.getId())
                .code(commandeFournisseur.getCode())
                .etatCommande(commandeFournisseur.getEtatCommande())
                .dateCommande((commandeFournisseur.getDateCommande()))
                .fournisseur(FournisseurDAO.fromEntity(commandeFournisseur.getFournisseur()))
                .build();
    }

    public static CommandeFournisseur toEntity(CommandeFournisseurDAO commandeFournisseurDAO){
        if(commandeFournisseurDAO==null){
            return null;
        }
        CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
        commandeFournisseur.setId(commandeFournisseurDAO.getId());
        commandeFournisseur.setCode(commandeFournisseurDAO.getCode());
        commandeFournisseur.setDateCommande(commandeFournisseurDAO.getDateCommande());
        commandeFournisseur.setFournisseur(FournisseurDAO.toEntity(commandeFournisseurDAO.getFournisseur()));
        commandeFournisseur.setEtatCommande(commandeFournisseurDAO.getEtatCommande());
        return commandeFournisseur;
    }
    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }

}
