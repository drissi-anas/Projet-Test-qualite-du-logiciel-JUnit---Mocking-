package testInterfaces;

import facade.*;
import facade.erreurs.*;
import modele.forum.Message;
import modele.forum.Theme;
import modele.forum.Topic;
import modele.inscription.InscriptionPotentielle;
import modele.personnes.Personne;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Collection;

import static facade.AdminService.*;

public abstract class  TestFacadeAll {

    AdminService adminService;
    BasiquesOffLineService basiquesOffLineService;
    ConnexionService connexionService;
    ForumService forumService;
    FabriqueFacade fabriqueFacade;



    public TestFacadeAll(FabriqueFacade fabriqueFacade) {
        this.fabriqueFacade = fabriqueFacade;

    }

    @Before
    public void initialisationModele() throws RoleDejaAttribueException {
        fabriqueFacade.majListes();


        connexionService = fabriqueFacade.getConnexionService();
        basiquesOffLineService = fabriqueFacade.getBasiquesOffLineService(connexionService);
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
            Personne p2 =adminService.creerUtilisateur( p.getIdentifiant(), "aaa", "bbb");
            Assert.assertTrue(connexionService.getListeUtilisateurs().contains(p2));
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
        adminService.creerUtilisateur(p.getIdentifiant(),"aaa","bbb");
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

    @Test(expected = ActionImpossibleException.class)
    public void creerUtilisateur_Moderateur_KO() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException {

        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","123456789");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(), MODERATEUR);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p2.getNom(),p2.getMdp());
        Personne p3 = adminService.creerUtilisateur(p2.getIdentifiant(),"utilisateur2","utilis123");

    }

    @Test(expected = ActionImpossibleException.class)
    public void creerUtilisateur_Basique_KO() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException {

        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","123456789");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(), BASIQUE);
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
            Assert.assertTrue(p2.getRoles().contains(BASIQUE));
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e1) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
    }
    @Test(expected =InformationManquanteException.class)
    public void admin_associerRoleUtilisateurBasique_InformationManquanteException_RoleNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur123");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),null);
    }
    @Test(expected =InformationManquanteException.class)
    public void admin_associerRoleUtilisateurBasique_InformationManquanteException_RoleVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur123");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),"");
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
            Assert.fail();
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
            Assert.assertTrue(p2.getRoles().contains(MODERATEUR));
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
    }

    @Test(expected =InformationManquanteException.class)
    public void admin_associerRoleUtilisateurModerateur_InformationManquanteException_RoleVide() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),"");
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
            Assert.fail();
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
            Assert.assertTrue(p2.getRoles().contains(ADMIN));
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



    @Test(expected = ActionImpossibleException.class)
    public void  noAdmin_associerRoleUtilisateurKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "moderateur123");
        Personne p4 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur2", "12345");
        adminService.associerRoleUtilisateur(p.getIdentifiant(), p3.getIdentifiant(), MODERATEUR);
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p3.getNom(), p3.getMdp());
        adminService.associerRoleUtilisateur(p3.getIdentifiant(),p4.getIdentifiant(), BASIQUE);
    }

    @Test
    public void getListeUtilisateurOK() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException {
        try{
            Personne p = connexionService.connexion("admin", "admin");
            Collection<Personne> listeResultat =adminService.getListeUtilisateur(p.getIdentifiant());
            Assert.assertEquals(listeResultat,connexionService.getListeUtilisateurs());
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
    public void getUserByIdTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
        try {
            Personne  resultat = adminService.getUserById(p.getIdentifiant(),p2.getIdentifiant());
            Assert.assertEquals(resultat,p2);
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        }

    }

    @Test(expected = IndividuNonConnecteException.class)
    public void getUserByIdTestKOUTilisateurNonco() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
        adminService.getUserById(p2.getIdentifiant(),p2.getIdentifiant());

    }



    @Test
    public void supprimerRoleUtilisateurTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException {
        try{
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","utilisateur2");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            adminService.supprimerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            Assert.assertTrue(!p2.getRoles().contains(BASIQUE));
        } catch (IndividuNonConnecteException e){
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
    }



    @Test
    public void changerMotDePasseUtilisateurTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, ActionImpossibleException {
        try{
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur2");
            adminService.changerMotDePasseUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),"123456");
            Assert.assertEquals(p2.getMdp(),"123456");
        }catch(IndividuNonConnecteException e){
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
    }

    @Test
    public void changerMotDePasseUtilisateurModerateurOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, ActionImpossibleException, RoleDejaAttribueException {
        try{
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur2");
            Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(),"modo","modo");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p3.getIdentifiant(),MODERATEUR);
            connexionService.connexion("modo","modo");
            adminService.changerMotDePasseUtilisateur(p3.getIdentifiant(),p2.getIdentifiant(),"123456");
            Assert.assertEquals(p2.getMdp(),"123456");
        }catch(IndividuNonConnecteException e){
            Assert.fail();
        } catch(InformationManquanteException e) {
            Assert.fail();
        }
    }

    @Test(expected = ActionImpossibleException.class)
    public void changerMotDePasseUtilisateur_BasiqueKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, ActionImpossibleException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur2");
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(),"modo","modo");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p3.getIdentifiant(),BASIQUE);
        connexionService.connexion("modo","modo");
        adminService.changerMotDePasseUtilisateur(p3.getIdentifiant(),p2.getIdentifiant(),"123456");
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
    public void supprimerUtilisateurTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur", "utilisateur2");
        try {
            adminService.supprimerUtilisateur(p.getIdentifiant(),p2.getIdentifiant());
            Assert.assertTrue(!connexionService.getListeUtilisateurs().contains(p2));

        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        }
    }

    @Test (expected = IndividuNonConnecteException.class)
    public void supprimerUtilisateur_nonConnecte() throws IndividuNonConnecteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"ilyas","ilyas");
        connexionService.deconnexion(p.getIdentifiant());
        adminService.supprimerUtilisateur(p.getIdentifiant(),p2.getIdentifiant());
    }



    @Test(expected = ActionImpossibleException.class)
    public void supprimerUtilisateurKO_NOAdmin() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException, RoleDejaAttribueException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur", "utilisateur");
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2", "utilisateur2");
        connexionService.connexion("utilisateur2", "utilisateur2");
        adminService.supprimerUtilisateur(p3.getIdentifiant(),p2.getIdentifiant());

    }


    @Test
    public void admin_validerInscriptionTestOK_Admin() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, UtilisateurDejaExistantException{
        Personne p = connexionService.connexion("admin", "admin");
        long demande = basiquesOffLineService.posterDemandeInscription("anas","admin123",ADMIN);
        try {
            adminService.validerInscription(p.getIdentifiant(), demande);
            Personne newInscris = null;
            for(Personne pers : connexionService.getListeUtilisateurs()){
                if(pers.getNom().equals("anas")){
                    newInscris=pers;
                }
            }
            Assert.assertTrue(newInscris!=null&&newInscris.getRoles().contains(ADMIN));
        } catch (ActionImpossibleException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        }

    }
    @Test
    public void admin_validerInscriptionTestOK_Basique() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, UtilisateurDejaExistantException{
        Personne p = connexionService.connexion("admin", "admin");
        long demande = basiquesOffLineService.posterDemandeInscription("anas","123",BASIQUE);

        try {
            adminService.validerInscription(p.getIdentifiant(), demande);
            Personne newInscris = null;
            for(Personne pers : connexionService.getListeUtilisateurs()){
                if(pers.getNom().equals("anas")){
                    newInscris=pers;
                }
            }
            Assert.assertTrue(newInscris!=null&&newInscris.getRoles().contains(BASIQUE));
        } catch (ActionImpossibleException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        }
    }



    @Test
    public void admin_validerInscriptionTestOK_Moderateur() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, UtilisateurDejaExistantException{
        Personne p = connexionService.connexion("admin", "admin");
        long demande = basiquesOffLineService.posterDemandeInscription("anas","13579",MODERATEUR);

        try {
            adminService.validerInscription(p.getIdentifiant(), demande);
            Personne newInscris = null;
            for(Personne pers : connexionService.getListeUtilisateurs()){
                if(pers.getNom().equals("anas")){
                    newInscris=pers;
                }
            }
            Assert.assertTrue(newInscris!=null&&newInscris.getRoles().contains(MODERATEUR));
        } catch (ActionImpossibleException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        }


    }

    @Test
    public void moderateur_validerInscriptionTestOK_Moderateur() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, UtilisateurDejaExistantException, IndividuNonConnecteException, RoleDejaAttribueException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 =adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("moderateur","moderateur");
        long demande = basiquesOffLineService.posterDemandeInscription("anas","13579",MODERATEUR);

        try {
            adminService.validerInscription(p2.getIdentifiant(), demande);

        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Personne newInscris = null;
            for(Personne pers : connexionService.getListeUtilisateurs()){
                if(pers.getNom().equals("anas")){
                    newInscris=pers;
                }
            }
            Assert.assertTrue(newInscris!=null&&newInscris.getRoles().contains(MODERATEUR));



    }

    @Test
    public void moderateur_validerInscriptionTestOK_Basique() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, UtilisateurDejaExistantException, ActionImpossibleException, IndividuNonConnecteException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 =adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("moderateur","moderateur");
        long demande = basiquesOffLineService.posterDemandeInscription("anas","13579",BASIQUE);
        try {
            adminService.validerInscription(p2.getIdentifiant(), demande);
            Personne newInscris = null;
            for(Personne pers : connexionService.getListeUtilisateurs()){
                if(pers.getNom().equals("anas")){
                    newInscris=pers;
                }
            }
            Assert.assertTrue(newInscris!=null&&newInscris.getRoles().contains(BASIQUE));
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }


    }

    @Test (expected = IndividuNonConnecteException.class)
    public void valider_inscription_nonConnecte() throws IndividuNonConnecteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin", "admin");
        long posteDemande = basiquesOffLineService.posterDemandeInscription("ilyas","ilyas",ADMIN);
        connexionService.deconnexion(p.getIdentifiant());
        adminService.validerInscription(p.getIdentifiant(),posteDemande);
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
    public void getListeDesDemandesNonTraiteesADMINOK () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException {
        basiquesOffLineService.posterDemandeInscription("util", "ccccc", BASIQUE);
        basiquesOffLineService.posterDemandeInscription("util2", "ccccc", MODERATEUR);
        basiquesOffLineService.posterDemandeInscription("util2", "ccccc", ADMIN);
        Personne p = connexionService.connexion("admin", "admin");
        try {
            Collection<InscriptionPotentielle> resultat = adminService.getListeDesDemandesNonTraitees(p.getIdentifiant());
            Assert.assertEquals(resultat, basiquesOffLineService.getListeDemandes());
        } catch (ActionImpossibleException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        }
    }

    @Test
    public void getListeDesDemandesNonTraiteesMODERATEUROK () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        basiquesOffLineService.posterDemandeInscription("util","ccccc",BASIQUE);
        basiquesOffLineService.posterDemandeInscription("util2","ccccc",MODERATEUR);
        basiquesOffLineService.posterDemandeInscription("util2","ccccc",ADMIN);
        Personne p =connexionService.connexion("admin","admin");
        Personne p2 =adminService.creerUtilisateur(p.getIdentifiant(),"modo","modo");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("modo","modo");
        try {
            Collection <InscriptionPotentielle> resultat = adminService.getListeDesDemandesNonTraitees(p2.getIdentifiant());
            boolean resultatContientAdmin =false;
            for (InscriptionPotentielle i:resultat) {
                if(i.getRoleDemande().equals(ADMIN)){
                    resultatContientAdmin=true;
                }
            }

            Assert.assertTrue(!resultatContientAdmin);
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }

    }
    @Test (expected = ActionImpossibleException.class)
    public void getListeDesDemandesNonTraiteesBASIQUE () throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, ActionImpossibleException, UtilisateurDejaExistantException, IndividuNonConnecteException {
        Personne p =connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"ilyas","ilyas");
        connexionService.connexion("ilyas","ilyas");
        adminService.getListeDesDemandesNonTraitees(p2.getIdentifiant());

    }
    @Test (expected = IndividuNonConnecteException.class)
    public void getListeDemandeNontraites__nonConnecte() throws IndividuNonConnecteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        basiquesOffLineService.posterDemandeInscription("util", "ccccc", BASIQUE);
        basiquesOffLineService.posterDemandeInscription("util2", "ccccc", MODERATEUR);
        Personne p = connexionService.connexion("admin", "admin");
        connexionService.deconnexion(p.getIdentifiant());
        Collection<InscriptionPotentielle> resultat = adminService.getListeDesDemandesNonTraitees(p.getIdentifiant());
        Assert.assertEquals(resultat, basiquesOffLineService.getListeDemandes());

    }

    @Test
    public void refuserInscriptionTestAdminRefuseBasiqueOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util2","ccccc",BASIQUE);
        InscriptionPotentielle newDemande =null;
        for (InscriptionPotentielle i:basiquesOffLineService.getListeDemandes()) {
            if(i.getIdentifiant()==posteDemande){
                newDemande=i;
            }
        }

        Personne p = connexionService.connexion("admin", "admin");
        Collection<String> listePseudoUtilisateursInscris = new ArrayList<>();
        for(Personne pers : connexionService.getListeUtilisateurs()){
            listePseudoUtilisateursInscris.add(pers.getNom());
        }



        try {
            adminService.refuserInscription(p.getIdentifiant(),posteDemande);
        } catch (ActionImpossibleException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        }
        Assert.assertTrue(!basiquesOffLineService.getListeDemandes().contains(newDemande)&&!listePseudoUtilisateursInscris.contains("util2"));
    }

    @Test
    public void refuserInscriptionTestAdminRefuseModerateurOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util2","ccccc",MODERATEUR);
        InscriptionPotentielle newDemande =null;
        for (InscriptionPotentielle i:basiquesOffLineService.getListeDemandes()) {
            if(i.getIdentifiant()==posteDemande){
                newDemande=i;
            }
        }

        Personne p = connexionService.connexion("admin", "admin");
        Collection<String> listePseudoUtilisateursInscris = new ArrayList<>();
        for(Personne pers : connexionService.getListeUtilisateurs()){
            listePseudoUtilisateursInscris.add(pers.getNom());
        }
        try {
            adminService.refuserInscription(p.getIdentifiant(),posteDemande);
        } catch (ActionImpossibleException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        }
        Assert.assertTrue(!basiquesOffLineService.getListeDemandes().contains(newDemande)&&!listePseudoUtilisateursInscris.contains("util2"));
    }

    @Test
    public void refuserInscriptionTestAdminRefuseAdminOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util2","ccccc",ADMIN);
        InscriptionPotentielle newDemande =null;
        for (InscriptionPotentielle i:basiquesOffLineService.getListeDemandes()) {
            if(i.getIdentifiant()==posteDemande){
                newDemande=i;
            }
        }

        Personne p = connexionService.connexion("admin", "admin");
        Collection<String> listePseudoUtilisateursInscris = new ArrayList<>();
        for(Personne pers : connexionService.getListeUtilisateurs()){
            listePseudoUtilisateursInscris.add(pers.getNom());
        }
        try {
            adminService.refuserInscription(p.getIdentifiant(),posteDemande);
        } catch (ActionImpossibleException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        }
        Assert.assertTrue(!basiquesOffLineService.getListeDemandes().contains(newDemande)&&!listePseudoUtilisateursInscris.contains("util2"));

    }

    @Test
    public void refuserInscriptionModerateurRefuseBasiqueOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, RoleDejaAttribueException, ActionImpossibleException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util1","ccccc",BASIQUE);
        InscriptionPotentielle newDemande =null;
        for (InscriptionPotentielle i:basiquesOffLineService.getListeDemandes()) {
            if(i.getIdentifiant()==posteDemande){
                newDemande=i;
            }
        }
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("moderateur","moderateur");
        Collection<String> listePseudoUtilisateursInscris = new ArrayList<>();
        for(Personne pers : connexionService.getListeUtilisateurs()){
            listePseudoUtilisateursInscris.add(pers.getNom());
        }
        try {
            adminService.refuserInscription(p2.getIdentifiant(),posteDemande);
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Assert.assertTrue(!basiquesOffLineService.getListeDemandes().contains(newDemande)&&!listePseudoUtilisateursInscris.contains("util2"));

    }

    @Test
    public void refuserInscriptionModerateurRefuseModerateurOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException, RoleDejaAttribueException, ActionImpossibleException {
        long posteDemande = basiquesOffLineService.posterDemandeInscription("util1","ccccc",MODERATEUR);
        InscriptionPotentielle newDemande =null;
        for (InscriptionPotentielle i:basiquesOffLineService.getListeDemandes()) {
            if(i.getIdentifiant()==posteDemande){
                newDemande=i;
            }
        }
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");

        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);

        connexionService.connexion("moderateur","moderateur");
        Collection<String> listePseudoUtilisateursInscris = new ArrayList<>();
        for(Personne pers : connexionService.getListeUtilisateurs()){
            listePseudoUtilisateursInscris.add(pers.getNom());
        }
        try {
            adminService.refuserInscription(p2.getIdentifiant(),posteDemande);
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Assert.assertTrue(!basiquesOffLineService.getListeDemandes().contains(newDemande)&&!listePseudoUtilisateursInscris.contains("util2"));

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

    @Test (expected = IndividuNonConnecteException.class)
    public void refuser_inscription_nonConnecte() throws IndividuNonConnecteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin", "admin");
        long posteDemande = basiquesOffLineService.posterDemandeInscription("ilyas","ilyas",ADMIN);
        connexionService.deconnexion(p.getIdentifiant());
        adminService.refuserInscription(p.getIdentifiant(),posteDemande);
    }




    /**
     * Moderateur peut traiter : Utilisateur Basique ou MODERATEUR
     */



    /**
     *     -------------------------------------------------------   Test de l'interface Connexion Service   -------------------------------------------------------
     */


    @Test
    public void connexionTestOK() throws UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "123");
            connexionService.connexion(p2.getNom(), p2.getMdp());
            Assert.assertTrue(connexionService.getPersonnesConnectes().contains(p2));
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
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
    public void deconnexionTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur3","utilisateur3");
        connexionService.connexion(p2.getNom(),p2.getMdp());
        connexionService.deconnexion(p2.getIdentifiant());
        Assert.assertTrue(!connexionService.getPersonnesConnectes().contains(p2));
    }

    @Test
    public void estUnUtilisateurConnuTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur4","utilisateur4");
        connexionService.connexion(p2.getNom(), p2.getMdp());
        boolean resultat = connexionService.estUnUtilisateurConnu(p2.getNom());
        Assert.assertTrue(resultat);
    }



    @Test
    public void estUnAdminTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur5","utilisateur5");
        connexionService.connexion(p2.getNom(), p2.getMdp());
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
        boolean resultat=connexionService.estUnAdmin(p.getIdentifiant());
        Assert.assertTrue(resultat);
    }



    @Test
    public void estUnModerateurTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur6","utilisateur6");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion(p2.getNom(), p2.getMdp());
        boolean resultat = connexionService.estUnModerateur(p2.getIdentifiant());
        Assert.assertTrue(resultat);
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
            long iddemande = basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur123",BASIQUE);
            Collection <Long> listeIdDesDemandes = new ArrayList<>();
            for(InscriptionPotentielle i:basiquesOffLineService.getListeDemandes()) {
                listeIdDesDemandes.add(i.getIdentifiant());
            }
            Assert.assertTrue(listeIdDesDemandes.contains(iddemande));
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
    public void posterDemandeInscriptionAdminTest_OK() {
        try {
            long iddemande = basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur123",ADMIN);
            Collection <Long> listeIdDesDemandes = new ArrayList<>();
            for (InscriptionPotentielle i:basiquesOffLineService.getListeDemandes()) {
                listeIdDesDemandes.add(i.getIdentifiant());
            }
            Assert.assertTrue(listeIdDesDemandes.contains(iddemande));
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
            long iddemande = basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur123",MODERATEUR);
            Collection <Long> listeIdDesDemandes = new ArrayList<>();
            for (InscriptionPotentielle i:basiquesOffLineService.getListeDemandes()) {
                listeIdDesDemandes.add(i.getIdentifiant());
            }
            Assert.assertTrue(listeIdDesDemandes.contains(iddemande));
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
    }


    @Test(expected = UtilisateurDejaExistantException.class)
    public void posterDemandeInscriptionKO() throws UtilisateurDejaExistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, ActionImpossibleException, IndividuNonConnecteException {

        Personne p =connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","mdp");
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


    @Test
    public void supprimerRole_AdminSupprimeModerateurOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","123456");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        try {
            adminService.supprimerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
            Assert.assertTrue(!p2.getRoles().contains(MODERATEUR));
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
    }

    @Test
    public void supprimerRole_AdminSupprimeBasiqueOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","123456");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        try {
            adminService.supprimerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            Assert.assertTrue(!p2.getRoles().contains(BASIQUE));
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
    }

    @Test(expected = ActionImpossibleException.class)
    public void supprimerRole_AdminSupprimeAdminKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","123456");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
        try {
            adminService.supprimerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);

        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        }
    }

    @Test
    public void supprimerRoleModerateurSupprimeBasiqueOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","123456");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","123456");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p3.getIdentifiant(),MODERATEUR);
         connexionService.connexion(p3.getNom(),p3.getMdp());
        try {
            adminService.supprimerRoleUtilisateur(p3.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            Assert.assertTrue(!p2.getRoles().contains(BASIQUE));
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
    }

    @Test(expected = ActionImpossibleException.class)
    public void supprimerRole_ModerateurSupprimeAdminKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","123456");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","123456");
        connexionService.connexion("utilisateur2","123456");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
            adminService.supprimerRoleUtilisateur(p3.getIdentifiant(),p.getIdentifiant(),ADMIN);
    }

    @Test(expected = ActionImpossibleException.class)
    public void supprimerRole_ModerateurSupprimeModerateurKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","123456");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","123456");
        connexionService.connexion("utilisateur2","123456");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p3.getIdentifiant(),MODERATEUR);
            adminService.supprimerRoleUtilisateur(p3.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
    }


    @Test(expected = ActionImpossibleException.class)
    public void supprimerRole_BasiqueSupprime_BASIQUEKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","123456");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","123456");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p3.getIdentifiant(),BASIQUE);
        connexionService.connexion("utilisateur","123456");
            adminService.supprimerRoleUtilisateur(p2.getIdentifiant(),p3.getIdentifiant(),BASIQUE);
    }

    @Test(expected = ActionImpossibleException.class)
    public void supprimerRole_BasiqueSupprime_MooderateurKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","123456");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","123456");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p3.getIdentifiant(),MODERATEUR);
        connexionService.connexion("utilisateur","123456");
            adminService.supprimerRoleUtilisateur(p2.getIdentifiant(),p3.getIdentifiant(),MODERATEUR);
    }

    @Test(expected = ActionImpossibleException.class)
    public void supprimerRole_BasiqueSupprime_AdminKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, ActionImpossibleException, IndividuNonConnecteException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","123456");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        Personne p3 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","123456");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p3.getIdentifiant(),ADMIN);
        connexionService.connexion("utilisateur","123456");
            adminService.supprimerRoleUtilisateur(p2.getIdentifiant(),p3.getIdentifiant(),ADMIN);
    }


    /********************************     Tests de l'Interface ForumService ********************************/

    @Test
    public void test_OK_getListeTopicPourUnTheme() throws InformationManquanteException, NomTopicDejaExistantException {
        try {
            Theme theme = forumService.creerThemeBis("Sante");
            Topic allergie= forumService.creerEtAjouterTopicATheme("allergie",theme,"anas");
            Topic rhume= forumService.creerEtAjouterTopicATheme("rhume",theme,"ilyas");
            Collection <Topic>resultat =forumService.getListeTopicPourUnTheme(theme);
            Assert.assertEquals(resultat,theme.getListeTopics());
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
            Theme resultat =forumService.recupererTheme("Sante");
            Assert.assertEquals(resultat,theme);
        }catch (ThemeInexistantException e){
            Assert.fail();
        }

    }

    @Test(expected = ThemeInexistantException.class)
    public void test_KO_recupererTheme() throws ThemeInexistantException {
        forumService.recupererTheme("Foot");
    }


    @Test
    public void test_OK_getListeMessagePourUnTopic() throws  InformationManquanteException, NomTopicDejaExistantException {
        try {
            Theme theme = forumService.creerThemeBis("Sante");
            Topic topic = forumService.creerEtAjouterTopicATheme("Allergie",theme,"Anas");
            Message message =forumService.creerMessage("Anas",topic,"je suis allergique mais je ne sais pas a quoi");
            forumService.ajouterMessage(topic,theme,message);
            Collection <Message> resultat = forumService.getListeMessagePourUnTopic(topic);
            Assert.assertEquals(resultat,topic.getListeMessages());
        } catch (TopicInexistantException e) {
            Assert.fail();

        }


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
            Topic topic = forumService.creerEtAjouterTopicATheme("Allergie",theme,"Ilyas");
            Topic resultat = forumService.recupererTopic("Allergie", "Sante");
            Assert.assertEquals(resultat,topic);
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
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p.getNom(),allergie,"nouveau message");
        forumService.ajouterMessage(allergie,sante,message);
        Assert.assertTrue(allergie.getListeMessages().contains(message));
    }
    @Test (expected = InformationManquanteException.class)
    public void ajouterMessage_test_KO_MessageNull() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p.getNom(),allergie,null);
        forumService.ajouterMessage(allergie,sante,message);
    }

    @Test (expected = InformationManquanteException.class)
    public void ajouterMessage_test_KO_MessageVide() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p.getNom(),allergie,"");
        forumService.ajouterMessage(allergie,sante,message);
    }


    @Test
    public void ajouterMessage_test_OK_Moderateur() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("moderateur","moderateur");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p2.getNom(),allergie,"nouveau message");
        forumService.ajouterMessage(allergie,sante,message);
        Assert.assertTrue(allergie.getListeMessages().contains(message));
    }

    @Test (expected = InformationManquanteException.class)
    public void ajouterMessage_test_KO_Moderateur_MessageNull() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, UtilisateurDejaExistantException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("moderateur","moderateur");


        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
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
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p2.getNom(),allergie,"");
        forumService.ajouterMessage(allergie,sante,message);
    }
