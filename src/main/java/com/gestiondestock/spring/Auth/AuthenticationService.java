package com.gestiondestock.spring.Auth;
import com.gestiondestock.spring.Config.JwtService;
import com.gestiondestock.spring.Exception.EntityNotFoundException;
import com.gestiondestock.spring.Repository.UtilisateurRepository;
import com.gestiondestock.spring.Services.ServiceImpl.UtilisateurServiceImpl;
import com.gestiondestock.spring.models.ERoles;
import com.gestiondestock.spring.models.Utilisateur;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request, String role) {
        var user= Utilisateur.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .mail(request.getMail())
                .photo(request.getPhoto())
                .ville(request.getVille())
                .pays(request.getPays())
                .codePostale(request.getCodePostale())
                .roles(ERoles.valueOf(role))
                .active(true)
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .build();
        utilisateurRepository.save(user);
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        var user= Utilisateur.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .mail(request.getMail())
                .photo(request.getPhoto())
                .ville(request.getVille())
                .pays(request.getPays())
                .codePostale(request.getCodePostale())
                .active(true)
                .roles(ERoles.ROLE_ADMIN)
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .build();
        utilisateurRepository.save(user);
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        UserDetails user=utilisateurRepository.findUtilisateurByMail(request.getEmail()).orElseThrow(()-> new EntityNotFoundException("Aucun utilisateur n'es trouvé"));
        if(!StringUtils.hasLength(user.getUsername())){
            log.warn("le mail de ce user est nulle voila toi mm regarde: "+user.getUsername());
        }
        String token=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
