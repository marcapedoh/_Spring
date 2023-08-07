package com.gestiondestock.spring.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Article extends AbstractEntity{
    @Column(name = "idArticle")
    private String codeArticle;

    @Column(name = "Designation")
    private String designation;

    @Column(name = "prixUnitaire")
    private BigDecimal prixUnitaire;

    @Column(name = "tauxTVA")
    private BigDecimal tauxTVA;

    @Column(name = "prixUnitaireTTC")
    private BigDecimal prixUnitaireTTC;
    @Column(name = "photo")
    private String photo;
    @ManyToOne
    @JoinColumn(name = "idCategory")
    private Category category;
    @OneToMany(mappedBy = "article")
    private List<LigneVente> ligneVentes;
    @OneToMany(mappedBy = "article")
    private List<LigneDeCommandeClient> ligneCommandeClients;

    @OneToMany(mappedBy = "article")
    private List<LigneDeCommandeFournisseur> ligneCommandeFournisseurs;

    @OneToMany(mappedBy = "article")
    private List<MvtStk> mvtStks;

    @ManyToOne
    @JoinColumn(name = "nomUtilisateur")
    private Utilisateur utilisateur;
}
