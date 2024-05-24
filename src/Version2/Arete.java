package Version2;

import java.util.EnumMap;
import java.util.Map;

import fr.ulille.but.sae_s2_2024.*;

/**
 * La classe Arete représente une liaison entre deux structures.
 */
public class Arete implements Trancon {
    /** La structure de départ de l'arête */
    private Structure depart;

    /** La structure d'arrivée de l'arête */
    private Structure arrivee;

    /** La modalité de transport associée à l'arête */
    private ModaliteTransport modalite;

    /** Les différents types de coûts associés à l'arête */
    private final Map<TypeCout, Double> couts;

    /**
     * Constructeur de la classe Arete.
     * @param depart La structure de départ de l'arête.
     * @param arrivee La structure d'arrivée de l'arête.
     * @param modalite La modalité de transport associée à l'arête.
     * @param prix Le coût en prix.
     * @param co2 Le coût en émissions de CO2.
     * @param temps Le coût en temps.
     */
    public Arete(final Structure depart, final Structure arrivee, final ModaliteTransport modalite, final double temps, final double co2, final double prix) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.modalite = modalite;
        this.couts = new EnumMap<>(TypeCout.class);
        this.couts.put(TypeCout.CO2, co2);
        this.couts.put(TypeCout.TEMPS, temps);
        this.couts.put(TypeCout.PRIX, prix);
    }

    /**
     * Constructeur de la classe Arete.
     * @param depart La structure de départ de l'arête.
     * @param arrivee La structure d'arrivée de l'arête.
     * @param modalite La modalité de transport associée à l'arête.
     * @param couts Une map contenant les différents coûts associés à l'arête.
     */
    public Arete(final Structure depart, final Structure arrivee, final ModaliteTransport modalite, final Map<TypeCout, Double> couts) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.modalite = modalite;
        this.couts = couts;
    }

    /**
     * Renvoie la structure de départ de l'arête.
     * @return La structure de départ de l'arête.
     */
    @Override
    public Structure getDepart() {
        return depart;
    }

    /**
     * Renvoie la structure d'arrivée de l'arête.
     * @return La structure d'arrivée de l'arête.
     */
    @Override
    public Structure getArrivee() {
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
    public double getCout(final TypeCout typeCout) {
        return couts.get(typeCout);
    }

    /**
     * Définit la structure de départ de l'arête.
     * @param depart La nouvelle structure de départ.
     */
    public void setDepart(final  Structure depart) {
        this.depart = depart;
    }

    /**
     * Définit la structure d'arrivée de l'arête.
     * @param arrivee La nouvelle structure d'arrivée.
     */
    public void setArrivee(final Structure arrivee) {
        this.arrivee = arrivee;
    }

    /**
     * Définit la modalité de transport associée à l'arête.
     * @param modalite La nouvelle modalité de transport.
     */
    public void setModalite(final ModaliteTransport modalite) {
        this.modalite = modalite;
    }

    /**
     * Obtient les différents coûts associés à l'arête.
     * @return Une map contenant les différents coûts.
     */
    public Map<TypeCout, Double> getCouts() {
        return this.couts;
    }

    /**
     * Renvoie une représentation textuelle de l'arête sous forme de chaîne de caractères.
     * @return Une représentation textuelle de l'arête.
     */
    @Override
    public String toString() {
        return depart + " -> " + arrivee + " [" + modalite + "]";
    }

    
}
