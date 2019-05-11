package controleur;

import controleur.erreurs.MotDePasseIncorrectJFXException;
import controleur.erreurs.PseudoInexistantJFXException;
import controleur.erreurs.UtilisateurDejaExistantJFXException;
import controleur.notifications.*;
import controleur.notifications.update.UpdateDemandesImpl;
import controleur.notifications.update.UpdateRolesImpl;
import controleur.notifications.update.UpdatesCreationImpl;
import facade.AdminService;
import facade.BasiquesOffLineService;
import facade.ConnexionService;
import facade.ForumService;
import facade.erreurs.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modele.forum.Message;
import modele.forum.Theme;
import modele.forum.Topic;
import modele.inscription.InscriptionPotentielle;

import modele.personnes.Personne;

import vues.FenetrePrincipale;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by YohanBoichut on 04/12/2016.
 */
public class Controleur implements Observateur {



    Collection<Sujet> sujets;

    ConnexionService connexionService;
    AdminService adminService;
    BasiquesOffLineService basiquesOffLineService;
    ForumService forumService;

    FenetrePrincipale maFenetre;

    String nom;
    Personne identifiant;

    Stage stage;



    public Controleur(ConnexionService connexionService, AdminService adminService, BasiquesOffLineService basiquesOffLineService, Stage stage,ForumService forumService){

        this.forumService=forumService;
        this.connexionService = connexionService;
        this.adminService = adminService;
        this.basiquesOffLineService = basiquesOffLineService;
        this.stage = stage;
        this.sujets = new ArrayList<>();
    }



    public void saisieNom(String nom) throws PseudoInexistantJFXException {
        this.nom = nom;
        if (this.connexionService.estUnUtilisateurConnu(nom)) {
            this.maFenetre.goToSaisieMotDePasse();
        }
        else {
            throw new PseudoInexistantJFXException();
        }
    }


    public void validerMotDePasse(String text) throws MotDePasseIncorrectJFXException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        try {
            identifiant = this.connexionService.connexion(nom,text);

            this.broadcastNotification(Notification.creerUpdateUtilisateur(this.adminService.getListeUtilisateur(identifiant.getIdentifiant())));
            this.broadcastNotification(new UpdateDemandesImpl(this.adminService.getListeDesDemandesNonTraitees(identifiant.getIdentifiant())));
            this.broadcastNotification(new UpdatesCreationImpl(AdminService.getRoles()));
            this.maFenetre.goToMenu();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            throw new MotDePasseIncorrectJFXException();
        }

