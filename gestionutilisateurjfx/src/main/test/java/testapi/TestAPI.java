package testapi;

import controleur.Controleur;
import controleur.erreurs.PseudoInexistantJFXException;
import facade.*;
import facade.erreurs.CoupleUtilisateurMDPInconnuException;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modele.inscription.InscriptionPotentielle;
import modele.personnes.Personne;
import org.easymock.EasyMock;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import vues.FenetrePrincipale;

import javax.xml.soap.Node;
import java.util.ArrayList;
import java.util.Collection;

import static org.testfx.matcher.control.MenuItemMatchers.hasText;

public class TestAPI extends ApplicationTest {

    private static final long SLEEP_TIME = 2000;
    private Stage stage;
    private AdminService adminService;
    private BasiquesOffLineService basiquesOffLineService;
    private ConnexionService connexionService;
    private FabriqueMock fabriqueMock;
    private Controleur controleur;



    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;
        fabriqueMock = new FabriqueMockImpl();
        adminService=fabriqueMock.creerMockAdminService();
        basiquesOffLineService=fabriqueMock.creerMockBOS();
        connexionService=fabriqueMock.creerMockConnexionServ();

    }


    @Test
    public void saisieNomTestKO() throws InterruptedException {

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

    }


    @Test
    public void saisieNomTestOK() throws InterruptedException {

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
    public void saisieMdpOK () throws CoupleUtilisateurMDPInconnuException {
        Personne p =fabriqueMock.creerMockPersonne();
        InscriptionPotentielle  ip= fabriqueMock.creerInscri();
        Collection<InscriptionPotentielle> ips = new ArrayList<>();
        Collection<Personne> personnes= new ArrayList<>();
        EasyMock.expect(connexionService.estUnUtilisateurConnu("Yohan")).andReturn(true);
        EasyMock.expect(connexionService.connexion("Yohan","bla")).andReturn(p);
        EasyMock.expect(adminService.getListeUtilisateur(1)).andReturn(personnes);
        EasyMock.expect(adminService.getListeDesDemandesNonTraitees(1)).andReturn(ips);
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

        write("bla");
        sleepBetweenActions();
        clickOn("#boutonValidermdp");
        sleepBetweenActions();



    }


        private void sleepBetweenActions() {

            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }

        }
    /* Just a shortcut to retrieve widgets in the GUI. */
    public <T extends Node> T find(final String query) {
        /** TestFX provides many operations to retrieve elements from the loaded GUI. */
        return (T) lookup(query).query();
    }


    }


