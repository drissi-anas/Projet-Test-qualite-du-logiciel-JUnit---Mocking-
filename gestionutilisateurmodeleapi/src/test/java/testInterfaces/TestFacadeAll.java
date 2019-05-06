package testInterfaces;

import facade.AdminService;
import facade.BasiquesOffLineService;
import facade.ConnexionService;
import facade.FabriqueFacade;
import facade.erreurs.*;
import modele.forum.Message;
import modele.forum.Topic;
import modele.inscription.InscriptionPotentielle;
import modele.personnes.Personne;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



import static facade.AdminService.*;

public abstract class  TestFacadeAll {

    AdminService adminService;
    BasiquesOffLineService basiquesOffLineService;
    ConnexionService connexionService;
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
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","utilisateur2");
        adminService.changerMotDePasseUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),"123456");
        Assert.assertTrue(true);
    }
    @Test(expected = InformationManquanteException.class)
    public void changerMotDePasseKOMdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","utilisateur2");
        adminService.changerMotDePasseUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),null);
    }

    @Test
    public void supprimerUtilisateurTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2", "utilisateur2");
        adminService.supprimerUtilisateur(p.getIdentifiant(),p2.getIdentifiant());
        Assert.assertTrue(true);
    }


    /**
     @Test
     public void validerInscriptionTestOK() throws CoupleUtilisateurMDPInconnuException {
     try {
     Personne p = connexionService.connexion("admin", "admin");
     long admin = basiquesOffLineService.posterDemandeInscription("admin","admin123",ADMIN);
     adminService.validerInscription(p.getIdentifiant(), admin);
     Assert.assertTrue(true);
     } catch (UtilisateurDejaExistantException e){
     Assert.fail();
     } catch (RoleDejaAttribueException e){
     Assert.fail();
     }
     }


     @Test(expected = UtilisateurDejaExistantException.class)
     public void validerInscriptionTestKOUtilisateurDejaExistant() throws RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
     Personne p = connexionService.connexion("admin", "admin");
     Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "utilisateur");
     try {
     adminService.validerInscription(p.getIdentifiant(), p2.getIdentifiant());
     } catch (UtilisateurDejaExistantException e) {
     e.printStackTrace();
     }
     adminService.validerInscription(p.getIdentifiant(), p2.getIdentifiant());
     }

     **/
    @Test(expected = RoleDejaAttribueException.class)
    public void validerInscriptionTestKORoleDejaAttribueException() throws RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur2", "utilisateur2");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
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

    /**
     *     -------------------------------------------------------   Test de l'interface BasiquesOffLineService    -------------------------------------------------------
     */


    @Test
    public void posterDemandeInscriptionBasiqueTestOK() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur",BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKORoleNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur",null);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKORoleVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur","");
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKOPseudoNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,"utilisateur",BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKOPseudoVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("","utilisateur",BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKOMdpNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("utilisateur",null,BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKOMdpVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("utilisateur","",BASIQUE);
    }




    @Test
    public void posterDemandeInscriptionModerateurTestOK() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("moderateur","moderateur",MODERATEUR);
        Assert.assertTrue(true);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKORoleNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("moderateur","moderateur",null);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKORoleVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("moderateur","moderateur","");
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKOPseudoNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,"moderateur",MODERATEUR);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKOPseudoVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("","moderateur",MODERATEUR);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKOMdpNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("moderateur",null,MODERATEUR);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKOMdpVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("moderateur","",MODERATEUR);
    }



    @Test
    public void posterDemandeInscriptionAdminTestOK() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("admin","admin",ADMIN);
        Assert.assertTrue(true);
    }


    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKORoleNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("admin","admin",null);
    }

    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKORoleVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("admin","admin","");
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKOPseudoNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription(null,"admin",null);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKOPseudoVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("","admin","");
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKOMdpoNull() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("admin",null,null);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKOMdpoVide() throws UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("admin","",null);
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
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur234");
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
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur234");
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
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur132");
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
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur345");
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



