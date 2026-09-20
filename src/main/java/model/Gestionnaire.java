package model;

public class Gestionnaire extends Personne {

    private int idGestionnaire;

    public Gestionnaire(String nom, String prenom, String email,
                        String motDePasse, int idGestionnaire) {

        super(nom, prenom, email, motDePasse);
        this.idGestionnaire = idGestionnaire;
    }

    public int getIdGestionnaire() {
        return idGestionnaire;
    }

    public void setIdGestionnaire(int idGestionnaire) {
        this.idGestionnaire = idGestionnaire;
    }
}