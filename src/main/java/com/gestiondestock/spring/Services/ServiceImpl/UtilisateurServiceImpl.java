package com.gestiondestock.spring.Services.ServiceImpl;

import com.gestiondestock.spring.DAO.ChangerMotDePasseUtilisateurDAO;
import com.gestiondestock.spring.DAO.UtilisateurDAO;
import com.gestiondestock.spring.Exception.EntityNotFoundException;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidEntityException;
import com.gestiondestock.spring.Exception.InvalidOperationException;
import com.gestiondestock.spring.Repository.UtilisateurRepository;
import com.gestiondestock.spring.Services.UtilisateurServices;
import com.gestiondestock.spring.Validator.UtilisateurValidator;
import com.gestiondestock.spring.models.Utilisateur;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@EnableWebMvc
public class UtilisateurServiceImpl implements UtilisateurServices {
    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public  UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository,PasswordEncoder passwordEncoder){
        this.utilisateurRepository=utilisateurRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public UtilisateurDAO save(UtilisateurDAO utilisateurDAO) {
        List<String> errors= UtilisateurValidator.validate(utilisateurDAO);
        if(!errors.isEmpty()){
            log.error("on ne peut pas enregistrer un utilisateur {} qui est vide",utilisateurDAO);
            throw new InvalidEntityException("l'utilisateur est null ce qui renvoi une entité invalid",ErrorCodes.Utilisateur_Not_Valid,errors);
        }
        if(userAlreadyExists(utilisateurDAO.getMail())) {
            throw new InvalidEntityException("Un autre utilisateur avec le même email existe deja", ErrorCodes.UTILISATEUR_ALREADY_EXISTS,
                    Collections.singletonList("Un autre utilisateur avec le meme email existe deja dans la BDD"));
        }
        utilisateurDAO.setMotDePasse(passwordEncoder.encode(utilisateurDAO.getMotDePasse()));
        return UtilisateurDAO.fromEntity(
                utilisateurRepository.save(
                        UtilisateurDAO.toEntity(utilisateurDAO)
                )
        );
    }
    private boolean userAlreadyExists(String email) {
        Optional<Utilisateur> user = utilisateurRepository.findUtilisateurByMail(email);
        return user.isPresent();
    }
    @Override
    public UtilisateurDAO findById(Integer id) {
        if(id==null){
            log.error("vous ne pouvez pas rechercher un utilisteur en passant en parametre un id null");
        }
        Optional<Utilisateur>utilisateur=utilisateurRepository.findById(id);
        /*return Optional.of(UtilisateurDAO.fromEntity(utilisateur.get())).orElseThrow(() ->
                new EntityNotFoundException("Aucun utilisateur ne correspond à l'id passer en parametre",ErrorCodes.Utilisateur_Not_Found));
*/
        return utilisateur.map(UtilisateurDAO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("aucun utilisateur ne correspond à votre id passer en parametre", ErrorCodes.Utilisateur_Not_Found));

    }

    @Override
    public UtilisateurDAO findByNom(String nom) {
        if(!StringUtils.hasLength(nom)){
            log.error("vous ne pouvez pas rechercher un utilisteur en passant en parametre un nom null");
        }
        Optional<Utilisateur>utilisateur=utilisateurRepository.findUtilisateurByNom(nom);
        return utilisateur.map(UtilisateurDAO::fromEntity).orElseThrow(()-> new EntityNotFoundException("aucun utilisateur ne correspond à votre nom passer en parametre", ErrorCodes.Utilisateur_Not_Found));
    }

    @Override
    public UtilisateurDAO findByMailAndMotDePasse(String mail, String password) {
        if(!StringUtils.hasLength(mail) && !StringUtils.hasLength(password)){
            log.error("pour vous connectez vous avez besoin de fournir ces informations");
        }
        Optional<Utilisateur>utilisateur=utilisateurRepository.findUtilisateurByMail(mail);
        utilisateur.ifPresent(value -> passwordEncoder.matches(password, value.getPassword()));
        //Optional<Utilisateur>utilisateur=utilisateurRepository.findUtilisateurByMailAndMotDePasse(mail,passwordEncoded);
        /*return Optional.of(UtilisateurDAO.fromEntity(utilisateur.get())).orElseThrow(()->
                new EntityNotFoundException("Aucun utilisateur n'a ces informations "));*/
        return utilisateur.map(UtilisateurDAO::fromEntity).orElseThrow(()-> new EntityNotFoundException("aucun utilisateur ne correspond à votre mail et mot de passe passer en parametre", ErrorCodes.Utilisateur_Not_Found));
    }

    @Override
    public UtilisateurDAO findByMail(String mail) {
        if(!StringUtils.hasLength(mail)){
            log.error("pour vous connectez vous avez besoin de fournir votre mail");
        }
        Optional<Utilisateur>utilisateur=utilisateurRepository.findUtilisateurByMail(mail);
        /*return Optional.of(UtilisateurDAO.fromEntity(utilisateur.get())).orElseThrow(()->
                new EntityNotFoundException("Aucun utilisateur n'a ce adresse mail",ErrorCodes.Utilisateur_Not_Found));*/
        return utilisateur.map(UtilisateurDAO::fromEntity).orElseThrow(()-> new EntityNotFoundException("Aucun utilisateur n'a ce adresse mail",ErrorCodes.Utilisateur_Not_Found));
    }

    @Override
    public List<UtilisateurDAO> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error("vous ne pouvez pas chercher un utilisateur si l'id est null");
        }
        utilisateurRepository.deleteById(id);
    }

    @Override
    public UtilisateurDAO changerMotDePasse(ChangerMotDePasseUtilisateurDAO dto) {
        validate(dto);
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(dto.getId());
        if (utilisateurOptional.isEmpty()) {
            log.warn("Aucun utilisateur n'a ete trouve avec l'ID " + dto.getId());
            throw new EntityNotFoundException("Aucun utilisateur n'a ete trouve avec l'ID " + dto.getId(), ErrorCodes.Utilisateur_Not_Found);
        }
        Utilisateur utilisateur = utilisateurOptional.get();
        utilisateur.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));

        return UtilisateurDAO.fromEntity(
                utilisateurRepository.save(utilisateur)
        );
    }

    @Override
    public void desactiverUser(Integer id) {
        Optional<Utilisateur> optionalUser = utilisateurRepository.findById(id);
        if (optionalUser.isPresent()) {
            Utilisateur user = optionalUser.get();
            user.setActive(false);
            utilisateurRepository.save(user);
        } else {
            throw new EntityNotFoundException("Utilisateur introuvable avec l'ID : " + id);
        }
    }

    @Override
    public void activerUser(Integer id) {
        Optional<Utilisateur> user=utilisateurRepository.findById(id);
        if(user.isPresent()){
           Utilisateur user1= user.get();
            user1.setActive(true);
            utilisateurRepository.save(user1);
        }else{
            throw new EntityNotFoundException("Utilisateur introuvable avec l'ID : " + id);
        }
    }

    private void validate(ChangerMotDePasseUtilisateurDAO dto) {
        if (dto == null) {
            log.warn("Impossible de modifier le mot de passe avec un objet NULL");
            throw new InvalidOperationException("Aucune information n'a ete fourni pour pouvoir changer le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (dto.getId() == null) {
            log.warn("Impossible de modifier le mot de passe avec un ID NULL");
            throw new InvalidOperationException("ID utilisateur null:: Impossible de modifier le mote de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (!StringUtils.hasLength(dto.getMotDePasse()) || !StringUtils.hasLength(dto.getConfirmMotDePasse())) {
            log.warn("Impossible de modifier le mot de passe avec un mot de passe NULL");
            throw new InvalidOperationException("Mot de passe utilisateur null:: Impossible de modifier le mote de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (!dto.getMotDePasse().equals(dto.getConfirmMotDePasse())) {
            log.warn("Impossible de modifier le mot de passe avec deux mots de passe different");
            throw new InvalidOperationException("Mots de passe utilisateur non conformes:: Impossible de modifier le mote de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
    }
}
