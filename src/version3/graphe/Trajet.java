package version3.graphe;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.ulille.but.sae_s2_2024.Trancon;
import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;

/**
 * Classe représentant un trajet, implémentant l'interface Chemin et Serializable.
 * Un trajet est défini par une liste ordonnée de trançons (arêtes) et un ensemble de poids pour chaque type de coût.
 */
public class Trajet implements Chemin, Serializable {

    /** La liste ordonnée des trançons (arêtes) constituant le trajet */
    private List<Trancon> trancons;

    /** Map associant chaque type de coût à son poids */
    private Map<TypeCout, Double> poidsParType;

    /** Le type de coût actuellement utilisé pour les calculs */
    private TypeCout currentType;

    private Chemin chemin;

    /**
     * Constructeur de la classe Trajet.
     * @param trancons La liste des trançons (arêtes) constituant le trajet.
     * @param poidsParType La map associant chaque type de coût à son poids.
     */
    public Trajet(List<Trancon> trancons, Chemin chemin) {
        this.trancons = trancons;
        this.chemin = chemin;
        initMap();
        this.currentType = TypeCout.CO2;
    }

    private void initMap() {
        poidsParType = new HashMap<>();
        double poids = 0;
        for (TypeCout typeCout : TypeCout.values()) {
            poids = getPoids(typeCout);
            poidsParType.put(typeCout, poids);
        }
    }

    private double getPoids(TypeCout typeCout) {
        double poids = 0;
        for (Trancon trancon : chemin.aretes()) {
            poids += ((Arete)trancon).getCout(typeCout);
        }
        return poids;
    }

    /**
     * Retourne la liste des trançons (arêtes) constituant le trajet.
     * @return La liste des trançons (arêtes) du trajet.
     */
    @Override
    public List<Trancon> aretes() {
        return trancons;
    }

    /**
     * Retourne le poids total du trajet pour le type de coût courant.
     * @return Le poids total du trajet pour le type de coût courant.
     */
    @Override
    public double poids() {
        return poidsParType.getOrDefault(currentType, 0.0);
    }

    /**
     * Vérifie si ce trajet contient une modalité de transport spécifique.
     * @param modalite La modalité de transport à vérifier.
     * @return true si le trajet contient la modalité, false sinon.
     */
    public boolean contains(ModaliteTransport modalite) {
        for (Trancon trancon : trancons) {
            if (trancon instanceof Arete) {
                ModaliteTransport modaliteDeLArete = ((Arete) trancon).getModalite();
                if (Objects.equals(modaliteDeLArete, modalite)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Redéfinition de la méthode equals pour comparer deux trajets.
     * Deux trajets sont considérés égaux s'ils ont les mêmes trançons
     * dans le même ordre et que les trançons sont égaux deux à deux.
     * @param obj L'objet à comparer.
     * @return true si les trajets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Trajet other = (Trajet) obj;
        return trancons.equals(other.trancons);
    }

    // Getters et setters

    public List<Trancon> getTrancons() {
        return trancons;
    }

    public void setTrancons(List<Trancon> trancons) {
        this.trancons = trancons;
    }

    public Map<TypeCout, Double> getPoidsParType() {
        return poidsParType;
    }

    public void setPoidsParType(Map<TypeCout, Double> poidsParType) {
        this.poidsParType = poidsParType;
    }

    public TypeCout getCurrentType() {
        return currentType;
    }

    public void setCurrentType(TypeCout currentType) {
        this.currentType = currentType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Trajet(");
        for (Trancon trancon : trancons) {
            sb.append(((Arete)trancon)+ ", ");
        }
        sb.replace(sb.length()-2, sb.length(), "| ");
        for (TypeCout tc : poidsParType.keySet()) {
            sb.append( tc.toString()+ ": " + poidsParType.get(tc) + tc.getSymbole() + ", "); 
        }
        sb.replace(sb.length()-2, sb.length(), "");
        sb.append(")");
        return sb.toString();
    }

    public Chemin getChemin() {
        return chemin;
    }

    public void setChemin(Chemin chemin) {
        this.chemin = chemin;
    }
}
