package version3.graphe.management;

import java.util.ArrayList;

import version3.graphe.Structure;

public class VilleManagement {
    private ArrayList<String> villes;

    public VilleManagement() {
        villes = new ArrayList<>();
    }

    public ArrayList<String> getVilles() {
        return villes;
    }

    public boolean addVille(final String ville) {
        return villes.add(ville);
    }

    public boolean containsVille( String ville) {
        boolean result = false;
        for (final String v : villes) {
            if (v.toUpperCase().equals(ville.toUpperCase())) {
                result = true;
            }
        }
        return result;
    }

    public boolean existVilleForAStructure(final String ville, StructureManagement structures) {
        boolean bool = false;
        for (final Structure structure : structures.getStructures()) {
            if (structure.getVille().equals(ville)) {
                bool = true;
            }
        }
        return bool;
    }
}
