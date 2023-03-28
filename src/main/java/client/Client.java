package client;


import server.models.Course;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {


    static String messagePrincipal = "*** Bienvenue au portail d'inscription de cours de l'UDEM***\n" +
            "Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:\n" +
            "1. Automne\n2. Hiver\n3. Ete";


    public static void affichageDesCours(ObjectOutputStream os,
                                  ObjectInputStream is,
                                  Scanner scan) throws IOException, ClassNotFoundException {
        System.out.println(messagePrincipal);

        while (scan.hasNext()) {
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
            if (scan.next() == "1"){
                affichageDesCours(os,is,scan);
            }
        }

    }

    public static void main(String[] args){
        try {
            Socket clientSocket = new Socket("127.0.0.1", 1337);
            ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());
            Scanner scan = new Scanner(System.in);

            affichageDesCours(os,is,scan);

        }catch (IOException ex){
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }



}
