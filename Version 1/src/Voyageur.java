import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import fr.ulille.but.sae_s2_2024.*;

/**
 * La classe Voyageur permet de gérer les fonctionnalités liées aux graphes et aux meilleurs trajets entre les différentes plateformes.
 */
public class Voyageur {
    
    public static String[] data = new String[]{
        "villeA;villeB;Train;60;1.7;80",
        "villeB;villeD;Train;22;2.4;40",
        "villeA;villeC;Train;42;1.4;50",
        "villeB;villeC;Train;14;1.4;60",
        "villeC;villeD;Avion;110;150;22",
        "villeC;villeD;Train;65;1.2;90"}; // rpochainement dans un fichier ( csv, txt ou autre)

    public static MultiGrapheOrienteValue G = buildGraph(data, 2); // Utilisation du temps comme critère 0: prix 1. co2 2.temps

    /**
     * Construit un graphe à partir des données fournies.
     * 
     * @param data le tableau de données contenant les informations sur les trajets
     * @param critere le critère à utiliser pour déterminer le poids des arêtes
     * @return le graphe construit à partir des données
     */
    public static MultiGrapheOrienteValue buildGraph(String[] data, int critere) {        
        MultiGrapheOrienteValue G = new MultiGrapheOrienteValue();
        
        // Map pour stocker les lieux créés afin d'éviter de les recréer plusieurs fois
        Map<String, Lieu> lieuMap = new HashMap<>();
        String[] dataCopy = Arrays.copyOf(data, data.length);

        // Parcours des données pour construire le graphe
        for (String entree : dataCopy) {
            String[] data_split = entree.split(";"); // Séparation des données

            // Extraction des informations de dataCopy
            ModaliteTransport modaliteDeTransport = ModaliteTransport.valueOf(data_split[2].toUpperCase());
            String plateforme1 = meilleurNom(data_split[0], modaliteDeTransport);
            String plateforme2 = meilleurNom(data_split[1], modaliteDeTransport);
            double prix = Integer.parseInt(data_split[3]);
            double co2 = Double.parseDouble(data_split[4]);
            double temps = Integer.parseInt(data_split[5]);

            // Création ou récupération des lieux correspondant aux plateformes
            Lieu endroit1 = lieuMap.getOrDefault(plateforme1, new Plateforme(plateforme1, modaliteDeTransport));
            Lieu endroit2 = lieuMap.getOrDefault(plateforme2, new Plateforme(plateforme2, modaliteDeTransport));

            // Ajout des lieux au graphe
            G.ajouterSommet(endroit1);
            G.ajouterSommet(endroit2);
            
            // Ajout des lieux à la map pour éviter de les recréer
            lieuMap.put(plateforme1, endroit1);
            lieuMap.put(plateforme2, endroit2);

            // Création des arêtes
            Arrete arrete1 = new Arrete(endroit1, endroit2, modaliteDeTransport);
            Arrete arrete2 = new Arrete(endroit2, endroit1, modaliteDeTransport);
            
            // Détermination du poids des arêtes en fonction du critère
            double poids = 0;
            switch (critere) {
                case 0: poids = prix; break;
                case 1: poids = co2; break;
                case 2: poids = temps; break;
                default: throw new IllegalArgumentException("Invalid critere value.");
            }

            // Ajout des arêtes au graphe
            G.ajouterArete(arrete1, poids);
            G.ajouterArete(arrete2, poids);
        }
        System.out.println("\n"+G+"\n"); // pour se repéré un peu :)
        return G;
    }

    /**
     * Retourne un meilleur nom plus adapté pour une plateforme en fonction de sa modalité de transport.
     * 
     * @param nom le nom de la plateforme
     * @param modaliteTransport la modalité de transport de la plateforme
     * @return un nom pour la plateforme
     */
    public static String meilleurNom(String nom, ModaliteTransport modaliteTransport) {
        switch (modaliteTransport) {
            case TRAIN:
                return "Gare_" + nom;
            case AVION:
                return "Aéroport_" + nom;
            case BUS:
                return "Arrêt_de_bus_" + nom;
            default:
                return nom; // Si la modalité de transport n'est pas spécifiée, retourne simplement le nom d'origine (ça devrait pas arriver mais bon).
        }
    }
    
    /**
     * Recherche et affiche les meilleurs chemins entre deux plateformes.
     * 
     * @param Graph le graphe contenant les informations sur les trajets
     * @param plateforme1 la plateforme de départ
     * @param plateforme2 la plateforme d'arrivée
     * @param n le nombre de chemins à trouver
     * @return une chaîne de caractères représentant les meilleurs chemins
     */
    public static String MeilleursChemins(MultiGrapheOrienteValue Graph, Lieu plateforme1, Lieu plateforme2, int n ){
        StringBuilder str = new StringBuilder(); 
        // TODO régler le problème
        System.out.println("err ligne 116 -Gaspard aucune idée de comment je peux le résoudre... je verrais avec vous");
        List<Chemin> shortestPaths = AlgorithmeKPCC.kpcc(Graph, plateforme1, plateforme2, n);
        
        for (int i = 0; i < shortestPaths.size(); i++) {
            Chemin path = shortestPaths.get(i);
            str.append(i + 1).append(") ").append(plateforme1).append(" à ").append(plateforme2)
               .append(" en passant par ").append(path.aretes()).append(". Durée : ").append(path.poids()).append(" minutes\n");
        }
        
        return str.toString(); 
    }    

