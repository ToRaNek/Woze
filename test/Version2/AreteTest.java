package Version2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ulille.but.sae_s2_2024.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumMap;

class AreteTest {

    private Structure gareLille;
    private Structure gareParis;
    private Arete arete;

    @BeforeEach
    void setUp() {
        gareLille = new Structure("Lille", ModaliteTransport.TRAIN);
        gareParis = new Structure("Paris", ModaliteTransport.TRAIN);
        arete = new Arete(gareLille, gareParis, ModaliteTransport.TRAIN, 10, 5, 120);
    }

    @Test
    void testGetDepart() {
        assertEquals(gareLille, arete.getDepart());
    }

    @Test
    void testGetArrivee() {
        assertEquals(gareParis, arete.getArrivee());
    }

    @Test
    void testGetModalite() {
        assertEquals(ModaliteTransport.TRAIN, arete.getModalite());
    }

    @Test
    void testGetCout() {
        assertEquals(10, arete.getCout(TypeCout.PRIX));
        assertEquals(5, arete.getCout(TypeCout.CO2));
        assertEquals(120, arete.getCout(TypeCout.TEMPS));
    }

    @Test
    void testSetDepart() {
        Structure gareRoubaix = new Structure("Roubaix", ModaliteTransport.TRAIN);
        arete.setDepart(gareRoubaix);
        assertEquals(gareRoubaix, arete.getDepart());
    }

    @Test
    void testSetArrivee() {
        Structure gareRoubaix = new Structure("Roubaix", ModaliteTransport.TRAIN);
        arete.setArrivee(gareRoubaix);
        assertEquals(gareRoubaix, arete.getArrivee());
    }

    @Test
    void testSetModalite() {
        arete.setModalite(ModaliteTransport.AVION);
        assertEquals(ModaliteTransport.AVION, arete.getModalite());
    }

    @Test
    void testGetCouts() {
        // Créer une map des couts attendus
        EnumMap<TypeCout, Double> expectedCouts = new EnumMap<>(TypeCout.class);
        expectedCouts.put(TypeCout.PRIX, 10.0);
        expectedCouts.put(TypeCout.CO2, 5.0);
        expectedCouts.put(TypeCout.TEMPS, 120.0);

        assertEquals(expectedCouts, arete.getCouts());
    }
        // V1 finis

}
