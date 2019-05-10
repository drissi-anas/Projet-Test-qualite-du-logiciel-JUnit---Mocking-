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
    private BasiquesOffLineService basiqueOffLineService;



    public AdminServiceImpl(ConnexionService connexionService, BasiquesOffLineService basiqueOffLineService){
        this.connexionService=connexionService;
        this.basiqueOffLineService=basiqueOffLineService;
    }



    // Collection<InscriptionPotentielle>listeDemandesNonTRaitees = new ArrayList<>();


    @Override
    public Personne creerUtilisateur(long u, String nom, String mdp) throws IndividuNonConnecteException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        boolean adminOuModoConnecte =false;
        for (Personne p : connexionService.getPersonnesConnectes()) {

            if(p.getIdentifiant()==u){
                adminOuModoConnecte=true;
            }
        }

        if(!adminOuModoConnecte){
            throw new IndividuNonConnecteException();
        }


        boolean isAdmin = false;

        for (Personne p : connexionService.getPersonnesConnectes()) {
            if(p.getIdentifiant()==u){
                for (String s:p.getRoles()) {
                    if(s.equals(ADMIN)){
                        isAdmin=true;
                    }

                }
            }


        }
        if(!isAdmin){
            throw new ActionImpossibleException();
        }

        if(nom==null || mdp==null || mdp.equals("") || nom.equals("")){
            throw new InformationManquanteException();
        }

        for (Personne p:connexionService.getListeUtilisateurs()) {
            if(p.getNom().equals(nom)){
                throw new UtilisateurDejaExistantException();
            }
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


        boolean isAdmin = false;


        for (Personne p : connexionService.getPersonnesConnectes()) {
            if(p.getIdentifiant()==u){
                for (String s:p.getRoles()) {
                    if(s.equals(ADMIN)){
                        isAdmin=true;
                    }

                }
            }


        }

        if(!isAdmin){
            throw new ActionImpossibleException();
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
    public void supprimerRoleUtilisateur(long identifiant, long identifiant1, String role) throws IndividuNonConnecteException, ActionImpossibleException {
        boolean adminOuModoConnecte = false;
        for (Personne p : connexionService.getPersonnesConnectes()) {
            if (p.getIdentifiant() == identifiant) {
                adminOuModoConnecte = true;
            }
        }
        if (!adminOuModoConnecte) {
            throw new IndividuNonConnecteException();
        }

        boolean isAdmin = false;
        boolean isModerateur = false;
        for (Personne p : connexionService.getPersonnesConnectes()) {
            if (p.getIdentifiant() == identifiant) {
                for (String s : p.getRoles()) {
                    if (s.equals(ADMIN)) {
                        isAdmin = true;
                    }
                    if (s.equals(MODERATEUR)) {
                        isModerateur = true;
                    }

                }
            }

        }

        if (role.equals(ADMIN)) {
            throw new ActionImpossibleException();
        }
        if (role.equals(MODERATEUR)) {
            if (!isAdmin) {
                throw new ActionImpossibleException();
            }
        }

        if (role.equals(BASIQUE)) {
            if (!isAdmin && !isModerateur) {
                throw new ActionImpossibleException();
            }
        }


        for (Personne p : connexionService.getListeUtilisateurs()) {
            if (p.getIdentifiant() == identifiant1) {
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
    public void supprimerUtilisateur(long identifiant, long idUtilisateur) throws IndividuNonConnecteException, ActionImpossibleException {

        boolean adminOuModoConnecte =false;
        for (Personne p : connexionService.getPersonnesConnectes()) {
            if(p.getIdentifiant()==identifiant){
                adminOuModoConnecte=true;
            }
        }
        if(!adminOuModoConnecte){
            throw new IndividuNonConnecteException();
        }


        boolean isAdmin = false;


        for (Personne p : connexionService.getPersonnesConnectes()) {
            if(p.getIdentifiant()==identifiant){
                for (String s:p.getRoles()) {
                    if(s.equals(ADMIN)){
                        isAdmin=true;
                    }

                }
            }


        }

        if(!isAdmin){
            throw new ActionImpossibleException();
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
    public void validerInscription(long identifiantUtilisateur, long identifiantDemande) throws ActionImpossibleException, RoleDejaAttribueException {

        boolean isAdmin=false;
        boolean isModerateur = false;
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
        for (InscriptionPotentielle i : basiqueOffLineService.getListeDemandes()) {

            if (i.getIdentifiant() == identifiantDemande) {
                inscriptionPotentielle = i;
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

        Personne personne = new PersonneImpl(inscriptionPotentielle.getNom(), inscriptionPotentielle.getMdp());
        personne.addRole(inscriptionPotentielle.getRoleDemande());
        connexionService.getListeUtilisateurs().add(personne);
        basiqueOffLineService.getListeDemandes().remove(inscriptionPotentielle);

    }




    @Override
    public Collection<InscriptionPotentielle> getListeDesDemandesNonTraitees(long identifiantUtilisateur) {

        boolean isAdmin= false;
        boolean isModerateur= false;
        for (Personne p:connexionService.getListeUtilisateurs()) {
            if(p.getIdentifiant()==identifiantUtilisateur){
                for(String s:p.getRoles()){
                    if(s.equals(ADMIN)){
                        isAdmin=true;
                    }else if(s.equals(MODERATEUR)){
                        isModerateur=true;
                    }
                }
            }
        }
        Collection<InscriptionPotentielle> listeARetourner=new ArrayList<>();

        if(isAdmin){
            return basiqueOffLineService.getListeDemandes();
        }else if(isModerateur){

            for (InscriptionPotentielle i:basiqueOffLineService.getListeDemandes()){

                if(!i.getRoleDemande().equals(ADMIN)){
                    listeARetourner.add(i);
                }

            }
            return basiqueOffLineService.getListeDemandes();

        }

        return  null;

    }

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
        for(InscriptionPotentielle i: basiqueOffLineService.getListeDemandes()){

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

        basiqueOffLineService.getListeDemandes().remove(inscriptionPotentielle);
    }



}

