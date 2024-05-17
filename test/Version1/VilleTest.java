package Version1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VilleTest {

    /**
     * Test du constructeur de la classe Ville.
     */
    @Test
    public void testConstructeurVille() {
        Ville ville = new Ville("Lille");
        
        // Vérifier que la ville est correctement construite
        assertNotNull(ville);
        assertEquals("Lille", ville.getNom());
        assertNotNull(ville.getStructures());
        assertEquals(0, ville.getStructures().size()); // La liste des structures est initialement vide
    }

    /**
     * Test de la méthode ajouterStructure.
     */
    @Test
    public void testAjouterStructure() {
        Ville ville = new Ville("Lille");
        Structure structure = new Structure("Gare de Lille");
        
        ville.ajouterStructure(structure);
        
        // Vérifier que la structure a bien été ajoutée à la liste des structures de la ville
        assertEquals(1, ville.getStructures().size());
        assertEquals(structure, ville.getStructures().get(0));
    }

    /**
     * Test de la méthode toString.
     */
    @Test
    public void testToString() {
        Ville ville = new Ville("Lille");
        assertEquals("Lille", ville.toString());
    }
}
