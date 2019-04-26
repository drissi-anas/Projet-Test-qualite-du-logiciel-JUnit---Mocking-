package facade;

import modele.forum.Message;
import modele.forum.Topic;
import modele.personnes.Personne;

import java.util.Date;

public interface FabriqueFacade {

    AdminService getAdminService();
    BasiquesOffLineService getBasiquesOffLineService();
    ConnexionService getConnexionService();
    ForumService getForumService();


}

