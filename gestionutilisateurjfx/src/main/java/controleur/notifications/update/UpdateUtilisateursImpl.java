package controleur.notifications.update;

import controleur.notifications.NotificationImpl;
import modele.personnes.Personne;

import vues.admin.SupprimerUtilisateur;

import java.util.Collection;

import static controleur.notifications.Notification.TypeNotification.UPDATE_COLLECTION;

public class UpdateUtilisateursImpl extends NotificationImpl {


    public UpdateUtilisateursImpl(Collection<Personne> demandes) {
        super(UPDATE_COLLECTION, "");
        this.utilisateurs = demandes;

    }





    Collection<Personne> utilisateurs;


    @Override
    public void visit(SupprimerUtilisateur supprimerUtilisateur) {
        supprimerUtilisateur.setListeUtilisateurs(utilisateurs);
    }
}
