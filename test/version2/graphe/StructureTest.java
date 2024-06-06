package version2.graphe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import fr.ulille.but.sae_s2_2024.*;

public class StructureTest {

    @Test
    void testGetNom() {
        // Test de la méthode getNom()
        Structure gareLille = new Structure("Lille", ModaliteTransport.TRAIN);
        assertEquals("Gare_Lille", gareLille.getNom());
    }

    @Test
    void testGetVille() {
        // Test de la méthode getVille()
        Structure gareLille = new Structure("Lille", ModaliteTransport.TRAIN);
        assertEquals("Lille", gareLille.getVille());
    }

    @Test
    void testGetModalite() {
        // Test de la méthode getModalite()
        Structure gareLille = new Structure("Lille", ModaliteTransport.TRAIN);
        assertEquals(ModaliteTransport.TRAIN, gareLille.getModalite());
    }

    @Test
    void testNom() {
        // Test de la méthode statique nom()
        String nomStructure = Structure.nom("Paris", ModaliteTransport.AVION);
        assertEquals("Aéroport_Paris", nomStructure);
    }

    @Test
    void testInSameCityAs() {
        // Test de la méthode inSameCityAs()
        Structure aeroportParis = new Structure("Paris", ModaliteTransport.AVION);
        Structure gareParis = new Structure("Paris", ModaliteTransport.TRAIN);
        assertTrue(aeroportParis.inSameCityAs(gareParis));
    }
        // V1 finis

}
