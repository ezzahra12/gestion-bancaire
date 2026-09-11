import service.ClientService;
import model.Client;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClientService clientService=new ClientService();

  Scanner scanner=new Scanner(System.in);
  boolean continuer= true;
  while(continuer){
      System.out.println("Welcooooome");
      System.out.println("1.inscription");
      System.out.println("2.connexion");
      System.out.println("3.Quitter");
      System.out.println("choix : ");


      int choix= scanner.nextInt();
      scanner.nextLine();

      if (choix == 1) {
          System.out.println("nom:");
          String nom = scanner.nextLine();
          System.out.println("prenom: ");
          String prenom=scanner.nextLine();
          System.out.print("Email : ");
          String email = scanner.nextLine();
          System.out.print("Mot de passe : ");
          String motDePasse = scanner.nextLine();
          clientService.Inscrire(nom,prenom,email,motDePasse);

      }
      if (choix == 2){
          System.out.println("Email: ");
          String email=scanner.nextLine();
          System.out.println("password: ");
          String password=scanner.nextLine();
          Client client=clientService.authentifier(email,password);
          if(email != null){
              System.out.println("Connexion réussie");
              System.out.println("bienvenue:"+ client.getPrenom());
              System.out.println("=======Menu==========");

              System.out.println("1. Créer un compte bancaire");
              System.out.println("2.  Consulter mes comptes");
              System.out.println("3. Déconnexion");
          }
      }

      else if (choix == 3) {
              System.out.println("Au revoir !");
              continuer=false;
          }

      else {
              System.out.println("Choix invalide !");
          }
      }



  }

    }




