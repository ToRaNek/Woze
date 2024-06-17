package version3.graphe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ulille.but.sae_s2_2024.*;

import static org.junit.jupiter.api.Assertions.*;
public class PlateformeTest {

    private Plateforme plateforme;

    @BeforeEach
    void setUp() {
        plateforme = new Plateforme();
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
    void testBuildGraph() {
        MultiGrapheOrienteValue graph = plateforme.buildGraph(TypeCout.CO2);
        assertNotNull(graph);
    }

    @Test
    void testIsLinked() {
        Structure lille = plateforme.getStructure("Gare_Lille");
        Structure paris = plateforme.getStructure("Gare_Paris");
        plateforme.buildGraph(TypeCout.CO2);
        assertTrue(plateforme.isLinked(lille, paris));
        assertFalse(plateforme.isLinked(lille, null));
    }


    // TODO des nouveaux test
}
