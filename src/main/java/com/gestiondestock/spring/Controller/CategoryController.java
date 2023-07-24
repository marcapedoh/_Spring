package com.gestiondestock.spring.Controller;

import com.gestiondestock.spring.Controller.API.CategoryAPI;
import com.gestiondestock.spring.DAO.CategoryDAO;
import com.gestiondestock.spring.Services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CategoryController implements CategoryAPI {
    private final CategoryServices categoryServices;

    @Autowired
    public CategoryController(CategoryServices categoryServices){
        this.categoryServices=categoryServices;
    }

    @Override
    public CategoryDAO findById(Integer id) {
        return categoryServices.findById(id);
    }

    @Override
    public CategoryDAO save(CategoryDAO categoryDAO) {
        return categoryServices.save(categoryDAO);
    }

    @Override
    public CategoryDAO findByCode(String code) {
        return categoryServices.findByCode(code);
    }

    @Override
    public List<CategoryDAO> findAll() {
        return categoryServices.findAll();
    }

    @Override
    public void delete(Integer id) {
        categoryServices.delete(id);
    }
}
