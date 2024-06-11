package version3.main;

import java.util.List;
import java.util.Scanner;

import fr.ulille.but.sae_s2_2024.Chemin;
import version3.user.Voyageur;
import version3.graphe.Algorithme;
import version3.graphe.Plateforme2;
import version3.graphe.Structure;
import version3.utils.data.extract.VilleDataExtractor;
import version3.utils.verifications.Verifications;
import version3.graphe.TypeCout;

public class Main2 {
    
    private static Plateforme2 p;
    private static Voyageur user;
    private static Scanner scanner = new Scanner(System.in);

    public static void clearTerminal(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void createPlateforme() {
        p = new Plateforme2();
    }

    public static void createUser() {
        System.out.println("Bonjour et bienvenue sur Woze, votre Plateforme de Comparaison d’itinéraires de transport.\n");

        System.out.println("Quel est votre Prénom ?");
        final String prenom = scanner.nextLine();

        System.out.println("Quel est votre Nom ?");
        final String nom = scanner.nextLine();

        final String villeDepart = "Aucune";

        clearTerminal();
        System.out.println("Quel critère de recherche d'itinéraire vous semble le plus adapté ? (Temps/Co2/Prix)");
        String critere = scanner.nextLine().toUpperCase();
        while(!Verifications.estCritereValide(critere)){
            System.out.println("Critère invalide. (Temps/Co2/Prix)");
            critere = scanner.nextLine().toUpperCase();
        };
        final TypeCout crit = TypeCout.valueOf(critere);
    
        clearTerminal();
        user = new Voyageur(prenom, nom, villeDepart, crit);
        p.addUser(user);
        p.setCurrentUser(user);
    }

    public static void displayMenu() {
        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Voir le graphe");
            System.out.println("2. Chercher un chemin");
            System.out.println("3. Mes informations");
            System.out.println("4. Changer d'utilisateur");
            System.out.println("5. Quitter");
            System.out.print("Votre choix: ");
            choice = Verifications.getValidIntInput(scanner);
            clearTerminal();
    
            switch (choice) {
                case 1:
                    displayGraph();
                    break;
                case 2:
                    searchPath();
                    break;
                case 3:
                    displayUserInfo();
                    break;
                case 4:
                    changeUser();
                    break;
                case 5:
                    scanner.close();
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        } while (choice != 5);
    }

    public static void changeUser() {
        System.out.println("Êtes-vous sûr de vouloir changer d'utilisateur ? (Oui/Non)");
        final String confirmation = scanner.nextLine().toUpperCase();
        if ("OUI".equals(confirmation) || "O".equals(confirmation)) {
            chooseUser();
        } else if ("NON".equals(confirmation) || "N".equals(confirmation)) {
            System.out.println("Changement d'utilisateur annulé.");
        } else {
            System.out.println("Choix invalide. Retour au menu principal.");
        }
    }

    public static void displayGraph() {
        System.out.println(p.getCurrentGraphe());
    }

    public static void searchPath() {
        if (p == null || user == null) {
            System.out.println("Veuillez d'abord créer une plateforme et un utilisateur.");
            return;
        }

        clearTerminal();

        // Départ
        System.out.println("De quelle structure souhaitez-vous partir ?");
        for (int i = 0; i < p.getStructures().size(); i++) {
            System.out.println((i + 1) + ". " + p.getStructures().get(i).getNom());
        }
        final int choixStructureDepart = Verifications.getValidIntInput(scanner) - 1;
        final Structure depart = p.getStructures().get(choixStructureDepart);

        // Arrivée
        System.out.println("À quelle structure souhaitez-vous arriver ?");
        for (int i = 0; i < p.getStructures().size(); i++) {
            System.out.println((i + 1) + ". " + p.getStructures().get(i).getNom());
        }
        final int choixStructureArrivee = Verifications.getValidIntInput(scanner) - 1;
        final Structure arrivee = p.getStructures().get(choixStructureArrivee);

        // Nombre de chemins à trouver
        System.out.println("Combien de chemins souhaitez-vous trouver ?");
        final int k = Verifications.getValidIntInput(scanner);

        // Poids maximum du trajet
        System.out.println("Quel poids ne doit pas excéder le trajet ?");
        final double poidsMax = Verifications.getValidDoubleInput(scanner);

        // Deuxième critère
        System.out.println("Quel est le deuxième critère (temps, prix ou CO2) ?");
        final String critere2 = scanner.next();
        final TypeCout critere2Enum = TypeCout.valueOf(critere2.toUpperCase());

        // Recherche des chemins
        List<Chemin> chemins = Algorithme.KPlusCourtsChemins(p, depart, arrivee, critere2Enum, k, critere2Enum, poidsMax);
        chemins = Plateforme2.reductionAffichageChemins(chemins);

        // Affichage des résultats
        if (chemins.isEmpty()) {
            System.out.println("Aucun chemin trouvé de " + depart.getNom() + " à " + arrivee.getNom() + " selon le critère " + user.getCritere() + ".");
        } else if (chemins.size() == 1) {
            System.out.println("Chemin le plus court trouvé de " + depart.getNom() + " à " + arrivee.getNom() + " selon le critère " + user.getCritere() + ":");
            for (final Chemin chemin : chemins) {
                System.out.println(chemin.toString());
            }
        } else {
            System.out.println("Les " + chemins.size() + " plus courts chemins trouvés de " + depart.getNom() + " à " + arrivee.getNom() + " selon le critère " + user.getCritere() + " sont :");
            for (final Chemin chemin : chemins) {
                System.out.println(chemin.toString());
            }
        }
    }


    public static void deleteUserData() {
        // Implémentation de la suppression des données utilisateur
    }

    public static void displayUserInfo() {
        // Implémentation de l'affichage des informations utilisateur
    }

    public static void verifyData(String [] datav) {
        // Implémentation de la vérification des données
    }

    public static void chooseUser() {
        // Implémentation du choix de l'utilisateur
    }

    public static void main(String[] args) {
        // Vérification des données
        verifyData(VilleDataExtractor.data_villes);

        // Création de la plateforme
        createPlateforme();

        // Choix de l'utilisateur
        chooseUser();

        displayMenu();
    }
}
