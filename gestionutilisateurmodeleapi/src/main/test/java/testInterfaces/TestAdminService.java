package testInterfaces;

import facade.AdminService;
import facade.erreurs.AdminServiceImpl;
import facade.erreurs.IndividuNonConnecteException;
import facade.erreurs.RoleDejaAttribueException;
import facade.erreurs.UtilisateurDejaExistantException;
import modele.personnes.Personne;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.fail;

public class TestAdminService {

    AdminService adminService = new AdminServiceImpl();

    @Test
    public void creerUtilisateurTest() {

        try {
            adminService.creerUtilisateur(1L, "aaa", "bbb");
            Assert.assertTrue(true);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();

        } catch(
                IndividuNonConnecteException e1){
            Assert.fail();
        }

    }

    @Test(expected = UtilisateurDejaExistantException.class)
    public void creerUtilisateurTestKOUtilisDejaExis() throws UtilisateurDejaExistantException {
        try{
            adminService.creerUtilisateur(1L, "aaa", "bbb");
        } catch (UtilisateurDejaExistantException e) {
            e.printStackTrace();
        } catch(
                IndividuNonConnecteException e1){
            e1.printStackTrace();
        }
        adminService.creerUtilisateur(1L, "aaa", "bbb");
    }

    @Test(expected = IndividuNonConnecteException.class)
    public void creerUtilisateurTestKOIndividuNonCo() throws UtilisateurDejaExistantException {
        try{
            adminService.creerUtilisateur(1L, "aaa", "bbb");
        } catch (UtilisateurDejaExistantException e) {
            e.printStackTrace();
        } catch(
                IndividuNonConnecteException e1){
            e1.printStackTrace();
        }

        /*try{
            adminService.getListeUtilisateur(1L);
        }catch (IndividuNonConnecteException e3){
            e3.printStackTrace();
        }*/
    }







    @Test
    public void  associerRoleUtilisateurTest() {
        try {
            adminService.associerRoleUtilisateur(2L, 1L, "MODERATEUR");
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        } catch (IndividuNonConnecteException e1) {
            Assert.fail();
        }
    }

    @Test(expected = RoleDejaAttribueException.class)
    public void  associerRoleUtilisateurTestKORoleAttribue() throws RoleDejaAttribueException {
        try {
            adminService.associerRoleUtilisateur(2L, 1L, "MODERATEUR");
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            e.printStackTrace();
        } catch (IndividuNonConnecteException e1) {
            e1.printStackTrace();
        }
        adminService.associerRoleUtilisateur(2L, 1L, "MODERATEUR");
    }


}

