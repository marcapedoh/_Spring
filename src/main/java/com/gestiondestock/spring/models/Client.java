package com.gestiondestock.spring.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Client extends AbstractEntity{
    @Column(name = "nomClient")
    private String nom;
    @Column(name = "prenom")
    private  String prenom;

    @Column(name = "ville")
    private String ville;
    @Column(name = "codePostale")
    private String codePostale;
    @Column(name = "Pays")
    private String pays;

    @Column(name = "email")
    private String email;
    @Column(name = "photo")
    private String photo;
    @Column(name = "numTel")
    private String numTel;
    @OneToMany(mappedBy = "client")
    private List<CommandeClient> listeCommande;
}
