package version3.utils.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Afficher les chemins les plus courts avec un critère avec poids.
    public static List<Chemin> KPlusCourtsChemins(Plateforme p, final Structure depart, final Structure arrivee, final TypeCout crit, final double p_max, final int k) {
        // Récupérer les k chemins du premier graphe avec le premier critère
        final List<Chemin> chemins = AlgorithmeKPCC.kpcc(p.getGraphes().get(crit), depart, arrivee, k);

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
            if (poids2 > p_max) {
                // Supprimer le chemin de la liste
                chemins.remove(i);
                i--; // Ajuster l'index en conséquence
            }
        }

        return chemins; // Retourner la liste des chemins restants
    }

    //  Afficher les chemins avec une modalité spécifique (ou deux ou trois).
    public static List<Chemin> cheminsParTransport(Plateforme p, final String depart, final String arrivee, List<ModaliteTransport> modaliteTransports, int k) {
        List<Chemin> tousLesChemins = new ArrayList<>();
        Map<TypeCout, Double> M = new HashMap<>();
        List<Chemin> chemins = kpccUltime(p, depart, arrivee, M, modaliteTransports, k);
        tousLesChemins.addAll(chemins);

        // Limiter la liste aux k premiers chemins
        if (tousLesChemins.size() > k) {
            tousLesChemins = tousLesChemins.subList(0, k);
        }

        return tousLesChemins;
    }

    /**
     * Méthode pour trouver les chemins les plus courts respectant plusieurs critères et modalités de transport.
     *
     * @param p             La plateforme contenant les graphes pour chaque critère.
     * @param villeDepart   La ville de départ du chemin.
     * @param villeArrivee  La ville d'arrivée du chemin.
     * @param poidsMaximaux Map associant chaque critère à son poids maximal à respecter.
     * @param modalites     Liste des modalités de transport que les chemins doivent posséder.
     * @param k             Nombre maximum de chemins à retourner.
     * @return Une liste des chemins les plus courts respectant tous les critères et modalités spécifiées.
     */
    public static List<Chemin> kpccUltime(Plateforme p, String villeDepart, String villeArrivee, Map<TypeCout, Double> poidsMaximaux, List<ModaliteTransport> modalites, int k) {
        List<Chemin> chemins = new ArrayList<>();

        // Récupération des structures de départ et d'arrivée
        List<Structure> structuresDepart = p.getStructuresFrom(villeDepart);
        List<Structure> structuresArrivee = p.getStructuresFrom(villeArrivee);

        // Parcours de chaque critère avec son poids maximal
        for (TypeCout critere : poidsMaximaux.keySet()) {
            double poidsMax = poidsMaximaux.getOrDefault(critere, Double.MAX_VALUE);

            // Liste pour stocker tous les chemins trouvés pour ce critère
            List<Chemin> cheminsCritere = new ArrayList<>();

            // Parcours de toutes les combinaisons de structures de départ et d'arrivée
            for (Structure depart : structuresDepart) {
                for (Structure arrivee : structuresArrivee) {
                    // Trouver les chemins les plus courts entre chaque paire de structures
                    List<Chemin> cheminsPartiels = KPlusCourtsChemins(p, depart, arrivee, critere, poidsMax, k);

                    // Filtrer les chemins selon les modalités choisies
                    List<Chemin> cheminsFiltres = new ArrayList<>();
                    for (Chemin chemin : cheminsPartiels) {
                        if (verifierModalites(chemin, modalites)) {
                            cheminsFiltres.add(chemin);
                        }
                    }

                    // Ajouter les chemins filtrés à la liste des chemins pour ce critère
                    cheminsCritere.addAll(cheminsFiltres);
                }
            }

            // Ajouter les chemins trouvés pour ce critère à la liste globale
            chemins.addAll(cheminsCritere);
        }

        // Trier les chemins par ordre croissant de poids total
        chemins.sort(Comparator.comparingDouble(Chemin::poids));

        return chemins;
    }

    /**
     * Méthode pour trouver les trajets les plus courts respectant plusieurs critères et modalités de transport.
     *
     * @param p             La plateforme contenant les graphes pour chaque critère.
     * @param villeDepart   La ville de départ du trajet.
     * @param villeArrivee  La ville d'arrivée du trajet.
     * @param poidsMaximaux Map associant chaque critère à son poids maximal à respecter.
     * @param modalites     Liste des modalités de transport que les trajets doivent posséder.
     * @param k             Nombre maximum de trajets à retourner.
     * @return Une liste des trajets les plus courts respectant tous les critères et modalités spécifiées.
     */
    public static List<Trajet> kpccUltimeTrajets(Plateforme p, String villeDepart, String villeArrivee, Map<TypeCout, Double> poidsMaximaux, List<ModaliteTransport> modalites, int k) {
        List<Chemin> chemins = kpccUltime(p, villeDepart, villeArrivee, poidsMaximaux, modalites, k);

        List<Trajet> trajets = new ArrayList<>();
        for (Chemin chemin : chemins) {
            List<Trancon> trancons = chemin.aretes();
            List<Arete> aretes = new ArrayList<>();

            for (Trancon trancon : trancons) {
                aretes.add((Arete)trancon);
            }
            
            // Créer un Trajet à partir des trançons et du poids
            if (chemin != null) {
                Trajet trajet = new Trajet(trancons, chemin);
                trajets.add(trajet);
            }
        }

        // Limiter la liste aux k premiers chemins
        if (trajets.size() > k) {
            trajets = trajets.subList(0, k);
        }

        return trajets;
    }


    /** Méthode pour vérifier si un chemin satisfait au moins l'une des modalités spécifiées ou la modalité null.
     * @param chemin Le chemin à vérifier.
     * @param modalites Liste des modalités de transport à vérifier.
     * @return true si le chemin satisfait au moins une des modalités spécifiées ou la modalité null, false sinon.
     */
    private static boolean verifierModalites(Chemin chemin, List<ModaliteTransport> modalites) {
        
        ModaliteTransport[] tmp = ModaliteTransport.values();
        List<ModaliteTransport> M = new ArrayList<>();

        for (ModaliteTransport modaliteTransport : tmp) {
            M.add(modaliteTransport);
        }

        for (ModaliteTransport modaliteTransport : modalites) {
            if (M.contains(modaliteTransport)) {
                M.remove(modaliteTransport);
            }
        }
        for (ModaliteTransport modaliteTransport : M) {
            boolean found = false;
            for (Trancon trancon : chemin.aretes()) {
                Arete arete = (Arete) trancon;
                if (arete.getModalite() == modaliteTransport || (arete.getModalite() == null)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }

        

        return true;
    }



}
