package Version1;

import org.junit.Before;
import org.junit.Test;

import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.MultiGrapheOrienteValue;

import static org.junit.Assert.*;

/**
 * Classe de test pour la classe Plateforme
 */
public class PlateformeTest {

    private Plateforme plateforme;

    @Before
    public void setUp() {
        // Données de test
        String[] data = {
                "villeA;villeB;Train;60;1.7;80",
                "villeB;villeD;Train;22;2.4;40",
                "villeA;villeC;Train;42;1.4;50",
                "villeB;villeC;Train;14;1.4;60",
                "villeC;villeD;Bus;110;150;22",
                "villeC;villeD;Train;65;1.2;90"
        };

        // Initialisation de la plateforme
        plateforme = new Plateforme(data);
    }

    @Test
    public void testContains() {
        assertTrue("La structure 'Gare_de_villeA' devrait être présente dans la plateforme", plateforme.contains("Gare_de_villeA"));
        assertFalse("La structure 'Gare_de_villeE' ne devrait pas être présente dans la plateforme", plateforme.contains("Gare_de_villeE"));
    }

    @Test
    public void testGetStructure() {
        Structure structureA = plateforme.getStructure("Gare_de_villeA");
        assertNotNull("La structure 'Gare_de_villeA' devrait être trouvée dans la plateforme", structureA);
        assertEquals("Le nom de la structure devrait être 'Gare_de_villeA'", "Gare_de_villeA", structureA.getNom());

        Structure structureE = plateforme.getStructure("Gare_de_villeE");
        assertNull("La structure 'Gare_de_villeE' ne devrait pas être trouvée dans la plateforme", structureE);
    }

    @Test
    public void testBuildGraph() {
        MultiGrapheOrienteValue graph = plateforme.buildGraph(TypeCout.TEMPS);
        assertNotNull("Le graphe devrait être construit", graph);

        assertEquals("Le nombre de sommets du graphe devrait être égal au nombre de structures dans la plateforme", plateforme.getStructures().size(), graph.sommets().size());
        assertEquals("Le nombre d'arêtes du graphe devrait être égal au nombre d'arêtes dans la plateforme", plateforme.getAretes().size(), graph.aretes().size());
    }

    @Test
    public void testRemoveStructure() {
        // Suppression d'une structure existante
        Structure structureA = plateforme.getStructure("Gare_de_villeA");
        assertNotNull("La structure 'Gare_de_villeA' devrait être trouvée dans la plateforme", structureA);
        plateforme.removeStructure(structureA);
        assertFalse("La structure 'Gare_de_villeA' ne devrait plus être présente dans la plateforme", plateforme.contains("Gare_de_villeA"));

        // Suppression d'une structure inexistante
        Structure structureE = new Structure("Gare_de_villeE");
        assertFalse("La structure 'Gare_de_villeE' ne devrait pas être présente dans la plateforme", plateforme.contains("Gare_de_villeE"));
        plateforme.removeStructure(structureE);
    }

    @Test
    public void testRemoveArete() {
        // Suppression d'une arête existante
        Arete arete = plateforme.getAretes().get(0);
        assertNotNull("Il devrait y avoir au moins une arête dans la plateforme", arete);
        plateforme.removeArete(arete);
        assertFalse("L'arête devrait être supprimée de la plateforme", plateforme.getAretes().contains(arete));

        // Suppression d'une arête inexistante
        Arete areteInexistante = new Arete(new Structure("Inexistante1"), new Structure("Inexistante2"), ModaliteTransport.TRAIN, 50, 1.5, 70);
        assertFalse("L'arête inexistante ne devrait pas être présente dans la plateforme", plateforme.getAretes().contains(areteInexistante));
        plateforme.removeArete(areteInexistante);
    }
}
