package Version1;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import fr.ulille.but.sae_s2_2024.*;

public class Plateforme {

    private static HashMap<TypeCout, MultiGrapheOrienteValue> graphes;
    private static Scanner scanner = new Scanner(System.in);
    private static Voyageur voyageur;
    private static HashMap<String,Ville> test= new HashMap<>();

    public static void buildGraphs(String[] data) {
        graphes = new HashMap<>();
        HashMap<String, Ville> villes = new HashMap<>();
        HashMap<String, Structure> structures = new HashMap<>();
        for (TypeCout cout : TypeCout.values()) {
            MultiGrapheOrienteValue graphe = new MultiGrapheOrienteValue();
            graphes.put(cout, graphe);
        }
        for (String entree : data) {
            String[] data_split = entree.split(";");

            ModaliteTransport modalite = ModaliteTransport.valueOf(data_split[2].toUpperCase());
            String nomville1=data_split[0];
            String nomville2=data_split[1];

            String nomstruct1 = nom(nomville1, modalite);
            String nomstruct2 = nom(nomville2, modalite);

            boolean verifville1 = villes.keySet().contains(nomville1); 
            boolean verifville2 = villes.keySet().contains(nomville2);

            boolean verfistruct1 = villes.keySet().contains(nomstruct1);
            boolean verifstruct2 = villes.keySet().contains(nomstruct2); 

            Double[] couts = new Double[]{Double.parseDouble(data_split[3]),Double.parseDouble(data_split[4]),Double.parseDouble(data_split[5])};
            
            Ville ville1;
            Ville ville2;

            Structure structure1;
            Structure structure2;


            if (verifville1) { 
                ville1 = villes.get(nomville1);
            }else{
                ville1 = new Ville(nomville1);
                
            }
            if (verifville2) { 
                ville2 = villes.get(nomville2);
            }else{
                
            }
            if (verfistruct1) { 
                
            }else{
                
            }
            if (verifstruct2) { 
                
            }else{
                
            }
            
            // verif si 0 ou 1 est la ville deja dans la map villes    
                // si non alors ça cree la ville et ça la met dans villeX
                // si oui ça cherche l'objet ville deja présent dans la map une variable Ville villeX
            
            // verif si 0 ou 1 est une structure deja dans la map structures    
                // si non alors ça cree la structure structureX et l'arrete entre elle est la villeX de cout 0
                // si oui ça cherche l'objet Structure deja présent dans la map une variable Structure structureX
            
            // boucle qui ajoute les lieu dans chacun dez 3 graphes si leur verif est true
                // if verif... true
                // graphe.get(cout).ajoutsommet
                // etc
            // boucle qui cree les 6 arrete (allee - retour) entre structure1 et structure2
                // allee co2
                // retour co2
                // allee prix
                // etc..
            // boucle qui parcours la liste des veleurs de TypeCout.values() -> cout
                // graphes.get(cout).ajouterarete
        }
    }

    public static String nom(String nomVille, ModaliteTransport modalite ){
        String nom;
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
        return nom;
    }


    

    public static List<Chemin> chercherPlusCourtsChemins(Ville depart, Ville arrivee, String crit, int k) {
        return AlgorithmeKPCC.kpcc(graphes.get(TypeCout.valueOf(crit)), depart, arrivee, k);
    }
    // TODO faire un affichage correcte
    // private static void afficherGraphe() {
    //     System.out.println("Graphe:");
    //     System.out.print("Villes: [");
    //     for (Lieu lieu : graphes.get(TypeCout.CO2).sommets()) {
    //         System.out.print(((Ville)lieu).getNom() + ",");
    //     }
    //     System.out.print("], Structures: [");
    //     for (Lieu lieu : graphes.get(TypeCout.CO2).sommets()) {
    //         System.out.print(((Structure)lieu).getNom() + ",");
    //     }
    //     System.out.print("], Arêtes: [");
    //     for (Trancon trancon : graphes.get(TypeCout.CO2).aretes()) {
    //         Arete arete = (Arete )trancon;
    //         System.out.print("(" + arete.getDepart().getNom() + "," + arete.getArrivee().getNom() +
    //                 ":CO2=" + arete.getCout(TypeCout.CO2) + ",prix=" + arete.getCout(TypeCout.PRIX) + ",temps=" + arete.getCout(TypeCout.TEMPS) + "),");
    //     }
    //     System.out.println("]");
    // }

    
    public static void main(String[] args) {
        // Chargement des données
        String[] data = new String[]{
            "villeA;villeB;Train;60;1.7;80",
            "villeB;villeD;Train;22;2.4;40",
            "villeA;villeC;Train;42;1.4;50",
            "villeB;villeC;Train;14;1.4;60",
            "villeC;villeD;Bus;110;150;22",
            "villeC;villeD;Train;65;1.2;90"
        };
        Plateforme.buildGraphs(data);

        String str [] = graphes.toString().split(",");
        for (String string : str) {
            System.out.println(str);            
        };
     }

}

