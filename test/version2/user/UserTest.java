package version2.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import version3.graphe.TypeCout;
import version3.user.User;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        // Initialisation d'un nouvel utilisateur avant chaque test
        user = new User("John", "Doe", "Lille", TypeCout.PRIX);
    }

    @Test
    void testGetCritere() {
        assertEquals(TypeCout.PRIX, user.getCritere());
    }

    @Test
    void testGetId() {
        assertEquals(1, user.getId());
        User user2 = new User(null, null, null, null);
        User user3 = new User(null, null, null, null);
        assertEquals(2, user2.getId());
        assertEquals(3, user3.getId());

    }

    @Test
    void testGetNom() {
        assertEquals("Doe", user.getNom());
    }

    @Test
    void testGetPrenom() {
        assertEquals("John", user.getPrenom());
    }

    @Test
    void testGetVille() {
        assertEquals("Lille", user.getVille());
    }

    @Test
    void testSetCritere() {
        user.setCritere(TypeCout.CO2);
        assertEquals(TypeCout.CO2, user.getCritere());
    }

    @Test
    void testSetNom() {
        user.setNom("Smith");
        assertEquals("Smith", user.getNom());
    }

    @Test
    void testSetPrenom() {
        user.setPrenom("Jane");
        assertEquals("Jane", user.getPrenom());
    }

    @Test
    void testSetVille() {
        user.setVille("Paris");
        assertEquals("Paris", user.getVille());
    }

    @Test
    void testAddIdToIdTrash() {
        user.addIdToIdTrash();
        List<Integer> idTrash = User.getIdTrash();
        assertEquals(1, idTrash.size());
        assertEquals(5, (int) idTrash.get(0)); // à cause des modifications
    }

    @Test
    void testGetProchaineID() {
        assertEquals(6, User.getProchaineID()); // à cause des modifications
    }
        // V1 finis

}
