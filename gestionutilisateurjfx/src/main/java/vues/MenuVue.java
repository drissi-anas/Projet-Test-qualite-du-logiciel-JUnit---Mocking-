package vues;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import vues.admin.CreationUtilisateur;
import vues.admin.SupprimerUtilisateur;
import vues.admin.TraiterDemandes;

import java.io.IOException;
import java.net.URL;

/**
 * Created by YohanBoichut on 05/12/2016.
 */
public class MenuVue {

    @FXML
    Button quitter;
    Controleur monControleur;


    public Controleur getMonControleur() {
        return monControleur;
    }

    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    private CreationUtilisateur creationUtilisateur;
    private SupprimerUtilisateur supprUtilisateur;
    private TraiterDemandes traitDemandes;


    public void setCreationUtilisateur(CreationUtilisateur creationUtilisateur) {
        this.creationUtilisateur = creationUtilisateur;
    }

    public void setSupprUtilisateur(SupprimerUtilisateur supprUtilisateur) {
        this.supprUtilisateur = supprUtilisateur;
    }

    public void setTraitDemandes(TraiterDemandes traitDemandes) {
        this.traitDemandes = traitDemandes;
    }

    public static MenuVue creerVue(Controleur c, CreationUtilisateur creationUtilisateur, SupprimerUtilisateur supprimerUtilisateur, TraiterDemandes traiterDemandes) {
        URL location = MenuVue.class.getResource("/vues/menu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MenuVue vue = fxmlLoader.getController();
        vue.setMonControleur(c);
        vue.ajouterUtilisateur.setContent(creationUtilisateur.getNode());
        vue.supprimerUtilisateur.setContent(supprimerUtilisateur.getNode());
        vue.traiterDemandes.setContent(traiterDemandes.getNode());
        vue.setCreationUtilisateur(creationUtilisateur);
        vue.setSupprUtilisateur(supprimerUtilisateur);
        vue.setTraitDemandes(traiterDemandes);
        vue.checkVisibility();
        return vue;
    }


    void checkVisibility() {

        ajouterUtilisateur.setDisable(true);
        supprimerUtilisateur.setDisable(true);
        traiterDemandes.setDisable(true);
        if (monControleur.isAdmin()) {
            ajouterUtilisateur.setDisable(false);
            supprimerUtilisateur.setDisable(false);
            traiterDemandes.setDisable(false);
        }
        if (monControleur.isModerateur()) {
            traiterDemandes.setDisable(false);
        }


    }

    @FXML
    private VBox node;


    @FXML
    TitledPane ajouterUtilisateur;

    @FXML
    TitledPane supprimerUtilisateur;

    @FXML
    TitledPane traiterDemandes;


    public Node getNode() {
        return node;
    }

    public void quitter(ActionEvent actionEvent) {
        this.monControleur.quitter();
    }

    public void chargerUtilisateur(MouseEvent mouseEvent) {
        this.supprUtilisateur.setListeUtilisateurs(monControleur.getUtilisateurs());
    }

    public void chargerDemandes(MouseEvent mouseEvent) {
        this.traitDemandes.setListeDemandes(monControleur.getDemandes());
    }
}
