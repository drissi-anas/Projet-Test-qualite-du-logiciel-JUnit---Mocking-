package facade;

import facade.erreurs.UtilisateurDejaExistantException;

/**
 * Created by YohanBoichut on 06/12/2016.
 */
public interface BasiquesOffLineService {


    /**
     * Représente une demande d'inscription avec pour rôle roleDemande
     * @param pseudo : pseudo demandé
     * @param motDePasse : mot de passe demandé
     * @param roleDemande : rôle demandé
     */
     long posterDemandeInscription(String pseudo, String motDePasse,String roleDemande) throws UtilisateurDejaExistantException;

}
