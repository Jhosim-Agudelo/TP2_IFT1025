package clientGraphique;


import java.io.IOException;

/**
 * Relie le model et la vue de l'application.
 */
public class Controleur {

    private final Modele modele;
    private final Vue vue;

    /**
     * Constructeur du contrôleur qui relie le model et la vue, traite aussi les événements
     * du click du bouton charger et envoyer.
     *
     * @param m le model.
     * @param v la vue.
     * @throws IOException s'il y a une erreur de lecture du fichier ou de l'écriture de l'objet dans le flux.
     * @throws ClassNotFoundException si la classe lue n'existe pas dans le programme.
     */
    public Controleur(Modele m,Vue v){
        this.modele = m;
        this.vue = v;


        vue.getButtonCharger().setOnAction(event->{
            try {
                handleChargerButtonClick();
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("erreur se produit lors de la lecture de l'objet, l'écriture dans un" +
                        "fichier ou dans le flux de sortie.");
            }
        });

        vue.getButtonEnvoyer().setOnAction(event->{
            try {
                handleEnvoyerButtonClick();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("erreur se produit lors de la lecture de l'objet, l'écriture dans un" +
                        "fichier ou dans le flux de sortie.");
            }
        });
    }

    /**
     *  Met à jour la liste des cours affichée dans l'interface graphique selon la valeur courante
     *  de la boîte de selection.
     *
     * @throws IOException s'il y a une erreur de lecture du fichier ou de l'écriture de l'objet dans le flux.
     * @throws ClassNotFoundException si la classe lue n'existe pas dans le programme.
     */
    public void handleChargerButtonClick() throws IOException, ClassNotFoundException {
        this.vue.updateListDeCours(
                this.modele.affichageDesCours(
                        this.vue.getDropDownButton().getValue()
                )
        );
    }

    /**
     * Envoi une requête d'inscription au serveur si le courriel, la matricule sont valides et si un cours a bel et
     * bien été choisi.
     *
     * @throws IOException s'il y a une erreur de lecture du fichier ou de l'écriture de l'objet dans le flux.
     * @throws ClassNotFoundException si la classe lue n'existe pas dans le programme.
     */
    public void handleEnvoyerButtonClick() throws IOException, ClassNotFoundException {
        boolean checkEmail = this.modele.controleSaisieEmail(this.vue.getEmailField());
        boolean checkMatricule = this.modele.controleSaisieMatricule(this.vue.getMatriculeField());
        boolean checkCourseSelection = this.vue.checkCourseSelection();
        if (checkMatricule && checkEmail && checkCourseSelection){
            Modele.inscriptionCours(
                    this.vue.getPrenomField(),
                    this.vue.getNomField(),
                    this.vue.getEmailField(),
                    this.vue.getMatriculeField(),
                    this.vue.getCourseSelected()
            );
            this.vue.successAlert();
        }else{
            this.vue.errorAlert(checkEmail,checkMatricule,checkCourseSelection);
        }
    }


}
