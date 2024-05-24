package Version2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;

public class Main {

    private static Plateforme p;
    // TODO csv avec les infos de data_villes
    // TODO csv data_correspondances
    private static String[] data_villes = DataExtractor.data_villes;
    private static String[] data_correspondances = DataExtractor.data_correspondances;
    
    // TODO csv avec les infos users
    private static Voyageur user;
    private static Scanner scanner = new Scanner(System.in);

    // méthode pour "effacer" ce qu'il y a sur le terminale ( plus beau pour les yeux)
    public static void clear(){
        // Code pour effacer le terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    


    // méthode qui crée la plateforme
    public static void createPlateforme() {
        // Création de la plateforme p
        p = new Plateforme(data_villes, data_correspondances);
        // System.out.println(p); 
    }

    public static void createUser() {
        System.out.println("Bonjour et bienvenue sur Woze, votre Plateforme de Comparaison d’itinéraires de transport.\n");

        // prenom
        clear();
        System.out.println("Quel est votre Prénom ?");
        String prenom = scanner.nextLine();

        // nom
        System.out.println("Quel est votre Nom ?");
        String nom = scanner.nextLine();



        // // ville ( supprimer pour le moment)
        // clear();
        // System.out.println("De quelle ville venez-vous ?");
        // System.out.println(p.getVilles());
        // String ville = scanner.nextLine();
        // String villeDepart;
        // while(!p.containsVille(ville)) {
        //     System.out.println("Cette ville n'existe pas. Aucune ville ne vous sera associé.");
        //     System.out.println(p.getVilles());
        //      ville = scanner.nextLine();
        // }
        // villeDepart = ville;
        String villeDepart = "Aucune";

        // critere
        clear();
        System.out.println("Quel critère de recherche d'itinéraire vous semble le plus adapté ? (Temps/Co2/Prix)");
        String critere = scanner.nextLine().toUpperCase();
        System.out.println(critere);
        while(!critere.equals("TEMPS") && !critere.equals("CO2") && !critere.equals("PRIX")){
            System.out.println("Critère invalide. (Temps/Co2/Prix)");
            critere = scanner.nextLine().toUpperCase();
        };
        TypeCout crit = TypeCout.valueOf(critere);
    
        // user
        clear();
        user = new Voyageur(prenom, nom, villeDepart, crit);

        // graphe
        p.buildGraph(crit);

    }

    // affiche un menu vite fait pour accéder aux fonctionnalités du futur logiciel
    public static void menu() {
        int choix;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Voir le graph");
            System.out.println("2. Chercher un chemin");
            System.out.println("3. Mes informations");
            System.out.println("4. Quitter");
            System.out.print("Votre choix: ");
            choix = getValidIntInput();
            clear();

            switch (choix) {
                case 1:
                    // affichage du graphe 
                    voirGraph();
                    break;
                case 2:
                    // algo kpcc
                    chercherChemin();
                    break;
                case 3:
                    // info user 
                    afficherInfosUtilisateur();
                    break;
                case 4:
                    // quitter 
                    System.out.println("Au revoir !");
                    break;
                default:
                    // choix invalide 
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        } while (choix != 4);
    }

    // fonction qui permet de ne pas avoir d'exception à cause du scanner pour les int ( désolé si on avait pas le droit j'en pouvais plus ) 
    private static int getValidIntInput() {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide, veuillez saisir un nombre entier.");
            }
        }
    }

    // fonction qui permet de ne pas avoir d'exception à cause du scanner pour les double( désolé si on avait pas le droit j'en pouvais plus ) 
    private static double getValidDoubleInput() {
        while (true) {
            try {
                double input = Double.parseDouble(scanner.nextLine());
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide, veuillez saisir un nombre entier.");
            }
        }
    }

    // affiche le graphe 
    public static void voirGraph() {
        System.out.println(p.getCurrentGraphe());
    }

    // algo kpcc adapter à une utilisation par un utilisateur
    public static void chercherChemin() {
        // départ
        System.out.println("De quelle ville partez-vous ?");
        System.out.println(p.getVilles());
        String villeDepart = scanner.nextLine();
        while (!p.containsVille(villeDepart)) {
            System.out.println("Cette ville n'existe pas. Veuillez entrer une ville valide.");
            System.out.println(p.getVilles());
            villeDepart = scanner.nextLine();
        }
        
        // arrivee 
        clear();
        System.out.println("Quelle est votre destination ?");
        System.out.println(p.getVilles());
        String villeArrivee = scanner.nextLine();
        while (!p.containsVille(villeArrivee)) {
            System.out.println("Cette ville n'existe pas. Veuillez entrer une ville valide.");
            System.out.println(p.getVilles());
            villeArrivee = scanner.nextLine();
        }
        
        // modalité
        clear();
        String moyenTransport;
        do {
            System.out.println("Quel moyen de transport souhaitez-vous utiliser ? (Train, Avion, Bus)");
            moyenTransport = scanner.nextLine().toUpperCase();

            if (!estMoyenTransportValide(moyenTransport)) {
                System.out.println("Ce moyen de transport n'est pas valide. Veuillez entrer un moyen de transport valide (Train, Avion, Bus).");
            } 
            // else if (!lienExiste(p, villeDepart, villeArrivee, moyenTransport)) {
            //     System.out.println("Il n'existe pas de lien entre les deux villes par ce moyen de transport.");
            // }
        } while (!estMoyenTransportValide(moyenTransport) /*|| !lienExiste(p, villeDepart, villeArrivee, moyenTransport) */);

        ModaliteTransport modalite = ModaliteTransport.valueOf(moyenTransport.toUpperCase());
        Structure depart = p.getStructure(Structure.nom(villeDepart, modalite));
        Structure arrivee = p.getStructure(Structure.nom(villeArrivee, modalite));

    
        System.out.println("Combien de chemins souhaitez-vous trouver ?");
        int k = getValidIntInput();

        System.out.println("Quel poids ne doit pas excéder le trajet ?");
        double poids_max = getValidDoubleInput();
        // chemin
        clear();
        List<Chemin> chemins = p.chercherPlusCourtsChemins(depart, arrivee, user.getCritere(), k);
        List<String> chemins_max = new ArrayList<>();
        for (Chemin chemin : chemins) {
            String poidsString = chemin.toString().split("Poids: ")[1].replace(',', '.').replace(')', '0');

            double poidsChemin = Double.parseDouble(poidsString);
        
            if (poidsChemin <= poids_max) {
                chemins_max.add(chemin.toString());
            }
        }
        
    
        if (chemins_max.isEmpty()) {
            System.out.println("Aucun chemin trouvé de " + villeDepart + " à " + villeArrivee + " selon le critère " + user.getCritere() + " et le moyen de transport " + moyenTransport + ".");
        } else if (chemins_max.size() == 1) {
            System.out.println("Chemin le plus court trouvé de " + villeDepart + " à " + villeArrivee + " selon le critère " + user.getCritere() + " et le moyen de transport " + moyenTransport + ":");
        } else {
            System.out.println("Les " + chemins_max.size() + " plus courts chemins trouvés de " + villeDepart + " à " + villeArrivee + " selon le critère " + user.getCritere() + " et le moyen de transport " + moyenTransport + " sont :");
        }
        
        for (String chemin : chemins_max) {
            System.out.println(chemin); // Affiche chaque chemin trouvé 
        }
        
        
    }

    // Méthode pour vérifier si le moyen de transport est valide, note :j'aurais pu faire avec TypeCout.valueOf() mais ça fonctionne
    private static boolean estMoyenTransportValide(String moyenTransport) {
        return moyenTransport.equals("TRAIN") || moyenTransport.equals("AVION") || moyenTransport.equals("BUS");
    }

    // Méthode pour vérifier si un lien existe entre les deux structures par le moyen de transport spécifié, ça réutilise une méthode de plateforme 
    private static boolean lienExiste(Plateforme p, String villeDepart, String villeArrivee, String moyenTransport) {
        ModaliteTransport modalite = ModaliteTransport.valueOf(moyenTransport);
        Structure depart = p.getStructure(Structure.nom(villeDepart, modalite));
        Structure arrivee = p.getStructure(Structure.nom(villeArrivee, modalite));
        return p.isLinked(depart, arrivee);
    }

    // tout est dans le titre, la méthode permet même de modifier les informations
    public static void afficherInfosUtilisateur() {
        System.out.println("Informations utilisateur:");
        System.out.println(user);
        
        while (true) {
            System.out.println("\nQue souhaitez-vous modifier ?");
            System.out.println("1. Nom");
            System.out.println("2. Prénom");
            System.out.println("3. Ville");
            System.out.println("4. Critère");
            System.out.println("5. Retour au menu principal");
            System.out.print("Choisissez une option: ");
            
            int choix = getValidIntInput();
    
            switch (choix) {
                case 1:
                    // Nom
                     clear();    
                    System.out.print("Entrez le nouveau nom: ");
                    String nouveauNom = scanner.nextLine();
                    user.setNom(nouveauNom);
                    break;
                case 2:
                    // Prénom
                    clear();
                    System.out.print("Entrez le nouveau prénom: ");
                    String nouveauPrenom = scanner.nextLine();
                    user.setPrenom(nouveauPrenom);
                    break;
                case 3:
                    // ville de référence note : supprimé au lancement donc initialement c'est établit à : "Aucune"
                    clear();
                    System.out.print("Entrez la nouvelle ville: ");
                    String nouvelleVille = scanner.nextLine();
                    if (!p.containsVille(nouvelleVille)) {
                        user.setVille(nouvelleVille);
                    } else {
                        System.out.println("Cette ville n'existe pas. Modification annulée.");
                    }
                    break;
                case 4:
                    // Critère (TEMPS, PRIX, CO2)
                    clear();
                    System.out.print("Entrez le nouveau critère (TEMPS, PRIX, CO2): ");
                    String nouveauCritere = scanner.nextLine().toUpperCase();
                    try {
                        user.setCritere(TypeCout.valueOf(nouveauCritere));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Critère invalide. Modification annulée.");
                    }
                    break;
                case 5:
                    clear();
                    return; // Retour au menu principal
                default:   
                    // Cas où on rentre un autre int
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
    
            // Affichage des informations mises à jour
            System.out.println("\nInformations mises à jour:");
            System.out.println(user);
        }
    }

    // méthode qui verifie les données 
    public static boolean verif(String [] datav, String [] datac) {
        Verification verificationData = new Verification();
        // Vérifier la validité des données
        boolean isValid = verificationData.dataIsValid(datav ) &&  verificationData.correspondanceIsValid(datac);
        System.out.println("Toutes les données sont valides : " + isValid + '\n');
        return isValid;
    }


    

    public static void main(String[] args) {
        if (verif(data_villes, data_correspondances)) {
            // Création de la plateforme
            createPlateforme();
            createUser();
            menu();
        }
    }
        

}
