package clientGraphique;


import java.io.IOException;

public class Controleur {

    private final Modele modele;
    private final Vue vue;

    public Controleur(Modele m,Vue v){
        this.modele = m;
        this.vue = v;


        vue.getButtonCharger().setOnAction(event->{
            try {
                handleChargerButtonClick();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        vue.getButtonEnvoyer().setOnAction(event->{
            try {
                handleEnvoyerButtonClick();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void handleChargerButtonClick() throws IOException, ClassNotFoundException {
        this.vue.updateListDeCours(
                this.modele.affichageDesCours(
                        this.vue.getDropDownButton().getValue()
                )
        );
    }

    public void handleEnvoyerButtonClick() throws IOException, ClassNotFoundException {
        boolean checkEmail = this.modele.controleSaisieEmail(this.vue.getEmailField());
        boolean checkMatricule = this.modele.controleSaisieMatricule(this.vue.getMatriculeField());
        if (checkMatricule && checkEmail){
            Modele.inscriptionCours(
                    this.vue.getPrenomField(),
                    this.vue.getNomField(),
                    this.vue.getEmailField(),
                    this.vue.getMatriculeField(),
                    this.vue.getCourseSelected()
            );
            this.vue.successAlert();
        }else{
            this.vue.errorAlert(checkEmail,checkMatricule);
        }
    }


}
