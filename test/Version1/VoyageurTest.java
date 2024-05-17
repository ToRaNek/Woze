package Version1;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Classe de test pour la classe Voyageur
 */
public class VoyageurTest {

    @Test
    public void testSetVille() {
        Structure parisGare = new Structure("Gare_de_Paris");
        Structure lilleAeroport = new Structure("Aéroport_de_Lille");

        Voyageur voyageur1 = new Voyageur("Jean", "Dupont", parisGare, TypeCout.TEMPS);
        Voyageur voyageur2 = new Voyageur("Alice", "Martin", lilleAeroport, TypeCout.CO2);

        // Vérifie si la ville a été correctement modifiée
        voyageur1.setVille(lilleAeroport);
        assertTrue("La ville devrait être Lille Aéroport", voyageur1.getVille().getNom().equals("Aéroport_de_Lille"));
        assertFalse("La ville ne devrait pas être Gare de Paris", voyageur1.getVille().getNom().equals("Gare_de_Paris"));

        voyageur2.setVille(parisGare);
        assertTrue("La ville devrait être Gare de Paris", voyageur2.getVille().getNom().equals("Gare_de_Paris"));
        assertFalse("La ville ne devrait pas être Aéroport de Lille", voyageur2.getVille().getNom().equals("Aéroport_de_Lille"));
    }

    @Test
    public void testSetCritere() {
        Structure parisGare = new Structure("Gare_de_Paris");
        Voyageur voyageur = new Voyageur("Jean", "Dupont", parisGare, TypeCout.TEMPS);

        // Vérifie si le critère a été correctement modifié
        voyageur.setCritere(TypeCout.CO2);
        assertTrue("Le critère devrait être CO2", voyageur.getCritere() == TypeCout.CO2);
        assertFalse("Le critère ne devrait pas être TEMPS", voyageur.getCritere() == TypeCout.TEMPS);
    }
}
