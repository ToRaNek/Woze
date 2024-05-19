package Version1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class VoyageurTest {

    private Voyageur voyageur;

    @BeforeEach
    void setUp() {
        // Initialisation d'un nouvel utilisateur avant chaque test
        voyageur = new Voyageur("John", "Doe", "Lille", TypeCout.PRIX);
    }

    @Test
    void testGetCritere() {
        assertEquals(TypeCout.PRIX, voyageur.getCritere());
    }

    @Test
    void testGetId() {
        assertEquals(1, voyageur.getId());
        Voyageur user2 = new Voyageur(null, null, null, null);
        Voyageur user3 = new Voyageur(null, null, null, null);
        assertEquals(2, user2.getId());
        assertEquals(3, user3.getId());

    }

    @Test
    void testGetNom() {
        assertEquals("Doe", voyageur.getNom());
    }

    @Test
    void testGetPrenom() {
        assertEquals("John", voyageur.getPrenom());
    }

    @Test
    void testGetVille() {
        assertEquals("Lille", voyageur.getVille());
    }

    @Test
    void testSetCritere() {
        voyageur.setCritere(TypeCout.CO2);
        assertEquals(TypeCout.CO2, voyageur.getCritere());
    }

    @Test
    void testSetNom() {
        voyageur.setNom("Smith");
        assertEquals("Smith", voyageur.getNom());
    }

    @Test
    void testSetPrenom() {
        voyageur.setPrenom("Jane");
        assertEquals("Jane", voyageur.getPrenom());
    }

    @Test
    void testSetVille() {
        voyageur.setVille("Paris");
        assertEquals("Paris", voyageur.getVille());
    }

    @Test
    void testAddIdToIdTrash() {
        voyageur.addIdToIdTrash();
        List<Integer> idTrash = Voyageur.getIdTrash();
        assertEquals(1, idTrash.size());
        assertEquals(5, (int) idTrash.get(0)); // à cause des modifications
    }

    @Test
    void testGetProchaineID() {
        assertEquals(6, Voyageur.getProchaineID()); // à cause des modifications
    }
        // V1 finis

}
