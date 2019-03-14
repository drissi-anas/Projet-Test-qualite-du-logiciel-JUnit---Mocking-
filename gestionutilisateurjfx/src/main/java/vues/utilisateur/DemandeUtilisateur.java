package vues.utilisateur;

import controleur.Controleur;
import controleur.notifications.Notification;
import controleur.notifications.Sujet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

/**
 * Created by YohanBoichut on 05/12/2016.
 */
public class DemandeUtilisateur implements Sujet {

    @FXML
    VBox node;


    @FXML
    TextField nom;

    @FXML
    PasswordField motDePasse;

    @FXML
    ComboBox<String> roles;

    public VBox getNode() {
        return node;
    }


    Controleur monControleur;

    public Controleur getMonControleur() {
        return monControleur;
    }

    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    public static DemandeUtilisateur creerVue(Controleur c) {
        URL location = DemandeUtilisateur.class.getResource("/vues/utilisateur/demandeinscription.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DemandeUtilisateur vue = fxmlLoader.getController();
        c.inscription(vue);
        vue.setMonControleur(c);
        return vue;
    }



    public void setRoles(Collection<String> roles) {
        this.roles.getItems().setAll(roles);
    }


    public void enregistrerNouvelUtilisateur(ActionEvent actionEvent) {

        String mot = motDePasse.getText();

        String role = roles.getValue();

        String pseudo = nom.getText();


        if (pseudo == null || mot == null || role == null || mot.length() == 0 || role.length() == 0 || pseudo.length() ==0) {
            Alert nomInexistant = new Alert(Alert.AlertType.ERROR);
            nomInexistant.setTitle("Erreur");
            nomInexistant.setHeaderText("Problème de connexion");
            nomInexistant.setContentText("Tous les champs sont obligatoires !!");
            nomInexistant.showAndWait();
        }
        else {

            this.monControleur.demanderInscription(pseudo,mot,role);


        }
    }

    private void informerUtilisateur(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,message, ButtonType.OK);
        alert.showAndWait();
    }


    private void reinitialiserChamps() {
        this.roles.getSelectionModel().selectFirst();
        this.motDePasse.setText("");
        this.nom.setText("");
    }

    @Override
    public void notifier(Notification notification) {
        switch (notification.getTypeNotification()) {
            case CONFIRMATION_DEMANDE : {
                informerUtilisateur(notification.getMessage());
                return;
            }

            case RESET_CHAMPS:{
                reinitialiserChamps();
                return;
            }

            case UPDATE_COLLECTION: {
                notification.visit(this);
            }

        }

    }

    public void annuler(ActionEvent actionEvent) {
        monControleur.gotoConnexion();
    }
}
