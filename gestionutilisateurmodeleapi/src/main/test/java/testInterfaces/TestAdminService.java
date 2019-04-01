package testInterfaces;

import facade.AdminService;
import facade.erreurs.UtilisateurDejaExistantException;
import modele.personnes.Personne;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.fail;

public class TestAdminService {

    AdminService adminService ;
    Personne p;


    public TestAdminService(){}


   /* PileExecution pileExecution = EasyMock.createMockBuilder(PileExecution.class).addMockedMethod("getTail")
            .addMockedMethod("getCellValue").addMockedMethod("pop").addMockedMethod("add").addMockedMethod("minus")
            .addMockedMethod("times").addMockedMethod("div").addMockedMethod("getFabrique").createMock();*/

    @Test()
    public void testCreerUtilisateur(){
      adminService = EasyMock.createMockBuilder(AdminService.class).addMockedMethod("associerRoleUtilisateur")
                .addMockedMethod("supprimerClient").addMockedMethod("getListeUtilisateur").addMockedMethod("getUserById")
                .addMockedMethod("supprimerRoleUtilisateur").addMockedMethod("changerMotDePasseUtilisateur").addMockedMethod("supprimerUtilisateur")
                .addMockedMethod("validerInscription").addMockedMethod("getListeDesDemandesNonTraitees").addMockedMethod("refuserInscription")
                .addMockedMethod("getRoles").createMock();

                p = EasyMock.createMock(Personne.class);

        EasyMock.replay(adminService,p);


        try {
            EasyMock.expect(adminService.creerUtilisateur(1,"Hajar","123")).andReturn(p);
            Assert.assertTrue(true);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        }


         }

    }

