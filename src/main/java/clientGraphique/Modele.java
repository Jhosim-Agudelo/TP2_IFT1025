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

/**
 * Contient la logique interne de l'application tel qu'envoyer des requêtes client et le contrôle de saisie.
 */
public class Modele {

    /**
     * Envoi la requête "CHARGER" au serveur qui permet d'obtenir la liste des cours disponible à la session désirée
     * et la retourne pour qu'elle soit affichée dans l'interface graphique.
     *
     * @param session La session pour laquelle on veut obtenir les cours disponibles.
     * @return  Une liste observable de cours.
     * @throws IOException  s'il y a une erreur de lecture ou de l'écriture de l'objet dans le flux.
     * @throws ClassNotFoundException si la classe lue n'existe pas dans le programme.
     */
    public ObservableList<Course> affichageDesCours(String session)
            throws IOException, ClassNotFoundException {

        Socket clientSocket = new Socket("127.0.0.1", 1337);
        ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());

        os.writeObject("CHARGER "+session);
        ArrayList<Course> temp =(ArrayList<Course>) is.readObject();
        return (FXCollections.observableArrayList(temp));

    }

    /**
     * Envoi une demande d'inscription "INSCRIRE" au serveur qui permet d'inscrire l'usager au cours.
     *
     * @param prenom Prénom de l'usager.
     * @param nom Nom de l'usager.
     * @param email Email de l'usager.
     * @param matricule  Matricule de l'usager.
     * @param cours Cours où l'usager désire s'inscrire.
     * @throws IOException s'il y a une erreur de lecture du fichier ou de l'écriture de l'objet dans le flux.
     * @throws ClassNotFoundException si la classe lue n'existe pas dans le programme.
     */
    public static void inscriptionCours(String prenom,String nom,String email,String matricule,Course cours)
            throws IOException, IllegalArgumentException {

        Socket clientSocket = new Socket("127.0.0.1", 1337);
        ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());

        os.writeObject("INSCRIRE");

        RegistrationForm registrationForm = new RegistrationForm(prenom,nom,email,matricule,cours);
        os.writeObject(registrationForm);

    }

    /**
     * Verifie la forme de saise de l'email de l'usager.
     *
     * @param email Email de l'usager.
     * @return vrai si forme est acceptée, faux sinon.
     */
    public boolean controleSaisieEmail(String email){
        // regex provient du site https://regexr.com/3e48o
        String regex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(regex);
    }

    /**
     * Vérifie la forme de saisie de la matricule.
     *
     * @param matricule matricule de l'étudiant.
     * @return vrai si forme est acceptée, faux sinon.
     */
    public boolean controleSaisieMatricule(String matricule){
        String regex = "^[0-9]{8}";
        return matricule.matches(regex);
    }




}
