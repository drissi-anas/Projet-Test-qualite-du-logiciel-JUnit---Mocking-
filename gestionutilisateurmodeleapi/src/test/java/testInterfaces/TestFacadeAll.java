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
    public void initialisationModele(){
        adminService = fabriqueFacade.getAdminService();
        basiquesOffLineService = fabriqueFacade.getBasiquesOffLineService();
        connexionService = fabriqueFacade.getConnexionService();

    }
    /**
     *     -------------------------------------------------------   Test de l'interface AdminService   -------------------------------------------------------
     */

    @Test
    public void creerUtilisateurTestOK() throws CoupleUtilisateurMDPInconnuException {
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
    public void creerUtilisateurTestKOUtilisDejaExis() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            adminService.creerUtilisateur(p.getIdentifiant(),"aaa", "bbb");
        } catch (UtilisateurDejaExistantException e) {
            e.printStackTrace();
        }
        adminService.creerUtilisateur(3L,"aaa","bbb");
    }
    @Test(expected = IndividuNonConnecteException.class)
    public void creerUtilisateurTestKOIndividuNonConnecte() throws UtilisateurDejaExistantException {
        try {
            adminService.creerUtilisateur(1L,"aaa", "bbb");
        } catch (IndividuNonConnecteException e){
            e.printStackTrace();
        }
    }

    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),null,"bbb");
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomNull_and_mdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),null,null);
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomVide_and_mdpVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),"","");
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomNull_and_mdpVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),null,"");
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomVide_and_mdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),"",null);
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_MdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),"aaa",null);
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_NomVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),"","bbb");
    }
    @Test(expected =InformationManquanteException.class)
    public void creerUtilisateurKO_MdpVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),"aaa","");
    }


    /**
     * Quand un ADMIN veut associer BASIQUE à un utilisateur.
     */
    @Test
    public void  admin_associerRoleUtilisateurBasique_OK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur123");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e1) {
            Assert.fail();
        }
    }
    @Test(expected =InformationManquanteException.class)
    public void admin_associerRoleUtilisateurBasique_InformationManquanteException_RoleNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin111");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur123");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),null);
        Assert.fail();
    }
    @Test(expected =InformationManquanteException.class)
    public void admin_associerRoleUtilisateurBasique_InformationManquanteException_RoleVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin111");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur123");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),"");
        Assert.fail();
    }
    @Test (expected = IndividuNonConnecteException.class)
    public void  admin_associerRoleUtilisateur_IndividuNonConnecte() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur45");
            connexionService.deconnexion(p.getIdentifiant());
            adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),BASIQUE);
        } catch (IndividuNonConnecteException e1) {
            e1.printStackTrace();
        }
    }
    @Test (expected = RoleDejaAttribueException.class)
    public void  admin_associerRoleUtilisateurBasique_RoleDejaAttribueException() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
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
    public void  admin_associerRoleUtilisateurModerateur_OK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
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
    public void admin_associerRoleUtilisateurModerateur_InformationManquanteException_RoleNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),null);
        Assert.fail();
    }
    @Test(expected =InformationManquanteException.class)
    public void admin_associerRoleUtilisateurModerateur_InformationManquanteException_RoleVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),"");
        Assert.fail();
    }
    @Test (expected = IndividuNonConnecteException.class)
    public void  admin_associerRoleUtilisateurModerateur_IndividuNonConnecte() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {

            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur33");
            connexionService.deconnexion(p.getIdentifiant());
            adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),MODERATEUR);
        } catch (IndividuNonConnecteException e1) {
            e1.printStackTrace();
        }
    }
    @Test (expected = RoleDejaAttribueException.class)
    public void  admin_associerRoleUtilisateurModerateur_RoleDejaAttribueException() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
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
    public void admin_associerRoleUtilisateurAdmin_OK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
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
    @Test(expected =InformationManquanteException.class)
    public void admin_associerRoleUtilisateurAdmin_InformationManquanteException_RoleNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur789");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),null);
        Assert.fail();
    }
    @Test(expected =InformationManquanteException.class)
    public void admin_associerRoleUtilisateurAdmin_InformationManquanteException_RoleVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur789");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),"");
        Assert.fail();
    }
    @Test (expected = IndividuNonConnecteException.class)
    public void  admin_associerRoleUtilisateurAdmin_IndividuNonConnecte() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur333");
            connexionService.deconnexion(p.getIdentifiant());
            adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),MODERATEUR);
        } catch (IndividuNonConnecteException e1) {
            e1.printStackTrace();
        }
    }
    @Test (expected = RoleDejaAttribueException.class)
    public void  admin_associerRoleUtilisateurAdmin_RoleDejaAttribueException() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
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
    public void  moderateur_associerRoleUtilisateurModerateur() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException{
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "moderateur", "moderateur123");
            adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p3.getNom(), p3.getMdp());
            Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "admin", "admin12345");
            adminService.associerRoleUtilisateur(p3.getIdentifiant(), p4.getIdentifiant(), MODERATEUR);
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e1) {
            Assert.fail();
        }
    }
    @Test(expected =InformationManquanteException.class)
    public void moderateur_associerRoleUtilisateurModerateur_InformationManquanteException_RoleNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "moderateur", "moderateur123");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p3.getNom(), p3.getMdp());
        Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "admin", "admin12345");
        adminService.associerRoleUtilisateur(p3.getIdentifiant(), p4.getIdentifiant(), null);
        Assert.assertTrue(true);
        Assert.fail();
    }
    @Test(expected =InformationManquanteException.class)
    public void moderateur_associerRoleUtilisateurModerateur_InformationManquanteException_RoleVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "moderateur", "moderateur123");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p3.getNom(), p3.getMdp());
        Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "admin", "admin12345");
        adminService.associerRoleUtilisateur(p3.getIdentifiant(), p4.getIdentifiant(), "");
        Assert.assertTrue(true);
        Assert.fail();
    }
    @Test (expected = IndividuNonConnecteException.class)
    public void  moderateur_associerRoleUtilisateurModerateur_IndividuNonConnecte() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "moderateur", "moderateur726");
            adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p3.getNom(), p3.getMdp());
            Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "admin", "admin12345");
            connexionService.deconnexion(p3.getIdentifiant());
            adminService.associerRoleUtilisateur(p3.getIdentifiant(), p4.getIdentifiant(), MODERATEUR);
        } catch (IndividuNonConnecteException e1) {
            e1.printStackTrace();
        }
    }
    @Test (expected = RoleDejaAttribueException.class)
    public void  moderateur_associerRoleUtilisateurModerateur_RoleDejaAttribueException() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p =  connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur555");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(), MODERATEUR);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p2.getNom(), p2.getMdp());
        Personne p3 = adminService.creerUtilisateur(p2.getIdentifiant(), "moderateur", "moderateur666");
        try {
            adminService.associerRoleUtilisateur(p2.getIdentifiant(), p3.getIdentifiant(),MODERATEUR);
        } catch (RoleDejaAttribueException e1) {
            e1.printStackTrace();
        }
        adminService.associerRoleUtilisateur(p2.getIdentifiant(), p3.getIdentifiant(),MODERATEUR);
    }
    /**
     * Quand un MODERATEUR veut associer BASIQUE à un utilisateur.
     */
    @Test
    public void  moderateur_associerRoleUtilisateurBasique() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException{
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "moderateur", "moderateur3213");
            adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p3.getNom(), p3.getMdp());
            Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "admin", "admin12345");
            adminService.associerRoleUtilisateur(p3.getIdentifiant(), p4.getIdentifiant(), BASIQUE);
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e1) {
            Assert.fail();
        }
    }
    @Test(expected =InformationManquanteException.class)
    public void moderateur_associerRoleUtilisateurBasique_InformationManquanteException_RoleNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "moderateur", "moderateur3213");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p3.getNom(), p3.getMdp());
        Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "admin", "admin123456");
        adminService.associerRoleUtilisateur(p3.getIdentifiant(), p4.getIdentifiant(), BASIQUE);
        Assert.fail();
    }
    @Test(expected =InformationManquanteException.class)
    public void moderateur_associerRoleUtilisateurBasique_InformationManquanteException_RoleVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "moderateur", "moderateur123");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p3.getNom(), p3.getMdp());
        Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "admin", "admin123456");
        adminService.associerRoleUtilisateur(p3.getIdentifiant(), p4.getIdentifiant(), "");
        Assert.fail();
    }
    @Test (expected = IndividuNonConnecteException.class)
    public void  moderateur_associerRoleUtilisateurBasique_IndividuNonConnecte() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "moderateur", "moderateur332");
            adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p3.getNom(), p3.getMdp());
            Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "admin", "admin12345");
            connexionService.deconnexion(p3.getIdentifiant());
            adminService.associerRoleUtilisateur(p3.getIdentifiant(), p4.getIdentifiant(), BASIQUE);
        } catch (IndividuNonConnecteException e1) {
            e1.printStackTrace();
        }
    }
    @Test (expected = RoleDejaAttribueException.class)
    public void  moderateur_associerRoleUtilisateurBasique_RoleDejaAttribueException() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p =  connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur123");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(), MODERATEUR);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p2.getNom(), p2.getMdp());
        Personne p3 = adminService.creerUtilisateur(p2.getIdentifiant(), "basique", "basique123");
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
    @Test
    public void  moderateur_associerRoleUtilisateur_Admin() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "moderateur", "moderateur123");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p3.getNom(), p3.getMdp());
        Personne p4 = adminService.creerUtilisateur(p3.getIdentifiant(), "admin", "admin12345");
        adminService.associerRoleUtilisateur(p3.getIdentifiant(),p4.getIdentifiant(), ADMIN);
        Assert.fail();
    }






    @Test
    public void getListeUtilisateurOK() throws IndividuNonConnecteException, CoupleUtilisateurMDPInconnuException {
        try{
            Personne p = connexionService.connexion("admin", "admin");
            adminService.getListeUtilisateur(p.getIdentifiant());
            Assert.assertTrue(true);
        }   catch (IndividuNonConnecteException e){
            Assert.fail();
        }
    }
    @Test (expected = IndividuNonConnecteException.class)
    public void getListeUtilisateurKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try{
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"a","a");
            connexionService.connexion("a","a");
            connexionService.deconnexion(p2.getIdentifiant());
            adminService.getListeUtilisateur(p2.getIdentifiant());
        }   catch (IndividuNonConnecteException e){
            e.printStackTrace();
        }
    }



    @Test
    public void getUserByIdTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
        adminService.getUserById(p.getIdentifiant(),p2.getIdentifiant());
        Assert.assertTrue(true);
    }



    @Test
    public void supprimerRoleUtilisateurTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","utilisateur2");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        adminService.supprimerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        Assert.assertTrue(true);
    }


    @Test
    public void changerMotDePasseUtilisateurTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","utilisateur2");
        adminService.changerMotDePasseUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),"123456");
        Assert.assertTrue(true);
    }
    @Test(expected = InformationManquanteException.class)
    public void changerMotDePasseKOMdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","utilisateur2");
        adminService.changerMotDePasseUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),null);
    }

    @Test
    public void supprimerUtilisateurTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
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
    public void validerInscriptionTestKORoleDejaAttribueException() throws RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur2", "utilisateur2");
        try {
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            adminService.validerInscription(p.getIdentifiant(), p2.getIdentifiant());
        } catch (RoleDejaAttribueException e) {
            e.printStackTrace();
        }
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
    }



    @Test
    public void getListeDesDemandesNonTraiteesADMIN () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"admin2","admin2");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
        adminService.getListeDesDemandesNonTraitees(p2.getIdentifiant());
        Assert.assertTrue(true);
    }
    @Test
    public void getListeDesDemandesNonTraiteesMODERATEUR () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        adminService.getListeDesDemandesNonTraitees(p2.getIdentifiant());
        Assert.assertTrue(true);
    }
    @Test
    public void getListeDesDemandesNonTraiteesBASIQUE () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        adminService.getListeDesDemandesNonTraitees(p2.getIdentifiant());
        Assert.fail();
    }





    @Test
    public void refuserInscriptionTestAdmin() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("admin","aaaaa",ADMIN);
        Personne p = connexionService.connexion("admin", "admin");
        adminService.refuserInscription(p.getIdentifiant(),posteDemande);
        adminService.getListeDesDemandesNonTraitees(p.getIdentifiant());
        for (InscriptionPotentielle i :adminService.getListeDesDemandesNonTraitees(p.getIdentifiant())) {
            if(i.getIdentifiant()==posteDemande){
                Assert.fail();
            }
        }
        Assert.assertTrue(true);


    }
    @Test
    public void refuserInscriptionTestModerateur() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("moderateur","bbbbb",MODERATEUR);
        Personne p = connexionService.connexion("admin", "admin");
        adminService.refuserInscription(p.getIdentifiant(),posteDemande);
        Assert.assertTrue(true);
    }
    @Test
    public void refuserInscriptionTestModerateur2() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("moderateur","bbbbb",MODERATEUR);
        Personne m = connexionService.connexion("moderateur", "moderateur");
        adminService.refuserInscription(m.getIdentifiant(),posteDemande);
        Assert.assertTrue(true);
    }
    @Test
    public void refuserInscriptionTestBasique() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("utiladmin","ccccc",BASIQUE);
        Personne p = connexionService.connexion("admin", "admin");
        adminService.refuserInscription(p.getIdentifiant(),posteDemande);
        Assert.assertTrue(true);
    }
    @Test
    public void refuserInscriptionTestBasique2() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("utiladmin","ccccc",BASIQUE);
        Personne p = connexionService.connexion("moderateur", "moderateur");
        adminService.refuserInscription(p.getIdentifiant(),posteDemande);
        Assert.assertTrue(true);
    }

    /**
     * Moderateur peut traiter : Utilisateur Basique ou MODERATEUR
     */



    /**
     *     -------------------------------------------------------   Test de l'interface Connexion Service   -------------------------------------------------------
     */


    @Test
    public void connexionTestOK() throws UtilisateurDejaExistantException, RoleDejaAttribueException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "123");
            adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
            connexionService.connexion(p2.getNom(), p2.getMdp());
            Assert.assertTrue(true);
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
    }
    @Test(expected = CoupleUtilisateurMDPInconnuException.class)
    public void connexionTestKOCoupleUtilisateurMDPInconnu() throws UtilisateurDejaExistantException, RoleDejaAttribueException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","utilisateur2");
            adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
            connexionService.connexion(p2.getNom(), "nana");
        } catch (CoupleUtilisateurMDPInconnuException e){
            e.printStackTrace();
        }
    }
    @Test (expected = InformationManquanteException.class)
    public void connexionKOPseudoNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "123");
        adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
        connexionService.connexion(null,p2.getMdp());
    }
    @Test (expected = InformationManquanteException.class)
    public void connexionKOMdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "123");
        adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
        connexionService.connexion(p2.getNom(),null);
    }


    @Test
    public void deconnexionTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur3","utilisateur3");
        adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
        connexionService.connexion(p2.getNom(),p2.getMdp());
        connexionService.deconnexion(p2.getIdentifiant());
        Assert.assertTrue(true);
    }

    @Test
    public void estUnUtilisateurConnuTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur4","utilisateur4");
        adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
        connexionService.connexion(p2.getNom(), p2.getMdp());
        connexionService.estUnUtilisateurConnu(p2.getNom());
        Assert.assertTrue(true);
    }



    @Test
    public void estUnAdminTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur5","utilisateur5");
        connexionService.connexion(p2.getNom(), p2.getMdp());
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
        connexionService.estUnAdmin(p2.getIdentifiant());
        Assert.assertTrue(true);
    }



    @Test
    public void estUnModerateurTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur6","utilisateur6");
        adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
        connexionService.connexion(p2.getNom(), p2.getMdp());
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.estUnModerateur(p2.getIdentifiant());
        Assert.assertTrue(true);
    }

    /**
     *     -------------------------------------------------------   Test de l'interface BasiquesOffLineService    -------------------------------------------------------
     */


    @Test
    public void posterDemandeInscriptionBasiqueTestOK() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur",BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKORoleNull() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur",null);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKORoleVide() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur","");
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKOPseudoNull() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription(null,"utilisateur",BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKOPseudoVide() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("","utilisateur",BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKOMdpNull() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("utilisateur",null,BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKOMdpVide() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("utilisateur","",BASIQUE);
    }




    @Test
    public void posterDemandeInscriptionModerateurTestOK() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("moderateur","moderateur",MODERATEUR);
        Assert.assertTrue(true);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKORoleNull() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("moderateur","moderateur",null);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKORoleVide() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("moderateur","moderateur","");
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKOPseudoNull() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription(null,"moderateur",MODERATEUR);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKOPseudoVide() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("","moderateur",MODERATEUR);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKOMdpNull() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("moderateur",null,MODERATEUR);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKOMdpVide() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("moderateur","",MODERATEUR);
    }



    @Test
    public void posterDemandeInscriptionAdminTestOK() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("admin","admin",ADMIN);
        Assert.assertTrue(true);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKORoleNull() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("admin","admin",null);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKORoleVide() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("admin","admin","");
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKOPseudoNull() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription(null,"admin",null);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKOPseudoVide() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("","admin","");
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKOMdpoNull() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("admin",null,null);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKOMdpoVide() throws UtilisateurDejaExistantException {
        basiquesOffLineService.posterDemandeInscription("admin","",null);
    }

    /**
     *     -------------------------------------------------------   Test de l'interface Personne    -------------------------------------------------------
     */


    @Test
    public void supprimerRoleModerateur() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
        connexionService.connexion(p2.getNom(),p2.getMdp());
        p2.getRoles(); /** Récupere la liste des Roles de l'utilisateur **/
        p2.hasRole(MODERATEUR);
        p2.supprimerRole(MODERATEUR); /** Supprime le role MODERATEUR **/
    }
    @Test
    public void supprimerRoleBasique() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
        connexionService.connexion(p2.getNom(),p2.getMdp());
        p2.getRoles();
        p2.hasRole(BASIQUE);
        p2.supprimerRole(BASIQUE);
        Assert.assertTrue(true);
    }
    @Test
    public void supprimerRoleAdmin() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"admin2","admin2");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
        adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
        connexionService.connexion(p2.getNom(),p2.getMdp());
        p2.getRoles();
        p2.hasRole(ADMIN);
        personne.supprimerRole(ADMIN);
        Assert.assertTrue(true);
    }




    @Test
    public void addRoleModerateurOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur234");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p2.getNom(),p2.getMdp());

            /**  p2.getRoles();
             if (p.hasRole(MODERATEUR) == false) **/

            p2.addRole(MODERATEUR);
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            e.printStackTrace();
        }
    }
    @Test(expected = RoleDejaAttribueException.class)
    public void addRoleModerateurKORoleDejaAttribue() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur234");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
            adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p2.getNom(),p2.getMdp());

            p2.getRoles();
            p2.hasRole(MODERATEUR);
            p2.addRole(MODERATEUR);
        } catch (RoleDejaAttribueException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void addRoleBasiqueOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur132");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
            adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p2.getNom(),p2.getMdp());
            p2.addRole(BASIQUE);
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        }
    }@Test (expected = RoleDejaAttribueException.class)
    public void addRoleBasiqueKORoleDejaAttribue() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur345");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p2.getNom(),p2.getMdp());

            p2.getRoles();
            p2.hasRole(BASIQUE);
            p2.addRole(BASIQUE);
        } catch (RoleDejaAttribueException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void addRoleAdminOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique789");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
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
    public void addRoleAdminKORoleDejaAttribueException() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"admin","admin1234");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
            adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p2.getNom(),p2.getMdp());

            p2.getRoles();
            p2.hasRole(ADMIN);
            p2.addRole(ADMIN);
        } catch (RoleDejaAttribueException e) {
            e.printStackTrace();
        }
    }

    /********************************     Tests de l'Interface Topic ********************************/

    @Test
    public void ajouterMessage_Test_OK() {
        Message message = EasyMock.createMock(Message.class);
        Topic topic = EasyMock.createMock(Topic.class);
        topic.ajouterMessage(message);
        EasyMock.expect(topic.ajouterMessage(message)).andReturn(true);
        EasyMock.replay(message,topic);

        boolean res = topic.ajouterMessage(message);

        Assert.assertTrue(res);
    }

    /**
     * Ajouter une exception à la méthode ajouterMessage() dans l'interface Topic.java
     */
    @Test
    public void ajouterMessage_Test_KO(){

    }





}



