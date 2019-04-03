package facade.erreurs;

import facade.AdminService;
import modele.inscription.InscriptionPotentielle;
import modele.personnes.Personne;

import java.util.Collection;

public class AdminServiceImpl implements AdminService {

    @Override
    public Personne creerUtilisateur(long u, String nom, String mdp) throws IndividuNonConnecteException, UtilisateurDejaExistantException {
        throw null;
    }

    @Override
    public void associerRoleUtilisateur(long u, long utilisateurConcerne, String role) throws IndividuNonConnecteException, RoleDejaAttribueException {

    }

    @Override
    public void supprimerClient(long u, long utilisateurConcerne) throws IndividuNonConnecteException {

    }

    @Override
    public Collection<Personne> getListeUtilisateur(long idDemandeur) throws IndividuNonConnecteException {
        return null;
    }

    @Override
    public Personne getUserById(long identifiant, long identifiant1) {
        return null;
    }

    @Override
    public void supprimerRoleUtilisateur(long identifiant, long identifiant1, String role) {

    }

    @Override
    public void changerMotDePasseUtilisateur(long identifiant, long identifiant1, String mdp) {

    }

    @Override
    public void supprimerUtilisateur(long identifiant, long idUtilisateur) {

    }

    @Override
    public void validerInscription(long identifiantUtilisateur, long identifiantDemande) throws UtilisateurDejaExistantException, RoleDejaAttribueException {

    }

    @Override
    public Collection<InscriptionPotentielle> getListeDesDemandesNonTraitees(long identifiantUtilisateur) {
        return null;
    }

    @Override
    public void refuserInscription(long identifiantUtilisateur, long identifiantDemande) {

    }
}
