package com.gestiondestock.spring.Controller;

import com.gestiondestock.spring.DAO.AutoCompleteDAO;
import com.gestiondestock.spring.Services.AutoCompleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gestiondestock.spring.Constants.Utils.APP_ROOT;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(APP_ROOT+"/autocomplete")
public class AutoCompleteController {
    private final AutoCompleteService autoCompleteService;
    @Autowired
    public AutoCompleteController(AutoCompleteService autoCompleteService) {
        this.autoCompleteService = autoCompleteService;
    }

    @GetMapping("/autocomplete/{codeArticle}")
    public List<AutoCompleteDAO> getAllAutoCompleteDATA(@PathVariable("codeArticle") String codeArticle){
      return autoCompleteService.getAllDataForAutoComplete(codeArticle);
    }
}
