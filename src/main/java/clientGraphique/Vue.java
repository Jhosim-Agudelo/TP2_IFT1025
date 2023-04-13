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

/**
 * Définie la partie graphique de l'interface du client, permet de visualiser
 * la liste des cours et le formulaire d'inscription à remplir et est une sous-classe de
 * borderPane.
 */
public class Vue  extends BorderPane {

    private final TableView<Course> tableDeCours = new TableView<>();

    private final Button charger = new Button("Charger");
    private final ComboBox<String> dropDownButton = new ComboBox<>();
    private final Button envoyer = new Button("envoyer");

    private final TextField prenomField = new TextField();
    private final TextField nomField = new TextField();
    private final TextField emailField = new TextField();
    private final TextField matriculeField = new TextField();

    /**
     * Constructeur de la classe Vue, Initialise les éléments visuels de l'application.
     */
    public Vue(){

        this.setBackground(new Background(new BackgroundFill(Color.BEIGE,CornerRadii.EMPTY,Insets.EMPTY)));

        BorderPane leftSide = new BorderPane();
        this.setLeft(leftSide);

        BorderPane rightSide = new BorderPane();
        this.setRight(rightSide);

        leftSide.setStyle("-fx-border-color: #d4c8c8; -fx-border-width: 2px;");
        rightSide.setStyle("-fx-border-color: #d4c8c8; -fx-border-width: 2px;");

        // Titres
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
        menuGauche.setMinSize(400,50);
        menuGauche.getChildren().add(titreGauche);

        TableColumn<Course, String> code = new TableColumn<>("Code");
        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        TableColumn<Course, String> cours = new TableColumn<>("cours");
        cours.setCellValueFactory(new PropertyValueFactory<>("name"));


        tableDeCours.getColumns().setAll(code, cours);
        tableDeCours.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        code.setMinWidth(150);
        cours.setMinWidth(250);

        StackPane centerPane = new StackPane(tableDeCours);
        centerPane.setPadding(new Insets(0, 10, 10, 10));
        leftSide.setCenter(centerPane);

        // Choix de session + load
        HBox bottomGauche = new HBox();
        bottomGauche.setAlignment(Pos.CENTER);
        leftSide.setBottom(bottomGauche);
        bottomGauche.setSpacing(20);
        bottomGauche.setMinSize(400,50);
        bottomGauche.setStyle("-fx-border-color: #d4c8c8; -fx-border-width: 2px;");

        // bouton à options

        dropDownButton.setPromptText("Choisir session");
        dropDownButton.getItems().addAll("Automne", "Ete","Hiver");

        bottomGauche.getChildren().add(dropDownButton);
        bottomGauche.getChildren().add(charger);

        // Formulaire d'inscription
        HBox menuDroit = new HBox();
        menuDroit.setAlignment(Pos.CENTER);
        rightSide.setTop(menuDroit);
        menuDroit.setPadding(new Insets(10));
        menuDroit.setMinSize(400,50);
        menuDroit.getChildren().add(titreDroit);

        rightSide.setTop(menuDroit);
        envoyer.setPrefWidth(100);

        //gridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(20);
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

        GridPane.setHalignment(envoyer, HPos.CENTER);
        gridPane.setAlignment(Pos.TOP_CENTER);
        rightSide.setCenter(gridPane);

    }

    /**
     * Affiche une alerte d'erreur si l'email ou la matricule ne sont pas valides.
     *
     * @param emailalert  boolean représentant s'il y a une erreur dans l'email.
     * @param matriculeAlert boolean représentant s'il y a une erreur dans la matricule.
     */
    public void errorAlert(boolean emailalert,boolean matriculeAlert,boolean selectionAlert){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Error");
        String messageErreurEmail = "le champ 'Email' est invalide!\n";
        String messageErreurMatricule = "le champ 'Matricule' est invalide!\n";
        String messageErreurSelection = "Vous devez sélectionner un cours!\n";
        String messageAffiche= "";

        if (!emailalert){
            messageAffiche += messageErreurEmail;
            this.emailField.setStyle("-fx-border-color: red");
        }
        if (!matriculeAlert){
            messageAffiche += messageErreurMatricule;
            this.matriculeField.setStyle("-fx-border-color: red");
        }
        if (!selectionAlert){
            messageAffiche += messageErreurSelection;
            this.tableDeCours.setStyle("-fx-border-color: red");
        }

        if(!emailalert || !matriculeAlert || !selectionAlert){
            errorAlert.setContentText(messageAffiche);
            errorAlert.showAndWait();
        }
    }

    /**
     * Affiche un message de succès et réinitialise les champs d'entrée de l'usager.
     */
    public void successAlert(){
        Alert messageSucces = new Alert(Alert.AlertType.INFORMATION);
        messageSucces.setTitle("Message");
        messageSucces.setHeaderText("Message");
        messageSucces.setContentText("Felicitations! "+this.prenomField.getText()+" "+this.nomField.getText()+
                " est inscrit(e)\navec succes pour le cours "+getCourseSelected().getName());
        messageSucces.showAndWait();

        prenomField.setText("");
        nomField.setText("");
        emailField.setText("");
        emailField.setStyle(envoyer.getStyle());
        matriculeField.setText("");
        matriculeField.setStyle(envoyer.getStyle());
        tableDeCours.setStyle(envoyer.getStyle());
    }

    /**
     * Renvoi le bouton Charger.
     *
     * @return le bouton charger.
     */
    public Button getButtonCharger(){
        return this.charger;
    }

    /**
     * Renvoi le bouton envoyer.
     *
     * @return bouton envoyer.
     */
    public Button getButtonEnvoyer(){
        return this.envoyer;
    }

    /**
     * Renvoi la boîte de séléction.
     *
     * @return boîte de séléction.
     */
    public ComboBox<String> getDropDownButton(){
        return dropDownButton;
    }

    /**
     * Met à jour la liste des cours dans l'affichage graphique.
     *
     * @param listeDeCours liste contetant les cours de la session demandée.
     */
    public void updateListDeCours(ObservableList<Course> listeDeCours){
        tableDeCours.setItems(listeDeCours);
    }

    /**
     * Retourne la valeur de la chaîne qui est dans le champ du prénom.
     *
     * @return le prénom de l'usager qui a été écrit.
     */
    public String getPrenomField(){
        return this.prenomField.getText();
    }

    /**
     * Retourne la valeur de la chaîne qui est dans le champ du nom.
     *
     * @return nom de l'usager qui a été écrit.
     */
    public String getNomField(){
        return this.nomField.getText();
    }

    /**
     * Retourne la valeur de la chaîne qui est dans le champ de l'email.
     *
     * @return l'email de l'usager qui a été écrit.
     */
    public String getEmailField(){
        return this.emailField.getText();
    }

    /**
     * Retourne la valeur de la chaîne qui est dans le champ de la matricule.
     *
     * @return la matricule de l'usager qui a été écrite.
     */
    public String getMatriculeField(){
        return this.matriculeField.getText();
    }

    /**
     * Retourne le cours qui est choisi dans la table d'affichage.
     *
     * @return le cours qui est sélectionné dans la table des cours.
     */
    public Course getCourseSelected(){
        return tableDeCours.getSelectionModel().getSelectedItem();
    }

    /**
     * Vérifie si un cours a été sélectionné.
     *
     * @return boolean false si l'usager n'a pas sélectionné de cours.
     */
    public boolean checkCourseSelection(){
        return !(tableDeCours.getSelectionModel().getSelectedItem() == null);
    };

}
