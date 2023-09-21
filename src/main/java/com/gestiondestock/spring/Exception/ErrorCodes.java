package com.gestiondestock.spring.Exception;

public enum ErrorCodes {
    Article_Not_Found(1000),
    Article_Not_Valid(1001),
    Category_Not_Found(2000),
    Category_Not_Valid(2001),
    Client_Not_Found(3000),
    Client_Not_Valid(3001),
    Commande_Client_Not_Found(4000),
    Commande_Fournisseur_Not_Found(5000),
    COMMANDE_FOURNISSEUR_NOT_FOUND(5001),
    Entreprise_Not_Found(6000),
    Entreprise_Not_Valid(6000),
    Fournisseur_Not_Found(7000),
    Fournisseur_Not_Valid(7001),
    Ligne_Commande_Client_Not_Found(8000),
    Ligne_Commande_Client_Not_VALID(8001),
    Ligne_Commande_Fournisseur_Not_Found(9000),
    Ligne_Vente_Not_Found(10000),
    Mvt_Stk_Not_Found(11000),
    MVT_STK_Not_Valid(11001),
    Utilisateur_Not_Found(12000),
    UTILISATEUR_ALREADY_EXISTS(12001),
    Utilisateur_Not_Valid(12002),
    Vente_Not_Found(13000),
    Vente_Not_Valid(13001),
    VENTE_ALREADY_IN_USE(13002),
    UPDATE_PHOTO_EXCEPTION(14000),
    COMMANDE_CLIENT_NON_MODIFIABLE(15000),
    COMMANDE_FOURNISSEUR_NON_MODIFIABLE(15001),
    COMMANDE_FOURNIISEEUR_ALREADY_IN_USE(15002),
    ARTICLE_ALREADY_IN_USE(15003),
    CLIENT_ALREADY_IN_USE(16000),
    CATEGORY_ALREADY_IN_USE(17000),
    FOURNISSEUR_ALREADY_IN_USE(19000),
    UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID(18000),

    CONTEXT_INCONNU(14001),
    Ligne_Commande_client_ALREADY_IN_USE(14002),
    Ligne_Commande_Fournisseur_ALREADY_IN_USE(14003),
    LIGNE_VENTE_NOT_VALID(14004),
    LIVRAISON_NOT_FOUND(14005),
    LIVRAISON_IS_ALREADY_USE(14006),
    Ligne_VENTE_ALREADY_IN_USE(14007);


    private int code;
    ErrorCodes(int code){
        this.code=code;
    }

    public int getCode(){
        return code;
    }

}
