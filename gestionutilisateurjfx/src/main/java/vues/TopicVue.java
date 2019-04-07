package vues;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import modele.forum.Message;
import modele.forum.Theme;
import modele.forum.Topic;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

/**
 * Created by asus on 07/04/2019.
 */
public class TopicVue {

    Controleur monControleur;

    @FXML
    Label nomDuTopic;

    @FXML
    ListView<Message> listeMessages;

    @FXML
    TextArea zoneTexte;

    public static TopicVue creerVue(Controleur c) {
        URL location = TopicVue.class.getResource("/vues/new/topic.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TopicVue vue = fxmlLoader.getController();
        vue.setMonControleur(c);

        return vue;
    }

    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    public void majTopic(Topic topic){
        nomDuTopic.setText(topic.getNom());
    }


    public void click(MouseEvent mouseEvent) {
        System.out.println();
    }

    public void setListeMessages(Collection<Message> messages) {
        this.listeMessages.getItems().setAll(messages);
        this.listeMessages.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            public ListCell<Message> call(ListView<Message> param) {
                ListCell<Message> cell = new ListCell<Message>(){
                    @Override
                    protected void updateItem(Message t, boolean bln) {
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

    public void ajouterMessage(ActionEvent event) {
        monControleur.ajouterMessage(nomDuTopic.getText(),zoneTexte.getText());
    }


}
