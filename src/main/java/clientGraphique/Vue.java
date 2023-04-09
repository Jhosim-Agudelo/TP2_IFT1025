package clientGraphique;


import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import server.models.Course;

public class Vue  extends BorderPane {

    private int v = 800;
    private int v1 = 500;

    private BorderPane leftSide = new BorderPane();
    private BorderPane rightSide = new BorderPane();

    private Text titreGauche = new Text("Liste des cours");

    private Text titreDroit = new Text("Formulaire d'inscription");

    private HBox menuGauche = new HBox();
    private HBox menuDroit = new HBox();
    private HBox bottomGauche = new HBox();
    private TableView<Course> affichageDeCours = new TableView<>();

    private TableColumn<Course,String> code = new TableColumn<>("Code");
    private TableColumn<Course,String> cours = new TableColumn<>("cours");
    private MenuButton dropdownButton = new MenuButton("Select an option");

    private Button charger = new Button("Charger");

    private Button envoyer = new Button("envoyer");

    private TextField prenomField = new TextField();
    private TextField nomField = new TextField();
    private TextField emailField = new TextField();
    private TextField matriculeField = new TextField();

    private Label prenom = new Label("Prenom");
    private Label nom = new Label("Nom");
    private Label email = new Label("Email");
    private Label matricule = new Label("Matricule");

    private GridPane gridPane = new GridPane();

    public Vue(){
        this.setBackground(new Background(new BackgroundFill(Color.BEIGE,CornerRadii.EMPTY,Insets.EMPTY)));
        this.setLeft(leftSide);
        this.setRight(rightSide);

        leftSide.setStyle("-fx-border-color: #d4c8c8; -fx-border-width: 2px;");
        rightSide.setStyle("-fx-border-color: #d4c8c8; -fx-border-width: 2px;");

        titreGauche.setFont(Font.font("Arial",25));
        titreDroit.setFont(Font.font("Arial",25));
        titreGauche.setTextAlignment(TextAlignment.CENTER);
        titreDroit.setTextAlignment(TextAlignment.CENTER);

        menuGauche.setAlignment(Pos.CENTER);
        leftSide.setTop(menuGauche);
        menuGauche.setMinSize(v/2,50);
        menuGauche.getChildren().add(titreGauche);
        affichageDeCours.getColumns().setAll(code,cours);
        affichageDeCours.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        StackPane centerPane = new StackPane(affichageDeCours);
        centerPane.setPadding(new Insets(0, 10, 10, 10));

        leftSide.setCenter(centerPane);

        bottomGauche.setAlignment(Pos.CENTER);
        leftSide.setBottom(bottomGauche);
        bottomGauche.setSpacing(20);
        bottomGauche.setMinSize(v/2,50);
        bottomGauche.setStyle("-fx-border-color: #d4c8c8; -fx-border-width: 2px;");

        MenuItem option1 = new MenuItem("Automne");
        MenuItem option2 = new MenuItem("Hiver");
        MenuItem option3 = new MenuItem("Ete");

        dropdownButton.getItems().addAll(option1,option2,option3);
        bottomGauche.getChildren().add(dropdownButton);
        bottomGauche.getChildren().add(charger);

        menuDroit.setAlignment(Pos.CENTER);
        rightSide.setTop(menuDroit);
        menuDroit.setPadding(new Insets(10));
        menuDroit.setMinSize(v/2,50);
        menuDroit.getChildren().add(titreDroit);

        rightSide.setTop(menuDroit);
        envoyer.setPrefWidth(100);

        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // labels
        prenom.setFont(Font.font("Arial",15));
        nom.setFont(Font.font("Arial",15));
        email.setFont(Font.font("Arial",15));
        matricule.setFont(Font.font("Arial",15));


        gridPane.add(prenom, 0, 0);
        gridPane.add(prenomField, 1, 0);
        gridPane.add(nom, 0, 1);
        gridPane.add(nomField, 1, 1);
        gridPane.add(email, 0, 2);
        gridPane.add(emailField, 1, 2);
        gridPane.add(matricule, 0, 3);
        gridPane.add(matriculeField, 1, 3);
        gridPane.add(envoyer,1, 4);
        gridPane.setAlignment(Pos.TOP_CENTER);
        GridPane.setHalignment(envoyer, HPos.CENTER);
        rightSide.setCenter(gridPane);



    }

}
