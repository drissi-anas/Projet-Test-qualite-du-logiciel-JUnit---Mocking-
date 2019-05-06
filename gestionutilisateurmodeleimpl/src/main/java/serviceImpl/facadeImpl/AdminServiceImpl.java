package serviceImpl.facadeImpl;

import facade.AdminService;
import facade.BasiquesOffLineService;
import facade.ConnexionService;
import facade.erreurs.*;
import modele.inscription.InscriptionPotentielle;
import modele.personnes.Personne;
import serviceImpl.personnesImpl.PersonneImpl;

import java.util.ArrayList;
import java.util.Collection;

public class AdminServiceImpl implements AdminService {

    private ConnexionService connexionService;


    @Override
    public void setListeDemandesNonTRaitees(Collection<InscriptionPotentielle> listeDemandes, long identifiant) {
        for (InscriptionPotentielle i:listeDemandes) {

            if(i.getIdentifiant()==identifiant){
                listeDemandesNonTRaitees.add(i);
            }

        }
        }

    public AdminServiceImpl(ConnexionService connexionService){
        this.connexionService=connexionService;

    }



    Collection<InscriptionPotentielle>listeDemandesNonTRaitees = new ArrayList<>();


    @Override
    public Personne creerUtilisateur(long u, String nom, String mdp) throws IndividuNonConnecteException, UtilisateurDejaExistantException, InformationManquanteException {

        if(nom==null || mdp==null || mdp.equals("") || nom.equals("")){
            throw new InformationManquanteException();
        }
        for (Personne p:connexionService.getListeUtilisateurs()) {
            if(p.getNom().equals(nom)){
                throw new UtilisateurDejaExistantException();
            }
        }

        boolean adminOuModoConnecte =false;
        for (Personne p : connexionService.getPersonnesConnectes()) {

            if(p.getIdentifiant()==u){
                adminOuModoConnecte=true;
            }
        }

        if(!adminOuModoConnecte){
            throw new IndividuNonConnecteException();
        }

        Personne personne = new PersonneImpl(nom,mdp);
        connexionService.getListeUtilisateurs().add(personne);
        return personne;

    }


