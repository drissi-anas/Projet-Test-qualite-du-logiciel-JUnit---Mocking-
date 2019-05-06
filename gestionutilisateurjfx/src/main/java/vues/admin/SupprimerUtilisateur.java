package vues.admin;

import controleur.Controleur;
import controleur.notifications.Notification;
import controleur.notifications.Sujet;
import facade.erreurs.IndividuNonConnecteException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import modele.personnes.Personne;


import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by YohanBoichut on 05/12/2016.
 */
public class SupprimerUtilisateur implements Sujet {

    @FXML Button supprimeruser;
    @FXML
    VBox node;


    @FXML
    ListView<Personne> listeUtilisateurs;

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

    public static SupprimerUtilisateur creerVue(Controleur c) {
        URL location = SupprimerUtilisateur.class.getResource("/vues/admin/supprimerutilisateur.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SupprimerUtilisateur vue = fxmlLoader.getController();
        c.inscription(vue);
        vue.setMonControleur(c);
        return vue;
    }



    public void setListeUtilisateurs(Collection<Personne> utilisateurs) {
        this.listeUtilisateurs.getItems().setAll(utilisateurs);
        this.listeUtilisateurs.setCellFactory(new Callback<ListView<Personne>, ListCell<Personne>>() {
            public ListCell<Personne> call(ListView<Personne> param) {
                ListCell<Personne> cell = new ListCell<Personne>(){
                    @Override
                    protected void updateItem(Personne t, boolean bln) {
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

    public void supprimer(ActionEvent actionEvent) throws IndividuNonConnecteException {
        Personne utilisateur = listeUtilisateurs.getSelectionModel().getSelectedItem();
        if (Objects.isNull(utilisateur)) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Vous devez sélectionner un utilisateur");
        }
        else {
            monControleur.supprimerUtilisateur(utilisateur);
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

    @Override
    public void notifier(Notification notification) {
        switch (notification.getTypeNotification()) {
            case UPDATE_COLLECTION: {
                notification.visit(this);
                return;
            }

            case CONFIRMATION_SUPPRESSION: {
                this.informerUtilisateur(notification.getMessage());
                return;
            }

            case ERREUR_SUPPRESSION: {
                this.informerErreur(notification.getMessage());
            }

        }
    }
}
