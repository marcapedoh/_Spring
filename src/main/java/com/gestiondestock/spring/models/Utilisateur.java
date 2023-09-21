package com.gestiondestock.spring.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Utilisateur extends AbstractEntity implements UserDetails {
    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name ="mail")
    private String mail;

    @Column(name = "motDePasse")
    private String motDePasse;

    @Column(name = "photo")
    private String photo;
    @Column(name = "Ville")
    private String ville;
    @Column(name = "codePostale")
    private String codePostale;
    @Column(name = "Pays")
    private String pays;
    @Column(name = "roleUser")
    @Enumerated(EnumType.STRING)
    private ERoles roles;
    @Column(name = "isActived")
    private boolean active;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.name()));
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
