package serviceImpl.facadeImpl;

import facade.*;
import modele.forum.Message;
import modele.forum.Theme;
import modele.forum.Topic;
import modele.personnes.Personne;
import serviceImpl.forumImpl.MessageImpl;
import serviceImpl.personnesImpl.PersonneImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class FabriqueFacadeImpl implements FabriqueFacade {

    Collection<Personne>listeUtilisateurs=new ArrayList<>();
    Collection<Personne>utilisateursConnecte=new ArrayList<>();

    @Override
    public AdminService getAdminService() {

        return new AdminServiceImpl(listeUtilisateurs,utilisateursConnecte);
    }

    @Override
    public BasiquesOffLineService getBasiquesOffLineService() {
        return new BasiqueOffLineServiceImpl();
    }

    @Override
    public ConnexionService getConnexionService() {
        return new ConnexionServiceImpl(listeUtilisateurs,utilisateursConnecte);
    }

    @Override
    public ForumService getForumService() {
        Collection<Theme>listeThemes=new ArrayList<>();
        return new ForumServiceImpl(listeThemes);
    }


}
