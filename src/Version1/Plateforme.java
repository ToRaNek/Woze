package Version1;

import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae_s2_2024.*;

/**
 * La classe Plateforme représente une plateforme de transport.
 */
public class Plateforme {

    private ArrayList<Structure> structures;
    private ArrayList<Arete> aretes;

    /**
     * Constructeur de la classe Plateforme.
     * @param data Les données pour initialiser la plateforme.
     */
    public Plateforme(String[] data) {
        structures = new ArrayList<>();
        aretes = new ArrayList<>();

        for (String entree : data) {
            String[] split = entree.split(";");

            ModaliteTransport modalite = ModaliteTransport.valueOf(split[2].toUpperCase());

            String nom_depart = nom(split[0], modalite);
            String nom_arrivee = nom(split[1], modalite);

           Structure depart, arrivee;

            // DEPART
            if (!contains(nom_depart)) {
                depart = new Structure(nom_depart);
                structures.add(depart);
            } else {
                depart = getStructure(nom_depart);
            }

            // ARRIVEE
            if (!contains(nom_arrivee)) {
                arrivee = new Structure(nom_arrivee);
                structures.add(arrivee);
            } else {
                arrivee = getStructure(nom_arrivee);
            }

            // ARETE ALLEE - RETOUR
            Arete allee = new Arete(depart, arrivee, modalite, Double.parseDouble(split[3]), Double.parseDouble(split[4]), Double.parseDouble(split[5]));
            Arete retour = new Arete(arrivee, depart, modalite, Double.parseDouble(split[3]), Double.parseDouble(split[4]), Double.parseDouble(split[5]));

            aretes.add(allee);
            aretes.add(retour);
        }
    }

