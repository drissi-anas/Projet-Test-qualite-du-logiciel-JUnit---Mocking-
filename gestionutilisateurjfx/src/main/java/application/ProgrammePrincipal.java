package application;

import controleur.Controleur;
import facade.AdminService;
import facade.BasiquesOffLineService;
import facade.ConnexionService;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by YohanBoichut on 05/12/2016.
 */
public class ProgrammePrincipal extends Application{


    @Override
    public void start(Stage primaryStage) throws Exception {
        AdminService adminService = null;
        BasiquesOffLineService basiquesOffLineService = null;
        ConnexionService connexionService = null;

        Controleur controleur = new Controleur(connexionService,adminService,basiquesOffLineService,primaryStage);
        controleur.run();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
