package Version1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VoyageurTest {
    
    @BeforeEach
    public void setUp() {
        // Réinitialiser la liste des ID d'utilisateurs supprimés avant chaque test
        Voyageur.getIdtrash().clear();
        // Réinitialiser la prochaine ID avant chaque test
        Voyageur.setProchaineID(0);
    }

    /**
     * Test du constructeur de la classe Voyageur.
     */
    @Test
    public void testConstructeurVoyageur() {
        Ville ville = new Ville("Lille");
        Voyageur voyageur = new Voyageur("John", "Doe", ville);
        
        // Vérifier que l'utilisateur est correctement construit
        assertNotNull(voyageur);
        assertEquals(1, voyageur.getId()); // L'ID du premier utilisateur est 1
        assertEquals("John", voyageur.getPrenom());
        assertEquals("Doe", voyageur.getNom());
        assertEquals(ville, voyageur.getVille());
    }

    /**
     * Test des méthodes getters et setters de la classe Voyageur.
     */
    @Test
    public void testGettersSetters() {
        Ville ville = new Ville("Lille");
        Voyageur voyageur = new Voyageur("John", "Doe", ville);
        
        // Modifier les attributs de l'utilisateur
        voyageur.setPrenom("Jane");
        voyageur.setNom("Smith");
        Ville nouvelleVille = new Ville("Paris");
        voyageur.setVille(nouvelleVille);
        
        // Vérifier que les modifications ont été correctement appliquées
        assertEquals("Jane", voyageur.getPrenom());
        assertEquals("Smith", voyageur.getNom());
        assertEquals(nouvelleVille, voyageur.getVille());
    }

    /**
     * Test de la méthode addIdToIdTrash.
     */
    @Test
    public void testAddIdToIdTrash() {
        Ville ville = new Ville("Lille");
        Voyageur voyageur = new Voyageur("John", "Doe", ville);
        int id = voyageur.getId();
        
        voyageur.addIdToIdTrash();
        
        // Vérifier que l'ID de l'utilisateur est ajouté à la liste des ID supprimés
        assertNotNull(voyageur.getId());
        assertEquals(id, Voyageur.getIdtrash().get(0));
    }
}
