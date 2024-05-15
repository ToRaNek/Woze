package Version1;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import fr.ulille.but.sae_s2_2024.*;

public class Plateforme {

    private static HashMap<TypeCout, MultiGrapheOrienteValue> graphes = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Voyageur voyageur;
    private static HashMap<String,Ville> test= new HashMap<>();

    public static void buildGraphs(String[] data) {
        for (TypeCout value : TypeCout.values()) {
            MultiGrapheOrienteValue graphe = new MultiGrapheOrienteValue();
            HashMap<String, Ville> villes = new HashMap<>();
            HashMap<String, Structure> structures = new HashMap<>();

            for (String entree : data) {
                String[] data_split = entree.split(";");
                Ville depart = getOrCreateVille(graphe, data_split[0], villes);
                Structure structureDepart = getOrCreateStructure(graphe, data_split[0], data_split[2].toUpperCase(), villes, structures);
                Ville arrivee = getOrCreateVille(graphe, data_split[1], villes);
                Structure structureArrivee = getOrCreateStructure(graphe, data_split[1], data_split[2].toUpperCase(), villes, structures);
                double co2 = Double.parseDouble(data_split[4]);
                double temps = Double.parseDouble(data_split[5]);
                double prix = Double.parseDouble(data_split[3]);
                Arete allee = new Arete(depart, structureArrivee, ModaliteTransport.valueOf(data_split[2].toUpperCase()), co2, temps, prix);
                Arete retour = new Arete(arrivee, structureDepart, ModaliteTransport.valueOf(data_split[2].toUpperCase()), co2, temps, prix);
                graphe.ajouterArete(allee, co2);
                graphe.ajouterArete(retour, co2);
            }
            graphes.put(value, graphe);
        }

    }

    private static Ville getOrCreateVille(MultiGrapheOrienteValue graphe, String nom, HashMap<String, Ville> villes) {
        if (!villes.containsKey(nom)) {
            Ville nouvelleVille = new Ville(nom);
            villes.put(nom, nouvelleVille);
            graphe.ajouterSommet(nouvelleVille);
            return nouvelleVille;
        }
        return villes.get(nom);
    }

    private static Structure getOrCreateStructure(MultiGrapheOrienteValue graphe, String nomVille, String modalite, HashMap<String, Ville> villes, HashMap<String, Structure> structures) {
        String nomStructure;
        switch (modalite) {
            case "TRAIN":
                nomStructure = "Gare_de_" + nomVille;
                break;
            case "AVION":
                nomStructure = "Aéroport_de_" + nomVille;
                break;
            case "BUS":
                nomStructure = "Arrêt_de_bus_de_" + nomVille;
                break;
            default:
                nomStructure = nomVille;
        }
        if (!structures.containsKey(nomStructure)) {
            Structure nouvelleStructure = new Structure(nomStructure);
            structures.put(nomStructure, nouvelleStructure);
            Ville villeAssociee = getOrCreateVille(graphe, nomVille, villes);
            villeAssociee.ajouterStructure(nouvelleStructure);
            graphe.ajouterSommet(nouvelleStructure);
            graphe.ajouterArete(new Arete(nouvelleStructure, villeAssociee, ModaliteTransport.valueOf(modalite.toUpperCase()), 0, 0, 0), 0);
            graphe.ajouterArete(new Arete(villeAssociee, nouvelleStructure, ModaliteTransport.valueOf(modalite.toUpperCase()), 0, 0, 0), 0);
            return nouvelleStructure;
        }
        return structures.get(nomStructure);
    }

    public static List<Chemin> chercherPlusCourtsChemins(Ville depart, Ville arrivee, String crit, int k) {
        return AlgorithmeKPCC.kpcc(graphes.get(TypeCout.valueOf(crit)), depart, arrivee, k);
    }

    private static void afficherGraphe() {
        System.out.println("Graphe:");
        System.out.print("Villes: [");
        for (Lieu lieu : graphes.get(TypeCout.CO2).sommets()) {
            System.out.print(((Ville)lieu).getNom() + ",");
        }
        System.out.print("], Structures: [");
        for (Lieu lieu : graphes.get(TypeCout.CO2).sommets()) {
            System.out.print(((Structure)lieu).getNom() + ",");
        }
        System.out.print("], Arêtes: [");
        for (Trancon trancon : graphes.get(TypeCout.CO2).aretes()) {
            Arete arete = (Arete )trancon;
            System.out.print("(" + arete.getDepart().getNom() + "," + arete.getArrivee().getNom() +
                    ":CO2=" + arete.getCout(TypeCout.CO2) + ",prix=" + arete.getCout(TypeCout.PRIX) + ",temps=" + arete.getCout(TypeCout.TEMPS) + "),");
        }
        System.out.println("]");
    }

}

