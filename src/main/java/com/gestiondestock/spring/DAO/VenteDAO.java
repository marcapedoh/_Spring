package com.gestiondestock.spring.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestiondestock.spring.models.Vente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenteDAO {
    private Integer id;
    private String code;
    private Instant dateVente;
    private String commentaire;

    private List<LigneVenteDAO> ligneVente;

    public static VenteDAO fromEntity(Vente vente){
        if(vente==null){
            return null;
        }
        return VenteDAO.builder()
                .id(vente.getId())
                .code(vente.getCode())
                .dateVente(Instant.now())
                .commentaire(vente.getCommentaire())
                .build();
    }
    public static Vente toEntity(VenteDAO venteDAO){
        if(venteDAO==null){
            return null;
        }
        Vente vente=new Vente();
        vente.setId(venteDAO.getId());
        vente.setCode(venteDAO.getCode());
        vente.setDateVente(Instant.now());
        vente.setCommentaire(venteDAO.getCommentaire());
        return vente;
    }
}
