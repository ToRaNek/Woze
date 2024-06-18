package version3.graphe.management;

import version3.graphe.Structure;
import java.util.ArrayList;

import fr.ulille.but.sae_s2_2024.ModaliteTransport;

/**
 * The class responsible for managing structures.
 */
public class StructureManagement {
    private ArrayList<Structure> structures;

    /**
     * Constructs a new StructureManagement object.
     */
    public StructureManagement() {
        structures = new ArrayList<>();
    }

    /**
     * Returns the list of structures.
     *
     * @return The list of structures.
     */
    public ArrayList<Structure> getStructures() {
        return structures;
    }

    /**
     * Returns the list of structures located in the specified city.
     *
     * @param ville The name of the city.
     * @return The list of structures in the specified city.
     */
    public ArrayList<Structure> getStructuresFrom(String ville) {
        ArrayList<Structure> struct = new ArrayList<>();
        for (Structure structure : structures) {
            if (structure.getVille().equals(ville)) {
                struct.add(structure);
            }
        }
        return struct;
    }

    /**
     * Returns the list of structures that support the specified mode of transportation.
     *
     * @param modaliteTransport The mode of transportation.
     * @return The list of structures supporting the specified mode of transportation.
     */
    public ArrayList<Structure> getStructuresFor(ModaliteTransport modaliteTransport) {
        ArrayList<Structure> struct = new ArrayList<>();
        for (Structure structure : structures) {
            if (structure.getModalite().equals(modaliteTransport)) {
                struct.add(structure);
            }
        }
        return struct;
    }

    /**
     * Returns the list of structures located in the specified city and supporting the specified mode of transportation.
     *
     * @param ville             The name of the city.
     * @param modaliteTransport The mode of transportation.
     * @return The list of structures in the specified city and supporting the specified mode of transportation.
     */
    public ArrayList<Structure> getStructuresFromAndFor(String ville, ModaliteTransport modaliteTransport) {
        ArrayList<Structure> result = new ArrayList<>();
        for (Structure structure : structures) {
            if (structure.getVille().equals(ville) && structure.getModalite() == modaliteTransport) {
                result.add(structure);
            }
        }
        return result;
    }

    /**
     * Adds a new structure to the list.
     *
     * @param structure The structure to add.
     */
    public void addStructure(final Structure structure) {
        structures.add(structure);
    }

    /**
     * Retrieves a structure with the specified name.
     *
     * @param nom The name of the structure.
     * @return The structure with the specified name, or null if not found.
     */
    public Structure getStructure(final String nom) {
        Structure structureResult = null;
        for (final Structure structure : structures) {
            if (structure.getNom().equals(nom)) {
                structureResult = structure;
            }
        }
        return structureResult;
    }

    /**
     * Retrieves a structure in the specified city and supporting the specified mode of transportation.
     *
     * @param ville   The name of the city.
     * @param modalite The mode of transportation.
     * @return The structure in the specified city and supporting the specified mode of transportation, or null if not found.
     */
    public Structure getStructure(final String ville, ModaliteTransport modalite) {
        Structure structureResult = null;
        String nom = Structure.nom(ville, modalite);
        for (final Structure structure : structures) {
            if (structure.getNom().equals(nom)) {
                structureResult = structure;
            }
        }
        return structureResult;
    }

    /**
     * Checks if a structure with the specified name exists.
     *
     * @param nom The name of the structure.
     * @return true if the structure exists, false otherwise.
     */
    public boolean contains(final String nom) {
        boolean result = false;
        for (final Structure structure : structures) {
            if (structure.getNom().equals(nom)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Creates a new structure in the specified city and supporting the specified mode of transportation, or retrieves an existing one if it already exists.
     *
     * @param villes           The VilleManagement object.
     * @param ville            The name of the city.
     * @param modalite         The mode of transportation.
     * @return The created or retrieved structure.
     */
    public Structure createOrGetStructure(VilleManagement villes, final String ville, final ModaliteTransport modalite) {
        Structure struct;
        final boolean exist = contains(Structure.nom(ville, modalite));
        if (exist) {
            if (!villes.containsVille(ville)) {
                villes.addVille(ville);
            }
            struct = getStructure(Structure.nom(ville, modalite));
        } else {
            if (!villes.containsVille(ville)) {
                villes.addVille(ville);
            }
            struct = new Structure(ville, modalite);
            addStructure(struct);
        }
        return struct;
    }

    /**
     * Returns the index of the specified structure in the list.
     *
     * @param structure The structure.
     * @return The index of the structure, or -1 if not found.
     */
    public int indexOf(final Structure structure) {
        return structures.indexOf(structure);
    }

    /**
     * Returns the index of the structure in the specified city and supporting the specified mode of transportation.
     *
     * @param ville   The name of the city.
     * @param modalite The mode of transportation.
     * @return The index of the structure, or -1 if not found.
     */
    public int indexOf(String ville, ModaliteTransport modalite) {
        return structures.indexOf(getStructure(ville, modalite));
    }

    /**
     * Removes the specified structure from the list.
     *
     * @param structure The structure to remove.
     */
    public void removeStructure(final Structure structure) {
        structures.remove(structure);
    }

    /**
     * Removes the structure at the specified index from the list.
     *
     * @param index The index of the structure to remove.
     */
    public void removeStructure(final int index) {
        removeStructure(structures.get(index));
    }

    /**
     * Checks if the specified structure is located in any city managed by the VilleManagement object.
     *
     * @param villes The VilleManagement object.
     * @param struct The structure to check.
     * @return true if the structure is located in a city, false otherwise.
     */
    public boolean isInACity(VilleManagement villes, final Structure struct) {
        boolean bool = false;
        for (final String ville : villes.getVilles()) {
            if (struct.getVille().equals(ville)) {
                bool = true;
            }
        }
        return bool;
    }
}