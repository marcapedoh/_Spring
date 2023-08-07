package com.gestiondestock.spring.models;

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
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idFournisseur")
    private Fournisseur fournisseur;
    @Column(name = "EtatCommande")
    /*@Enumerated(EnumType.STRING)*/
    private EtatCommande etatCommande;
    @OneToMany(mappedBy = "commandeFournisseurs")
    private List<LigneDeCommandeFournisseur> ligneCommandeFournisseurs;

}
