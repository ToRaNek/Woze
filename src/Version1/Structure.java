package Version1;
import fr.ulille.but.sae_s2_2024.*;

/**
 * La classe Structure représente une structure géographique comme une gare, un aéroport ou un arrêt de bus.
 */
public class Structure implements Lieu {

    /** Le nom de la structure */
    private String nom;

    /**
     * Constructeur de la classe Structure.
     * @param nom Le nom de la structure.
     */
    public Structure(String nom){
        this.nom = nom;
    }

    /**
     * Constructeur de la classe Structure prenant en compte la ville et la modalité de transport.
     * @param ville La ville à laquelle la structure est associée.
     * @param modalite La modalité de transport associée à la structure.
     */
    public Structure(Ville ville, ModaliteTransport modalite ){
        String nomVille = ville.getNom();
        switch (modalite) {
            case TRAIN:
                nom = "Gare_de_" + nomVille;
                break;
            case AVION:
                nom =  "Aéroport_de_" + nomVille;
                break;
            case BUS:
                nom =  "Arrêt_de_bus_de_" + nomVille;
                break;
            default:
                nom =  nomVille; // Si la modalité de transport n'est pas donnée, ça retourne simplement le nom d'origine ( bon après ça devrait pas arrivé mais on sait jamais).
        }
    }

    @Override
    public String toString() {
        return  nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


}
