import fr.ulille.but.sae_s2_2024.*;
/**
 * La classe Arrete représente une liaison entre deux lieux.
 */
public class Arrete implements Trancon {
    /** Le lieu de départ de l'arête */
    private Lieu depart;
    
    /** Le lieu d'arrivée de l'arête */
    private Lieu arrivee;
    
    /** La modalité de transport associée à l'arête */
    private ModaliteTransport modaliteTransport;

    /**
     * Constructeur de la classe Arrete.
     *
     * @param depart le lieu de départ de l'arête
     * @param arrivee le lieu d'arrivée de l'arête
     * @param modaliteTransport la modalité de transport associée à l'arête
     */
    public Arrete(Lieu depart, Lieu arrivee, ModaliteTransport modaliteTransport) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.modaliteTransport = modaliteTransport;
    }

    /**
     * Renvoie le lieu de départ de l'arête.
     *
     * @return le lieu de départ de l'arête
     */
    @Override
    public Lieu getDepart() {
        return depart;
    }

    /**
     * Renvoie le lieu d'arrivée de l'arête.
     *
     * @return le lieu d'arrivée de l'arête
     */
    @Override
    public Lieu getArrivee() {
        return arrivee;
    }

    /**
     * Renvoie la modalité de transport associée à l'arête.
     *
     * @return la modalité de transport associée à l'arête
     */
    @Override
    public ModaliteTransport getModalite() {
        return modaliteTransport;
    }

    /**
     * Renvoie une représentation textuelle de l'arête sous forme de chaîne de caractères.
     *
     * @return une représentation textuelle de l'arête
     */
    @Override
    public String toString() {
        return depart + " -> " + arrivee;
    }
}
