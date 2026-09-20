package model;
import java.time.LocalDateTime;
public class Transaction {
private String idTransaction;
private TypeTransaction type;
private double montant;
private LocalDateTime date;
private Compte compteSource;
private Compte compteDestination;
    public Transaction(String idTransaction, TypeTransaction type, double montant,
                       LocalDateTime date, Compte compteSource, Compte compteDestination) {

        this.idTransaction = idTransaction;
        this.type = type;
        this.montant = montant;
        this.date = date;
        this.compteSource = compteSource;
        this.compteDestination = compteDestination;
    }
    public Compte getCompteDestination() {
        return compteDestination;
    }

    public Compte getCompteSource() {
        return compteSource;

    }





    public double getMontant() {
        return montant;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public TypeTransaction getType() {
        return type;
    }

}
