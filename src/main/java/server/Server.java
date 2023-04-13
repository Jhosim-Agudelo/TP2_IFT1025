package server;

import javafx.util.Pair;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * La classe Server permet d'instancier un serveur et de gérer les requêtes du client
 */
public class Server {
    /**
     * La commande "INSCRIRE" du client qui permet de faire l'inscription à un cours.
     */
    public final static String REGISTER_COMMAND = "INSCRIRE";
    /**
     * La commande "CHARGER" du client qui permet de charger une liste de cours.
     */
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    /**
     * Constructeur qui permet d'instancier un objet de type Server et déclare la liste d'événement à gérer.
     *
     * @param port port de connection du serveur.
     * @throws IOException s'il y a une erreur lors la création du serveur.
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * Permet d'ajouter un gestionnaire d'événement à la liste de handlers.
     *
     * @param h un gestionnaire d'événements.
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    /**
     * Avise les gestionnaires d'événement qu'une commande a été recu en appelant leur méthode handle.
     *
     * @param cmd est la commande envoyée par le client au serveur.
     * @param arg est l'argument qui accompagne la commande.
     */
    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     * Écoute continuellement le port du serveur, accepte le client qui se connecte et traite
     * les commandes envoyées par celui-ci.
     *
     * @throws Exception s'il y a un problème de connection, de lecture ou écriture sur le stream ou
     * si la classe lue n'existe pas dans le programme.
     */
    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Écoute la commande du client, la traite et avertis les gestionnaires d'événements.
     *
     * @throws IOException s'il y a une erreur d'entrée dans les données recues du client.
     * @throws ClassNotFoundException si la classe lue n'existe pas dans le programme.
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    /**
     * Traite une commande de chaîne de caractères en la séparant et retourne une paire avec
     * la commande et ses arguments.
     *
     * @param line la chaîne de caractères de la commande à traiter.
     * @return une paire avec la première valeur étant la commande et la deuxième les arguments.
     */
    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     * Termine la connection au serveur du client et les flux de communications.
     *
     * @throws IOException si une erreur se produit lors de la fermeture des ressources.
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     * Prend les commandes recues par le client ainsi que les arguments et appels
     * les méthodes appropriées.
     *
     * @param cmd commande du client.
     * @param arg argument du client.
     */
    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     * Permet de charger la liste des cours offerts pour la session arg d'un fichier de données
     * et renvoie cette liste au client.
     *
     * @param arg la session pour laquelle on veut les cours disponibles.
     * @throws IOException s'il y a une erreur de lecture du fichier ou de l'écriture de l'objet dans le flux.
     */
    public void handleLoadCourses(String arg) {
        ArrayList<Course> listOfCourses = new ArrayList<>();
        try{
            Scanner scan = new Scanner(new File("src/main/java/server/data/cours.txt"));

            while(scan.hasNext()){
                String code = scan.next();
                String name = scan.next();
                String session = scan.next();
                if (session.equals(arg) ) {
                    listOfCourses.add(new Course(name, code, session));
                }
            }
            scan.close();
            objectOutputStream.writeObject(listOfCourses);
        }catch(IOException ex){
            System.out.println("Erreur se produit lors de la lecture du fichier ou " +
                                "de l'écriture de l'objet dans le flux");
        }
    }

    /**
     * Enregistre les informations fournies par le client lors de la demande d'inscription dans un fichier
     * de données et renvoie un message de confirmation s'il n'y a pas d'erreurs.
     *
     * @throws IOException s'il y a une erreur de lecture du fichier ou de l'écriture de l'objet dans le flux.
     * @throws ClassNotFoundException si la classe lue n'existe pas dans le programme.
     */
    public void handleRegistration() {
        try {

            RegistrationForm rf = (RegistrationForm) objectInputStream.readObject();

            BufferedWriter writer = new BufferedWriter(new FileWriter(
                                                "src/main/java/server/data/inscription.txt",true));
            writer.newLine();
            writer.append(rf.getCourse().getSession()+"\t"+rf.getCourse().getCode()+"\t"+rf.getMatricule()+"\t"
                            +rf.getPrenom()+"\t"+rf.getNom()+"\t"+rf.getEmail());
            writer.close();
            objectOutputStream.writeObject("Félicitations! Inscription réussie de "+rf.getPrenom()+
                                            " au cours "+rf.getCourse().getCode());


        }catch (IOException | ClassNotFoundException ex){
            System.out.println("erreur se produit lors de la lecture de l'objet, l'écriture dans un" +
                                "fichier ou dans le flux de sortie.");
        }
    }
}

