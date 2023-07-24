package com.gestiondestock.spring.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tensorflow.*;

@Service
public class TensorGraphOperationPredict {

private ServicesDataLoaded servicesDataLoaded;
    @Autowired
    public TensorGraphOperationPredict(ServicesDataLoaded servicesDataLoaded){
        this.servicesDataLoaded=servicesDataLoaded;
    }
    public int PredictNumberClient(){
        Graph graph=new Graph();
        Operation nbreCmde=graph.opBuilder("Const", "nbreCmde")
                .setAttr("dtype", DataType.fromClass(Integer.class))
                .setAttr("value", Tensor.<Integer>create(servicesDataLoaded.RetourNbCommandeEnUnmois(), Integer.class))
                .build();
        Operation nbreClientPerCommde=graph.opBuilder("Const","nbreClientPerCommde")
                .setAttr("dtype",DataType.fromClass(Integer.class))
                .setAttr("value",Tensor.<Integer>create(servicesDataLoaded.RetourAVGClientPerCommande(), Integer.class))
                .build();

        Operation rapport=graph.opBuilder("Div","NombreDeClientParPrediction")
                .addInput(nbreCmde.output(0))
                .addInput(nbreClientPerCommde.output(0))
                .build();
        Session session=new Session(graph);

        Tensor<Integer> retour=session.runner()
                .fetch("NombreDeClientParPrediction")
                .run()
                .get(0)
                .expect(Integer.class);
        int nombreDeClients=((Integer) retour.intValue()).intValue();
        retour.close();
        return nombreDeClients;
    }
}
