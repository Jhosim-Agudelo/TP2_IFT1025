package client;


import server.models.Course;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {


    static final String messagePrincipal = "*** Bienvenue au portail d'inscription de cours de l'UDEM***\n" +
            "Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:\n" +
            "1. Automne\n2. Hiver\n3. Ete \n>Choix: ";

    static final String messageSecondaire = "Choix: \n1. Consulter les cours offerts pour une autre session" +
            "\n2. Inscription à un cours\n>Choix: ";


    public static void affichageDesCours(Scanner scan) throws IOException, ClassNotFoundException {

        Socket clientSocket = new Socket("127.0.0.1", 1337);
        ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());



            String answer = scan.nextLine();
            switch (answer) {
                case "1":
                    os.writeObject("CHARGER Automne");
                    break;
                case "2":
                    os.writeObject("CHARGER Hiver");
                    break;
                case "3":
                    os.writeObject("CHARGER Ete");
                    break;
            }

            ArrayList<Course> listOfCourses = (ArrayList<Course>) is.readObject();
            String session = listOfCourses.get(0).getSession();
            System.out.println("Les cours offerts pendant la session d'"+session+" sont:");

            int i = 0;
            for(Course c:listOfCourses){
                i++;
                System.out.println(i+". "+c.getCode()+" "+c.getName());
            }


    }

    public static void inscriptionCours() throws IOException {
        Socket clientSocket = new Socket("127.0.0.1", 1337);
        ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());



    }


    public static void main(String[] args){
        try {
            System.out.print(messagePrincipal);
            Scanner scan = new Scanner(System.in);
            while (scan.hasNext()){

                affichageDesCours(scan);
                System.out.print(messageSecondaire);

                String followingAnswer = scan.nextLine();
                if (followingAnswer.equals("1") ){
                    main(args);
                }else{
                    System.out.println("on va s'inscrire");
                }
            }
            scan.close();

        }catch (IOException ex){
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



}
