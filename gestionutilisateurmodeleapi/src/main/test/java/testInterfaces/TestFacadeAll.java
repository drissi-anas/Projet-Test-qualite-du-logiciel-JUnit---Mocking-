package testInterfaces;

import facade.AdminService;
import facade.BasiquesOffLineService;
import facade.ConnexionService;
import facade.FabriqueFacade;
import facade.erreurs.*;
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
        Personne personne;

        public TestFacadeAll(FabriqueFacade fabriqueFacade) {
            this.fabriqueFacade = fabriqueFacade;
        }


        @Before
        public void initialisationModele(){
        adminService = fabriqueFacade.getAdminService();
        basiquesOffLineService = fabriqueFacade.getBasiquesOffLineService();
        connexionService = fabriqueFacade.getConnexionService();
        personne = fabriqueFacade.getPersonne();
        }
    /**
     *     -------------------------------------------------------   Test de l'interface AdminService   -------------------------------------------------------
     */

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
                adminService.creerUtilisateur(3L,"aaa","bbb");
        }
        @Test(expected = IndividuNonConnecteException.class)
        public void creerUtilisateurTestKOIndividuNonConnecte() throws UtilisateurDejaExistantException {
        try {
            adminService.creerUtilisateur(1L,"aaa", "bbb");
        } catch (IndividuNonConnecteException e){
            e.printStackTrace();
        }
        }
        @Test(expected =InformationManquanteException.class)
        public void creerUtilisateurKONomNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
             Personne p = connexionService.connexion("admin","admin");
             adminService.creerUtilisateur(p.getIdentifiant(),null,"bbb");
        }
        @Test(expected =InformationManquanteException.class)
        public void creerUtilisateurKOMdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        Personne p = connexionService.connexion("admin","admin");
        adminService.creerUtilisateur(p.getIdentifiant(),"aaa",null);
        }



        @Test
        public void  associerRoleUtilisateurTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
            try {
                Personne p = connexionService.connexion("admin", "aaa");
                Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
                adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
                Assert.assertTrue(true);
            } catch (RoleDejaAttribueException e) {
                Assert.fail();
            } catch (IndividuNonConnecteException e1) {
                Assert.fail();
            }
        }
        @Test (expected = IndividuNonConnecteException.class)
        public void  associerRoleUtilisateurTestKOIndividuNonConnecte() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
            try {
                Personne p = connexionService.connexion("admin", "aaa");
                Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
                connexionService.deconnexion(p.getIdentifiant());
                adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),BASIQUE);
            } catch (IndividuNonConnecteException e1) {
                e1.printStackTrace();
            }
        }
        @Test (expected = RoleDejaAttribueException.class)
        public void  associerRoleUtilisateurTestKORoleDejaAttribue() throws IndividuNonConnecteException, RoleDejaAttribueException, CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
            Personne p = connexionService.connexion("admin", "aaa");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
            try {
                adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),BASIQUE);
            } catch (RoleDejaAttribueException e1) {
                e1.printStackTrace();
            }
                adminService.associerRoleUtilisateur(p.getIdentifiant(), p2.getIdentifiant(),BASIQUE);
        }
        @Test (expected = InformationManquanteException.class)
        public void  associerRoleUtilisateurTestKORoleNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
            Personne p = connexionService.connexion("admin", "aaa");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
             adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),null);

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
        public void getListeUtilisateurKO() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try{
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"a","a");
            connexionService.connexion("a","a");
            connexionService.deconnexion(p2.getIdentifiant());
            adminService.getListeUtilisateur(p2.getIdentifiant());
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
        @Test(expected = InformationManquanteException.class)
        public void changerMotDePasseKOMdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","utilisateur2");
            adminService.changerMotDePasseUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),null);
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
        public void getListeDesDemandesNonTraiteesADMIN () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"admin2","admin2");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
        adminService.getListeDesDemandesNonTraitees(p2.getIdentifiant());
        Assert.assertTrue(true);
        }
        @Test
        public void getListeDesDemandesNonTraiteesMODERATEUR () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        adminService.getListeDesDemandesNonTraitees(p2.getIdentifiant());
        Assert.assertTrue(true);
        }
        @Test
        public void getListeDesDemandesNonTraiteesBASIQUE () throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur","utilisateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        adminService.getListeDesDemandesNonTraitees(p2.getIdentifiant());
        Assert.fail();
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
    public void connexionTestOK() throws UtilisateurDejaExistantException, RoleDejaAttribueException {
        try {
            Personne p = connexionService.connexion("admin", "admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "123");
            adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
            connexionService.connexion(p2.getNom(), p2.getMdp());
            Assert.assertTrue(true);
        } catch (CoupleUtilisateurMDPInconnuException e) {
            Assert.fail();
        }
    }
    @Test(expected = CoupleUtilisateurMDPInconnuException.class)
    public void connexionTestKOCoupleUtilisateurMDPInconnu() throws UtilisateurDejaExistantException, RoleDejaAttribueException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur2","utilisateur2");
            adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
            connexionService.connexion(p2.getNom(), "nana");
        } catch (CoupleUtilisateurMDPInconnuException e){
            e.printStackTrace();
        }
    }
    @Test (expected = InformationManquanteException.class)
    public void connexionKOPseudoNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "123");
        adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
        connexionService.connexion(null,p2.getMdp());
    }
    @Test (expected = InformationManquanteException.class)
    public void connexionKOMdpNull() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin", "admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(), "utilisateur", "123");
        adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
        connexionService.connexion(p2.getNom(),null);
    }


    @Test
    public void deconnexionTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur3","utilisateur3");
            adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
            connexionService.connexion(p2.getNom(),p2.getMdp());
            connexionService.deconnexion(p2.getIdentifiant());
            Assert.assertTrue(true);
    }

    @Test
    public void estUnUtilisateurConnuTestOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"utilisateur4","utilisateur4");
        adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
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
        adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
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
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKORoleNull() {
        basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur",null);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKORoleVide() {
        basiquesOffLineService.posterDemandeInscription("utilisateur","utilisateur","");
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKOPseudoNull() {
        basiquesOffLineService.posterDemandeInscription(null,"utilisateur",BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKOPseudoVide() {
        basiquesOffLineService.posterDemandeInscription("","utilisateur",BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKOMdpNull() {
        basiquesOffLineService.posterDemandeInscription("utilisateur",null,BASIQUE);
    }
    @Test(expected = InformationManquanteException.class)
    public void posterDemandeInscriptionBasiqueKOMdpVide() {
        basiquesOffLineService.posterDemandeInscription("utilisateur","",BASIQUE);
    }




    @Test
    public void posterDemandeInscriptionModerateurTestOK()  {
        basiquesOffLineService.posterDemandeInscription("moderateur","moderateur",MODERATEUR);
        Assert.assertTrue(true);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKORoleNull()  {
        basiquesOffLineService.posterDemandeInscription("moderateur","moderateur",null);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKORoleVide()  {
        basiquesOffLineService.posterDemandeInscription("moderateur","moderateur","");
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKOPseudoNull()  {
        basiquesOffLineService.posterDemandeInscription(null,"moderateur",MODERATEUR);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKOPseudoVide()  {
        basiquesOffLineService.posterDemandeInscription("","moderateur",MODERATEUR);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKOMdpNull()  {
        basiquesOffLineService.posterDemandeInscription("moderateur",null,MODERATEUR);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionModerateurKOMdpVide()  {
        basiquesOffLineService.posterDemandeInscription("moderateur","",MODERATEUR);
    }



    @Test
    public void posterDemandeInscriptionAdminTestOK()  {
        basiquesOffLineService.posterDemandeInscription("admin","admin",ADMIN);
        Assert.assertTrue(true);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKORoleNull()  {
        basiquesOffLineService.posterDemandeInscription("admin","admin",null);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKORoleVide()  {
        basiquesOffLineService.posterDemandeInscription("admin","admin","");
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKOPseudoNull()  {
        basiquesOffLineService.posterDemandeInscription(null,"admin",null);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKOPseudoVide()  {
        basiquesOffLineService.posterDemandeInscription("","admin","");
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKOMdpoNull()  {
        basiquesOffLineService.posterDemandeInscription("admin",null,null);
    }
    @Test (expected = InformationManquanteException.class)
    public void posterDemandeInscriptionAdminKOMdpoVide()  {
        basiquesOffLineService.posterDemandeInscription("admin","",null);
    }

    /**
     *     -------------------------------------------------------   Test de l'interface Personne    -------------------------------------------------------
     */


    @Test
    public void supprimerRoleModerateur() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
        connexionService.connexion(p2.getNom(),p2.getMdp());
        p2.getRoles(); /** Récupere la liste des Roles de l'utilisateur **/
        p2.hasRole(MODERATEUR);
        p2.supprimerRole(MODERATEUR); /** Supprime le role MODERATEUR **/
    }
    @Test
    public void supprimerRoleBasique() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
        adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
        connexionService.connexion(p2.getNom(),p2.getMdp());
        p2.getRoles();
        p2.hasRole(BASIQUE);
        p2.supprimerRole(BASIQUE);
        Assert.assertTrue(true);
    }
    @Test
    public void supprimerRoleAdmin() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException, RoleDejaAttribueException {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"admin2","admin2");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
        adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
        connexionService.connexion(p2.getNom(),p2.getMdp());
        p2.getRoles();
        p2.hasRole(ADMIN);
        personne.supprimerRole(ADMIN);
        Assert.assertTrue(true);
    }




    @Test
    public void addRoleModerateurOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur234");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p2.getNom(),p2.getMdp());

            /**  p2.getRoles();
             if (p.hasRole(MODERATEUR) == false) **/

            p2.addRole(MODERATEUR);
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            e.printStackTrace();
        }
    }
    @Test(expected = RoleDejaAttribueException.class)
    public void addRoleModerateurKORoleDejaAttribue() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur234");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
            adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p2.getNom(),p2.getMdp());

             p2.getRoles();
             p2.hasRole(MODERATEUR);
             p2.addRole(MODERATEUR);
        } catch (RoleDejaAttribueException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void addRoleBasiqueOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException{
        try {
        Personne p = connexionService.connexion("admin","admin");
        Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur132");
        adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),MODERATEUR);
        adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
        connexionService.deconnexion(p.getIdentifiant());
        connexionService.connexion(p2.getNom(),p2.getMdp());
        p2.addRole(BASIQUE);
        Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        }
    }@Test (expected = RoleDejaAttribueException.class)
    public void addRoleBasiqueKORoleDejaAttribue() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException{
        try {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"moderateur","moderateur345");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p2.getNom(),p2.getMdp());

            p2.getRoles();
            p2.hasRole(BASIQUE);
            p2.addRole(BASIQUE);
        } catch (RoleDejaAttribueException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void addRoleAdminOK() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"basique","basique789");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),BASIQUE);
            adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p2.getNom(),p2.getMdp());

            p2.getRoles();
            p2.hasRole(ADMIN);
            p2.addRole(ADMIN);
            Assert.assertTrue(true);
        } catch (RoleDejaAttribueException e) {
            Assert.fail();
        }
    }
    @Test (expected = RoleDejaAttribueException.class)
    public void addRoleAdminKORoleDejaAttribueException() throws CoupleUtilisateurMDPInconnuException, UtilisateurDejaExistantException {
        try {
            Personne p = connexionService.connexion("admin","admin");
            Personne p2 = adminService.creerUtilisateur(p.getIdentifiant(),"admin","admin1234");
            adminService.associerRoleUtilisateur(p.getIdentifiant(),p2.getIdentifiant(),ADMIN);
            adminService.validerInscription(p.getIdentifiant(),p2.getIdentifiant());
            connexionService.deconnexion(p.getIdentifiant());
            connexionService.connexion(p2.getNom(),p2.getMdp());

            p2.getRoles();
            p2.hasRole(ADMIN);
            p2.addRole(ADMIN);
        } catch (RoleDejaAttribueException e) {
            e.printStackTrace();
        }
    }




}



