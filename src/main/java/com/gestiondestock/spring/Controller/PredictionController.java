package com.gestiondestock.spring.Controller;

import com.gestiondestock.spring.Services.ServicesDataLoaded;
import com.gestiondestock.spring.Services.TensorGraphOperationPredict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gestiondestock.spring.Constants.Utils.APP_ROOT;

@RestController
@CrossOrigin(origins = "*")
public class PredictionController {
    @Autowired
    private TensorGraphOperationPredict tensorGraphOperationPredict;
    @Autowired
    private ServicesDataLoaded servicesDataLoaded;
    @GetMapping(APP_ROOT+"/predictNumberClient")
    public int predictNumberClient() {
        int rapport = tensorGraphOperationPredict.PredictNumberClient();
        return rapport;
    }

    @GetMapping(APP_ROOT+"/predictArticleShouldFinishQuickly")
    public String getArticleAvecStockQuiDiminueVite() {
        String retour= servicesDataLoaded.getArticleAvecStockQuiDiminueVite();
        return retour;
    }


}
