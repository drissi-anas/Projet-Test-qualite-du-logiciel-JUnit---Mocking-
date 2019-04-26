package serviceImpl.facadeImpl;

import facade.ConnexionService;
import facade.erreurs.CoupleUtilisateurMDPInconnuException;
import modele.personnes.Personne;
import java.util.Collection;

public class ConnexionServiceImpl implements ConnexionService {

    Collection<Personne> listeUtilisateurs;
    Collection<Personne>personnesConnectes;
    public ConnexionServiceImpl(Collection<Personne> listeUtilisateurs, Collection<Personne> personnesConnectes) {
        this.listeUtilisateurs = listeUtilisateurs;
        this.personnesConnectes = personnesConnectes;
    }
    @Override
    public Personne connexion(String pseudo, String mdp) throws CoupleUtilisateurMDPInconnuException {

        Personne personne = null;
        for (Personne p : listeUtilisateurs) {
            if(p.getNom().equals(pseudo)&&p.getMdp().equals(mdp)){
                personne =p;
            }

        }
        if(personne==null){
            throw new CoupleUtilisateurMDPInconnuException();
        }
        personnesConnectes.add(personne);
        return personne;
    }


    @Override
    public void deconnexion(long idUtilisateur) {

        Personne personne = null;
        for (Personne p : listeUtilisateurs) {
            if(p.getIdentifiant()==idUtilisateur){
                personne =p;
            }

        }
        personnesConnectes.remove(personne);
    }


    @Override
    public boolean estUnUtilisateurConnu(String pseudo) {


        for (Personne p : listeUtilisateurs) {
            if(p.getNom().equals(pseudo)){
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean estUnAdmin(long idUtilisateur) {

        for (Personne p : listeUtilisateurs) {
            if(p.getIdentifiant()== idUtilisateur){
                if(p.hasRole("ADMINISTRATEUR")){
                    return true;
                }
            }

        }

        return false;
    }

    @Override
    public boolean estUnModerateur(long identifiant) {
        for (Personne p : listeUtilisateurs) {
            if(p.getIdentifiant()== identifiant){
                if(p.hasRole("MODERATEUR")){
                    return true;
                }
            }

        }

        return false;
    }
}
