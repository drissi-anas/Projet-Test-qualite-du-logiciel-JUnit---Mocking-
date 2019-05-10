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

        listeUtilisateurs.add(personne);

    }




    @Override
    public BasiquesOffLineService getBasiquesOffLineService(ConnexionService connexionService) {
        return new BasiqueOffLineServiceImpl(connexionService);
    }

    @Override
    public ConnexionService getConnexionService() {
        return new ConnexionServiceImpl(listeUtilisateurs,personnesConnectes);
    }

    @Override
    public AdminService getAdminService(ConnexionService connexionService, BasiquesOffLineService basiquesOffLineService) throws RoleDejaAttribueException {
        return new AdminServiceImpl(connexionService,basiquesOffLineService);

    }


    @Override
    public ForumService getForumService(ConnexionService connexionService) {
        Collection<Theme>listeThemes=new ArrayList<>();
        return new ForumServiceImpl(listeThemes,connexionService);
    }


}
