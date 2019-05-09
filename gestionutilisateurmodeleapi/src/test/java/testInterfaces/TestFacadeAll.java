package testInterfaces;

import facade.*;
import facade.erreurs.*;
import modele.forum.Message;
import modele.forum.Theme;
import modele.forum.Topic;
import modele.personnes.Personne;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



import static facade.AdminService.*;

public abstract class  TestFacadeAll {

    AdminService adminService;
    BasiquesOffLineService basiquesOffLineService;
    ConnexionService connexionService;
    ForumService forumService;
    FabriqueFacade fabriqueFacade;
    Personne personne;
    Message message;
    Topic topic;



    public TestFacadeAll(FabriqueFacade fabriqueFacade) {
        this.fabriqueFacade = fabriqueFacade;

    }

    @Before
    public void initialisationModele() throws RoleDejaAttribueException {
        fabriqueFacade.majListes();

        basiquesOffLineService = fabriqueFacade.getBasiquesOffLineService();
        connexionService = fabriqueFacade.getConnexionService();
        adminService = fabriqueFacade.getAdminService(connexionService,basiquesOffLineService);
        forumService = fabriqueFacade.getForumService(connexionService);

    }
    /**
     *     -------------------------------------------------------   Test de l'interface AdminService   -------------------------------------------------------
     */

