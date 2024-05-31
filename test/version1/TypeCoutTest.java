package version1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TypeCoutTest {

    @Test
    void testGetSymbole() {
        // Vérification des symboles associés aux types de coûts
        assertEquals(" kg", TypeCout.CO2.getSymbole());
        assertEquals(" min", TypeCout.TEMPS.getSymbole());
        assertEquals(" €", TypeCout.PRIX.getSymbole());
    }

    @Test
    void testToString() {
        // Vérification de la redéfinition de la méthode toString pour chaque type de coût
        assertEquals("CO2 kg", TypeCout.CO2.toString());
        assertEquals("TEMPS min", TypeCout.TEMPS.toString());
        assertEquals("PRIX €", TypeCout.PRIX.toString());
    }
        // V1 finis

}
