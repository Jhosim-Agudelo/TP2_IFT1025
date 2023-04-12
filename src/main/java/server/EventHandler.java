package server;
/**
 * L'interface EventHandler est utilisée pour gérer les événements de commande reçus par le client.
 */
@FunctionalInterface
public interface EventHandler {
    /**
     * Traîte événement de la commande.
     *
     * @param cmd commande recu par le client.
     * @param arg argument de la commande.
     */
    void handle(String cmd, String arg);
}
