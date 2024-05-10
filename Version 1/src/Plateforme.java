import fr.ulille.but.sae_s2_2024.*;

/**
 * La classe Plateforme représente un lieu qui peut être une ville, une gare, un aéroport, un arrêt de bus, etc.
 */
public class Plateforme implements Lieu {
    /** Le nom de la plateforme */
    private String nom;
    
    /** La modalité de transport associée à la plateforme */
    final private ModaliteTransport modaliteTransport;

    /**
     * Constructeur de la classe Plateforme.
     *
     * @param nom le nom de la plateforme
     * @param modaliteTransport la modalité de transport associée à la plateforme
     */
    public Plateforme(String nom, ModaliteTransport modaliteTransport) {
        this.nom = nom;
        this.modaliteTransport = modaliteTransport;
    }

    /**
     * Renvoie le nom de la plateforme.
     *
     * @return le nom de la plateforme
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de la plateforme.
     *
     * @param nom le nom de la plateforme à définir
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Renvoie la modalité de transport associée à la plateforme.
     *
     * @return la modalité de transport associée à la plateforme
     */
    public ModaliteTransport getModaliteTransport() {
        return modaliteTransport;
    }


    /**
     * Renvoie une représentation textuelle de la plateforme.
     *
     * @return une représentation textuelle de la plateforme
     */
    @Override
    public String toString() {
        return nom;
    }
}