    @Override
    public void associerRoleUtilisateur(long u, long utilisateurConcerne, String role) throws IndividuNonConnecteException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException {

        if(role==null || role.equals("")){
            throw new InformationManquanteException();
        }



        boolean adminOuModoConnecte =false;
        for (Personne p : connexionService.getPersonnesConnectes()) {
            if(p.getIdentifiant()==u){
                adminOuModoConnecte=true;
            }
        }

        if(!adminOuModoConnecte){
            throw new IndividuNonConnecteException();
        }


        Boolean isAdmin = false;
        Boolean isModerateur = false;

            for (Personne p : connexionService.getPersonnesConnectes()) {
            if(p.getIdentifiant()==u){
                for (String s:p.getRoles()) {
                    if(s.equals(ADMIN)){
                        isAdmin=true;
                    }
                    if(s.equals(MODERATEUR)){
                        isModerateur=true;
                    }

                }
            }


            }

        if(role.equals(ADMIN)){
            if(!isAdmin){
                throw new ActionImpossibleException();
            }
        }
        if(role.equals(MODERATEUR)){
            if(!isAdmin && !isModerateur ){
                throw new ActionImpossibleException();
            }
        }



        for (Personne p:connexionService.getListeUtilisateurs()) {
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
        for (Personne p : connexionService.getPersonnesConnectes()) {
            if(p.getIdentifiant()==idDemandeur){
                demandeurConnecte=true;
            }
        }
        if(!demandeurConnecte){
            throw new IndividuNonConnecteException();
        }

        return connexionService.getListeUtilisateurs();
    }

    @Override
    public Personne getUserById(long identifiant, long identifiant1) {
        return null;
    }


    @Override
    public void supprimerRoleUtilisateur(long identifiant, long identifiant1, String role) throws IndividuNonConnecteException {
        boolean adminOuModoConnecte =false;
        for (Personne p : connexionService.getPersonnesConnectes()) {
            if(p.getIdentifiant()==identifiant){
                adminOuModoConnecte=true;
            }
        }
        if(!adminOuModoConnecte){
            throw new IndividuNonConnecteException();
        }
        for (Personne p:connexionService.getListeUtilisateurs()) {
            if(p.getIdentifiant()==identifiant1){
                p.supprimerRole(role);
            }
        }

    }

    @Override
    public void changerMotDePasseUtilisateur(long identifiant, long identifiant1, String mdp) throws InformationManquanteException, IndividuNonConnecteException {
        if( mdp == null ||mdp.equals("")  ){
            throw new InformationManquanteException();
        }
        boolean adminOuModoConnecte =false;
        for (Personne p : connexionService.getPersonnesConnectes()) {
            if(p.getIdentifiant()==identifiant){
                adminOuModoConnecte=true;
            }
        }
        if(!adminOuModoConnecte){
            throw new IndividuNonConnecteException();
        }

        for (Personne p:connexionService.getListeUtilisateurs()) {
            if(p.getIdentifiant()==identifiant1){
                p.setMdp(mdp);
            }
        }


    }

    @Override
    public void supprimerUtilisateur(long identifiant, long idUtilisateur) throws IndividuNonConnecteException {

        boolean adminOuModoConnecte =false;
        for (Personne p : connexionService.getPersonnesConnectes()) {
            if(p.getIdentifiant()==identifiant){
                adminOuModoConnecte=true;
            }
        }
        if(!adminOuModoConnecte){
            throw new IndividuNonConnecteException();
        }
        Personne personne=null;
        for (Personne p:connexionService.getListeUtilisateurs()) {
            if(p.getIdentifiant()==idUtilisateur){
                 personne=p;
            }
        }
        connexionService.getListeUtilisateurs().remove(personne);

    }

    @Override
    public void validerInscription(long identifiantUtilisateur, long identifiantDemande) {


        for (Personne p : connexionService.getListeUtilisateurs()) {
            InscriptionPotentielle inscriptionPotentielle = null;
            for (InscriptionPotentielle i : listeDemandesNonTRaitees) {

                if (i.getIdentifiant() == identifiantDemande) {
                    inscriptionPotentielle = i;
                }
            }
            Personne personne = new PersonneImpl(inscriptionPotentielle.getNom(), inscriptionPotentielle.getMdp());
            connexionService.getListeUtilisateurs().add(personne);
            listeDemandesNonTRaitees.remove(inscriptionPotentielle);

        }

    }



    //Pas sur, je sais pas quoi faire du parametre
    @Override
    public Collection<InscriptionPotentielle> getListeDesDemandesNonTraitees(long identifiantUtilisateur) {

        boolean isAdmin= false;
        boolean isModerateur= false;
        for (Personne p:connexionService.getListeUtilisateurs()) {
            if(p.getIdentifiant()==identifiantUtilisateur){
                for(String s:p.getRoles()){
                    if(s.equals("ADMINISTRATEUR")){
                        isAdmin=true;
                    }else if(s.equals("MODERATEUR")){
                        isModerateur=true;
                    }
                }
            }
        }
        Collection<InscriptionPotentielle> listeARetourner=new ArrayList<>();

        if(isAdmin){
            return listeDemandesNonTRaitees;
        }else if(isModerateur){

            for (InscriptionPotentielle i:listeDemandesNonTRaitees){

                if(!i.getRoleDemande().equals("ADMINISTRATEUR")){
                    listeARetourner.add(i);
                }

            }
            return listeDemandesNonTRaitees;

        }

        return  null;

    }

    //Pas sur, je sais pas quoi faire du parametre identifiantUTilisateur
    @Override
    public void refuserInscription(long identifiantUtilisateur, long identifiantDemande) throws ActionImpossibleException {

        Boolean isAdmin = false;
        Boolean isModerateur = false;

        for (Personne p : connexionService.getPersonnesConnectes()) {
            if(p.getIdentifiant()==identifiantUtilisateur){
                for (String s:p.getRoles()) {
                    if(s.equals(ADMIN)){
                        isAdmin=true;
                    }
                    if(s.equals(MODERATEUR)){
                        isModerateur=true;
                    }

                }
            }


        }

        InscriptionPotentielle inscriptionPotentielle = null;
        for(InscriptionPotentielle i: listeDemandesNonTRaitees){

            if(i.getIdentifiant()==identifiantDemande){
                inscriptionPotentielle=i;
            }
        }

        if(inscriptionPotentielle.getRoleDemande().equals(ADMIN)){
                if(!isAdmin){
                    throw new ActionImpossibleException();
                }
            }

            if(inscriptionPotentielle.getRoleDemande().equals(MODERATEUR)){
                if(!isAdmin && !isModerateur ){
                    throw new ActionImpossibleException();
                }
            }
        if(inscriptionPotentielle.getRoleDemande().equals(BASIQUE)){
            if(!isAdmin && !isModerateur ){
                throw new ActionImpossibleException();
            }
        }

        listeDemandesNonTRaitees.remove(inscriptionPotentielle);
    }



}

