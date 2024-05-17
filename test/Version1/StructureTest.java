package Version1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StructureTest {

    @Test
    void testGetNom() {
        // Création d'une structure avec un nom spécifique
        Structure structure = new Structure("Gare de Lille");

        // Vérification que le nom retourné est correct
        assertEquals("Gare de Lille", structure.getNom());
    }

    @Test
    void testToString() {
        // Création d'une structure avec un nom spécifique
        Structure structure = new Structure("Aéroport de Paris");

        // Vérification que la représentation textuelle est correcte
        assertEquals("Aéroport de Paris", structure.toString());
    }
}
