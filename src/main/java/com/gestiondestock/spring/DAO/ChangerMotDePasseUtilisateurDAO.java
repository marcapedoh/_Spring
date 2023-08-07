package com.gestiondestock.spring.DAO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangerMotDePasseUtilisateurDAO {
    private Integer id;
    private String motDePasse;
    private String confirmMotDePasse;
}
