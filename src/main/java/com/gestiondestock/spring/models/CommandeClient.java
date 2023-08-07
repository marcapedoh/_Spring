package com.gestiondestock.spring.models;

import jakarta.persistence.*;
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
    @CreatedDate
    @Column(name = "dateCommande")
    private Instant dateCommande;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idclient")
    private Client client;
    @Column(name = "EtatCommande")
    /*@Enumerated(EnumType.STRING)*/
    private EtatCommande etatCommande;
    @OneToMany(mappedBy = "commandeClient")
    private List<LigneDeCommandeClient> listeCommandeClient;
}