    /**
     * Vérifie si la plateforme contient une structure avec le nom spécifié.
     * @param nom Le nom de la structure à vérifier.
     * @return true si la plateforme contient la structure, sinon false.
     */
    public boolean contains(final String nom) {
        for (Structure structure : structures) {
            if (structure.getNom().equals(nom)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtient la structure avec le nom spécifié.
     * @param nom Le nom de la structure à obtenir.
     * @return La structure correspondante, ou null si elle n'est pas trouvée.
     */
    public Structure getStructure(String nom) {
        for (Structure structure : structures) {
            if (structure.getNom().equals(nom)) {
                return structure;
            }
        }
        return null;
    }

    /**
     * Génère un nom de structure en fonction du nom de base et de la modalité de transport.
     * @param structure Le nom de base de la structure.
     * @param modalite La modalité de transport associée.
     * @return Le nom généré pour la structure.
     */
    public static String nom(String structure, ModaliteTransport modalite) {
        String nom;
        switch (modalite) {
            case TRAIN:
                nom = "Gare_de_" + structure;
                break;
            case AVION:
                nom = "Aéroport_de_" + structure;
                break;
            case BUS:
                nom = "Arrêt_de_bus_de_" + structure;
                break;
            default:
                nom = structure; // Si la modalité de transport n'est pas donnée, ça retourne simplement le nom d'origine ( bon après ça devrait pas arrivé mais on sait jamais).
        }
        return nom;
    }

    // Méthodes pour obtenir et modifier les listes de structures et d'arêtes

    /**
     * Obtient la liste des structures de la plateforme.
     * @return La liste des structures.
     */
    public ArrayList<Structure> getStructures() {
        return structures;
    }

    /**
     * Définit la liste des structures de la plateforme.
     * @param structures La nouvelle liste des structures.
     */
    public void setStructures(ArrayList<Structure> structures) {
        this.structures = structures;
    }

    /**
     * Obtient la liste des arêtes de la plateforme.
     * @return La liste des arêtes.
     */
    public ArrayList<Arete> getAretes() {
        return aretes;
    }

    /**
     * Définit la liste des arêtes de la plateforme.
     * @param aretes La nouvelle liste des arêtes.
     */
    public void setAretes(ArrayList<Arete> aretes) {
        this.aretes = aretes;
    }

    // Méthodes pour ajouter et supprimer des arêtes et des structures

    /**
     * Ajoute une arête à la liste des arêtes de la plateforme.
     * @param arete L'arête à ajouter.
     */
    public void addArete(Arete arete) {
        aretes.add(arete);
    }

    /**
     * Ajoute une structure à la liste des structures de la plateforme.
     * @param structure La structure à ajouter.
     */
    public void addStructure(Structure structure) {
        structures.add(structure);
    }

    /**
     * Supprime une arête de la liste des arêtes de la plateforme.
     * @param arete L'arête à supprimer.
     */
    public void removeArete(Arete arete) {
        aretes.remove(arete);
    }

    /**
     * Supprime une arête de la liste des arêtes de la plateforme en utilisant son index.
     * @param index L'index de l'arête à supprimer.
     */
    public void removeArete(int index) {
        removeArete(aretes.get(index));
    }
    
    /**
     * Supprime une structure de la liste des structures de la plateforme.
     * @param structure La structure à supprimer.
     */
    public void removeStructure(Structure structure) {
        structures.remove(structure);
    }
    
    /**
     * Supprime une structure de la liste des structures de la plateforme en utilisant son index.
     * @param index L'index de la structure à supprimer.
     */
    public void removeStructure(int index) {
        removeStructure(structures.get(index));
    }
    
    /**
     * Construit un graphe orienté avec les coûts spécifiés comme critère.
     * @param critere Le critère de coût.
     * @return Le graphe orienté construit.
     */
    public MultiGrapheOrienteValue buildGraph(TypeCout critere) {
        MultiGrapheOrienteValue g = new MultiGrapheOrienteValue();
        for (Structure structure : structures) {
            g.ajouterSommet(structure);
        }
        for (Arete arete : aretes) {
            g.ajouterArete(arete, arete.getCouts(critere));
        }
        return g;
    }
    
    /**
     * Construit un graphe orienté avec les coûts spécifiés comme critère.
     * @param critere Le critère de coût.
     * @return Le graphe orienté construit.
     */
    public MultiGrapheOrienteValue buildGraph(String critere) {
        for (TypeCout value : TypeCout.values()) {
            if (TypeCout.valueOf(critere.toUpperCase()) == value) {
                return buildGraph(value);
            }
        }
        System.err.println("Le critère n'est pas valide");
        return null;
    }
    
    /**
     * Cherche les k plus courts chemins entre deux structures dans le graphe avec le critère spécifié.
     * @param graphe Le graphe dans lequel rechercher les chemins.
     * @param depart La structure de départ.
     * @param arrivee La structure d'arrivée.
     * @param k Le nombre de chemins à trouver.
     * @return La liste des k plus courts chemins.
     */
    public static List<Chemin> chercherPlusCourtsChemins(MultiGrapheOrienteValue graphe,Structure depart,Structure arrivee, int k) {
        return AlgorithmeKPCC.kpcc(graphe, depart, arrivee, k);
    }
    
    /**
     * Cherche les k plus courts chemins entre deux structures dans le graphe avec le critère spécifié.
     * @param depart La structure de départ.
     * @param arrivee La structure d'arrivée.
     * @param crit Le critère de coût.
     * @param k Le nombre de chemins à trouver.
     * @return La liste des k plus courts chemins.
     */
    public List<Chemin> chercherPlusCourtsChemins(Structure depart,Structure arrivee, TypeCout crit, int k) {
        return AlgorithmeKPCC.kpcc(buildGraph(crit), depart, arrivee, k);
    }
    
    /**
     * Cherche les k plus courts chemins entre deux structures dans le graphe avec le critère spécifié.
     * @param depart La structure de départ.
     * @param arrivee La structure d'arrivée.
     * @param crit Le critère de coût.
     * @param k Le nombre de chemins à trouver.
     * @return La liste des k plus courts chemins.
     */
    public List<Chemin> chercherPlusCourtsChemins(Structure depart,Structure arrivee, String crit, int k) {
        return AlgorithmeKPCC.kpcc(buildGraph(crit), depart, arrivee, k);
    }
    
    /**
     * Renvoie une représentation textuelle de la plateforme sous forme de chaîne de caractères.
     * @return Une représentation textuelle de la plateforme.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
    
        sb.append("Structures:\n");
        for (Structure structure : structures) {
            sb.append(structure).append("\n");
        }
    
        sb.append("\nAretes:\n");
        for (Arete arete : aretes) {
            sb.append(arete).append("\n");
        }
    
        return sb.toString();
    }
}
    
