package Version1;

import fr.ulille.but.sae_s2_2024.*;

/**
 * La classe Structure représente une structure liée à une ville (comme une gare, un aéroport, etc.).
 */
public class Structure implements Lieu {
    /** Le nom de la structure */
    private final String nom;

    /**
     * Constructeur de la classe Structure.
     * @param nom Le nom de la structure.
     */
    public Structure(String nom) {
        this.nom = nom;
    }

    /**
     * Obtient le nom de la structure.
     * @return Le nom de la structure.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Renvoie une représentation textuelle de la structure sous forme de chaîne de caractères.
     * @return Une représentation textuelle de la structure.
     */
    @Override
    public String toString() {
        return nom;
    }
}
