package facade;

import facade.erreurs.CoupleUtilisateurMDPInconnuException;
import modele.personnes.Personne;

public interface ConnexionService {

    /**
     * Permet de connecter une personne à l'application
     * @param pseudo
     * @param mdp
     * @return
     * @throws CoupleUtilisateurMDPInconnuException
     */

    Personne connexion(String pseudo, String mdp) throws CoupleUtilisateurMDPInconnuException;

    /**
     * Permet de déconnecter une personne de l'application
     * @param idUtilisateur
     */

    public void deconnexion(long idUtilisateur);


    /**
     *
     * @param pseudo : pseudo d'un utilisateur potentiel
     * @return : true si le pseudo correspond au nom d'un utilisateur du SI.
     */
    boolean estUnUtilisateurConnu(String pseudo);

    /**
     *
     * @param idUtilisateur : identifiant de l'utilisateur
     * @return true si l'utilisateur est un administrateur et false sinon
     */

    boolean estUnAdmin(long idUtilisateur);




    /**
     *
     * @param identifiant : identifiant de l'utilisateur
     * @return true si l'utilisateur est un moderateur et false sinon
     */


    boolean estUnModerateur(long identifiant);
}
