package Version1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ulille.but.sae_s2_2024.*;

public class AreteTest {

    private Structure structureA;
    private Structure structureB;
    private ModaliteTransport modalite;
    private Arete arete;

    @BeforeEach
    public void initialization() {
        structureA = new Structure("StructureA");
        structureB = new Structure("StructureB");
        modalite = ModaliteTransport.TRAIN;
        arete = new Arete(structureA, structureB, modalite, 100.0, 1.20, 30.0);
    }

    @Test
    public void testGetDepart() {
        assertEquals(structureA, arete.getDepart());
    }

    @Test
    public void testGetArrivee() {
        assertEquals(structureB, arete.getArrivee());
    }

    @Test
    public void testGetModalite() {
        assertEquals(modalite, arete.getModalite());
    }

    @Test
    public void testGetCoutCo2() {
        assertEquals(1.2, arete.getCouts(TypeCout.CO2));
    }

    @Test
    public void testGetCoutTemps() {
        assertEquals(30.0, arete.getCouts(TypeCout.TEMPS));
    }

    @Test
    public void testGetCoutPrix() {
        assertEquals(100.0, arete.getCouts(TypeCout.PRIX));
    }

    @Test
    public void testToString() {
        assertNotNull(arete.toString());
        assertEquals("StructureA -> StructureB [TRAIN]", arete.toString());
    }
}
