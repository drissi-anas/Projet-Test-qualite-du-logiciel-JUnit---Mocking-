package facade;

import facade.erreurs.IndividuNonConnecteException;
import facade.erreurs.RoleDejaAttribueException;
import facade.erreurs.UtilisateurDejaExistantException;
import modele.inscription.InscriptionPotentielle;
import modele.personnes.Personne;


import java.util.Arrays;
import java.util.Collection;

public interface AdminService {


    Personne creerUtilisateur(long u, String nom, String mdp) throws IndividuNonConnecteException,UtilisateurDejaExistantException;

    void associerRoleUtilisateur(long u, long utilisateurConcerne, String role) throws IndividuNonConnecteException, RoleDejaAttribueException;

    void supprimerClient(long u, long utilisateurConcerne) throws IndividuNonConnecteException;

    Collection<Personne> getListeUtilisateur(long idDemandeur)throws IndividuNonConnecteException;

    Personne getUserById(long identifiant, long identifiant1);

    void supprimerRoleUtilisateur(long identifiant, long identifiant1, String role);

    void changerMotDePasseUtilisateur(long identifiant, long identifiant1, String mdp);

    void supprimerUtilisateur(long identifiant, long idUtilisateur);

    void validerInscription(long identifiantUtilisateur,long identifiantDemande) throws UtilisateurDejaExistantException, RoleDejaAttribueException;


    /**
     * Selon le rôle de l'identifiant, la liste des traitements des demandes est construite
     * Les demandes d'admin ne peuvent être traitées que par des admins alors que toutes les autres demandes
     * peuvent être traiter par un modérateur. Un utilisateur classique ne peut rien faire.
     * @param identifiantUtilisateur
     * @return
     */
    Collection<InscriptionPotentielle> getListeDesDemandesNonTraitees(long identifiantUtilisateur);

    void refuserInscription(long identifiantUtilisateur,long identifiantDemande);
    String ADMIN = "ADMIN";
    String MODERATEUR = "MODERATEUR";
    String BASIQUE = "BASIQUE";



    static Collection<String> getRoles() {
        return Arrays.asList(ADMIN,MODERATEUR,BASIQUE);
    }

}
