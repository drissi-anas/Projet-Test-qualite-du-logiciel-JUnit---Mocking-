package testapi;

import controleur.Controleur;
import controleur.erreurs.PseudoInexistantJFXException;
import facade.AdminService;
import facade.BasiquesOffLineService;
import facade.ConnexionService;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.easymock.EasyMock;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import vues.FenetrePrincipale;

import javax.xml.soap.Node;
import static org.testfx.matcher.control.MenuItemMatchers.hasText;

public class TestAPI extends ApplicationTest {

    private static final long SLEEP_TIME = 1000;
    private Stage stage;
    private AdminService adminService;
    private BasiquesOffLineService basiquesOffLineService;
    private ConnexionService connexionService;
    private ConnexionService modelConnexion;


    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;

        Controleur controleurMock = EasyMock.createMockBuilder(Controleur.class)
                .addMockedMethod("getConnexion")
                .addMockedMethod("getMaFenetre").createMock();
        modelConnexion = EasyMock.createMock(ConnexionService.class);


        EasyMock.expect(controleurMock.getConnexionService()).andReturn(modelConnexion).anyTimes();

        EasyMock.expect(controleurMock.getMaFenetre()).andReturn(new FenetrePrincipale()).anyTimes();

        EasyMock.replay(controleurMock);

        controleurMock.run();

    }
    @Test(expected = PseudoInexistantJFXException.class)

    public void saisieNomTest() {

        EasyMock.expect(modelConnexion.estUnUtilisateurConnu("Sema")).andReturn(true);
        EasyMock.replay(modelConnexion);

        TextField txt = find("#nom");
        txt.setText("Sema");

        EasyMock.verify(modelConnexion);

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


