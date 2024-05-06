import fr.ulille.but.sae_s2_2024.*;

public class Arrete implements Trancon {
    private Lieu depart;
    private Lieu arrivee;
    private ModaliteTransport modaliteTransport;


    public Arrete(Lieu depart, Lieu arrivee, ModaliteTransport modaliteTransport) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.modaliteTransport = modaliteTransport;
    }

    @Override
    public Lieu getDepart() {
        return depart;
    }

    @Override
    public Lieu getArrivee() {
        return arrivee;
    }

    @Override
    public ModaliteTransport getModalite() {
        return modaliteTransport;
    }

    @Override
    public String toString() {
        return depart + "" + arrivee;
    }
}