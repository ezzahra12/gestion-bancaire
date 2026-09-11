package service;

import model.Client;

import java.util.HashMap;

public class ClientService {
    private int idClient=1;
    private HashMap<String, Client> clients=new HashMap<>();

    public Client Inscrire(String nom, String prenom, String email,
                           String motDePasse){
        Client cl=new Client(  nom,
                prenom,
                email,
                motDePasse,
                idClient);
        clients.put(email,cl);
        idClient++;
        System.out.println("Inscription réussie");
        System.out.println("ID:"+ cl.getIdClient());
        return cl;
    }

    public Client authentifier(String email,String motDePass){
        Client client = clients.get(email);
        if (client != null && client.getMotDePasse().equals(motDePass)) {
            return client;
        }
        return null;
    }
}
