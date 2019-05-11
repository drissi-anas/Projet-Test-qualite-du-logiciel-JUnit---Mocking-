package vues.admin;

import controleur.Controleur;
import controleur.notifications.Notification;
import controleur.notifications.Sujet;
import facade.erreurs.ActionImpossibleException;
import facade.erreurs.IndividuNonConnecteException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import modele.inscription.InscriptionPotentielle;


import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by YohanBoichut on 05/12/2016.
 */
public class TraiterDemandes implements Sujet {

    @FXML
    VBox node;


    @FXML
    ListView<InscriptionPotentielle> listeDemandes;

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

    public static TraiterDemandes creerVue(Controleur c) {
        URL location = TraiterDemandes.class.getResource("/vues/admin/traiterdemandes.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TraiterDemandes vue = fxmlLoader.getController();
        c.inscription(vue);
        vue.setMonControleur(c);
        return vue;
    }



    public void setListeDemandes(Collection<InscriptionPotentielle> utilisateurs) {
        this.listeDemandes.getItems().setAll(utilisateurs);
        this.listeDemandes.setCellFactory(new Callback<ListView<InscriptionPotentielle>, ListCell<InscriptionPotentielle>>() {
            public ListCell<InscriptionPotentielle> call(ListView<InscriptionPotentielle> param) {
                ListCell<InscriptionPotentielle> cell = new ListCell<InscriptionPotentielle>(){
                    @Override
                    protected void updateItem(InscriptionPotentielle t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getIdentifiant() + " - " + t.getNom() + " - "+t.getRoleDemande());
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

    public void accepter(ActionEvent actionEvent) throws ActionImpossibleException, IndividuNonConnecteException {
        InscriptionPotentielle inscriptionPotentielle = listeDemandes.getSelectionModel().getSelectedItem();
        if (Objects.isNull(inscriptionPotentielle)) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Vous devez sélectionner une demande");
        }
        else {
            monControleur.accepterDemande(inscriptionPotentielle);
        }
    }

    private void informerErreur(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR,s,ButtonType.OK);
        alert.showAndWait();
    }


    private void informerUtilisateur(String s) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,s,ButtonType.OK);
        alert.showAndWait();

    }

    public void refuser(ActionEvent actionEvent) throws ActionImpossibleException, IndividuNonConnecteException {
        InscriptionPotentielle inscriptionPotentielle = listeDemandes.getSelectionModel().getSelectedItem();
        if (Objects.isNull(inscriptionPotentielle)) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Vous devez sélectionner une demande");
        }
        else {
            monControleur.refuserDemandes(inscriptionPotentielle);
        }
    }

    @Override
    public void notifier(Notification notification) {
        switch (notification.getTypeNotification()) {
            case CONFIRMATION_ACCEPTATION: {
                this.informerUtilisateur(notification.getMessage());
                return;
            }
            case CONFIRMATION_REFUS: {
                this.informerUtilisateur(notification.getMessage());
                return;
            }

            case ERREUR_ACCEPTATION: {
                this.informerErreur(notification.getMessage());
                return;
            }

            case UPDATE_COLLECTION: {
                notification.visit(this);
            }

        }
    }
}
