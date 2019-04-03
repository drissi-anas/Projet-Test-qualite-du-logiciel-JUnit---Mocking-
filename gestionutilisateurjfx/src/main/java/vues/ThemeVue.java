package vues;

import controleur.Controleur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import modele.forum.Theme;
import vues.connexion.ConnexionVue;

import java.io.IOException;
import java.net.URL;

public class ThemeVue {


    @FXML
    private Label nomDuTheme;

    Controleur monControleur;

    public static ThemeVue creerVue(Controleur c) {
        URL location = ConnexionVue.class.getResource("/vues/connexion/connexion.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ThemeVue vue = fxmlLoader.getController();
        vue.setMonControleur(c);

        return vue;
    }

    public void majTheme(Theme theme){
        nomDuTheme.setText(theme.getNom());
    }



    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }





}
