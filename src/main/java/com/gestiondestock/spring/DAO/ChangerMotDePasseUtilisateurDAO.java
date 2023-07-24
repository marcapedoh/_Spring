package com.gestiondestock.spring.DAO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChangerMotDePasseUtilisateurDAO {
    private Integer id;
    private String motDePasse;
    private String confirmMotDePasse;
}