    @Test
    public void creerUtilisateurTestOK() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            adminService.creerUtilisateur( p.getIdentifiant(), "aaa", "bbb");
            Assert.assertTrue(true);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch(IndividuNonConnecteException e1){
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
    }
    @Test(expected = UtilisateurDejaExistantException.class)
    public void creerUtilisateurTestKOUtilisDejaExis() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
    Personne p = connexionService.connexion("admin","admin");
    adminService.creerUtilisateur(p.getIdentifiant(),"aaa", "bbb");
    adminService.creerUtilisateur(3L,"aaa","bbb");
    }
    @Test(expected = IndividuNonConnecteException.class)
    public void creerUtilisateurTestKOIndividuNonConnecte() throws UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, CoupleUtilisateurMDPInconnuException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin","admin");
        connexionService.deconnexion(p.getIdentifiant());
        adminService.creerUtilisateur(p.getIdentifiant(),"aaa", "bbb");
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),null,"bbb");
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomNull_and_mdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),null,null);
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomVide_and_mdpVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),"","");
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomNull_and_mdpVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),null,"");
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomVide_and_mdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),"",null);
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_MdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),"aaa",null);
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),"","bbb");
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_MdpVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),"aaa","");
    }

    @Test (expected = ActionImpossibleException.class)
    public void creerUtilisateur_Moderateur_KO() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","123456789");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(), MODERATEUR);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p2.getNom(),p2.getMdp());
        Personne p3 = adminService.creerUtilisateur(p2.getIdentifiant(),"utilisateur2","utilis123");
    }

    @Test (expected = ActionImpossibleException.class)
    public void creerUtilisateur_Basique_KO() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","123456789");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p2.getNom(),p2.getMdp());
        Personne p3 = adminService.creerUtilisateur(p2.getIdentifiant(),"utilisateur2","utilis123");
    }

    /*******

    /**
     * Quand un ADMIN veut associer BASIQUE à un utilisateur.
     */
    @Test
    public void  admin_associerRoleUtilisateurBasique_OK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, ActionImpossibleException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur123");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e1) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            e.printStackTrace();
        }
    }


    @Test(expected =InformationManquanteException.class)
    public void admin_associerRoleUtilisateurBasique_InformationManquanteException_RoleNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur123");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),null);
        Assert.fail();
    }
    @Test(expected =InformationManquanteException.class)
    public void admin_associerRoleUtilisateurBasique_InformationManquanteException_RoleVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur123");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),"");
        Assert.fail();
    }
    @Test (expected = IndividuNonConnecteException.class)
    public void  admin_associerRoleUtilisateur_IndividuNonConnecte() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur45");
            connexionService.deconnexion(p.getIdentifiant());
            adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),BASIQUE);
    }
    @Test (expected = RoleDejaAttribueException.class)
    public void  admin_associerRoleUtilisateurBasique_RoleDejaAttribueException() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        Personne p =  connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),BASIQUE);
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),BASIQUE);
    }


    /**
     * Quand un ADMIN veut associer MODERATEUR à un utilisateur.
     */
    @Test
    public void  admin_associerRoleUtilisateurModerateur_OK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e1) {
            Assert.fail();
        }
    }
    @Test(expected =InformationManquanteException.class)
    public void admin_associerRoleUtilisateurModerateur_InformationManquanteException_RoleNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p  = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),null);
        Assert.fail();
    }
    @Test(expected =InformationManquanteException.class)
    public void admin_associerRoleUtilisateurModerateur_InformationManquanteException_RoleVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),"");
        Assert.fail();
    }

    @Test (expected = IndividuNonConnecteException.class)
    public void admin_associerRoleUtilisateurModerateur_IndividuNonConnecte() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur33");
            connexionService.deconnexion(p.getIdentifiant());
            adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),MODERATEUR);
    }

    @Test (expected = RoleDejaAttribueException.class)
    public void  admin_associerRoleUtilisateurModerateur_RoleDejaAttribueException() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        Personne p =  connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),MODERATEUR);
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),MODERATEUR);
    }
    /**
     * Quand un ADMIN veut associer ADMIN à un utilisateur.
     */
    @Test
    public void admin_associerRoleUtilisateurAdmin_OK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur789");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e1) {
            Assert.fail();
        }
    }
    @Test(expected = InformationManquanteException.class)
    public void admin_associerRoleUtilisateurAdmin_InformationManquanteException_RoleNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur789");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),null);
    }

    @Test(expected =InformationManquanteException.class)
    public void admin_associerRoleUtilisateurAdmin_InformationManquanteException_RoleVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur789");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),"");
    }
    @Test (expected = IndividuNonConnecteException.class)
    public void  admin_associerRoleUtilisateurAdmin_IndividuNonConnecte() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur333");
            connexionService.deconnexion(p.getIdentifiant());
            adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),MODERATEUR);
    }
    @Test (expected = RoleDejaAttribueException.class)
    public void  admin_associerRoleUtilisateurAdmin_RoleDejaAttribueException() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        Personne p =  connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),ADMIN);
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),ADMIN);
    }


    @Test
    public void getListeUtilisateurOK() throws IndividuNonConnecteException, CoupleUtilisateurMDPInconnuException, InformationManquanteException {
        try{
            Personne p = connexionService.connexion("admin", "admin");
            adminService.getListeUtilisateur(p.getIdentifiant());
            Assert.assertTrue(true);
        }   catch (IndividuNonConnecteException e){
            Assert.fail();
        }
    }
    @Test (expected = IndividuNonConnecteException.class)
    public void getListeUtilisateurKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"a","a");
            connexionService.connexion("a","a");
            connexionService.deconnexion(p2.getIdentifiant());
            adminService.getListeUtilisateur(p2.getIdentifiant());
    }



    @Test
    public void getUserByIdTest()  {
        Personne p = null;
        try {
            p = connexionService.connexion("admin", "admin");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        Personne p2 = null;
        try {
            p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        adminService.getUserById(p.getIdentifiant(),p2.getIdentifiant());
        Assert.assertTrue(true);
    }



    @Test
    public void supprimerRoleUtilisateurTest(){
        Personne p = null;
        try {
            p = connexionService.connexion("admin", "admin");
        } catch (InformationManquanteException e) {
            e.printStackTrace();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            e.printStackTrace();
        }
        Personne p2 = null;
        try {
            p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","utilisateur2");
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            e.printStackTrace();
        }
        try {
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        try {
            adminService.supprimerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        }
        Assert.assertTrue(true);
    }


    @Test
    public void changerMotDePasseUtilisateurTest() {
        Personne p = null;
        try {
            p = connexionService.connexion("admin", "admin");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        Personne p2 = null;
        try {
            p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur2");
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        try {
            adminService.changerMotDePasseUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),"123456");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        }
        Assert.assertTrue(true);
    }
    @Test(expected = InformationManquanteException.class)
    public void changerMotDePasseKOMdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur2");
        adminService.changerMotDePasseUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),null);
    }
    @Test(expected = InformationManquanteException.class)
    public void changerMotDePasseKOMdpVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur2");
        adminService.changerMotDePasseUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),"");
    }


    @Test
    public void supprimerUtilisateurTest() {
        Personne p = null;
        try {
            p = connexionService.connexion("admin", "admin");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        Personne p2 = null;
        try {
            p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur", "utilisateur2");
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        try {
            adminService.supprimerUtilisateur(p.getIdentifiant(),p2.getIdentifiant());
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        }
        Assert.assertTrue(true);
    }
    @Test
    public void supprimerUtilisateurTest_Basique_Version2()  {
        Personne p = null;
        try {
            p = connexionService.connexion("admin", "admin");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        long posteDemande = 0;
        try {
            posteDemande = basiquesOffLineService.posterDemandeInscription("utilisateur","1234",BASIQUE);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
        try {
            adminService.validerInscription(p.getIdentifiant(),posteDemande);
        } catch (ActionImpossibleException e) {
            Assert.fail();
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        }
        try {
            adminService.supprimerUtilisateur(p.getIdentifiant(),posteDemande);
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        }
        Assert.assertTrue(true);
    }
    @Test
    public void supprimerUtilisateurTest_Moderateur_Version2(){
        Personne p = null;
        try {
            p = connexionService.connexion("admin", "admin");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        long posteDemande = 0;
        try {
            posteDemande = basiquesOffLineService.posterDemandeInscription("utilisateur","1642",MODERATEUR);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
        try {
            adminService.validerInscription(p.getIdentifiant(),posteDemande);
        } catch (ActionImpossibleException e) {
            Assert.fail();
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        }
        try {
            adminService.supprimerUtilisateur(p.getIdentifiant(),posteDemande);
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        }
        Assert.assertTrue(true);
    }
    @Test
    public void supprimerUtilisateurTest_Admin_Version2(){
        Personne p = null;
        try {
            p = connexionService.connexion("admin", "admin");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        long posteDemande = 0;
        try {
            posteDemande = basiquesOffLineService.posterDemandeInscription("utilisateur","util1234",ADMIN);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
        try {
            adminService.validerInscription(p.getIdentifiant(),posteDemande);
        } catch (ActionImpossibleException e) {
            Assert.fail();
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        }
        try {
            adminService.supprimerUtilisateur(p.getIdentifiant(),posteDemande);
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        }
        Assert.assertTrue(true);
    }


     @Test
    public void validerInscriptionTestOK_Admin() {
         Personne p = null;
         try {
             p = connexionService.connexion("admin", "admin");
         } catch (InformationManquanteException e) {
             Assert.fail();
         } catch (CoupleUtilisateurMDPInconnuException e) {
             Assert.fail();
         }
         long demande = 0;
         try {
             demande = basiquesOffLineService.posterDemandeInscription("anas","admin123",ADMIN);
         } catch (UtilisateurDejaExistantException e) {
             Assert.fail();
         } catch (InformationManquanteException e) {
             Assert.fail();
         }
         try {
             adminService.validerInscription(p.getIdentifiant(), demande);
         } catch (ActionImpossibleException e) {
             Assert.fail();
         } catch (RoleDejaAttribueException e) {
             Assert.fail();
         }
         Assert.assertTrue(true);
    }
    @Test
    public void validerInscriptionTestOK_Basique()  {
        Personne p = null;
        try {
            p = connexionService.connexion("admin", "admin");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        long demande = 0;
        try {
            demande = basiquesOffLineService.posterDemandeInscription("anas","123",BASIQUE);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
        try {
            adminService.validerInscription(p.getIdentifiant(), demande);
        } catch (ActionImpossibleException e) {
            Assert.fail();
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        }
        Assert.assertTrue(true);
    }
    @Test
    public void validerInscriptionTestOK_Moderateur() {
        Personne p = null;
        try {
            p = connexionService.connexion("admin", "admin");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        long demande = 0;
        try {
            demande = basiquesOffLineService.posterDemandeInscription("anas","13579",MODERATEUR);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
        try {
            adminService.validerInscription(p.getIdentifiant(), demande);
        } catch (ActionImpossibleException e) {
            Assert.fail();
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        }
        Assert.assertTrue(true);
    }
    @Test (expected = ActionImpossibleException.class)
    public void validerInscriptionTestKO_Moderateur_ADMIN() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, UtilisateurDejaExistantException, ActionImpossibleException, IndividuNonConnecteException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("moderateur","moderateur");
        long demande = basiquesOffLineService.posterDemandeInscription("anas","13579",ADMIN);
        adminService.validerInscription(p2.getIdentifiant(), demande);
    }

    @Test (expected = ActionImpossibleException.class)
    public void validerInscriptionTestKO_Basique_ADMIN() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, UtilisateurDejaExistantException, ActionImpossibleException, IndividuNonConnecteException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");
        long demande = basiquesOffLineService.posterDemandeInscription("anas","13579",ADMIN);
        adminService.validerInscription(p2.getIdentifiant(), demande);
    }


    @Test (expected = ActionImpossibleException.class)
    public void validerInscriptionTestKO_Basique_MODERATEUR() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, UtilisateurDejaExistantException, ActionImpossibleException, RoleDejaAttribueException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");
        long demande = basiquesOffLineService.posterDemandeInscription("anas","13579",MODERATEUR);
        adminService.validerInscription(p2.getIdentifiant(), demande);
    }

    @Test (expected = ActionImpossibleException.class)
    public void validerInscriptionTestKO_Basique_BASIQUE() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, UtilisateurDejaExistantException, ActionImpossibleException, IndividuNonConnecteException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");
        long demande = basiquesOffLineService.posterDemandeInscription("anas","13579",BASIQUE);
        adminService.validerInscription(p2.getIdentifiant(), demande);
    }


    @Test
    public void getListeDesDemandesNonTraiteesADMIN () {
        Personne p = null;
        try {
            p = connexionService.connexion("admin", "admin");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        Personne p2 = null;
        try {
            p2 = adminService.creerUtilisateur(p.getIdentifiant(),"admin2","admin2");
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        try {
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        adminService.getListeDesDemandesNonTraitees(p2.getIdentifiant());
        Assert.assertTrue(true);
    }
    @Test
    public void getListeDesDemandesNonTraiteesMODERATEUR (){
        Personne p = null;
        try {
            p = connexionService.connexion("admin", "admin");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        Personne p2 = null;
        try {
            p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        try {
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        try {
            connexionService.connexion("moderateur","moderateur");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        adminService.getListeDesDemandesNonTraitees(p2.getIdentifiant());
        Assert.assertTrue(true);
    }
    @Test
    public void getListeDesDemandesNonTraiteesBASIQUE () {
        Personne p = null;
        try {
            p = connexionService.connexion("admin", "admin");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        Personne p2 = null;
        try {
            p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        try {
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        adminService.getListeDesDemandesNonTraitees(p2.getIdentifiant());
        Assert.assertTrue(true);
    }

    @Test
    public void refuserInscriptionTestAdminBasiqueOK(){
        long posteDemande = 0;
        try {
            posteDemande = basiquesOffLineService.posterDemandeInscription("util2","ccccc",BASIQUE);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
        Personne p = null;
        try {
            p = connexionService.connexion("admin", "admin");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        try {
            adminService.refuserInscription(p.getIdentifiant(),posteDemande);
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Assert.assertTrue(true);
    }

    @Test
    public void refuserInscriptionTestAdminModerateurOK(){
        long posteDemande = 0;
        try {
            posteDemande = basiquesOffLineService.posterDemandeInscription("util2","ccccc",MODERATEUR);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
        Personne p = null;
        try {
            p = connexionService.connexion("admin", "admin");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        try {
            adminService.refuserInscription(p.getIdentifiant(),posteDemande);
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Assert.assertTrue(true);
    }
    @Test
    public void refuserInscriptionTestAdminAdminOK() {
        long posteDemande = 0;
        try {
            posteDemande = basiquesOffLineService.posterDemandeInscription("util2","ccccc",ADMIN);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
        Personne p = null;
        try {
            p = connexionService.connexion("admin", "admin");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        try {
            adminService.refuserInscription(p.getIdentifiant(),posteDemande);
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Assert.assertTrue(true);
    }

    @Test
    public void refuserInscriptionModerateurTestBasiqueOK() {
        long posteDemande = 0;
        try {
            posteDemande = basiquesOffLineService.posterDemandeInscription("util1","ccccc",BASIQUE);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
        Personne p = null;
        try {
            p = connexionService.connexion("admin", "admin");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        Personne p2 = null;
        try {
            p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        try {
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        try {
            connexionService.connexion("moderateur","moderateur");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        try {
            adminService.refuserInscription(p2.getIdentifiant(),posteDemande);
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Assert.assertTrue(true);
    }

    @Test
    public void refuserInscriptionModerateurTestModerateurOK() {
        long posteDemande = 0;
        try {
            posteDemande = basiquesOffLineService.posterDemandeInscription("util1","ccccc",MODERATEUR);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
        Personne p = null;
        try {
            p = connexionService.connexion("admin", "admin");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        Personne p2 = null;
        try {
            p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        try {
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        try {
            connexionService.connexion("moderateur","moderateur");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        try {
            adminService.refuserInscription(p2.getIdentifiant(),posteDemande);
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Assert.assertTrue(true);
    }

    @Test (expected = ActionImpossibleException.class)
    public void refuserInscriptionModerateurTestAdminKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException, RoleDejaAttribueException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util1","ccccc",ADMIN);
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("moderateur","moderateur");
        adminService.refuserInscription(p2.getIdentifiant(),posteDemande);
    }

    @Test (expected = ActionImpossibleException.class)
    public void refuserInscriptionBasiqueTestAdminKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException, RoleDejaAttribueException, IndividuNonConnecteException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util1","ccccc",ADMIN);
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");
        adminService.refuserInscription(p2.getIdentifiant(),posteDemande);
    }

    @Test (expected = ActionImpossibleException.class)
    public void refuserInscriptionBasiqueTestModerateurKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException, RoleDejaAttribueException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util1","ccccc",MODERATEUR);
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");
        adminService.refuserInscription(p2.getIdentifiant(),posteDemande);
    }

    @Test (expected = ActionImpossibleException.class)
    public void refuserInscriptionBasiqueTestBasiquenKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException, RoleDejaAttribueException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util1","ccccc",BASIQUE);
        connexionService.connexion("basique","basique");
        adminService.refuserInscription(p2.getIdentifiant(),posteDemande);
    }





    /**
     * Moderateur peut traiter : Utilisateur Basique ou MODERATEUR
     */



    /**
     *     -------------------------------------------------------   Test de l'interface Connexion Service   -------------------------------------------------------
     */


    @Test
    public void connexionTestOK(){
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "123");
          //  adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
            connexionService.connexion(p2.getNom(), p2.getMdp());
            Assert.assertTrue(true);
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e) {
            e.printStackTrace();
        } catch (UtilisateurDejaExistantException e) {
            e.printStackTrace();
        }
    }
    @Test(expected = CoupleUtilisateurMDPInconnuException.class)
    public void connexionTestKOCoupleUtilisateurMDPInconnu() throws UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException, CoupleUtilisateurMDPInconnuException, ActionImpossibleException {

            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","utilisateur2");
            connexionService.connexion(p2.getNom(), "nana");



    }
    @Test (expected = InformationManquanteException.class)
    public void connexionKOPseudoNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "123");
        connexionService.connexion(null,p2.getMdp());
    }
    @Test (expected = InformationManquanteException.class)
    public void connexionKOMdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "123");
        connexionService.connexion(p2.getNom(),null);
    }


    @Test
    public void deconnexionTestOK() {
        Personne p = null;
        try {
            p = connexionService.connexion("admin","admin");
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        Personne p2 = null;
        try {
            p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur3","utilisateur3");
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        try {
            connexionService.connexion(p2.getNom(),p2.getMdp());
        } catch (InformationManquanteException e) {
            Assert.fail();
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        connexionService.deconnexion(p2.getIdentifiant());
        Assert.assertTrue(true);
    }
//=======================================================================//
    @Test/////////////////////////////
    public void estUnUtilisateurConnuTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur4","utilisateur4");
        connexionService.connexion(p2.getNom(), p2.getMdp());
        connexionService.estUnUtilisateurConnu(p2.getNom());
        Assert.assertTrue(true);
    }



    @Test///////////////////////////////////
    public void estUnAdminTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur5","utilisateur5");
        connexionService.connexion(p2.getNom(), p2.getMdp());
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
        connexionService.estUnAdmin(p2.getIdentifiant());
        Assert.assertTrue(true);
    }



    @Test//////////////////////////////////
    public void estUnModerateurTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur6","utilisateur6");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion(p2.getNom(), p2.getMdp());
        connexionService.estUnModerateur(p2.getIdentifiant());
        Assert.assertTrue(true);
    }


    // reste  a tester : getUserById et GetListeDemandeNonTraites

    /**
     *     -------------------------------------------------------   Test de l'interface BasiquesOffLineService    -------------------------------------------------------
     */


    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKOPseudoNullRoleNullMdpNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,null,null);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKOPseudoVideMDPVideRoleVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("","","");
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKOPseudoNullMDPVideRoleNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,"",null);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKOPseudoVideMDPNullRoleVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("",null,"");
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKOPseudoVideMDPVideRoleNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("","",null);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKOPseudoNullMDPVideRoleVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,"","");
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKOPseudoNullMDPNullRoleVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,null,"");
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKOPseudoVideMDPNullRoleNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("",null,null);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKO_PseudoNullRoleNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,"mdp123",null);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKO_PseudoVideRoleVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("","mdp123","");
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKO_PseudoNullRoleVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,"mdp123","");
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKO_PseudoVideRoleNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("","mdp123",null);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKO_MDPNullRoleNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("pseudo",null,null);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKO_MDPVideRoleVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("pseudo","","");
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKO_MDPNullRoleVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("pseudo",null,"");
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKO_MDPVideRoleNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("pseudo","",null);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKO_RoleVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("pseudo","mdp123","");
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionKO_RoleNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("pseudo","mdp123",null);
    }




    @Test
    public void posterDemandeInscriptionBasiqueTestOK() {
        try {
            basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur123",BASIQUE);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
    }

    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKO_PseudoVideMdpVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("","",BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKO_PseudoNullMdpNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,null,BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKO_PseudoNullMdpVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,"",BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKO_PseudoVideMdpNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("",null,BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKO_PseudoVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("","mpd123",BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKO_PseudoNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,"mdp123",BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKO_MdpNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("pseudo",null,BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKO_MdpVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("pseudo","",BASIQUE);
    }


    @Test
    public void posterDemandeInscriptionAdminTest_OK()  {
        try {
            basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur123",ADMIN);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
    }

    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKO_PseudoVideMdpVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("","",ADMIN);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKO_PseudoNullMdpNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,null,ADMIN);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKO_PseudoNullMdpVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,"",ADMIN);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKO_PseudoVideMdpNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("",null,ADMIN);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKO_PseudoVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("","mpd123",ADMIN);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKO_PseudoNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,"mdp123",ADMIN);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKO_MdpNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("pseudo",null,ADMIN);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKO_MdpVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("pseudo","",ADMIN);
    }


    @Test
    public void posterDemandeInscriptionModerateurTestOK() {
        try {
            basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur123",MODERATEUR);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
    }

    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKO_PseudoVideMdpVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("","",MODERATEUR);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKO_PseudoNullMdpNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,null,MODERATEUR);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKO_PseudoNullMdpVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,"",MODERATEUR);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKO_PseudoVideMdpNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("",null,MODERATEUR);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKO_PseudoVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("","mpd123",MODERATEUR);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKO_PseudoNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,"mdp123",MODERATEUR);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKO_MdpNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("pseudo",null,MODERATEUR);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKO_MdpVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("pseudo","",MODERATEUR);
    }


    /**
     *     -------------------------------------------------------   Test de l'interface Personne    -------------------------------------------------------
     */

//=============================================================//
    @Test
    public void supprimerRoleModerateur() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","123456");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion(p2.getNom(),p2.getMdp());
        p2.getRoles(); /** Récupere la liste des Roles de l'utilisateur **/
        p2.hasRole(MODERATEUR);
        p2.supprimerRole(MODERATEUR); /** Supprime le role MODERATEUR **/
    }
    @Test
    public void supprimerRoleBasique() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","123");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion(p2.getNom(),p2.getMdp());
        p2.getRoles();
        p2.hasRole(BASIQUE);
        p2.supprimerRole(BASIQUE);
        Assert.assertTrue(true);
    }
    @Test
    public void supprimerRoleAdmin() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"admin2","admin2");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
        p2.getRoles();
        p2.hasRole(ADMIN);
        adminService.supprimerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
        Assert.assertTrue(true);
    }




    @Test
    public void addRoleModerateurOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"anas","moderateur234");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p2.getNom(),p2.getMdp());

            /**  p2.getRoles();
             if (p.hasRole(MODERATEUR) == false) **/

            p2.addRole(MODERATEUR);
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            e.printStackTrace();
        } catch (ActionImpossibleException e) {
            e.printStackTrace();
        }
    }
    @Test(expected = RoleDejaAttribueException.class)
    public void addRoleModerateurKORoleDejaAttribue() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException, RoleDejaAttribueException {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"anas","moderateur234");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p2.getNom(),p2.getMdp());
            p2.getRoles();
            p2.hasRole(MODERATEUR);
            p2.addRole(MODERATEUR);
    }



    @Test
    public void addRoleBasiqueOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"anas","moderateur132");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p2.getNom(),p2.getMdp());
            p2.addRole(BASIQUE);
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        }
    }@Test (expected = RoleDejaAttribueException.class)
    public void addRoleBasiqueKORoleDejaAttribue() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, RoleDejaAttribueException, ActionImpossibleException, IndividuNonConnecteException {

            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"anas","moderateur345");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p2.getNom(),p2.getMdp());
            p2.getRoles();
            p2.hasRole(BASIQUE);
            p2.addRole(BASIQUE);

    }


    @Test
    public void addRoleAdminOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","basique789");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p2.getNom(),p2.getMdp());
            p2.getRoles();
            p2.hasRole(ADMIN);
            p2.addRole(ADMIN);
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        }
    }
    @Test (expected = RoleDejaAttribueException.class)
    public void addRoleAdminKORoleDejaAttribueException() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException, RoleDejaAttribueException {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","1234");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p2.getNom(),p2.getMdp());

            p2.getRoles();
            p2.hasRole(ADMIN);
            p2.addRole(ADMIN);

    }

    /********************************     Tests de l'Interface ForumService ********************************/


   /* @Test
    public void ajouterMessage_Test_OK() {
        Message message = EasyMock.createMock(Message.class);
        Topic topic = EasyMock.createMock(Topic.class);
        topic.ajouterMessage(message);
        EasyMock.expect(topic.ajouterMessage(message)).andReturn(true);
        EasyMock.replay(message,topic);

        boolean res = topic.ajouterMessage(message);

        Assert.assertTrue(res);
    }    */

    /**
     * Ajouter une exception à la méthode ajouterMessage() dans l'interface Topic.java
     */
    @Test
    public void test_OK_getListeTopicPourUnTheme() throws InformationManquanteException {
       try {
           Theme theme = forumService.creerThemeBis("Sante");
           forumService.getListeTopicPourUnTheme(theme);
           Assert.assertTrue(true);
       } catch (ThemeInexistantException e){
           Assert.fail();
       }
    }

    @Test (expected = ThemeInexistantException.class)
    public void test_KO_getListeTopicPourUnTheme() throws ThemeInexistantException, InformationManquanteException {
            Theme theme1 = forumService.recupererTheme("Foot");
            forumService.getListeTopicPourUnTheme(theme1);
    }

    @Test
    public void test_OK_recupererTheme() throws InformationManquanteException {
        try {
            Theme theme = forumService.creerThemeBis("Sante");
            forumService.recupererTheme("Sante");
            Assert.assertTrue(true);
        }catch (ThemeInexistantException e){
            Assert.fail();
        }

    }

    @Test(expected = ThemeInexistantException.class)
    public void test_KO_recupererTheme() throws ThemeInexistantException {
        forumService.recupererTheme("Foot");
    }


    @Test
    public void test_OK_getListeMessagePourUnTopic() throws TopicInexistantException, ThemeInexistantException, InformationManquanteException, NomTopicDejaExistantException {
        Theme theme = forumService.creerThemeBis("Sante");
        Topic topic = forumService.creerTopic("Allergie",theme,"Anas");
        Message message =forumService.creerMessage("Anas",topic,"je suis allergique mais je ne sais pas a quoi");
        forumService.ajouterMessage(topic,theme,message);
        forumService.getListeMessagePourUnTopic(topic);
        Assert.assertTrue(true);

    }

    @Test(expected = TopicInexistantException.class)
    public void test_KO_getListeMessagePourUnTopic_TopicInexistant() throws TopicInexistantException, ThemeInexistantException, InformationManquanteException {
        Theme sante = forumService.creerThemeBis("Sante");
        Topic foot = forumService.recupererTopic("Foot","Sante");
        forumService.getListeMessagePourUnTopic(foot);
    }

    @Test
    public void recupererTopic_OK() throws ThemeInexistantException, InformationManquanteException, NomTopicDejaExistantException {
        try {
            Theme theme = forumService.creerThemeBis("Sante");
            Topic topic = forumService.creerTopic("Allergie",theme,"Ilyas");
            forumService.recupererTopic("Allergie", "Sante");
            Assert.assertTrue(true);
        } catch (TopicInexistantException e) {
            Assert.fail();
        }
    }

    @Test (expected = TopicInexistantException.class)
    public void recupererTopic_KO_TopicInexistant() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException {
        Theme sante = forumService.creerThemeBis("Sante");
        forumService.recupererTopic("Foot", "Sante");
    }

    @Test (expected = ThemeInexistantException.class)
    public void recupererTopic_KO_ThemeInexistant() throws ThemeInexistantException, TopicInexistantException {
        forumService.recupererTopic("Foot", "sport");
    }



    @Test
    public void ajouterMessage_test_OK() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, NomTopicDejaExistantException {
            Personne p = connexionService.connexion("admin","admin");
            Theme sante = forumService.creerThemeBis("Sante");
            Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");
            Message message = forumService.creerMessage(p.getNom(),allergie,"nouveau message");
            forumService.ajouterMessage(allergie,sante,message);

            Assert.assertTrue(true);
    }
    @Test (expected = InformationManquanteException.class)
    public void ajouterMessage_test_KO_MessageNull() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p.getNom(),allergie,null);
        forumService.ajouterMessage(allergie,sante,message);
    }

    @Test (expected = InformationManquanteException.class)
    public void ajouterMessage_test_KO_MessageVide() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p.getNom(),allergie,"");
        forumService.ajouterMessage(allergie,sante,message);
    }
