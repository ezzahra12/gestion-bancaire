package service;

import exceptions.ParZeroExeption;
import model.Client;
import model.Compte;

public class GestionnaireService {

    private CompteService compteService;

    public GestionnaireService() {
        compteService = new CompteService();
    }

    // ================= CREER COMPTE =================

    public void creerCompte(
            Client client,
            String type
    ) {

        compteService.creerCompte(
                client,
                type
        );
    }

    // ================= CLOTURER COMPTE =================

    public void cloturerCompte(
            Compte compte
    ) {

        if (!compte.isActif()) {

            System.out.println(
                    "Le compte est déjà clôturé."
            );

            return;
        }

        compte.setActif(false);

        System.out.println(
                "Compte clôturé avec succès."
        );
    }

    // ================= MODIFIER CLIENT =================

    public void modifierClient(
            Client client,
            String nom,
            String prenom,
            String email
    ) {

        client.setNom(nom);
        client.setPrenom(prenom);
        client.setEmail(email);

        System.out.println(
                "Informations du client modifiées avec succès."
        );
    }

}
