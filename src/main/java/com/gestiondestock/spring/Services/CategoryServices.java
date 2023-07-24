package com.gestiondestock.spring.Services;

import com.gestiondestock.spring.DAO.CategoryDAO;

import java.util.List;

public interface CategoryServices {
    CategoryDAO findById(Integer id);
    CategoryDAO save(CategoryDAO categoryDAO);
    CategoryDAO findByCode(String code);
    List<CategoryDAO> findAll();
    void delete(Integer id);
}
