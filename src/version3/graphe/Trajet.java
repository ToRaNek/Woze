package version3.graphe;

import java.util.List;
import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.Trancon;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;

public class Trajet implements Chemin {

    private List<Trancon> trancons;
    private double poids;

    public Trajet(List<Trancon> trancons, double poids) {
        this.trancons = trancons;
        this.poids = poids;
    }

    @Override
    public List<Trancon> aretes() {
        return trancons;
    }

    @Override
    public double poids() {
        return poids;
    }

    /**
     * Vérifie si ce trajet contient une modalité de transport spécifique.
     * @param modalite La modalité de transport à vérifier.
     * @return true si le trajet contient la modalité, false sinon.
     */
    public boolean contains(ModaliteTransport modalite) {
        for (Trancon trancon : trancons) {
            if (((Arete)trancon).getModalite() == modalite) {
                return true;
            }
        }
        return false;
    }
}
