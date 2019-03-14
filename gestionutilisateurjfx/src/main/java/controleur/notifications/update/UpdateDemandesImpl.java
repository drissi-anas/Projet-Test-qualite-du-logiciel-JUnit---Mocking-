package controleur.notifications.update;

import controleur.notifications.NotificationImpl;
import modele.inscription.InscriptionPotentielle;

import vues.admin.TraiterDemandes;

import java.util.Collection;

import static controleur.notifications.Notification.TypeNotification.UPDATE_COLLECTION;

public class UpdateDemandesImpl extends NotificationImpl {


    public UpdateDemandesImpl(Collection<InscriptionPotentielle> demandes) {
        super(UPDATE_COLLECTION, "");
        this.inscriptionPotentielles = demandes;

    }





    Collection<InscriptionPotentielle> inscriptionPotentielles;


    @Override
    public void visit(TraiterDemandes traiterDemandes) {
        traiterDemandes.setListeDemandes(inscriptionPotentielles);
    }
}
