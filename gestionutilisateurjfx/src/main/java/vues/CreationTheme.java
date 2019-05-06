package vues;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class CreationTheme {

    @FXML
    private TextField labelTheme;
    @FXML
    private Button validerTheme;
    @FXML
    private AnchorPane node;
    Controleur monControleur;


    public static CreationTheme creerVue(Controleur c) {
        URL location = CreationTheme.class.getResource("/vues/new/creationTheme.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CreationTheme vue = fxmlLoader.getController();

        vue.setMonControleur(c);

        return vue;
    }



    private void setMonControleur(Controleur c) {
        this.monControleur=c;
    }

    public Node getNode() {
        return node;
    }

    public void validerTheme(ActionEvent actionEvent) {
        this.monControleur.validerTheme(labelTheme.getText());
    }
}
