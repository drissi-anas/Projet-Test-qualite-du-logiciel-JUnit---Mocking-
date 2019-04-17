package facade;

import modele.forum.Message;
import modele.forum.Theme;
import modele.forum.Topic;
import modele.inscription.InscriptionPotentielle;
import modele.personnes.Personne;

public interface FabriqueMock {

    AdminService creerMockAdminService();
    BasiquesOffLineService creerMockBOS();
    ConnexionService creerMockConnexionServ();
    Personne creerMockPersonne();
    InscriptionPotentielle creerInscri();
    ForumService creerMockForum();
    Theme creerThemeForum();

    Topic creerTopic();
    Message creerMessage();
}
