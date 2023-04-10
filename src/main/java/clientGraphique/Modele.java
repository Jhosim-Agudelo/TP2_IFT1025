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


public class Modele {


    public ObservableList<Course> affichageDesCours(String selectedTerm)
            throws IOException, ClassNotFoundException {

        Socket clientSocket = new Socket("127.0.0.1", 1337);
        ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());

        os.writeObject("CHARGER "+selectedTerm);
        ArrayList<Course> temp =(ArrayList<Course>) is.readObject();
        return (FXCollections.observableArrayList(temp));

    }

    public static void inscriptionCours(String prenom,String nom,String email,String matricule,Course cours)
            throws IOException, IllegalArgumentException {

        Socket clientSocket = new Socket("127.0.0.1", 1337);
        ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());

        os.writeObject("INSCRIRE");

        RegistrationForm registrationForm = new RegistrationForm(prenom,nom,email,matricule,cours);
        os.writeObject(registrationForm);

    }

    public boolean controleSaisieEmail(String email){
        // regex provient du site https://regexr.com/3e48o
        String regex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(regex);
    }

    public boolean controleSaisieMatricule(String matricule){
        String regex = "^[0-9]{8}";
        return matricule.matches(regex);
    }




}
