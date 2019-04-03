package vues;

import controleur.Controleur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import vues.admin.CreationUtilisateur;
import vues.admin.SupprimerUtilisateur;
import vues.admin.TraiterDemandes;
import vues.connexion.ConnexionVue;
import vues.connexion.MotDePasseVue;
import vues.utilisateur.DemandeUtilisateur;

import java.io.IOException;
import java.net.URL;

/**
 * Created by YohanBoichut on 04/12/2016.
 */
public class FenetrePrincipale {




    Controleur monControleur;
    private Stage monTheatre;
    private SupprimerUtilisateur supprimerUtilisateur;
    private TraiterDemandes traiterDemandes;


    public Controleur getMonControleur() {
        return monControleur;
    }

    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }



    @FXML
    BorderPane maFenetre;


    private void initialiserVues() {
        connexionVue = ConnexionVue.creerVue(monControleur);
        motDePasseVue = MotDePasseVue.creerVue(monControleur);
        creationUtilisateurVue = CreationUtilisateur.creerVue(monControleur);
        demandeUtilisateur = DemandeUtilisateur.creerVue(monControleur);
        traiterDemandes = TraiterDemandes.creerVue(monControleur);
        supprimerUtilisateur = SupprimerUtilisateur.creerVue(monControleur);
        menuVue = MenuVue.creerVue(monControleur,creationUtilisateurVue,supprimerUtilisateur,traiterDemandes);
        listeThemes = ListeThemes.creerVue(monControleur);

        //menuVue..setContent();

    }



    public void goToConnexion() {

        this.maFenetre.setCenter(this.connexionVue.getNode());
    }



    public void goToSaisieMotDePasse() {

        this.maFenetre.setCenter(this.motDePasseVue.getNode());


    }


    public void goToMenu() {

        this.menuVue.checkVisibility();
        this.maFenetre.setCenter(this.menuVue.getNode());
    }

    MenuVue menuVue;
    ConnexionVue connexionVue;
    MotDePasseVue motDePasseVue;
    DemandeUtilisateur demandeUtilisateur;
    ListeThemes listeThemes;


    public static FenetrePrincipale creerVue(Controleur c, Stage stage) {
        URL location = FenetrePrincipale.class.getResource("/vues/fenetrePrincipale.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FenetrePrincipale vue = fxmlLoader.getController();
        vue.setMonControleur(c);
        vue.initialiserVues();
        stage.setScene(new Scene(root,600,600));
        vue.setMonTheatre(stage);
        return vue;
    }


    public void setMonTheatre(Stage monTheatre) {
        this.monTheatre = monTheatre;
    }

    CreationUtilisateur creationUtilisateurVue;


    public void goToDemandeInscription() {
        this.maFenetre.setCenter(this.demandeUtilisateur.getNode());
    }


    public void show() {
        this.monTheatre.show();
    }





}
