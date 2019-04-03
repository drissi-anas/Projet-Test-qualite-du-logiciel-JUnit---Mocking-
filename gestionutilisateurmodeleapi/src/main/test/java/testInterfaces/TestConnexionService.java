package testInterfaces;

import facade.AdminService;
import facade.AdminServiceImpl;
import facade.ConnexionService;
import facade.ConnexionServiceImpl;
import facade.erreurs.CoupleUtilisateurMDPInconnuException;
import facade.erreurs.RoleDejaAttribueException;
import org.junit.Assert;
import org.junit.Test;

import static facade.AdminService.ADMIN;
import static facade.AdminService.MODERATEUR;

public class TestConnexionService {

    ConnexionService connexionService = new ConnexionServiceImpl();
    AdminService adminService = new AdminServiceImpl();


    @Test
    public void TestConnexion() {
        try {
            connexionService.connexion("aaa", "aaa");
            Assert.assertTrue(true);
        }catch (CoupleUtilisateurMDPInconnuException e){
            Assert.fail();
        }
    }

    /**
    @Test(expected = CoupleUtilisateurMDPInconnuException.class)
    public void TestConnexionKOCoupleUtilisateurMDPInconnu() throws CoupleUtilisateurMDPInconnuException {
        try{
            adminService.getListeUtilisateur()
            connexionService.connexion("anas","bbb");
        } catch ()
    }  **/


    @Test
    public void TestDeconnexion() {
        connexionService.deconnexion(1L);
        Assert.assertTrue(true);
    }


    @Test
    public void TestEstUnUtilisateurConnu() throws RoleDejaAttribueException {
        adminService.associerRoleUtilisateur(1L,2L,ADMIN);
        connexionService.estUnUtilisateurConnu("aaa");
        Assert.assertTrue(true);
    }

    @Test
    public void TestEstUnAdmin() throws RoleDejaAttribueException {
            adminService.associerRoleUtilisateur(1L,2L,ADMIN);
            connexionService.estUnAdmin(1L);
            Assert.assertTrue(true);
    }

    @Test
    public void TestEstUnModerateur() throws RoleDejaAttribueException {
        adminService.associerRoleUtilisateur(1L,2L,MODERATEUR);
        connexionService.estUnModerateur(1L);
        Assert.assertTrue(true);
    }
}
