package com.gestiondestock.spring.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Livraison extends AbstractEntity{
    @Column(name = "codeLivraison")
    private String code;
    @Column(name = "commentaire",nullable = true)
    private String commentaire;
    @Column(name = "DateLivraison")
    private Instant dateLivraison;
    @Column(name = "TypeLivraison")
    @Enumerated(EnumType.STRING)
    private TypeMvtStk typeMvtStk;
    @Column(name = "quantite")
    private BigDecimal quantite;
    @ManyToOne
    @JoinColumn(name = "idFournisseur")
    private Fournisseur fournisseur;
    @ManyToOne
    @JoinColumn(name = "idcommandeFournisseur")
    private CommandeFournisseur commandeFournisseur;
}
