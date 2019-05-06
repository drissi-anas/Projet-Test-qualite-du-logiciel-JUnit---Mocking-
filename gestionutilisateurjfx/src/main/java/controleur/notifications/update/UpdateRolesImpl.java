package controleur.notifications.update;

import controleur.notifications.NotificationImpl;
import vues.utilisateur.DemandeUtilisateur;

import java.util.Collection;

import static controleur.notifications.Notification.TypeNotification.UPDATE_COLLECTION;

public class UpdateRolesImpl extends NotificationImpl {


    public UpdateRolesImpl(Collection<String> demandes) {
        super(UPDATE_COLLECTION, "");
        this.roles = demandes;

    }





    Collection<String> roles;


    @Override
    public void visit(DemandeUtilisateur traiterDemandes) {
        traiterDemandes.setRoles(roles);
    }


}
