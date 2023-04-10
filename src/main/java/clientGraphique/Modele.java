package clientGraphique;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Modele {
    private static ObservableList<Course> listOfCourses = FXCollections.observableArrayList();


    public ObservableList<Course> affichageDesCours(String selectedTerm)
            throws IOException, ClassNotFoundException {

        Socket clientSocket = new Socket("127.0.0.1", 1337);
        ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());

        os.writeObject("CHARGER "+selectedTerm);
        ArrayList<Course> temp =(ArrayList<Course>) is.readObject();
        listOfCourses = (FXCollections.observableArrayList(temp));
        return listOfCourses;

    }

    public static void inscriptionCours(String prenom,String nom,String email,String matricule,Course cours)
            throws IOException, IllegalArgumentException, ClassNotFoundException {

        Socket clientSocket = new Socket("127.0.0.1", 1337);
        ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());

        os.writeObject("INSCRIRE");

        RegistrationForm registrationForm = new RegistrationForm(prenom,nom,email,matricule,cours);
        os.writeObject(registrationForm);

    }




}
