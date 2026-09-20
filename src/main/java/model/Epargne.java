package model;

public class Epargne extends Compte {

    public Epargne(String numeroCompte, double solde) {

        this.numeroCompte = numeroCompte;
        this.solde = solde;
        this.typeCompte = TypeCompte.EPARGNE;
    }
}