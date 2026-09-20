import exceptions.MontantInvalideException;
import exceptions.SoldeInsuffisantException;
import model.Client;
import model.Compte;
import model.Gestionnaire;
import model.Transaction;
import service.ClientService;
import service.CompteService;
import service.GestionnaireService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ClientService clientService = new ClientService();
        GestionnaireService gestionnaireService = new GestionnaireService();

        // ================= GESTIONNAIRE =================

        Gestionnaire gestionnaire = new Gestionnaire(
                "Admin",
                "NexaBank",
                "admin@nexabank.com",
                "1234",
                1
        );

        boolean continuer = true;

        while (continuer) {

            System.out.println("\n========== NEXABANK ==========");
            System.out.println("1. Inscription Client");
            System.out.println("2. Connexion Client");
            System.out.println("3. Connexion Gestionnaire");
            System.out.println("4. Quitter");
            System.out.print("Choix : ");

            if (!scanner.hasNextInt()) {
                System.out.println("Veuillez entrer un nombre valide !");
                scanner.nextLine();
                continue;
            }



            int choix = scanner.nextInt();
            scanner.nextLine();

            // =========================================================
            // 1 - INSCRIPTION CLIENT
            // =========================================================

            if (choix == 1) {

                System.out.println("\n========= INSCRIPTION =========");

                System.out.print("Nom : ");
                String nom = scanner.nextLine();

                System.out.print("Prénom : ");
                String prenom = scanner.nextLine();

                System.out.print("Email : ");
                String email = scanner.nextLine();

                System.out.print("Mot de passe : ");
                String motDePasse = scanner.nextLine();

                clientService.Inscrire(
                        nom,
                        prenom,
                        email,
                        motDePasse
                );
            }

            // =========================================================
            // 2 - CONNEXION CLIENT
            // =========================================================

            else if (choix == 2) {

                System.out.println("\n========= CONNEXION CLIENT =========");

                System.out.print("Email : ");
                String email = scanner.nextLine();

                System.out.print("Mot de passe : ");
                String password = scanner.nextLine();

                Client client = clientService.authentifier(
                        email,
                        password
                );

                if (client != null) {

                    System.out.println("\nConnexion réussie !");
                    System.out.println("Bienvenue " + client.getPrenom());

                    boolean connecte = true;

                    while (connecte) {

                        System.out.println("\n========== MENU CLIENT ==========");
                        System.out.println("1. Consulter mes comptes");
                        System.out.println("2. Déposer de l'argent");
                        System.out.println("3. Retirer de l'argent");
                        System.out.println("4. Faire un virement");
                        System.out.println("5. Consulter l'historique");
                        System.out.println("6. Consulter le relevé bancaire");
                        System.out.println("7. Déconnexion");
                        System.out.print("Choix : ");

                        if (!scanner.hasNextInt()) {
                            System.out.println("Veuillez entrer un nombre valide !");
                            scanner.nextLine();
                            continue;
                        }

                        int choixClient = scanner.nextInt();
                        scanner.nextLine();

                        switch (choixClient) {

                            // =================================================
                            // 1 - CONSULTER LES COMPTES
                            // =================================================

                            case 1:

                                System.out.println("\n===== MES COMPTES =====");

                                if (client.getComptes().isEmpty()) {

                                    System.out.println(
                                            "Vous n'avez aucun compte bancaire."
                                    );

                                } else {

                                    for (Compte compte : client.getComptes().values()) {

                                        System.out.println("-------------------------");
                                        System.out.println(
                                                "Numéro : "
                                                        + compte.getNumeroCompte()
                                        );

                                        System.out.println(
                                                "Solde : "
                                                        + compte.getSolde()
                                                        + " DH"
                                        );

                                        System.out.println(
                                                "Type : "
                                                        + compte.getTypeCompte()
                                        );

                                        System.out.println(
                                                "Statut : "
                                                        + (compte.isActif()
                                                        ? "Actif"
                                                        : "Clôturé")
                                        );
                                    }
                                }

                                break;

                            // =================================================
                            // 2 - DEPOT
                            // =================================================

                            case 2:

                                System.out.println(
                                        "\n===== DÉPOSER DE L'ARGENT ====="
                                );

                                System.out.print(
                                        "Numéro du compte : "
                                );

                                String numeroCompteDepot =
                                        scanner.nextLine();

                                Compte compteDepot =
                                        client.getComptes()
                                                .get(numeroCompteDepot);

                                if (compteDepot == null) {

                                    System.out.println(
                                            "Compte introuvable."
                                    );

                                    break;
                                }

                                if (!compteDepot.isActif()) {

                                    System.out.println(
                                            "Ce compte est clôturé."
                                    );

                                    break;
                                }

                                System.out.print(
                                        "Montant à déposer : "
                                );

                                double montantDepot =
                                        scanner.nextDouble();

                                scanner.nextLine();

                                try {

                                    CompteService.deposer(
                                            compteDepot,
                                            montantDepot
                                    );

                                    CompteService.enregistrerReleve(
                                            compteDepot
                                    );

                                } catch (MontantInvalideException e) {

                                    System.out.println(
                                            "Erreur : "
                                                    + e.getMessage()
                                    );
                                }

                                break;

                            // =================================================
                            // 3 - RETRAIT
                            // =================================================

                            case 3:

                                System.out.println(
                                        "\n===== RETIRER DE L'ARGENT ====="
                                );

                                System.out.print(
                                        "Numéro du compte : "
                                );

                                String numeroCompteRetrait =
                                        scanner.nextLine();

                                Compte compteRetrait =
                                        client.getComptes()
                                                .get(numeroCompteRetrait);

                                if (compteRetrait == null) {

                                    System.out.println(
                                            "Compte introuvable."
                                    );

                                    break;
                                }

                                if (!compteRetrait.isActif()) {

                                    System.out.println(
                                            "Ce compte est clôturé."
                                    );

                                    break;
                                }

                                System.out.print(
                                        "Montant à retirer : "
                                );

                                double montantRetrait =
                                        scanner.nextDouble();

                                scanner.nextLine();

                                try {

                                    CompteService.retirer(
                                            compteRetrait,
                                            montantRetrait
                                    );

                                    CompteService.enregistrerReleve(
                                            compteRetrait
                                    );

                                } catch (MontantInvalideException e) {

                                    System.out.println(
                                            "Erreur : "
                                                    + e.getMessage()
                                    );

                                } catch (SoldeInsuffisantException e) {

                                    System.out.println(
                                            "Erreur : "
                                                    + e.getMessage()
                                    );
                                }

                                break;

                            // =================================================
                            // 4 - VIREMENT
                            // =================================================

                            case 4:

                                System.out.println(
                                        "\n===== FAIRE UN VIREMENT ====="
                                );

                                System.out.print(
                                        "Numéro du compte source : "
                                );

                                String numeroSource =
                                        scanner.nextLine();

                                Compte compteSource =
                                        client.getComptes()
                                                .get(numeroSource);

                                if (compteSource == null) {

                                    System.out.println(
                                            "Compte source introuvable."
                                    );

                                    break;
                                }

                                if (!compteSource.isActif()) {

                                    System.out.println(
                                            "Le compte source est clôturé."
                                    );

                                    break;
                                }

                                System.out.print(
                                        "Numéro du compte destination : "
                                );

                                String numeroDestination =
                                        scanner.nextLine();

                                Compte compteDestination =
                                        client.getComptes()
                                                .get(numeroDestination);

                                if (compteDestination == null) {

                                    System.out.println(
                                            "Compte destination introuvable."
                                    );

                                    break;
                                }

                                if (!compteDestination.isActif()) {

                                    System.out.println(
                                            "Le compte destination est clôturé."
                                    );

                                    break;
                                }

                                if (compteSource == compteDestination) {

                                    System.out.println(
                                            "Les deux comptes doivent être différents."
                                    );

                                    break;
                                }

                                System.out.print(
                                        "Montant du virement : "
                                );

                                double montantVirement =
                                        scanner.nextDouble();

                                scanner.nextLine();

                                try {

                                    CompteService.faireVirement(
                                            compteSource,
                                            compteDestination,
                                            montantVirement
                                    );

                                    CompteService.enregistrerReleve(
                                            compteSource
                                    );

                                    CompteService.enregistrerReleve(
                                            compteDestination
                                    );

                                } catch (MontantInvalideException e) {

                                    System.out.println(
                                            "Erreur : "
                                                    + e.getMessage()
                                    );

                                } catch (SoldeInsuffisantException e) {

                                    System.out.println(
                                            "Erreur : "
                                                    + e.getMessage()
                                    );
                                }

                                break;

                            // =================================================
                            // 5 - HISTORIQUE
                            // =================================================

                            case 5:

                                System.out.println(
                                        "\n===== HISTORIQUE DES TRANSACTIONS ====="
                                );

                                System.out.print(
                                        "Numéro du compte : "
                                );

                                String numeroCompteHistorique =
                                        scanner.nextLine();

                                Compte compteHistorique =
                                        client.getComptes()
                                                .get(numeroCompteHistorique);

                                if (compteHistorique == null) {

                                    System.out.println(
                                            "Compte introuvable."
                                    );

                                    break;
                                }

                                if (compteHistorique
                                        .getHistoriqueTransactions()
                                        .isEmpty()) {

                                    System.out.println(
                                            "Aucune transaction pour ce compte."
                                    );

                                    break;
                                }

                                for (Transaction transaction :
                                        compteHistorique
                                                .getHistoriqueTransactions()) {

                                    System.out.println(
                                            "-------------------------"
                                    );

                                    System.out.println(
                                            "ID : "
                                                    + transaction
                                                    .getIdTransaction()
                                    );

                                    System.out.println(
                                            "Type : "
                                                    + transaction.getType()
                                    );

                                    System.out.println(
                                            "Montant : "
                                                    + transaction.getMontant()
                                                    + " DH"
                                    );

                                    System.out.println(
                                            "Date : "
                                                    + transaction.getDate()
                                    );
                                }

                                break;

                            // =================================================
                            // 6 - RELEVE BANCAIRE
                            // =================================================

                            case 6:

                                System.out.println(
                                        "\n===== RELEVÉ BANCAIRE ====="
                                );

                                System.out.print(
                                        "Numéro du compte : "
                                );

                                String numeroCompteReleve =
                                        scanner.nextLine();

                                Compte compteReleve =
                                        client.getComptes()
                                                .get(numeroCompteReleve);

                                if (compteReleve == null) {

                                    System.out.println(
                                            "Compte introuvable."
                                    );

                                    break;
                                }

                                CompteService.enregistrerReleve(
                                        compteReleve
                                );

                                CompteService.consulterReleve(
                                        compteReleve
                                );

                                break;

                            // =================================================
                            // 7 - DECONNEXION
                            // =================================================

                            case 7:

                                System.out.println(
                                        "Déconnexion..."
                                );

                                connecte = false;

                                break;

                            default:

                                System.out.println(
                                        "Choix invalide !"
                                );

                                break;
                        }
                    }

                } else {

                    System.out.println(
                            "Email ou mot de passe incorrect !"
                    );
                }
            }

            // =========================================================
            // 3 - CONNEXION GESTIONNAIRE
            // =========================================================

            else if (choix == 3) {

                System.out.println(
                        "\n========= CONNEXION GESTIONNAIRE ========="
                );

                System.out.print("Email : ");
                String email = scanner.nextLine();

                System.out.print("Mot de passe : ");
                String password = scanner.nextLine();

                if (email.equals(gestionnaire.getEmail())
                        && password.equals(
                        gestionnaire.getMotDePasse())) {

                    System.out.println(
                            "\nConnexion réussie !"
                    );

                    System.out.println(
                            "Bienvenue "
                                    + gestionnaire.getPrenom()
                    );

                    boolean connecteGestionnaire = true;

                    while (connecteGestionnaire) {

                        System.out.println(
                                "\n========== MENU GESTIONNAIRE =========="
                        );

                        System.out.println(
                                "1. Créer un compte"
                        );

                        System.out.println(
                                "2. Clôturer un compte"
                        );

                        System.out.println(
                                "3. Modifier les informations d'un client"
                        );

                        System.out.println(
                                "4. Consulter le relevé d'un client"
                        );

                        System.out.println(
                                "5. Déconnexion"
                        );

                        System.out.print("Choix : ");

                        if (!scanner.hasNextInt()) {

                            System.out.println(
                                    "Veuillez entrer un nombre valide !"
                            );

                            scanner.nextLine();
                            continue;
                        }

                        int choixGestionnaire =
                                scanner.nextInt();

                        scanner.nextLine();

                        switch (choixGestionnaire) {

                            // =================================================
                            // 1 - CREER UN COMPTE
                            // =================================================

                            case 1:

                                System.out.println(
                                        "\n===== CRÉER UN COMPTE ====="
                                );

                                System.out.print(
                                        "ID du client : "
                                );

                                int idClient =
                                        scanner.nextInt();

                                scanner.nextLine();

                                Client clientCompte =
                                        clientService
                                                .trouverParId(idClient);

                                if (clientCompte == null) {

                                    System.out.println(
                                            "Client introuvable."
                                    );

                                    break;
                                }

                                System.out.println(
                                        "1. Compte Courant"
                                );

                                System.out.println(
                                        "2. Compte Épargne"
                                );

                                System.out.print(
                                        "Choix : "
                                );

                                int typeCompte =
                                        scanner.nextInt();

                                scanner.nextLine();

                                if (typeCompte == 1) {

                                    gestionnaireService.creerCompte(
                                            clientCompte,
                                            "COURANT"
                                    );

                                } else if (typeCompte == 2) {

                                    gestionnaireService.creerCompte(
                                            clientCompte,
                                            "EPARGNE"
                                    );

                                } else {

                                    System.out.println(
                                            "Type de compte invalide."
                                    );
                                }

                                break;

                            // =================================================
                            // 2 - CLOTURER UN COMPTE
                            // =================================================

                            case 2:

                                System.out.println(
                                        "\n===== CLÔTURER UN COMPTE ====="
                                );

                                System.out.print(
                                        "ID du client : "
                                );

                                int idClientCloture =
                                        scanner.nextInt();

                                scanner.nextLine();

                                Client clientCloture =
                                        clientService
                                                .trouverParId(
                                                        idClientCloture
                                                );

                                if (clientCloture == null) {

                                    System.out.println(
                                            "Client introuvable."
                                    );

                                    break;
                                }

                                if (clientCloture
                                        .getComptes()
                                        .isEmpty()) {

                                    System.out.println(
                                            "Ce client n'a aucun compte."
                                    );

                                    break;
                                }

                                System.out.println(
                                        "\n===== COMPTES DU CLIENT ====="
                                );

                                for (Compte compte :
                                        clientCloture
                                                .getComptes()
                                                .values()) {

                                    System.out.println(
                                            "Numéro : "
                                                    + compte
                                                    .getNumeroCompte()
                                    );

                                    System.out.println(
                                            "Solde : "
                                                    + compte.getSolde()
                                                    + " DH"
                                    );

                                    System.out.println(
                                            "Statut : "
                                                    + (compte.isActif()
                                                    ? "Actif"
                                                    : "Clôturé")
                                    );

                                    System.out.println(
                                            "-------------------------"
                                    );
                                }

                                System.out.print(
                                        "Numéro du compte à clôturer : "
                                );

                                String numeroCloture =
                                        scanner.nextLine();

                                Compte compteCloture =
                                        clientCloture
                                                .getComptes()
                                                .get(numeroCloture);

                                if (compteCloture == null) {

                                    System.out.println(
                                            "Compte introuvable."
                                    );

                                    break;
                                }

                                if (compteCloture.getSolde() != 0) {

                                    System.out.println(
                                            "Impossible de clôturer le compte."
                                    );

                                    System.out.println(
                                            "Le solde doit être égal à 0 DH."
                                    );

                                    break;
                                }

                                gestionnaireService.cloturerCompte(
                                        compteCloture
                                );

                                break;

                            // =================================================
                            // 3 - MODIFIER CLIENT
                            // =================================================

                            case 3:


                                System.out.println(
                                        "\n===== MODIFIER UN CLIENT ====="
                                );

                                System.out.print(
                                        "ID du client : "
                                );

                                int idClientModification =
                                        scanner.nextInt();

                                scanner.nextLine();

                                Client clientModification =
                                        clientService
                                                .trouverParId(
                                                        idClientModification
                                                );

                                if (clientModification == null) {

                                    System.out.println(
                                            "Client introuvable."
                                    );

                                    break;
                                }

                                System.out.println(
                                        "Ancien nom : "
                                                + clientModification
                                                .getNom()
                                );

                                System.out.print(
                                        "Nouveau nom : "
                                );

                                String nouveauNom =
                                        scanner.nextLine();

                                System.out.println(
                                        "Ancien prénom : "
                                                + clientModification
                                                .getPrenom()
                                );

                                System.out.print(
                                        "Nouveau prénom : "
                                );

                                String nouveauPrenom =
                                        scanner.nextLine();

                                System.out.println(
                                        "Ancien email : "
                                                + clientModification
                                                .getEmail()
                                );

                                System.out.print(
                                        "Nouvel email : "
                                );

                                String nouvelEmail =
                                        scanner.nextLine();

                                gestionnaireService.modifierClient(
                                        clientModification,
                                        nouveauNom,
                                        nouveauPrenom,
                                        nouvelEmail
                                );

                                break;

                            // =================================================
                            // 4 - RELEVE CLIENT
                            // =================================================

                            case 4:

                                System.out.println(
                                        "\n===== RELEVÉ D'UN CLIENT ====="
                                );

                                System.out.print(
                                        "ID du client : "
                                );

                                int idClientReleve =
                                        scanner.nextInt();

                                scanner.nextLine();

                                Client clientReleve =
                                        clientService
                                                .trouverParId(
                                                        idClientReleve
                                                );

                                if (clientReleve == null) {

                                    System.out.println(
                                            "Client introuvable."
                                    );

                                    break;
                                }

                                if (clientReleve
                                        .getComptes()
                                        .isEmpty()) {

                                    System.out.println(
                                            "Ce client n'a aucun compte."
                                    );

                                    break;
                                }

                                System.out.println(
                                        "\n===== COMPTES DU CLIENT ====="
                                );

                                for (Compte compte :
                                        clientReleve
                                                .getComptes()
                                                .values()) {

                                    System.out.println(
                                            "Numéro : "
                                                    + compte
                                                    .getNumeroCompte()
                                    );

                                    System.out.println(
                                            "Type : "
                                                    + compte
                                                    .getTypeCompte()
                                    );

                                    System.out.println(
                                            "Solde : "
                                                    + compte.getSolde()
                                                    + " DH"
                                    );

                                    System.out.println(
                                            "-------------------------"
                                    );
                                }

                                System.out.print(
                                        "Numéro du compte : "
                                );

                                String numeroCompteGestionnaire =
                                        scanner.nextLine();

                                Compte compteGestionnaire =
                                        clientReleve
                                                .getComptes()
                                                .get(
                                                        numeroCompteGestionnaire
                                                );

                                if (compteGestionnaire == null) {

                                    System.out.println(
                                            "Compte introuvable."
                                    );

                                    break;
                                }

                                CompteService.enregistrerReleve(
                                        compteGestionnaire
                                );

                                CompteService.consulterReleve(
                                        compteGestionnaire
                                );

                                break;

                            // =================================================
                            // 5 - DECONNEXION
                            // =================================================

                            case 5:

                                System.out.println(
                                        "Déconnexion..."
                                );

                                connecteGestionnaire = false;

                                break;

                            default:

                                System.out.println(
                                        "Choix invalide !"
                                );

                                break;
                        }
                    }

                } else {

                    System.out.println(
                            "Email ou mot de passe incorrect !"
                    );
                }
            }

            // =========================================================
            // 4 - QUITTER
            // =========================================================

            else if (choix == 4) {

                System.out.println(
                        "Au revoir !"
                );

                continuer = false;

            } else {

                System.out.println(
                        "Choix invalide !"
                );
            }
        }

        scanner.close();
    }
}