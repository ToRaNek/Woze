package Version1;
import fr.ulille.but.sae_s2_2024.*;

/**
 * La classe Structure représente une ville.
 */
public class Structure implements Lieu {
    /** Le nom de la ville */
    private final String nom;

    /**
     * Constructeur de la classe Structure.
     * @param nom Le nom de la ville.
     */
    public Structure(String nom) {
        this.nom = nom;
    }

    /**
     * Obtient le nom de la ville.
     * @return Le nom de la ville.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Renvoie une représentation textuelle de la ville sous forme de chaîne de caractères.
     * @return Une représentation textuelle de la ville.
     */
    @Override
    public String toString() {
        return nom;
    }
}
