package vues.connexion;

import controleur.Controleur;
import controleur.erreurs.MotDePasseIncorrectJFXException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

/**
 * Created by YohanBoichut on 04/12/2016.
 */
public class MotDePasseVue  {

    @FXML
    PasswordField motDePasse;
    @FXML
    Button boutonValidermdp;

    Controleur monControleur;


    public Controleur getMonControleur() {
        return monControleur;
    }

    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    public static MotDePasseVue creerVue(Controleur c) {
        URL location = MotDePasseVue.class.getResource("/vues/connexion/motdepasse.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MotDePasseVue vue = fxmlLoader.getController();
        vue.setMonControleur(c);

        return vue;
    }


    @FXML
    private VBox node;

    public Node getNode() {
        return node;
    }


    public void validerMotDePasse(ActionEvent actionEvent) {


        String mot = this.motDePasse.getText();
        System.out.println("Why???" + mot);
        if (mot ==null || mot.length()==0) {
            Alert nomInexistant = new Alert(Alert.AlertType.ERROR);
            nomInexistant.setTitle("Erreur");
            nomInexistant.setHeaderText("Problème de connexion");
            nomInexistant.setContentText("le champ est obligatoire");
            nomInexistant.showAndWait();
        }
        else {
            try {
                this.monControleur.validerMotDePasse(mot);
            } catch (MotDePasseIncorrectJFXException e) {
                Alert nomInexistant = new Alert(Alert.AlertType.ERROR);
                nomInexistant.setTitle("Erreur");
                nomInexistant.setHeaderText("Problème de connexion");
                nomInexistant.setContentText("Mot de passe incorrect !");
                nomInexistant.showAndWait();
            }
        }
    }


}
