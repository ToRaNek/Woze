package Version1;

import fr.ulille.but.sae_s2_2024.*;

/**
 * La classe Structure représente une structure liée à une ville (comme une gare, un aéroport, etc.).
 */
public class Structure implements Lieu {
    /** Le nom de la structure */
    private final String nom;
    private final String ville;
    private final ModaliteTransport modalite;

    /**
     * Constructeur de la classe Structure.
     * @param ville Le nom de la ville où se trouve la structure.
     * @param modalite La modalité de transport associée à la structure.
     */
    public Structure(String ville, ModaliteTransport modalite) {
        this.ville = ville;
        this.modalite = modalite;
        this.nom = Structure.nom(ville, modalite);
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
     * Génère un nom de structure en fonction du nom de base de la ville et de la modalité de transport.
     * @param ville Le nom de base de la ville.
     * @param modalite La modalité de transport associée.
     * @return Le nom généré pour la structure.
     */
    public static String nom(String ville, ModaliteTransport modalite) {
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
    public boolean inSameCityAs(Structure structure) {
        return this.ville.equals(structure.getVille());
    }

    /**
     * Renvoie une représentation textuelle de la structure sous forme de chaîne de caractères.
     * @return Une représentation textuelle de la structure.
     */
    @Override
    public String toString() {
        return ville + " : " + nom;
    }
        // V1 finis

}
