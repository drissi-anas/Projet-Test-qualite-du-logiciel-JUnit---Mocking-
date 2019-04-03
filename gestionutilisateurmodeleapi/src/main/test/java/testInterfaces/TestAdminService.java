package testInterfaces;

import facade.AdminService;
import facade.AdminServiceImpl;
import facade.erreurs.IndividuNonConnecteException;
import facade.erreurs.RoleDejaAttribueException;
import facade.erreurs.UtilisateurDejaExistantException;
import org.junit.Assert;
import org.junit.Test;


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


    @Test
    public void supprimerClientTest(){
        try {
            adminService.supprimerClient(1L, 2L);
            Assert.assertTrue(true);
        } catch (IndividuNonConnecteException e) {
            Assert.fail();
        }
    }


    @Test
    public void getListeUtilisateur(){
        try{
            adminService.getListeUtilisateur(1L);
            Assert.assertTrue(true);
        }catch (IndividuNonConnecteException e){
            Assert.fail();
        }
    }

    @Test
    public void getUserByIdTest(){
        adminService.getUserById(1L,2L);
        Assert.assertTrue(true);

    }

    @Test
    public void supprimerRoleUtilisateurTest(){
        adminService.supprimerRoleUtilisateur(1L,2L,"MODERATEUR");
        Assert.assertTrue(true);
    }


    @Test
    public void changerMotDePasseUtilisateurTest(){
        adminService.changerMotDePasseUtilisateur(1L,2L,"123456");
        Assert.assertTrue(true);

    }


    @Test
    public void supprimerUtilisateurTest(){
        adminService.supprimerUtilisateur(1L,2L);
        Assert.assertTrue(true);


    }

    @Test
    public void validerInscriptionTest(){
        try {
            adminService.validerInscription(1L,2L);
            Assert.assertTrue(true);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        }
    }

    @Test
    public void getListeDesDemandesNonTraiteesTest(){
        adminService.getListeDesDemandesNonTraitees(1L);
        Assert.assertTrue(true);
    }

    @Test
    public void refuserInscriptionTest(){
        adminService.refuserInscription(1L,2L);
        Assert.assertTrue(true);
    }













    ///////////////////////////////////////////////////////////////

/*  @Test(expected = UtilisateurDejaExistantException.class)
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
   }*/



}

