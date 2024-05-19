package Version1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ulille.but.sae_s2_2024.*;

import static org.junit.jupiter.api.Assertions.*;
public class PlateformeTest {

    private Plateforme plateforme;

    @BeforeEach
    void setUp() {
        String[] data = {
                "Lille;Paris;TRAIN;50;10;120",
                "Paris;Lille;BUS;30;15;180",
                "Londres;Calais;TRAIN;150;1;180"};
        plateforme = new Plateforme(data);
    }

    @Test
    void testAdd2Arete() {
        assertEquals(6, plateforme.getAretes().size());
    }

    @Test
    void testAdd1Arete() {
        Structure s =  new Structure("Lille", ModaliteTransport.AVION);
        Arete a = new Arete(s, s, ModaliteTransport.AVION, 1000,200,4);
        plateforme.add1Arete(a);
        assertEquals(7, plateforme.getAretes().size());
    }

    @Test
    void testAddVille() {
        assertTrue(plateforme.containsVille("Lille"));
        assertTrue(plateforme.containsVille("Paris"));
    }

    @Test
    void testExistVilleStructureVersion() {
        assertTrue(plateforme.existVilleStructureVersion("Lille"));
        assertFalse(plateforme.existVilleStructureVersion("London"));
    }

    @Test
    void testAddStructure() {
        assertEquals(4, plateforme.getVilles().size());
        assertEquals(6, plateforme.getStructures().size());
    }

    @Test
    void testCreateOrGetStructure() {
        assertEquals(6, plateforme.getStructures().size());
        Structure lTrain = plateforme.createOrGetStructure("Lille", ModaliteTransport.AVION);
        assertEquals(7, plateforme.getStructures().size());
        assertNotNull(lTrain);
    }

    @Test
    void testGetStructure() {
        Structure paris = plateforme.getStructure(Structure.nom("Paris", ModaliteTransport.BUS));
        assertNotNull(paris);
        assertEquals("Paris", paris.getVille());
    }

    @Test
    void testIndexOf() {
        assertEquals(0, plateforme.indexOf("Lille", "Paris", "TRAIN"));
        assertEquals(1, plateforme.indexOf("Paris", "Lille", "TRAIN"));
    }

    @Test
    void testRemoveArete() {
        Arete arete = plateforme.getAretes().get(0);
        plateforme.removeArete(arete);
        assertEquals(5, plateforme.getAretes().size());
    }

    @Test
    void testRemoveStructure() {
        assertEquals(6, plateforme.getStructures().size());
        Structure structure = plateforme.getStructures().get(0);
        plateforme.removeStructure(structure);
        assertEquals(5, plateforme.getStructures().size());
    }

    @Test
    void testBuildGraph() {
        MultiGrapheOrienteValue graph = plateforme.buildGraph(TypeCout.CO2);
        assertNotNull(graph);
    }

    @Test
    void testChercherPlusCourtsChemins() {
        Structure lille = plateforme.getStructure("Gare_Lille");
        Structure paris = plateforme.getStructure("Gare_Paris");
        assertEquals(1, plateforme.chercherPlusCourtsChemins(lille, paris, TypeCout.TEMPS, 1).size());
        assertEquals(1, plateforme.chercherPlusCourtsChemins(lille, paris, TypeCout.TEMPS, 2).size());
    }

    @Test
    void testIsLinked() {
        Structure lille = plateforme.getStructure("Gare_Lille");
        Structure paris = plateforme.getStructure("Gare_Paris");
        plateforme.buildGraph(TypeCout.CO2);
        assertTrue(plateforme.isLinked(lille, paris));
        assertFalse(plateforme.isLinked(lille, null));
    }
        // V1 finis

}
