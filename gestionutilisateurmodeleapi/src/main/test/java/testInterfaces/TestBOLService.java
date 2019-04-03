package testInterfaces;

import facade.BasiqueOffLineServiceImpl;
import facade.BasiquesOffLineService;
import org.junit.Assert;
import org.junit.Test;

import static facade.AdminService.ADMIN;
import static facade.AdminService.BASIQUE;
import static facade.AdminService.MODERATEUR;

public class TestBOLService {

    BasiquesOffLineService basiquesOffLineService = new BasiqueOffLineServiceImpl();

    @Test
    public void TesterPosterDemandeInscriptionUtilisateur() {
            basiquesOffLineService.posterDemandeInscription("aaa","aaa",BASIQUE);
            Assert.assertTrue(true);
    }

    @Test
    public void TesterPosterDemandeInscriptionModerateur() {
        basiquesOffLineService.posterDemandeInscription("aaa","aaa",MODERATEUR);
        Assert.assertTrue(true);
    }

    @Test
    public void TesterPosterDemandeInscriptionAdmin() {
        basiquesOffLineService.posterDemandeInscription("aaa","aaa",ADMIN);
        Assert.assertTrue(true);
    }
}
