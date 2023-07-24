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
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idArticle")
    private Article article;

    @Column(name = "dateMouvementStock")
    private Instant dateMvt;

    @Column(name = "quantite")
    private BigDecimal quantite;
    @Column(name = "idEntreprise")
    private Integer idEntreprise;
    @Column(name = "typeMvt")
    private TypeMvtStk typeMvt;

    @Column(name = "sourceDeMouvement")
    private SourceMvtStk sourceMvtStk;
}
