package com.gestiondestock.spring.Repository;

import com.gestiondestock.spring.models.LigneDeCommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneDeCommandeClientRepository extends JpaRepository<LigneDeCommandeClient,Integer> {
    List<LigneDeCommandeClient> findAllByCommandeClientId(Integer integer);
    List<LigneDeCommandeClient> findAllByArticleId(Integer id);
}
