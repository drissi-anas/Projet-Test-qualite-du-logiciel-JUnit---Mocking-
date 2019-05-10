package facade;

import facade.erreurs.RoleDejaAttribueException;
import modele.forum.Message;
import modele.forum.Topic;
import modele.personnes.Personne;

import java.util.Date;

public interface FabriqueFacade {

    void majListes() throws RoleDejaAttribueException;

    BasiquesOffLineService getBasiquesOffLineService(ConnexionService connexionService);
    ConnexionService getConnexionService();

    AdminService getAdminService(ConnexionService connexionService,BasiquesOffLineService basiquesOffLineService) throws RoleDejaAttribueException;

    ForumService getForumService(ConnexionService connexionService);


}

