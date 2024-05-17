package Version1;
import fr.ulille.but.sae_s2_2024.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class StructureTest {

    /**
     * Test du constructeur de la classe Structure avec nom.
     */
    @Test
    public void testConstructeurStructureNom() {
        Structure structure = new Structure("Gare de Lille");
        
        // Vérifier que la structure est correctement construite avec le nom
        assertNotNull(structure);
        assertEquals("Gare de Lille", structure.getNom());
    }

    /**
     * Test du constructeur de la classe Structure avec ville et modalité de transport.
     */
    @Test
    public void testConstructeurStructureVilleModalite() {
        Ville ville = new Ville("Lille");
        Structure structure = new Structure(ville, ModaliteTransport.TRAIN);
        
        // Vérifier que la structure est correctement construite avec la ville et la modalité de transport
        assertNotNull(structure);
        assertEquals("Gare_de_Lille", structure.getNom());
    }

    /**
     * Test de la méthode toString.
     */
    @Test
    public void testToString() {
        Structure structure = new Structure("Gare de Lille");
        assertEquals("Gare de Lille", structure.toString());
    }
}
