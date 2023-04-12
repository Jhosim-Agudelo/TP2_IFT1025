package server;

/**
 * La classe ServerLauncher permet de lancer un serveur
 */
public class ServerLauncher {
    /**
     * Port de connexion du serveur
     */
    public final static int PORT = 1337;

    /**
     * Lance un serveur en créant une instance de la classe serveur
     *
     * @param args Les arguments de la ligne de commande, dans ce cas, ils ne seront pas utilisés.
     */
    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(PORT);
            System.out.println("Server is running...");
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}