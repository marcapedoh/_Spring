package com.gestiondestock.spring.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestiondestock.spring.models.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDAO {
    private Integer id;
    private String nom;
    private  String prenom;
    private String ville;
    private String codePostale;
    private String pays;
    private String email;
    private String photo;
    private String numTel;

    @JsonIgnore
    private List<CommandeClientDAO> listeCommande;

    public static ClientDAO fromEntity(Client client){
        if(client==null){
            return null;
        }
        return ClientDAO.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .ville(client.getVille())
                .codePostale(client.getCodePostale())
                .pays(client.getPays())
                .email(client.getEmail())
                .photo(client.getPhoto())
                .numTel(client.getNumTel())
                .build();
    }
    public static Client toEntity(ClientDAO clientDAO){
        if(clientDAO==null){
            return null;
        }
        Client client=new Client();
        client.setId(clientDAO.getId());
        client.setNom(clientDAO.getNom());
        client.setPrenom(clientDAO.getPrenom());
        client.setEmail(clientDAO.getEmail());
        client.setPays(clientDAO.getPays());
        client.setCodePostale(clientDAO.getCodePostale());
        client.setPhoto(clientDAO.getPhoto());
        client.setVille(clientDAO.getVille());
        client.setNumTel(clientDAO.getNumTel());
        return client;
    }
}
