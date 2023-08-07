package com.gestiondestock.spring.Validator;

import com.gestiondestock.spring.DAO.ClientDAO;
import com.gestiondestock.spring.DAO.CommandeClientDAO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeClientValidator {

    public static List<String> validate(CommandeClientDAO commandeClientDAO){
        List<String> errors=new ArrayList<>();

        if(commandeClientDAO==null){
            errors.add("le code est un champ obligatoire");
            errors.add("client de cette commande n'est op trouvé");
            return errors;
        }
        if(!StringUtils.hasLength(commandeClientDAO.getCode())){
            errors.add("le code est un champ obligatoire");
        }

        if(commandeClientDAO.getClient()==null){
            errors.add("client de cette commande n'est op trouvé");
        }
        return errors;
    }
}
