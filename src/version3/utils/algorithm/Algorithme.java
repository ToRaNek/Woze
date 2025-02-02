package version3.utils.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.ulille.but.sae_s2_2024.AlgorithmeKPCC;
import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.MultiGrapheOrienteValue;
import fr.ulille.but.sae_s2_2024.Trancon;
import version3.graphe.Plateforme;
import version3.graphe.Arete;
import version3.graphe.Structure;
import version3.graphe.Trajet;
import version3.graphe.TypeCout;

public class Algorithme {
    // Afficher les chemins les plus courts avec un critère sans poids.
    public static List<Chemin> simplePCC(final MultiGrapheOrienteValue graphe,final Structure depart,final Structure arrivee,final int k) {
        return AlgorithmeKPCC.kpcc(graphe, depart, arrivee, k);
    }

    Map<TypeCout, Double> M = new HashMap<>();


    /**
     * Trouve les k plus courts chemins entre deux structures en utilisant un critère de coût spécifique,
     * et en s'assurant que le poids total ne dépasse pas une limite donnée.
     *
     * Cette méthode utilise un algorithme pour trouver les k plus courts chemins dans un graphe
     * en fonction d'un critère de coût donné. Elle filtre ensuite ces chemins pour s'assurer
     * que leur poids total ne dépasse pas la limite spécifiée et élimine les doublons.
     *
     * @param p La plateforme contenant les graphes et les structures.
     * @param depart La structure de départ.
     * @param arrivee La structure d'arrivée.
     * @param crit Le type de coût à utiliser pour le calcul des poids.
     * @param p_max Le poids maximal autorisé pour le critère spécifié.
     * @param k Le nombre de chemins à retourner.
     * @return Une liste des k plus courts chemins respectant la limite de poids spécifiée, sans doublons.
     */
    public static List<Chemin> KPlusCourtsChemins(Plateforme p, final Structure depart, final Structure arrivee, final TypeCout crit, final double p_max, final int k) {
        // Récupérer les k chemins du premier graphe avec le premier critère
        final List<Chemin> chemins = AlgorithmeKPCC.kpcc(p.getGraphes().get(crit), depart, arrivee, k);

        // Utiliser un Set pour stocker les chemins uniques
        Set<Chemin> cheminsUniques = new HashSet<>();

        // Parcourir chaque chemin
        for (int i = 0; i < chemins.size(); i++) {
            final Chemin chemin = chemins.get(i);
            final List<Trancon> trancons = chemin.aretes(); // Obtenir la liste des trançons du chemin
            double poids2 = 0; // Initialiser le poids pour le deuxième critère

            // Calculer le poids pour le deuxième critère en parcourant chaque trançon
            for (final Trancon trancon : trancons) {
                final Arete arete = p.getArete((Structure)trancon.getDepart(), (Structure)trancon.getArrivee()); // Obtenir l'arête correspondante
                poids2 += arete.getCout(crit); // Ajouter le poids de l'arête pour le deuxième critère
            }

            // Vérifier si le poids pour le deuxième critère dépasse la limite
            if (poids2 <= p_max) {
                // Ajouter le chemin au Set des chemins uniques
                cheminsUniques.add(chemin);
            }
        }

        // Convertir le Set en List et la retourner
        return new ArrayList<>(cheminsUniques);
    }

