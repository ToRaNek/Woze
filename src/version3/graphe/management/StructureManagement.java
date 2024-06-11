package version3.graphe.management;

import version3.graphe.Structure;
import java.util.ArrayList;

import fr.ulille.but.sae_s2_2024.ModaliteTransport;

public class StructureManagement {
    private ArrayList<Structure> structures;

    public StructureManagement() {
        structures = new ArrayList<>();
    }

    public ArrayList<Structure> getStructures() {
        return structures;
    }

    public void addStructure(final Structure structure) {
        structures.add(structure);
    }

    public Structure getStructure(final String nom) {
        Structure structureResult = null;
        for (final Structure structure : structures) {
            if (structure.getNom().equals(nom)) {
                structureResult = structure;
            }
        }
        return structureResult;
    }

    public boolean contains(final String nom) {
        boolean result = false;
        for (final Structure structure : structures) {
            if (structure.getNom().equals(nom)) {
                result = true;
            }
        }
        return result;
    }

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

    public int indexOf(final Structure structure) {
        return structures.indexOf(structure);
    }

    public void removeStructure(final Structure structure) {
        structures.remove(structure);
    }

    public void removeStructure(final int index) {
        removeStructure(structures.get(index));
    }

    public boolean isInACity(VilleManagement villes, final Structure struct) {
        boolean bool = false;
        for (final String ville : villes.getVilles()) {
            if(struct.getVille().equals(ville)) {
                bool = true;
            } 
        }
        return bool;
    }
    
}
