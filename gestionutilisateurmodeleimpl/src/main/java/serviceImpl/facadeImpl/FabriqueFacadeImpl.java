package serviceImpl.facadeImpl;

import facade.*;
import facade.erreurs.RoleDejaAttribueException;
import modele.forum.Message;
import modele.forum.Theme;
import modele.forum.Topic;
import modele.personnes.Personne;
import serviceImpl.forumImpl.MessageImpl;
import serviceImpl.personnesImpl.PersonneImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static facade.AdminService.ADMIN;
import static facade.AdminService.BASIQUE;
import static facade.AdminService.MODERATEUR;

public class FabriqueFacadeImpl implements FabriqueFacade {
    Collection<Personne>listeUtilisateurs;
    Collection<Personne>personnesConnectes;

    public FabriqueFacadeImpl(){
        listeUtilisateurs=new ArrayList<>();
        personnesConnectes=new ArrayList<>();
    }

    @Override
    public void majListes() throws RoleDejaAttribueException {

        Personne personne = new PersonneImpl("admin", "admin");
        personne.addRole(ADMIN);
        Personne personne2 = new PersonneImpl("moderateur1", "moderateur1");
        personne2.addRole(MODERATEUR);
        Personne personne3 = new PersonneImpl("basique", "basique");
        personne2.addRole(BASIQUE);

        listeUtilisateurs.add(personne3);
        listeUtilisateurs.add(personne);
        listeUtilisateurs.add(personne2);

    }




    @Override
    public BasiquesOffLineService getBasiquesOffLineService() {
        return new BasiqueOffLineServiceImpl();
    }

    @Override
    public ConnexionService getConnexionService() {
        return new ConnexionServiceImpl(listeUtilisateurs,personnesConnectes);
    }

    @Override
    public AdminService getAdminService(ConnexionService connexionService) throws RoleDejaAttribueException {

        return new AdminServiceImpl(connexionService);
    }

    @Override
    public ForumService getForumService() {
        Collection<Theme>listeThemes=new ArrayList<>();
        return new ForumServiceImpl(listeThemes);
    }


}
