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

public class Server {

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

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

    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     Lire un fichier texte contenant des informations sur les cours et les transofmer en liste d'objets 'Course'.
     La méthode filtre les cours par la session spécifiée en argument.
     Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     @param arg la session pour laquelle on veut récupérer la liste des cours
     @throws Exception si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux
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
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream',
     l'enregistrer dans un fichier texte et renvoyer un message de confirmation au client.
     @throws Exception si une erreur se produit lors de la lecture de l'objet, l'écriture dans une
     fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        try {

            RegistrationForm rf = (RegistrationForm) objectInputStream.readObject();

            BufferedWriter writer = new BufferedWriter(new FileWriter(
                                                "src/main/java/server/data/inscription.txt",true));
            writer.newLine();
            writer.append(rf.getCourse().getSession()+"\t"+rf.getCourse().getCode()+"\t"+rf.getMatricule()+"\t"
                            +rf.getPrenom()+"\t"+rf.getNom()+"\t"+rf.getEmail());
            writer.newLine();
            writer.close();
            objectOutputStream.writeObject("Félicitations! Inscription réussie de "+rf.getPrenom()+
                                            " au cours "+rf.getCourse().getCode());


        }catch (IOException | ClassNotFoundException ex){
            System.out.println("erreur se produit lors de la lecture de l'objet, l'écriture dans un" +
                                "fichier ou dans le flux de sortie.");
        }
    }
}

