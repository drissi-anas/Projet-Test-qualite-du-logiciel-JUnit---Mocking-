package testInterfaces;

import facade.AdminService;
import facade.BasiquesOffLineService;
import facade.ConnexionService;
import facade.FabriqueFacade;
import facade.erreurs.CoupleUtilisateurMDPInconnuException;
import facade.erreurs.IndividuNonConnecteException;
import facade.erreurs.RoleDejaAttribueException;
import facade.erreurs.UtilisateurDejaExistantException;
import modele.personnes.Personne;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static facade.AdminService.*;

public abstract class TestFacadeAll {

        AdminService adminService;
        BasiquesOffLineService basiquesOffLineService;
        ConnexionService connexionService;
        FabriqueFacade fabriqueFacade;

        public TestFacadeAll(FabriqueFacade fabriqueFacade) {
            this.fabriqueFacade = fabriqueFacade;
        }


        @Before
        public void initialisationModele(){
        adminService = fabriqueFacade.getAdminService();
        basiquesOffLineService = fabriqueFacade.getBasiquesOffLineService();
        connexionService = fabriqueFacade.getConnexionService();
        }


         @Test
        public void creerUtilisateurTestOK() throws CoupleUtilisateurMDPInconnuException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            adminService.creerUtilisateur( p.getIdentifiant(), "aaa", "bbb");
            Assert.assertTrue(true);
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        } catch(IndividuNonConnecteException e1){
            Assert.fail();
        }
        }
        @Test(expected = UtilisateurDejaExistantException.class)
        public void creerUtilisateurTestKOUtilisDejaExis() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
                Personne p = connexionService.connexion("admin","admin");
                adminService.creerUtilisateur(p.getIdentifiant(),"aaa", "bbb");
            } catch (UtilisateurDejaExistantException e) {
                e.printStackTrace();
            }
                adminService.creerUtilisateur(3L,"aaa","bbbb");
        }
        @Test(expected = IndividuNonConnecteException.class)
        public void creerUtilisateurTestKOIndividuNonConnecte() throws UtilisateurDejaExistantException {
        try {
            adminService.creerUtilisateur(1L,"aaa", "bbb");
        } catch (IndividuNonConnecteException e){
            e.printStackTrace();
        }
        }







        @Test
        public void  associerRoleUtilisateurTestOK() throws CoupleUtilisateurMDPInconnuException {
            try {
                Personne p = connexionService.connexion("admin", "aaa");
                adminService.associerRoleUtilisateur(p.getIdentifiant(), 1L,BASIQUE);
                Assert.assertTrue(true);
            } catch (RoleDejaAttribueException e) {
                Assert.fail();
            } catch (IndividuNonConnecteException e1) {
                Assert.fail();
            }
        }
        @Test (expected = IndividuNonConnecteException.class)
        public void  associerRoleUtilisateurTestKOIndividuNonConnecte() throws IndividuNonConnecteException, RoleDejaAttribueException {
            try {
                adminService.associerRoleUtilisateur(2L, 1L,BASIQUE);
                Assert.assertTrue(true);
            } catch (IndividuNonConnecteException e1) {
                Assert.fail();
            }
        }
        @Test (expected = RoleDejaAttribueException.class)
        public void  associerRoleUtilisateurTestKORoleDejaAttribue() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException {
            try {
                Personne p = connexionService.connexion("admin", "aaa");
                adminService.associerRoleUtilisateur(p.getIdentifiant(), 1L,BASIQUE);
                Assert.assertTrue(true);
            } catch (RoleDejaAttribueException e1) {
                Assert.fail();
            }
            adminService.associerRoleUtilisateur(1L, 1L,BASIQUE);
        }




        @Test
        public void supprimerClientTestOK() throws IndividuNonConnecteException, CoupleUtilisateurMDPInconnuException {
        try {
            try {
                Personne p = connexionService.connexion("admin", "admin");
                Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"a","a");

            adminService.supprimerClient(p.getIdentifiant(), p2.getIdentifiant());
            Assert.assertTrue(true);

        } catch (UtilisateurDejaExistantException e) {
            e.printStackTrace();
        }
        }   catch (IndividuNonConnecteException e) {
            Assert.fail();
        }
        }
        @Test(expected = IndividuNonConnecteException.class)
        public void supprimerClientTestKOIndividuNonConnecte() throws IndividuNonConnecteException, UtilisateurDejaExistantException {
        try {
            Personne p = adminService.creerUtilisateur(2L,"aaa","bbb");
            adminService.supprimerClient(1L, p.getIdentifiant());
            Assert.assertTrue(true);
        }   catch (IndividuNonConnecteException e) {
            Assert.fail();
        }
        }



         @Test
        public void getListeUtilisateurOK() throws IndividuNonConnecteException, CoupleUtilisateurMDPInconnuException {
        try{
            Personne p = connexionService.connexion("admin", "admin");
            adminService.getListeUtilisateur(p.getIdentifiant());
            Assert.assertTrue(true);
        }   catch (IndividuNonConnecteException e){
            Assert.fail();
        }
        adminService.getListeUtilisateur(1L);
        }
        @Test (expected = IndividuNonConnecteException.class)
        public void getListeUtilisateurKO() throws IndividuNonConnecteException {
        try{
            adminService.getListeUtilisateur(1L);
            Assert.assertTrue(true);
        }   catch (IndividuNonConnecteException e){
            Assert.fail();
        }
        }





        @Test
        public void getUserByIdTest(){
        adminService.getUserById(1L,2L);
        Assert.assertTrue(true);
        }




        @Test
        public void supprimerRoleUtilisateurTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","utilisateur2");
            adminService.supprimerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            Assert.assertTrue(true);
        }



        @Test
        public void changerMotDePasseUtilisateurTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","utilisateur2");

            adminService.changerMotDePasseUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),"123456");
            Assert.assertTrue(true);
        }





        @Test
        public void supprimerUtilisateurTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2", "utilisateur2");
        adminService.supprimerUtilisateur(p.getIdentifiant(),p2.getIdentifiant());
        Assert.assertTrue(true);
        }



        @Test
        public void validerInscriptionTestOK() throws CoupleUtilisateurMDPInconnuException {
           try {
               Personne p = connexionService.connexion("admin", "admin");
               Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "utilisateur");
               adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
               adminService.validerInscription(p.getIdentifiant(), p2.getIdentifiant());
               Assert.assertTrue(true);
           } catch (UtilisateurDejaExistantException e){
               Assert.fail();
           } catch (RoleDejaAttribueException e){
               Assert.fail();
           }
        }
        @Test(expected = UtilisateurDejaExistantException.class)
        public void validerInscriptionTestKOUtilisateurDejaExistant() throws RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "utilisateur");
            adminService.validerInscription(p.getIdentifiant(), p2.getIdentifiant());
            adminService.validerInscription(p.getIdentifiant(), p2.getIdentifiant()); /** A verifier  **/
        } catch (UtilisateurDejaExistantException e) {
            Assert.fail();
        }

        }
        @Test(expected = RoleDejaAttribueException.class)
        public void validerInscriptionTestKORoleDejaAttribueException() throws RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur2", "utilisateur2");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            adminService.validerInscription(p.getIdentifiant(), p2.getIdentifiant());
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        }
            adminService.associerRoleUtilisateur(1L,1L,MODERATEUR);
        }


        @Test
        public void TestgetListeDesDemandesNonTraitees () throws CoupleUtilisateurMDPInconnuException {
        Personne p = connexionService.connexion("admin", "admin");
        adminService.getListeUtilisateur(p.getIdentifiant());
        Assert.assertTrue(true);
        }

        @Test
        public void refuserInscriptionTestAdmin() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur3", "utilisateur3");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
            adminService.refuserInscription(p.getIdentifiant(),p2.getIdentifiant());
            Assert.assertTrue(true);
        }
        @Test
        public void refuserInscriptionTestModerateur() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur4", "utilisateur4");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        adminService.refuserInscription(p.getIdentifiant(),p2.getIdentifiant());
        Assert.assertTrue(true);
        }
        @Test
        public void refuserInscriptionTestBasique() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur5", "utilisateur5");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        adminService.refuserInscription(p.getIdentifiant(),p2.getIdentifiant());
        Assert.assertTrue(true);
        }


    /**
     *     -------------------------------------------------------   Test de l'interface Connexion Service   -------------------------------------------------------
     */


    @Test
    public void connexionTestOK() throws UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "123");
            connexionService.connexion(p2.getNom(), p2.getMdp());
            Assert.assertTrue(true);
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
        }
    }
