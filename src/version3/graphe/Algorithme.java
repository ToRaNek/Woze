package version3.graphe;

import java.util.List;

import fr.ulille.but.sae_s2_2024.AlgorithmeKPCC;
import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.MultiGrapheOrienteValue;
import fr.ulille.but.sae_s2_2024.Trancon;

public class Algorithme {

   public static List<Chemin> KPlusCourtsChemins(Plateforme2 p, final Structure depart, final Structure arrivee, final TypeCout crit1, final int k, TypeCout crit2, double p_max) {
        // Récupérer les k chemins du premier graphe avec le premier critère
        final List<Chemin> chemins = AlgorithmeKPCC.kpcc(p.getGraphes().get(crit1), depart, arrivee, k);
    
        // Parcourir chaque chemin
        for (final Chemin chemin : chemins) {
            final List<Trancon> trancons = chemin.aretes(); // Obtenir la liste des trançons du chemin
            double poids2 = 0; // Initialiser le poids pour le deuxième critère
    
            // Calculer le poids pour le deuxième critère en parcourant chaque trançon
            for (final Trancon trancon : trancons) {
                final Arete arete = p.getArete((Structure)trancon.getDepart(), (Structure)trancon.getArrivee()); // Obtenir l'arête correspondante
                poids2 += arete.getCout(crit2); // Ajouter le poids de l'arête pour le deuxième critère
            }
    
            // Vérifier si le poids pour le deuxième critère dépasse la limite
            if (poids2 > p_max) {
                // Supprimer le chemin de la liste
                chemins.remove(chemin);
                break; // Arrêter la boucle, puisque nous avons déjà supprimé un chemin
            }
        }
    
        return chemins; // Retourner la liste des chemins restants
    }

    public static List<Chemin> simplePCC(final MultiGrapheOrienteValue graphe,final Structure depart,final Structure arrivee,final int k) {
        return AlgorithmeKPCC.kpcc(graphe, depart, arrivee, k);
    }
}
