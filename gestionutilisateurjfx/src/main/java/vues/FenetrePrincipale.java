package vues;

import controleur.Controleur;
import facade.erreurs.ThemeInexistantException;
import facade.erreurs.TopicInexistantException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modele.forum.Theme;
import modele.forum.Topic;
import vues.admin.CreationUtilisateur;
import vues.admin.SupprimerUtilisateur;
import vues.admin.TraiterDemandes;
import vues.connexion.ConnexionVue;
import vues.connexion.MotDePasseVue;
import vues.utilisateur.DemandeUtilisateur;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

/**
 * Created by YohanBoichut on 04/12/2016.
 */
public class FenetrePrincipale {




    Controleur monControleur;
    private Stage monTheatre;
    private SupprimerUtilisateur supprimerUtilisateur;
    private TraiterDemandes traiterDemandes;
    MenuVue menuVue;
    ConnexionVue connexionVue;
    MotDePasseVue motDePasseVue;
    DemandeUtilisateur demandeUtilisateur;
    ListeThemes listeThemes;
    ThemeVue listeTopic;
    CreationTopic creerTopic;
    TopicVue topic;
    CreationTheme creationTheme;

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
        listeThemes = ListeThemes.creerVue(monControleur);
        listeTopic= ThemeVue.creerVue(monControleur);
        creerTopic=CreationTopic.creerVue(monControleur);
        topic=TopicVue.creerVue(monControleur);
        creationTheme=CreationTheme.creerVue(monControleur);
        menuVue = MenuVue.creerVue(monControleur,creationUtilisateurVue,supprimerUtilisateur,traiterDemandes,listeThemes,listeTopic,creerTopic,topic,creationTheme);

//        listeThemes.setListeThemes(monControleur.getThemes());

        //menuVue..setContent();

    }



    public void goToConnexion() {

        this.maFenetre.setCenter(this.connexionVue.getNode());
    }


    public void gotoListetheme() {
        this.maFenetre.setCenter(this.listeThemes.getNode());
    }


    public void gotoListeTopic(String nom) throws ThemeInexistantException {

        this.maFenetre.setCenter(this.listeTopic.getNode());
        listeTopic.majTheme(nom);
        Collection<Topic>topics=monControleur.getTopicByTheme(nom);
        listeTopic.setListeTopics(topics);


    }
    public void goToSaisieMotDePasse() {

        this.maFenetre.setCenter(this.motDePasseVue.getNode());


    }

    public void goToMenu() {

        this.menuVue.checkVisibility();
        this.maFenetre.setCenter(this.menuVue.getNode());
    }



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
        return vue;//
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


    public void gotoCreerTopic(String nomDuTheme) {
        this.maFenetre.setCenter(this.creerTopic.getNode());
        this.creerTopic.majTheme(nomDuTheme);

    }

    public void gotoTopic(Topic topic) throws TopicInexistantException {
        this.topic.majTopic(topic);
        this.maFenetre.setCenter(this.topic.getNode());
        this.topic.setListeMessages(this.monControleur.getMessageByTopic(topic));
    }

    public void gotoCreerTheme() {
        this.maFenetre.setCenter(creationTheme.getNode());
    }



}
