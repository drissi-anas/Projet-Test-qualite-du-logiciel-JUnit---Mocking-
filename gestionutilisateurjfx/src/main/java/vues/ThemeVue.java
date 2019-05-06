package vues;

import controleur.Controleur;
import controleur.notifications.Notification;
import controleur.notifications.Sujet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import modele.forum.Topic;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;

public class ThemeVue implements Sujet {

    @FXML
    private Button choisirTopics;
    @FXML
    private Button creerTopic;
    @FXML
    private AnchorPane node;
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
        c.inscription(vue);
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
    public void choisirTopic(ActionEvent actionEvent) throws TopicInexistantexception {

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


    public void creerTopic(ActionEvent event) {
        monControleur.gotoCreerTopic(nomDuTheme.getText());
    }


    public Node getNode() {
        return node;
    }


    @Override
    public void notifier(Notification notification) {

    }
}
