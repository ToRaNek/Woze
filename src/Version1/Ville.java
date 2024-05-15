package Version1;
import fr.ulille.but.sae_s2_2024.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe Ville représente une ville.
 */
public class Ville implements Lieu {
    /** Le nom de la ville */
    private final String nom;

    /** La liste des structures présentes dans la ville */
    private List<Structure> structures;

    /**
     * Constructeur de la classe Ville.
     * @param nom Le nom de la ville.
     */
    public Ville(String nom) {
        this.nom = nom;
        this.structures = new ArrayList<>();
    }

    /**
     * Obtient le nom de la ville.
     * @return Le nom de la ville.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Ajoute une structure à la liste des structures de la ville.
     * @param structure La structure à ajouter.
     */
    public void ajouterStructure(Structure structure) {
        structures.add(structure);
    }

    /**
     * Obtient la liste des structures présentes dans la ville.
     * @return La liste des structures.
     */
    public List<Structure> getStructures() {
        return structures;
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
