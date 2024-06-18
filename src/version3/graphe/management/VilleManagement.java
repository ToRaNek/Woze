package version3.graphe.management;

import java.util.ArrayList;

import version3.graphe.Structure;

/**
 * Classe pour la gestion des villes.
 */
public class VilleManagement {
    private ArrayList<String> villes;

    /**
     * Constructeur par défaut initialisant la liste des villes.
     */
    public VilleManagement() {
        villes = new ArrayList<>();
    }

    /**
     * Retourne la liste des villes.
     *
     * @return La liste des villes.
     */
    public ArrayList<String> getVilles() {
        return villes;
    }

    /**
     * Ajoute une ville à la liste des villes.
     *
     * @param ville La ville à ajouter.
     * @return true si la ville a été ajoutée avec succès, false sinon.
     */
    public boolean addVille(final String ville) {
        return villes.add(ville);
    }

    /**
     * Vérifie si une ville est contenue dans la liste des villes, en ignorant la casse.
     *
     * @param ville La ville à rechercher.
     * @return true si la ville est contenue dans la liste, false sinon.
     */
    public boolean containsVille(final String ville) {
        boolean result = false;
        for (final String v : villes) {
            if (v.equalsIgnoreCase(ville)) {
                result = true;
                break; // Sortir de la boucle dès que la ville est trouvée
            }
        }
        return result;
    }

    /**
     * Vérifie si une ville est associée à au moins une structure dans un gestionnaire de structures donné.
     *
     * @param ville La ville à vérifier.
     * @param structures Le gestionnaire de structures où rechercher.
     * @return true si la ville est associée à au moins une structure, false sinon.
     */
    public boolean existVilleForAStructure(final String ville, StructureManagement structures) {
        boolean exists = false;
        for (final Structure structure : structures.getStructures()) {
            if (structure.getVille().equals(ville)) {
                exists = true;
                break; // Sortir de la boucle dès que la ville est trouvée pour une structure
            }
        }
        return exists;
    }
}
