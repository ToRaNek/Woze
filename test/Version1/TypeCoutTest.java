package Version1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Classe de test pour la classe TypeCout.
 */
public class TypeCoutTest {

    /**
     * Test de la méthode getCout().
     */
    @Test
    public void testGetCout() {
        assertEquals(0.0, TypeCout.CO2.getCout());
        assertEquals(0.0, TypeCout.TEMPS.getCout());
        assertEquals(0.0, TypeCout.PRIX.getCout());
    }

    /**
     * Test de la méthode getSymbole().
     */
    @Test
    public void testGetSymbole() {
        assertEquals(" kg", TypeCout.CO2.getSymbole());
        assertEquals(" min", TypeCout.TEMPS.getSymbole());
        assertEquals(" €", TypeCout.PRIX.getSymbole());
    }

    /**
     * Test de la méthode setCout() et getCout() après modification.
     */
    @Test
    public void testSetCout() {
        TypeCout.CO2.setCout(20);
        assertEquals(20.0, TypeCout.CO2.getCout());

        TypeCout.TEMPS.setCout(15);
        assertEquals(15.0, TypeCout.TEMPS.getCout());

        TypeCout.PRIX.setCout(50);
        assertEquals(50.0, TypeCout.PRIX.getCout());
    }

    /**
     * Test de la méthode display().
     */
    @Test
    public void testDisplay() {
        TypeCout.CO2.setCout(20);
        assertEquals("20.0 kg", TypeCout.CO2.display());

        TypeCout.TEMPS.setCout(15);
        assertEquals("15.0 min", TypeCout.TEMPS.display());

        TypeCout.PRIX.setCout(50);
        assertEquals("50.0 €", TypeCout.PRIX.display());
    }

    /**
     * Test de la méthode toString().
     */
    @Test
    public void testToString() {
        assertEquals("CO2", TypeCout.CO2.toString());
        assertEquals("TEMPS", TypeCout.TEMPS.toString());
        assertEquals("PRIX", TypeCout.PRIX.toString());
    }
}