    /**
     * Affiche un menu et gère les actions de l'utilisateur vis à vis de ce menu.
     * 
     * @param G le graphe contenant les informations sur les trajets
     */
    public static void gestionMenu(MultiGrapheOrienteValue G) {
        Scanner scanner = new Scanner(System.in);
        int choix;
        do {
            afficherMenu();
            choix = scanner.nextInt();
            switch (choix) {
                case 1:
                    System.out.println("Affichage du graphe :\n");
                    System.out.println(G+"\n");
                    break;
                case 2:
                    rechercheMeilleursChemins(G);
                    break;
                case 3:
                    System.out.println("Merci d'avoir utilisé le programme !");
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer !");
                    break;
            }
        } while (choix != 3);
    }

   /**
     * Recherche et affiche les meilleurs chemins entre deux plateformes en fonction d'un critère choisi.
     * 
     * @param G le graphe contenant les informations sur les trajets
     */
    public static void rechercheMeilleursChemins(MultiGrapheOrienteValue G) {
        Scanner scanner = new Scanner(System.in);
        
        // Affichage des sommets sous forme de menu numéroté
        System.out.println("Veuillez choisir la plateforme de départ :");
        List<Lieu> sommets = new ArrayList<>(G.sommets());
        for (int i = 0; i < sommets.size(); i++) {
            System.out.println((i + 1) + ". " + sommets.get(i));
        }
        
        // Demande à l'utilisateur de choisir la plateforme de départ
        int choixDepart = scanner.nextInt();
        scanner.nextLine(); 
        
        // Récupération de la plateforme de départ
        Lieu plateformeDepart = sommets.get(choixDepart - 1);
        System.out.println(plateformeDepart);

        // Affichage des sommets pour la plateforme d'arrivée
        System.out.println("Veuillez choisir la plateforme d'arrivée :");
        for (int i = 0; i < sommets.size(); i++) {
            System.out.println((i + 1) + ". " + sommets.get(i));
        }
        
        // Demande à l'utilisateur de choisir la plateforme d'arrivée
        int choixArrivee = scanner.nextInt();
        scanner.nextLine(); 
        
        
        // Récupération de la plateforme d'arrivée
        Lieu plateformeArrivee = sommets.get(choixArrivee - 1);
        System.out.println(plateformeArrivee.getClass());

        
        // Demande à l'utilisateur de choisir le critère
        int critere;
        do {
            // Demande à l'utilisateur de choisir le critère
            System.out.println("Veuillez choisir le critère (1: prix, 2: éco-responsable, 3: temps) :");
            critere = scanner.nextInt() - 1;

            // Vérifie si le critère est valide (1, 2 ou 3)
            if (critere < 0 || critere > 2) {
                System.out.println("Critère invalide, veuillez réessayer !");
            }
        } while (critere < 0 || critere > 2);

        
        // Construction du graphe en fonction du critère choisi
        MultiGrapheOrienteValue graphCritere = buildGraph(data, critere);

        // Demande à l'utilisateur le nombre de chemins à trouver
        System.out.println("Veuillez saisir le nombre de chemins à trouver :");
        int nombreChemins = scanner.nextInt();
        
        // Recherche et affichage des meilleurs chemins
        String meilleursChemins = MeilleursChemins(graphCritere, plateformeDepart, plateformeArrivee, nombreChemins);
        String[] lignes = meilleursChemins.split("\n"); 

        if (lignes.length > 1) {
            System.out.println("Les " + lignes.length + " trajets recommandés de " + plateformeDepart + " à " + plateformeArrivee + " sont :");
            System.out.println(meilleursChemins);
        } else if (lignes.length == 1) {
            System.out.println("Le seul trajet recommandé de " + plateformeDepart + " à " + plateformeArrivee + " est :");
            System.out.println(meilleursChemins);
        } else {
            System.out.println("Aucun trajet recommandé de " + plateformeDepart + " à " + plateformeArrivee + ".");
        }
    }
    
    /**
     * Affiche le menu principal.
     */
    public static void afficherMenu() {
        System.out.println("Menu :");
        System.out.println("1. Afficher le graphe");
        System.out.println("2. Trouver les meilleurs chemins entre deux plateformes");
        System.out.println("3. Quitter");
        System.out.println("Veuillez saisir votre choix :");
    }

    public static void main(String[] args) {
        gestionMenu(G); // ctrl+clic sur la méthode pour se tp dessus

        // PS: j'ai pas finis - Gaspard
    }
}
