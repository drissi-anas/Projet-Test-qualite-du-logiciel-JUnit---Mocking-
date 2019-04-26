package serviceImpl.facadeImpl;

import facade.AdminService;
import facade.erreurs.IndividuNonConnecteException;
import facade.erreurs.RoleDejaAttribueException;
import facade.erreurs.UtilisateurDejaExistantException;
import modele.inscription.InscriptionPotentielle;
import modele.personnes.Personne;
import serviceImpl.personnesImpl.PersonneImpl;

import java.util.ArrayList;
import java.util.Collection;

public class AdminServiceImpl implements AdminService {


    public AdminServiceImpl(Collection<Personne> listeUtilisateurs, Collection<Personne> personnesConnectes) {
        this.listeUtilisateurs = listeUtilisateurs;
        this.personnesConnectes = personnesConnectes;
        listeUtilisateurs.add(new PersonneImpl("admin","admin"));
        personnesConnectes.add(new PersonneImpl("admin","admin"));
    }

    Collection<Personne>listeUtilisateurs;
    Collection<Personne>personnesConnectes;
    Collection<InscriptionPotentielle>listeDemandesNonTRaitees = new ArrayList<>();

    @Override
    public Personne creerUtilisateur(long u, String nom, String mdp) throws IndividuNonConnecteException, UtilisateurDejaExistantException {
        for (Personne p:listeUtilisateurs) {
            if(p.getNom().equals(nom)){
                throw new UtilisateurDejaExistantException();
            }
        }
        boolean adminOuModoConnecte =false;
        for (Personne p : personnesConnectes) {
            if(p.getIdentifiant()==u){
                adminOuModoConnecte=true;
            }
        }
        if(!adminOuModoConnecte){
            throw new IndividuNonConnecteException();
        }

        Personne personne = new PersonneImpl(nom,mdp);
        listeUtilisateurs.add(personne);
        return personne;

    }


    @Override
    public void associerRoleUtilisateur(long u, long utilisateurConcerne, String role) throws IndividuNonConnecteException, RoleDejaAttribueException {

        boolean adminOuModoConnecte =false;
        for (Personne p : personnesConnectes) {
            if(p.getIdentifiant()==u){
                adminOuModoConnecte=true;
            }
        }
        if(!adminOuModoConnecte){
            throw new IndividuNonConnecteException();
        }

        for (Personne p:listeUtilisateurs) {
            if(p.getIdentifiant()==utilisateurConcerne && p.hasRole(role)){
                throw new RoleDejaAttribueException();
            }else if(p.getIdentifiant()==utilisateurConcerne){
                p.addRole(role);
            }
        }

    }
/**
    @Override
    public void supprimerClient(long u, long utilisateurConcerne) throws IndividuNonConnecteException {
        boolean adminOuModoConnecte =false;
        for (Personne p : personnesConnectes) {
            if(p.getIdentifiant()==u){
                adminOuModoConnecte=true;
            }
        }
        if(!adminOuModoConnecte){
            throw new IndividuNonConnecteException();
        }
        for (Personne p:listeUtilisateurs) {
            if(p.getIdentifiant()==utilisateurConcerne){
               listeUtilisateurs.remove(p);
            }
        }
    }
**/
    @Override
    public Collection<Personne> getListeUtilisateur(long idDemandeur) throws IndividuNonConnecteException {

        boolean demandeurConnecte =false;
        for (Personne p : personnesConnectes) {
            if(p.getIdentifiant()==idDemandeur){
                demandeurConnecte=true;
            }
        }
        if(!demandeurConnecte){
            throw new IndividuNonConnecteException();
        }

        return listeUtilisateurs;
    }

    @Override
    public Personne getUserById(long identifiant, long identifiant1) {
        return null;
    }


    @Override
    public void supprimerRoleUtilisateur(long identifiant, long identifiant1, String role) {
        boolean adminOuModoConnecte =false;
        for (Personne p : personnesConnectes) {
            if(p.getIdentifiant()==identifiant){
                adminOuModoConnecte=true;
            }
        }
        if(!adminOuModoConnecte){
            throw new IndividuNonConnecteException();
        }
        for (Personne p:listeUtilisateurs) {
            if(p.getIdentifiant()==identifiant1){
                p.supprimerRole(role);
            }
        }

    }

    @Override
    public void changerMotDePasseUtilisateur(long identifiant, long identifiant1, String mdp) {
        boolean adminOuModoConnecte =false;
        for (Personne p : personnesConnectes) {
            if(p.getIdentifiant()==identifiant){
                adminOuModoConnecte=true;
            }
        }
        if(!adminOuModoConnecte){
            throw new IndividuNonConnecteException();
        }

        for (Personne p:listeUtilisateurs) {
            if(p.getIdentifiant()==identifiant1){
                p.setMdp(mdp);
            }
        }


    }

    @Override
    public void supprimerUtilisateur(long identifiant, long idUtilisateur) {

        boolean adminOuModoConnecte =false;
        for (Personne p : personnesConnectes) {
            if(p.getIdentifiant()==identifiant){
                adminOuModoConnecte=true;
            }
        }
        if(!adminOuModoConnecte){
            throw new IndividuNonConnecteException();
        }
        Personne personne=null;
        for (Personne p:listeUtilisateurs) {
            if(p.getIdentifiant()==idUtilisateur){
                 personne=p;
            }
        }
        listeUtilisateurs.remove(personne);

    }

    @Override
    public void validerInscription(long identifiantUtilisateur, long identifiantDemande) {


        for (Personne p : listeUtilisateurs) {
            InscriptionPotentielle inscriptionPotentielle = null;
            for (InscriptionPotentielle i : listeDemandesNonTRaitees) {

                if (i.getIdentifiant() == identifiantDemande) {
                    inscriptionPotentielle = i;
                }

            }

            Personne personne = new PersonneImpl(inscriptionPotentielle.getNom(), inscriptionPotentielle.getMdp());
            listeUtilisateurs.add(personne);
            listeDemandesNonTRaitees.remove(inscriptionPotentielle);

        }

    }



    //Pas sur, je sais pas quoi faire du parametre
    @Override
    public Collection<InscriptionPotentielle> getListeDesDemandesNonTraitees(long identifiantUtilisateur) {
        return listeDemandesNonTRaitees;
    }

    //Pas sur, je sais pas quoi faire du parametre identifiantUTilisateur
    @Override
    public void refuserInscription(long identifiantUtilisateur, long identifiantDemande) {
        InscriptionPotentielle inscriptionPotentielle = null;
        for(InscriptionPotentielle i: listeDemandesNonTRaitees){

            if(i.getIdentifiant()==identifiantDemande){
                inscriptionPotentielle=i;
            }
        }
        listeDemandesNonTRaitees.remove(inscriptionPotentielle);
    }

}

