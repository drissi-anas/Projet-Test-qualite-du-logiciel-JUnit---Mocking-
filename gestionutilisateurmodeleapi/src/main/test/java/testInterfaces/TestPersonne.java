package testInterfaces;

import facade.erreurs.RoleDejaAttribueException;
import modele.personnes.Personne;
import modele.personnes.PersonneImpl;
import org.junit.Assert;
import org.junit.Test;

import static facade.AdminService.ADMIN;
import static facade.AdminService.BASIQUE;
import static facade.AdminService.MODERATEUR;

public class TestPersonne {

    Personne personne = new PersonneImpl();

    @Test
    public void TestSupprimerRoleAdmin() {
        personne.supprimerRole(ADMIN);
        Assert.assertTrue(true);
    }


    @Test
    public void TestSupprimerRoleUtilisateur() {
        personne.supprimerRole(BASIQUE);
        Assert.assertTrue(true);
    }

    @Test
    public void TestSupprimerRoleModerateur() {
        personne.supprimerRole(MODERATEUR);
        Assert.assertTrue(true);
    }



    @Test
    public void TestAddRoleAdmin(){
       try {
           personne.addRole(ADMIN);
           Assert.assertTrue(true);
       } catch (RoleDejaAttribueException e){
           Assert.fail();
       }
    }

    @Test
    public void TestAddRoleUtilisateur(){
        try {
            personne.addRole(BASIQUE);
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e){
            Assert.fail();
        }
    }

    @Test
    public void TestAddRoleModerateur(){
        try {
            personne.addRole(MODERATEUR);
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e){
            Assert.fail();
        }
    }
}
