package Version1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ulille.but.sae_s2_2024.*;

public class AreteTest {

    private Lieu villeA;
    private Lieu villeB;
    private ModaliteTransport modalite;
    private Arete arete;

    @BeforeEach
    public void initialization() {
        villeA = new Ville("VilleA");
        villeB = new Ville("VilleB");
        modalite = ModaliteTransport.TRAIN;
        arete = new Arete(villeA, villeB, modalite, 100.0, 60.0, 30.0);
    }

    @Test
    public void testGetDepart() {
        assertEquals(villeA, arete.getDepart());
    }

    @Test
    public void testGetArrivee() {
        assertEquals(villeB, arete.getArrivee());
    }

    @Test
    public void testGetModalite() {
        assertEquals(modalite, arete.getModalite());
    }

    @Test
    public void testGetCoutCo2() {
        assertEquals(100.0, arete.getCout(TypeCout.CO2));
    }

    @Test
    public void testGetCoutTemps() {
        assertEquals(60.0, arete.getCout(TypeCout.TEMPS));
    }

    @Test
    public void testGetCoutPrix() {
        assertEquals(30.0, arete.getCout(TypeCout.PRIX));
    }

    @Test
    public void testToString() {
        assertNotNull(arete.toString());
        assertEquals("VilleA -> VilleB[TRAIN]", arete.toString());
    }
}
