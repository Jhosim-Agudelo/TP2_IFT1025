package clientGraphique;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Modele leModele = new Modele();
        Vue laVue = new Vue();
        new Controleur(leModele, laVue);
        Scene scene = new Scene(laVue,800,500);

        primaryStage.setTitle("My Application");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}
