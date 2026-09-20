package model;

import java.util.HashSet;

public abstract class Compte {

    protected String numeroCompte;
    protected double solde;
    protected String typeCompte;
    protected HashSet<Transaction> historiqueTransactions = new HashSet<>();

    private boolean actif = true;

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public double getSolde() {
        return solde;
    }




    public String getTypeCompte() {
        return typeCompte;
    }

    public HashSet<Transaction> getHistoriqueTransactions() {
        return historiqueTransactions;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }
}