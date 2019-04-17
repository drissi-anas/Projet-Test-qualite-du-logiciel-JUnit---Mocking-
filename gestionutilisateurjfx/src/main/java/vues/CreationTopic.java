package vues;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import modele.forum.Theme;

import java.io.IOException;
import java.net.URL;

/**
 * Created by asus on 07/04/2019.
 */
public class CreationTopic {


    @FXML
    private AnchorPane node;
    @FXML
    private Button validerTopic;

    @FXML
    private Button annulerTopic;
    Controleur monControleur;
    @FXML
    TextField nomTopic;

    @FXML
    TextArea messageDuTopic;

    @FXML
    private Label nomDuTheme;

    public static CreationTopic creerVue(Controleur c) {
        URL location = CreationTopic.class.getResource("/vues/new/creationTopic.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CreationTopic vue = fxmlLoader.getController();
        vue.setMonControleur(c);

        return vue;
    }

    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    public void majTheme(String nomTheme){
        nomDuTheme.setText(nomTheme);
    }

    public void enregistrerNouveauTopic(ActionEvent event) {
        monControleur.creerTopic(nomTopic.getText(),nomDuTheme.getText(),messageDuTopic.getText());
    }

    public Node getNode() {
        return node;
    }



    public void annulerTopic(ActionEvent actionEvent) {
    }
}