        catch (AccesRefuseException e) {
            this.broadcastNotification(Notification.creerUpdateUtilisateur(new ArrayList<>()));
            this.broadcastNotification(new UpdateDemandesImpl(this.adminService.getListeDesDemandesNonTraitees(identifiant.getIdentifiant())));
            this.broadcastNotification(new UpdatesCreationImpl(AdminService.getRoles()));
            this.maFenetre.goToMenu();
        } catch (InformationManquanteException e) {
            e.printStackTrace();
        } catch (ActionImpossibleException e) {
            e.printStackTrace();
        }
    }

    public boolean isAdmin() {
        return Objects.nonNull(this.identifiant) && connexionService.estUnAdmin(this.identifiant.getIdentifiant());
    }



    public void run() {

        this.maFenetre = FenetrePrincipale.creerVue(this,stage);
        this.maFenetre.goToConnexion();
        this.maFenetre.show();

    }



    public void enregistrerNouvelUtilisateur(String pseudo, String mot, String role) throws UtilisateurDejaExistantJFXException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        try {
            Personne p = this.adminService.creerUtilisateur(this.identifiant.getIdentifiant(),pseudo,mot);
            this.adminService.associerRoleUtilisateur(identifiant.getIdentifiant(),p.getIdentifiant(),role);
            this.broadcastNotification(Notification.creerNotification(Notification.TypeNotification.CONFIRMATION_CREATION,"L'utilisateur "+p.getNom() + " a bien été créé"));
            this.broadcastNotification(Notification.creerNotification(Notification.TypeNotification.RESET_CHAMPS,""));
            this.maFenetre.goToMenu();

        } catch (UtilisateurDejaExistantException e) {
            throw new UtilisateurDejaExistantJFXException();
        } catch (InformationManquanteException e) {
            e.printStackTrace();
        }
    }

    public void demanderInscription(String pseudo, String mot, String role) throws UtilisateurDejaExistantException, InformationManquanteException {
        this.basiquesOffLineService.posterDemandeInscription(pseudo,mot,role);
        this.broadcastNotification(Notification.creerNotification(Notification.TypeNotification.CONFIRMATION_DEMANDE,pseudo+", votre demande a été soumise aux responsables !"));
        this.broadcastNotification(Notification.creerNotification(Notification.TypeNotification.RESET_CHAMPS,""));
        this.maFenetre.goToConnexion();
    }


    public void gotoConnexion() {
        this.maFenetre.goToConnexion();
    }

    public void demandeInscription() {

        this.broadcastNotification(new UpdateRolesImpl(AdminService.getRoles()));
        this.maFenetre.goToDemandeInscription();
    }

    public boolean isModerateur() {
        return Objects.nonNull(this.identifiant) && this.connexionService.estUnModerateur(this.identifiant.getIdentifiant());
    }

    public void quitter() {
        this.connexionService.deconnexion(this.identifiant.getIdentifiant());
        this.maFenetre.goToConnexion();
    }

    public Collection<Personne> getUtilisateurs() throws IndividuNonConnecteException {
        return this.adminService.getListeUtilisateur(identifiant.getIdentifiant());

    }

    public void supprimerUtilisateur(Personne utilisateur) throws IndividuNonConnecteException, ActionImpossibleException {
        if (utilisateur.getIdentifiant() == this.identifiant.getIdentifiant()) {
            this.broadcastNotification(Notification.creerNotification(Notification.TypeNotification.ERREUR_SUPPRESSION,"On ne peut pas se supprimer du SI"));
        }
        else {

            this.adminService.supprimerUtilisateur(this.identifiant.getIdentifiant(),utilisateur.getIdentifiant());
            this.broadcastNotification(Notification.creerUpdateUtilisateur(this.adminService.getListeUtilisateur(identifiant.getIdentifiant())));
            this.broadcastNotification(Notification.creerNotification(Notification.TypeNotification.CONFIRMATION_SUPPRESSION,"L'utilisateur "+utilisateur.getNom()+ " a bien été supprimé"));
        }
    }

    public void accepterDemande(InscriptionPotentielle inscriptionPotentielle) throws ActionImpossibleException, IndividuNonConnecteException {
        this.adminService.validerInscription(identifiant.getIdentifiant(),inscriptionPotentielle.getIdentifiant());
        this.broadcastNotification(Notification.creerUpdateDemandes(this.adminService.getListeDesDemandesNonTraitees(identifiant.getIdentifiant())));
        this.broadcastNotification(Notification.creerNotification(Notification.TypeNotification.CONFIRMATION_ACCEPTATION,"La demande "+inscriptionPotentielle.getIdentifiant() + " concernant "+inscriptionPotentielle.getNom()+ " a été validée"));
    }


    public void refuserDemandes(InscriptionPotentielle inscriptionPotentielle) throws ActionImpossibleException, IndividuNonConnecteException {
        this.adminService.refuserInscription(identifiant.getIdentifiant(),inscriptionPotentielle.getIdentifiant());
        this.broadcastNotification(Notification.creerUpdateDemandes(this.adminService.getListeDesDemandesNonTraitees(identifiant.getIdentifiant())));
        this.broadcastNotification(Notification.creerNotification(Notification.TypeNotification.CONFIRMATION_REFUS,"La demande "+inscriptionPotentielle.getIdentifiant() + " concernant "+inscriptionPotentielle.getNom()+ " a été refusée"));

    }

    public Collection<InscriptionPotentielle> getDemandes() throws ActionImpossibleException, IndividuNonConnecteException {
        return this.adminService.getListeDesDemandesNonTraitees(identifiant.getIdentifiant());
    }

    @Override
    public void inscription(Sujet sujet) {
        this.sujets.add(sujet);
    }

    @Override
    public void broadcastNotification(Notification notification) {
        for (Sujet sujet : sujets) {
            sujet.notifier(notification);
        }
    }

    //// Methodes ajoutées

   /* public void gototheme(String nomTheme) {
        ThemeVue themeVue = ThemeVue.creerVue(this);
        themeVue.majTheme(nomTheme);
        Theme theme=forumService.recupererTheme(nomTheme);
        themeVue.setListeTopics(forumService.getListeTopicPourUnTheme(theme));
    }*/

    public void gototopic(Topic topic) throws TopicInexistantException {
        this.maFenetre.gotoTopic(topic);

    }

    public void ajouterMessage(String nomDuTheme, String nomDuTopic, String texteMessage) throws ThemeInexistantException, TopicInexistantException {
        Theme t = forumService.recupererTheme(nomDuTheme);
        Topic topic = forumService.recupererTopic(nomDuTopic,nomDuTheme);
        Message m= null;
        try {
            m = forumService.creerMessage(identifiant.getNom(),topic,texteMessage);
        } catch (InformationManquanteException e) {
            e.printStackTrace();
        }
        this.forumService.ajouterMessage(topic,t,m);
        gototopic(topic);
    }

    public void creerTopic(String nomDuTopic, String messageDuTopicText, String themeDuTopic) throws ThemeInexistantException, TopicInexistantException {
        Theme theme = forumService.recupererTheme(themeDuTopic);
        Topic nouveauTopic = null;
        try {
            nouveauTopic = forumService.creerTopic(nomDuTopic,theme,identifiant.getNom());
            Message m1=forumService.creerMessage(identifiant.getNom(),nouveauTopic,messageDuTopicText);
            forumService.ajouterMessage(nouveauTopic,theme,m1);
            gototopic(nouveauTopic);
        } catch (NomTopicDejaExistantException e) {
            e.printStackTrace();
        } catch (InformationManquanteException e) {
            e.printStackTrace();
        }
    }

    public void gotoCreerTopic(String nomDuTheme) {
        this.maFenetre.gotoCreerTopic(nomDuTheme);

    }
