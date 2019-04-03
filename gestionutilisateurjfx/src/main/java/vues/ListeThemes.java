package vues;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import modele.forum.Theme;
import modele.inscription.InscriptionPotentielle;
import modele.personnes.Personne;
import vues.connexion.ConnexionVue;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;


public class ListeThemes {


    Controleur monControleur;

    @FXML
    ListView<Theme> listeThemes;


    public static ListeThemes creerVue(Controleur c) {
        URL location = ConnexionVue.class.getResource("/vues/connexion/connexion.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListeThemes vue = fxmlLoader.getController();
        vue.setMonControleur(c);

        return vue;
    }


    public void setListeThemes(Collection<Theme> themes) {
        this.listeThemes.getItems().setAll(themes);
        this.listeThemes.setCellFactory(new Callback<ListView<Theme>, ListCell<Theme>>() {
            public ListCell<Theme> call(ListView<Theme> param) {
                ListCell<Theme> cell = new ListCell<Theme>(){
                    @Override
                    protected void updateItem(Theme t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getIdentifiant() + " - " + t.getNom());
                        }
                    }
                };
                return cell;
            }
        });
    }

    public void click(MouseEvent mouseEvent) {
        System.out.println();
    }


    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }


    public void choisir(ActionEvent actionEvent) {

        Theme theme = listeThemes.getSelectionModel().getSelectedItem();

        if (Objects.isNull(theme)) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Vous devez sélectionner un theme");
        }
        else {
            monControleur.gototheme(theme);
        }

    }


}
