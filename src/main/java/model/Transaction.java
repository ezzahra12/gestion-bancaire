package model;
import java.time.LocalDateTime;
public class Transaction {
private String idTransaction;
private TypeTransaction type;
private double montant;
private LocalDateTime date;
private Compte compteSource;
private Compte compteDestination;
}
