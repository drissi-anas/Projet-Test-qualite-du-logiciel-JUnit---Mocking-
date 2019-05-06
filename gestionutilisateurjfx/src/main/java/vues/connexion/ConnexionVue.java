package vues.connexion;

import controleur.Controleur;
import controleur.erreurs.PseudoInexistantJFXException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Created by YohanBoichut on 04/12/2016.
 */
public class ConnexionVue {

    @FXML
    TextField nom;


    @FXML
    Button boutonValider;

    Controleur monControleur;


    public Controleur getMonControleur() {
        return monControleur;
    }

    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }



    public static ConnexionVue creerVue(Controleur c) {
        URL location = ConnexionVue.class.getResource("/vues/connexion/connexion.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConnexionVue vue = fxmlLoader.getController();
        vue.setMonControleur(c);

        return vue;
    }

    @FXML
    private VBox node;

    public Node getNode() {
        return node;
    }

    public void validerNom(ActionEvent actionEvent) {

        String nom = this.nom.getText();
        if (nom != null && nom.length()>0) {
            try {
                this.monControleur.saisieNom(this.nom.getText());
            } catch (PseudoInexistantJFXException e) {
                Alert nomInexistant = new Alert(Alert.AlertType.ERROR);
                nomInexistant.setTitle("Erreur");
                nomInexistant.setHeaderText("Problème de connexion");
                nomInexistant.setContentText("Le pseudo n'est pas enregistré dans le SI");
                nomInexistant.showAndWait();
            }
        }
        else {
            Alert nomInexistant = new Alert(Alert.AlertType.ERROR);
            nomInexistant.setTitle("Erreur");
            nomInexistant.setHeaderText("Problème de connexion");
            nomInexistant.setContentText("le champ est obligatoire");
            nomInexistant.showAndWait();
        }

    }

    public void demanderInscription(ActionEvent actionEvent) {
        this.monControleur.demandeInscription();
    }
}
