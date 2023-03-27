package client;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static String messagePrincipal = "*** Bienvenue au portail d'inscription de cours de l'UDEM***\n" +
            "Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:\n" +
            "1. Automne\n2. Hiver\n3. Ete";

    public static void main(String[] args){
        try {
            Socket clientSocket = new Socket("127.0.0.1",1337);
            OutputStreamWriter os = new OutputStreamWriter(clientSocket.getOutputStream());
            BufferedWriter writer = new BufferedWriter(os);
            System.out.println(messagePrincipal);





        }catch (IOException ex){
            ex.printStackTrace();
        }

    }

}