    /**
     * 
     *
     * @param p             La plateforme contenant les graphes pour chaque critère.
     * @param villeDepart   La ville de départ du chemin.
     * @param villeArrivee  La ville d'arrivée du chemin.
     * @param poidsMaximaux Map associant chaque critère à son poids maximal à respecter.
     * @param modalites     Liste des modalités de transport que les chemins doivent posséder.
     * @param k             Nombre maximum de chemins à retourner.
     * @return Une liste des Trajets les plus courts respectant tous les critères et modalités spécifiées.
     */
    public static List<Trajet> kpccUltime(Plateforme p, String villeDepart, String villeArrivee, Map<TypeCout, Double> poidsMaximaux, List<ModaliteTransport> modalites, int k) {
        List<Trajet> trajetsUnique = new ArrayList<>(); // Liste pour stocker les chemins uniques
    
        // Récupération des structures de départ et d'arrivée
        List<Structure> structuresDepart = p.getStructuresFrom(villeDepart);
        List<Structure> structuresArrivee = p.getStructuresFrom(villeArrivee);
    
        // Parcours de toutes les combinaisons de structures de départ et d'arrivée
        for (Structure depart : structuresDepart) {
            for (Structure arrivee : structuresArrivee) {
                // Trouver les chemins les plus courts entre chaque paire de structures pour chaque critère
                for (TypeCout critere : TypeCout.values()) {
                    double poidsMax = poidsMaximaux.getOrDefault(critere, Double.MAX_VALUE);
                    List<Chemin> cheminsPartiels = KPlusCourtsChemins(p, depart, arrivee, critere, poidsMax, k);
    
                    // Ajouter les trajets potentiels en évitant les doublons
                    for (Chemin chemin : cheminsPartiels) {
                        List<Arete> ar = new ArrayList<>();
                        for (Trancon tr : chemin.aretes()) {
                            ar.add(((Arete)tr));
                        }
                        Trajet trajet = new Trajet(ar);
                        if (!thereIs(trajetsUnique, trajet)) {
                            trajet.setCurrentType(p.getCurrentCrit());
                            trajetsUnique.add(trajet);
                        }
                    }
                }
            }
        }
    
        // Création d'une nouvelle liste pour les trajets filtrés
        List<Trajet> trajetsFiltres = new ArrayList<>();
        for (Trajet trajet : trajetsUnique) {
            if (verifierModalites(trajet, modalites) && verifierCouts(trajet, poidsMaximaux)) {
                trajetsFiltres.add(trajet);
            }
        }
    
        // Trier les trajets par ordre croissant de poids total pour le critère actuel
        Collections.sort(trajetsFiltres);
    
        // Limiter la liste aux k premiers chemins
        if (trajetsFiltres.size() > k) {
            trajetsFiltres = trajetsFiltres.subList(0, k);
        }
    
        return trajetsFiltres;
    }
    
    private static boolean verifierCouts(Trajet trajet, Map<TypeCout, Double> poidsMaximaux) {
        for (TypeCout critere : TypeCout.values()) {
            double cout = trajet.getPoids(critere);
            double poidsMax = poidsMaximaux.getOrDefault(critere, Double.MAX_VALUE);
            if (cout > poidsMax) {
                return false;
            }
        }
        return true;
    }
    

    
    
     /**
     * Vérifie si un trajet donné existe dans une liste de trajets.
     * 
     * @param trajets La liste de trajets dans laquelle chercher.
     * @param trajet Le trajet à vérifier.
     * @return true si le trajet existe dans la liste, false sinon.
     */
    private static boolean thereIs(List<Trajet> trajetsUnique, Trajet newTrajet) {
        for (Trajet trajet : trajetsUnique) {
            if (trajet.equals(newTrajet)) {
                return true;
            }
        }
        return false;
    }

    /** Méthode pour vérifier si un trajet satisfait au moins l'une des modalités spécifiées ou la modalité null.
     * @param trajet Le trajet à vérifier.
     * @param modalites Liste des modalités de transport à vérifier.
     * @return true si le trajet satisfait au moins une des modalités spécifiées ou la modalité null, false sinon.
     */
    private static boolean verifierModalites(Trajet trajet, List<ModaliteTransport> modalites) {
        for (Trancon tr : trajet.getTrancons()) {
            ModaliteTransport modalite = ((Arete)tr).getModalite();
            
            if (modalite != null && !modalites.contains(modalite)) {
                return false; // Modalité non autorisée trouvée
            }
        }
        return true; // Aucune modalité non autorisée trouvée
    }


}