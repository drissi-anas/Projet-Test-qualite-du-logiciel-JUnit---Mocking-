package controleur;

import controleur.erreurs.MotDePasseIncorrectJFXException;
import controleur.erreurs.PseudoInexistantJFXException;
import controleur.erreurs.UtilisateurDejaExistantJFXException;
import controleur.notifications.*;
import controleur.notifications.update.UpdateDemandesImpl;
import controleur.notifications.update.UpdateRolesImpl;
import facade.AdminService;
import facade.BasiquesOffLineService;
import facade.ConnexionService;
import facade.erreurs.AccesRefuseException;
import facade.erreurs.CoupleUtilisateurMDPInconnuException;
import facade.erreurs.RoleDejaAttribueException;
import facade.erreurs.UtilisateurDejaExistantException;
import javafx.stage.Stage;
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

    FenetrePrincipale maFenetre;

    String nom;
    Personne identifiant;

    Stage stage;

    public Controleur(ConnexionService connexionService, AdminService adminService, BasiquesOffLineService basiquesOffLineService, Stage stage){

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


    public void validerMotDePasse(String text) throws MotDePasseIncorrectJFXException {
        try {
            identifiant = this.connexionService.connexion(nom,text);

            this.broadcastNotification(Notification.creerUpdateUtilisateur(this.adminService.getListeUtilisateur(identifiant.getIdentifiant())));
            this.broadcastNotification(new UpdateDemandesImpl(this.adminService.getListeDesDemandesNonTraitees(identifiant.getIdentifiant())));

            this.maFenetre.goToMenu();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            throw new MotDePasseIncorrectJFXException();
        }

        catch (AccesRefuseException e) {
            this.broadcastNotification(Notification.creerUpdateUtilisateur(new ArrayList<>()));
            this.broadcastNotification(new UpdateDemandesImpl(this.adminService.getListeDesDemandesNonTraitees(identifiant.getIdentifiant())));
            this.maFenetre.goToMenu();
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



    public void enregistrerNouvelUtilisateur(String pseudo, String mot) throws UtilisateurDejaExistantJFXException {
        try {
            Personne p = this.adminService.creerUtilisateur(this.identifiant.getIdentifiant(),pseudo,mot);
            this.broadcastNotification(Notification.creerNotification(Notification.TypeNotification.CONFIRMATION_CREATION,"L'utilisateur "+p.getNom() + " a bien été créé"));
            this.broadcastNotification(Notification.creerNotification(Notification.TypeNotification.RESET_CHAMPS,""));
            this.maFenetre.goToMenu();

        } catch (UtilisateurDejaExistantException e) {
            throw new UtilisateurDejaExistantJFXException();
        }
    }

    public void demanderInscription(String pseudo, String mot, String role) {
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

    public Collection<Personne> getUtilisateurs() {
        return this.adminService.getListeUtilisateur(identifiant.getIdentifiant());

    }

    public void supprimerUtilisateur(Personne utilisateur) {
        if (utilisateur.getIdentifiant() == this.identifiant.getIdentifiant()) {
            this.broadcastNotification(Notification.creerNotification(Notification.TypeNotification.ERREUR_SUPPRESSION,"On ne peut pas se supprimer du SI"));
        }
        else {

            this.adminService.supprimerUtilisateur(this.identifiant.getIdentifiant(),utilisateur.getIdentifiant());
            this.broadcastNotification(Notification.creerUpdateUtilisateur(this.adminService.getListeUtilisateur(identifiant.getIdentifiant())));
            this.broadcastNotification(Notification.creerNotification(Notification.TypeNotification.CONFIRMATION_SUPPRESSION,"L'utilisateur "+utilisateur.getNom()+ " a bien été supprimé"));
        }
    }

    public void accepterDemande(InscriptionPotentielle inscriptionPotentielle) {
        try {
            this.adminService.validerInscription(identifiant.getIdentifiant(),inscriptionPotentielle.getIdentifiant());
            this.broadcastNotification(Notification.creerUpdateDemandes(this.adminService.getListeDesDemandesNonTraitees(identifiant.getIdentifiant())));
            this.broadcastNotification(Notification.creerNotification(Notification.TypeNotification.CONFIRMATION_ACCEPTATION,"La demande "+inscriptionPotentielle.getIdentifiant() + " concernant "+inscriptionPotentielle.getNom()+ " a été validée"));
        } catch (UtilisateurDejaExistantException e) {
            this.broadcastNotification(Notification.creerNotification(Notification.TypeNotification.ERREUR_ACCEPTATION,"L'utilisateur existe déjà dans le SI donc le traitement ne peut aboutir"));

        } catch (RoleDejaAttribueException e) {
            this.broadcastNotification(Notification.creerNotification(Notification.TypeNotification.ERREUR_ACCEPTATION,"Le rôle existe déjà pour cet utilisateur"));

        }
    }



    public void refuserDemandes(InscriptionPotentielle inscriptionPotentielle) {
        this.adminService.refuserInscription(identifiant.getIdentifiant(),inscriptionPotentielle.getIdentifiant());
        this.broadcastNotification(Notification.creerUpdateDemandes(this.adminService.getListeDesDemandesNonTraitees(identifiant.getIdentifiant())));
        this.broadcastNotification(Notification.creerNotification(Notification.TypeNotification.CONFIRMATION_REFUS,"La demande "+inscriptionPotentielle.getIdentifiant() + " concernant "+inscriptionPotentielle.getNom()+ " a été refusée"));

    }

    public Collection<InscriptionPotentielle> getDemandes() {
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
}
