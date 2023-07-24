package com.gestiondestock.spring.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestiondestock.spring.models.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDAO {
    private Integer id;
    private String code;
    private String designation;
    @JsonIgnore
    private List<ArticleDAO> articles;

    public static CategoryDAO fromEntity(Category category){
        if(category==null){
            return null;
        }
        return CategoryDAO.builder()
                .id(category.getId())
                .code(category.getCode())
                .designation(category.getDesignation())

                .build();
    }
    public static Category toEntity(CategoryDAO categoryDao){
        if(categoryDao==null){
            return null;
        }
        Category category=new Category();
        category.setId(categoryDao.getId());
        category.setCode(categoryDao.getCode());
        category.setDesignation(categoryDao.getDesignation());
        return category;
    }

}
