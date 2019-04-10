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
            } catch (IndividuNonConnecteException e1) {
                e1.printStackTrace();
            }
        }
        @Test (expected = RoleDejaAttribueException.class)
        public void  associerRoleUtilisateurTestKORoleDejaAttribue() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException {
            Personne p = connexionService.connexion("admin", "aaa");
            try {
                adminService.associerRoleUtilisateur(p.getIdentifiant(), 1L,BASIQUE);
            } catch (RoleDejaAttribueException e1) {
                e1.printStackTrace();
            }
                adminService.associerRoleUtilisateur(p.getIdentifiant(), 1L,BASIQUE);
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
            Assert.fail();
        }
        }   catch (IndividuNonConnecteException e) {
            Assert.fail();
        }
        }
        @Test(expected = IndividuNonConnecteException.class)
        public void supprimerClientTestKOIndividuNonConnecte() throws UtilisateurDejaExistantException,CoupleUtilisateurMDPInconnuException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"a","a");
            connexionService.deconnexion(p.getIdentifiant());
            adminService.supprimerClient(p.getIdentifiant(), p2.getIdentifiant());

        } catch (IndividuNonConnecteException e) {
            e.printStackTrace();
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
        }

        @Test (expected = IndividuNonConnecteException.class)
        public void getListeUtilisateurKO()  {
        try{
            adminService.getListeUtilisateur(1L);
            Assert.assertTrue(true);
        }   catch (IndividuNonConnecteException e){
            e.printStackTrace();
        }
        }





        @Test
        public void getUserByIdTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
            adminService.getUserById(p.getIdentifiant(),p2.getIdentifiant());
            Assert.assertTrue(true);
        }




        @Test
        public void supprimerRoleUtilisateurTest() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","utilisateur2");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
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
    public void validerInscriptionTestKOUtilisateurDejaExistant() throws RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "utilisateur");

        try {
            adminService.validerInscription(p.getIdentifiant(), p2.getIdentifiant());

        } catch (UtilisateurDejaExistantException e) {
            e.printStackTrace();
        }
        adminService.validerInscription(p.getIdentifiant(), p2.getIdentifiant());
    }

    @Test(expected = RoleDejaAttribueException.class)
    public void validerInscriptionTestKORoleDejaAttribueException() throws RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur2", "utilisateur2");
        try {

            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            adminService.validerInscription(p.getIdentifiant(), p2.getIdentifiant());
        } catch (RoleDejaAttribueException e) {
            e.printStackTrace();
        }
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
    }


        @Test
        public void TestgetListeDesDemandesNonTraitees () throws CoupleUtilisateurMDPInconnuException {
        Personne p = connexionService.connexion("admin", "admin");
        adminService.getListeDesDemandesNonTraitees(p.getIdentifiant());
        Assert.assertTrue(true);
        }

        /**
        @Test
        public void refuserInscriptionTestAdmin() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
            basiquesOffLineService.posterDemandeInscription("utiladmin","aaaaa",ADMIN);
            Personne p1 = connexionService.connexion("admin", "admin");
            adminService.getListeDesDemandesNonTraitees(p1.getIdentifiant());
            Personne p2 =  adminService.creerUtilisateur(p1.getIdentifiant(),"utiladmin","aaaaa");
            adminService.refuserInscription(p1.getIdentifiant(),p2.getIdentifiant());
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

        **/

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
    @Test(expected = CoupleUtilisateurMDPInconnuException.class)
    public void connexionTestKOCoupleUtilisateurMDPInconnu() throws  UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","utilisateur2");
            connexionService.connexion(p2.getNom(), p2.getMdp());
        } catch (CoupleUtilisateurMDPInconnuException e){
            e.printStackTrace();
        }
    }


    @Test
    public void deconnexionTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur3","utilisateur3");
            connexionService.connexion(p2.getNom(), p2.getMdp());
            connexionService.deconnexion(p2.getIdentifiant());
            Assert.assertTrue(true);
    }

    @Test
    public void estUnUtilisateurConnuTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur4","utilisateur4");
        connexionService.connexion(p2.getNom(), p2.getMdp());
        connexionService.estUnUtilisateurConnu(p2.getNom());
        Assert.assertTrue(true);
    }



    @Test
    public void estUnAdminTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur5","utilisateur5");
        connexionService.connexion(p2.getNom(), p2.getMdp());
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
        connexionService.estUnAdmin(p2.getIdentifiant());
        Assert.assertTrue(true);
    }



    @Test
    public void estUnModerateurTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur6","utilisateur6");
        connexionService.connexion(p2.getNom(), p2.getMdp());
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        connexionService.estUnModerateur(p2.getIdentifiant());
        Assert.assertTrue(true);
    }

    /**
     *     -------------------------------------------------------   Test de l'interface BasiquesOffLineService    -------------------------------------------------------
     */

    @Test
    public void posterDemandeInscriptionBasiqueTestOK()  {
        basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur",BASIQUE);
        Assert.assertTrue(true);
    }

    @Test
    public void posterDemandeInscriptionModerateurTestOK()  {
        basiquesOffLineService.posterDemandeInscription("moderateur1","moderateur1",MODERATEUR);
        Assert.assertTrue(true);
    }

    @Test
    public void posterDemandeInscriptionAdminTestOK()  {
        basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur",ADMIN);
        Assert.assertTrue(true);
    }


}



