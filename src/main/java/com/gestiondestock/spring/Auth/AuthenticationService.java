package com.gestiondestock.spring.Auth;
import com.gestiondestock.spring.Config.JwtService;
import com.gestiondestock.spring.Repository.UtilisateurRepository;
import com.gestiondestock.spring.Services.ServiceImpl.UtilisateurServiceImpl;
import com.gestiondestock.spring.models.ERoles;
import com.gestiondestock.spring.models.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UtilisateurRepository utilisateurRepository;
    private  final UtilisateurServiceImpl utilisateurService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var user= Utilisateur.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .dateNaissance(request.getDateNaissance())
                .mail(request.getMail())
                .photo(request.getPhoto())
                .ville(request.getVille())
                .pays(request.getPays())
                .codePostale(request.getCodePostale())
                .roles(ERoles.ROLE_USER)
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .build();
        utilisateurRepository.save(user);
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
            );
        }catch (AuthenticationException exception){
            throw new BadCredentialsException("Identifiants invalides.");
        }
        var user=utilisateurRepository.findUtilisateurByMailAndMotDePasse(request.getEmail(),encodedPassword).orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        System.out.println(jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