//
    public Collection<Theme> getThemes() {
        return forumService.getListeTheme();
    }

    public void gotoListeThemes() {
        this.maFenetre.gotoListetheme();
    }

    public void gototheme(String nom) throws ThemeInexistantException {
        this.maFenetre.gotoListeTopic(nom);

    }

    public Collection<Topic> getTopicByTheme(String nomTheme) throws ThemeInexistantException{
        Theme t= forumService.recupererTheme(nomTheme);
        return forumService.getListeTopicPourUnTheme(t);

    }

    public Collection<Message> getMessageByTopic(Topic t) throws TopicInexistantException{
        return forumService.getListeMessagePourUnTopic(t);
    }

    public void gotoCreerTheme() {
        this.maFenetre.gotoCreerTheme();
    }

    public void validerTheme(String text) {
        try {
            forumService.creerTheme(text);
        } catch (InformationManquanteException e) {
            e.printStackTrace();
        }
        this.maFenetre.gotoListetheme();
    }

    public void supprimerMessage(Message m) throws TopicInexistantException, ActionImpossibleException {
       String nom = m.getAuteur();
        if(this.identifiant.getNom().equals(nom)){

                forumService.supprimerMessage(m,identifiant.getIdentifiant());
                this.maFenetre.gotoTopic(m.getTopic());
            }
        else if(this.isModerateur() || this.isAdmin()) {
            forumService.supprimerMessage(m,identifiant.getIdentifiant());
            this.maFenetre.gotoTopic(m.getTopic());
        }
        else {
            throw new ActionImpossibleException();
        }



    }

    public void supprimerTopic(Topic topic) throws ThemeInexistantException, ActionImpossibleException {
        forumService.supprimerTopic(topic,identifiant.getIdentifiant());
        this.maFenetre.gotoListeTopic(topic.getTheme().getNom());
    }


}
