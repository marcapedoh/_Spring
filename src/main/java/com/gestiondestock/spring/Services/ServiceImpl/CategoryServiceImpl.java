package com.gestiondestock.spring.Services.ServiceImpl;

import com.gestiondestock.spring.DAO.CategoryDAO;
import com.gestiondestock.spring.Exception.EntityNotFoundException;
import com.gestiondestock.spring.Exception.ErrorCodes;
import com.gestiondestock.spring.Exception.InvalidEntityException;
import com.gestiondestock.spring.Exception.InvalidOperationException;
import com.gestiondestock.spring.Repository.ArticleRepository;
import com.gestiondestock.spring.Repository.CategoryRepository;
import com.gestiondestock.spring.Services.CategoryServices;
import com.gestiondestock.spring.Validator.CategoryValidator;
import com.gestiondestock.spring.models.Article;
import com.gestiondestock.spring.models.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@EnableWebMvc
@EnableAutoConfiguration
public class CategoryServiceImpl implements CategoryServices {
    private CategoryRepository categoryRepository;
    private ArticleRepository articleRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,ArticleRepository articleRepository){
        this.categoryRepository=categoryRepository;
        this.articleRepository=articleRepository;
    }

    @Override
    public CategoryDAO findById(Integer id) {
        if(id==null){
            log.error("vous ne pouvez cherchez une category qui est null");
            return null;
        }
        Optional<Category> category=categoryRepository.findById(id);
//
        return category.map(CategoryDAO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("aucune category ne correspond à votre id passer en parametre", ErrorCodes.Category_Not_Found));

    }

    @Override
    public CategoryDAO save(CategoryDAO categoryDAO) {
        List<String> errors= CategoryValidator.validate(categoryDAO);
        if(!errors.isEmpty()){
            log.error("Category non valide {}",categoryDAO);
            throw new InvalidEntityException("la categorie n'est pas valide",ErrorCodes.Category_Not_Valid,errors);
        }
        return CategoryDAO.fromEntity(
                categoryRepository.save(
                        CategoryDAO.toEntity(categoryDAO)
                )
        );
    }

    @Override
    public CategoryDAO findByCode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("vous ne pouvez cherchez une category si le code est null");
            return null;
        }
        Optional<Category> category=categoryRepository.findCategoryByCode(code);
        return category.map(CategoryDAO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("aucune category ne correspond à votre code passer en parametre", ErrorCodes.Category_Not_Found));

    }

    @Override
    public List<CategoryDAO> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error("vous ne pouvez cherchez une category si le champ est null");
        }
        List<Article> articles = articleRepository.findAllByCategoryId(id);
        if (!articles.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer cette categorie qui est deja utilise",
                    ErrorCodes.CATEGORY_ALREADY_IN_USE);
        }
        categoryRepository.deleteById(id);

    }
}
