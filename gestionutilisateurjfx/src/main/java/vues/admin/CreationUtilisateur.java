package vues.admin;

import controleur.Controleur;
import controleur.erreurs.UtilisateurDejaExistantJFXException;
import controleur.notifications.Notification;
import controleur.notifications.Sujet;
import facade.erreurs.InformationManquanteException;
import facade.erreurs.RoleDejaAttribueException;
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
public class CreationUtilisateur implements Sujet {
    @FXML
    private ComboBox roles;
    @FXML
    VBox node;


    @FXML
    TextField nom;

    @FXML
    PasswordField motDePasse;

    @FXML
    PasswordField confirmationMotDePasse;

    @FXML
    Button enregistreUser;


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

    public static CreationUtilisateur creerVue(Controleur c) {
        URL location = CreationUtilisateur.class.getResource("/vues/admin/creationutilisateur.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CreationUtilisateur vue = fxmlLoader.getController();
        vue.setMonControleur(c);
        c.inscription(vue);
        return vue;
    }



    private void reinitialiserChamps() {
        this.confirmationMotDePasse.setText("");
        this.motDePasse.setText("");
        this.nom.setText("");

    }


    public void enregistrerNouvelUtilisateur(ActionEvent actionEvent) throws RoleDejaAttribueException, InformationManquanteException {

        String mot = motDePasse.getText();

        String confirmation = confirmationMotDePasse.getText();

        String pseudo = nom.getText();
        String role= roles.getValue().toString();


        if (pseudo == null || mot == null || confirmation == null || mot.length() == 0 || confirmation.length() == 0 || pseudo.length() ==0) {
            Alert nomInexistant = new Alert(Alert.AlertType.ERROR);
            nomInexistant.setTitle("Erreur");
            nomInexistant.setHeaderText("Problème de connexion");
            nomInexistant.setContentText("Tous les champs sont obligatoires !!");
            nomInexistant.showAndWait();
        }
        else {
            try {
                if (confirmation.equals(mot)) {
                    this.monControleur.enregistrerNouvelUtilisateur(pseudo, mot,role);
                }
                else {
                    Alert nomInexistant = new Alert(Alert.AlertType.ERROR);
                    nomInexistant.setTitle("Erreur");
                    nomInexistant.setHeaderText("Problème de connexion");
                    nomInexistant.setContentText("le mot de passe n'est pas confirmé !!");
                    nomInexistant.showAndWait();
                }
            } catch (UtilisateurDejaExistantJFXException e) {
                Alert nomInexistant = new Alert(Alert.AlertType.ERROR);
                nomInexistant.setTitle("Erreur");
                nomInexistant.setHeaderText("Problème de connexion");
                nomInexistant.setContentText("Le pseudo est déjà utilisé !!");
                nomInexistant.showAndWait();
            }

        }
    }

    private void informerUtilisateur(String message) {
        Alert nomInexistant = new Alert(Alert.AlertType.INFORMATION);
        nomInexistant.setTitle("News");
        nomInexistant.setHeaderText("Création d'un nouvel utilisateur");
        nomInexistant.setContentText(message);
        nomInexistant.showAndWait();

    }

    public void setRoles(Collection<String> roles) {
        this.roles.getItems().setAll(roles);
    }

    @Override
    public void notifier(Notification notification) {
        switch (notification.getTypeNotification()) {
            case RESET_CHAMPS: {
                this.reinitialiserChamps();
                return;
            }


            case CONFIRMATION_CREATION: {
                this.informerUtilisateur(notification.getMessage());
                return;
            }

            case UPDATE_COLLECTION: {
                notification.visit(this);
            }
        }
    }
}
