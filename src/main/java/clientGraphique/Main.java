package clientGraphique;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Permet de lancer l'interface graphique du client.
 */
public class Main extends Application {


    /**
     * Permet de lancer l'application client.
     *
     * @param args arguments de ligne de commande.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Instancie la vue, le modèle et les relie avec un contrôleur puis démarre l'interface graphique.
     *
     * @param primaryStage la fenêtre principale de l'application.
     */
    @Override
    public void start(Stage primaryStage) {
        Modele leModele = new Modele();
        Vue laVue = new Vue();
        new Controleur(leModele, laVue);
        Scene scene = new Scene(laVue,800,500);

        primaryStage.setTitle("Inscription UdeM");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}
