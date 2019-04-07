package testapi;

import controleur.Controleur;
import controleur.erreurs.PseudoInexistantJFXException;
import facade.*;
import facade.erreurs.CoupleUtilisateurMDPInconnuException;
import facade.erreurs.RoleDejaAttribueException;
import facade.erreurs.UtilisateurDejaExistantException;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import modele.inscription.InscriptionPotentielle;
import modele.personnes.Personne;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.ComboBoxMatchers;
import vues.FenetrePrincipale;

import javax.xml.soap.Node;
import java.util.ArrayList;
import java.util.Collection;

import static facade.AdminService.ADMIN;
import static facade.AdminService.BASIQUE;
import static facade.AdminService.MODERATEUR;
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


    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;
        fabriqueMock = new FabriqueMockImpl();
        adminService=fabriqueMock.creerMockAdminService();
        basiquesOffLineService=fabriqueMock.creerMockBOS();
        connexionService=fabriqueMock.creerMockConnexionServ();
        forumService=fabriqueMock.creerMockForum();

    }

    @Test
    public void saisieNomTestOK() { /*l'utilisateur saisie un nom valide */

        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.replay(adminService,basiquesOffLineService,connexionService);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
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

    @Test
    public void saisieNomTestCV() { /*l'utilisateur laisse un champ vide */

        EasyMock.expect(connexionService.estUnUtilisateurConnu("")).andReturn(true);
        EasyMock.replay(adminService,basiquesOffLineService,connexionService);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
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


    @Test
    public void saisieMdpOKAdminAndModCV () throws CoupleUtilisateurMDPInconnuException { /*Un utilisateur (admin et moderateur) se connecte avec un  couple login/mdp valide */
        p =fabriqueMock.creerMockPersonne();
        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);

        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
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


    @Test
    public void saisieNomTestKO()  { /*l'utilisateur saisie un nom déjà existant */

        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(false);
        EasyMock.replay(adminService,basiquesOffLineService,connexionService);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
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





    @Test
    public void saisieMdpOKAdminAndMod () throws CoupleUtilisateurMDPInconnuException { /*Un utilisateur (admin et moderateur) se connecte avec un  couple login/mdp valide */
        p =fabriqueMock.creerMockPersonne();
        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","123")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);




        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
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


    @Test
    public void saisiePseudoOKMDPVide () throws CoupleUtilisateurMDPInconnuException { /*Un utilisateur (admin et moderateur) se connecte avec un  couple login/mdp valide */
        p =fabriqueMock.creerMockPersonne();
        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);




        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
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


    @Test
    public void saisieMdpNonModNonAdmin () throws CoupleUtilisateurMDPInconnuException { /*Un utilisateur basique se connecte avec un  couple login/mdp valide */
        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","123")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(false);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(false);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);




        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
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

    @Test
    public void saisieMdpModNonAdmin () throws CoupleUtilisateurMDPInconnuException {/*Un moderateur se connecte avec un  couple login/mdp valide */
        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","123")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(false);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);




        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
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

    @Test
    public void saisieMdpAdminNonMod () throws CoupleUtilisateurMDPInconnuException { /*Un admin (non moderateur) se connecte avec un  couple login/mdp valide */
        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","123")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(false);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);




        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
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

    @Test
    public void saisieMdpKO() throws CoupleUtilisateurMDPInconnuException { /*Un utilisateurse connecte avec un  couple login/mdp invalide */
        long l = 1;
        CoupleUtilisateurMDPInconnuException e= new CoupleUtilisateurMDPInconnuException();
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","123")).andThrow(e.fillInStackTrace());

        EasyMock.replay(adminService,basiquesOffLineService,connexionService);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
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




    @Test
    public void ajouterUtilisateurOK () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException { /*un admin ajoute un nouvel utilisateur */

        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","123")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.creerUtilisateur(1,"Hajar","111")).andReturn(p);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(p.getNom()).andReturn("Hajar");
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);



        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
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
        clickOn("#enregistreUser");
        sleepBetweenActions();
        press(KeyCode.ENTER);

    }



    @Test
    public void ajouterUtilisateurKO () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException { /*Un admin ajoute un utilisateur déjà existant */
        UtilisateurDejaExistantException e =new UtilisateurDejaExistantException();

        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","123")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);

        EasyMock.expect(adminService.creerUtilisateur(1,"Hajar","111")).andThrow(e.fillInStackTrace());
        EasyMock.expect(p.getIdentifiant()).andReturn(l);


        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
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
        clickOn("#enregistreUser");
        sleepBetweenActions();
        press(KeyCode.ENTER);

    }

    @Test
    public void ajouterUtilisateurChampVide () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException { /*un admin ajoute un nouvel utilisateur */

        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","123")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.creerUtilisateur(1,"","")).andReturn(p);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(p.getNom()).andReturn("");
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);



        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
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
        clickOn("#enregistreUser");
        sleepBetweenActions();
        press(KeyCode.ENTER);

    }

    @Test
    public void ajouterUtilisateurChampVidePseudo () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException { /*un admin ajoute un nouvel utilisateur */

        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","123")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.creerUtilisateur(1,"","000")).andReturn(p);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(p.getNom()).andReturn("");
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);



        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
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
        clickOn("#enregistreUser");
        sleepBetweenActions();
        press(KeyCode.ENTER);

    }





    @Test
    public void adminQuitter () throws CoupleUtilisateurMDPInconnuException { /* un admin se connecte puis quitte l'appli */
        UtilisateurDejaExistantException e =new UtilisateurDejaExistantException();

        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","123")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        connexionService.deconnexion(1);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);



        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
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


    @Test
    public void modQuitter () throws CoupleUtilisateurMDPInconnuException { /* un moderateur se connecte puis quitte l'appli */
        UtilisateurDejaExistantException e =new UtilisateurDejaExistantException();

        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","123")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(false);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        connexionService.deconnexion(1);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);



        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
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


    @Test
    public void basicQuitter () throws CoupleUtilisateurMDPInconnuException { /* un utilisateur basique se connecte puis quitte l'appli */
        UtilisateurDejaExistantException e =new UtilisateurDejaExistantException();

        p =fabriqueMock.creerMockPersonne();
        long l = 1;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","123")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(false);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(false);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        connexionService.deconnexion(1);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);



        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
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
    @Test
    public void demandeInscriptionModerateurTestOK()  { /* un utilisateur fait une demande d'inscription en tant que moderateur */


        basiquesOffLineService.posterDemandeInscription("Yohan","123",MODERATEUR);
        EasyMock.expectLastCall();
        EasyMock.replay(adminService, basiquesOffLineService, connexionService);

        controleur = new Controleur(connexionService, adminService, basiquesOffLineService, stage);
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





    @Test
    public void demandeInscriptionAdminTestOK()  { /* un utilisateur fait une demande d'inscription en tant qu'admin */


        basiquesOffLineService.posterDemandeInscription("Yohan","123",ADMIN);
        EasyMock.expectLastCall();
        EasyMock.replay(adminService, basiquesOffLineService, connexionService);

        controleur = new Controleur(connexionService, adminService, basiquesOffLineService, stage);
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

    @Test
    public void demandeInscriptionBasiqueTestOK()  { /* un utilisateur fait une demande d'inscription en tant qu'utlisateur basique */

        basiquesOffLineService.posterDemandeInscription("Yohan","123",BASIQUE);
        EasyMock.expectLastCall();
        EasyMock.replay(adminService, basiquesOffLineService, connexionService);

        controleur = new Controleur(connexionService, adminService, basiquesOffLineService, stage);
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

    @Test
    public void demandeInscriptionAnnuler()  { /*un utlisateur veut faire une demande d'inscription puis annule sa demande */

        EasyMock.replay(adminService, basiquesOffLineService, connexionService);

        controleur = new Controleur(connexionService, adminService, basiquesOffLineService, stage);
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
    @Test
    public void supprimerUser() throws CoupleUtilisateurMDPInconnuException {
        p =fabriqueMock.creerMockPersonne();
        p2= fabriqueMock.creerMockPersonne();
        long l = 1;
        long l2=2;

        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();

        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","123")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);

        //Recupère la liste et ajoute des Users
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(p.getNom()).andReturn("Yohan");
        EasyMock.expect(p.getNom()).andReturn("Yohan");



        EasyMock.expect(p2.getIdentifiant()).andReturn(l2);
        EasyMock.expect(p2.getIdentifiant()).andReturn(l2);
        EasyMock.expect(p2.getIdentifiant()).andReturn(l2);
        EasyMock.expect(p2.getNom()).andReturn("Sema");
        EasyMock.expect(p2.getNom()).andReturn("Sema");
        EasyMock.expect(p2.getIdentifiant()).andReturn(l2);


        //SupprimerUSer
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(p2.getIdentifiant()).andReturn(l2);
        adminService.supprimerUtilisateur(1,2);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);


        EasyMock.replay(adminService,basiquesOffLineService,connexionService,p,p2);

        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);

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
        sleepBetweenActions();

    }

    @Test
    public void traiterDemandeAdminEtModAccept () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        p =fabriqueMock.creerMockPersonne();
        InscriptionPotentielle i= fabriqueMock.creerInscri();
        InscriptionPotentielle i2=fabriqueMock.creerInscri();
        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","123")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);


        //Recupère liste des demandes
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(i.getIdentifiant()).andReturn(1L);
        EasyMock.expect(i.getIdentifiant()).andReturn(1L);
        EasyMock.expect(i.getNom()).andReturn("Hajar");
        EasyMock.expect(i.getNom()).andReturn("Hajar");
        EasyMock.expect(i.getRoleDemande()).andReturn(BASIQUE);
        EasyMock.expect(i.getRoleDemande()).andReturn(BASIQUE);

        //accept
        adminService.validerInscription(l,1L);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(i.getIdentifiant()).andReturn(1L);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(l)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(i.getIdentifiant()).andReturn(1L);
        EasyMock.expect(i.getNom()).andReturn("Hajar");



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
        clickOn("#accept");
        sleepBetweenActions();
        press(KeyCode.ENTER);

    }


    @Test
    public void traiterDemandeKOAdminOKMod () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        p =fabriqueMock.creerMockPersonne();
        InscriptionPotentielle i= fabriqueMock.creerInscri();
        InscriptionPotentielle i2=fabriqueMock.creerInscri();
        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","123")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(false);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);


        //Recupère liste des demandes
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(i.getIdentifiant()).andReturn(1L);
        EasyMock.expect(i.getIdentifiant()).andReturn(1L);
        EasyMock.expect(i.getNom()).andReturn("Hajar");
        EasyMock.expect(i.getNom()).andReturn("Hajar");
        EasyMock.expect(i.getRoleDemande()).andReturn(BASIQUE);
        EasyMock.expect(i.getRoleDemande()).andReturn(BASIQUE);

        //accept
        adminService.validerInscription(l,1L);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(i.getIdentifiant()).andReturn(1L);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(l)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(i.getIdentifiant()).andReturn(1L);
        EasyMock.expect(i.getNom()).andReturn("Hajar");



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
        clickOn("#accept");
        sleepBetweenActions();
        press(KeyCode.ENTER);


    }

    @Test
    public void traiterDemandeAdminEtModRefus () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        p =fabriqueMock.creerMockPersonne();
        InscriptionPotentielle i= fabriqueMock.creerInscri();
        InscriptionPotentielle i2=fabriqueMock.creerInscri();
        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","123")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);


        //Recupère liste des demandes
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(i.getIdentifiant()).andReturn(1L);
        EasyMock.expect(i.getIdentifiant()).andReturn(1L);
        EasyMock.expect(i.getNom()).andReturn("Hajar");
        EasyMock.expect(i.getNom()).andReturn("Hajar");
        EasyMock.expect(i.getRoleDemande()).andReturn(BASIQUE);
        EasyMock.expect(i.getRoleDemande()).andReturn(BASIQUE);

        //accept
        adminService.refuserInscription(l,1L);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(i.getIdentifiant()).andReturn(1L);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(l)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(i.getIdentifiant()).andReturn(1L);
        EasyMock.expect(i.getNom()).andReturn("Hajar");



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

    @Test
    public void traiterDemandeAdminKOModOKRefus () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        p =fabriqueMock.creerMockPersonne();
        InscriptionPotentielle i= fabriqueMock.creerInscri();
        InscriptionPotentielle i2=fabriqueMock.creerInscri();
        long l = 1;
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        controleur= new Controleur(connexionService,adminService,basiquesOffLineService,stage);
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","123")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnAdmin(1)).andReturn(false);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(connexionService.estUnModerateur(1)).andReturn(true);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);


        //Recupère liste des demandes
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(i.getIdentifiant()).andReturn(1L);
        EasyMock.expect(i.getIdentifiant()).andReturn(1L);
        EasyMock.expect(i.getNom()).andReturn("Hajar");
        EasyMock.expect(i.getNom()).andReturn("Hajar");
        EasyMock.expect(i.getRoleDemande()).andReturn(BASIQUE);
        EasyMock.expect(i.getRoleDemande()).andReturn(BASIQUE);

        //accept
        adminService.refuserInscription(l,1L);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(i.getIdentifiant()).andReturn(1L);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(l)).andReturn(ips);
        EasyMock.expect(p.getIdentifiant()).andReturn(l);
        EasyMock.expect(i.getIdentifiant()).andReturn(1L);
        EasyMock.expect(i.getNom()).andReturn("Hajar");



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









    @After
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }



}


