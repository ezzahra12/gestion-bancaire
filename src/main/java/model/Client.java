package model;

import service.ClientService;

import java.util.HashMap;

public class Client extends Personne {

    private int idClient;

    private HashMap<String, Compte> comptes;
public Client(String nom, String prenom, String email, String motDePasse, int idClient){
    super(nom,prenom,email,motDePasse);
    this.idClient=idClient;
    this.comptes=new HashMap<>();
}

    public int getIdClient() {
        return idClient;
    }

    public HashMap<String, Compte> getComptes() {
        return comptes;
    }

    public void setComptes(HashMap<String, Compte> comptes) {
        this.comptes = comptes;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }


}