////

    @Test
    public void ajouterMessage_test_OK_Moderateur() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("moderateur","moderateur");


        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p2.getNom(),allergie,"nouveau message");
        forumService.ajouterMessage(allergie,sante,message);
        Assert.assertTrue(true);
    }

    @Test (expected = InformationManquanteException.class)
    public void ajouterMessage_test_KO_Moderateur_MessageNull() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, UtilisateurDejaExistantException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("moderateur","moderateur");


        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p2.getNom(),allergie,null);
        forumService.ajouterMessage(allergie,sante,message);
    }

    @Test (expected = InformationManquanteException.class)
    public void ajouterMessage_test_KO_Moderateur_MessageVide() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("moderateur","moderateur");


        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p2.getNom(),allergie,"");
        forumService.ajouterMessage(allergie,sante,message);
    }
/////

    @Test
    public void ajouterMessage_test_OK_Basique() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, UtilisateurDejaExistantException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");


        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");

        Message message = forumService.creerMessage(p2.getNom(),allergie,"nouveau message");
        forumService.ajouterMessage(allergie,sante,message);
        Assert.assertTrue(true);
    }
    @Test (expected = InformationManquanteException.class)
    public void ajouterMessage_test_KO_Basique_MessageNull() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");


        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p2.getNom(),allergie,null);
        forumService.ajouterMessage(allergie,sante,message);
    }
    @Test (expected = InformationManquanteException.class)
    public void ajouterMessage_test_KO_Basique_MessageVide() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");

        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p2.getNom(),allergie,"");
        forumService.ajouterMessage(allergie,sante,message);
    }



    @Test
    public void creerMessage_test_OK_Admin() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");
        forumService.creerMessage(p.getNom(),allergie,"new message");
        Assert.assertTrue(true);
    }
    @Test (expected = InformationManquanteException.class)
    public void creerMessage_test_KO_Admin_MessageVide() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");
        forumService.creerMessage(p.getNom(),allergie,"");
    }
    @Test (expected = InformationManquanteException.class)
    public void creerMessage_test_KO_Admin_MessageNull() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");
        forumService.creerMessage(p.getNom(),allergie,null);
    }


    @Test
    public void creerMessage_test_OK_Moderateur() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("moderateur","moderateur");

        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");
        forumService.creerMessage(p2.getNom(),allergie,"new message");
        Assert.assertTrue(true);
    }

    @Test (expected = InformationManquanteException.class)
    public void creerMessage_test_KO_Moderateur_MessageVide() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("moderateur","moderateur");

        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");
        forumService.creerMessage(p2.getNom(),allergie,"");
    }


    @Test (expected = InformationManquanteException.class)
    public void creerMessage_test_KO_Moderateur_MessageNull() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("moderateur","moderateur");


        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");
        forumService.creerMessage(p2.getNom(),allergie,null);
    }


    @Test
    public void creerMessage_test_OK_Basique() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");

        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");


        forumService.creerMessage(p2.getNom(),allergie,"new message");
        Assert.assertTrue(true);
    }

