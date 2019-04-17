package controleur.notifications;

import vues.ListeThemes;
import vues.ThemeVue;
import vues.admin.SupprimerUtilisateur;
import vues.admin.TraiterDemandes;
import vues.utilisateur.DemandeUtilisateur;

public interface Visiteur {

    void visit(TraiterDemandes traiterDemandes);

    void visit(SupprimerUtilisateur supprimerUtilisateur);

    void visit(DemandeUtilisateur creationUtilisateur);
    void visit (ListeThemes listeThemes);
    void visit (ThemeVue listeTopic);

}
