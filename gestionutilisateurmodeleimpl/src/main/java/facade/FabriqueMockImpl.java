package facade;

import modele.forum.Message;
import modele.forum.Theme;
import modele.forum.Topic;
import modele.inscription.InscriptionPotentielle;
import modele.personnes.Personne;
import org.easymock.EasyMock;

public class FabriqueMockImpl implements FabriqueMock {

    public FabriqueMockImpl() { }

    @Override
    public AdminService creerMockAdminService() {
        AdminService adminService = EasyMock.createMock(AdminService.class);
        return adminService;

    }

    @Override
    public BasiquesOffLineService creerMockBOS() {
        BasiquesOffLineService bols = EasyMock.createMock(BasiquesOffLineService.class);
        return bols;
    }

    @Override
    public ConnexionService creerMockConnexionServ() {
        ConnexionService connexionService = EasyMock.createMock(ConnexionService.class);
        return connexionService;
    }

    @Override
    public Personne creerMockPersonne() {
        Personne personne = EasyMock.createMock(Personne.class);
        return personne;
    }

    @Override
    public InscriptionPotentielle creerInscri() {
        InscriptionPotentielle inscriptionPotentielle = EasyMock.createMock(InscriptionPotentielle.class);
        return inscriptionPotentielle;
    }

    @Override
    public ForumService creerMockForum() {
        ForumService forumService = EasyMock.createMock(ForumService.class);
        return forumService;
    }

    @Override
    public Theme creerThemeForum() {
        Theme theme= EasyMock.createMock(Theme.class);

        return theme;
    }

    @Override
    public Topic creerTopic() {
        Topic topic=EasyMock.createMock(Topic.class);
        return topic;
    }

    @Override
    public Message creerMessage() {
        Message message=EasyMock.createMock(Message.class);
        return message;
    }
}
