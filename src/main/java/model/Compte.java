package model;

import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;


public abstract class Compte {
    protected String numeroCompte;
    protected double solde;
    protected TypeCompte typeCompte;
    protected List<Transaction> historiqueTransactions;
}


