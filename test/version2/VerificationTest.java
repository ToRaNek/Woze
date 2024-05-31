package version2;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Classe test de VerifictionData
 */
public class VerificationTest {
    Verification verification = new Verification();
    
    @Test
    public void testDataIsValid() {
        String[] data = {
            "villeA;villeB;Train;60;1.7;80",
            "villeB;villeD;Train;22;2.4;40",
            "villeA;villeC;Train;42;1.4;50",
            "villeB;villeC;Train;14;1.4;60",
            "villeC;villeD;Bus;110;150;22",
            "villeC;villeD;Train;65;1.2;90"
    };

        assertTrue("Toutes les données devraient être valides", verification.dataIsValid(data));
    }

    @Test
    public void testLineIsValid() {
        assertTrue("Une ligne valide devrait être reconnue", verification.lineIsValid("villeA;villeB;Train;60;1.7;80"));
        assertFalse("Une ligne invalide ne devrait pas être reconnue", verification.lineIsValid("villA;villeB;;60;1.7;80"));
    }

    @Test
    public void testDoubleIsValid() {
        assertTrue("Un nombre double valide devrait être reconnu", verification.doubleIsValid("3.14"));
        assertFalse("Un nombre double invalide ne devrait pas être reconnu", verification.doubleIsValid("abc"));
    }

    @Test
    public void testTransportIsValid() {
        assertTrue("Un transport valide devrait être reconnu", verification.transportIsValid("TraIn"));
        assertFalse("Un transport invalide ne devrait pas être reconnu", verification.transportIsValid("CAR"));
    }

    @Test
    public void testVilleIsValid() {
        assertTrue("Une ville valide devrait être reconnue", verification.villeIsValid("Lille"));
        assertFalse("Une ville invalide ne devrait pas être reconnue", verification.villeIsValid("123ville"));
        assertFalse("Une ligne avec un nombre double invalide ne devrait pas être reconnue", verification.lineIsValid("villeA;villeB;Train;60;1.7abc;80")); // Test ajouté pour couvrir le cas où une ligne a un nombre double invalide
    }

    @Test
    public void testCorrespondanceIsValid() {
        String[] data = {
                "Lille;Train;Avion;130;0.1;20",
                "Lille;Train;Bus;20;0;0",
                "Lille;Avion;Bus;120;0.1;20",
                "Valenciennes;Train;Bus;10;0;0"
        };
        assertTrue("Toutes les données devraient être valides", verification.correspondanceIsValid(data));
    }

    @Test
    public void testCorrespondanceLineIsValid() {
        assertTrue("Une ligne valide devrait être reconnue", verification.correspondanceLineIsValid("Lille;Train;Avion;130;0.1;20"));
        assertFalse("Une ligne invalide ne devrait pas être reconnue", verification.correspondanceLineIsValid("Lille;Marseille;Bus;20;0;0"));
    }

}
