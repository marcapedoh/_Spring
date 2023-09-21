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
public class Vente extends AbstractEntity{
    @Column(name = "codeVente")
    private String code;
    @Column(name = "dateVente")
    private Instant dateVente;
    @Column(name = "commentaire")
    private String commentaire;
    @OneToMany(mappedBy = "ventes",cascade = CascadeType.ALL)
    private List<LigneVente>ligneVentes;
    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur;
}
