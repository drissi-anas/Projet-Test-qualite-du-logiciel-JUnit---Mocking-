package facade;

import facade.erreurs.*;
import modele.inscription.InscriptionPotentielle;
import modele.personnes.Personne;


import java.util.Arrays;
import java.util.Collection;

public interface AdminService {


    Personne creerUtilisateur(long u, String nom, String mdp) throws IndividuNonConnecteException,UtilisateurDejaExistantException,InformationManquanteException;

    void associerRoleUtilisateur(long u, long utilisateurConcerne, String role) throws IndividuNonConnecteException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException;


    /**
     * Récupére la liste des utilisateur qui sont présents dans le Systéme
     */
    Collection<Personne> getListeUtilisateur(long idDemandeur)throws IndividuNonConnecteException;

    Personne getUserById(long identifiant, long identifiant1);

    void supprimerRoleUtilisateur(long identifiant, long identifiant1, String role) throws IndividuNonConnecteException;

    void changerMotDePasseUtilisateur(long identifiant, long identifiant1, String mdp) throws InformationManquanteException, IndividuNonConnecteException;

    void supprimerUtilisateur(long identifiant, long idUtilisateur) throws IndividuNonConnecteException;

    void validerInscription(long identifiantUtilisateur,long identifiantDemande) throws ActionImpossibleException, RoleDejaAttribueException;


    /**
     * Selon le rôle de l'identifiant, la liste des traitements des demandes est construite
     * Les demandes d'admin ne peuvent être traitées que par des admins alors que toutes les autres demandes
     * peuvent être traiter par un modérateur. Un utilisateur classique ne peut rien faire.
     * @param identifiantUtilisateur
     * @return
     */
    Collection<InscriptionPotentielle> getListeDesDemandesNonTraitees(long identifiantUtilisateur);

    void refuserInscription(long identifiantUtilisateur,long identifiantDemande) throws ActionImpossibleException;
    String ADMIN = "ADMIN";
    String MODERATEUR = "MODERATEUR";
    String BASIQUE = "BASIQUE";



    static Collection<String> getRoles() {
        return Arrays.asList(ADMIN,MODERATEUR,BASIQUE);
    }

}
