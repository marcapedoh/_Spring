package com.gestiondestock.spring.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class MvtStk extends AbstractEntity{
    @ManyToOne
    @JoinColumn(name = "idArticle")
    private Article article;

    @Column(name = "dateMouvementStock")
    private Instant dateMvt;

    @Column(name = "quantite")
    private BigDecimal quantite;
    @Column(name = "typeMvt")
    @Enumerated(EnumType.STRING)
    private TypeMvtStk typeMvt;

    @Column(name = "sourceDeMouvement")
    @Enumerated(EnumType.STRING)
    private SourceMvtStk sourceMvtStk;
    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur;
}
