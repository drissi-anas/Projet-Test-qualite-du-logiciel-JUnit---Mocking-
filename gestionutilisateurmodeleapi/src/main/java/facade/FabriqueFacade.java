package facade;

import modele.forum.Message;
import modele.forum.Topic;
import modele.personnes.Personne;

public interface FabriqueFacade {

    AdminService getAdminService();
    BasiquesOffLineService getBasiquesOffLineService();
    ConnexionService getConnexionService();
    Personne getPersonne();
    Message getMessage();
    Topic getTopic();


}

