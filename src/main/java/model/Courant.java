package model;

public class Courant extends Compte {

    public Courant(String numeroCompte, double solde) {

        this.numeroCompte = numeroCompte;
        this.solde = solde;
        this.typeCompte = TypeCompte.COURANT;
    }
}