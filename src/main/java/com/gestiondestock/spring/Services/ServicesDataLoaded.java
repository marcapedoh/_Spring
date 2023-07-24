package com.gestiondestock.spring.Services;

import com.gestiondestock.spring.Repository.ArticleRepository;
import com.gestiondestock.spring.models.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ServicesDataLoaded {
    private JdbcTemplate jdbcTemplate;
    private ArticleRepository articleRepository;
    @Autowired
    public ServicesDataLoaded(JdbcTemplate jdbcTemplate,ArticleRepository articleRepository){
        this.jdbcTemplate=jdbcTemplate;
        this.articleRepository=articleRepository ;
    }

    public int RetourNbCommandeEnUnmois(){
        String requeteSql="SELECT COUNT(*) AS total_Commande FROM Commande_client WHERE creation_date BETWEEN CURRENT_DATE() - INTERVAL 1 MONTH AND CURRENT_DATE();";
        log.warn("retour du nombre des clients dans le mois precedent en bd "+jdbcTemplate.queryForObject(requeteSql, Integer.class));
        return jdbcTemplate.queryForObject(requeteSql, Integer.class);
    }
    public int RetourAVGClientPerCommande(){
        String nombreTotalCommandeSql="SELECT COUNT(*) AS totalCommande from commande_client;";
        int retour1= jdbcTemplate.queryForObject(nombreTotalCommandeSql, Integer.class);
        log.warn("retour du nombre des commandes en bd "+retour1);
        String nombreTotalClientSql="SELECT COUNT(*) AS totalClient from client;";
        int retour2= jdbcTemplate.queryForObject(nombreTotalClientSql, Integer.class);
        log.warn("retour du nombre des clients en bd "+retour2);
        log.warn("le nombre de client moyen par commande est: "+((int) Math.floor(retour1/retour2)));
        return (int) Math.floor(retour1/retour2) ;
    }
    public String getArticleAvecStockQuiDiminueVite() {
        String sql = "SELECT id_article\n" +
                "FROM ligne_de_commande_client\n" +
                "WHERE creation_date >= DATE_SUB(CURRENT_DATE(), INTERVAL 1 MONTH)\n" +
                "GROUP BY id_article\n" +
                "ORDER BY SUM(quantite) DESC\n" +
                "LIMIT 1;\n";
        int idArticle = jdbcTemplate.queryForObject(sql, Integer.class);
        log.warn("votre idArticle= "+idArticle);
        if (idArticle != 0) {
            Optional<Article> article = articleRepository.findById(idArticle);
            if (article.isPresent()) {
                return "L'article susceptible de vite finir dans votre stock est : " + article.get().getCodeArticle();
            } else {
                return "Article introuvable";
            }
        } else {
            return "Aucun article trouvé";
        }
    }



}
