package controleur.notifications;

import controleur.notifications.update.UpdateDemandesImpl;
import controleur.notifications.update.UpdateRolesImpl;
import controleur.notifications.update.UpdateUtilisateursImpl;
import controleur.notifications.update.UpdatesCreationImpl;
import modele.inscription.InscriptionPotentielle;

import modele.personnes.Personne;

import java.util.Collection;

public interface Notification extends Visiteur{

     enum TypeNotification {UPDATE_COLLECTION,CONFIRMATION_DEMANDE,RESET_CHAMPS,CONFIRMATION_CREATION, CONFIRMATION_SUPPRESSION,CONFIRMATION_ACCEPTATION,CONFIRMATION_REFUS,ERREUR_CREATION,ERREUR_SUPPRESSION,ERREUR_ACCEPTATION,ERREUR_CONNEXION};

    static Notification creerNotification(TypeNotification typeNotification, String message) {
       return new NotificationImpl(typeNotification,message);
    }


    static Notification creerUpdateUtilisateur(Collection<Personne> utilisateurs) {
        return new UpdateUtilisateursImpl(utilisateurs);
    }

    static Notification creerUpdateDemandes(Collection<InscriptionPotentielle> inscriptionPotentielles) {
        return new UpdateDemandesImpl(inscriptionPotentielles);
    }


    static Notification creerUpdateRoles(Collection<String> roles) {
        return new UpdateRolesImpl(roles);
    }

    static Notification creerUpdateCreation(Collection<String> roles) {
        return new UpdatesCreationImpl(roles);
    }


    TypeNotification getTypeNotification();
    String getMessage();
}
