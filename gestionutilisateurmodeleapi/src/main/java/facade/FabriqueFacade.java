package facade;

import modele.personnes.Personne;

public interface FabriqueFacade {

    AdminService getAdminService();
    BasiquesOffLineService getBasiquesOffLineService();
    ConnexionService getConnexionService();
    Personne getPersonne();

}

