package clientGraphique;


import java.io.IOException;

public class Controleur {

    private final Modele modele;
    private final Vue vue;

    public Controleur(Modele m,Vue v){
        this.modele = m;
        this.vue = v;

        vue.getButtonCharger().setOnAction(e->{
            try {
                handleChargerButtonClick();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
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


}
