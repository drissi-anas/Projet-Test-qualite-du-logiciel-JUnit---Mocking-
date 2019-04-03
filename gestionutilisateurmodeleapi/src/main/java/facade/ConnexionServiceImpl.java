package facade;

import facade.erreurs.CoupleUtilisateurMDPInconnuException;
import modele.personnes.Personne;

public class ConnexionServiceImpl implements ConnexionService {
    @Override
    public Personne connexion(String pseudo, String mdp) throws CoupleUtilisateurMDPInconnuException {
        return null;
    }

    @Override
    public void deconnexion(long idUtilisateur) {

    }

    @Override
    public boolean estUnUtilisateurConnu(String pseudo) {
        return false;
    }

    @Override
    public boolean estUnAdmin(long idUtilisateur) {
        return false;
    }

    @Override
    public boolean estUnModerateur(long identifiant) {
        return false;
    }
}
