package Version1;
import java.util.EnumMap;
import java.util.Map;

import fr.ulille.but.sae_s2_2024.*;

/**
 * La classe Arrete représente une liaison entre deux lieux.
 */
public class Arete implements Trancon {
    /** Le lieu de départ de l'arête */
    private Lieu depart;
    
    /** Le lieu d'arrivée de l'arête */
    private Lieu arrivee;
    
    /** La modalité de transport associée à l'arête */
    private ModaliteTransport modalite;

    /** Les différents types de coûts associés à l'arête */
    private final Map<TypeCout, Double> couts;

    /**
     * Constructeur de la classe Arrete.
     * @param depart Le lieu de départ de l'arête.
     * @param arrivee Le lieu d'arrivée de l'arête.
     * @param modalite La modalité de transport associée à l'arête.
     * @param co2 Le coût en émissions de CO2.
     * @param temps Le coût en temps.
     * @param prix Le coût en prix.
     */
    public Arete(Lieu depart, Lieu arrivee, ModaliteTransport modalite, double co2, double temps, double prix) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.modalite = modalite;
        this.couts = new EnumMap<>(TypeCout.class);
        this.couts.put(TypeCout.CO2, co2);
        this.couts.put(TypeCout.TEMPS, temps);
        this.couts.put(TypeCout.PRIX, prix);
    }

    /**
     * Renvoie le lieu de départ de l'arête.
     * @return Le lieu de départ de l'arête.
     */
    @Override
    public Lieu getDepart() {
        return depart;
    }

    /**
     * Renvoie le lieu d'arrivée de l'arête.
     * @return Le lieu d'arrivée de l'arête.
     */
    @Override
    public Lieu getArrivee() {
        return arrivee;
    }

    /**
     * Renvoie la modalité de transport associée à l'arête.
     * @return La modalité de transport associée à l'arête.
     */
    @Override
    public ModaliteTransport getModalite() {
        return modalite;
    }

    /**
     * Renvoie le coût associé à un type donné.
     * @param typeCout Le type de coût.
     * @return Le coût associé au type donné.
     */
    public double getCout(TypeCout typeCout) {
        return couts.get(typeCout);
    }

    /**
     * Renvoie une représentation textuelle de l'arête sous forme de chaîne de caractères.
     * @return Une représentation textuelle de l'arête.
     */
    @Override
    public String toString() {
        return depart +" -> " + arrivee + "[" + modalite + "]";
    }
}
