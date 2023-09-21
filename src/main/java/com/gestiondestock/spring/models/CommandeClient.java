package com.gestiondestock.spring.models;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class CommandeClient extends AbstractEntity{
    @Column(name = "code")
    private String code;
    @Column(name = "dateCommande")
    private Instant dateCommande;
    @ManyToOne
    @JoinColumn(name = "idclient")
    private Client client;
    @Column(name = "EtatCommande")
    @Enumerated(EnumType.STRING)
    private EtatCommande etatCommande;
    @OneToMany(mappedBy = "commandeClient",cascade = CascadeType.MERGE)
    private List<LigneDeCommandeClient> listeCommandeClient;

    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur;
}