/////

    @Test
    public void ajouterMessage_test_OK_Basique() throws  InformationManquanteException, CoupleUtilisateurMDPInconnuException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, UtilisateurDejaExistantException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");


        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");

        Message message = forumService.creerMessage(p2.getNom(),allergie,"nouveau message");
        forumService.ajouterMessage(allergie,sante,message);
        Assert.assertTrue(allergie.getListeMessages().contains(message));


    }
    @Test (expected = InformationManquanteException.class)
    public void ajouterMessage_test_KO_Basique_MessageNull() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
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
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p2.getNom(),allergie,"");
        forumService.ajouterMessage(allergie,sante,message);
    }



    @Test
    public void creerMessage_test_OK_Admin() throws CoupleUtilisateurMDPInconnuException, NomTopicDejaExistantException, InformationManquanteException {

        Personne p = connexionService.connexion("admin","admin");
        Theme  sante = forumService.creerThemeBis("Sante");
        Topic  allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        try {
            Message resultat =forumService.creerMessage(p.getNom(),allergie,"new message");
            Assert.assertTrue(resultat.getText().equals("new message"));
        } catch (InformationManquanteException e) {
            Assert.fail();
        }

    }
    @Test (expected = InformationManquanteException.class)
    public void creerMessage_test_KO_Admin_MessageVide() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        forumService.creerMessage(p.getNom(),allergie,"");
    }
    @Test (expected = InformationManquanteException.class)
    public void creerMessage_test_KO_Admin_MessageNull() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        forumService.creerMessage(p.getNom(),allergie,null);
    }


    @Test
    public void creerMessage_test_OK_Moderateur() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("moderateur","moderateur");

        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message resultat = forumService.creerMessage(p2.getNom(),allergie,"new message");
        Assert.assertTrue(resultat.getText().equals("new message"));
    }

    @Test (expected = InformationManquanteException.class)
    public void creerMessage_test_KO_Moderateur_MessageVide() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("moderateur","moderateur");

        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        forumService.creerMessage(p2.getNom(),allergie,"");
    }


    @Test (expected = InformationManquanteException.class)
    public void creerMessage_test_KO_Moderateur_MessageNull() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("moderateur","moderateur");


        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        forumService.creerMessage(p2.getNom(),allergie,null);
    }


    @Test
    public void creerMessage_test_OK_Basique() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException, InformationManquanteException {
        Personne p= connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        try {
            Message resultat = forumService.creerMessage(p2.getNom(),allergie,"new message");
            Assert.assertTrue(resultat.getText().equals("new message"));
        } catch (InformationManquanteException e) {
            Assert.fail();
        }

    }

    @Test (expected = InformationManquanteException.class)
    public void creerMessage_test_KO_Basique_MessageVide() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        forumService.creerMessage(p2.getNom(),allergie,"");
    }


    @Test (expected = InformationManquanteException.class)
    public void creerMessage_test_KO_Basique_MessageNull() throws ThemeInexistantException, TopicInexistantException, InformationManquanteException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, IndividuNonConnecteException, ActionImpossibleException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");

        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");

        forumService.creerMessage(p2.getNom(),allergie,null);
    }


    @Test
    public void creerThemeBis_OK()  {
        try {
            Theme resultat =forumService.creerThemeBis("Sport");
            Assert.assertTrue(forumService.getListeTheme().contains(resultat));
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
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
    public void creerTopic_OK() throws NomTopicDejaExistantException, InformationManquanteException {
        Theme sante = forumService.creerThemeBis("Sante");
        try {
            Topic resultat = forumService.creerEtAjouterTopicATheme("Cancer",sante,"Ilyas");
            Assert.assertTrue(sante.getListeTopics().contains(resultat));
        } catch (InformationManquanteException e) {
            Assert.fail();
        }
    }

    @Test (expected = InformationManquanteException.class)
    public void creerTopic_K0_NomTopicNull() throws  NomTopicDejaExistantException, InformationManquanteException {
        Theme sante = forumService.creerThemeBis("Sante");
        forumService.creerEtAjouterTopicATheme(null,sante,"Ilyas");
    }
    @Test (expected = InformationManquanteException.class)
    public void creerTopic_K0_NomTopicVide() throws  NomTopicDejaExistantException, InformationManquanteException {

        Theme sante = forumService.creerThemeBis("Sante");
        forumService.creerEtAjouterTopicATheme("",sante,"Ilyas");
    }


    @Test ()
    public void supprimerMessage_ADMIN_supprimeMessageDeModerateurOK() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, IndividuNonConnecteException, UtilisateurDejaExistantException, RoleDejaAttribueException, NomTopicDejaExistantException, ActionImpossibleException {

        Personne p= connexionService.connexion("admin","admin");
        Personne p2=adminService.creerUtilisateur(p.getIdentifiant(),"modo","modo");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("modo","modo");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p2.getNom(),allergie,"new message");

        forumService.ajouterMessage(allergie,sante,message);
        try {
            forumService.supprimerMessage(message,p.getIdentifiant());
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Assert.assertTrue(!allergie.getListeMessages().contains(message));


    }

    @Test ()
    public void supprimerMessage_ADMIN_supprimeMessageDeBasiqueOK() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, IndividuNonConnecteException, UtilisateurDejaExistantException, RoleDejaAttribueException, NomTopicDejaExistantException, ActionImpossibleException {

        Personne p= connexionService.connexion("admin","admin");
        Personne p2=adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");

        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p2.getNom(),allergie,"new message");

        forumService.ajouterMessage(allergie,sante,message);
        try {
            forumService.supprimerMessage(message,p.getIdentifiant());
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Assert.assertTrue(!allergie.getListeMessages().contains(message));


    }

    @Test ()
    public void supprimerMessage_ADMIN_supprimeMessageDeADMINOK() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, IndividuNonConnecteException, ActionImpossibleException, UtilisateurDejaExistantException, RoleDejaAttribueException, NomTopicDejaExistantException {

        Personne p= connexionService.connexion("admin","admin");
        Personne p2=adminService.creerUtilisateur(p.getIdentifiant(),"admin2","admin2");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
        connexionService.connexion("admin2","admin2");

        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p2.getNom(),allergie,"new message");

        forumService.ajouterMessage(allergie,sante,message);
        forumService.supprimerMessage(message,p.getIdentifiant());
        Assert.assertTrue(!allergie.getListeMessages().contains(message));


    }


    @Test ()
    public void supprimerMessage_ADMIN_supprimeSonMessageOK() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, NomTopicDejaExistantException {

        Personne p= connexionService.connexion("admin","admin");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p.getNom(),allergie,"new message");
        forumService.ajouterMessage(allergie,sante,message);
        try {
            forumService.supprimerMessage(message,p.getIdentifiant());
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Assert.assertTrue(!allergie.getListeMessages().contains(message));

    }





    @Test ()
    public void supprimerMessage_Moderateur_suppSonMessageOK() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, IndividuNonConnecteException, UtilisateurDejaExistantException, RoleDejaAttribueException, NomTopicDejaExistantException, ActionImpossibleException {

        Personne p= connexionService.connexion("admin","admin");
        Personne p2=adminService.creerUtilisateur(p.getIdentifiant(),"modo","modo");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("modo","modo");

        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p2.getNom(),allergie,"new message");
        forumService.ajouterMessage(allergie,sante,message);
        try {
            forumService.supprimerMessage(message,p2.getIdentifiant());
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Assert.assertTrue(!allergie.getListeMessages().contains(message));


    }

    @Test ()
    public void supprimerMessage_MODERATEUR_supprimeMessageDeBasiqueOK() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, IndividuNonConnecteException, UtilisateurDejaExistantException, RoleDejaAttribueException, NomTopicDejaExistantException, ActionImpossibleException {

        Personne p= connexionService.connexion("admin","admin");
        Personne p2=adminService.creerUtilisateur(p.getIdentifiant(),"modo","modo");

        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        Personne p3=adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p3.getIdentifiant(),BASIQUE);

        connexionService.connexion("basique","basique");
        connexionService.connexion("modo","modo");

        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p3.getNom(),allergie,"new message");

        forumService.ajouterMessage(allergie,sante,message);
        try {
            forumService.supprimerMessage(message,p2.getIdentifiant());
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Assert.assertTrue(!allergie.getListeMessages().contains(message));

    }

    @Test ()
    public void supprimerMessage_MODERATEUR_supprimeMessageDeModerateurOK() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, IndividuNonConnecteException, UtilisateurDejaExistantException, RoleDejaAttribueException, NomTopicDejaExistantException, ActionImpossibleException {

        Personne p= connexionService.connexion("admin","admin");
        Personne p2=adminService.creerUtilisateur(p.getIdentifiant(),"modo","modo");

        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        Personne p3=adminService.creerUtilisateur(p.getIdentifiant(),"modo2","modo2");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p3.getIdentifiant(),MODERATEUR);

        connexionService.connexion("modo","modo");
        connexionService.connexion("modo2","modo2");

        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p3.getNom(),allergie,"new message");

        forumService.ajouterMessage(allergie,sante,message);
        try {
            forumService.supprimerMessage(message,p2.getIdentifiant());
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Assert.assertTrue(!allergie.getListeMessages().contains(message));

    }

    @Test ()
    public void supprimerMessage_MODERATEUR_supprimeMessageDeAdminOK() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, IndividuNonConnecteException, UtilisateurDejaExistantException, RoleDejaAttribueException, NomTopicDejaExistantException, ActionImpossibleException {

        Personne p= connexionService.connexion("admin","admin");
        Personne p2=adminService.creerUtilisateur(p.getIdentifiant(),"modo","modo");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("modo","modo");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p.getNom(),allergie,"new message");
        forumService.ajouterMessage(allergie,sante,message);
        try {
            forumService.supprimerMessage(message,p2.getIdentifiant());
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Assert.assertTrue(!allergie.getListeMessages().contains(message));

    }


    @Test ()
    public void supprimerMessage_Basique_suppSonMessageOK() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, IndividuNonConnecteException, UtilisateurDejaExistantException, RoleDejaAttribueException, NomTopicDejaExistantException, ActionImpossibleException {

        Personne p= connexionService.connexion("admin","admin");
        Personne p2=adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p2.getNom(),allergie,"new message");
        forumService.ajouterMessage(allergie,sante,message);
        try {
            forumService.supprimerMessage(message,p2.getIdentifiant());
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Assert.assertTrue(!allergie.getListeMessages().contains(message));


    }


    @Test (expected = ActionImpossibleException.class)
    public void supprimerMessage_Basique_suppMessageDeAdmin() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, IndividuNonConnecteException, ActionImpossibleException, UtilisateurDejaExistantException, RoleDejaAttribueException, NomTopicDejaExistantException {

        Personne p= connexionService.connexion("admin","admin");
        Personne p2=adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p.getNom(),allergie,"new message");
        forumService.ajouterMessage(allergie,sante,message);
        forumService.supprimerMessage(message,p2.getIdentifiant());

    }


    @Test (expected = ActionImpossibleException.class)
    public void supprimerMessage_Basique_suppMessageDeModerateur() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, IndividuNonConnecteException, ActionImpossibleException, UtilisateurDejaExistantException, RoleDejaAttribueException, NomTopicDejaExistantException {

        Personne p= connexionService.connexion("admin","admin");
        Personne p2=adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        Personne p3=adminService.creerUtilisateur(p.getIdentifiant(),"modo","modo");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p3.getIdentifiant(),MODERATEUR);

        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p3.getNom(),allergie,"new message");
        forumService.ajouterMessage(allergie,sante,message);
        forumService.supprimerMessage(message,p2.getIdentifiant());

    }

    @Test (expected = ActionImpossibleException.class)
    public void supprimerMessage_Basique_suppMessageDeAutreBasique() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, IndividuNonConnecteException, ActionImpossibleException, UtilisateurDejaExistantException, RoleDejaAttribueException, NomTopicDejaExistantException {

        Personne p= connexionService.connexion("admin","admin");
        Personne p2=adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        Personne p3=adminService.creerUtilisateur(p.getIdentifiant(),"modo","modo");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p3.getIdentifiant(),BASIQUE);

        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        Message message = forumService.creerMessage(p3.getNom(),allergie,"new message");
        forumService.ajouterMessage(allergie,sante,message);
        forumService.supprimerMessage(message,p2.getIdentifiant());

    }


    @Test ()
    public void supprimerTopic_AdminOK() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, NomTopicDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        try {
            forumService.supprimerTopic(allergie,p.getIdentifiant());
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Assert.assertTrue(!sante.getListeTopics().contains(allergie));


    }

    @Test ()
    public void supprimerTopic_ModerateurOK() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, IndividuNonConnecteException, UtilisateurDejaExistantException, RoleDejaAttribueException, NomTopicDejaExistantException, ActionImpossibleException {
        Personne p= connexionService.connexion("admin","admin");
        Personne p2=adminService.creerUtilisateur(p.getIdentifiant(),"modo","modo");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.connexion("modo","modo");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        try {
            forumService.supprimerTopic(allergie,p2.getIdentifiant());
        } catch (ActionImpossibleException e) {
            Assert.fail();
        }
        Assert.assertTrue(!sante.getListeTopics().contains(allergie));


    }

    @Test (expected = ActionImpossibleException.class)
    public void supprimerTopic_BasiqueKO() throws InformationManquanteException, CoupleUtilisateurMDPInconnuException, IndividuNonConnecteException, ActionImpossibleException, UtilisateurDejaExistantException, RoleDejaAttribueException, NomTopicDejaExistantException {
        Personne p= connexionService.connexion("admin","admin");
        Personne p2=adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        connexionService.connexion("basique","basique");
        Theme sante = forumService.creerThemeBis("Sante");
        Topic allergie = forumService.creerEtAjouterTopicATheme("Allergie",sante,"Ilyas");
        forumService.supprimerTopic(allergie,p2.getIdentifiant());
        Assert.assertTrue(!sante.getListeTopics().contains(allergie));
    }











}