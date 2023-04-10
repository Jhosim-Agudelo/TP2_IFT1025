package clientGraphique;

import com.sun.webkit.Timer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private int v = 800;
    private int v1 = 500;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Modele leModele = new Modele();
        Vue laVue = new Vue();
        Controleur leControleur = new Controleur(leModele,laVue);
        Scene scene = new Scene(laVue,v,v1);

        primaryStage.setTitle("My Application");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}
