package facade;

import modele.inscription.InscriptionPotentielle;
import modele.personnes.Personne;

public interface FabriqueMock {

    AdminService creerMockAdminService();
    BasiquesOffLineService creerMockBOS();
    ConnexionService creerMockConnexionServ();
    Personne creerMockPersonne();
    InscriptionPotentielle creerInscri();
}