/////

    @Test (expected = InformationManquanteException.class)
    public void creerMessage_test_KO_Basique_MessageVide() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");
        forumService.creerMessage(p2.getNom(),allergie,"");
    }


    @Test (expected = InformationManquanteException.class)
    public void creerMessage_test_KO_Basique_MessageNull() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");

        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerTopic("Allergie",sante,"Ilyas");

        forumService.creerMessage(p2.getNom(),allergie,null);
    }


    @Test
    public void creerTheme_OK() throws InformationManquanteException {
        forumService.creerTheme("Sport");
    }
    @Test (expected = InformationManquanteException.class)
    public void creerTheme_K0_nomThemeNull() throws InformationManquanteException {
        forumService.creerTheme(null);
    }
    @Test (expected = InformationManquanteException.class)
    public void creerTheme_K0_nomThemeVide() throws InformationManquanteException {
        forumService.creerTheme("");
    }



    @Test
    public void creerTopic_OK() throws ThemeInexistantException, NomTopicDejaExistantException, InformationManquanteException {
        Theme sante = forumService.creerThemeBis("Sante");
        forumService.creerTopic("Cancer",sante,"Ilyas");
    }
    @Test (expected = InformationManquanteException.class)
    public void creerTopic_K0_NomTopicNull() throws ThemeInexistantException, NomTopicDejaExistantException, InformationManquanteException {
        Theme sante = forumService.creerThemeBis("Sante");
        forumService.creerTopic(null,sante,"Ilyas");
    }
    @Test (expected = InformationManquanteException.class)
    public void creerTopic_K0_NomTopicVide() throws ThemeInexistantException, NomTopicDejaExistantException, InformationManquanteException {

        Theme sante = forumService.creerThemeBis("Sante");
        forumService.creerTopic("",sante,"Ilyas");
    }





}