package version2;

import fr.ulille.but.sae_s2_2024.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe Structure représente une structure liée à une ville (comme une gare, un aéroport, etc.).
 */
public class Structure implements Lieu {
    /** Le nom de la structure */
    private final String nom;
    private final String ville;
    private final ModaliteTransport modalite;
    private final int id;

    /** Static fields for managing IDs */
    private static int nextId = 1;
    private static final List<Integer> deletedIds = new ArrayList<>();

    /**
     * Constructeur de la classe Structure.
     * @param ville Le nom de la ville où se trouve la structure.
     * @param modalite La modalité de transport associée à la structure.
     */
    public Structure(final String ville, final ModaliteTransport modalite) {
        this.ville = ville;
        this.modalite = modalite;
        this.nom = Structure.nom(ville, modalite);
        this.id = generateId();
    }

    /**
     * Génère un ID unique pour la structure.
     * @return Un ID unique.
     */
    private static synchronized int generateId() {
        if (!deletedIds.isEmpty()) {
            return deletedIds.remove(0);
        } else {
            return nextId++;
        }
    }

    /**
     * Obtient le nom de la structure.
     * @return Le nom de la structure.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Obtient le nom de la ville où se trouve la structure.
     * @return Le nom de la ville.
     */
    public String getVille() {
        return ville;
    }

    /**
     * Obtient la modalité de transport associée à la structure.
     * @return La modalité de transport.
     */
    public ModaliteTransport getModalite() {
        return modalite;
    }

    /**
     * Obtient l'ID de la structure.
     * @return L'ID de la structure.
     */
    public int getId() {
        return id;
    }

    /**
     * Génère un nom de structure en fonction du nom de base de la ville et de la modalité de transport.
     * @param ville Le nom de base de la ville.
     * @param modalite La modalité de transport associée.
     * @return Le nom généré pour la structure.
     */
    public static String nom(final String ville,final  ModaliteTransport modalite) {
        String nom;
        switch (modalite) {
            case TRAIN:
                nom = "Gare_" + ville;
                break;
            case AVION:
                nom = "Aéroport_" + ville;
                break;
            case BUS:
                nom = "Arrêt_de_bus_" + ville;
                break;
            default:
                nom = ville; // Si la modalité de transport n'est pas donnée, ça retourne simplement le nom d'origine.
        }
        return nom;
    }

    /**
     * Vérifie si la structure est dans la même ville qu'une autre structure.
     * @param structure La structure à comparer.
     * @return true si les structures sont dans la même ville, sinon false.
     */
    public boolean inSameCityAs(final Structure structure) {
        return this.ville.equals(structure.getVille());
    }

    /**
     * Renvoie une représentation textuelle de la structure sous forme de chaîne de caractères.
     * @return Une représentation textuelle de la structure.
     */
    @Override
    public String toString() {
        return ville + " : " + nom + " (ID: " + id + ")";
    }

}
