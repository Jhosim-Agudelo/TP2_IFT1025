package clientGraphique;



import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import server.models.Course;
import server.models.RegistrationForm;


public class Vue  extends BorderPane {

    private final TableView<Course> tableDeCours = new TableView<>();

    private final Button charger = new Button("Charger");
    private final ComboBox<String> dropDownButton = new ComboBox<>();
    private final Button envoyer = new Button("envoyer");

    private TextField prenomField = new TextField();
    private TextField nomField = new TextField();
    private TextField emailField = new TextField();
    private TextField matriculeField = new TextField();

    private int v = 800;

    public Vue(){
        this.setBackground(new Background(new BackgroundFill(Color.BEIGE,CornerRadii.EMPTY,Insets.EMPTY)));
        BorderPane leftSide = new BorderPane();
        this.setLeft(leftSide);
        BorderPane rightSide = new BorderPane();
        this.setRight(rightSide);

        leftSide.setStyle("-fx-border-color: #d4c8c8; -fx-border-width: 2px;");
        rightSide.setStyle("-fx-border-color: #d4c8c8; -fx-border-width: 2px;");

        // titles
        Text titreGauche = new Text("Liste des cours");
        titreGauche.setFont(Font.font("Arial",25));
        Text titreDroit = new Text("Formulaire d'inscription");
        titreDroit.setFont(Font.font("Arial",25));
        titreGauche.setTextAlignment(TextAlignment.CENTER);
        titreDroit.setTextAlignment(TextAlignment.CENTER);

        // table view
        HBox menuGauche = new HBox();
        menuGauche.setAlignment(Pos.CENTER);
        leftSide.setTop(menuGauche);
        menuGauche.setMinSize(v/2,50);
        menuGauche.getChildren().add(titreGauche);
        TableColumn<Course, String> code = new TableColumn<>("Code");
        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        TableColumn<Course, String> cours = new TableColumn<>("cours");
        cours.setCellValueFactory(new PropertyValueFactory<>("name"));


        tableDeCours.getColumns().setAll(code, cours);
        tableDeCours.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        StackPane centerPane = new StackPane(tableDeCours);
        centerPane.setPadding(new Insets(0, 10, 10, 10));

        leftSide.setCenter(centerPane);

        // Choix de session + load
        HBox bottomGauche = new HBox();
        bottomGauche.setAlignment(Pos.CENTER);
        leftSide.setBottom(bottomGauche);
        bottomGauche.setSpacing(20);
        bottomGauche.setMinSize(v/2,50);
        bottomGauche.setStyle("-fx-border-color: #d4c8c8; -fx-border-width: 2px;");

        // bouton a options

        dropDownButton.setPromptText("Choisir session");
        dropDownButton.getItems().addAll("Automne", "Ete","Hiver");

        bottomGauche.getChildren().add(dropDownButton);



        bottomGauche.getChildren().add(charger);

        // Formulaire d'inscription
        HBox menuDroit = new HBox();
        menuDroit.setAlignment(Pos.CENTER);
        rightSide.setTop(menuDroit);
        menuDroit.setPadding(new Insets(10));
        menuDroit.setMinSize(v/2,50);
        menuDroit.getChildren().add(titreDroit);

        rightSide.setTop(menuDroit);
        envoyer.setPrefWidth(100);

        //gridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // labels
        Label prenom = new Label("Prenom");
        prenom.setFont(Font.font("Arial",15));
        Label nom = new Label("Nom");
        nom.setFont(Font.font("Arial",15));
        Label email = new Label("Email");
        email.setFont(Font.font("Arial",15));
        Label matricule = new Label("Matricule");
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

    public Button getButtonCharger(){
        return this.charger;
    }
    public Button getButtonEnvoyer(){
        return this.envoyer;
    }

    public ComboBox<String> getDropDownButton(){
        return dropDownButton;
    }
    public void updateListDeCours(ObservableList<Course> listeDeCours){
        tableDeCours.setItems(listeDeCours);
    }
    public String getPrenomField(){
        return this.prenomField.getText();
    }
    public String getNomField(){
        return this.nomField.getText();
    }
    public String getEmailField(){
        return this.emailField.getText();
    }
    public String getMatriculeField(){
        return this.matriculeField.getText();
    }
    public Course getCourseSelected(){
        return tableDeCours.getSelectionModel().getSelectedItem();
    }

}
