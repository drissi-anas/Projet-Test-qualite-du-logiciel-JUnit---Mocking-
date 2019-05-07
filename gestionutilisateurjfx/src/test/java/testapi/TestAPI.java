package testapi;

import controleur.Controleur;
import facade.*;
import facade.erreurs.*;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import modele.forum.Message;
import modele.forum.Theme;
import modele.forum.Topic;
import modele.inscription.InscriptionPotentielle;
import modele.personnes.Personne;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.Collection;



import static facade.AdminService.ADMIN;
import static facade.AdminService.BASIQUE;
import static facade.AdminService.MODERATEUR;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertTrue;
import static org.testfx.matcher.control.MenuItemMatchers.hasText;

public class TestAPI extends ApplicationTest {


    private static final long SLEEP_TIME = 2000;
    private Stage stage;
    private AdminService adminService;
    private BasiquesOffLineService basiquesOffLineService;
    private ConnexionService connexionService;
    private ForumService forumService;
    private FabriqueMock fabriqueMock;
    private Controleur controleur;
    private Personne p;
    private Personne p2;

    ComboBox<String> comboBox;

//
    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;
        fabriqueMock = new FabriqueMockImpl();
        adminService=fabriqueMock.creerMockAdminService();
        basiquesOffLineService=fabriqueMock.creerMockBOS();
        connexionService=fabriqueMock.creerMockConnexionServ();
        forumService=fabriqueMock.creerMockForum();

    }

    /**
     * Test si le champs nom est non vide
     */
    @Test
    public void saisieNomTestOK() { /*l'utilisateur saisie un nom valide */

        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.replay(adminService,basiquesOffLineService,connexionService);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });

        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();


    }

    /**
     * Test le champ vide et affiche un message d'erreur
     */
    @Test
    public void saisieNomTestCV() { /*l'utilisateur laisse un champ vide */

        expect(connexionService.estUnUtilisateurConnu("")).andReturn(true);
        EasyMock.replay(adminService,basiquesOffLineService,connexionService);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });


        sleepBetweenActions();
        clickOn("#nom");
        sleepBetweenActions();
        write("");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();


    }


    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws InformationManquanteException
     *
     * Test le champ vide pour mot de passe, affiche un message d'erreur
     */
    @Test
    public void saisieMdpOKAdminAndModCV () throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, IndividuNonConnecteException { /*Un utilisateur (admin et moderateur) se connecte avec un  couple login/mdp valide */
        p =fabriqueMock.creerMockPersonne();
        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);

        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });

        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();



    }


    /**
     *
     * Test d'un saisi de nom déjà existant
     */
    @Test
    public void saisieNomTestKO()  {

        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(false);
        EasyMock.replay(adminService,basiquesOffLineService,connexionService);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });

        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        press(KeyCode.ENTER);

    }


    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws InformationManquanteException
     *
     * Test la connexion d'un Admin/Modérateur
     */
    @Test
    public void saisieMdpOKAdminAndMod () throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, IndividuNonConnecteException { /*Un utilisateur (admin et moderateur) se connecte avec un  couple login/mdp valide */
        p =fabriqueMock.creerMockPersonne();
        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);




        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });

        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();



    }


    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws InformationManquanteException
     *
     * Test la connexion pour un utilisateur non admin et non modérateur
     */
    @Test
    public void saisieMdpNonModNonAdmin () throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, IndividuNonConnecteException { /*Un utilisateur basique se connecte avec un  couple login/mdp valide */
        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);




        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });

        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();



    }

    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws InformationManquanteException
     *
     * Test la connexion d'un modérateur
     */
    @Test
    public void saisieMdpModNonAdmin () throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, IndividuNonConnecteException {/*Un moderateur se connecte avec un  couple login/mdp valide */
        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);




        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });

        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();



    }

    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws InformationManquanteException
     *
     * Test la connexion d'un admin
     */
    @Test
    public void saisieMdpAdminNonMod () throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, IndividuNonConnecteException { /*Un admin (non moderateur) se connecte avec un  couple login/mdp valide */
        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);




        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });

        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions(); clickOn("#motDePasse");
        clickOn("#boutonValidermdp");
        sleepBetweenActions();



    }

    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws InformationManquanteException
     *
     * Test la saisie d'un mauvais mot de passe, affiche un message d'erreur
     */
    @Test
    public void saisieMdpKO() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException { /*Un utilisateurse connecte avec un  couple login/mdp invalide */
        long l = 1;
        CoupleUtilisateurMDPInconnuException e= new CoupleUtilisateurMDPInconnuException();
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","123")).andThrow(e.fillInStackTrace());

        EasyMock.replay(adminService,basiquesOffLineService,connexionService);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });

        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();
        press(KeyCode.ENTER);



    }


    private void sleepBetweenActions() {

        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }


    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws UtilisateurDejaExistantException
     * @throws RoleDejaAttribueException
     * @throws InformationManquanteException
     *
     * Test la création d'un modérateur par un admin/modérateur
     */
    @Test
    public void ajouterUtilisateurOK () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException { /*un admin ajoute un nouvel utilisateur */

        p =fabriqueMock.creerMockPersonne();
        p2=fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);

        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);

        expect(adminService.creerUtilisateur(1,"Hajar","111")).andReturn(p2);
        expect(p.getIdentifiant()).andReturn(l);
        this.adminService.associerRoleUtilisateur(1,1,MODERATEUR);
        expect(p.getIdentifiant()).andReturn(l);
        expect(p2.getIdentifiant()).andReturn(1L);
        expect(p2.getNom()).andReturn("Hajar");
        expect(this.connexionService.estUnAdmin(l)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(this.connexionService.estUnModerateur(l)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);



        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p,p2);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });

        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");

        sleepBetweenActions();
        clickOn("#ajouterUtilisateur");
        sleepBetweenActions();

        sleepBetweenActions();
        clickOn("#nom");
        sleepBetweenActions();
        write("Hajar");

        sleepBetweenActions();
        clickOn("#motDePasse");
        sleepBetweenActions();
        write("111");

        sleepBetweenActions();
        clickOn("#confirmationMotDePasse");
        sleepBetweenActions();
        write("111");

        sleepBetweenActions();
        clickOn("#roles");
        sleepBetweenActions();
        clickOn(MODERATEUR);
        sleepBetweenActions();
        clickOn("#enregistreUser");
        sleepBetweenActions();
        press(KeyCode.ENTER);

    }


    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws UtilisateurDejaExistantException
     * @throws InformationManquanteException
     *
     * Test la création d'un utilisateur déjà existant
     */
    @Test
    public void ajouterUtilisateurKO () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException { /*Un admin ajoute un utilisateur déjà existant */
        UtilisateurDejaExistantException e =new UtilisateurDejaExistantException();

        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);

        expect(adminService.creerUtilisateur(1,"Hajar","111")).andThrow(e.fillInStackTrace());
        expect(p.getIdentifiant()).andReturn(l);


        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });

        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();

        sleepBetweenActions();
        clickOn("#ajouterUtilisateur");
        sleepBetweenActions();

        sleepBetweenActions();
        clickOn("#nom");
        sleepBetweenActions();
        write("Hajar");

        sleepBetweenActions();
        clickOn("#motDePasse");
        sleepBetweenActions();
        write("111");

        sleepBetweenActions();
        clickOn("#confirmationMotDePasse");
        sleepBetweenActions();
        write("111");

        sleepBetweenActions();
        clickOn("#roles");
        sleepBetweenActions();
        clickOn(MODERATEUR);

        sleepBetweenActions();
        clickOn("#enregistreUser");
        sleepBetweenActions();
        press(KeyCode.ENTER);

    }

    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws UtilisateurDejaExistantException
     * @throws InformationManquanteException
     *
     * Test la création d'un utilisateur avec des champs vides
     */
    @Test
    public void ajouterUtilisateurChampVide () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException { /*un admin ajoute un nouvel utilisateur */

        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.creerUtilisateur(1,"","")).andReturn(p);
        expect(p.getIdentifiant()).andReturn(l);
        expect(p.getNom()).andReturn("");
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);



        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });

        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");

        sleepBetweenActions();
        clickOn("#ajouterUtilisateur");
        sleepBetweenActions();

        sleepBetweenActions();
        clickOn("#nom");
        sleepBetweenActions();
        write("");
        clickOn("#motDePasse");
        write("");
        clickOn("#confirmationMotDePasse");
        write("");

        sleepBetweenActions();
        clickOn("#roles");
        sleepBetweenActions();
        clickOn(MODERATEUR);

        sleepBetweenActions();
        clickOn("#enregistreUser");
        sleepBetweenActions();
        press(KeyCode.ENTER);

    }

    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws UtilisateurDejaExistantException
     * @throws InformationManquanteException
     *
     * Test la création d'un utilisateur avec des informations manquantes
     */
    @Test
    public void ajouterUtilisateurChampVidePseudo () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, InformationManquanteException, IndividuNonConnecteException { /*un admin ajoute un nouvel utilisateur */

        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.creerUtilisateur(1,"","000")).andReturn(p);
        expect(p.getIdentifiant()).andReturn(l);
        expect(p.getNom()).andReturn("");
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);



        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });

        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");

        sleepBetweenActions();
        clickOn("#ajouterUtilisateur");
        sleepBetweenActions();

        sleepBetweenActions();
        clickOn("#nom");
        sleepBetweenActions();
        write("");
        clickOn("#motDePasse");
        write("000");
        clickOn("#confirmationMotDePasse");
        write("000");

        sleepBetweenActions();
        clickOn("#roles");
        sleepBetweenActions();
        clickOn(MODERATEUR);
        sleepBetweenActions();

        clickOn("#enregistreUser");
        sleepBetweenActions();
        press(KeyCode.ENTER);

    }


    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws InformationManquanteException
     *
     * Test la déconnexion d'un admin
     */
    @Test
    public void adminQuitter () throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, IndividuNonConnecteException { /* un admin se connecte puis quitte l'appli */
        UtilisateurDejaExistantException e =new UtilisateurDejaExistantException();

        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        connexionService.deconnexion(1);
        expect(p.getIdentifiant()).andReturn(l);



        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });

        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();

        sleepBetweenActions();
        clickOn("#quitter");
        sleepBetweenActions();


    }


    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws InformationManquanteException
     *
     * Test la déconnexion d'un modérateur
     */
    @Test
    public void modQuitter () throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, IndividuNonConnecteException { /* un moderateur se connecte puis quitte l'appli */
        UtilisateurDejaExistantException e =new UtilisateurDejaExistantException();

        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        connexionService.deconnexion(1);
        expect(p.getIdentifiant()).andReturn(l);



        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });

        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();

        sleepBetweenActions();
        clickOn("#quitter");
        sleepBetweenActions();


    }


    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws InformationManquanteException
     *
     * Test la déconnexion d'un utilisateur basic
     */
    @Test
    public void basicQuitter () throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, IndividuNonConnecteException { /* un utilisateur basique se connecte puis quitte l'appli */
        UtilisateurDejaExistantException e =new UtilisateurDejaExistantException();

        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);
        connexionService.deconnexion(1);
        expect(p.getIdentifiant()).andReturn(l);



        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });

        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        sleepBetweenActions();
        sleepBetweenActions();
        sleepBetweenActions();

        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();

        sleepBetweenActions();
        clickOn("#quitter");
        sleepBetweenActions();


    }

    /**
     * @throws UtilisateurDejaExistantException
     * @throws InformationManquanteException
     *
     * Test d'une demande d'inscription pour un modérateur
     */
    @Test
    public void demandeInscriptionModerateurTestOK() throws UtilisateurDejaExistantException, InformationManquanteException { /* un utilisateur fait une demande d'inscription en tant que moderateur */


        expect(basiquesOffLineService.posterDemandeInscription("Yohan","123",MODERATEUR)).andReturn(1L);
        EasyMock.expectLastCall();


        EasyMock.replay(adminService, basiquesOffLineService, connexionService);

        controleur = new Controleur(connexionService, adminService, basiquesOffLineService, stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });


        sleepBetweenActions();
        clickOn("#demanderInscription");
        sleepBetweenActions();

        clickOn("#nom");
        sleepBetweenActions();
        write("Yohan");
        sleepBetweenActions();
        clickOn("#motDePasse");
        write("123");
        sleepBetweenActions();
        clickOn("#roles");
        sleepBetweenActions();
        clickOn(MODERATEUR);
        sleepBetweenActions();
        clickOn("#valideruser");
        sleepBetweenActions();
        press(KeyCode.ENTER);


    }


    /**
     * @throws UtilisateurDejaExistantException
     * @throws InformationManquanteException
     *
     * Test d'une demande d'inscription pour un Admin
     */
    @Test
    public void demandeInscriptionAdminTestOK() throws UtilisateurDejaExistantException, InformationManquanteException { /* un utilisateur fait une demande d'inscription en tant qu'admin */


        expect(basiquesOffLineService.posterDemandeInscription("Yohan","123",ADMIN)).andReturn(1L);
        EasyMock.expectLastCall();
        EasyMock.replay(adminService, basiquesOffLineService, connexionService);

        controleur = new Controleur(connexionService, adminService, basiquesOffLineService, stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });
        sleepBetweenActions();
        clickOn("#demanderInscription");
        sleepBetweenActions();

        clickOn("#nom");
        sleepBetweenActions();
        write("Yohan");
        sleepBetweenActions();
        clickOn("#motDePasse");
        write("123");
        sleepBetweenActions();
        clickOn("#roles");
        sleepBetweenActions();
        clickOn(ADMIN);
        sleepBetweenActions();
        clickOn("#valideruser");
        sleepBetweenActions();
        press(KeyCode.ENTER);


    }

    /**
     * @throws UtilisateurDejaExistantException
     * @throws InformationManquanteException
     *
     * Test d'une demande d'inscription pour un utilisateur basic
     */
    @Test
    public void demandeInscriptionBasiqueTestOK() throws UtilisateurDejaExistantException, InformationManquanteException { /* un utilisateur fait une demande d'inscription en tant qu'utlisateur basique */

        expect(basiquesOffLineService.posterDemandeInscription("Yohan","123",BASIQUE)).andReturn(1L);
        EasyMock.expectLastCall();
        EasyMock.replay(adminService, basiquesOffLineService, connexionService);

        controleur = new Controleur(connexionService, adminService, basiquesOffLineService, stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });
        sleepBetweenActions();
        clickOn("#demanderInscription");
        sleepBetweenActions();

        clickOn("#nom");
        sleepBetweenActions();
        write("Yohan");
        sleepBetweenActions();
        clickOn("#motDePasse");
        write("123");
        sleepBetweenActions();
        clickOn("#roles");
        sleepBetweenActions();
        clickOn(BASIQUE);
        sleepBetweenActions();
        clickOn("#valideruser");
        sleepBetweenActions();
        press(KeyCode.ENTER);


    }

    /**
     * un utlisateur veut faire une demande d'inscription puis annule sa demande
     */
    @Test
    public void demandeInscriptionAnnuler()  {

        EasyMock.replay(adminService, basiquesOffLineService, connexionService);

        controleur = new Controleur(connexionService, adminService, basiquesOffLineService, stage,forumService);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });


        sleepBetweenActions();
        clickOn("#demanderInscription");
        sleepBetweenActions();
        clickOn("#annulerInscrip");
        sleepBetweenActions();

    }

    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws InformationManquanteException
     *
     * Test la suppression d'un utilisateur par un Admin/Modérateur puis affiche la liste des utilisateurs mise à jour
     */
    @Test
    public void supprimerUser() throws CoupleUtilisateurMDPInconnuException, InformationManquanteException, IndividuNonConnecteException {
        p =fabriqueMock.creerMockPersonne();
        p2= fabriqueMock.creerMockPersonne();
        long l = 1;
        long l2=2;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();

        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);

        //Recupère la liste et ajoute des Users
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(p.getIdentifiant()).andReturn(l);
        expect(p.getIdentifiant()).andReturn(l);
        expect(p.getNom()).andReturn("Yohan");
        expect(p.getNom()).andReturn("Yohan");



        expect(p2.getIdentifiant()).andReturn(l2);
        expect(p2.getIdentifiant()).andReturn(l2);
        expect(p2.getIdentifiant()).andReturn(l2);
        expect(p2.getNom()).andReturn("Sema");
        expect(p2.getNom()).andReturn("Sema");
        expect(p2.getIdentifiant()).andReturn(l2);


        //SupprimerUSer
        expect(p.getIdentifiant()).andReturn(l);
        expect(p2.getIdentifiant()).andReturn(l2);
        adminService.supprimerUtilisateur(1,2);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);

        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(p.getIdentifiant()).andReturn(l);
        expect(p.getIdentifiant()).andReturn(l);
        expect(p.getNom()).andReturn("Yohan");
        expect(p.getNom()).andReturn("Yohan");


        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p,p2);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });

        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");
        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();
        clickOn("#supprimerUtilisateur");

        ListView<Personne> liste = (ListView<Personne>) GuiTest.find("#listeUtilisateurs");
        liste.getItems().add(p);
        liste.getItems().add(p2);


        sleepBetweenActions();
        liste.getSelectionModel().selectIndices(1);
        sleepBetweenActions();
        clickOn("#supprimeruser");

        sleepBetweenActions();
        press(KeyCode.ENTER);

        ListView<Personne> liste2 = (ListView<Personne>) GuiTest.find("#listeUtilisateurs");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                liste2.getItems().add(p);
            }
        });

        sleepBetweenActions();
        sleepBetweenActions();


    }

    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws UtilisateurDejaExistantException
     * @throws RoleDejaAttribueException
     * @throws InformationManquanteException
     *
     * Un Admin/Moderateur accepte une demande d'inscription pour un utilisateur basique et affiche liste mise à jour
     */
    @Test
    public void traiterDemandeAdminEtModAccept () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        p =fabriqueMock.creerMockPersonne();
        p2=fabriqueMock.creerMockPersonne();
        InscriptionPotentielle i= fabriqueMock.creerInscri();
        InscriptionPotentielle i2=fabriqueMock.creerInscri();
        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);


        //Recupère liste des demandes
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(i.getIdentifiant()).andReturn(1L);
        expect(i.getIdentifiant()).andReturn(1L);
        expect(i.getNom()).andReturn("Hajar");
        expect(i.getNom()).andReturn("Hajar");
        expect(i.getRoleDemande()).andReturn(BASIQUE);
        expect(i.getRoleDemande()).andReturn(BASIQUE);

        expect(p2.getIdentifiant()).andReturn(2L);
        expect(i2.getIdentifiant()).andReturn(2L);
        expect(i2.getIdentifiant()).andReturn(2L);
        expect(i2.getNom()).andReturn("Yohan");
        expect(i2.getNom()).andReturn("Yohan");
        expect(i2.getRoleDemande()).andReturn(BASIQUE);
        expect(i2.getRoleDemande()).andReturn(BASIQUE);


        //accept
        adminService.validerInscription(l,1L);
        expect(p.getIdentifiant()).andReturn(l);
        expect(i.getIdentifiant()).andReturn(1L);
        expect(adminService.getListeDesDemandesNonTraitees(l)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(i.getIdentifiant()).andReturn(1L);
        expect(i.getNom()).andReturn("Hajar");

        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p2.getIdentifiant()).andReturn(2L);
        expect(i2.getIdentifiant()).andReturn(2L);
        expect(i2.getIdentifiant()).andReturn(2L);
        expect(i2.getNom()).andReturn("Yohan");
        expect(i2.getNom()).andReturn("Yohan");
        expect(i2.getRoleDemande()).andReturn(BASIQUE);
        expect(i2.getRoleDemande()).andReturn(BASIQUE);

        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p,i,p2,i2);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });


        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();


        write("123");
        sleepBetweenActions();
        clickOn("#traiterDemandes");
        ListView<InscriptionPotentielle> liste = (ListView<InscriptionPotentielle>) GuiTest.find("#listeDemandes");
        liste.getItems().add(i);
        liste.getItems().add(i2);
        sleepBetweenActions();
        liste.getSelectionModel().selectIndices(0);
        sleepBetweenActions();
        clickOn("#accept");
        sleepBetweenActions();
        press(KeyCode.ENTER);
        ListView<InscriptionPotentielle> liste2 = (ListView<InscriptionPotentielle>) GuiTest.find("#listeDemandes");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                liste2.getItems().add(i2
                );
            }
        });
        sleepBetweenActions();
        sleepBetweenActions();

    }


    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws UtilisateurDejaExistantException
     * @throws RoleDejaAttribueException
     * @throws InformationManquanteException
     *
     * Un Modérateur accepte une demande d'inscription d'un utilisateur basique. Mise à jour de la liste des demandes non traitees.
     */
    @Test
    public void traiterDemandeKOAdminOKMod () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        p =fabriqueMock.creerMockPersonne();

        p2=fabriqueMock.creerMockPersonne();
        InscriptionPotentielle i= fabriqueMock.creerInscri();
        InscriptionPotentielle i2=fabriqueMock.creerInscri();
        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);


        //Recupère liste des demandes
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(1L);
        expect(i.getIdentifiant()).andReturn(1L);
        expect(i.getIdentifiant()).andReturn(1L);
        expect(i.getNom()).andReturn("Hajar");
        expect(i.getNom()).andReturn("Hajar");
        expect(i.getRoleDemande()).andReturn(BASIQUE);
        expect(i.getRoleDemande()).andReturn(BASIQUE);
        expect(p.getIdentifiant()).andReturn(1L);
        expect(i2.getIdentifiant()).andReturn(2L);
        expect(i2.getIdentifiant()).andReturn(2L);
        expect(i2.getNom()).andReturn("Yohan");
        expect(i2.getNom()).andReturn("Yohan");
        expect(i2.getRoleDemande()).andReturn(BASIQUE);
        expect(i2.getRoleDemande()).andReturn(BASIQUE);

        //accept
        adminService.validerInscription(l,1L);
        expect(p.getIdentifiant()).andReturn(l);
        expect(i.getIdentifiant()).andReturn(1L);
        expect(adminService.getListeDesDemandesNonTraitees(l)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(i.getIdentifiant()).andReturn(1L);
        expect(i.getNom()).andReturn("Hajar");

        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(1L);
        expect(i2.getIdentifiant()).andReturn(2L);
        expect(i2.getIdentifiant()).andReturn(2L);
        expect(i2.getNom()).andReturn("Yohan");
        expect(i2.getNom()).andReturn("Yohan");
        expect(i2.getRoleDemande()).andReturn(BASIQUE);
        expect(i2.getRoleDemande()).andReturn(BASIQUE);


        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p,i,i2);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });


        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();


        write("123");
        sleepBetweenActions();
        clickOn("#traiterDemandes");
        ListView<InscriptionPotentielle> liste = (ListView<InscriptionPotentielle>) GuiTest.find("#listeDemandes");
        liste.getItems().addAll(i,i2);
        sleepBetweenActions();
        liste.getSelectionModel().selectIndices(0);
        sleepBetweenActions();
        clickOn("#accept");
        sleepBetweenActions();
        press(KeyCode.ENTER);
        ListView<InscriptionPotentielle> liste2 = (ListView<InscriptionPotentielle>) GuiTest.find("#listeDemandes");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                liste2.getItems().add(i2);
            }
        });

        sleepBetweenActions();
        sleepBetweenActions();




    }

    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws UtilisateurDejaExistantException
     * @throws RoleDejaAttribueException
     * @throws InformationManquanteException
     *
     * Un Admin/moderateur refuse une demande d'inscription. Mise à jour de la liste.
     */
    @Test
    public void traiterDemandeAdminEtModRefus () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        p =fabriqueMock.creerMockPersonne();
        InscriptionPotentielle i= fabriqueMock.creerInscri();
        InscriptionPotentielle i2=fabriqueMock.creerInscri();
        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);


        //Recupère liste des demandes
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(i.getIdentifiant()).andReturn(1L);
        expect(i.getIdentifiant()).andReturn(1L);
        expect(i.getNom()).andReturn("Hajar");
        expect(i.getNom()).andReturn("Hajar");
        expect(i.getRoleDemande()).andReturn(BASIQUE);
        expect(i.getRoleDemande()).andReturn(BASIQUE);

        //accept
        adminService.refuserInscription(l,1L);
        expect(p.getIdentifiant()).andReturn(l);
        expect(i.getIdentifiant()).andReturn(1L);
        expect(adminService.getListeDesDemandesNonTraitees(l)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(i.getIdentifiant()).andReturn(1L);
        expect(i.getNom()).andReturn("Hajar");



        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p,i);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });


        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();


        write("123");
        sleepBetweenActions();
        clickOn("#traiterDemandes");
        ListView<InscriptionPotentielle> liste = (ListView<InscriptionPotentielle>) GuiTest.find("#listeDemandes");
        liste.getItems().add(i);
        sleepBetweenActions();
        liste.getSelectionModel().selectIndices(0);
        sleepBetweenActions();
        clickOn("#decline");
        sleepBetweenActions();
        press(KeyCode.ENTER);

    }

    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws UtilisateurDejaExistantException
     * @throws RoleDejaAttribueException
     * @throws InformationManquanteException
     *
     * Un Modérateur refuse la demande d'inscription.Mise à jour de la liste.
     */
    @Test
    public void traiterDemandeAdminKOModOKRefus () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, InformationManquanteException, IndividuNonConnecteException, ActionImpossibleException {
        p =fabriqueMock.creerMockPersonne();
        InscriptionPotentielle i= fabriqueMock.creerInscri();
        InscriptionPotentielle i2=fabriqueMock.creerInscri();
        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage,forumService);
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan","123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);


        //Recupère liste des demandes
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(i.getIdentifiant()).andReturn(1L);
        expect(i.getIdentifiant()).andReturn(1L);
        expect(i.getNom()).andReturn("Hajar");
        expect(i.getNom()).andReturn("Hajar");
        expect(i.getRoleDemande()).andReturn(BASIQUE);
        expect(i.getRoleDemande()).andReturn(BASIQUE);

        //accept
        adminService.refuserInscription(l,1L);
        expect(p.getIdentifiant()).andReturn(l);
        expect(i.getIdentifiant()).andReturn(1L);
        expect(adminService.getListeDesDemandesNonTraitees(l)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(i.getIdentifiant()).andReturn(1L);
        expect(i.getNom()).andReturn("Hajar");



        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p,i);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });


        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();


        write("123");
        sleepBetweenActions();
        clickOn("#traiterDemandes");
        ListView<InscriptionPotentielle> liste = (ListView<InscriptionPotentielle>) GuiTest.find("#listeDemandes");
        liste.getItems().add(i);
        sleepBetweenActions();
        liste.getSelectionModel().selectIndices(0);
        sleepBetweenActions();
        clickOn("#decline");
        sleepBetweenActions();
        press(KeyCode.ENTER);

    }

    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws UtilisateurDejaExistantException
     * @throws RoleDejaAttribueException
     * @throws ThemeInexistantException
     * @throws InformationManquanteException
     *
     * Affichage de la liste des themes
     */

    @Test
    public void chargerTheme () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, ThemeInexistantException, InformationManquanteException, IndividuNonConnecteException {
        p = fabriqueMock.creerMockPersonne();
        Theme t= fabriqueMock.creerThemeForum();
        Collection <Theme> lesthemes=new ArrayList<>();
        Collection<Topic> lesTopics=new ArrayList<>();

        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes = new ArrayList<>();
        controleur = new Controleur(connexionService, adminService, basiquesOffLineService, stage,forumService);
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan", "123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);

        expect(forumService.getListeTheme()).andReturn(lesthemes);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getNom()).andReturn("Santé");
        expect(t.getNom()).andReturn("Santé");
        expect(t.getNom()).andReturn("Santé");

        expect(forumService.recupererTheme("Santé")).andReturn(t);
        expect(forumService.getListeTopicPourUnTheme(t)).andReturn(lesTopics);


        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p,forumService,t);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });


        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();
        clickOn("#chargerListe");
        ListView <Theme> listeTheme = (ListView<Theme>) GuiTest.find("#listeTheme");
        listeTheme.getItems().add(t);
        sleepBetweenActions();
        listeTheme.getSelectionModel().selectIndices(0);
        sleepBetweenActions();


    }

    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws UtilisateurDejaExistantException
     * @throws RoleDejaAttribueException
     * @throws ThemeInexistantException
     * @throws InformationManquanteException
     *
     * Affichage des topics d'un theme choisi
     */

    @Test
    public void choisirTheme () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, ThemeInexistantException, InformationManquanteException, IndividuNonConnecteException {
        p = fabriqueMock.creerMockPersonne();
        Theme t= fabriqueMock.creerThemeForum();
        Collection <Theme> lesthemes=new ArrayList<>();
        Collection<Topic> lesTopics=new ArrayList<>();
        Topic topic = fabriqueMock.creerTopic();

        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes = new ArrayList<>();
        controleur = new Controleur(connexionService, adminService, basiquesOffLineService, stage,forumService);
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan", "123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);

        expect(forumService.getListeTheme()).andReturn(lesthemes);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getNom()).andReturn("Santé");
        expect(t.getNom()).andReturn("Santé");
        expect(t.getNom()).andReturn("Santé");

        expect(forumService.recupererTheme("Santé")).andReturn(t);
        expect(forumService.getListeTopicPourUnTheme(t)).andReturn(lesTopics);
        expect(topic.getIdentifiant()).andReturn(1L);
        expect(topic.getIdentifiant()).andReturn(1L);
        expect(topic.getNom()).andReturn("Maladie");
        expect(topic.getNom()).andReturn("Maladie");


        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p,forumService,t,topic);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });


        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();
        clickOn("#chargerListe");
        ListView <Theme> listeTheme = (ListView<Theme>) GuiTest.find("#listeTheme");
        listeTheme.getItems().add(t);
        sleepBetweenActions();
        listeTheme.getSelectionModel().selectIndices(0);
        sleepBetweenActions();
        clickOn("#choisirTheme");

        ListView<Topic> listTopic=(ListView<Topic>)GuiTest.find("#listeTopics");
        listTopic.getItems().add(topic);
        sleepBetweenActions();
    }


    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws UtilisateurDejaExistantException
     * @throws RoleDejaAttribueException
     * @throws ThemeInexistantException
     * @throws InformationManquanteException
     *
     *Affiche la page pour la création d'un topic
     */
    @Test
    public void affihcerCreationTopic () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, ThemeInexistantException, InformationManquanteException, IndividuNonConnecteException {
        p = fabriqueMock.creerMockPersonne();
        Theme t= fabriqueMock.creerThemeForum();
        Collection <Theme> lesthemes=new ArrayList<>();
        Collection<Topic> lesTopics=new ArrayList<>();
        Topic topic = fabriqueMock.creerTopic();

        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes = new ArrayList<>();
        controleur = new Controleur(connexionService, adminService, basiquesOffLineService, stage,forumService);
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan", "123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);

        expect(forumService.getListeTheme()).andReturn(lesthemes);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getNom()).andReturn("Santé");
        expect(t.getNom()).andReturn("Santé");
        expect(t.getNom()).andReturn("Santé");

        expect(forumService.recupererTheme("Santé")).andReturn(t);
        expect(forumService.getListeTopicPourUnTheme(t)).andReturn(lesTopics);
        expect(topic.getIdentifiant()).andReturn(1L);
        expect(topic.getIdentifiant()).andReturn(1L);
        expect(topic.getNom()).andReturn("Maladie");
        expect(topic.getNom()).andReturn("Maladie");





        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p,forumService,t,topic);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });


        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();
        clickOn("#chargerListe");
        ListView <Theme> listeTheme = (ListView<Theme>) GuiTest.find("#listeTheme");
        listeTheme.getItems().add(t);
        sleepBetweenActions();
        listeTheme.getSelectionModel().selectIndices(0);
        sleepBetweenActions();
        clickOn("#choisirTheme");

        ListView<Topic> listTopic=(ListView<Topic>)GuiTest.find("#listeTopics");
        listTopic.getItems().add(topic);
        sleepBetweenActions();
        clickOn("#creerTopic");
        sleepBetweenActions();

    }

    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws TopicInexistantException
     * @throws ThemeInexistantException
     * @throws TopicInexistantException
     * @throws InformationManquanteException
     *
     * Affichage des messages pour un topic choisi
     */
    @Test
    public void afficherUnTopic () throws CoupleUtilisateurMDPInconnuException, TopicInexistantException, ThemeInexistantException, TopicInexistantException, InformationManquanteException, IndividuNonConnecteException {
        p = fabriqueMock.creerMockPersonne();
        Theme t= fabriqueMock.creerThemeForum();
        Collection <Theme> lesthemes=new ArrayList<>();
        Collection<Topic> lesTopics=new ArrayList<>();
        Collection<Message> lesMessages=new ArrayList<>();
        Topic topic = fabriqueMock.creerTopic();
        Message message=fabriqueMock.creerMessage();

        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes = new ArrayList<>();
        controleur = new Controleur(connexionService, adminService, basiquesOffLineService, stage,forumService);
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan", "123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);

        expect(forumService.getListeTheme()).andReturn(lesthemes);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getNom()).andReturn("Santé");
        expect(t.getNom()).andReturn("Santé");
        expect(t.getNom()).andReturn("Santé");

        expect(forumService.recupererTheme("Santé")).andReturn(t);
        expect(forumService.getListeTopicPourUnTheme(t)).andReturn(lesTopics);
        expect(topic.getIdentifiant()).andReturn(1L);
        expect(topic.getIdentifiant()).andReturn(1L);
        expect(topic.getNom()).andReturn("Maladie");
        expect(topic.getNom()).andReturn("Maladie");
        expect(topic.getNom()).andReturn("Maladie");

        expect(topic.getTheme()).andReturn(t);
        expect(t.getNom()).andReturn("Santé");
        expect(forumService.getListeMessagePourUnTopic(topic)).andReturn(lesMessages);
        expect(message.getAuteur()).andReturn("Sema");
        expect(message.getAuteur()).andReturn("Sema");
        expect(message.getText()).andReturn("Combien coute un doliprane ?");
        expect(message.getText()).andReturn("Combien coute un doliprane ?");



        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p,forumService,t,topic,message);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });


        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();
        clickOn("#chargerListe");
        ListView <Theme> listeTheme = (ListView<Theme>) GuiTest.find("#listeTheme");
        listeTheme.getItems().add(t);
        sleepBetweenActions();
        listeTheme.getSelectionModel().selectIndices(0);
        sleepBetweenActions();
        clickOn("#choisirTheme");

        ListView<Topic> listTopic=(ListView<Topic>)GuiTest.find("#listeTopics");
        listTopic.getItems().add(topic);
        sleepBetweenActions();
        listTopic.getSelectionModel().selectIndices(0);
        sleepBetweenActions();
        clickOn("#choisirTopics");
        ListView<Message> listMessage=(ListView<Message>)GuiTest.find("#listeMessage");
        listMessage.getItems().add(message);
        sleepBetweenActions();

    }

    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws NomTopicDejaExistantException
     * @throws ThemeInexistantException
     * @throws TopicInexistantException
     * @throws InformationManquanteException
     *
     * Creéation d'un topic avec un message pour un theme choisi
     */
    @Test
    public void creerUnTopic () throws CoupleUtilisateurMDPInconnuException, NomTopicDejaExistantException, ThemeInexistantException, TopicInexistantException, InformationManquanteException, IndividuNonConnecteException {
        p = fabriqueMock.creerMockPersonne();
        Theme t= fabriqueMock.creerThemeForum();
        Collection <Theme> lesthemes=new ArrayList<>();
        Collection<Topic> lesTopics=new ArrayList<>();
        Collection<Message> lesMessages=new ArrayList<>();
        Topic topic = fabriqueMock.creerTopic();
        Topic topic1 = fabriqueMock.creerTopic();
        Message message=fabriqueMock.creerMessage();

        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes = new ArrayList<>();
        controleur = new Controleur(connexionService, adminService, basiquesOffLineService, stage,forumService);
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan", "123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);

        expect(forumService.getListeTheme()).andReturn(lesthemes);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getNom()).andReturn("Santé");
        expect(t.getNom()).andReturn("Santé");
        expect(t.getNom()).andReturn("Santé");

        expect(forumService.recupererTheme("Santé")).andReturn(t);
        expect(forumService.getListeTopicPourUnTheme(t)).andReturn(lesTopics);
        expect(topic.getIdentifiant()).andReturn(1L);
        expect(topic.getIdentifiant()).andReturn(1L);
        expect(topic.getNom()).andReturn("Maladie");
        expect(topic.getNom()).andReturn("Maladie");
        expect(topic.getNom()).andReturn("Maladie");

        expect(forumService.recupererTheme("Santé")).andReturn(t);
        expect(p.getNom()).andReturn("Yohan");
        expect(forumService.creerTopic("Allergie",t,"Yohan")).andReturn(topic1);
        expect(forumService.creerMessage("Yohan",topic1,"Que faire contre le pollen ?")).andReturn(message);
        forumService.ajouterMessage(topic1,t,message);
        expect(forumService.getListeMessagePourUnTopic(topic1)).andReturn(lesMessages);
        expect(topic1.getNom()).andReturn("Allergie");
        expect(topic1.getTheme()).andReturn(t);
        expect(t.getNom()).andReturn("Santé");


        expect(message.getAuteur()).andReturn("Yohan");
        expect(message.getAuteur()).andReturn("Yohan");
        expect(message.getText()).andReturn("Que faire contre le pollen ?");
        expect(message.getText()).andReturn("Que faire contre le pollen ?");


        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p,forumService,t,topic,topic1,message);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });


        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();
        clickOn("#chargerListe");
        ListView <Theme> listeTheme = (ListView<Theme>) GuiTest.find("#listeTheme");
        listeTheme.getItems().add(t);
        sleepBetweenActions();
        listeTheme.getSelectionModel().selectIndices(0);
        sleepBetweenActions();
        clickOn("#choisirTheme");

        ListView<Topic> listTopic=(ListView<Topic>)GuiTest.find("#listeTopics");
        listTopic.getItems().add(topic);
        sleepBetweenActions();
        clickOn("#creerTopic");
        sleepBetweenActions();
        clickOn("#nomTopic");
        write("Allergie");
        sleepBetweenActions();
        clickOn("#messageDuTopic");
        write("Que faire contre le pollen ?");
        sleepBetweenActions();
        clickOn("#validerTopic");
        ListView<Message> listeMessage=(ListView<Message>)GuiTest.find("#listeMessage");
        listeMessage.getItems().add(message);
        sleepBetweenActions();



    }

    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws ThemeInexistantException
     * @throws TopicInexistantException
     * @throws InformationManquanteException
     *
     * Ajouter un message à un topic
     */
    @Test
    public void ajouterUnMessageAUnTopic () throws CoupleUtilisateurMDPInconnuException, ThemeInexistantException, TopicInexistantException, InformationManquanteException, IndividuNonConnecteException {
        p = fabriqueMock.creerMockPersonne();
        Theme t= fabriqueMock.creerThemeForum();
        Collection <Theme> lesthemes=new ArrayList<>();
        Collection<Topic> lesTopics=new ArrayList<>();
        Collection<Message> lesMessages=new ArrayList<>();
        Topic topic = fabriqueMock.creerTopic();
        Message message=fabriqueMock.creerMessage();
        Message m1=fabriqueMock.creerMessage();

        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes = new ArrayList<>();
        controleur = new Controleur(connexionService, adminService, basiquesOffLineService, stage,forumService);
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan", "123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);

        expect(forumService.getListeTheme()).andReturn(lesthemes);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getNom()).andReturn("Santé");
        expect(t.getNom()).andReturn("Santé");
        expect(t.getNom()).andReturn("Santé");

        expect(forumService.recupererTheme("Santé")).andReturn(t);
        expect(forumService.getListeTopicPourUnTheme(t)).andReturn(lesTopics);
        expect(topic.getIdentifiant()).andReturn(1L);
        expect(topic.getIdentifiant()).andReturn(1L);
        expect(topic.getNom()).andReturn("Maladie");
        expect(topic.getNom()).andReturn("Maladie");
        expect(topic.getNom()).andReturn("Maladie");
        expect(topic.getTheme()).andReturn(t);
        expect(t.getNom()).andReturn("Santé");
        expect(forumService.getListeMessagePourUnTopic(topic)).andReturn(lesMessages);
        expect(message.getAuteur()).andReturn("Sema");
        expect(message.getAuteur()).andReturn("Sema");
        expect(message.getText()).andReturn("Combien coute un doliprane ?");
        expect(message.getText()).andReturn("Combien coute un doliprane ?");

        //valider message
        expect(forumService.recupererTheme("Santé")).andReturn(t);
        expect(forumService.recupererTopic("Maladie","Santé")).andReturn(topic);
        expect(forumService.creerMessage("Yohan",topic,"3 euros !")).andReturn(m1);
        forumService.ajouterMessage(topic,t,m1);
        expect(topic.getNom()).andReturn("Maladie");
        expect(topic.getTheme()).andReturn(t);
        expect(t.getNom()).andReturn("Santé");
       expect(forumService.getListeMessagePourUnTopic(topic)).andReturn(lesMessages);
        expect(message.getAuteur()).andReturn("Sema");
        expect(message.getText()).andReturn("Combien coute un doliprane ?");
        expect(message.getAuteur()).andReturn("Sema");
        expect(message.getText()).andReturn("Combien coute un doliprane ?");
        expect(m1.getAuteur()).andReturn("Yohan");
        expect(m1.getText()).andReturn("3 euros !");
        expect(m1.getAuteur()).andReturn("Yohan");
        expect(m1.getText()).andReturn("3 euros !");


        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p,forumService,t,topic,message,m1);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });


        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();
        clickOn("#chargerListe");
        ListView <Theme> listeTheme = (ListView<Theme>) GuiTest.find("#listeTheme");
        listeTheme.getItems().add(t);
        sleepBetweenActions();
        listeTheme.getSelectionModel().selectIndices(0);
        sleepBetweenActions();
        clickOn("#choisirTheme");
        ListView<Topic> listTopic=(ListView<Topic>)GuiTest.find("#listeTopics");
        listTopic.getItems().add(topic);
        sleepBetweenActions();
        listTopic.getSelectionModel().selectIndices(0);
        sleepBetweenActions();
        clickOn("#choisirTopics");
        ListView<Message> listMessage=(ListView<Message>)GuiTest.find("#listeMessage");
        listMessage.getItems().add(message);
        sleepBetweenActions();
        clickOn("#votreMessage");
        sleepBetweenActions();
        write("3 euros !");
        clickOn("#validerMessage");

        ListView<Message> listMessage2=(ListView<Message>)GuiTest.find("#listeMessage");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                listMessage2.getItems().addAll(message,m1);
            }
        });
        sleepBetweenActions();

        sleepBetweenActions();


    }

    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws UtilisateurDejaExistantException
     * @throws RoleDejaAttribueException
     * @throws ThemeInexistantException
     * @throws InformationManquanteException
     *
     * Test la création d'un theme pour utilisateur basique et affiche un message d'erreur
     */
    @Test
    public void creerThemeUser () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, ThemeInexistantException, InformationManquanteException, IndividuNonConnecteException {
        p = fabriqueMock.creerMockPersonne();
        Theme t= fabriqueMock.creerThemeForum();
        Collection <Theme> lesthemes=new ArrayList<>();
        Collection<Topic> lesTopics=new ArrayList<>();
        Topic topic = fabriqueMock.creerTopic();

        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes = new ArrayList<>();
        controleur = new Controleur(connexionService, adminService, basiquesOffLineService, stage,forumService);
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan", "123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(false);
        expect(connexionService.estUnModerateur(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);

        expect(forumService.getListeTheme()).andReturn(lesthemes);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getNom()).andReturn("Santé");
        expect(t.getNom()).andReturn("Santé");
        expect(t.getNom()).andReturn("Santé");





        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p,forumService,t);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });


        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();
        clickOn("#chargerListe");
        ListView <Theme> listeTheme = (ListView<Theme>) GuiTest.find("#listeTheme");
        listeTheme.getItems().add(t);
        sleepBetweenActions();
        clickOn("#creerTheme");
        sleepBetweenActions();
    }


    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws UtilisateurDejaExistantException
     * @throws RoleDejaAttribueException
     * @throws ThemeInexistantException
     * @throws InformationManquanteException
     *
     * Test la création d'un thème pour un Admin et affiche la liste des thèmes mise à jour.
     */
    @Test
    public void creerThemeAdmin () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, ThemeInexistantException, InformationManquanteException, IndividuNonConnecteException {
        p = fabriqueMock.creerMockPersonne();
        Theme t= fabriqueMock.creerThemeForum();
        Theme t1=fabriqueMock.creerThemeForum();
        Collection <Theme> lesthemes=new ArrayList<>();
        Collection<Topic> lesTopics=new ArrayList<>();


        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes = new ArrayList<>();
        controleur = new Controleur(connexionService, adminService, basiquesOffLineService, stage,forumService);
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan", "123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(true);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);

        expect(forumService.getListeTheme()).andReturn(lesthemes);
        expect(t.getIdentifiant()).andReturn(1L).times(2);
        expect(t.getNom()).andReturn("Santé").times(2);



        forumService.creerTheme("Voiture");
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(true);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);

        expect(forumService.getListeTheme()).andReturn(lesthemes).times(2);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getNom()).andReturn("Santé");
        expect(t.getNom()).andReturn("Santé");

        expect(t1.getIdentifiant()).andReturn(2L);
        expect(t1.getIdentifiant()).andReturn(2L);
        expect(t1.getNom()).andReturn("Voiture");
        expect(t1.getNom()).andReturn("Voiture");





        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p,forumService,t,t1);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });


        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();
        clickOn("#chargerListe");
        ListView <Theme> listeTheme = (ListView<Theme>) GuiTest.find("#listeTheme");
        listeTheme.getItems().add(t);
        sleepBetweenActions();
        clickOn("#creerTheme");
        sleepBetweenActions();
        clickOn("#labelTheme");
        sleepBetweenActions();
        write("Voiture");
        sleepBetweenActions();
        clickOn("#validerTheme");

       ListView<Theme> list2=(ListView<Theme>)GuiTest.find("#listeTheme");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
         list2.getItems().addAll(t,t1);
            }
        });
        sleepBetweenActions();

        sleepBetweenActions();

    }

    /**
     * @throws CoupleUtilisateurMDPInconnuException
     * @throws UtilisateurDejaExistantException
     * @throws RoleDejaAttribueException
     * @throws ThemeInexistantException
     * @throws InformationManquanteException
     *
     * Test la création d'un thème pour un Modérateur et affiche un message d'erreur
     * .
     */
    @Test
    public void creerThemeModerateur () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException, ThemeInexistantException, InformationManquanteException, IndividuNonConnecteException {
        p = fabriqueMock.creerMockPersonne();
        Theme t= fabriqueMock.creerThemeForum();
        Collection <Theme> lesthemes=new ArrayList<>();
        Collection<Topic> lesTopics=new ArrayList<>();
        Topic topic = fabriqueMock.creerTopic();

        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes = new ArrayList<>();
        controleur = new Controleur(connexionService, adminService, basiquesOffLineService, stage,forumService);
        expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        expect(connexionService.connexion("Yohan", "123")).andReturn(p);
        expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        expect(p.getIdentifiant()).andReturn(l);
        expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(false);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);
        expect(p.getIdentifiant()).andReturn(l);
        expect(connexionService.estUnAdmin(1)).andReturn(false);
        expect(connexionService.estUnModerateur(1)).andReturn(true);
        expect(p.getIdentifiant()).andReturn(l);

        expect(forumService.getListeTheme()).andReturn(lesthemes).times(2);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getIdentifiant()).andReturn(1L);
        expect(t.getNom()).andReturn("Santé");
        expect(t.getNom()).andReturn("Santé");
        expect(t.getNom()).andReturn("Santé");





        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p,forumService,t);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controleur.run();
            }
        });


        sleepBetweenActions();
        clickOn("#nom");

        write("Yohan");
        sleepBetweenActions();
        clickOn("#boutonValider");
        sleepBetweenActions();
        clickOn("#motDePasse");

        write("123");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();
        clickOn("#chargerListe");
        ListView <Theme> listeTheme = (ListView<Theme>) GuiTest.find("#listeTheme");
        listeTheme.getItems().add(t);
        sleepBetweenActions();
        clickOn("#creerTheme");
        sleepBetweenActions();
    }


    @After
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});


    }





}


