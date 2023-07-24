package com.gestiondestock.spring.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String mail;
    private  String photo;
    private String motDePasse;
    private String ville ;
    private String pays;
    private String codePostale;
}
