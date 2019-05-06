package controleur.notifications;

import vues.ListeThemes;
import vues.ThemeVue;
import vues.admin.CreationUtilisateur;
import vues.admin.SupprimerUtilisateur;
import vues.admin.TraiterDemandes;
import vues.utilisateur.DemandeUtilisateur;

public class NotificationImpl implements Notification {
    String message;
    TypeNotification typeNotification;


    public NotificationImpl(TypeNotification typeNotification, String message) {
        this.message = message;
        this.typeNotification = typeNotification;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public TypeNotification getTypeNotification() {
        return typeNotification;
    }

    @Override
    public void visit(TraiterDemandes traiterDemandes) {

    }

    @Override
    public void visit(SupprimerUtilisateur supprimerUtilisateur) {

    }


    @Override
    public void visit(DemandeUtilisateur traiterDemandes) {
    }

    @Override
    public void visit(ListeThemes listeThemes) {

    }

    @Override
    public void visit(ThemeVue listeTopic) {

    }

    @Override
    public void visit(CreationUtilisateur creationUtilisateur) {

    }
}
