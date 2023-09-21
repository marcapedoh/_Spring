package com.gestiondestock.spring.models;

import com.gestiondestock.spring.Repository.LivraisonRepository;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class CommandeFournisseur extends AbstractEntity{
    @Column(name = "code")
    private String code;
    @Column(name = "dateCommande")
    private Instant dateCommande;
    @ManyToOne
    @JoinColumn(name = "idfournisseur")
    private Fournisseur fournisseur;
    @Column(name = "EtatCommande")
    @Enumerated(EnumType.STRING)
    private EtatCommande etatCommande;
    @OneToMany(mappedBy = "commandeFournisseur")
    private List<Livraison> livraison;
    @OneToMany(mappedBy = "commandeFournisseurs",cascade = CascadeType.MERGE)
    private List<LigneDeCommandeFournisseur> ligneCommandeFournisseurs;
    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur;

}
