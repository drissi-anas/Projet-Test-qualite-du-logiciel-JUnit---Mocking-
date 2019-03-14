package controleur.notifications;

import vues.admin.SupprimerUtilisateur;
import vues.admin.TraiterDemandes;
import vues.utilisateur.DemandeUtilisateur;

public interface Visiteur {

    void visit(TraiterDemandes traiterDemandes);

    void visit(SupprimerUtilisateur supprimerUtilisateur);

    void visit(DemandeUtilisateur creationUtilisateur);


}
