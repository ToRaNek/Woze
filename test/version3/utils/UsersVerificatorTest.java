package version3.utils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UsersVerificatorTest {

    private final UsersVerificator verificator = new UsersVerificator();

    // Tests for getLine
    @Test
    public void testGetLine_nullInput() {
        int result = verificator.getLine(null);
        assertTrue("Le nombre de lignes pour une entrée nulle doit être 0", result == 0);
    }

    @Test
    public void testGetLine_emptyInput() {
        int result = verificator.getLine(new String[0]);
        assertTrue("Le nombre de lignes pour un tableau vide doit être 0", result == 0);
    }

    @Test
    public void testGetLine_validInput() {
        String[] data = {"ligne1", "ligne2", "ligne3"};
        int result = verificator.getLine(data);
        assertTrue("Le nombre de lignes doit correspondre à la taille du tableau", result == 3);
    }

    // Tests for userIsValid
    @Test
    public void testUserIsValid_nullInput() {
        assertFalse("Les données nulles ne sont pas valides", verificator.userIsValid(null));
    }

    @Test
    public void testUserIsValid_emptyInput() {
        assertFalse("Les données vides ne sont pas valides", verificator.userIsValid(new String[0]));
    }

    @Test
    public void testUserIsValid_validInput() {
        String[] data = {"Dupont;Jean;Lille;TEMPS", "Martin;Marie;Paris;CO2"};
        assertTrue("Les données valides doivent être acceptées", verificator.userIsValid(data));
    }

    @Test
    public void testUserIsValid_invalidInput() {
        String[] data = {"Dupont;Jean;Lille;TEMPS", "Martin;Marie;123;CO2"}; // Ville invalide
        assertFalse("Les données invalides doivent être rejetées", verificator.userIsValid(data));
    }

    // Tests for lineIsValid
    @Test
    public void testLineIsValid_validLine() {
        String line = "Dupont;Jean;Lille;TEMPS";
        assertTrue("Une ligne valide doit être acceptée", verificator.lineIsValid(line));
    }

    @Test
    public void testLineIsValid_invalidLine_wrongFormat() {
        String line = "Dupont;Jean;Lille"; // Format incorrect
        assertFalse("Une ligne avec un format incorrect doit être rejetée", verificator.lineIsValid(line));
    }

    @Test
    public void testLineIsValid_invalidLine_invalidName() {
        String line = "Dupont;Jean123;Lille;TEMPS"; // Nom invalide
        assertFalse("Une ligne avec un nom invalide doit être rejetée", verificator.lineIsValid(line));
    }

    // Tests for nameSurnameVilleIsValid
    @Test
    public void testNameSurnameVilleIsValid_validNames() {
        assertTrue(verificator.nameSurnameVilleIsValid("Dupont"));
        assertTrue(verificator.nameSurnameVilleIsValid("Jean-Pierre"));
        assertTrue(verificator.nameSurnameVilleIsValid("L'Isle-d'Abeau")); // Noms avec traits d'union et apostrophes
    }

    @Test
    public void testNameSurnameVilleIsValid_invalidNames() {
        assertFalse(verificator.nameSurnameVilleIsValid("Dupont123")); // Chiffres non autorisés
        assertFalse(verificator.nameSurnameVilleIsValid("Jean-Pierre_")); // Caractères spéciaux non autorisés
    }

    // Tests for critereIsValid
    @Test
    public void testCritereIsValid_validCriteria() {
        assertTrue(verificator.critereIsValid("TEMPS"));
        assertTrue(verificator.critereIsValid("co2")); // Insensible à la casse
        assertTrue(verificator.critereIsValid("pRiX"));
    }

    @Test
    public void testCritereIsValid_invalidCriteria() {
        assertFalse(verificator.critereIsValid("DISTANCE"));
        assertFalse(verificator.critereIsValid("temps ")); // Espace supplémentaire
    }
}
