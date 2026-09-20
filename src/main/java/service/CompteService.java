package service;

import exceptions.MontantInvalideException;
import exceptions.SoldeInsuffisantException;
import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class CompteService {

    private int compteur = 1;

    // ================= CREER COMPTE =================

    public void creerCompte(Client client, String type) {

        String numeroCompte = "C00" + compteur;
        compteur++;

        Compte compte;

        if (type.equals(TypeCompte.COURANT)) {

            compte = new Courant(numeroCompte, 0);

        } else if (type.equals(TypeCompte.EPARGNE)) {

            compte = new Epargne(numeroCompte, 0);

        } else {

            System.out.println("Type de compte invalide.");
            return;
        }

        client.ajouterCompte(compte);

        System.out.println("Compte créé avec succès !");
        System.out.println("Numéro du compte : " + numeroCompte);
        System.out.println("Type : " + compte.getTypeCompte());
    }

    // ================= DEPOT =================

    public static void deposer(
            Compte compte,
            double montant
    ) throws MontantInvalideException {

        if (montant <= 0) {

            throw new MontantInvalideException(
                    "Le montant doit être supérieur à 0."
            );
        }

        compte.setSolde(
                compte.getSolde() + montant
        );

        Transaction transaction = new Transaction(
                "T" + System.currentTimeMillis(),
                TypeTransaction.DEPOT,
                montant,
                LocalDateTime.now(),
                null,
                compte
        );

        compte.getHistoriqueTransactions().add(transaction);

        System.out.println("Dépôt effectué avec succès !");
        System.out.println(
                "Nouveau solde : "
                        + compte.getSolde()
                        + " DH"
        );
    }

    // ================= RETRAIT =================

    public static void retirer(
            Compte compte,
            double montant
    ) throws MontantInvalideException,
            SoldeInsuffisantException {

        if (montant <= 0) {

            throw new MontantInvalideException(
                    "Le montant doit être supérieur à 0."
            );
        }

        if (montant > compte.getSolde()) {

            throw new SoldeInsuffisantException(
                    "Solde insuffisant."
            );
        }

        compte.setSolde(
                compte.getSolde() - montant
        );

        Transaction transaction = new Transaction(
                "T" + System.currentTimeMillis(),
                TypeTransaction.RETRAIT,
                montant,
                LocalDateTime.now(),
                compte,
                null
        );

        compte.getHistoriqueTransactions().add(transaction);

        System.out.println("Retrait effectué avec succès !");
        System.out.println(
                "Nouveau solde : "
                        + compte.getSolde()
                        + " DH"
        );
    }

    // ================= VIREMENT =================

    public static void faireVirement(
            Compte compteSource,
            Compte compteDestination,
            double montant
    ) throws MontantInvalideException,
            SoldeInsuffisantException {

        if (montant <= 0) {

            throw new MontantInvalideException(
                    "Le montant doit être supérieur à 0."
            );
        }

        if (montant > compteSource.getSolde()) {

            throw new SoldeInsuffisantException(
                    "Solde insuffisant pour effectuer le virement."
            );
        }

        compteSource.setSolde(
                compteSource.getSolde() - montant
        );

        compteDestination.setSolde(
                compteDestination.getSolde() + montant
        );

        Transaction transaction = new Transaction(
                "T" + System.currentTimeMillis(),
                TypeTransaction.VIREMENT,
                montant,
                LocalDateTime.now(),
                compteSource,
                compteDestination
        );

        compteSource.getHistoriqueTransactions()
                .add(transaction);

        compteDestination.getHistoriqueTransactions()
                .add(transaction);

        System.out.println("Virement effectué avec succès !");

        System.out.println(
                "Nouveau solde du compte source : "
                        + compteSource.getSolde()
                        + " DH"
        );
    }

    // ================= ENREGISTRER RELEVE =================

    public static void enregistrerReleve(Compte compte) {

        String nomFichier =
                "releves/" + compte.getNumeroCompte() + ".txt";

        try {

            File dossier = new File("releves");

            if (!dossier.exists()) {
                dossier.mkdir();
            }

            FileWriter writer =
                    new FileWriter(nomFichier);

            writer.write(
                    "===== RELEVE BANCAIRE =====\n"
            );

            writer.write(
                    "Numéro du compte : "
                            + compte.getNumeroCompte()
                            + "\n"
            );

            writer.write(
                    "Type : "
                            + compte.getTypeCompte()
                            + "\n"
            );

            writer.write(
                    "Solde : "
                            + compte.getSolde()
                            + " DH\n"
            );

            writer.write(
                    "\n===== TRANSACTIONS =====\n"
            );

            for (Transaction transaction :
                    compte.getHistoriqueTransactions()) {

                writer.write(
                        "-------------------------\n"
                );

                writer.write(
                        "ID : "
                                + transaction.getIdTransaction()
                                + "\n"
                );

                writer.write(
                        "Type : "
                                + transaction.getType()
                                + "\n"
                );

                writer.write(
                        "Montant : "
                                + transaction.getMontant()
                                + " DH\n"
                );

                writer.write(
                        "Date : "
                                + transaction.getDate()
                                + "\n"
                );
            }

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Erreur lors de l'enregistrement du relevé."
            );
        }
    }

    // ================= CONSULTER RELEVE =================

    public static void consulterReleve(Compte compte) {

        String nomFichier =
                "releves/" + compte.getNumeroCompte() + ".txt";

        try {

            File fichier =
                    new File(nomFichier);

            if (!fichier.exists()) {

                System.out.println(
                        "Aucun relevé bancaire trouvé."
                );

                return;
            }

            Scanner scannerFichier =
                    new Scanner(fichier);

            System.out.println(
                    "===== RELEVÉ BANCAIRE ====="
            );

            while (scannerFichier.hasNextLine()) {

                System.out.println(
                        scannerFichier.nextLine()
                );
            }

            scannerFichier.close();

        } catch (FileNotFoundException e) {

            System.out.println(
                    "Erreur lors de l'accès au fichier."
            );
        }
    }
}