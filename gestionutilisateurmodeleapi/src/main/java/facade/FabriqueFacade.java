package facade;

import facade.erreurs.RoleDejaAttribueException;
import modele.forum.Message;
import modele.forum.Topic;
import modele.personnes.Personne;

import java.util.Date;

public interface FabriqueFacade {

    void majListes() throws RoleDejaAttribueException;

    BasiquesOffLineService getBasiquesOffLineService();
    ConnexionService getConnexionService();

    AdminService getAdminService(ConnexionService connexionService) throws RoleDejaAttribueException;

    ForumService getForumService(ConnexionService connexionService);


}

