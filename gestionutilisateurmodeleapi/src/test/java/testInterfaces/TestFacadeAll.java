package testInterfaces;

import facade.*;
import facade.erreurs.*;
import modele.forum.Message;
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
        adminService = fabriqueFacade.getAdminService(connexionService);
        forumService = fabriqueFacade.getForumService();

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
        }
    }
    @Test(expected = UtilisateurDejaExistantException.class)
    public void creerUtilisateurTestKOUtilisDejaExis() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            adminService.creerUtilisateur(p.getIdentifiant(),"aaa", "bbb");
        } catch (UtilisateurDejaExistantException e) {
            e.printStackTrace();
        }
        adminService.creerUtilisateur(3L,"aaa","bbb");
    }
    @Test(expected = IndividuNonConnecteException.class)
    public void creerUtilisateurTestKOIndividuNonConnecte() throws UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, CoupleUtilisateurMDPInconnuException {
        Personne p = connexionService.connexion("admin","admin");
        connexionService.deconnexion(p.getIdentifiant());
        adminService.creerUtilisateur(p.getIdentifiant(),"aaa", "bbb");
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),null,"bbb");
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomNull_and_mdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),null,null);
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomVide_and_mdpVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),"","");
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomNull_and_mdpVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),null,"");
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomVide_and_mdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),"",null);
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_MdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),"aaa",null);
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),"","bbb");
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_MdpVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),"aaa","");
    }


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
        try {
            adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),BASIQUE);
        } catch (RoleDejaAttribueException e1) {
            e1.printStackTrace();
        }
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
        Personne p = connexionService.connexion("admin", "admin");
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
        try {
            adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),MODERATEUR);
        } catch (RoleDejaAttribueException e1) {
            e1.printStackTrace();
        }
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
        try {
            adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),ADMIN);
        } catch (RoleDejaAttribueException e1) {
            e1.printStackTrace();
        }
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),ADMIN);
    }



    /**
     * Quand un MODERATEUR veut associer MODERATEUR à un utilisateur.
     */
    @Test
    public void  moderateur_associerRoleUtilisateurModerateur() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur2", "123");
            adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p3.getNom(), p3.getMdp());
            Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "utilisateur3", "12345");
            adminService.associerRoleUtilisateur(p3.getIdentifiant(), p4.getIdentifiant(), MODERATEUR);
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e1) {
            Assert.fail();
        }
    }
    @Test(expected =InformationManquanteException.class)
    public void moderateur_associerRoleUtilisateurModerateur_InformationManquanteException_RoleNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "moderateur", "moderateur123");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p3.getNom(), p3.getMdp());
        Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "anas", "anas12345");
        adminService.associerRoleUtilisateur(p3.getIdentifiant(), p4.getIdentifiant(), null);
    }
    @Test(expected =InformationManquanteException.class)
    public void moderateur_associerRoleUtilisateurModerateur_InformationManquanteException_RoleVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "util2", "123");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p3.getNom(), p3.getMdp());
        Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "util3", "12345");
        adminService.associerRoleUtilisateur(p3.getIdentifiant(), p4.getIdentifiant(), "");
    }
    @Test (expected = IndividuNonConnecteException.class)
    public void  moderateur_associerRoleUtilisateurModerateur_IndividuNonConnecte() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "util4", "726");
            adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p3.getNom(), p3.getMdp());
            Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "ilyas", "ilyas12345");
            connexionService.deconnexion(p3.getIdentifiant());
            adminService.associerRoleUtilisateur(p3.getIdentifiant(), p4.getIdentifiant(), MODERATEUR);

    }
    @Test (expected = RoleDejaAttribueException.class)
    public void  moderateur_associerRoleUtilisateurModerateur_RoleDejaAttribueException() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        Personne p =  connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"anas","moderateur555");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(), MODERATEUR);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p2.getNom(), p2.getMdp());
        Personne p3 = adminService.creerUtilisateur(p2.getIdentifiant(), "hajar", "moderateur666");
            adminService.associerRoleUtilisateur(p2.getIdentifiant(), p3.getIdentifiant(),MODERATEUR);

            adminService.associerRoleUtilisateur(p2.getIdentifiant(), p3.getIdentifiant(),MODERATEUR);
    }
    /**
     * Quand un MODERATEUR veut associer BASIQUE à un utilisateur.
     */
    @Test
    public void  moderateur_associerRoleUtilisateurBasique() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "moderateur", "moderateur3213");
            adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p3.getNom(), p3.getMdp());
            Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "admin12", "admin12345");
            adminService.associerRoleUtilisateur(p3.getIdentifiant(), p4.getIdentifiant(), BASIQUE);
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e1) {
            Assert.fail();
        }
    }
    @Test(expected =InformationManquanteException.class)
    public void moderateur_associerRoleUtilisateurBasique_InformationManquanteException_RoleNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "moderateur3213");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p3.getNom(), p3.getMdp());
        Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "utilisateur2", "123456");
        adminService.associerRoleUtilisateur(p3.getIdentifiant(), p4.getIdentifiant(), null);
    }
    @Test(expected =InformationManquanteException.class)
    public void moderateur_associerRoleUtilisateurBasique_InformationManquanteException_RoleVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "moderateur123");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p3.getNom(), p3.getMdp());
        Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "utilisateur2", "123456");
        adminService.associerRoleUtilisateur(p3.getIdentifiant(), p4.getIdentifiant(), "");
        Assert.fail();
    }
    @Test (expected = IndividuNonConnecteException.class)
    public void  moderateur_associerRoleUtilisateurBasique_IndividuNonConnecte() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "moderateur", "moderateur332");
            adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p3.getNom(), p3.getMdp());
            Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "admin12", "admin12345");
            connexionService.deconnexion(p3.getIdentifiant());
            adminService.associerRoleUtilisateur(p3.getIdentifiant(), p4.getIdentifiant(), BASIQUE);

    }
    @Test (expected = RoleDejaAttribueException.class)
    public void  moderateur_associerRoleUtilisateurBasique_RoleDejaAttribueException() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        Personne p =  connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","moderateur123");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(), MODERATEUR);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p2.getNom(), p2.getMdp());
        Personne p3 = adminService.creerUtilisateur(p2.getIdentifiant(), "utilisateur2", "basique123");
        try {
            adminService.associerRoleUtilisateur(p2.getIdentifiant(), p3.getIdentifiant(),BASIQUE);
        } catch (RoleDejaAttribueException e1) {
            e1.printStackTrace();
        }
        adminService.associerRoleUtilisateur(p2.getIdentifiant(), p3.getIdentifiant(),BASIQUE);
    }
    /**
     * Quand un MODERATEUR veut associer ADMIN à un utilisateur.
     */
    @Test(expected = ActionImpossibleException.class)
    public void  moderateur_associerRoleUtilisateur_Admin() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "moderateur123");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p3.getNom(), p3.getMdp());
        Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "utilisateur2", "12345");
        adminService.associerRoleUtilisateur(p3.getIdentifiant(),p4.getIdentifiant(), ADMIN);
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
    public void getListeUtilisateurKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"a","a");
            connexionService.connexion("a","a");
            connexionService.deconnexion(p2.getIdentifiant());
            adminService.getListeUtilisateur(p2.getIdentifiant());
    }



    @Test
    public void getUserByIdTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
        adminService.getUserById(p.getIdentifiant(),p2.getIdentifiant());
        Assert.assertTrue(true);
    }



    @Test
    public void supprimerRoleUtilisateurTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","utilisateur2");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        adminService.supprimerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        Assert.assertTrue(true);
    }


    @Test
    public void changerMotDePasseUtilisateurTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur2");
        adminService.changerMotDePasseUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),"123456");
        Assert.assertTrue(true);
    }
    @Test(expected = InformationManquanteException.class)
    public void changerMotDePasseKOMdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur2");
        adminService.changerMotDePasseUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),null);
    }
    @Test(expected = InformationManquanteException.class)
    public void changerMotDePasseKOMdpVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur2");
        adminService.changerMotDePasseUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),"");
    }


    @Test
    public void supprimerUtilisateurTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur", "utilisateur2");
        adminService.supprimerUtilisateur(p.getIdentifiant(),p2.getIdentifiant());
        Assert.assertTrue(true);
    }
    @Test
    public void supprimerUtilisateurTest_Basique_Version2() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin", "admin");
        long posteDemande = basiquesOffLineService.posterDemandeInscription("utilisateur","1234",BASIQUE);
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),posteDemande);
        adminService.validerInscription(p.getIdentifiant(),posteDemande);
        adminService.supprimerUtilisateur(p.getIdentifiant(),posteDemande);
        Assert.assertTrue(true);
    }
    @Test
    public void supprimerUtilisateurTest_Moderateur_Version2() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin", "admin");
        long posteDemande = basiquesOffLineService.posterDemandeInscription("utilisateur","1642",MODERATEUR);
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),posteDemande);
        adminService.validerInscription(p.getIdentifiant(),posteDemande);
        adminService.supprimerUtilisateur(p.getIdentifiant(),posteDemande);
        Assert.assertTrue(true);
    }
    @Test
    public void supprimerUtilisateurTest_Admin_Version2() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin", "admin");
        long posteDemande = basiquesOffLineService.posterDemandeInscription("utilisateur","util1234",ADMIN);
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),posteDemande);
        adminService.validerInscription(p.getIdentifiant(),posteDemande);
        adminService.supprimerUtilisateur(p.getIdentifiant(),posteDemande);
        Assert.assertTrue(true);
    }


     @Test
    public void validerInscriptionTestOK_Admin() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, UtilisateurDejaExistantException, ActionImpossibleException {
            Personne p = connexionService.connexion("admin", "admin");
            long demande = basiquesOffLineService.posterDemandeInscription("anas","admin123",ADMIN);
            adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),demande);
            adminService.validerInscription(p.getIdentifiant(), demande);
            Assert.assertTrue(true);
    }
    @Test
    public void validerInscriptionTestOK_Basique() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, UtilisateurDejaExistantException, ActionImpossibleException {
            Personne p = connexionService.connexion("admin", "admin");
            long demande = basiquesOffLineService.posterDemandeInscription("anas","123",BASIQUE);
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),demande);
            adminService.validerInscription(p.getIdentifiant(), demande);
            Assert.assertTrue(true);
    }
    @Test
    public void validerInscriptionTestOK_Moderateur() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, UtilisateurDejaExistantException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin", "admin");
        long demande = basiquesOffLineService.posterDemandeInscription("anas","13579",MODERATEUR);
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),demande);
        adminService.validerInscription(p.getIdentifiant(), demande);
        Assert.assertTrue(true);
    }
    @Test (expected = ActionImpossibleException.class)
    public void validerInscriptionTestKO_Moderateur_ADMIN() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, UtilisateurDejaExistantException, ActionImpossibleException {
        Personne p = connexionService.connexion("moderateur1", "moderateur1");
        long demande = basiquesOffLineService.posterDemandeInscription("anas","13579",ADMIN);
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),demande);
        adminService.validerInscription(p.getIdentifiant(), demande);
    }

    @Test (expected = ActionImpossibleException.class)
    public void validerInscriptionTestKO_Basique_ADMIN() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, UtilisateurDejaExistantException, ActionImpossibleException {
        Personne p = connexionService.connexion("basique", "basique");
        long demande = basiquesOffLineService.posterDemandeInscription("anas","13579",ADMIN);
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),demande);
        adminService.validerInscription(p.getIdentifiant(), demande);
    }


    @Test (expected = ActionImpossibleException.class)
    public void validerInscriptionTestKO_Basique_MODERATEUR() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, UtilisateurDejaExistantException, ActionImpossibleException {
        Personne p = connexionService.connexion("basique", "basique");
        long demande = basiquesOffLineService.posterDemandeInscription("anas","13579",MODERATEUR);
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),demande);
        adminService.validerInscription(p.getIdentifiant(), demande);
    }

    @Test (expected = ActionImpossibleException.class)
    public void validerInscriptionTestKO_Basique_BASIQUE() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, UtilisateurDejaExistantException, ActionImpossibleException {
        Personne p = connexionService.connexion("basique", "basique");
        long demande = basiquesOffLineService.posterDemandeInscription("anas","13579",BASIQUE);
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),demande);
        adminService.validerInscription(p.getIdentifiant(), demande);
    }

    @Test
    public void getListeDesDemandesNonTraiteesADMIN () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"admin2","admin2");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
        adminService.getListeDesDemandesNonTraitees(p2.getIdentifiant());
        Assert.assertTrue(true);
    }
    @Test
    public void getListeDesDemandesNonTraiteesMODERATEUR () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        adminService.getListeDesDemandesNonTraitees(p2.getIdentifiant());
        Assert.assertTrue(true);
    }
    @Test
    public void getListeDesDemandesNonTraiteesBASIQUE () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        adminService.getListeDesDemandesNonTraitees(p2.getIdentifiant());
        Assert.assertTrue(true);
    }

    @Test
    public void refuserInscriptionTestAdminBasiqueOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util2","ccccc",BASIQUE);
        Personne p = connexionService.connexion("admin", "admin");
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),posteDemande);
        adminService.refuserInscription(p.getIdentifiant(),posteDemande);
        Assert.assertTrue(true);
    }

    @Test
    public void refuserInscriptionTestAdminModerateurOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util2","ccccc",MODERATEUR);
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),posteDemande);
        Personne p = connexionService.connexion("admin", "admin");
        adminService.refuserInscription(p.getIdentifiant(),posteDemande);
        Assert.assertTrue(true);
    }
    @Test
    public void refuserInscriptionTestAdminAdminOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util2","ccccc",ADMIN);
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),posteDemande);
        Personne p = connexionService.connexion("admin", "admin");
        adminService.refuserInscription(p.getIdentifiant(),posteDemande);
        Assert.assertTrue(true);
    }
    
    @Test
    public void refuserInscriptionModerateurTestBasiqueOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util1","ccccc",BASIQUE);
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),posteDemande);
        Personne p = connexionService.connexion("moderateur1", "moderateur1");
        adminService.refuserInscription(p.getIdentifiant(),posteDemande);
        Assert.assertTrue(true);
    }

    @Test
    public void refuserInscriptionModerateurTestModerateurOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util1","ccccc",MODERATEUR);
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),posteDemande);
        Personne p = connexionService.connexion("moderateur1", "moderateur1");
        adminService.refuserInscription(p.getIdentifiant(),posteDemande);
        Assert.assertTrue(true);
    }

    @Test (expected = ActionImpossibleException.class)
    public void refuserInscriptionModerateurTestAdminKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util1","ccccc",ADMIN);
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),posteDemande);
        Personne p = connexionService.connexion("moderateur1", "moderateur1");
        adminService.refuserInscription(p.getIdentifiant(),posteDemande);
    }

    @Test (expected = ActionImpossibleException.class)
    public void refuserInscriptionBasiqueTestAdminKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util1","ccccc",ADMIN);
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),posteDemande);
        Personne p = connexionService.connexion("basique", "basique");
        adminService.refuserInscription(p.getIdentifiant(),posteDemande);
    }

    @Test (expected = ActionImpossibleException.class)
    public void refuserInscriptionBasiqueTestModerateurKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util1","ccccc",MODERATEUR);
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),posteDemande);
        Personne p = connexionService.connexion("basique", "basique");
        adminService.refuserInscription(p.getIdentifiant(),posteDemande);
    }

    @Test (expected = ActionImpossibleException.class)
    public void refuserInscriptionBasiqueTestBasiquenKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        Personne p = connexionService.connexion("basique", "basique");
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util1","ccccc",BASIQUE);
        adminService.setListeDemandesNonTRaitees(basiquesOffLineService.getListeDemandes(),posteDemande);
        adminService.refuserInscription(p.getIdentifiant(),posteDemande);
    }





    /**
     * Moderateur peut traiter : Utilisateur Basique ou MODERATEUR
     */



    /**
     *     -------------------------------------------------------   Test de l'interface Connexion Service   -------------------------------------------------------
     */


    @Test
    public void connexionTestOK() throws UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "123");
          //  adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
            connexionService.connexion(p2.getNom(), p2.getMdp());
            Assert.assertTrue(true);
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
    }
    @Test(expected = CoupleUtilisateurMDPInconnuException.class)
    public void connexionTestKOCoupleUtilisateurMDPInconnu() throws UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException, CoupleUtilisateurMDPInconnuException {

            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","utilisateur2");
            connexionService.connexion(p2.getNom(), "nana");



    }
    @Test (expected = InformationManquanteException.class)
    public void connexionKOPseudoNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "123");
        connexionService.connexion(null,p2.getMdp());
    }
    @Test (expected = InformationManquanteException.class)
    public void connexionKOMdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "123");
        connexionService.connexion(p2.getNom(),null);
    }


    @Test
    public void deconnexionTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur3","utilisateur3");
        connexionService.connexion(p2.getNom(),p2.getMdp());
        connexionService.deconnexion(p2.getIdentifiant());
        Assert.assertTrue(true);
    }

    @Test
    public void estUnUtilisateurConnuTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur4","utilisateur4");
        connexionService.connexion(p2.getNom(), p2.getMdp());
        connexionService.estUnUtilisateurConnu(p2.getNom());
        Assert.assertTrue(true);
    }



    @Test
    public void estUnAdminTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur5","utilisateur5");
        connexionService.connexion(p2.getNom(), p2.getMdp());
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
        connexionService.estUnAdmin(p2.getIdentifiant());
        Assert.assertTrue(true);
    }



    @Test
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
    public void posterDemandeInscriptionBasiqueTestOK() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur123",BASIQUE);
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
    public void posterDemandeInscriptionAdminTest_OK() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur123",ADMIN);
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
    public void posterDemandeInscriptionModerateurTestOK() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur123",MODERATEUR);
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

    /********************************     Tests de l'Interface Topic ********************************/


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
    public void ajouterMessage_Test_KO(){

    }





}



