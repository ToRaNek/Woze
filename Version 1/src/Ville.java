import fr.ulille.but.sae_s2_2024.*;

public class Ville implements Lieu {
    String nom;
    ModaliteTransport modaliteTransport;

    public Ville(String nom, ModaliteTransport modaliteTransport) {
        this.nom = nom;
        this.modaliteTransport = modaliteTransport;
    }

    @Override
    public String toString() {
        return nom;
    }

}