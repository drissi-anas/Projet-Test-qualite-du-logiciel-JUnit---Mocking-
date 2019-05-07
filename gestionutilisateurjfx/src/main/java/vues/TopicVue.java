package vues;

import controleur.Controleur;
import facade.erreurs.ActionImpossibleException;
import facade.erreurs.ThemeInexistantException;
import facade.erreurs.TopicInexistantException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import modele.forum.Message;
import modele.forum.Theme;
import modele.forum.Topic;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by asus on 07/04/2019.
 */
public class TopicVue {


    public Button supprimer;
    @FXML
    private TextArea votreMessage;
    @FXML
    private ListView listeMessage;

    @FXML
    private Label nomDuTheme;

    @FXML
    private Button retour;

    @FXML
    private Button validerMessage;

    @FXML
    private AnchorPane node;

    Controleur monControleur;

    @FXML
    Label nomDuTopic;



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
        nomDuTheme.setText(topic.getTheme().getNom());
    }


    public void click(MouseEvent mouseEvent) {
        System.out.println();
    }

    public void setListeMessages(Collection<Message> messages) {
        this.listeMessage.getItems().setAll(messages);
        this.listeMessage.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            public ListCell<Message> call(ListView<Message> param) {
                ListCell<Message> cell = new ListCell<Message>(){
                    @Override
                    protected void updateItem(Message t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getAuteur() + " - " + t.getText());
                        }
                    }
                };
                return cell;
            }
        });
    }

    public void ajouterMessage(ActionEvent event) throws ThemeInexistantException, TopicInexistantException {
        monControleur.ajouterMessage(nomDuTheme.getText(),nomDuTopic.getText(),votreMessage.getText());
    }


    public Node getNode() {
        return node;
    }

    public void supprimerMessage(ActionEvent actionEvent) throws TopicInexistantException {
        Message m = (Message) listeMessage.getSelectionModel().getSelectedItem();

        if (Objects.isNull(m)) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Vous devez sélectionner un message");
        }
        else {

            try {
                monControleur.supprimerMessage(m);
            } catch (ActionImpossibleException e) {
                Alert actionImpos = new Alert(Alert.AlertType.ERROR);
                actionImpos.setTitle("Erreur");
                actionImpos.setHeaderText("Action Impossible");
                actionImpos.setContentText("Vous ne pouvez pas supprimer ce message !");
                actionImpos.showAndWait();
            }

        }


    }
}
