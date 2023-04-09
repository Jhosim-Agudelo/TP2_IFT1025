package clientGraphique;

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

        Vue laVue = new Vue();
        Scene scene = new Scene(laVue,800,500);

        primaryStage.setTitle("My Application");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}
