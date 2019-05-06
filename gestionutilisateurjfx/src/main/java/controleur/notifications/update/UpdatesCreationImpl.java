package controleur.notifications.update;

import controleur.notifications.Notification;
import controleur.notifications.NotificationImpl;
import vues.admin.CreationUtilisateur;
import vues.admin.TraiterDemandes;

import java.util.Collection;
import static controleur.notifications.Notification.TypeNotification.UPDATE_COLLECTION;


public class UpdatesCreationImpl extends NotificationImpl {

    Collection<String>roles;
    public UpdatesCreationImpl(Collection<String> roles) {
        super(UPDATE_COLLECTION,"");
        this.roles=roles;

    }


    @Override
    public void visit(CreationUtilisateur creationUtilisateur) {
        creationUtilisateur.setRoles(roles);
    }
}
