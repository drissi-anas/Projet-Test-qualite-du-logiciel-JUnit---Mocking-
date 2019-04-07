package vues;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import modele.forum.Theme;
import modele.forum.Topic;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;

public class ThemeVue {

    @FXML
    private Label nomDuTheme;

    @FXML
    ListView<Topic> listeTopics;

    Controleur monControleur;

    public static ThemeVue creerVue(Controleur c) {
        URL location = ThemeVue.class.getResource("/vues/new/theme.fxml");
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

    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    public void majTheme(String nomTheme){
        nomDuTheme.setText(nomTheme);
    }
    public void setListeTopics(Collection<Topic> topics) {
        this.listeTopics.getItems().setAll(topics);
        this.listeTopics.setCellFactory(new Callback<ListView<Topic>, ListCell<Topic>>() {
            public ListCell<Topic> call(ListView<Topic> param) {
                ListCell<Topic> cell = new ListCell<Topic>(){
                    @Override
                    protected void updateItem(Topic t, boolean bln) {
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
    public void choisirTopic(ActionEvent actionEvent) {

        Topic topic = listeTopics.getSelectionModel().getSelectedItem();

        if (Objects.isNull(topic)) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Vous devez sélectionner un theme");
        }
        else {
            monControleur.gototopic(topic);
        }

    }

    public void click(MouseEvent mouseEvent) {
        System.out.println();
    }


    public void goToCreationEquipe(ActionEvent event) {
        monControleur.goToCreerEquipe(nomDuTheme.getText());
    }


}
